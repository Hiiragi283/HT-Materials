package io.github.hiiragi283.material.api.material.materials

import io.github.hiiragi283.material.HTMaterialsCommon
import io.github.hiiragi283.material.api.HTMaterialsAddon
import io.github.hiiragi283.material.api.material.ColorConvertible
import io.github.hiiragi283.material.api.material.FormulaConvertible
import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.material.MolarMassConvertible
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlag
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlagSet
import io.github.hiiragi283.material.api.material.property.HTGemProperty
import io.github.hiiragi283.material.api.material.property.HTMaterialPropertyMap
import io.github.hiiragi283.material.api.material.property.HTMetalProperty
import io.github.hiiragi283.material.api.material.property.HTStoneProperty
import io.github.hiiragi283.material.api.registry.HTDefaultedMap
import io.github.hiiragi283.material.api.registry.HTObjectKeySet
import io.github.hiiragi283.material.util.HTColor

object HTCommonMaterials : HTMaterialsAddon {

    //    Fluids    //

    //    Gems    //

    @JvmField
    val CINNABAR = HTMaterialKey("cinnabar")

    @JvmField
    val COKE = HTMaterialKey("coke")

    @JvmField
    val OLIVINE = HTMaterialKey("olivine")

    @JvmField
    val PERIDOT = HTMaterialKey("peridot")

    @JvmField
    val RUBY = HTMaterialKey("ruby")

    @JvmField
    val SALT = HTMaterialKey("salt")

    @JvmField
    val SAPPHIRE = HTMaterialKey("sapphire")

    //    Metal    //

    @JvmField
    val BRASS = HTMaterialKey("brass")

    @JvmField
    val BRONZE = HTMaterialKey("bronze")

    @JvmField
    val ELECTRUM = HTMaterialKey("electrum")

    @JvmField
    val INVAR = HTMaterialKey("invar")

    @JvmField
    val STAINLESS_STEEL = HTMaterialKey("stainless_steel")

    @JvmField
    val STEEl = HTMaterialKey("steel")

    //    Solids    //

    @JvmField
    val ASHES = HTMaterialKey("ashes")

    @JvmField
    val BAUXITE = HTMaterialKey("bauxite")

    @JvmField
    val RUBBER = HTMaterialKey("rubber")

    //    Stones    //

    @JvmField
    val MARBLE = HTMaterialKey("marble")

    //    Woods    //

    //    Register    //

    override val modId: String = HTMaterialsCommon.MOD_ID

    override val priority: Int = -90

    override fun registerMaterialKey(registry: HTObjectKeySet<HTMaterialKey>) {
        //Fluids
        //Gems
        registry.addAll(
            CINNABAR,
            COKE,
            OLIVINE,
            PERIDOT,
            RUBY,
            SALT,
            SAPPHIRE
        )
        //Metals
        registry.addAll(
            BRASS,
            BRONZE,
            ELECTRUM,
            INVAR,
            STAINLESS_STEEL
        )
        //Solids
        registry.addAll(
            ASHES,
            BAUXITE,
            RUBBER
        )
        //Stones
        registry.addAll(
            MARBLE
        )
        //Woods
    }

    override fun modifyMaterialProperty(registry: HTDefaultedMap<HTMaterialKey, HTMaterialPropertyMap.Builder>) {
        //Fluids
        //Gems
        registry.getOrCreate(CINNABAR).add(HTGemProperty.EMERALD)
        registry.getOrCreate(COKE).add(HTGemProperty.COAL)
        registry.getOrCreate(OLIVINE).add(HTGemProperty.EMERALD)
        registry.getOrCreate(PERIDOT).add(HTGemProperty.RUBY)
        registry.getOrCreate(RUBY).add(HTGemProperty.RUBY)
        registry.getOrCreate(SALT).add(HTGemProperty.CUBIC)
        registry.getOrCreate(SAPPHIRE).add(HTGemProperty.RUBY)
        //Metals
        registry.getOrCreate(BRASS).add(HTMetalProperty)
        registry.getOrCreate(BRONZE).add(HTMetalProperty)
        registry.getOrCreate(ELECTRUM).add(HTMetalProperty)
        registry.getOrCreate(INVAR).add(HTMetalProperty)
        registry.getOrCreate(STAINLESS_STEEL).add(HTMetalProperty)
        registry.getOrCreate(STEEl).add(HTMetalProperty)
        //Solids
        registry.getOrCreate(ASHES)
        registry.getOrCreate(BAUXITE)
        registry.getOrCreate(RUBBER)
        //Stones
        registry.getOrCreate(MARBLE).add(HTStoneProperty)
        //Woods
    }

