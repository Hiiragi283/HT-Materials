package io.github.hiiragi283.material.api.shape

import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.HTMaterialKey

object HTShapes {

    //    Block    //

    @JvmField
    val BLOCK: HTShape = HTShape.register(object : HTShape("block") {

        override fun canGenerateItem(material: HTMaterial): Boolean = false

        override fun getIdPath(material: HTMaterialKey): String = "${material.name}_block"

        override fun getForgePath(material: HTMaterialKey): String = "storage_blocks/${material.name}"

        override fun getCommonPath(material: HTMaterialKey): String = "${material.name}_blocks"

    })

    @JvmField
    val ORE: HTShape = HTShape.register(object : HTShape("ore") {

        override fun canGenerateItem(material: HTMaterial): Boolean = false

        override fun getIdPath(material: HTMaterialKey): String = "${material.name}_ore"

        override fun getForgePath(material: HTMaterialKey): String = "ores/${material.name}"

        override fun getCommonPath(material: HTMaterialKey): String = "${material.name}_ores"

    })

    @JvmField
    val RAW_BLOCK: HTShape = HTShape.register(object : HTShape("raw_block") {

        override fun canGenerateItem(material: HTMaterial): Boolean = false

        override fun getIdPath(material: HTMaterialKey): String = "raw_${material.name}_block"

        override fun getForgePath(material: HTMaterialKey): String = "storage_blocks/raw_${material.name}"

        override fun getCommonPath(material: HTMaterialKey): String = "raw_${material.name}_blocks"

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

        override fun canGenerateItem(material: HTMaterial): Boolean = false

        override fun getIdPath(material: HTMaterialKey): String = "raw_${material.name}_block"

        override fun getForgePath(material: HTMaterialKey): String = "raw_materials/${material.name}"

        override fun getCommonPath(material: HTMaterialKey): String = "raw_${material.name}_ores"

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