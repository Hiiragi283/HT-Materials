package io.github.hiiragi283.api.extension

import com.google.gson.JsonObject
import net.minecraft.resource.Resource
import net.minecraft.resource.ResourceFinder
import net.minecraft.resource.ResourceManager
import net.minecraft.util.Identifier
import net.minecraft.util.JsonHelper

val JSON_DESERIALIZER: (Resource) -> JsonObject =
    { JsonHelper.deserialize(it.inputStream.buffered().bufferedReader()) }

fun getJsonMultiMap(manager: ResourceManager, startingPath: String): Map<Identifier, Collection<JsonObject>> {
    return ResourceFinder.json(startingPath).findAllResources(manager)
        .mapValues { (_, resources: Collection<Resource>) -> resources.map(JSON_DESERIALIZER) }
}
