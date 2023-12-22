package io.github.hiiragi283.material.api.item

import io.github.hiiragi283.material.api.material.HTMaterialNew
import io.github.hiiragi283.material.api.part.HTPart
import io.github.hiiragi283.material.api.shape.HTShape
import net.minecraft.item.ItemConvertible

@JvmDefaultWithCompatibility
interface HTMaterialItemConvertible : ItemConvertible {

    val materialHT: HTMaterialNew
    val shapeHT: HTShape
    val part: HTPart

    operator fun component1(): HTMaterialNew = materialHT

    operator fun component2(): HTShape = shapeHT

}