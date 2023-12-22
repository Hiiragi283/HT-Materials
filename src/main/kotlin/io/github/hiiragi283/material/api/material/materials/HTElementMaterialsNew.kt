package io.github.hiiragi283.material.api.material.materials

import io.github.hiiragi283.material.HTMaterialsCommon
import io.github.hiiragi283.material.api.addon.HTMaterialAddonNew
import io.github.hiiragi283.material.api.material.ColorConvertible
import io.github.hiiragi283.material.api.material.FormulaConvertible
import io.github.hiiragi283.material.api.material.HTMaterialNew
import io.github.hiiragi283.material.api.material.MolarMassConvertible
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlagsNew
import io.github.hiiragi283.material.api.material.property.HTFluidProperty
import io.github.hiiragi283.material.api.material.property.HTMaterialPropertiesNew
import io.github.hiiragi283.material.api.material.property.HTPropertyKey
import io.github.hiiragi283.material.api.registry.HTDefaultedMap
import io.github.hiiragi283.material.api.registry.HTObjectKey
import io.github.hiiragi283.material.api.registry.HTObjectKeySet
import io.github.hiiragi283.material.util.HTColor

object HTElementMaterialsNew : HTMaterialAddonNew {

    //    1st Period    //

    @JvmField
    val HYDROGEN: HTObjectKey<HTMaterialNew> = HTObjectKey.create("hydrogen")

    @JvmField
    val HELIUM: HTObjectKey<HTMaterialNew> = HTObjectKey.create("helium")

    //    Register    //

    override val modId: String = HTMaterialsCommon.MOD_ID

    override fun registerMaterialKey(registry: HTObjectKeySet<HTMaterialNew>) {
        registry.addAll(
            HYDROGEN,
            HELIUM
        )
    }

    override fun modifyMaterialProperty(registry: HTDefaultedMap<HTObjectKey<HTMaterialNew>, HTMaterialPropertiesNew.Builder>) {
        registry.getOrCreate(HYDROGEN).apply {
            this[HTPropertyKey.FLUID] = HTFluidProperty()
        }
    }

    override fun modifyMaterialFlag(registry: HTDefaultedMap<HTObjectKey<HTMaterialNew>, HTMaterialFlagsNew.Builder>) {

    }

    override fun modifyMaterialColor(registry: HashMap<HTObjectKey<HTMaterialNew>, ColorConvertible>) {
        registry[HYDROGEN] = ColorConvertible { HTColor.BLUE }
        registry[HELIUM] = ColorConvertible { HTColor.YELLOW }

    }

    override fun modifyMaterialFormula(registry: HashMap<HTObjectKey<HTMaterialNew>, FormulaConvertible>) {
        registry[HYDROGEN] = FormulaConvertible { "H" }
        registry[HELIUM] = FormulaConvertible { "He" }

    }

    override fun modifyMaterialMolar(registry: HashMap<HTObjectKey<HTMaterialNew>, MolarMassConvertible>) {
        registry[HYDROGEN] = MolarMassConvertible { 1.0 }
        registry[HELIUM] = MolarMassConvertible { 4.0 }

    }

}