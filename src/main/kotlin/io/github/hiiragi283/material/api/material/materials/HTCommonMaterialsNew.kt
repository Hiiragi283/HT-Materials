package io.github.hiiragi283.material.api.material.materials

import io.github.hiiragi283.material.HTMaterialsCommon
import io.github.hiiragi283.material.api.addon.HTMaterialsAddon
import io.github.hiiragi283.material.api.material.*
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlag
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlagsNew
import io.github.hiiragi283.material.api.material.property.HTGemProperty
import io.github.hiiragi283.material.api.material.property.HTMaterialPropertiesNew
import io.github.hiiragi283.material.api.registry.HTDefaultedMap
import io.github.hiiragi283.material.util.HTColor

object HTCommonMaterialsNew : HTMaterialsAddon {

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

    //    Register    //

    override val modId: String = HTMaterialsCommon.MOD_ID

    override val priority: Int = -100

    override fun registerMaterialKey(registry: HTMaterialKeySet) {
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
    }

    override fun modifyMaterialProperty(registry: HTDefaultedMap<HTMaterialKey, HTMaterialPropertiesNew.Builder>) {
        //Fluids
        //Gems
        registry.getOrCreate(CINNABAR).add(HTGemProperty(HTGemProperty.Type.EMERALD))
        registry.getOrCreate(COKE).add(HTGemProperty(HTGemProperty.Type.COAL))
        registry.getOrCreate(OLIVINE).add(HTGemProperty(HTGemProperty.Type.EMERALD))
        registry.getOrCreate(PERIDOT).add(HTGemProperty(HTGemProperty.Type.RUBY))
        registry.getOrCreate(RUBY).add(HTGemProperty(HTGemProperty.Type.RUBY))
        registry.getOrCreate(SALT).add(HTGemProperty(HTGemProperty.Type.CUBIC))
        registry.getOrCreate(SAPPHIRE).add(HTGemProperty(HTGemProperty.Type.RUBY))
    }

    override fun modifyMaterialFlag(registry: HTDefaultedMap<HTMaterialKey, HTMaterialFlagsNew.Builder>) {
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
    }

    override fun modifyMaterialFormula(registry: HashMap<HTMaterialKey, FormulaConvertible>) {
        //Fluids
        //Gems
        registry[CINNABAR]
        registry[COKE] = registry[HTElementMaterialsNew.CARBON]!!


        registry[RUBY] = FormulaConvertible.of(
            registry,
            HTElementMaterialsNew.ALUMINUM to 3,
            HTElementMaterialsNew.OXYGEN to 2
        )
        registry[SALT] = FormulaConvertible.of(
            registry[HTElementMaterialsNew.SODIUM]!! to 1,
            registry[HTElementMaterialsNew.CHLORINE]!! to 1
        )
        registry[SAPPHIRE] = FormulaConvertible.of(
            registry,
            HTElementMaterialsNew.ALUMINUM to 3,
            HTElementMaterialsNew.OXYGEN to 2
        )
    }

    override fun modifyMaterialMolar(registry: HashMap<HTMaterialKey, MolarMassConvertible>) {
        //Fluids
        //Gems

    }

}