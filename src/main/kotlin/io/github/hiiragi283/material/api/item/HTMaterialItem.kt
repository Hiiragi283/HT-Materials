package io.github.hiiragi283.material.api.item

import io.github.hiiragi283.material.HTMaterialsCommon
import io.github.hiiragi283.material.api.material.HTMaterialNew
import io.github.hiiragi283.material.api.part.HTPart
import io.github.hiiragi283.material.api.shape.HTShape
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.text.Text

class HTMaterialItem(
    override val materialHT: HTMaterialNew,
    override val shapeHT: HTShape
) : Item(FabricItemSettings().group(HTMaterialsCommon.ITEM_GROUP)), HTMaterialItemConvertible {

    override val part: HTPart = HTPart(materialHT, shapeHT)

    override fun getName(): Text = part.getTranslatedText()

    override fun getName(stack: ItemStack): Text = part.getTranslatedText()

}