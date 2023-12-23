package io.github.hiiragi283.material.api.material.materials

import io.github.hiiragi283.material.HTMaterialsCommon
import io.github.hiiragi283.material.api.addon.HTMaterialsAddon
import io.github.hiiragi283.material.api.material.*
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlag
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlagsNew
import io.github.hiiragi283.material.api.material.property.HTFluidProperty
import io.github.hiiragi283.material.api.material.property.HTMaterialPropertiesNew
import io.github.hiiragi283.material.api.material.property.HTMetalProperty
import io.github.hiiragi283.material.api.registry.HTDefaultedMap
import io.github.hiiragi283.material.util.HTColor

object HTElementMaterialsNew : HTMaterialsAddon {

    //    1st Period    //

    @JvmField
    val HYDROGEN = HTMaterialKey("hydrogen")

    @JvmField
    val HELIUM = HTMaterialKey("helium")

    //    2nd Period    //

    @JvmField
    val LITHIUM = HTMaterialKey("lithium")

    @JvmField
    val BERYLLIUM = HTMaterialKey("beryllium")

    @JvmField
    val CARBON = HTMaterialKey("carbon")

    @JvmField
    val NITROGEN = HTMaterialKey("nitrogen")

    @JvmField
    val OXYGEN = HTMaterialKey("oxygen")

    @JvmField
    val FLUORINE = HTMaterialKey("fluorine")

    //    3rd Period    //

    @JvmField
    val SODIUM = HTMaterialKey("sodium")

    @JvmField
    val MAGNESIUM = HTMaterialKey("magnesium")

    @JvmField
    val ALUMINUM = HTMaterialKey("aluminum")

    @JvmField
    val SILICON = HTMaterialKey("silicon")

    @JvmField
    val PHOSPHORUS = HTMaterialKey("phosphorus")

    @JvmField
    val SULFUR = HTMaterialKey("sulfur")

    @JvmField
    val CHLORINE = HTMaterialKey("chlorine")

    //    4th Period    //

    //    5th Period    //

    @JvmField
    val SILVER = HTMaterialKey("silver")

    @JvmField
    val TIN = HTMaterialKey("tin")

    //    6th Period    //

    //    7th Period    //

    @JvmField
    val URANIUM = HTMaterialKey("uranium")

    @JvmField
    val PLUTONIUM = HTMaterialKey("plutonium")

    //    Register    //

    override val modId: String = HTMaterialsCommon.MOD_ID

    override val priority: Int = -100

    override fun registerMaterialKey(registry: HTMaterialKeySet) {
        //1st Period
        registry.addAll(
            HYDROGEN,
            HELIUM
        )
        //2nd Period
        registry.addAll(
            LITHIUM,
            BERYLLIUM,
            CARBON,
            NITROGEN,
            OXYGEN,
            FLUORINE
        )
        //3rd Period
        registry.addAll(
            SODIUM,
            MAGNESIUM,
            ALUMINUM,
            SILICON,
            PHOSPHORUS,
            SULFUR,
            CHLORINE
        )
        //5th Period
        registry.addAll(
            SILVER,
            TIN
        )
        //7th Period
        registry.addAll(
            URANIUM,
            PLUTONIUM
        )
    }

    override fun modifyMaterialProperty(registry: HTDefaultedMap<HTMaterialKey, HTMaterialPropertiesNew.Builder>) {
        //1st Period
        registry.getOrCreate(HYDROGEN).add(HTFluidProperty()) { this.isGas = true }
        registry.getOrCreate(HELIUM).add(HTFluidProperty()) { this.isGas = true }
        //2nd Period
        registry.getOrCreate(LITHIUM).add(HTMetalProperty())
        registry.getOrCreate(BERYLLIUM).add(HTMetalProperty())
        registry.getOrCreate(NITROGEN).add(HTFluidProperty()) { this.isGas = true }
        registry.getOrCreate(OXYGEN).add(HTFluidProperty()) { this.isGas = true }
        registry.getOrCreate(FLUORINE).add(HTFluidProperty()) { this.isGas = true }
        //3rd Period
        registry.getOrCreate(SODIUM).add(HTMetalProperty())
        registry.getOrCreate(MAGNESIUM).add(HTMetalProperty())
        registry.getOrCreate(ALUMINUM).add(HTMetalProperty())
        registry.getOrCreate(SILICON).add(HTMetalProperty())
        registry.getOrCreate(SODIUM).add(HTFluidProperty()) { this.isGas = true }
        //5th Period
        registry.getOrCreate(SILVER).add(HTMetalProperty())
        registry.getOrCreate(TIN).add(HTMetalProperty())
        //7th Period
        registry.getOrCreate(URANIUM).add(HTMetalProperty())
        registry.getOrCreate(PLUTONIUM).add(HTMetalProperty())
    }

