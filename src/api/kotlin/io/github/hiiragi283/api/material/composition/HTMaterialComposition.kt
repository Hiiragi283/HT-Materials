package io.github.hiiragi283.api.material.composition

import com.mojang.serialization.Codec
import com.mojang.serialization.JsonOps
import com.mojang.serialization.codecs.RecordCodecBuilder
import io.github.hiiragi283.api.extension.*
import io.github.hiiragi283.api.material.element.HTElement
import java.awt.Color

abstract class HTMaterialComposition : Encodable<HTMaterialComposition> {
    abstract val componentMap: Map<HTElement, Int>
    abstract val color: Color
    abstract val formula: String
    abstract val molar: Double

    final override val codec: Codec<HTMaterialComposition>
        get() = CODEC

    companion object {
        @JvmField
        val CODEC: Codec<HTMaterialComposition> = RecordCodecBuilder.create { instance ->
            instance.group(
                Codec.mapPair(HTElement.CODEC.fieldOf("element"), Codec.INT.fieldOf("weight")).xmap(
                    { pair -> pair.mapFirst { encode(it, JsonOps.INSTANCE) } },
                    { pair -> pair.mapFirst { decode(HTElement.CODEC, it, JsonOps.INSTANCE) } },
                ).codec().listOf().fieldOf("componentMap")
                    .forGetter { it.componentMap.mapKeys { entry -> encode(entry.key, JsonOps.INSTANCE) }.toMojangList() },
                HTColor.CODEC.orElse(HTColor.WHITE).fieldOf("color").forGetter(HTMaterialComposition::color),
                Codec.STRING.orElse("").fieldOf("formula").forGetter(HTMaterialComposition::formula),
                Codec.DOUBLE.orElse(0.0).fieldOf("molar").forGetter(HTMaterialComposition::molar),
            ).apply(instance) { componentMap, color, formula, molar ->
                molecular(componentMap.toMap().mapKeys { decode(HTElement.CODEC, it.key, JsonOps.INSTANCE) }) {
                    this.color = color
                    this.formula = formula
                    this.molar = molar
                }
            }
        }

        @JvmField
        val EMPTY: HTMaterialComposition = Empty

        @JvmOverloads
        @JvmStatic
        fun molecular(vararg pairs: Pair<HTElement, Int>, builderAction: Builder.() -> Unit = {}): HTMaterialComposition =
            molecular(mapOf(*pairs), builderAction)

        @JvmOverloads
        @JvmStatic
        fun molecular(map: Map<HTElement, Int>, builderAction: Builder.() -> Unit = {}): HTMaterialComposition =
            Molecular(map).apply(builderAction)

        @JvmOverloads
        @JvmStatic
        fun hydrate(vararg pairs: Pair<HTElement, Int>, waterCount: Int, builderAction: Builder.() -> Unit = {}): HTMaterialComposition =
            hydrate(molecular(*pairs), waterCount, builderAction)

        @JvmOverloads
        @JvmStatic
        fun hydrate(unhydrate: HTMaterialComposition, waterCount: Int, builderAction: Builder.() -> Unit = {}): HTMaterialComposition =
            Hydrate(unhydrate, waterCount).apply(builderAction)

        @JvmOverloads
        @JvmStatic
        fun mixture(vararg providers: HTElement, builderAction: Builder.() -> Unit = {}): HTMaterialComposition =
            mixture(providers.toList(), builderAction)

        @JvmOverloads
        @JvmStatic
        fun mixture(elements: Iterable<HTElement>, builderAction: Builder.() -> Unit = {}): HTMaterialComposition =
            Mixture(elements).apply(builderAction)

        @JvmOverloads
        @JvmStatic
        fun polymer(vararg pairs: Pair<HTElement, Int>, builderAction: Builder.() -> Unit = {}): HTMaterialComposition =
            polymer(molecular(*pairs), builderAction)

        @JvmOverloads
        @JvmStatic
        fun polymer(monomar: HTMaterialComposition, builderAction: Builder.() -> Unit = {}): HTMaterialComposition =
            Polymer(monomar).apply(builderAction)
    }

    private data object Empty : HTMaterialComposition() {
        override val componentMap: Map<HTElement, Int> = mapOf()
        override val color: Color = HTColor.WHITE
        override val formula: String = ""
        override val molar: Double = 0.0
    }

    sealed class Builder(
        override var componentMap: Map<HTElement, Int>,
        override var color: Color,
        override var formula: String,
        override var molar: Double,
    ) : HTMaterialComposition()

    private class Molecular(
        override var componentMap: Map<HTElement, Int>,
        override var color: Color = averageColor(componentMap.mapKeys { it.key.color }),
        override var formula: String = formatFormula(componentMap.mapKeys { it.key.formula }),
        override var molar: Double = calculateMolar(componentMap.mapKeys { it.key.molar }),
    ) : Builder(componentMap, color, formula, molar)

    private class Hydrate(
        unhydrate: HTMaterialComposition,
        waterCount: Int,
        override var color: Color = HTColor.WHITE,
        override var formula: String = "${unhydrate.formula}-${waterCount}H₂O",
        override var molar: Double = unhydrate.molar + waterCount * 18.0,
    ) : Builder(buildMap { putAll(unhydrate.componentMap) }, color, formula, molar)

    private class Mixture(
        elements: Iterable<HTElement>,
        override var color: Color = averageColor(elements.map(HTElement::color)),
        override var formula: String = "",
        override var molar: Double = 0.0,
    ) : Builder(elements.associateWith { 1 }, color, formula, molar)

    private class Polymer(
        monomar: HTMaterialComposition,
        override var color: Color = monomar.color,
        override var formula: String = "(${monomar.formula})n",
        override var molar: Double = 0.0,
    ) : Builder(monomar.componentMap, color, formula, molar)
}
