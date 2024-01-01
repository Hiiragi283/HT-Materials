package io.github.hiiragi283.material.api.part

import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.shape.HTShape
import io.github.hiiragi283.material.api.shape.HTShapeKey

data class HTPart(val materialKey: HTMaterialKey, val shapeKey: HTShapeKey) {

    fun getMaterial(): HTMaterial = materialKey.getMaterial()

    fun getMaterialOrNull(): HTMaterial? = materialKey.getMaterialOrNull()

    fun getShape(): HTShape = shapeKey.getShape()

    fun getShapeOrNull(): HTShape? = shapeKey.getShapeOrNull()

}