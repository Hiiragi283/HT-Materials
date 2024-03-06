package io.github.hiiragi283.material.impl

import com.google.common.collect.ImmutableMap
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.extension.getJsonMultiMap
import io.github.hiiragi283.api.extension.runTryAndCatch
import io.github.hiiragi283.api.shape.HTShape
import io.github.hiiragi283.api.shape.HTShapeRegistry
import io.github.hiiragi283.material.HTMaterials
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener
import net.minecraft.resource.ResourceManager
import net.minecraft.util.Identifier
import net.minecraft.util.JsonHelper

object HTShapeRegistryImpl : HTShapeRegistry, SimpleSynchronousResourceReloadListener {
    //    HTShapeRegistry    //

    override var registry: ImmutableMap<String, HTShape> = ImmutableMap.of()
        private set

    //    SimpleSynchronousResourceReloadListener    //

    override fun reload(manager: ResourceManager) {
        HTShapeRegistry.Builder().run {
            try {
                // Reload from Data Pack
                getJsonMultiMap(manager, "shape").values.forEach { jsonObjects: Collection<JsonObject> ->
                    jsonObjects.forEach { jsonObject: JsonObject ->
                        runTryAndCatch {
                            JsonHelper.getArray(jsonObject, "add", null)
                                ?.map(JsonElement::getAsString)
                                ?.forEach<String>(::add)
                        }
                    }
                }
                // Reload from Addons
                HTMaterials.addons.forEach { runTryAndCatch { it.modifyShapeRegistry(this) } }
                // Initialize
                this@HTShapeRegistryImpl.registry = this.registry
                HTMaterialsAPI.log("HTShapeRegistry initialized!")
            } catch (e: Exception) {
                HTMaterialsAPI.exception(e)
            }
        }
    }

    override fun getFabricId(): Identifier = HTMaterialsAPI.id("shape")
}
