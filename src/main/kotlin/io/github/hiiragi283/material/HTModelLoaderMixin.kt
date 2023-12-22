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
            val modelId: Identifier = when {
                id.path.startsWith("block/") -> getBlockId(id)
                id.path.startsWith("item/") -> getItemId(id)
                else -> null
            } ?: return
            val resource: Resource = resourceManager.getResource(modelId)
            val reader = InputStreamReader(resource.inputStream, StandardCharsets.UTF_8)
            val jsonUnbakedModel: JsonUnbakedModel =
                JsonUnbakedModel.deserialize(reader).apply { this.id = id.toString() }
            cir.returnValue = jsonUnbakedModel
        }
    }

    @JvmStatic
    private fun getBlockId(id: Identifier): Identifier? {
        val block: Block = Registry.BLOCK.get(id.modify { it.removePrefix("block/") })
        return null
    }

    @JvmStatic
    private fun getItemId(id: Identifier): Identifier? {
        val item: Item = Registry.ITEM.get(id.modify { it.removePrefix("item/") })
        return when (item) {
            is HTMaterialItem -> getMaterialItemId(item)
            is HTMaterialFluid.Bucket -> HTMaterialsCommon.id("models/item/bucket.json")
            else -> null
        }
    }

    @JvmStatic
    private fun getMaterialItemId(item: HTMaterialItem): Identifier? {
        val (material: HTMaterial, shape: HTShape) = item
        return if (shape == HTShapes.GEM) {
            material.getProperty(HTPropertyKey.GEM)?.type?.let { "${it.name.lowercase()}_gem" }?.let {
                HTMaterialsCommon.id("models/item/$it.json")
            }
        } else HTMaterialsCommon.id("models/item/${shape}.json")
    }

}