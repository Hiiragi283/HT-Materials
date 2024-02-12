package io.github.hiiragi283.api.shape

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.HTPlatformHelper
import io.github.hiiragi283.api.material.HTMaterialKey
import net.minecraft.item.Item
import net.minecraft.tag.TagKey
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

data class HTShape(
    val key: HTShapeKey,
    val idPath: String,
    val tagPath: String,
) {
    //    Identifier    //

    fun getIdentifier(materialKey: HTMaterialKey, namespace: String = HTMaterialsAPI.MOD_ID) =
        Identifier(namespace, idPath.replace("%s", materialKey.name))

    fun getCommonId(material: HTMaterialKey): Identifier =
        HTPlatformHelper.INSTANCE.getLoaderType().id(tagPath.replace("%s", material.name))

    //    Tag    //

    fun getShapeTag(): TagKey<Item> = TagKey.of(Registry.ITEM_KEY, key.getShapeId())
}