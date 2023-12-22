package io.github.hiiragi283.material

import io.github.hiiragi283.material.api.fluid.HTFluidManager
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
            //Registry.BLOCK -> blockTags(map)
            Registry.FLUID -> fluidTags(map)
            Registry.ITEM -> itemTags(map)
            else -> {}
        }
    }

    /*
    @JvmStatic
    fun blockTags(map: MutableMap<Identifier, Tag.Builder>) {
        //Register Mining Tool & Harvest Level Tags
        for (material: HTMaterial in HTMaterial.REGISTRY) {
            val solidProperty: HTSolidProperty = material.getProperty(HTPropertyKey.SOLID) ?: continue
            HTShapes.REGISTRY.forEach { shape: HTShape ->
                (HTPartManager.getDefaultItem(material, shape)?.asBlock() as? HTMaterialBlock)?.run {
                    solidProperty.harvestTool
                        ?.let { getOrCreateBuilder(map, it) }
                        ?.let { registerTag(it, Registry.BLOCK, this) }
                    solidProperty.getHarvestLevelTag()
                        ?.let { getOrCreateBuilder(map, it) }
                        ?.let { registerTag(it, Registry.BLOCK, this) }
                }
            }
        }
        HTMaterialsCommon.LOGGER.info("Registered Mining Tool & Harvest Level Tags!")
    }*/

    @JvmStatic
    fun fluidTags(map: MutableMap<Identifier, Tag.Builder>) {
        //Register Tags from HTFluidManager
        HTFluidManager.getMaterialToFluidsMap().forEach { material: HTMaterialNew, fluid: Fluid ->
            registerTag(
                getOrCreateBuilder(map, commonId(material.getName())),
                Registry.FLUID,
                fluid
            )
        }
    }

    @JvmStatic
    fun itemTags(map: MutableMap<Identifier, Tag.Builder>) {
        //Register Tags from HTPartManager
        HTPartManager.getPartToItemTable()
            .forEach { (material: HTMaterialNew, shape: HTShape, items: Collection<Item>) ->
            items.forEach { item: Item ->
                registerTag(
                    getOrCreateBuilder(map, shape.getCommonTag(material).id),
                    Registry.ITEM,
                    item
                )
            }
        }
        HTMixinLogger.INSTANCE.info("Registered Tags for HTPartManager's Entries!")
        //Sync ForgeTag and CommonTag entries
        HTMaterialNew.REGISTRY.forEach { material ->
            HTShape.REGISTRY.forEach shape@{ shape ->
                val forgeBuilder: Tag.Builder = getOrCreateBuilder(map, shape.getForgeTag(material))
                val commonBuilder: Tag.Builder = getOrCreateBuilder(map, shape.getCommonTag(material))
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