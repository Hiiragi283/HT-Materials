package io.github.hiiragi283.material.api.material.materials

import com.google.common.collect.Multimap
import io.github.hiiragi283.material.HTMaterialsCommon
import io.github.hiiragi283.material.api.addon.HTMaterialsAddon
import io.github.hiiragi283.material.api.material.ColorConvertible
import io.github.hiiragi283.material.api.material.FormulaConvertible
import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.material.MolarMassConvertible
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlag
import io.github.hiiragi283.material.api.material.property.HTMaterialProperties
import io.github.hiiragi283.material.api.registry.HTDefaultedMap
import io.github.hiiragi283.material.api.registry.HTObjectKeySet
import io.github.hiiragi283.material.util.HTColor

object HTVanillaMaterialsNew : HTMaterialsAddon {

    //    Fluids    //

    @JvmField
    val WATER = HTMaterialKey("water")

    @JvmField
    val LAVA = HTMaterialKey("lava")

    //    Register    //

    override val modId: String = HTMaterialsCommon.MOD_ID

    override val priority: Int = -90

    override fun registerMaterialKey(registry: HTObjectKeySet<HTMaterialKey>) {
        //Fluids
        registry.addAll(
            WATER,
            LAVA
        )
    }

    override fun modifyMaterialProperty(registry: HTDefaultedMap<HTMaterialKey, HTMaterialProperties.Builder>) {
        //Fluids
        registry.getOrCreate(WATER)
        registry.getOrCreate(LAVA)
    }

    override fun modifyMaterialFlag(registry: Multimap<HTMaterialKey, HTMaterialFlag>) {
        //Fluids
    }

    override fun modifyMaterialColor(registry: HashMap<HTMaterialKey, ColorConvertible>) {
        //Fluids
        registry[WATER] = ColorConvertible { HTColor.BLUE }
        registry[LAVA] = ColorConvertible.ofColor(HTColor.DARK_RED, HTColor.GOLD)
    }

    override fun modifyMaterialFormula(registry: HashMap<HTMaterialKey, FormulaConvertible>) {
        //Fluids
        registry[WATER] = FormulaConvertible.of(
            registry,
            HTElementMaterials.HYDROGEN to 2,
            HTElementMaterials.OXYGEN to 1
        )
        registry[LAVA] = FormulaConvertible.of(
            registry,
            *HTAtomicGroups.SILICON_OXIDE
        )
    }

    override fun modifyMaterialMolar(registry: HashMap<HTMaterialKey, MolarMassConvertible>) {
        //Fluids
        registry[WATER] = MolarMassConvertible.of(
            registry,
            HTElementMaterials.HYDROGEN to 2,
            HTElementMaterials.OXYGEN to 1
        )
        registry[LAVA] = MolarMassConvertible.of(
            registry,
            *HTAtomicGroups.SILICON_OXIDE
        )
    }

}