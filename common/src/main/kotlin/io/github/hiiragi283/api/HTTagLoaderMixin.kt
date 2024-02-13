package io.github.hiiragi283.api

import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.material.content.HTMaterialContent
import io.github.hiiragi283.api.part.HTPart
import io.github.hiiragi283.mixin.TagBuilderAccessor
import net.minecraft.block.Block
import net.minecraft.fluid.Fluid
import net.minecraft.tag.Tag
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

internal object HTTagLoaderMixin {
    private val Tag.Builder.entries: MutableList<Tag.TrackedEntry>
        get() = (this as TagBuilderAccessor).entries

    @JvmStatic
    fun loadTags(map: MutableMap<Identifier, Tag.Builder>, registry: Registry<*>?) {
        val registryName: Identifier? = registry?.key?.value

        fun removeEmptyBuilder() {
            HashMap(map).forEach { (id: Identifier, builder: Tag.Builder) ->
                if (builder.entries.isEmpty()) {
                    map.remove(id)
                }
            }
            HTMaterialsAPI.log("Removed empty tag builders from registry: $registryName!")
        }
        when (registry) {
            Registry.BLOCK -> {
                HTMaterialsAPI.log("Current registry: $registryName")
                blockTags(map)
                removeEmptyBuilder()
            }

            Registry.FLUID -> {
                HTMaterialsAPI.log("Current registry: $registryName")
                fluidTags(map)
                removeEmptyBuilder()
            }

            Registry.ITEM -> {
                HTMaterialsAPI.log("Current registry: $registryName")
                itemTags(map)
                removeEmptyBuilder()
            }
            else -> {}
        }
    }

    @JvmStatic
    private fun blockTags(map: MutableMap<Identifier, Tag.Builder>) {
        // Register Harvest Level and Tool for Material Block
        HTMaterialsAPI.INSTANCE.materialRegistry().getValues()
            .map { it.getContents(Block::class.java) }
            .filterIsInstance<HTMaterialContent.Block>()
            .forEach { content ->
                // Harvest Tool
                content.toolTag?.get()?.id?.run {
                    registerTag(
                        getOrCreateBuilder(map, this),
                        Registry.BLOCK,
                        content.block,
                    )
                }
                // Harvest Level
                registerTag(
                    getOrCreateBuilder(map, HTPlatformHelper.INSTANCE.getMiningLevelTag(content.toolLevel).id),
                    Registry.BLOCK,
                    content.block,
                )
            }
        HTMaterialsAPI.log("Registered harvest tool & level tags!")
    }

    @JvmStatic
    private fun fluidTags(map: MutableMap<Identifier, Tag.Builder>) {
        // Register Tags from HTFluidManagerOld
        HTMaterialsAPI.INSTANCE.fluidManager().materialToFluidsMap.forEach { key: HTMaterialKey, fluid: Fluid ->
            registerTag(
                getOrCreateBuilder(map, key.getCommonId()),
                Registry.FLUID,
                fluid,
            )
        }
    }

    @JvmStatic
    private fun itemTags(map: MutableMap<Identifier, Tag.Builder>) {
        // Convert tags into part format
        HashMap(map).forEach { (id: Identifier, builder: Tag.Builder) ->
            HTPart.fromId(id)?.getPartId()?.let { partId: Identifier ->
                // HTMaterialsAPI.log("======")
                // merge old builder entries to part id builder's, removing duplicate entries
                map.computeIfAbsent(partId) { Tag.Builder.create() }.apply {
                    builder.entries.forEach(::add)
                    // entries.onEach { HTMaterialsAPI.log("Current Entry; $it") }
                }
                // remove original id
                map.remove(id)
                HTMaterialsAPI.log("Migrated tag builder: $id -> $partId")
            }
        }
        HTMaterialsAPI.log("Converted existing tags!")
        // Register Tags from HTPartManager
        HTMaterialsAPI.INSTANCE.partManager().getAllEntries().forEach { entry ->
            // Part tag
            registerTag(
                getOrCreateBuilder(map, entry.getPart().getPartId()),
                Registry.ITEM,
                entry.item,
            )
        }
        HTMaterialsAPI.log("Registered Tags for HTPartManager's Entries!")
    }

    @JvmStatic
    private fun getOrCreateBuilder(map: MutableMap<Identifier, Tag.Builder>, id: Identifier) =
        map.computeIfAbsent(id) { Tag.Builder.create() }

    @JvmStatic
    private fun <T> registerTag(builder: Tag.Builder, registry: Registry<T>, value: T) {
        builder.add(registry.getId(value), HTMaterialsAPI.MOD_NAME)
    }
}