    override fun modifyMaterialFlag(registry: HTDefaultedMap<HTMaterialKey, HTMaterialFlagsNew.Builder>) {
        //1st Period

        //2nd Period
        registry.getOrCreate(CARBON).apply {
            add(HTMaterialFlag.GENERATE_DUST)
            add(HTMaterialFlag.GENERATE_PLATE)
        }
        //3rd Period
        registry.getOrCreate(ALUMINUM).apply {
            add(HTMaterialFlag.GENERATE_BLOCk)
            add(HTMaterialFlag.GENERATE_DUST)
            add(HTMaterialFlag.GENERATE_GEAR)
            add(HTMaterialFlag.GENERATE_INGOT)
            add(HTMaterialFlag.GENERATE_NUGGET)
            add(HTMaterialFlag.GENERATE_PLATE)
            add(HTMaterialFlag.GENERATE_ROD)
        }
        registry.getOrCreate(SILICON).apply {
            add(HTMaterialFlag.GENERATE_DUST)
            add(HTMaterialFlag.GENERATE_INGOT)
            add(HTMaterialFlag.GENERATE_NUGGET)
            add(HTMaterialFlag.GENERATE_PLATE)
            add(HTMaterialFlag.GENERATE_ROD)
        }
        registry.getOrCreate(PHOSPHORUS).apply {
            add(HTMaterialFlag.GENERATE_DUST)
        }
        registry.getOrCreate(SULFUR).apply {
            add(HTMaterialFlag.GENERATE_DUST)
        }
        //5th Period
        registry.getOrCreate(SILVER).apply {
            add(HTMaterialFlag.GENERATE_BLOCk)
            add(HTMaterialFlag.GENERATE_DUST)
            add(HTMaterialFlag.GENERATE_GEAR)
            add(HTMaterialFlag.GENERATE_INGOT)
            add(HTMaterialFlag.GENERATE_NUGGET)
            add(HTMaterialFlag.GENERATE_PLATE)
            add(HTMaterialFlag.GENERATE_ROD)
        }
        registry.getOrCreate(TIN).apply {
            add(HTMaterialFlag.GENERATE_BLOCk)
            add(HTMaterialFlag.GENERATE_DUST)
            add(HTMaterialFlag.GENERATE_GEAR)
            add(HTMaterialFlag.GENERATE_INGOT)
            add(HTMaterialFlag.GENERATE_NUGGET)
            add(HTMaterialFlag.GENERATE_PLATE)
            add(HTMaterialFlag.GENERATE_ROD)
        }
        //7th Period
        registry.getOrCreate(URANIUM).apply {
            add(HTMaterialFlag.GENERATE_BLOCk)
            add(HTMaterialFlag.GENERATE_DUST)
            add(HTMaterialFlag.GENERATE_INGOT)
            add(HTMaterialFlag.GENERATE_NUGGET)
            add(HTMaterialFlag.GENERATE_PLATE)
            add(HTMaterialFlag.GENERATE_ROD)
        }
        registry.getOrCreate(PLUTONIUM).apply {
            add(HTMaterialFlag.GENERATE_BLOCk)
            add(HTMaterialFlag.GENERATE_DUST)
            add(HTMaterialFlag.GENERATE_INGOT)
            add(HTMaterialFlag.GENERATE_NUGGET)
            add(HTMaterialFlag.GENERATE_PLATE)
            add(HTMaterialFlag.GENERATE_ROD)
        }
    }

