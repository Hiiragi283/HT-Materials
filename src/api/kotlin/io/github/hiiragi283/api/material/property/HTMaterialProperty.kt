package io.github.hiiragi283.api.material.property

import io.github.hiiragi283.api.material.HTMaterial
import io.github.hiiragi283.api.shape.HTShape
import net.minecraft.item.ItemStack
import net.minecraft.text.Text

interface HTMaterialProperty {
    fun appendTooltip(
        material: HTMaterial,
        shapeKey: HTShape?,
        stack: ItemStack,
        lines: MutableList<Text>,
    ) {}
}
