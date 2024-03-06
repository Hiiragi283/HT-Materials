package io.github.hiiragi283.api.material.property

import io.github.hiiragi283.api.material.HTMaterial
import io.github.hiiragi283.api.shape.HTShape
import net.minecraft.item.ItemStack
import net.minecraft.text.Text

fun interface MaterialTooltipProvider {
    fun appendTooltip(
        material: HTMaterial,
        shape: HTShape?,
        stack: ItemStack,
        lines: MutableList<Text>,
    )
}
