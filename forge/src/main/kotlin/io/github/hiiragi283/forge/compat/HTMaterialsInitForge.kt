package io.github.hiiragi283.forge.compat

import io.github.hiiragi283.api.HTAddon
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.material.HTMaterialKeys
import io.github.hiiragi283.api.material.HTMaterialsInit
import io.github.hiiragi283.api.material.content.HTMaterialContentMap
import io.github.hiiragi283.api.material.content.HTStorageBlockContent
import io.github.hiiragi283.api.shape.HTShapeKey
import io.github.hiiragi283.api.shape.HTShapeKeys
import io.github.hiiragi283.api.util.collection.DefaultedMap
import io.github.hiiragi283.forge.content.HTForgeFluidContent
import net.minecraft.tag.BlockTags

@HTAddon
class HTMaterialsInitForge : HTMaterialsInit() {
    override fun modifyShapeTagPath(registry: MutableMap<HTShapeKey, String>) {
        // Block
        registry[HTShapeKeys.BLOCK] = "storage_blocks/%s"
        registry[HTShapeKeys.ORE] = "ores/%s"
        // Items
        registry[HTShapeKeys.DUST] = "dusts/%s"
        registry[HTShapeKeys.GEAR] = "gears/%s"
        registry[HTShapeKeys.GEM] = "gems/%s"
        registry[HTShapeKeys.INGOT] = "ingots/%s"
        registry[HTShapeKeys.NUGGET] = "nuggets/%s"
        registry[HTShapeKeys.PLATE] = "plates/%s"
        registry[HTShapeKeys.ROD] = "rods/%s"
    }

