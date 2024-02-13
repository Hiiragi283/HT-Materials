package io.github.hiiragi283.forge.compat

import io.github.hiiragi283.api.HTAddon
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.material.HTMaterialKeys
import io.github.hiiragi283.api.material.HTMaterialsInit
import io.github.hiiragi283.api.material.content.HTMaterialContentMap
import io.github.hiiragi283.api.util.collection.DefaultedMap
import io.github.hiiragi283.forge.content.HTForgeFluidContent

@HTAddon
class HTMaterialsInitForge : HTMaterialsInit() {
    override fun modifyMaterialContent(registry: DefaultedMap<HTMaterialKey, HTMaterialContentMap>) {
        super.modifyMaterialContent(registry)
        // 1st Period
        registry.getOrCreate(HTMaterialKeys.HYDROGEN)
            .add(HTForgeFluidContent())
        registry.getOrCreate(HTMaterialKeys.HELIUM)
            .add(HTForgeFluidContent())
        // 2nd Period
        registry.getOrCreate(HTMaterialKeys.NITROGEN)
            .add(HTForgeFluidContent())
        registry.getOrCreate(HTMaterialKeys.OXYGEN)
            .add(HTForgeFluidContent())
        registry.getOrCreate(HTMaterialKeys.FLUORINE)
            .add(HTForgeFluidContent())
        // 3rd Period
        registry.getOrCreate(HTMaterialKeys.CHLORINE)
            .add(HTForgeFluidContent())
        // 6th Period
        registry.getOrCreate(HTMaterialKeys.MERCURY)
            .add(HTForgeFluidContent())
    }
}