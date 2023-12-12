package io.github.hiiragi283.material.addon

import com.simibubi.create.AllItems
import io.github.hiiragi283.material.api.addon.HTMaterialsAddon
import io.github.hiiragi283.material.api.material.materials.HTVanillaMaterials
import io.github.hiiragi283.material.api.part.HTPartManager
import io.github.hiiragi283.material.api.shape.HTShapes

object HMCreateAddon : HTMaterialsAddon {

    override val modId: String = "create"

    override fun commonSetup() {
        HTPartManager.register(HTVanillaMaterials.NETHERRACK, HTShapes.DUST, AllItems.CINDER_FLOUR.get())
        HTPartManager.register(HTVanillaMaterials.OBSIDIAN, HTShapes.DUST, AllItems.POWDERED_OBSIDIAN.get())
    }

}