package io.github.hiiragi283.material.api.part

import io.github.hiiragi283.material.HTMaterialsCommon
import io.github.hiiragi283.material.api.item.HTMaterialItemConvertible
import io.github.hiiragi283.material.api.material.HTMaterialNew
import io.github.hiiragi283.material.api.shape.HTShape
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.resource.language.I18n
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier

data class HTPart(override val materialHT: HTMaterialNew, override val shapeHT: HTShape) : HTMaterialItemConvertible {

    @Environment(EnvType.CLIENT)
    fun getTranslatedName(): String =
        I18n.translate("ht_shape.${shapeHT.name}", materialHT.getTranslatedName())

    fun getTranslatedText(): TranslatableText =
        TranslatableText("ht_shape.${shapeHT.name}", materialHT.getTranslatedName())

    fun getIdentifier(namespace: String = HTMaterialsCommon.MOD_ID): Identifier =
        shapeHT.getIdentifier(materialHT, namespace)

    fun appendTooltip(stack: ItemStack, lines: MutableList<Text>) {
        //Title
        lines.add(TranslatableText("tooltip.ht_materials.material.title"))
        //Name
        lines.add(TranslatableText("tooltip.ht_materials.material.name", getTranslatedName()))
        //Formula
        materialHT.info.formula.takeIf(String::isNotEmpty)?.let { formula: String ->
            lines.add(TranslatableText("tooltip.ht_materials.material.formula", formula))
        }
        //Molar Mass
        materialHT.info.molarMass.takeIf { it > 0.0 }?.let { molar: Double ->
            lines.add(TranslatableText("tooltip.ht_materials.material.molar", molar))
        }
        //Tooltip from Properties
        materialHT.properties.values.forEach { it.appendTooltip(this, stack, lines) }
    }

    //    HTMaterialItemConvertible    //

    override val part: HTPart = this

    override fun asItem(): Item = HTPartManager.getDefaultItem(materialHT, shapeHT) ?: Items.AIR

}