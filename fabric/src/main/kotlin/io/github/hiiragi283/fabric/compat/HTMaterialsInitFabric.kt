package io.github.hiiragi283.fabric.compat

import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.material.HTMaterialKeys
import io.github.hiiragi283.api.material.HTMaterialsInit
import io.github.hiiragi283.api.material.content.HTMaterialContentMap
import io.github.hiiragi283.api.util.collection.DefaultedMap
import io.github.hiiragi283.fabric.content.HTSimpleFluidContent

object HTMaterialsInitFabric : HTMaterialsInit() {
    override fun modifyMaterialContent(registry: DefaultedMap<HTMaterialKey, HTMaterialContentMap>) {
        super.modifyMaterialContent(registry)
        // 1st Period
        registry.getOrCreate(HTMaterialKeys.HYDROGEN)
            .add(HTSimpleFluidContent())
        registry.getOrCreate(HTMaterialKeys.HELIUM)
            .add(HTSimpleFluidContent())
        // 2nd Period
        registry.getOrCreate(HTMaterialKeys.NITROGEN)
            .add(HTSimpleFluidContent())
        registry.getOrCreate(HTMaterialKeys.OXYGEN)
            .add(HTSimpleFluidContent())
        registry.getOrCreate(HTMaterialKeys.FLUORINE)
            .add(HTSimpleFluidContent())
        // 3rd Period
        registry.getOrCreate(HTMaterialKeys.CHLORINE)
            .add(HTSimpleFluidContent())
        // 6th Period
        registry.getOrCreate(HTMaterialKeys.MERCURY)
            .add(HTSimpleFluidContent())
    }
}