package io.github.hiiragi283.material.api.material

import io.github.hiiragi283.material.HTMaterialsCommon
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlag
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlagsNew
import io.github.hiiragi283.material.api.material.property.HTMaterialPropertiesNew
import io.github.hiiragi283.material.api.material.property.HTMaterialProperty
import io.github.hiiragi283.material.api.material.property.HTPropertyKey
import io.github.hiiragi283.material.api.part.HTPart
import io.github.hiiragi283.material.api.shape.HTShape
import io.github.hiiragi283.material.api.shape.HTShapes
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.resource.language.I18n
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier

class HTMaterialNew internal constructor(
    val info: HTMaterialInfo,
    val properties: HTMaterialPropertiesNew,
    val flags: HTMaterialFlagsNew
) {

    init {
        registry.putIfAbsent(info.name, this)
    }

    //    Info    //

    fun getName(): String = info.name

    fun getIdentifier(namespace: String = HTMaterialsCommon.MOD_ID): Identifier = Identifier(namespace, getName())

    @Environment(EnvType.CLIENT)
    fun getTranslatedName(): String = I18n.translate(info.translationKey)

    fun getTranslatedText(): TranslatableText = TranslatableText(info.translationKey)

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

    override fun toString(): String = info.name

    companion object {

        //    Registry    //

        private val registry: MutableMap<String, HTMaterialNew> = linkedMapOf()

        @JvmField
        val REGISTRY: Collection<HTMaterialNew> = registry.values

        @JvmStatic
        fun getMaterial(name: String): HTMaterialNew? = registry[name]

        private val FLUID = object : HTShape("fluid") {

            override fun canGenerateBlock(material: HTMaterialNew): Boolean = false

            override fun canGenerateItem(material: HTMaterialNew): Boolean = false

            override fun getIdPath(material: HTMaterialNew): String = material.getName()

            override fun getForgePath(material: HTMaterialNew): String {
                throw UnsupportedOperationException()
            }

            override fun getCommonPath(material: HTMaterialNew): String {
                throw UnsupportedOperationException()
            }

        }

    }

    fun appendFluidTooltip(stack: ItemStack, lines: MutableList<Text>) {
        HTPart(this, FLUID).appendTooltip(stack, lines)
    }

}