    override fun modifyMaterialFlag(registry: HTDefaultedMap<HTMaterialKey, HTMaterialFlagSet.Builder>) {
        //Fluids
        //Gems
        registry.getOrCreate(CINNABAR).apply {
            add(HTMaterialFlag.GENERATE_BLOCk)
            add(HTMaterialFlag.GENERATE_DUST)
            add(HTMaterialFlag.GENERATE_GEM)
        }
        registry.getOrCreate(COKE).apply {
            add(HTMaterialFlag.GENERATE_BLOCk)
            add(HTMaterialFlag.GENERATE_DUST)
            add(HTMaterialFlag.GENERATE_GEM)
        }
        registry.getOrCreate(OLIVINE).apply {
            add(HTMaterialFlag.GENERATE_BLOCk)
            add(HTMaterialFlag.GENERATE_DUST)
            add(HTMaterialFlag.GENERATE_GEM)
        }
        registry.getOrCreate(PERIDOT).apply {
            add(HTMaterialFlag.GENERATE_BLOCk)
            add(HTMaterialFlag.GENERATE_DUST)
            add(HTMaterialFlag.GENERATE_GEM)
            add(HTMaterialFlag.GENERATE_PLATE)
            add(HTMaterialFlag.GENERATE_ROD)
        }
        registry.getOrCreate(RUBY).apply {
            add(HTMaterialFlag.GENERATE_BLOCk)
            add(HTMaterialFlag.GENERATE_DUST)
            add(HTMaterialFlag.GENERATE_GEM)
            add(HTMaterialFlag.GENERATE_PLATE)
            add(HTMaterialFlag.GENERATE_ROD)
        }
        registry.getOrCreate(SALT).apply {
            add(HTMaterialFlag.GENERATE_BLOCk)
            add(HTMaterialFlag.GENERATE_DUST)
            add(HTMaterialFlag.GENERATE_GEM)
            add(HTMaterialFlag.GENERATE_PLATE)
            add(HTMaterialFlag.GENERATE_ROD)
        }

        registry.getOrCreate(SAPPHIRE).apply {
            add(HTMaterialFlag.GENERATE_BLOCk)
            add(HTMaterialFlag.GENERATE_DUST)
            add(HTMaterialFlag.GENERATE_GEM)
            add(HTMaterialFlag.GENERATE_PLATE)
            add(HTMaterialFlag.GENERATE_ROD)
        }
        //Metals
        registry.getOrCreate(BRASS).apply {
            add(HTMaterialFlag.GENERATE_BLOCk)
            add(HTMaterialFlag.GENERATE_DUST)
            add(HTMaterialFlag.GENERATE_GEAR)
            add(HTMaterialFlag.GENERATE_INGOT)
            add(HTMaterialFlag.GENERATE_NUGGET)
            add(HTMaterialFlag.GENERATE_PLATE)
            add(HTMaterialFlag.GENERATE_ROD)
        }
        registry.getOrCreate(BRONZE).apply {
            add(HTMaterialFlag.GENERATE_BLOCk)
            add(HTMaterialFlag.GENERATE_DUST)
            add(HTMaterialFlag.GENERATE_GEAR)
            add(HTMaterialFlag.GENERATE_INGOT)
            add(HTMaterialFlag.GENERATE_NUGGET)
            add(HTMaterialFlag.GENERATE_PLATE)
            add(HTMaterialFlag.GENERATE_ROD)
        }
        registry.getOrCreate(ELECTRUM).apply {
            add(HTMaterialFlag.GENERATE_BLOCk)
            add(HTMaterialFlag.GENERATE_DUST)
            add(HTMaterialFlag.GENERATE_GEAR)
            add(HTMaterialFlag.GENERATE_INGOT)
            add(HTMaterialFlag.GENERATE_NUGGET)
            add(HTMaterialFlag.GENERATE_PLATE)
            add(HTMaterialFlag.GENERATE_ROD)
        }
        registry.getOrCreate(INVAR).apply {
            add(HTMaterialFlag.GENERATE_BLOCk)
            add(HTMaterialFlag.GENERATE_DUST)
            add(HTMaterialFlag.GENERATE_GEAR)
            add(HTMaterialFlag.GENERATE_INGOT)
            add(HTMaterialFlag.GENERATE_NUGGET)
            add(HTMaterialFlag.GENERATE_PLATE)
            add(HTMaterialFlag.GENERATE_ROD)
        }
        registry.getOrCreate(STAINLESS_STEEL).apply {
            add(HTMaterialFlag.GENERATE_BLOCk)
            add(HTMaterialFlag.GENERATE_DUST)
            add(HTMaterialFlag.GENERATE_GEAR)
            add(HTMaterialFlag.GENERATE_INGOT)
            add(HTMaterialFlag.GENERATE_NUGGET)
            add(HTMaterialFlag.GENERATE_PLATE)
            add(HTMaterialFlag.GENERATE_ROD)
        }
        registry.getOrCreate(STEEl).apply {
            add(HTMaterialFlag.GENERATE_BLOCk)
            add(HTMaterialFlag.GENERATE_DUST)
            add(HTMaterialFlag.GENERATE_GEAR)
            add(HTMaterialFlag.GENERATE_INGOT)
            add(HTMaterialFlag.GENERATE_NUGGET)
            add(HTMaterialFlag.GENERATE_PLATE)
            add(HTMaterialFlag.GENERATE_ROD)
        }
        //Solids
        registry.getOrCreate(ASHES).apply {
            add(HTMaterialFlag.GENERATE_DUST)
        }
        registry.getOrCreate(BAUXITE).apply {
            add(HTMaterialFlag.GENERATE_DUST)
        }
        registry.getOrCreate(RUBBER).apply {
            add(HTMaterialFlag.GENERATE_BLOCk)
            add(HTMaterialFlag.GENERATE_DUST)
            add(HTMaterialFlag.GENERATE_INGOT)
            add(HTMaterialFlag.GENERATE_PLATE)
            add(HTMaterialFlag.GENERATE_ROD)
        }
        //Stones
        registry.getOrCreate(MARBLE).apply {
            add(HTMaterialFlag.GENERATE_DUST)
        }
        //Woods
    }

