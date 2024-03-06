package io.github.hiiragi283.api.material.type

import io.github.hiiragi283.api.shape.HTShape

data class HTMaterialType(
    val name: String,
    val pathPrefix: String,
    val defaultShapeKey: HTShape?,
)
