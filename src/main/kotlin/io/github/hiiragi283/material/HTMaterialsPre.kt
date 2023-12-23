package io.github.hiiragi283.material

import io.github.hiiragi283.material.api.addon.HTMaterialsAddonManager
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Suppress("unused")
object HTMaterialsPre : PreLaunchEntrypoint {

    private val LOGGER: Logger = LoggerFactory.getLogger("${HTMaterialsCommon.MOD_NAME}/Pre")

    override fun onPreLaunch() {

        //Register Shapes
        HTMaterialsAddonManager.registerShape()

        //Register Materials
        HTMaterialsAddonManager.registerMaterialKey()
        HTMaterialsAddonManager.modifyMaterialProperty()
        HTMaterialsAddonManager.modifyMaterialFlag()
        HTMaterialsAddonManager.modifyMaterialColor()
        HTMaterialsAddonManager.modifyMaterialFormula()
        HTMaterialsAddonManager.modifyMaterialMolar()
        HTMaterialsAddonManager.createMaterial()
        HTMaterialsAddonManager.verifyMaterial()

    }

}