    override fun modifyMaterialColor(registry: MutableMap<HTMaterialKey, ColorConvertible>) {
        //Fluids
        //Gems
        registry[CINNABAR] = ColorConvertible { HTColor.RED }
        registry[COKE] = ColorConvertible { HTColor.DARK_GRAY }
        registry[OLIVINE] = ColorConvertible.ofColor(HTColor.DARK_GREEN, HTColor.GREEN)
        registry[PERIDOT] = ColorConvertible.ofColor(HTColor.GREEN, HTColor.WHITE)
        registry[RUBY] = ColorConvertible { HTColor.RED }
        registry[SALT] = ColorConvertible { HTColor.WHITE }
        registry[SAPPHIRE] = ColorConvertible { HTColor.BLUE }
        //Metals
        registry[BRASS] = ColorConvertible { HTColor.GOLD }
        registry[BRONZE] = ColorConvertible.Child(HTElementMaterials.COPPER to 3, HTElementMaterials.TIN to 1)
        registry[ELECTRUM] = ColorConvertible.ofColor(HTColor.GOLD, HTColor.YELLOW, HTColor.WHITE)
        registry[INVAR] = ColorConvertible.ofColor(HTColor.GREEN to 1, HTColor.GRAY to 3, HTColor.WHITE to 4)
        registry[STAINLESS_STEEL] = ColorConvertible.ofColor(HTColor.GRAY, HTColor.WHITE)
        registry[STEEl] = ColorConvertible { HTColor.DARK_GRAY }
        //Solids
        registry[ASHES] = ColorConvertible { HTColor.DARK_GRAY }
        registry[BAUXITE] = ColorConvertible.ofColor(HTColor.BLACK to 1, HTColor.DARK_RED to 2, HTColor.GOLD to 1)
        registry[RUBBER] = ColorConvertible.ofColor(HTColor.BLACK, HTColor.DARK_GRAY)
        //Stones
        registry[MARBLE] = ColorConvertible { HTColor.WHITE }
        //Woods
    }

    override fun modifyMaterialFormula(registry: MutableMap<HTMaterialKey, FormulaConvertible>) {
        //Fluids
        //Gems
        registry[CINNABAR]
        registry[COKE] = registry[HTElementMaterials.CARBON]!!
        registry[OLIVINE]
        registry[PERIDOT]
        registry[RUBY] = FormulaConvertible.Child(
            *HTAtomicGroups.ALUMINUM_OXIDE
        )
        registry[SALT] = FormulaConvertible.Child(
            HTElementMaterials.SODIUM to 1,
            HTElementMaterials.CHLORINE to 1
        )
        registry[SAPPHIRE] = FormulaConvertible.Child(
            *HTAtomicGroups.ALUMINUM_OXIDE
        )
        //Metals
        registry[BRASS] = FormulaConvertible.Child(
            HTElementMaterials.COPPER to 3,
            HTElementMaterials.ZINC to 1
        )
        registry[BRONZE] = FormulaConvertible.Child(
            HTElementMaterials.COPPER to 3,
            HTElementMaterials.TIN to 1
        )
        registry[ELECTRUM] = FormulaConvertible.Child(
            HTElementMaterials.SILVER to 1,
            HTElementMaterials.GOLD to 1
        )
        registry[INVAR] = FormulaConvertible.Child(
            HTElementMaterials.IRON to 1,
            HTElementMaterials.NICKEL to 2
        )
        registry[STAINLESS_STEEL] = FormulaConvertible.Child(
            HTElementMaterials.IRON to 6,
            HTElementMaterials.CHROMIUM to 1,
            HTElementMaterials.MANGANESE to 1,
            HTElementMaterials.NICKEL to 1
        )
        registry[STEEl] = FormulaConvertible { "Fe, C" }
        //Solids
        registry[ASHES]
        registry[BAUXITE]
        registry[RUBBER]
        //Stones
        registry[MARBLE] = FormulaConvertible.Child(
            HTElementMaterials.CARBON to 1,
            *HTAtomicGroups.CARBONATE
        )
        //Woods
    }

    override fun modifyMaterialMolar(registry: MutableMap<HTMaterialKey, MolarMassConvertible>) {
        //Fluids
        //Gems
        //Metals
        //Solids
        //Stones
        //Woods
    }

}