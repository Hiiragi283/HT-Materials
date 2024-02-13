package io.github.hiiragi283.api.shape

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.HTPlatformHelper
import io.github.hiiragi283.api.material.HTMaterialKey
import net.minecraft.util.Identifier

data class HTShape(
    val key: HTShapeKey,
    private val idPath: String,
    private val fabricTagPath: String,
    private val forgeTagPath: String,
) {
    //    Identifier    //

    fun getIdentifier(materialKey: HTMaterialKey, namespace: String = HTMaterialsAPI.MOD_ID) =
        Identifier(namespace, idPath.replace("%s", materialKey.name))

    fun getFabricId(materialKey: HTMaterialKey): Identifier =
        HTPlatformHelper.Loader.FABRIC.id(fabricTagPath.replace("%s", materialKey.name))

    fun getForgeId(materialKey: HTMaterialKey): Identifier = HTPlatformHelper.Loader.FORGE.id(forgeTagPath.replace("%s", materialKey.name))
}
