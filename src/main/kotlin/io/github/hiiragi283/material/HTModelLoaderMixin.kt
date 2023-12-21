package io.github.hiiragi283.material

import io.github.hiiragi283.material.api.fluid.HTMaterialFluid
import io.github.hiiragi283.material.api.item.HTMaterialItem
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.property.HTPropertyKey
import io.github.hiiragi283.material.api.shape.HTShape
import io.github.hiiragi283.material.api.shape.HTShapes
import io.github.hiiragi283.material.util.modify
import net.minecraft.block.Block
import net.minecraft.client.render.model.json.JsonUnbakedModel
import net.minecraft.item.Item
import net.minecraft.resource.Resource
import net.minecraft.resource.ResourceManager
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

object HTModelLoaderMixin {

    @JvmStatic
    fun loadModelFromJson(
        id: Identifier,
        resourceManager: ResourceManager,
        cir: CallbackInfoReturnable<JsonUnbakedModel>
    ) {
        if (id.namespace == HTMaterialsCommon.MOD_ID) {
            val resource: Resource = when {
                id.path.startsWith("block/") -> getBlockResource(id, resourceManager)
                id.path.startsWith("item/") -> getItemResource(id, resourceManager)
                else -> null
            } ?: return
            val reader = InputStreamReader(resource.inputStream, StandardCharsets.UTF_8)
            val jsonUnbakedModel: JsonUnbakedModel =
                JsonUnbakedModel.deserialize(reader).apply { this.id = id.toString() }
            cir.returnValue = jsonUnbakedModel
        }
    }

    @JvmStatic
    private fun getBlockResource(id: Identifier, resourceManager: ResourceManager): Resource? {
        val block: Block = Registry.BLOCK.get(id.modify { it.removePrefix("block/") })
        return null
    }

    @JvmStatic
    private fun getItemResource(id: Identifier, resourceManager: ResourceManager): Resource? {
        val item: Item = Registry.ITEM.get(id.modify { it.removePrefix("item/") })
        return when (item) {
            is HTMaterialItem -> getMaterialItemResource(item, resourceManager)
            is HTMaterialFluid.Bucket -> resourceManager.getResource(HTMaterialsCommon.id("models/item/bucket.json"))
            else -> null
        }
    }

    @JvmStatic
    private fun getMaterialItemResource(item: HTMaterialItem, resourceManager: ResourceManager): Resource? {
        val (material: HTMaterial, shape: HTShape) = item
        return if (shape == HTShapes.GEM) {
            material.getProperty(HTPropertyKey.GEM)?.type?.let { "${it.name.lowercase()}_gem" }?.let {
                resourceManager.getResource(HTMaterialsCommon.id("models/item/$it.json"))
            }
        } else resourceManager.getResource(HTMaterialsCommon.id("models/item/${shape}.json"))
    }

}