package io.github.hiiragi283.material.api.shape

import io.github.hiiragi283.material.HTMaterialsCommon
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlag
import io.github.hiiragi283.material.util.commonId
import net.minecraft.item.Item
import net.minecraft.tag.TagKey
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

abstract class HTShape(val name: String) {

    //    Predicate    //

    abstract fun canGenerateBlock(material: HTMaterial): Boolean

    abstract fun canGenerateItem(material: HTMaterial): Boolean

    //    Identifier    //

    fun getIdentifier(material: HTMaterial, namespace: String = HTMaterialsCommon.MOD_ID): Identifier =
        Identifier(namespace, getIdPath(material))

    abstract fun getIdPath(material: HTMaterial): String

    //    TagKey    //

    fun getForgeTag(material: HTMaterial): TagKey<Item> =
        TagKey.of(Registry.ITEM_KEY, commonId(getForgePath(material)))

    abstract fun getForgePath(material: HTMaterial): String

    fun getCommonTag(material: HTMaterial): TagKey<Item> =
        TagKey.of(Registry.ITEM_KEY, commonId(getCommonPath(material)))

    abstract fun getCommonPath(material: HTMaterial): String

    companion object {

        @JvmStatic
        fun create(name: String): HTShape = Impl(name)

        @JvmStatic
        fun createAndRegister(name: String): HTShape = create(name).also(HTShapes::register)

    }

    //    Any    //

    override fun equals(other: Any?): Boolean = when (other) {
        null -> false
        !is HTShape -> false
        else -> other.name == this.name
    }

    override fun hashCode(): Int = name.hashCode()

    override fun toString(): String = name

    //    Impl    //

    private class Impl(name: String) : HTShape(name) {

        override fun canGenerateBlock(material: HTMaterial): Boolean = false

        override fun canGenerateItem(material: HTMaterial): Boolean =
            HTMaterialFlag.REGISTRY["generate_$name"]?.let(material::hasFlag) ?: false

        override fun getIdPath(material: HTMaterial): String = "${material.getName()}_$name"

        override fun getForgePath(material: HTMaterial): String {
            val split: List<String> = name.split("_")
            return when (split.size) {
                1 -> "${name}s/${material.getName()}"
                2 -> "${split[0]}s/${split[1]}/${material.getName()}"
                else -> throw IllegalStateException("Cannot decompose name: $name into Forge Tag format!")
            }
        }

        override fun getCommonPath(material: HTMaterial): String = "${material.getName()}_${name}s"

    }

}