    override fun modifyMaterialContent(registry: DefaultedMap<HTMaterialKey, HTMaterialContentMap>) {
        super.modifyMaterialContent(registry)
        // 1st Period
        registry.getOrCreate(HTMaterialKeys.HYDROGEN)
            .add(HTForgeFluidContent())
        registry.getOrCreate(HTMaterialKeys.HELIUM)
            .add(HTForgeFluidContent())
        // 2nd Period
        registry.getOrCreate(HTMaterialKeys.NITROGEN)
            .add(HTForgeFluidContent())
        registry.getOrCreate(HTMaterialKeys.OXYGEN)
            .add(HTForgeFluidContent())
        registry.getOrCreate(HTMaterialKeys.FLUORINE)
            .add(HTForgeFluidContent())
        // 3rd Period
        registry.getOrCreate(HTMaterialKeys.ALUMINUM)
            .add(HTStorageBlockContent(toolTag = BlockTags.PICKAXE_MINEABLE, toolLevel = 1))
        registry.getOrCreate(HTMaterialKeys.CHLORINE)
            .add(HTForgeFluidContent())
        // 4th Period
        registry.getOrCreate(HTMaterialKeys.TITANIUM)
            .add(HTStorageBlockContent(toolTag = BlockTags.PICKAXE_MINEABLE, toolLevel = 3))
        registry.getOrCreate(HTMaterialKeys.NICKEL)
            .add(HTStorageBlockContent(toolTag = BlockTags.PICKAXE_MINEABLE, toolLevel = 2))
        registry.getOrCreate(HTMaterialKeys.COPPER)
            .add(HTStorageBlockContent(toolTag = BlockTags.PICKAXE_MINEABLE, toolLevel = 1))
        registry.getOrCreate(HTMaterialKeys.ZINC)
            .add(HTStorageBlockContent(toolTag = BlockTags.PICKAXE_MINEABLE, toolLevel = 1))
        // 5th Period
        registry.getOrCreate(HTMaterialKeys.SILVER)
            .add(HTStorageBlockContent(toolTag = BlockTags.PICKAXE_MINEABLE, toolLevel = 2))
        registry.getOrCreate(HTMaterialKeys.TIN)
            .add(HTStorageBlockContent(toolTag = BlockTags.PICKAXE_MINEABLE, toolLevel = 1))
        // 6th Period
        registry.getOrCreate(HTMaterialKeys.TUNGSTEN)
            .add(HTStorageBlockContent(toolTag = BlockTags.PICKAXE_MINEABLE, toolLevel = 3))
        registry.getOrCreate(HTMaterialKeys.IRIDIUM)
            .add(HTStorageBlockContent(toolTag = BlockTags.PICKAXE_MINEABLE, toolLevel = 3))
        registry.getOrCreate(HTMaterialKeys.PLATINUM)
            .add(HTStorageBlockContent(toolTag = BlockTags.PICKAXE_MINEABLE, toolLevel = 3))
        registry.getOrCreate(HTMaterialKeys.MERCURY)
            .add(HTForgeFluidContent())
        registry.getOrCreate(HTMaterialKeys.LEAD)
            .add(HTStorageBlockContent(toolTag = BlockTags.PICKAXE_MINEABLE, toolLevel = 1))
        // 7th Period
        registry.getOrCreate(HTMaterialKeys.URANIUM)
            .add(HTStorageBlockContent(toolTag = BlockTags.PICKAXE_MINEABLE, toolLevel = 2))
        registry.getOrCreate(HTMaterialKeys.PLUTONIUM)
            .add(HTStorageBlockContent(toolTag = BlockTags.PICKAXE_MINEABLE, toolLevel = 2))
        // Common - Gems
        registry.getOrCreate(HTMaterialKeys.CINNABAR)
            .add(HTStorageBlockContent(toolTag = BlockTags.PICKAXE_MINEABLE, toolLevel = 1))
        registry.getOrCreate(HTMaterialKeys.COKE)
            .add(HTStorageBlockContent(toolTag = BlockTags.PICKAXE_MINEABLE))
        registry.getOrCreate(HTMaterialKeys.OLIVINE)
            .add(HTStorageBlockContent(toolTag = BlockTags.PICKAXE_MINEABLE, toolLevel = 2))
        registry.getOrCreate(HTMaterialKeys.PERIDOT)
            .add(HTStorageBlockContent(toolTag = BlockTags.PICKAXE_MINEABLE, toolLevel = 2))
        registry.getOrCreate(HTMaterialKeys.RUBY)
            .add(HTStorageBlockContent(toolTag = BlockTags.PICKAXE_MINEABLE, toolLevel = 3))
        registry.getOrCreate(HTMaterialKeys.SALT)
            .add(HTStorageBlockContent(toolTag = BlockTags.PICKAXE_MINEABLE, toolLevel = 1))
        registry.getOrCreate(HTMaterialKeys.SAPPHIRE)
            .add(HTStorageBlockContent(toolTag = BlockTags.PICKAXE_MINEABLE, toolLevel = 3))
        // Common - Metals
        registry.getOrCreate(HTMaterialKeys.BRASS)
            .add(HTStorageBlockContent(toolTag = BlockTags.PICKAXE_MINEABLE, toolLevel = 1))
        registry.getOrCreate(HTMaterialKeys.BRONZE)
            .add(HTStorageBlockContent(toolTag = BlockTags.PICKAXE_MINEABLE, toolLevel = 1))
        registry.getOrCreate(HTMaterialKeys.ELECTRUM)
            .add(HTStorageBlockContent(toolTag = BlockTags.PICKAXE_MINEABLE, toolLevel = 2))
        registry.getOrCreate(HTMaterialKeys.INVAR)
            .add(HTStorageBlockContent(toolTag = BlockTags.PICKAXE_MINEABLE, toolLevel = 2))
        registry.getOrCreate(HTMaterialKeys.STAINLESS_STEEL)
            .add(HTStorageBlockContent(toolTag = BlockTags.PICKAXE_MINEABLE, toolLevel = 2))
        registry.getOrCreate(HTMaterialKeys.STEEl)
            .add(HTStorageBlockContent(toolTag = BlockTags.PICKAXE_MINEABLE, toolLevel = 2))
        // Common - Solids
        registry.getOrCreate(HTMaterialKeys.RUBBER)
            .add(HTStorageBlockContent())
    }
}
