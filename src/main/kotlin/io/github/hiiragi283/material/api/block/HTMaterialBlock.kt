package io.github.hiiragi283.material.api.block

import io.github.hiiragi283.material.api.item.HTMaterialItemConvertible
import io.github.hiiragi283.material.api.material.HTMaterialNew
import io.github.hiiragi283.material.api.part.HTPart
import io.github.hiiragi283.material.api.shape.HTShape
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.text.MutableText

class HTMaterialBlock(
    override val materialHT: HTMaterialNew,
    override val shapeHT: HTShape,
    settings: FabricBlockSettings
) : Block(settings), HTMaterialItemConvertible {

    override val part: HTPart = HTPart(materialHT, shapeHT)

    override fun getName(): MutableText = part.getTranslatedText()

}