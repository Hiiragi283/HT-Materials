package io.github.hiiragi283.material

import io.github.hiiragi283.material.api.fluid.HTFluidManager
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.part.HTPartManager
import io.github.hiiragi283.material.api.shape.HTShape
import io.github.hiiragi283.material.api.shape.HTShapes
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
        HTFluidManager.getMaterialToFluidsMap().forEach { material: HTMaterial, fluid: Fluid ->
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
        HTPartManager.getPartToItemTable().forEach { (material: HTMaterial, shape: HTShape, items: Collection<Item>) ->
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
        HTMaterial.REGISTRY.forEach { material ->
            HTShapes.REGISTRY.forEach shape@{ shape ->
                val forgeBuilder: Tag.Builder = getBuilder(map, shape.getForgeTag(material)) ?: return@shape
                val commonBuilder: Tag.Builder = getBuilder(map, shape.getCommonTag(material)) ?: return@shape
                syncBuilder(commonBuilder, forgeBuilder)
                syncBuilder(forgeBuilder, commonBuilder)
            }
        }
        HTMixinLogger.INSTANCE.info("Synced Forge Tags and Common Tags!")
    }

    private fun getBuilder(map: MutableMap<Identifier, Tag.Builder>, tagKey: TagKey<*>): Tag.Builder? = map[tagKey.id]

    private fun getOrCreateBuilder(map: MutableMap<Identifier, Tag.Builder>, id: Identifier) =
        map.computeIfAbsent(id) { Tag.Builder.create() }

    private fun <T> registerTag(builder: Tag.Builder, registry: Registry<T>, value: T) {
        builder.add(registry.getId(value), HTMaterialsCommon.MOD_NAME)
    }

    private fun syncBuilder(parentBuilder: Tag.Builder, childBuilder: Tag.Builder) {
        (childBuilder as TagBuilderMixin).entries.forEach(parentBuilder::add)
    }

}