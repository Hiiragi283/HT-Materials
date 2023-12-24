package io.github.hiiragi283.material.api.material

import io.github.hiiragi283.material.api.material.flag.HTMaterialFlag
import io.github.hiiragi283.material.api.material.property.HTMaterialProperties
import io.github.hiiragi283.material.api.material.property.HTMaterialProperty
import io.github.hiiragi283.material.api.material.property.HTPropertyKey
import io.github.hiiragi283.material.api.part.HTPart
import io.github.hiiragi283.material.api.shape.HTShape
import io.github.hiiragi283.material.api.shape.HTShapes
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class HTMaterial private constructor(
    val key: HTMaterialKey,
    val info: HTMaterialInfo,
    val properties: HTMaterialProperties,
    val flags: Collection<HTMaterialFlag>
) {

    //    Properties    //

    fun <T : HTMaterialProperty<T>> getProperty(key: HTPropertyKey<T>): T? = properties.getAs(key)

    fun <T : HTMaterialProperty<T>> hasProperty(key: HTPropertyKey<T>): Boolean = key in properties

    fun getDefaultShape(): HTShape? = when {
        hasProperty(HTPropertyKey.METAL) -> HTShapes.INGOT
        hasProperty(HTPropertyKey.GEM) -> HTShapes.GEM
        else -> null
    }

    fun verify() {
        properties.verify(this)
        flags.forEach { it.verify(this) }
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
        internal fun create(
            key: HTMaterialKey,
            info: HTMaterialInfo,
            properties: HTMaterialProperties,
            flags: Collection<HTMaterialFlag>
        ): HTMaterial = HTMaterial(key, info, properties, flags).also {
            registry.putIfAbsent(key, it)
            LOGGER.info("Material: $key registered!")
        }

        private val FLUID = object : HTShape("fluid") {

            override fun canGenerateItem(material: HTMaterial): Boolean = false

            override fun getIdPath(material: HTMaterialKey): String = material.name

            override fun getForgePath(material: HTMaterialKey): String {
                throw UnsupportedOperationException()
            }

            override fun getCommonPath(material: HTMaterialKey): String {
                throw UnsupportedOperationException()
            }

        }

    }

    fun appendFluidTooltip(stack: ItemStack, lines: MutableList<Text>) {
        HTPart(key, FLUID).appendTooltip(stack, lines)
    }

}