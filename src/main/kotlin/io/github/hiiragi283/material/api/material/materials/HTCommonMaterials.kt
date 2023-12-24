package io.github.hiiragi283.material.api.material.materials

import com.google.common.collect.Multimap
import io.github.hiiragi283.material.HTMaterialsCommon
import io.github.hiiragi283.material.api.addon.HTMaterialsAddon
import io.github.hiiragi283.material.api.material.ColorConvertible
import io.github.hiiragi283.material.api.material.FormulaConvertible
import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.material.MolarMassConvertible
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlag
import io.github.hiiragi283.material.api.material.property.HTGemProperty
import io.github.hiiragi283.material.api.material.property.HTMaterialProperties
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

    override fun modifyMaterialProperty(registry: HTDefaultedMap<HTMaterialKey, HTMaterialProperties.Builder>) {
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

    override fun modifyMaterialFlag(registry: Multimap<HTMaterialKey, HTMaterialFlag>) {
        //Fluids
        //Gems
        registry.get(CINNABAR).apply {
            add(HTMaterialFlag.GENERATE_BLOCk)
            add(HTMaterialFlag.GENERATE_DUST)
            add(HTMaterialFlag.GENERATE_GEM)
        }
        registry.get(COKE).apply {
            add(HTMaterialFlag.GENERATE_BLOCk)
            add(HTMaterialFlag.GENERATE_DUST)
            add(HTMaterialFlag.GENERATE_GEM)
        }
        registry.get(OLIVINE).apply {
            add(HTMaterialFlag.GENERATE_BLOCk)
            add(HTMaterialFlag.GENERATE_DUST)
            add(HTMaterialFlag.GENERATE_GEM)
        }
        registry.get(PERIDOT).apply {
            add(HTMaterialFlag.GENERATE_BLOCk)
            add(HTMaterialFlag.GENERATE_DUST)
            add(HTMaterialFlag.GENERATE_GEM)
            add(HTMaterialFlag.GENERATE_PLATE)
            add(HTMaterialFlag.GENERATE_ROD)
        }
        registry.get(RUBY).apply {
            add(HTMaterialFlag.GENERATE_BLOCk)
            add(HTMaterialFlag.GENERATE_DUST)
            add(HTMaterialFlag.GENERATE_GEM)
            add(HTMaterialFlag.GENERATE_PLATE)
            add(HTMaterialFlag.GENERATE_ROD)
        }
        registry.get(SALT).apply {
            add(HTMaterialFlag.GENERATE_BLOCk)
            add(HTMaterialFlag.GENERATE_DUST)
            add(HTMaterialFlag.GENERATE_GEM)
            add(HTMaterialFlag.GENERATE_PLATE)
            add(HTMaterialFlag.GENERATE_ROD)
        }

        registry.get(SAPPHIRE).apply {
            add(HTMaterialFlag.GENERATE_BLOCk)
            add(HTMaterialFlag.GENERATE_DUST)
            add(HTMaterialFlag.GENERATE_GEM)
            add(HTMaterialFlag.GENERATE_PLATE)
            add(HTMaterialFlag.GENERATE_ROD)
        }
        //Metals
        registry.get(BRASS).apply {
            add(HTMaterialFlag.GENERATE_BLOCk)
            add(HTMaterialFlag.GENERATE_DUST)
            add(HTMaterialFlag.GENERATE_GEAR)
            add(HTMaterialFlag.GENERATE_INGOT)
            add(HTMaterialFlag.GENERATE_NUGGET)
            add(HTMaterialFlag.GENERATE_PLATE)
            add(HTMaterialFlag.GENERATE_ROD)
        }
        registry.get(BRONZE).apply {
            add(HTMaterialFlag.GENERATE_BLOCk)
            add(HTMaterialFlag.GENERATE_DUST)
            add(HTMaterialFlag.GENERATE_GEAR)
            add(HTMaterialFlag.GENERATE_INGOT)
            add(HTMaterialFlag.GENERATE_NUGGET)
            add(HTMaterialFlag.GENERATE_PLATE)
            add(HTMaterialFlag.GENERATE_ROD)
        }
        registry.get(ELECTRUM).apply {
            add(HTMaterialFlag.GENERATE_BLOCk)
            add(HTMaterialFlag.GENERATE_DUST)
            add(HTMaterialFlag.GENERATE_GEAR)
            add(HTMaterialFlag.GENERATE_INGOT)
            add(HTMaterialFlag.GENERATE_NUGGET)
            add(HTMaterialFlag.GENERATE_PLATE)
            add(HTMaterialFlag.GENERATE_ROD)
        }
        registry.get(INVAR).apply {
            add(HTMaterialFlag.GENERATE_BLOCk)
            add(HTMaterialFlag.GENERATE_DUST)
            add(HTMaterialFlag.GENERATE_GEAR)
            add(HTMaterialFlag.GENERATE_INGOT)
            add(HTMaterialFlag.GENERATE_NUGGET)
            add(HTMaterialFlag.GENERATE_PLATE)
            add(HTMaterialFlag.GENERATE_ROD)
        }
        registry.get(STAINLESS_STEEL).apply {
            add(HTMaterialFlag.GENERATE_BLOCk)
            add(HTMaterialFlag.GENERATE_DUST)
            add(HTMaterialFlag.GENERATE_GEAR)
            add(HTMaterialFlag.GENERATE_INGOT)
            add(HTMaterialFlag.GENERATE_NUGGET)
            add(HTMaterialFlag.GENERATE_PLATE)
            add(HTMaterialFlag.GENERATE_ROD)
        }
        registry.get(STEEl).apply {
            add(HTMaterialFlag.GENERATE_BLOCk)
            add(HTMaterialFlag.GENERATE_DUST)
            add(HTMaterialFlag.GENERATE_GEAR)
            add(HTMaterialFlag.GENERATE_INGOT)
            add(HTMaterialFlag.GENERATE_NUGGET)
            add(HTMaterialFlag.GENERATE_PLATE)
            add(HTMaterialFlag.GENERATE_ROD)
        }
        //Solids
        registry.get(ASHES).apply {
            add(HTMaterialFlag.GENERATE_DUST)
        }
        registry.get(BAUXITE).apply {
            add(HTMaterialFlag.GENERATE_DUST)
        }
        registry.get(RUBBER).apply {
            add(HTMaterialFlag.GENERATE_BLOCk)
            add(HTMaterialFlag.GENERATE_DUST)
            add(HTMaterialFlag.GENERATE_INGOT)
            add(HTMaterialFlag.GENERATE_PLATE)
            add(HTMaterialFlag.GENERATE_ROD)
        }
        //Stones
        registry.get(MARBLE).apply {
            add(HTMaterialFlag.GENERATE_DUST)
        }
        //Woods
    }

    override fun modifyMaterialColor(registry: HashMap<HTMaterialKey, ColorConvertible>) {
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
        registry[BRONZE] = ColorConvertible.of(registry, HTElementMaterials.COPPER to 3, HTElementMaterials.TIN to 1)
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

    override fun modifyMaterialFormula(registry: HashMap<HTMaterialKey, FormulaConvertible>) {
        //Fluids
        //Gems
        registry[CINNABAR]
        registry[COKE] = registry[HTElementMaterials.CARBON]!!
        registry[OLIVINE]
        registry[PERIDOT]
        registry[RUBY] = FormulaConvertible.of(registry, *HTAtomicGroups.ALUMINUM_OXIDE)
        registry[SALT] = FormulaConvertible.of(
            registry[HTElementMaterials.SODIUM]!! to 1,
            registry[HTElementMaterials.CHLORINE]!! to 1
        )
        registry[SAPPHIRE] = FormulaConvertible.of(registry, *HTAtomicGroups.ALUMINUM_OXIDE)
        //Metals
        registry[BRASS] = FormulaConvertible.of(
            registry,
            HTElementMaterials.COPPER to 3,
            HTElementMaterials.ZINC to 1
        )
        registry[BRONZE] = FormulaConvertible.of(
            registry,
            HTElementMaterials.COPPER to 3,
            HTElementMaterials.TIN to 1
        )
        registry[ELECTRUM] = FormulaConvertible.of(
            registry,
            HTElementMaterials.SILVER to 1,
            HTElementMaterials.GOLD to 1
        )
        registry[INVAR] = FormulaConvertible.of(
            registry,
            HTElementMaterials.IRON to 1,
            HTElementMaterials.NICKEL to 2
        )
        registry[STAINLESS_STEEL] = FormulaConvertible.of(
            registry,
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
        registry[MARBLE] = FormulaConvertible.of(
            registry,
            HTElementMaterials.CARBON to 1,
            *HTAtomicGroups.CARBONATE
        )
        //Woods
    }

    override fun modifyMaterialMolar(registry: HashMap<HTMaterialKey, MolarMassConvertible>) {
        //Fluids
        //Gems
        //Metals
        //Solids
        //Stones
        //Woods
    }

}