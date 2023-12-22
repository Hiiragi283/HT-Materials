package io.github.hiiragi283.material.api.shape

import io.github.hiiragi283.material.HTMaterialsCommon
import io.github.hiiragi283.material.api.material.HTMaterialNew
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

    abstract fun canGenerateBlock(material: HTMaterialNew): Boolean

    abstract fun canGenerateItem(material: HTMaterialNew): Boolean

    //    Identifier    //

    fun getIdentifier(material: HTMaterialNew, namespace: String = HTMaterialsCommon.MOD_ID): Identifier =
        Identifier(namespace, getIdPath(material))

    abstract fun getIdPath(material: HTMaterialNew): String

    //    TagKey    //

    fun getForgeTag(material: HTMaterialNew): TagKey<Item> =
        TagKey.of(Registry.ITEM_KEY, commonId(getForgePath(material)))

    abstract fun getForgePath(material: HTMaterialNew): String

    fun getCommonTag(material: HTMaterialNew): TagKey<Item> =
        TagKey.of(Registry.ITEM_KEY, commonId(getCommonPath(material)))

    abstract fun getCommonPath(material: HTMaterialNew): String

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

        override fun canGenerateBlock(material: HTMaterialNew): Boolean = false

        override fun canGenerateItem(material: HTMaterialNew): Boolean =
            HTMaterialFlag.getFlag("generate_$name")?.let(material::hasFlag) ?: false

        override fun getIdPath(material: HTMaterialNew): String = "${material.getName()}_$name"

        override fun getForgePath(material: HTMaterialNew): String {
            val split: List<String> = name.split("_")
            return when (split.size) {
                1 -> "${name}s/${material.getName()}"
                2 -> "${split[0]}s/${split[1]}/${material.getName()}"
                else -> throw IllegalStateException("Cannot decompose name: $name into Forge Tag format!")
            }
        }

        override fun getCommonPath(material: HTMaterialNew): String = "${material.getName()}_${name}s"

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
            LOGGER.info("The Shape: ${it.name} registered!")
        }

    }

}