package io.github.hiiragi283.material.compat

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.HTMaterialsAddon
import io.github.hiiragi283.api.extension.HTColor
import io.github.hiiragi283.api.extension.averageColor
import io.github.hiiragi283.api.material.HTMaterialKeys
import io.github.hiiragi283.api.material.HTMaterialRegistry
import io.github.hiiragi283.api.material.composition.HTMaterialComposition
import io.github.hiiragi283.api.material.element.HTElements
import io.github.hiiragi283.api.shape.HTShapeRegistry
import io.github.hiiragi283.api.shape.HTShapes
import java.awt.Color

internal object HTMaterialsInit : HTMaterialsAddon {
    override val modId: String = HTMaterialsAPI.MOD_ID
    override val priority: Int = -100

    override fun modifyShapeRegistry(builder: HTShapeRegistry.Builder) {
        // Block
        builder.add(HTShapes.BLOCK)
        builder.add(HTShapes.BRICKS)
        builder.add(HTShapes.LOG)
        builder.add(HTShapes.ORE)
        builder.add(HTShapes.PLANKS)
        // Item
        builder.add(HTShapes.DUST)
        builder.add(HTShapes.GEAR)
        builder.add(HTShapes.GEM)
        builder.add(HTShapes.INGOT)
        builder.add(HTShapes.NUGGET)
        builder.add(HTShapes.PLATE)
        builder.add(HTShapes.ROD)
    }

