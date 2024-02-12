package io.github.hiiragi283.fabric.compat

import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.material.HTMaterialKeys
import io.github.hiiragi283.api.material.HTMaterialsInit
import io.github.hiiragi283.api.material.content.HTMaterialContentMap
import io.github.hiiragi283.api.material.content.HTStorageBlockContent
import io.github.hiiragi283.api.util.collection.DefaultedMap
import io.github.hiiragi283.fabric.content.HTSimpleFluidContent
import net.minecraft.tag.BlockTags

object HTMaterialsInitFabric : HTMaterialsInit() {
    override fun modifyMaterialContent(registry: DefaultedMap<HTMaterialKey, HTMaterialContentMap>) {
        super.modifyMaterialContent(registry)
        // 1st Period
        registry.getOrCreate(HTMaterialKeys.HYDROGEN)
            .add(HTSimpleFluidContent())
        registry.getOrCreate(HTMaterialKeys.HELIUM)
            .add(HTSimpleFluidContent())
        // 2nd Period
        registry.getOrCreate(HTMaterialKeys.NITROGEN)
            .add(HTSimpleFluidContent())
        registry.getOrCreate(HTMaterialKeys.OXYGEN)
            .add(HTSimpleFluidContent())
        registry.getOrCreate(HTMaterialKeys.FLUORINE)
            .add(HTSimpleFluidContent())
        // 3rd Period
        registry.getOrCreate(HTMaterialKeys.ALUMINUM)
            .add(HTStorageBlockContent(toolTag = BlockTags.PICKAXE_MINEABLE, toolLevel = 1))
        registry.getOrCreate(HTMaterialKeys.CHLORINE)
            .add(HTSimpleFluidContent())
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
            .add(HTSimpleFluidContent())
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
