package io.github.hiiragi283.material.api.shape

import io.github.hiiragi283.material.HTMaterialsCommon
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlag
import io.github.hiiragi283.material.util.commonId
import net.minecraft.item.Item
import net.minecraft.tag.TagKey
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class HTShape(val name: String) {

    //    Predicate    //

    abstract fun canGenerateItem(material: HTMaterial): Boolean

    //    Identifier    //

    fun getIdentifier(material: HTMaterialKey, namespace: String = HTMaterialsCommon.MOD_ID): Identifier =
        Identifier(namespace, getIdPath(material))

    abstract fun getIdPath(material: HTMaterialKey): String

    //    TagKey    //

    fun getForgeTag(material: HTMaterialKey): TagKey<Item> =
        TagKey.of(Registry.ITEM_KEY, commonId(getForgePath(material)))

    abstract fun getForgePath(material: HTMaterialKey): String

    fun getCommonTag(material: HTMaterialKey): TagKey<Item> =
        TagKey.of(Registry.ITEM_KEY, commonId(getCommonPath(material)))

    abstract fun getCommonPath(material: HTMaterialKey): String

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

        override fun canGenerateItem(material: HTMaterial): Boolean =
            HTMaterialFlag.getFlag("generate_$name")?.let(material::hasFlag) ?: false

        override fun getIdPath(material: HTMaterialKey): String = "${material.name}_$name"

        override fun getForgePath(material: HTMaterialKey): String {
            val split: List<String> = name.split("_")
            return when (split.size) {
                1 -> "${name}s/${material.name}"
                2 -> "${split[0]}s/${split[1]}/${material.name}"
                else -> throw IllegalStateException("Cannot decompose name: $name into Forge Tag format!")
            }
        }

        override fun getCommonPath(material: HTMaterialKey): String = "${material.name}_${name}s"

    }

    companion object {

        private val LOGGER: Logger = LoggerFactory.getLogger(this::class.java)

        internal var canModify: Boolean = true

        private val map: MutableMap<String, HTShape> = linkedMapOf()

        @JvmField
        val REGISTRY: Collection<HTShape> = map.values

        @JvmStatic
        fun getShape(name: String): HTShape? = map[name]

        @JvmStatic
        fun create(name: String): HTShape = Impl(name)

        @JvmStatic
        fun createAndRegister(name: String): HTShape = create(name).also(::register)

        @JvmStatic
        fun register(shape: HTShape): HTShape = shape.also {
            map.putIfAbsent(it.name, shape)
            LOGGER.info("Shape: $it registered!")
        }

    }

}