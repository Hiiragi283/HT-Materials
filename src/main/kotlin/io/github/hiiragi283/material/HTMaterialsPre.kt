package io.github.hiiragi283.material

import io.github.hiiragi283.material.api.material.HTMaterialNew
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint

object HTMaterialsPre : PreLaunchEntrypoint {

    override fun onPreLaunch() {
        HTMaterialNew.REGISTRY
    }

}