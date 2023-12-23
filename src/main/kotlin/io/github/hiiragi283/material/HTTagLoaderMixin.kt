package io.github.hiiragi283.material

import io.github.hiiragi283.material.api.fluid.HTFluidManager
import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.material.HTMaterialNew
import io.github.hiiragi283.material.api.part.HTPartManager
import io.github.hiiragi283.material.api.shape.HTShape
import io.github.hiiragi283.material.mixin.TagBuilderMixin
import io.github.hiiragi283.material.util.*
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.tag.Tag
import net.minecraft.tag.TagKey
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

internal object HTTagLoaderMixin {

    @JvmStatic
    fun loadTags(map: MutableMap<Identifier, Tag.Builder>, registry: Registry<*>?) {
        when (registry) {
            Registry.FLUID -> fluidTags(map)
            Registry.ITEM -> itemTags(map)
            else -> {}
        }
    }

    @JvmStatic
    fun fluidTags(map: MutableMap<Identifier, Tag.Builder>) {
        //Register Tags from HTFluidManager
        HTFluidManager.getMaterialToFluidsMap().forEach { key: HTMaterialKey, fluid: Fluid ->
            registerTag(
                getOrCreateBuilder(map, key.getCommonId()),
                Registry.FLUID,
                fluid
            )
        }
    }

    @JvmStatic
    fun itemTags(map: MutableMap<Identifier, Tag.Builder>) {
        //Register Tags from HTPartManager
        HTPartManager.getPartToItemTable()
            .forEach { (key: HTMaterialKey, shape: HTShape, items: Collection<Item>) ->
            items.forEach { item: Item ->
                registerTag(
                    getOrCreateBuilder(map, shape.getCommonTag(key).id),
                    Registry.ITEM,
                    item
                )
            }
        }
        HTMixinLogger.INSTANCE.info("Registered Tags for HTPartManager's Entries!")
        //Sync ForgeTag and CommonTag entries
        HTMaterialNew.REGISTRY.keys.forEach { key ->
            HTShape.REGISTRY.forEach shape@{ shape ->
                val forgeBuilder: Tag.Builder = getOrCreateBuilder(map, shape.getForgeTag(key))
                val commonBuilder: Tag.Builder = getOrCreateBuilder(map, shape.getCommonTag(key))
                syncBuilder(commonBuilder, forgeBuilder)
                syncBuilder(forgeBuilder, commonBuilder)
            }
        }
        HTMixinLogger.INSTANCE.info("Synced Forge Tags and Common Tags!")
        //Remove Empty Builder
        HashMap(map).forEach { (key: Identifier, value: Tag.Builder) ->
            if ((value as TagBuilderMixin).entries.isEmpty()) {
                map.remove(key)
            }
        }
        HTMixinLogger.INSTANCE.info("Removed empty tag builders!")
    }

    @JvmStatic
    private fun getOrCreateBuilder(map: MutableMap<Identifier, Tag.Builder>, tagKey: TagKey<*>): Tag.Builder =
        getOrCreateBuilder(map, tagKey.id)

    @JvmStatic
    private fun getOrCreateBuilder(map: MutableMap<Identifier, Tag.Builder>, id: Identifier) =
        map.computeIfAbsent(id) { Tag.Builder.create() }

    @JvmStatic
    private fun <T> registerTag(builder: Tag.Builder, registry: Registry<T>, value: T) {
        builder.add(registry.getId(value), HTMaterialsCommon.MOD_NAME)
    }

    @JvmStatic
    private fun syncBuilder(parentBuilder: Tag.Builder, childBuilder: Tag.Builder) {
        (childBuilder as TagBuilderMixin).entries.forEach(parentBuilder::add)
    }

}