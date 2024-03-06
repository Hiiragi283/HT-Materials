package io.github.hiiragi283.material

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.HTMaterialsAddon
import io.github.hiiragi283.api.extension.*
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.material.element.HTElements
import io.github.hiiragi283.api.shape.HTShape
import io.github.hiiragi283.api.tag.GlobalTagEvent
import io.github.hiiragi283.material.dictionary.MaterialDictionaryScreen
import io.github.hiiragi283.material.dictionary.MaterialDictionaryScreenHandler
import io.github.hiiragi283.material.impl.*
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.DedicatedServerModInitializer
import net.fabricmc.api.EnvType
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback
import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.resource.ResourceManagerHelper
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.gui.screen.ingame.HandledScreens
import net.minecraft.client.item.TooltipContext
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.registry.tag.TagEntry
import net.minecraft.registry.tag.TagGroupLoader
import net.minecraft.registry.tag.TagKey
import net.minecraft.resource.ResourceType
import net.minecraft.text.Text
import net.minecraft.util.Identifier

object HTMaterials : ModInitializer, ClientModInitializer, DedicatedServerModInitializer {
    //    ModInitializer    //

    internal lateinit var addons: Iterable<HTMaterialsAddon>

    private fun initAddons() {
        addons = getEntrypoints<HTMaterialsAddon>(HTMaterialsAPI.MOD_ID)
            .filter { isModLoaded(it.modId) }
            .sortedWith(compareBy(HTMaterialsAddon::priority).thenBy { it.javaClass.name })
        // Print sorted addons
        HTMaterialsAPI.log("HTMaterialsAddon collected!")
        HTMaterialsAPI.log("=== List ===")
        addons.forEach {
            HTMaterialsAPI.log("${it::class.qualifiedName} - Priority: ${it.priority}")
        }
        HTMaterialsAPI.log("============")
    }

    override fun onInitialize() {
        // Collect Addons
        initAddons()
        // Register custom registries
        HTMaterialsAPI
        HTMaterialsAPI.Registries
        HTElements
        listOf(
            HTFluidManagerImpl,
            HTMaterialRegistryImpl,
            HTPartManagerImpl,
            HTShapeRegistryImpl,
        ).forEach { ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(it) }
        HTMaterialsAPI.log("Initialized!")
    }

    //    ClientModInitializer    //

    private fun postInitialize(envType: EnvType) {
        // Register Events
        val id: Identifier = HTMaterialsAPI.id("before_default")
        GlobalTagEvent.ITEM.run {
            addPhaseOrdering(id, Event.DEFAULT_PHASE)
            register(id, ::onItemTags)
        }
        GlobalTagEvent.FLUID.run {
            addPhaseOrdering(id, Event.DEFAULT_PHASE)
            register(id, ::onFluidTags)
        }
        EnvType.CLIENT.runWhenOn {
            ItemTooltipCallback.EVENT.register(::getTooltip)
        }
        HTMaterialsAPI.log("Registered events!")

        EnvType.CLIENT.runWhenOn {
            HandledScreens.register(
                MaterialDictionaryScreenHandler.TYPE,
                ::MaterialDictionaryScreen,
            )
        }

        // Post initialize from addons
        HTMaterials.addons.forEach { it.postInitialize(envType) }
        HTMaterialsAPI.log("Post-initialize completed!")
    }

    override fun onInitializeClient() {
        postInitialize(EnvType.CLIENT)
        HTMaterialsAPI.log("Client post-initialize completed!")
    }

    //    DedicatedServerModInitializer    //

    override fun onInitializeServer() {
        postInitialize(EnvType.SERVER)
        HTMaterialsAPI.log("Server post-initialize completed!")
    }

    //    Event    //

    @Suppress("UnstableApiUsage", "UNUSED_PARAMETER")
    private fun getTooltip(stack: ItemStack, context: TooltipContext, lines: MutableList<Text>) {
        if (stack.isEmpty) return
        // Part tooltip on item
        stack.item.partEntry?.let {
            it.materialKey.getMaterial().appendTooltip(it.shapeKey, stack, lines)
        }
        // Material tooltip on fluid container item
        FluidStorage.ITEM.find(stack, ContainerItemContext.withConstant(stack))?.forEach { view ->
            view.resource
                .fluid
                .materialKey
                ?.getMaterial()?.appendTooltip(null, stack, lines)
        }
        // Tag tooltips (only dev)
        if (FabricLoader.getInstance().isDevelopmentEnvironment) {
            stack.streamTags()
                .map(TagKey<Item>::id)
                .map(Identifier::toString)
                .map(Text::of)
                .forEach(lines::add)
        }
    }

    private fun onItemTags(handler: GlobalTagEvent.Handler) {
        // Register Tags from HTPartManager
        HTMaterialsAPI.INSTANCE.partManager().getAllEntries().forEach { entry ->
            val (materialKey: HTMaterialKey, shapeKey: HTShape, item: Item) = entry
            // Shape tag
            handler.builder(shapeKey.getShapeId()).add(
                TagGroupLoader.TrackedEntry(TagEntry.create(item.id), HTMaterialsAPI.MOD_NAME),
            )
            // Material tag
            handler.builder(materialKey.getMaterialId()).add(
                TagGroupLoader.TrackedEntry(TagEntry.create(item.id), HTMaterialsAPI.MOD_NAME),
            )
            // Part tag
            handler.builder(entry.part.getPartId()).add(
                TagGroupLoader.TrackedEntry(TagEntry.create(item.id), HTMaterialsAPI.MOD_NAME),
            )
        }
        HTMaterialsAPI.log("Registered Tags for HTPartManager's Entries!")
    }

    private fun onFluidTags(handler: GlobalTagEvent.Handler) {
        // Register Tags from HTFluidManagerOld
        HTMaterialsAPI.INSTANCE.fluidManager().materialToFluidsMap.forEach { materialKey, entry ->
            handler.builder(materialKey.getCommonId()).add(
                TagGroupLoader.TrackedEntry(TagEntry.create(entry.fluid.id), HTMaterialsAPI.MOD_NAME),
            )
        }
    }
}
