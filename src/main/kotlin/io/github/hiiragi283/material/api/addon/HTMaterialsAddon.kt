package io.github.hiiragi283.material.api.addon

import io.github.hiiragi283.material.api.material.*
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlagsNew
import io.github.hiiragi283.material.api.material.property.HTMaterialPropertiesNew
import io.github.hiiragi283.material.api.registry.HTDefaultedMap
import io.github.hiiragi283.material.api.registry.HTObjectKeySet
import io.github.hiiragi283.material.api.shape.HTShape
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment

@JvmDefaultWithCompatibility
interface HTMaterialsAddon {

    val modId: String

    val priority: Int

    //    Pre Launch    //

    fun registerShape(registry: HTObjectKeySet<HTShape>) {}

    fun registerMaterialKey(registry: HTMaterialKeySet) {}

    fun modifyMaterialProperty(registry: HTDefaultedMap<HTMaterialKey, HTMaterialPropertiesNew.Builder>) {}

    fun modifyMaterialFlag(registry: HTDefaultedMap<HTMaterialKey, HTMaterialFlagsNew.Builder>) {}

    fun modifyMaterialColor(registry: HashMap<HTMaterialKey, ColorConvertible>) {}

    fun modifyMaterialFormula(registry: HashMap<HTMaterialKey, FormulaConvertible>) {}

    fun modifyMaterialMolar(registry: HashMap<HTMaterialKey, MolarMassConvertible>) {}

    //    Initialization    //

    fun bindItemToPart() {}

    fun bindFluidToPart() {}

    //    Post Initialization    //

    fun commonSetup() {}

    @Environment(EnvType.CLIENT)
    fun clientSetup() {
    }

}