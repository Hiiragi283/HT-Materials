package io.github.hiiragi283.material.api.part

import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.shape.HTShape
import io.github.hiiragi283.material.api.shape.HTShapeKey
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText

data class HTPart(val materialKey: HTMaterialKey, val shapeKey: HTShapeKey) {

    fun getMaterial(): HTMaterial = materialKey.getMaterial()

    fun getMaterialOrNull(): HTMaterial? = materialKey.getMaterialOrNull()

    fun getShape(): HTShape = shapeKey.getShape()

    fun getShapeOrNull(): HTShape? = shapeKey.getShapeOrNull()

    fun appendTooltip(stack: ItemStack, lines: MutableList<Text>) {
        //Title
        lines.add(TranslatableText("tooltip.ht_materials.material.title"))
        //Name
        val name: String = shapeKey.getTranslatedName(materialKey)
        lines.add(TranslatableText("tooltip.ht_materials.material.name", name))
        val material: HTMaterial = getMaterialOrNull() ?: return
        //Formula
        material.info.formula.takeIf(String::isNotEmpty)?.let { formula: String ->
            lines.add(TranslatableText("tooltip.ht_materials.material.formula", formula))
        }
        //Molar Mass
        material.info.molarMass.takeIf { it > 0.0 }?.let { molar: Double ->
            lines.add(TranslatableText("tooltip.ht_materials.material.molar", molar))
        }
        //Tooltip from Properties
        material.properties.values.forEach { it.appendTooltip(this, stack, lines) }
    }

}