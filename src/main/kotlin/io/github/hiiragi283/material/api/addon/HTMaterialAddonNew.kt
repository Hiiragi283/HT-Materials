package io.github.hiiragi283.material.api.addon

import io.github.hiiragi283.material.api.material.ColorConvertible
import io.github.hiiragi283.material.api.material.FormulaConvertible
import io.github.hiiragi283.material.api.material.HTMaterialNew
import io.github.hiiragi283.material.api.material.MolarMassConvertible
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlagsNew
import io.github.hiiragi283.material.api.material.property.HTMaterialPropertiesNew
import io.github.hiiragi283.material.api.registry.HTDefaultedMap
import io.github.hiiragi283.material.api.registry.HTObjectKey
import io.github.hiiragi283.material.api.registry.HTObjectKeySet
import io.github.hiiragi283.material.api.shape.HTShape
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment

@JvmDefaultWithCompatibility
interface HTMaterialAddonNew {

    //    Pre Launch    //

    val modId: String

    fun registerShape(registry: HTObjectKeySet<HTShape>) {}

    fun registerMaterialKey(registry: HTObjectKeySet<HTMaterialNew>) {}

    fun modifyMaterialProperty(registry: HTDefaultedMap<HTObjectKey<HTMaterialNew>, HTMaterialPropertiesNew.Builder>) {}

    fun modifyMaterialFlag(registry: HTDefaultedMap<HTObjectKey<HTMaterialNew>, HTMaterialFlagsNew.Builder>) {}

    fun modifyMaterialColor(registry: HashMap<HTObjectKey<HTMaterialNew>, ColorConvertible>) {}

    fun modifyMaterialFormula(registry: HashMap<HTObjectKey<HTMaterialNew>, FormulaConvertible>) {}

    fun modifyMaterialMolar(registry: HashMap<HTObjectKey<HTMaterialNew>, MolarMassConvertible>) {}

    //    Initialization    //

    fun bindItemToPart() {}

    //    Post Initialization    //

    fun commonSetup() {}

    @Environment(EnvType.CLIENT)
    fun clientSetup() {
    }

}