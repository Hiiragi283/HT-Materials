package io.github.hiiragi283.material

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Suppress("unused")
object HTMaterialsPre : PreLaunchEntrypoint {

    private val LOGGER: Logger = LoggerFactory.getLogger("${HTMaterialsCommon.MOD_NAME}/Pre")

    override fun onPreLaunch() {

        //Register Shapes
        HTMaterialsCore.registerShape()
        HTMaterialsCore.modifyShapePredicate()
        HTMaterialsCore.createShape()

        //Register Materials
        HTMaterialsCore.registerMaterialKey()
        HTMaterialsCore.modifyMaterialProperty()
        HTMaterialsCore.modifyMaterialFlag()
        HTMaterialsCore.modifyMaterialColor()
        HTMaterialsCore.modifyMaterialFormula()
        HTMaterialsCore.modifyMaterialMolar()
        HTMaterialsCore.createMaterial()
        HTMaterialsCore.verifyMaterial()

    }

}