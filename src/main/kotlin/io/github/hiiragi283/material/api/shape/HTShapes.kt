package io.github.hiiragi283.material.api.shape

import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlag

object HTShapes {

    internal var canModify: Boolean = true

    private val map: MutableMap<String, HTShape> = mutableMapOf()

    @JvmField
    val REGISTRY: Collection<HTShape> = map.values

    @JvmStatic
    fun getShape(name: String): HTShape? = map[name]

    @JvmStatic
    fun register(shape: HTShape): HTShape = shape.also { map.putIfAbsent(it.name, shape) }

    //    Block    //

    @JvmField
    val BLOCK: HTShape = register(object : HTShape("block") {

        override fun canGenerateBlock(material: HTMaterial): Boolean = material.hasFlag(HTMaterialFlag.GENERATE_BLOCk)

        override fun canGenerateItem(material: HTMaterial): Boolean = false

        override fun getIdPath(material: HTMaterial): String = "${material.getName()}_block"

        override fun getForgePath(material: HTMaterial): String = "storage_blocks/${material.getName()}"

        override fun getCommonPath(material: HTMaterial): String = "${material.getName()}_blocks"

    })

    @JvmField
    val ORE: HTShape = register(object : HTShape("ore") {

        override fun canGenerateBlock(material: HTMaterial): Boolean = false

        override fun canGenerateItem(material: HTMaterial): Boolean = false

        override fun getIdPath(material: HTMaterial): String = "${material.getName()}_ore"

        override fun getForgePath(material: HTMaterial): String = "ores/${material.getName()}"

        override fun getCommonPath(material: HTMaterial): String = "${material.getName()}_ores"

    })

    @JvmField
    val RAW_BLOCK: HTShape = register(object : HTShape("raw_block") {

        override fun canGenerateBlock(material: HTMaterial): Boolean = false

        override fun canGenerateItem(material: HTMaterial): Boolean = false

        override fun getIdPath(material: HTMaterial): String = "raw_${material.getName()}_block"

        override fun getForgePath(material: HTMaterial): String = "storage_blocks/raw_${material.getName()}"

        override fun getCommonPath(material: HTMaterial): String = "raw_${material.getName()}_blocks"

    })

    //    Fluid    //

    //DO NOT call register()!!
    @JvmField
    val FLUID = object : HTShape("fluid") {

        override fun canGenerateBlock(material: HTMaterial): Boolean = false

        override fun canGenerateItem(material: HTMaterial): Boolean = false

        override fun getIdPath(material: HTMaterial): String = material.getName()

        override fun getForgePath(material: HTMaterial): String {
            throw UnsupportedOperationException()
        }

        override fun getCommonPath(material: HTMaterial): String {
            throw UnsupportedOperationException()
        }

    }

    //    Item    //

    @JvmField
    val BLADE: HTShape = HTShape.create("blade")

    @JvmField
    val BOLT: HTShape = HTShape.create("bolt")

    @JvmField
    val BUCKET: HTShape = HTShape.create("bucket")

    @JvmField
    val CRUSHED_DUST: HTShape = HTShape.create("crushed_dust")

    @JvmField
    val CURVED_PLATE: HTShape = HTShape.create("curved_plate")

    @JvmField
    val DOUBLE_INGOT: HTShape = HTShape.create("double_ingot")

    @JvmField
    val DRILL_HEAD: HTShape = HTShape.create("drill_head")

    @JvmField
    val DUST: HTShape = HTShape.create("dust")

    @JvmField
    val GEAR: HTShape = HTShape.create("gear")

    @JvmField
    val GEM: HTShape = HTShape.create("gem")

    @JvmField
    val HOT_INGOT: HTShape = HTShape.create("hot_ingot")

    @JvmField
    val INGOT: HTShape = HTShape.create("ingot")

    @JvmField
    val LARGE_PLATE: HTShape = HTShape.create("large_plate")

    @JvmField
    val NUGGET: HTShape = HTShape.create("nugget")

    @JvmField
    val PLATE: HTShape = HTShape.create("plate")

    @JvmField
    val RING: HTShape = HTShape.create("ring")

    @JvmField
    val RAW_ORE: HTShape = object : HTShape("raw_ore") {

        override fun canGenerateBlock(material: HTMaterial): Boolean = false

        override fun canGenerateItem(material: HTMaterial): Boolean = false

        override fun getIdPath(material: HTMaterial): String = "raw_${material.getName()}_block"

        override fun getForgePath(material: HTMaterial): String = "raw_materials/${material.getName()}"

        override fun getCommonPath(material: HTMaterial): String = "raw_${material.getName()}_ores"

    }

    @JvmField
    val ROD: HTShape = HTShape.create("rod")

    @JvmField
    val ROTOR: HTShape = HTShape.create("rotor")

    @JvmField
    val SMALL_DUST: HTShape = HTShape.create("small_dust")

    @JvmField
    val TINY_DUST: HTShape = HTShape.create("tiny_dust")

    @JvmField
    val WIRE: HTShape = HTShape.create("wire")

}