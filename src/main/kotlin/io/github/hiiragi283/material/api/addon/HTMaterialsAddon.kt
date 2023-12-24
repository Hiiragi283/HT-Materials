package io.github.hiiragi283.material.api.addon

import com.google.common.collect.Multimap
import io.github.hiiragi283.material.api.material.ColorConvertible
import io.github.hiiragi283.material.api.material.FormulaConvertible
import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.material.MolarMassConvertible
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlag
import io.github.hiiragi283.material.api.material.property.HTMaterialProperties
import io.github.hiiragi283.material.api.registry.HTDefaultedMap
import io.github.hiiragi283.material.api.registry.HTObjectKeySet
import io.github.hiiragi283.material.api.shape.HTShapeKey
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment

@JvmDefaultWithCompatibility
interface HTMaterialsAddon {

    val modId: String

    val priority: Int

    //    Pre Launch    //

    fun registerShape(registry: HTObjectKeySet<HTShapeKey>) {}

    fun registerMaterialKey(registry: HTObjectKeySet<HTMaterialKey>) {}

    fun modifyMaterialProperty(registry: HTDefaultedMap<HTMaterialKey, HTMaterialProperties.Builder>) {}

    fun modifyMaterialFlag(registry: Multimap<HTMaterialKey, HTMaterialFlag>) {}

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