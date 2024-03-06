package io.github.hiiragi283.api.material.element

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.extension.*
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import java.awt.Color
import java.util.*

@Suppress("DataClassPrivateConstructor")
data class HTElement private constructor(
    val color: Color,
    val formula: String,
    val molar: Double,
) : Encodable<HTElement> {
    override val codec: Codec<HTElement>
        get() = CODEC

    fun bracket() = copy(formula = "($formula)")

    companion object {
        @JvmField
        val CODEC: Codec<HTElement> = RecordCodecBuilder.create { instance ->
            instance.group(
                Identifier.CODEC.optionalFieldOf("id").forGetter {
                    Optional.ofNullable(HTMaterialsAPI.Registries.ELEMENT.getId(it))
                },
                HTColor.CODEC.orElse(HTColor.WHITE).fieldOf("color").forGetter(HTElement::color),
                Codec.STRING.orElse("").fieldOf("formula").forGetter(HTElement::formula),
                Codec.DOUBLE.orElse(0.0).fieldOf("molar").forGetter(HTElement::molar),
            ).apply(instance) { id, color, formula, molar ->
                id.map<HTElement>(HTMaterialsAPI.Registries.ELEMENT::get).orElse(HTElement(color, formula, molar))
            }
        }

        @JvmStatic
        fun of(
            id: Identifier,
            color: Color,
            formula: String,
            molar: Double,
        ): HTElement = Registry.register(
            HTMaterialsAPI.Registries.ELEMENT,
            id,
            HTElement(color, formula, molar),
        )

        @JvmStatic
        fun group(id: Identifier, vararg pairs: Pair<HTElement, Int>): HTElement = group(id, mapOf(*pairs))

        @JvmStatic
        fun group(
            id: Identifier,
            map: Map<HTElement, Int>,
            color: Color = averageColor(map.mapKeys { it.key.color }),
            formula: String = formatFormula(map.mapKeys { it.key.formula }),
            molar: Double = calculateMolar(map.mapKeys { it.key.molar }),
        ): HTElement = Registry.register(
            HTMaterialsAPI.Registries.ELEMENT,
            id,
            HTElement(color, formula, molar),
        )
    }
}