    override fun modifyMaterialColor(registry: HashMap<HTMaterialKey, ColorConvertible>) {
        //1st Period
        registry[HYDROGEN] = ColorConvertible { HTColor.BLUE }
        registry[HELIUM] = ColorConvertible { HTColor.YELLOW }
        //2nd Period
        registry[LITHIUM] = ColorConvertible { HTColor.GRAY }
        registry[BERYLLIUM] = ColorConvertible { HTColor.DARK_GREEN }
        registry[CARBON] = ColorConvertible.ofColor(HTColor.BLACK, HTColor.DARK_GRAY)
        registry[NITROGEN] = ColorConvertible { HTColor.AQUA }
        registry[OXYGEN] = ColorConvertible { HTColor.WHITE }
        registry[FLUORINE] = ColorConvertible { HTColor.GREEN }
        //3rd Period
        registry[SODIUM] = ColorConvertible.ofColor(HTColor.DARK_BLUE to 1, HTColor.BLUE to 4)
        registry[MAGNESIUM] = ColorConvertible { HTColor.GRAY }
        registry[ALUMINUM] = ColorConvertible.ofColor(HTColor.BLUE to 1, HTColor.WHITE to 5)
        registry[SILICON] = ColorConvertible.ofColor(HTColor.BLACK to 2, HTColor.GRAY to 1, HTColor.BLUE to 1)
        registry[PHOSPHORUS] = ColorConvertible { HTColor.YELLOW }
        registry[SULFUR] = ColorConvertible.ofColor(HTColor.GOLD, HTColor.YELLOW)
        registry[CHLORINE] = ColorConvertible { HTColor.YELLOW }
        //5th Period
        registry[SILVER] = ColorConvertible.ofColor(HTColor.AQUA to 1, HTColor.WHITE to 3)
        registry[TIN] = ColorConvertible.ofColor(HTColor.BLUE to 1, HTColor.AQUA to 1, HTColor.WHITE to 3)
        //7th Period
        registry[URANIUM] = ColorConvertible { HTColor.GREEN }
        registry[PLUTONIUM] = ColorConvertible { HTColor.RED }
    }

    override fun modifyMaterialFormula(registry: HashMap<HTMaterialKey, FormulaConvertible>) {
        //1st Period
        registry[HYDROGEN] = FormulaConvertible { "H" }
        registry[HELIUM] = FormulaConvertible { "He" }
        //2nd Period
        registry[LITHIUM] = FormulaConvertible { "Li" }
        registry[BERYLLIUM] = FormulaConvertible { "Be" }
        registry[CARBON] = FormulaConvertible { "C" }
        registry[NITROGEN] = FormulaConvertible { "N" }
        registry[OXYGEN] = FormulaConvertible { "O" }
        registry[FLUORINE] = FormulaConvertible { "F" }
        //3rd Period
        registry[SODIUM] = FormulaConvertible { "Na" }
        registry[MAGNESIUM] = FormulaConvertible { "Mg" }
        registry[ALUMINUM] = FormulaConvertible { "Al" }
        registry[SILICON] = FormulaConvertible { "Si" }
        registry[PHOSPHORUS] = FormulaConvertible { "P" }
        registry[SULFUR] = FormulaConvertible { "S" }
        registry[CHLORINE] = FormulaConvertible { "Cl" }
        //5th Period
        registry[SILVER] = FormulaConvertible { "Ag" }
        registry[TIN] = FormulaConvertible { "Sn" }
        //7th Period
        registry[URANIUM] = FormulaConvertible { "U" }
        registry[PLUTONIUM] = FormulaConvertible { "Pu" }
    }

    override fun modifyMaterialMolar(registry: HashMap<HTMaterialKey, MolarMassConvertible>) {
        //1st Period
        registry[HYDROGEN] = MolarMassConvertible { 1.0 }
        registry[HELIUM] = MolarMassConvertible { 4.0 }
        //2nd Period
        registry[LITHIUM] = MolarMassConvertible { 6.9 }
        registry[BERYLLIUM] = MolarMassConvertible { 9.0 }
        registry[CARBON] = MolarMassConvertible { 12.0 }
        registry[NITROGEN] = MolarMassConvertible { 14.0 }
        registry[OXYGEN] = MolarMassConvertible { 16.0 }
        registry[FLUORINE] = MolarMassConvertible { 19.0 }
        //3rd Period
        registry[SODIUM] = MolarMassConvertible { 23.0 }
        registry[MAGNESIUM] = MolarMassConvertible { 24.3 }
        registry[ALUMINUM] = MolarMassConvertible { 27.0 }
        registry[SILICON] = MolarMassConvertible { 28.1 }
        registry[PHOSPHORUS] = MolarMassConvertible { 31.0 }
        registry[SULFUR] = MolarMassConvertible { 32.1 }
        registry[CHLORINE] = MolarMassConvertible { 35.5 }
        //5th Period
        registry[SILVER] = MolarMassConvertible { 107.9 }
        registry[TIN] = MolarMassConvertible { 118.7 }
        //7th Period
        registry[URANIUM] = MolarMassConvertible { 238.0 }
        registry[PLUTONIUM] = MolarMassConvertible { 244.1 }
    }

}