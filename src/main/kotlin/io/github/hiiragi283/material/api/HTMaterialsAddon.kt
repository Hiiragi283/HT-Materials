package io.github.hiiragi283.material.api

import io.github.hiiragi283.material.api.material.ColorConvertible
import io.github.hiiragi283.material.api.material.FormulaConvertible
import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.material.MolarMassConvertible
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlagSet
import io.github.hiiragi283.material.api.material.property.HTMaterialPropertyMap
import io.github.hiiragi283.material.api.registry.HTDefaultedMap
import io.github.hiiragi283.material.api.registry.HTDefaultedTable
import io.github.hiiragi283.material.api.registry.HTObjectKeySet
import io.github.hiiragi283.material.api.shape.HTShapeKey
import io.github.hiiragi283.material.api.shape.HTShapePredicate
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.fluid.Fluid
import net.minecraft.item.ItemConvertible

@JvmDefaultWithCompatibility
interface HTMaterialsAddon {

    val modId: String

    val priority: Int

    //    Pre Launch    //

    fun registerShape(registry: HTObjectKeySet<HTShapeKey>) {}

    fun modifyShapePredicate(registry: HTDefaultedMap<HTShapeKey, HTShapePredicate.Builder>) {}

    fun registerMaterialKey(registry: HTObjectKeySet<HTMaterialKey>) {}

    fun modifyMaterialProperty(registry: HTDefaultedMap<HTMaterialKey, HTMaterialPropertyMap.Builder>) {}

    fun modifyMaterialFlag(registry: HTDefaultedMap<HTMaterialKey, HTMaterialFlagSet.Builder>) {}

    fun modifyMaterialColor(registry: MutableMap<HTMaterialKey, ColorConvertible>) {}

    fun modifyMaterialFormula(registry: MutableMap<HTMaterialKey, FormulaConvertible>) {}

    fun modifyMaterialMolar(registry: MutableMap<HTMaterialKey, MolarMassConvertible>) {}

    //    Post Initialization    //

    fun bindItemToPart(registry: HTDefaultedTable<HTMaterialKey, HTShapeKey, MutableCollection<ItemConvertible>>) {}

    fun bindFluidToPart(registry: HTDefaultedMap<HTMaterialKey, MutableCollection<Fluid>>) {}

    fun commonSetup() {}

    @Environment(EnvType.CLIENT)
    fun clientSetup() {
    }

}