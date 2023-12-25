package io.github.hiiragi283.material.api.shape

import io.github.hiiragi283.material.HTMaterialsCommon
import io.github.hiiragi283.material.api.HTMaterialsAddon
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlag
import io.github.hiiragi283.material.api.registry.HTDefaultedMap
import io.github.hiiragi283.material.api.registry.HTObjectKeySet

object HTShapes : HTMaterialsAddon {

    //    Block    //

    @JvmField
    val BLOCK = HTShapeKey("block", forgePath = "storage_blocks/%s")

    @JvmField
    val ORE = HTShapeKey("ore")

    @JvmField
    val RAW_BLOCK = HTShapeKey("raw_block", forgePath = "storage_blocks/raw_%s")

    //    Item    //

    @JvmField
    val DUST = HTShapeKey("dust")

    @JvmField
    val GEAR = HTShapeKey("gear")

    @JvmField
    val GEM = HTShapeKey("gem")

    @JvmField
    val INGOT = HTShapeKey("ingot")

    @JvmField
    val NUGGET = HTShapeKey("nugget")

    @JvmField
    val PLATE = HTShapeKey("plate")

    @JvmField
    val RAW_ORE = HTShapeKey(
        "raw_ore",
        idPath = "raw_%s_ores",
        forgePath = "raw_materials/%s"
    )

    @JvmField
    val ROD = HTShapeKey("rod")

    //    Register    //

    override val modId: String = HTMaterialsCommon.MOD_ID

    override val priority: Int = -200

    override fun registerShape(registry: HTObjectKeySet<HTShapeKey>) {
        //Block
        registry.addAll(
            BLOCK,
            ORE,
            RAW_BLOCK
        )
        //Item
        registry.addAll(
            DUST,
            GEAR,
            GEM,
            INGOT,
            NUGGET,
            PLATE,
            RAW_ORE,
            ROD
        )
    }

    override fun modifyShapePredicate(registry: HTDefaultedMap<HTShapeKey, HTShapePredicate.Builder>) {
        //Block
        //Item
        registry.getOrCreate(DUST).apply {
            disabled = false
            requiredFlags.add(HTMaterialFlag.GENERATE_DUST)
        }
        registry.getOrCreate(GEAR).apply {
            disabled = false
            requiredFlags.add(HTMaterialFlag.GENERATE_GEAR)
        }
        registry.getOrCreate(GEM).apply {
            disabled = false
            requiredFlags.add(HTMaterialFlag.GENERATE_GEM)
        }
        registry.getOrCreate(INGOT).apply {
            disabled = false
            requiredFlags.add(HTMaterialFlag.GENERATE_INGOT)
        }
        registry.getOrCreate(NUGGET).apply {
            disabled = false
            requiredFlags.add(HTMaterialFlag.GENERATE_NUGGET)
        }
        registry.getOrCreate(PLATE).apply {
            disabled = false
            requiredFlags.add(HTMaterialFlag.GENERATE_PLATE)
        }
        registry.getOrCreate(ROD).apply {
            disabled = false
            requiredFlags.add(HTMaterialFlag.GENERATE_ROD)
        }
    }

}