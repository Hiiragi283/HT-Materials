package io.github.hiiragi283.api.material

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.extension.Encodable
import io.github.hiiragi283.api.material.composition.HTMaterialComposition
import io.github.hiiragi283.api.material.element.HTElement
import io.github.hiiragi283.api.material.property.HTPropertyMap
import io.github.hiiragi283.api.material.property.HTPropertyType
import io.github.hiiragi283.api.material.property.MaterialTooltipProvider
import io.github.hiiragi283.api.material.type.HTMaterialType
import io.github.hiiragi283.api.material.type.HTMaterialTypes
import io.github.hiiragi283.api.shape.HTShape
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import java.awt.Color
import java.util.function.BiConsumer
import java.util.function.Consumer

class HTMaterial(builder: Builder) : Encodable<HTMaterial> {
    val key: HTMaterialKey = HTMaterialKey(builder.key)
    val composition: HTMaterialComposition = builder.composition
    val flags: Set<String> = builder.flags.toSet()
    val properties: HTPropertyMap = HTPropertyMap(builder.properties)
    private val type: HTMaterialType = HTMaterialTypes.UNDEFINED

    override val codec: Codec<HTMaterial>
        get() = CODEC

    companion object {
        @JvmField
        val CODEC: Codec<HTMaterial> = RecordCodecBuilder.create { instance ->
            instance.group(
                Codec.STRING.fieldOf("key").forGetter { it.key.name },
                HTMaterialComposition.CODEC.fieldOf("composition").forGetter(HTMaterial::composition),
                Codec.STRING.listOf().fieldOf("flags").forGetter { it.flags.toList() },
                HTPropertyMap.CODEC.fieldOf("properties").forGetter(HTMaterial::properties),
            ).apply(instance) { key, _, _, _ -> HTMaterialsAPI.INSTANCE.materialRegistry().getByName(key) }
        }
    }

    //    Composition    //

    fun componentMap(): Map<HTElement, Int> = composition.componentMap

    fun color(): Color = composition.color

    fun formula(): String = composition.formula

    fun molar(): Double = "%.1f".format(composition.molar).toDouble()

    //    Flags    //

    fun forEachFlag(consumer: Consumer<String>) {
        flags.forEach(consumer)
    }

    fun forEachFlag(action: (String) -> Unit) {
        flags.forEach(action)
    }

    fun hasFlag(flag: String): Boolean = flag in flags

    //    Properties    //

    fun forEachProperty(biConsumer: BiConsumer<HTPropertyType<*>, Any>) {
        properties.forEach(biConsumer)
    }

    fun forEachProperty(action: (HTPropertyType<*>, Any) -> Unit) {
        properties.forEach(action)
    }

    fun <T> getProperty(key: HTPropertyType<T>): T? = key.cast(properties[key])

    fun hasProperty(key: HTPropertyType<*>): Boolean = key in properties

    //    Type    //

    fun getDefaultShape(): HTShape? = type.defaultShapeKey

    //    Other    //

    fun appendTooltip(shapeKey: HTShape?, stack: ItemStack, lines: MutableList<Text>) {
        // Title
        lines.add(Text.translatable("tooltip.ht_materials.material.title"))
        // Name
        val name: String = shapeKey?.getTranslatedName(key) ?: key.getTranslatedName()
        lines.add(Text.translatable("tooltip.ht_materials.material.name", name))
        // Type
        lines.add(Text.translatable("tooltip.ht_materials.material.type", type))
        // Formula
        formula().takeIf(String::isNotEmpty)?.let { formula: String ->
            lines.add(Text.translatable("tooltip.ht_materials.material.formula", formula))
        }
        // Molar Mass
        molar().takeIf { it > 0.0 }?.let { molar: Double ->
            lines.add(Text.translatable("tooltip.ht_materials.material.molar", molar))
        }
        // Tooltip from Properties
        properties.values
            .filterIsInstance<MaterialTooltipProvider>()
            .forEach { it.appendTooltip(this, shapeKey, stack, lines) }
    }

    //    Any    //

    override fun toString(): String = key.toString()

    //    Builder    //

    class Builder(val key: String) {
        var composition: HTMaterialComposition = HTMaterialComposition.EMPTY
        val flags: MutableCollection<String> = mutableSetOf()
        val properties: HTPropertyMap.Builder = HTPropertyMap.Builder()

        fun merge(other: Builder) {
            merge(other.composition, other.flags, other.properties)
        }

        fun merge(composition: HTMaterialComposition, flags: Collection<String>, properties: HTPropertyMap.Builder) {
            // composition
            composition.let { this.composition = it }
            // flags
            this.flags.addAll(flags)
            // properties
            this.properties.addAll(properties)
        }
    }
}
