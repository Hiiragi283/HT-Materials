package io.github.hiiragi283.api.material.type

import io.github.hiiragi283.api.shape.HTShapes

object HTMaterialTypes {
    @JvmField
    val UNDEFINED = HTMaterialType("undefined", "solid", null)

    @JvmField
    val GEM_AMETHYST = HTMaterialType("amethyst", "gem", HTShapes.GEM)

    @JvmField
    val GEM_COAL = HTMaterialType("coal", "gem", HTShapes.GEM)

    @JvmField
    val GEM_CUBIC = HTMaterialType("cubic", "gem", HTShapes.GEM)

    @JvmField
    val GEM_DIAMOND = HTMaterialType("diamond", "gem", HTShapes.GEM)

    @JvmField
    val GEM_EMERALD = HTMaterialType("emerald", "gem", HTShapes.GEM)

    @JvmField
    val GEM_FLINT = HTMaterialType("flint", "gem", HTShapes.GEM)

    @JvmField
    val GEM_LAPIS = HTMaterialType("lapis", "gem", HTShapes.GEM)

    @JvmField
    val GEM_QUARTZ = HTMaterialType("quartz", "gem", HTShapes.GEM)

    @JvmField
    val GEM_RUBY = HTMaterialType("ruby", "gem", HTShapes.GEM)

    @JvmField
    val METAL = HTMaterialType("metal", "metal", HTShapes.INGOT)

    @JvmField
    val STONE = HTMaterialType("stone", "stone", null)

    @JvmField
    val WOOD = HTMaterialType("wood", "wood", null)
}
