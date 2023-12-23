package io.github.hiiragi283.material.addon

import io.github.hiiragi283.material.api.addon.HTMaterialsAddon

@Suppress("unused")
object HMCreateAddon : HTMaterialsAddon {

    override val modId: String = "create"

    override val priority: Int = 0

    override fun commonSetup() {
        //HTPartManager.register(HTVanillaMaterials.NETHERRACK, HTShapes.DUST, AllItems.CINDER_FLOUR.get())
        //HTPartManager.register(HTVanillaMaterials.OBSIDIAN, HTShapes.DUST, AllItems.POWDERED_OBSIDIAN.get())
    }

}