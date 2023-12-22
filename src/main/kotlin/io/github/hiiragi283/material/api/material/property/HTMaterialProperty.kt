package io.github.hiiragi283.material.api.material.property

import io.github.hiiragi283.material.api.material.HTMaterialNew
import io.github.hiiragi283.material.api.part.HTPart
import net.minecraft.item.ItemStack
import net.minecraft.text.Text

@JvmDefaultWithCompatibility
interface HTMaterialProperty<T : HTMaterialProperty<T>> {

    val key: HTPropertyKey<T>

    fun verify(material: HTMaterialNew)

    fun appendTooltip(part: HTPart, stack: ItemStack, lines: MutableList<Text>) {}

}