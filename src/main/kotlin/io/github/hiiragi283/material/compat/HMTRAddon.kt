package io.github.hiiragi283.material.compat

import io.github.hiiragi283.api.HTMaterialsAddon
import io.github.hiiragi283.api.material.HTMaterialKeys
import io.github.hiiragi283.api.part.HTPartManager
import io.github.hiiragi283.api.shape.HTShape
import io.github.hiiragi283.api.shape.HTShapeRegistry
import io.github.hiiragi283.api.shape.HTShapes
import techreborn.init.TRContent

@Suppress("unused")
object HMTRAddon : HTMaterialsAddon {
    override val modId: String = "techreborn"
    override val priority: Int = 0

    @JvmField
    val SMALL_DUST = HTShape("small_dust")

    override fun modifyShapeRegistry(builder: HTShapeRegistry.Builder) {
        builder.add(SMALL_DUST)
    }

    override fun modifyPartManager(builder: HTPartManager.Builder) {
        builder.add(HTMaterialKeys.PHOSPHORUS, HTShapes.DUST, TRContent.Dusts.PHOSPHOROUS)
        builder.add(HTMaterialKeys.PHOSPHORUS, SMALL_DUST, TRContent.SmallDusts.PHOSPHOROUS)
        builder.add(HTMaterialKeys.RUBY, HTShapes.GEM, TRContent.Gems.RUBY)
        builder.add(HTMaterialKeys.SAPPHIRE, HTShapes.GEM, TRContent.Gems.SAPPHIRE)
        builder.add(HTMaterialKeys.WOOD, HTShapes.DUST, TRContent.Dusts.SAW)
    }
}
