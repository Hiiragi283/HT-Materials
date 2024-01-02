package io.github.hiiragi283.material

import io.github.hiiragi283.material.api.fluid.HTMaterialFluid
import io.github.hiiragi283.material.api.item.HTMaterialItem
import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.material.property.HTPropertyKey
import io.github.hiiragi283.material.api.shape.HTShapeKey
import io.github.hiiragi283.material.util.modify
import net.minecraft.item.Item
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object HTModelLoaderMixin {

    private const val BLOCK_PREFIX = "block/"

    private const val ITEM_PREFIX = "item/"

    @JvmStatic
    fun modifyBlockStateId(id: Identifier): Identifier = if (id.namespace == HTMaterialsCommon.MOD_ID) {
        id
    } else id

    @JvmStatic
    fun modifyModelId(id: Identifier): Identifier = if (id.namespace == HTMaterialsCommon.MOD_ID) {
        val path: String = id.path.removePrefix("models/").removeSuffix(".json")
        val fixedId: Identifier = id.modify { path }
        if (path.startsWith(BLOCK_PREFIX)) {
            modifyBlockModelId(fixedId)
        } else if (path.startsWith(ITEM_PREFIX)) {
            modifyItemModelId(fixedId)
        } else id
    } else id

    private fun modifyBlockModelId(id: Identifier): Identifier = id

    private fun modifyItemModelId(id: Identifier): Identifier {
        val item: Item = Registry.ITEM.get(id.modify { it.removePrefix(ITEM_PREFIX) })
        return when (item) {
            is HTMaterialItem -> {
                val (material: HTMaterialKey, shape: HTShapeKey) = item
                val modelName: String = material.getMaterial()
                    .getProperty(HTPropertyKey.GEM)
                    ?.let { "${it.name.lowercase()}_gem" }
                    ?: shape.toString()
                HTMaterialsCommon.id("models/item/$modelName.json")
            }
            is HTMaterialFluid.Bucket -> HTMaterialsCommon.id("models/item/bucket.json")
            else -> id
        }
    }

}