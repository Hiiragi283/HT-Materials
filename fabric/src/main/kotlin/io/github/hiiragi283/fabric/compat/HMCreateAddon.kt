package io.github.hiiragi283.fabric.compat

import com.simibubi.create.AllItems
import io.github.hiiragi283.api.HTMaterialsAddon
import io.github.hiiragi283.api.material.HTMaterialKeys
import io.github.hiiragi283.api.part.HTPartManager
import io.github.hiiragi283.api.shape.HTShapeKeys

@Suppress("unused")
object HMCreateAddon : HTMaterialsAddon {
    override val modId: String = "create"

    override val priority: Int = 0

    override fun bindItemToPart(builder: HTPartManager.Builder) {
        builder.add(HTMaterialKeys.NETHERRACK, HTShapeKeys.DUST, AllItems.CINDER_FLOUR.get())
        builder.add(HTMaterialKeys.OBSIDIAN, HTShapeKeys.DUST, AllItems.POWDERED_OBSIDIAN.get())
    }
}
