package io.github.hiiragi283.fabric

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.HTMaterialsAddon
import io.github.hiiragi283.api.HTMaterialsCore
import io.github.hiiragi283.api.HTPlatformHelper
import io.github.hiiragi283.api.fluid.HTFluidManager
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.part.HTPart
import io.github.hiiragi283.api.part.HTPartManager
import io.github.hiiragi283.api.shape.HTShapeKey
import io.github.hiiragi283.api.shape.HTShapeKeys
import io.github.hiiragi283.api.util.prefix
import io.github.hiiragi283.api.util.resource.HTRuntimeDataPack
import io.github.hiiragi283.api.util.values
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.data.server.RecipeProvider
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder
import net.minecraft.item.Item
import net.minecraft.server.MinecraftServer
import net.minecraft.server.world.ServerWorld
import net.minecraft.tag.TagKey
import net.minecraft.util.registry.Registry

internal object HTMaterialsCoreFabric : HTMaterialsCore() {
    override fun collectAddons(): Iterable<HTMaterialsAddon> = FabricLoader.getInstance()
        .getEntrypoints(HTMaterialsAPI.MOD_ID, HTMaterialsAddon::class.java)
        .filter { HTPlatformHelper.INSTANCE.isModLoaded(it.modId) }
        .sortedWith(compareBy(HTMaterialsAddon::priority).thenBy { it.javaClass.name })

    override fun bindFluidToPart() {
        HTFluidManager.Builder().run {
            addons.forEach { it.bindFluidToPart(this) }
            HTMaterialsAPIFabric.fluidManager = HTFluidManager(this)
        }
    }

    override fun bindItemToPart() {
        HTPartManager.Builder().run {
            addons.forEach { it.bindItemToPart(this) }
            HTMaterialsAPIFabric.partManager = HTPartManager(this)
        }
        ServerWorldEvents.LOAD.register(HTMaterialsCoreFabric::onWorldLoaded)
    }

    override fun ingotRecipe(partManager: HTPartManager, materialKey: HTMaterialKey, item: Item) {
        if (!partManager.hasItem(materialKey, HTShapeKeys.NUGGET)) return
        val nuggetTag: TagKey<Item> = HTPart(materialKey, HTShapeKeys.NUGGET).getPartTag()
        HTRuntimeDataPack.addRecipe { exporter ->
            ShapedRecipeJsonBuilder.create(item)
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .input('A', nuggetTag)
                .criterion("has_nugget", RecipeProvider.conditionsFromTag(nuggetTag))
                .offerTo(exporter, HTShapeKeys.INGOT.getShape().getIdentifier(materialKey).prefix("shaped/"))
        }
    }

    override fun nuggetRecipe(partManager: HTPartManager, materialKey: HTMaterialKey, item: Item) {
        if (!partManager.hasItem(materialKey, HTShapeKeys.INGOT)) return
        val ingotTag: TagKey<Item> = HTPart(materialKey, HTShapeKeys.INGOT).getPartTag()
        HTRuntimeDataPack.addRecipe { exporter ->
            ShapelessRecipeJsonBuilder.create(item, 9)
                .input(ingotTag)
                .criterion("has_ingot", RecipeProvider.conditionsFromTag(ingotTag))
                .offerTo(exporter, HTShapeKeys.NUGGET.getShape().getIdentifier(materialKey).prefix("shapeless/"))
        }
    }

    @Suppress("UNUSED_PARAMETER")
    private fun onWorldLoaded(server: MinecraftServer, world: ServerWorld) {
        // Reload Fluid Manager
        HTMaterialsAPIFabric.fluidManager = HTFluidManager.Builder().apply {
            // Register fluids from common tag
            HTMaterialsAPI.INSTANCE.materialRegistry().getKeys().forEach { materialKey: HTMaterialKey ->
                TagKey.of(Registry.FLUID_KEY, materialKey.getCommonId()).values(Registry.FLUID).forEach { fluid ->
                    add(materialKey, fluid)
                }
            }
        }.let { HTFluidManager(it) }
        // Reload Part Manager
        HTMaterialsAPIFabric.partManager = HTPartManager.Builder().apply {
            // Register items from part tag
            HTMaterialsAPI.INSTANCE.materialRegistry().getKeys().forEach { materialKey: HTMaterialKey ->
                HTMaterialsAPI.INSTANCE.shapeRegistry().getKeys().forEach { shapeKey: HTShapeKey ->
                    HTPart(materialKey, shapeKey).getPartTag().values(Registry.ITEM).forEach { item ->
                        add(materialKey, shapeKey, item)
                    }
                }
            }
        }.let(::HTPartManager)
    }
}
