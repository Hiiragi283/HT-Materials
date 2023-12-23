package io.github.hiiragi283.material.api.item

import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.part.HTPart
import io.github.hiiragi283.material.api.shape.HTShape
import net.minecraft.item.ItemConvertible

@JvmDefaultWithCompatibility
interface HTMaterialItemConvertible : ItemConvertible {

    val materialKey: HTMaterialKey
    val shape: HTShape
    val part: HTPart

    operator fun component1(): HTMaterialKey = materialKey

    operator fun component2(): HTShape = shape

}