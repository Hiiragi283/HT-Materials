package io.github.hiiragi283.material.api.material

import java.awt.Color

data class HTMaterialInfo(
    val name: String,
    val color: Color,
    val formula: String,
    val molarMass: Double,
    val translationKey: String = "ht_material.$name"
)