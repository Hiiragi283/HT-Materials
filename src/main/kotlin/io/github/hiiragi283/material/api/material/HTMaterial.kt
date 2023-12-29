package io.github.hiiragi283.material.api.material

import io.github.hiiragi283.material.api.material.flag.HTMaterialFlag
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlagSet
import io.github.hiiragi283.material.api.material.property.HTMaterialProperty
import io.github.hiiragi283.material.api.material.property.HTMaterialPropertyMap
import io.github.hiiragi283.material.api.material.property.HTPropertyKey
import io.github.hiiragi283.material.api.shape.HTShape
import io.github.hiiragi283.material.api.shape.HTShapeKey
import io.github.hiiragi283.material.api.shape.HTShapes
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class HTMaterial private constructor(
    val key: HTMaterialKey,
    val info: HTMaterialInfo,
    val properties: HTMaterialPropertyMap,
    val flags: HTMaterialFlagSet
) {

    //    Properties    //

    fun <T : HTMaterialProperty<T>> getProperty(key: HTPropertyKey<T>): T? = properties.getAs(key)

    fun hasProperty(key: HTPropertyKey<*>): Boolean = key in properties

    fun getDefaultShape(): HTShapeKey? = when {
        hasProperty(HTPropertyKey.METAL) -> HTShapes.INGOT
        hasProperty(HTPropertyKey.GEM) -> HTShapes.GEM
        else -> null
    }

    fun verify() {
        properties.verify(this)
        flags.verify(this)
    }

    //    Flags    //

    fun hasFlag(flag: HTMaterialFlag): Boolean = flag in flags

    //    Any    //

    operator fun component1() = info

    operator fun component2() = properties

    operator fun component3() = flags

    override fun toString(): String = key.toString()

    companion object {

        private val LOGGER: Logger = LoggerFactory.getLogger(HTMaterial::class.java)

        //    Registry    //

        private val registry: MutableMap<HTMaterialKey, HTMaterial> = linkedMapOf()

        @JvmField
        val REGISTRY: Map<HTMaterialKey, HTMaterial> = registry

        @JvmStatic
        fun getMaterial(key: HTMaterialKey): HTMaterial =
            registry[key] ?: throw IllegalStateException("Material: $key is not registered!")

        @JvmStatic
        fun getMaterialOrNull(key: HTMaterialKey): HTMaterial? = registry[key]

        @JvmStatic
        internal fun create(
            key: HTMaterialKey,
            info: HTMaterialInfo,
            properties: HTMaterialPropertyMap,
            flags: HTMaterialFlagSet
        ): HTMaterial = HTMaterial(key, info, properties, flags).also {
            registry.putIfAbsent(key, it)
            LOGGER.info("Material: $key registered!")
        }

        private val shapeKey = HTShapeKey("fluid")

        fun appendTooltip(material: HTMaterial, shape: HTShape?, stack: ItemStack, lines: MutableList<Text>) {
            //Title
            lines.add(TranslatableText("tooltip.ht_materials.material.title"))
            //Name
            val name: String = shape?.key?.getTranslatedName(material.key) ?: material.key.getTranslatedName()
            lines.add(TranslatableText("tooltip.ht_materials.material.name", name))
            //Formula
            material.info.formula.takeIf(String::isNotEmpty)?.let { formula: String ->
                lines.add(TranslatableText("tooltip.ht_materials.material.formula", formula))
            }
            //Molar Mass
            material.info.molarMass.takeIf { it > 0.0 }?.let { molar: Double ->
                lines.add(TranslatableText("tooltip.ht_materials.material.molar", molar))
            }
            //Tooltip from Properties
            material.properties.values.forEach { it.appendTooltip(material, shape, stack, lines) }
        }

    }

}