    override fun modifyMaterialRegistry(builder: HTMaterialRegistry.Builder) {
        // 1st Period
        builder.setSimple(HTMaterialKeys.HYDROGEN, HTElements.H to 2)
        builder.setSimple(HTMaterialKeys.HELIUM, HTElements.He to 1)
        // 2nd Period
        builder.setMetal(HTMaterialKeys.LITHIUM, HTElements.Li)
        builder.setMetal(HTMaterialKeys.BERYLLIUM, HTElements.Be)
        builder.setSimple(HTMaterialKeys.CARBON, HTElements.C to 1)
        builder.setSimple(HTMaterialKeys.NITROGEN, HTElements.N to 2)
        builder.setSimple(HTMaterialKeys.OXYGEN, HTElements.O to 2)
        builder.setSimple(HTMaterialKeys.FLUORINE, HTElements.F to 2)
        // 3rd Period
        builder.setMetal(HTMaterialKeys.SODIUM, HTElements.Na)
        builder.setMetal(HTMaterialKeys.MAGNESIUM, HTElements.Mg)
        builder.setMetal(HTMaterialKeys.ALUMINUM, HTElements.Al)
        // builder.addAlternativeName(HTMaterialKeys.ALUMINUM, "aluminium")
        builder.setMetal(HTMaterialKeys.SILICON, HTElements.Si)
        builder.setSimple(HTMaterialKeys.PHOSPHORUS, HTElements.P to 1)
        builder.setSimple(HTMaterialKeys.SULFUR, HTElements.S to 8)
        builder.setSimple(HTMaterialKeys.CHLORINE, HTElements.Cl to 2)
        // 4th Period
        builder.setMetal(HTMaterialKeys.POTASSIUM, HTElements.K)
        builder.setMetal(HTMaterialKeys.CALCIUM, HTElements.Ca)
        builder.setMetal(HTMaterialKeys.TITANIUM, HTElements.Ti)
        builder.setMetal(HTMaterialKeys.CHROMIUM, HTElements.Cr)
        // builder.addAlternativeName(HTMaterialKeys.CHROMIUM, "chrome")
        builder.setMetal(HTMaterialKeys.MANGANESE, HTElements.Mn)
        builder.setMetal(HTMaterialKeys.IRON, HTElements.Fe)
        builder.setMetal(HTMaterialKeys.COBALT, HTElements.Co)
        builder.setMetal(HTMaterialKeys.NICKEL, HTElements.Ni)
        builder.setMetal(HTMaterialKeys.COPPER, HTElements.Cu)
        builder.setMetal(HTMaterialKeys.ZINC, HTElements.Zn)
        // 5th Period
        builder.setMetal(HTMaterialKeys.SILVER, HTElements.Ag)
        builder.setMetal(HTMaterialKeys.TIN, HTElements.Sn)
        // 6th Period
        builder.setMetal(HTMaterialKeys.TUNGSTEN, HTElements.W)
        builder.setMetal(HTMaterialKeys.IRIDIUM, HTElements.Ir)
        builder.setMetal(HTMaterialKeys.PLATINUM, HTElements.Pt)
        builder.setMetal(HTMaterialKeys.GOLD, HTElements.Au)
        builder.setMetal(HTMaterialKeys.MERCURY, HTElements.Hg)
        builder.setMetal(HTMaterialKeys.LEAD, HTElements.Pb)
        // 7th Period
        builder.setMetal(HTMaterialKeys.URANIUM, HTElements.U)
        builder.setMetal(HTMaterialKeys.PLUTONIUM, HTElements.Pu)
        // Vanilla - Fluids
        builder.setSimple(
            HTMaterialKeys.WATER,
            HTMaterialComposition.molecular(HTElements.H to 2, HTElements.O to 1) {
                color = HTColor.BLUE
            },
        )
        builder.setSimple(
            HTMaterialKeys.LAVA,
            HTMaterialComposition.molecular(HTElements.SiO2 to 1) {
                color = averageColor(HTColor.DARK_RED, HTColor.GOLD)
            },
        )
        // Vanilla - Gems
        builder.setGem(
            HTMaterialKeys.AMETHYST,
            HTMaterialComposition.molecular(HTElements.SiO2 to 1) {
                color = averageColor(HTColor.BLUE, HTColor.LIGHT_PURPLE)
            },
        )
        builder.setGem(
            HTMaterialKeys.DIAMOND,
            HTMaterialComposition.molecular(HTElements.C to 1) {
                color = HTColor.AQUA
            },
        )
        builder.setGem(
            HTMaterialKeys.ENDER_PEARL,
            HTMaterialComposition.molecular {
                color = averageColor(HTColor.DARK_GREEN, HTColor.BLUE)
            },
        )
        builder.setGem(
            HTMaterialKeys.EMERALD,
            HTMaterialComposition.molecular(
                HTElements.Be to 3,
                HTElements.Al to 2,
                HTElements.Si to 6,
                HTElements.O to 18,
            ) { color = HTColor.GREEN },
        )
        builder.setGem(
            HTMaterialKeys.FLINT,
            HTMaterialComposition.molecular(HTElements.SiO2 to 1) {
                color = averageColor(HTColor.BLACK to 3, HTColor.BLUE to 1)
            },
        )
        builder.setGem(
            HTMaterialKeys.LAPIS,
            HTMaterialComposition.molecular {
                color = HTColor.BLUE
            },
        )
        builder.setGem(
            HTMaterialKeys.QUARTZ,
            HTMaterialComposition.molecular(HTElements.SiO2 to 1),
        )
        // Vanilla - Metal
        builder.setMetal(HTMaterialKeys.NETHERITE, HTElements.Nr)
        // Vanilla - Solids
        builder.setSimple(
            HTMaterialKeys.BRICK,
            HTMaterialComposition.molecular {
                color = averageColor(HTColor.DARK_RED to 2, HTColor.GOLD to 1, HTColor.DARK_GRAY to 2)
            },
        )
        builder.setSimple(
            HTMaterialKeys.CHARCOAL,
            HTMaterialComposition.molecular(HTElements.C to 1) {
                color = averageColor(HTColor.BLACK to 7, HTColor.YELLOW to 1)
            },
        )

        builder.setSimple(
            HTMaterialKeys.CLAY,
            HTMaterialComposition.molecular {
                color = Color(0xa4a8b8)
            },
        )
        builder.setSimple(HTMaterialKeys.COAL, HTElements.C to 1)
        builder.setSimple(HTMaterialKeys.GLASS, HTElements.SiO2 to 1)
        builder.setSimple(
            HTMaterialKeys.GLOWSTONE,
            HTMaterialComposition.molecular {
                color = averageColor(HTColor.GOLD to 1, HTColor.YELLOW to 2)
            },
        )
        builder.setSimple(
            HTMaterialKeys.NETHER_BRICK,
            HTMaterialComposition.molecular {
                color = averageColor(HTColor.BLACK to 4, HTColor.DARK_RED to 1, HTColor.WHITE to 1)
            },
        )
        builder.setSimple(
            HTMaterialKeys.REDSTONE,
            HTMaterialComposition.molecular { color = HTColor.DARK_RED },
        )
        // Vanilla - Stones
        builder.setStone(
            HTMaterialKeys.STONE,
            HTMaterialComposition.molecular(HTElements.SiO2 to 1) {
                color = HTColor.DARK_GRAY
            },
        )
        builder.setStone(
            HTMaterialKeys.GRANITE,
            HTMaterialComposition.molecular(HTElements.SiO2 to 1) {
                color = averageColor(HTColor.DARK_RED to 1, HTColor.GRAY to 4, HTColor.RED to 1)
            },
        )
        builder.setStone(
            HTMaterialKeys.DIORITE,
            HTMaterialComposition.molecular(HTElements.SiO2 to 1) {
                color = HTColor.GRAY
            },
        )
        builder.setStone(
            HTMaterialKeys.ANDESITE,
            HTMaterialComposition.molecular(HTElements.SiO2 to 1) {
                color = averageColor(HTColor.DARK_GRAY to 7, HTColor.YELLOW to 1)
            },
        )
        builder.setStone(
            HTMaterialKeys.DEEPSLATE,
            HTMaterialComposition.molecular(HTElements.SiO2 to 1) {
                color = averageColor(HTColor.BLACK, HTColor.DARK_GRAY)
            },
        )
        builder.setStone(
            HTMaterialKeys.CALCITE,
            HTMaterialComposition.molecular(HTElements.Ca to 1, HTElements.CO3 to 1),
        )
        builder.setStone(
            HTMaterialKeys.TUFF,
            HTMaterialComposition.molecular(HTElements.SiO2 to 1) {
                color = Color(0x4d5d53)
            },
        )
        builder.setStone(
            HTMaterialKeys.OBSIDIAN,
            HTMaterialComposition.molecular(HTElements.SiO2 to 1) {
                color = averageColor(
                    HTColor.BLACK to 4,
                    HTColor.DARK_BLUE to 2,
                    HTColor.DARK_RED to 1,
                    HTColor.WHITE to 1,
                )
            },
        )
        builder.setStone(
            HTMaterialKeys.NETHERRACK,
            HTMaterialComposition.molecular(HTElements.SiO2 to 1) {
                color = averageColor(HTColor.BLACK to 4, HTColor.DARK_RED to 1, HTColor.RED to 3)
            },
        )
        builder.setStone(
            HTMaterialKeys.BASALT,
            HTMaterialComposition.molecular(HTElements.SiO2 to 1) {
                color = averageColor(HTColor.BLACK, HTColor.GRAY)
            },
        )
        builder.setStone(
            HTMaterialKeys.END_STONE,
            HTMaterialComposition.molecular(HTElements.SiO2 to 1) {
                color = averageColor(HTColor.YELLOW to 1, HTColor.WHITE to 3)
            },
        )
        builder.setStone(
            HTMaterialKeys.BLACKSTONE,
            HTMaterialComposition.molecular(HTElements.SiO2 to 1) {
                color = HTColor.BLACK
            },
        )
        // Vanilla - Woods
        builder.setWood(
            HTMaterialKeys.WOOD,
            HTMaterialComposition.mixture(HTElements.C, HTElements.H, HTElements.O) {
                color = averageColor(HTColor.DARK_GRAY to 2, HTColor.RED to 1, HTColor.YELLOW to 1)
                formula = "(C, H, O)"
            },
        )
        // Common - Fluids
        // Common - Gems
        builder.setGem(
            HTMaterialKeys.CINNABAR,
            HTMaterialComposition.molecular(HTElements.Hg to 1, HTElements.S to 1) {
                color = HTColor.RED
            },
        )
        builder.setGem(
            HTMaterialKeys.COKE,
            HTMaterialComposition.molecular(HTElements.C to 1) {
                color = HTColor.DARK_GRAY
            },
        )
        builder.setGem(
            HTMaterialKeys.OLIVINE,
            HTMaterialComposition.molecular {
                color = averageColor(HTColor.DARK_GREEN, HTColor.GREEN)
            },
        )
        builder.setGem(
            HTMaterialKeys.PERIDOT,
            HTMaterialComposition.molecular {
                color = averageColor(HTColor.GREEN, HTColor.WHITE)
            },
        )
        builder.setGem(
            HTMaterialKeys.RUBY,
            HTMaterialComposition.molecular(HTElements.Al2O3 to 1) {
                color = HTColor.RED
            },
        )
        builder.setGem(
            HTMaterialKeys.SALT,
            HTMaterialComposition.molecular(HTElements.Na to 1, HTElements.Cl to 1) {
                color = HTColor.WHITE
            },
        )
        builder.setGem(
            HTMaterialKeys.SAPPHIRE,
            HTMaterialComposition.molecular(HTElements.Al2O3 to 1) {
                color = HTColor.BLUE
            },
        )
        // Common - Metals
        builder.setMetal(
            HTMaterialKeys.BRASS,
            HTMaterialComposition.molecular(HTElements.Cu to 3, HTElements.Zn to 1) {
                color = HTColor.GOLD
            },
        )
        builder.setMetal(
            HTMaterialKeys.BRONZE,
            HTMaterialComposition.molecular(HTElements.Cu to 3, HTElements.Sn to 1),
        )
        builder.setMetal(
            HTMaterialKeys.ELECTRUM,
            HTMaterialComposition.molecular(HTElements.Ag to 1, HTElements.Au to 1) {
                color = averageColor(HTColor.GOLD, HTColor.YELLOW, HTColor.WHITE)
            },
        )
        builder.setMetal(
            HTMaterialKeys.INVAR,
            HTMaterialComposition.molecular(HTElements.Fe to 2, HTElements.Ni to 1) {
                color = averageColor(HTColor.GREEN to 1, HTColor.GRAY to 3, HTColor.WHITE to 4)
            },
        )
        builder.setMetal(
            HTMaterialKeys.STAINLESS_STEEL,
            HTMaterialComposition.molecular(
                HTElements.Fe to 6,
                HTElements.Cr to 1,
                HTElements.Mn to 1,
                HTElements.Ni to 1,
            ) { color = averageColor(HTColor.GRAY, HTColor.WHITE) },
        )
        builder.setMetal(
            HTMaterialKeys.STEEl,
            HTMaterialComposition.mixture(HTElements.Fe, HTElements.C) {
                color = HTColor.DARK_GRAY
                formula = "(Fe, C)"
            },
        )
        // Common - Solids
        builder.setSimple(
            HTMaterialKeys.ASHES,
            HTMaterialComposition.mixture { color = HTColor.DARK_GRAY },
        )
        builder.setSimple(
            HTMaterialKeys.BAUXITE,
            HTMaterialComposition.hydrate(
                HTMaterialComposition.molecular(HTElements.Al2O3 to 1),
                2,
            ) { color = averageColor(HTColor.BLACK to 1, HTColor.DARK_RED to 2, HTColor.GOLD to 1) },
        )
        builder.setSimple(
            HTMaterialKeys.RUBBER,
            HTMaterialComposition.polymer(HTElements.C to 5, HTElements.H to 6) {
                color = averageColor(HTColor.BLACK, HTColor.DARK_GRAY)
                formula = "CC(=C)C=C"
            },
        )
        // Common - Stones
        builder.setStone(
            HTMaterialKeys.MARBLE,
            HTMaterialComposition.molecular(HTElements.Ca to 1, HTElements.CO3 to 1),
        )
        // Common - Woods
    }
}
