package io.github.hiiragi283.material.api.addon

import io.github.hiiragi283.material.HTMaterialsCommon
import io.github.hiiragi283.material.api.material.*
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlagsNew
import io.github.hiiragi283.material.api.material.property.HTMaterialPropertiesNew
import io.github.hiiragi283.material.api.registry.HTDefaultedMap
import io.github.hiiragi283.material.api.registry.HTObjectKey
import io.github.hiiragi283.material.api.registry.HTObjectKeySet
import io.github.hiiragi283.material.api.shape.HTShape
import io.github.hiiragi283.material.util.isModLoaded
import net.fabricmc.loader.api.FabricLoader
import java.awt.Color

object HTMaterialAddonManager {

    private val list: List<HTMaterialAddonNew> = FabricLoader.getInstance()
        .getEntrypoints(HTMaterialsCommon.MOD_ID, HTMaterialAddonNew::class.java)
        .filter { isModLoaded(it.modId) }

    private val shapeKeySet: HTObjectKeySet<HTShape> = HTObjectKeySet.create()

    fun registerShape() {
        list.forEach { it.registerShape(shapeKeySet) }
    }

    private val materialKeySet: HTObjectKeySet<HTMaterialNew> = HTObjectKeySet.create()

    fun registerMaterialKey() {
        list.forEach { it.registerMaterialKey(materialKeySet) }
    }

    private val propertyMap: HTDefaultedMap<HTObjectKey<HTMaterialNew>, HTMaterialPropertiesNew.Builder> =
        HTDefaultedMap.create { HTMaterialPropertiesNew.Builder() }

    fun modifyMaterialProperty() {
        list.forEach { it.modifyMaterialProperty(propertyMap) }
    }

    private val flagMap: HTDefaultedMap<HTObjectKey<HTMaterialNew>, HTMaterialFlagsNew.Builder> =
        HTDefaultedMap.create { HTMaterialFlagsNew.Builder() }

    fun modifyMaterialFlag() {
        list.forEach { it.modifyMaterialFlag(flagMap) }
    }

    private val colorMap: HashMap<HTObjectKey<HTMaterialNew>, ColorConvertible> = hashMapOf()

    fun modifyMaterialColor() {
        list.forEach { it.modifyMaterialColor(colorMap) }
    }

    private val formulaMap: HashMap<HTObjectKey<HTMaterialNew>, FormulaConvertible> = hashMapOf()

    fun modifyMaterialFormula() {
        list.forEach { it.modifyMaterialFormula(formulaMap) }
    }

    private val molarMap: HashMap<HTObjectKey<HTMaterialNew>, MolarMassConvertible> = hashMapOf()

    fun modifyMaterialMolar() {
        list.forEach { it.modifyMaterialMolar(molarMap) }
    }

    fun createMaterial() {
        val test = materialKeySet.map { key ->
            val property: HTMaterialPropertiesNew = propertyMap.getOrCreate(key).build()
            val flags: HTMaterialFlagsNew = flagMap.getOrCreate(key).build()
            val color: Color = colorMap.getOrDefault(key, ColorConvertible.EMPTY).asColor()
            val formula: String = formulaMap.getOrDefault(key, FormulaConvertible.EMPTY).asFormula()
            val molar: Double = molarMap.getOrDefault(key, MolarMassConvertible.EMPTY).asMolarMass()
            val info = HTMaterialInfo(key.name, color, formula, molar)
            HTMaterialNew(info, property, flags)
        }
    }

}