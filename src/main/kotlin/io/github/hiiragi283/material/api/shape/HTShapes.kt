package io.github.hiiragi283.material.api.shape

import io.github.hiiragi283.material.api.material.HTMaterialNew
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlag

object HTShapes {

    //    Block    //

    @JvmField
    val BLOCK: HTShape = HTShape.register(object : HTShape("block") {

        override fun canGenerateBlock(material: HTMaterialNew): Boolean = material.hasFlag(HTMaterialFlag.GENERATE_BLOCk)

        override fun canGenerateItem(material: HTMaterialNew): Boolean = false

        override fun getIdPath(material: HTMaterialNew): String = "${material.getName()}_block"

        override fun getForgePath(material: HTMaterialNew): String = "storage_blocks/${material.getName()}"

        override fun getCommonPath(material: HTMaterialNew): String = "${material.getName()}_blocks"

    })

    @JvmField
    val ORE: HTShape = HTShape.register(object : HTShape("ore") {

        override fun canGenerateBlock(material: HTMaterialNew): Boolean = false

        override fun canGenerateItem(material: HTMaterialNew): Boolean = false

        override fun getIdPath(material: HTMaterialNew): String = "${material.getName()}_ore"

        override fun getForgePath(material: HTMaterialNew): String = "ores/${material.getName()}"

        override fun getCommonPath(material: HTMaterialNew): String = "${material.getName()}_ores"

    })

    @JvmField
    val RAW_BLOCK: HTShape = HTShape.register(object : HTShape("raw_block") {

        override fun canGenerateBlock(material: HTMaterialNew): Boolean = false

        override fun canGenerateItem(material: HTMaterialNew): Boolean = false

        override fun getIdPath(material: HTMaterialNew): String = "raw_${material.getName()}_block"

        override fun getForgePath(material: HTMaterialNew): String = "storage_blocks/raw_${material.getName()}"

        override fun getCommonPath(material: HTMaterialNew): String = "raw_${material.getName()}_blocks"

    })

    //    Item    //

    @JvmField
    val BLADE: HTShape = HTShape.createAndRegister("blade")

    @JvmField
    val BOLT: HTShape = HTShape.createAndRegister("bolt")

    @JvmField
    val CRUSHED_DUST: HTShape = HTShape.createAndRegister("crushed_dust")

    @JvmField
    val CURVED_PLATE: HTShape = HTShape.createAndRegister("curved_plate")

    @JvmField
    val DOUBLE_INGOT: HTShape = HTShape.createAndRegister("double_ingot")

    @JvmField
    val DRILL_HEAD: HTShape = HTShape.createAndRegister("drill_head")

    @JvmField
    val DUST: HTShape = HTShape.createAndRegister("dust")

    @JvmField
    val GEAR: HTShape = HTShape.createAndRegister("gear")

    @JvmField
    val GEM: HTShape = HTShape.createAndRegister("gem")

    @JvmField
    val HOT_INGOT: HTShape = HTShape.createAndRegister("hot_ingot")

    @JvmField
    val INGOT: HTShape = HTShape.createAndRegister("ingot")

    @JvmField
    val LARGE_PLATE: HTShape = HTShape.createAndRegister("large_plate")

    @JvmField
    val NUGGET: HTShape = HTShape.createAndRegister("nugget")

    @JvmField
    val PLATE: HTShape = HTShape.createAndRegister("plate")

    @JvmField
    val RING: HTShape = HTShape.createAndRegister("ring")

    @JvmField
    val RAW_ORE: HTShape = object : HTShape("raw_ore") {

        override fun canGenerateBlock(material: HTMaterialNew): Boolean = false

        override fun canGenerateItem(material: HTMaterialNew): Boolean = false

        override fun getIdPath(material: HTMaterialNew): String = "raw_${material.getName()}_block"

        override fun getForgePath(material: HTMaterialNew): String = "raw_materials/${material.getName()}"

        override fun getCommonPath(material: HTMaterialNew): String = "raw_${material.getName()}_ores"

    }

    @JvmField
    val ROD: HTShape = HTShape.createAndRegister("rod")

    @JvmField
    val ROTOR: HTShape = HTShape.createAndRegister("rotor")

    @JvmField
    val SMALL_DUST: HTShape = HTShape.createAndRegister("small_dust")

    @JvmField
    val TINY_DUST: HTShape = HTShape.createAndRegister("tiny_dust")

    @JvmField
    val WIRE: HTShape = HTShape.createAndRegister("wire")

}