package io.github.hiiragi283.material.api.material

import io.github.hiiragi283.material.api.material.flag.HTMaterialFlag
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlagsNew
import io.github.hiiragi283.material.api.material.property.HTMaterialPropertiesNew
import io.github.hiiragi283.material.api.material.property.HTMaterialProperty
import io.github.hiiragi283.material.api.material.property.HTPropertyKey
import io.github.hiiragi283.material.api.shape.HTShape
import io.github.hiiragi283.material.api.shape.HTShapes
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class HTMaterialNew private constructor(
    val info: HTMaterialInfo,
    val properties: HTMaterialPropertiesNew,
    val flags: HTMaterialFlagsNew
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
        flags.verify(this)
    }

    //    Flags    //

    fun hasFlag(flag: HTMaterialFlag): Boolean = flag in flags

    //    Any    //

    operator fun component1() = info

    operator fun component2() = properties

    operator fun component3() = flags

    override fun toString(): String = getKey(this).name

    companion object {

        private val LOGGER: Logger = LoggerFactory.getLogger(HTMaterialNew::class.java)

        //    Registry    //

        private val registry: MutableMap<HTMaterialKey, HTMaterialNew> = linkedMapOf()

        @JvmField
        val REGISTRY: Map<HTMaterialKey, HTMaterialNew> = registry

        @JvmStatic
        fun getMaterial(key: HTMaterialKey): HTMaterialNew =
            registry[key] ?: throw IllegalStateException("Material: $key is not registered!")

        @JvmStatic
        fun getKey(material: HTMaterialNew): HTMaterialKey = registry.map { it.value to it.key }.toMap()[material]
            ?: throw IllegalStateException("Material key not found!!")

        @JvmStatic
        internal fun create(
            key: HTMaterialKey,
            info: HTMaterialInfo,
            properties: HTMaterialPropertiesNew,
            flags: HTMaterialFlagsNew
        ): HTMaterialNew = HTMaterialNew(info, properties, flags).also {
            registry.putIfAbsent(key, it)
            LOGGER.info("Material: $key registered!")
        }

        private val FLUID = object : HTShape("fluid") {

            override fun canGenerateBlock(material: HTMaterialNew): Boolean = false

            override fun canGenerateItem(material: HTMaterialNew): Boolean = false

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
        //HTPart(getRegistry().inverse().get(this), FLUID).appendTooltip(stack, lines)
    }

}