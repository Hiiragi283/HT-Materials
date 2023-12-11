package io.github.hiiragi283.material.client

import io.github.hiiragi283.material.api.fluid.HTFluidManager
import io.github.hiiragi283.material.api.fluid.HTMaterialFluid
import io.github.hiiragi283.material.api.item.HTMaterialBlockItem
import io.github.hiiragi283.material.api.item.HTMaterialItem
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.part.HTPart
import io.github.hiiragi283.material.api.part.HTPartManager
import io.github.hiiragi283.material.api.shape.HTShapes
import io.github.hiiragi283.material.common.HTMaterialsCommon
import io.github.hiiragi283.material.common.util.getTransaction
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView
import net.minecraft.client.color.block.BlockColorProvider
import net.minecraft.client.color.item.ItemColorProvider
import net.minecraft.client.render.RenderLayer
import net.minecraft.fluid.Fluid
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.util.Identifier

@Suppress("UnstableApiUsage")
@Environment(EnvType.CLIENT)
object HTMaterialsClient : ClientModInitializer {

    override fun onInitializeClient() {

        //Register Default Model Consumer
        HTMaterialModelManager

        //Register Block Color Provider
        registerBlockColorProvider()
        HTMaterialsCommon.LOGGER.info("Block Color Provider Registered!")

        //Register Render Handler for Material Fluid
        registerFluidRenderHandler()
        HTMaterialsCommon.LOGGER.info("Material Fluid Renderer Registered!")

        //Register Item Color Provider
        registerItemColorProvider()
        HTMaterialsCommon.LOGGER.info("Item Color Provider Registered!")

        //Register Client Events
        registerEvents()
        HTMaterialsCommon.LOGGER.info("Client Events Registered!")

    }

    private fun registerBlockColorProvider() {
        //Material Blocks
        HTPartManager.getDefaultItemTable().values()
            .filterIsInstance<HTMaterialBlockItem>()
            .forEach { item: HTMaterialBlockItem ->
                ColorProviderRegistry.BLOCK.register(
                    BlockColorProvider { _, _, _, tintIndex: Int -> if (tintIndex == 0) item.materialHT.asColor().rgb else -1 },
                    item.block
                )
                ColorProviderRegistry.ITEM.register(
                    ItemColorProvider { _, tintIndex: Int -> if (tintIndex == 0) item.materialHT.asColor().rgb else -1 },
                    item
                )
            }
    }

    private fun registerFluidRenderHandler() {
        HTMaterial.REGISTRY.forEach { material: HTMaterial ->
            val fluid: HTMaterialFluid = HTMaterialFluid.getFluid(material) ?: return@forEach
            val flowing: Fluid = fluid.flowing
            val still: Fluid = fluid.still
            //Register Fluid Model
            FluidRenderHandlerRegistry.INSTANCE.register(
                still, flowing, SimpleFluidRenderHandler(
                    Identifier("minecraft:block/white_concrete"),
                    Identifier("minecraft:block/white_concrete"),
                    material.asColor().rgb
                )
            )
            //Register Translucent Layer
            BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), still, flowing)
        }
    }

    private fun registerItemColorProvider() {
        //Material Items
        HTPartManager.getDefaultItemTable().values()
            .filterIsInstance<HTMaterialItem>()
            .forEach { item: HTMaterialItem ->
                ColorProviderRegistry.ITEM.register(
                    ItemColorProvider { _, tintIndex: Int -> if (tintIndex == 0) item.materialHT.asColor().rgb else -1 },
                    item
                )
            }
        //Material Fluid Bucket
        HTFluidManager.getDefaultFluidMap().values.filterIsInstance<HTMaterialFluid.Still>().forEach { fluid ->
            val color: Int = FluidRenderHandlerRegistry.INSTANCE.get(fluid)
                ?.getFluidColor(null, null, fluid.defaultState) ?: -1
            ColorProviderRegistry.ITEM.register(
                ItemColorProvider { _: ItemStack, tintIndex: Int -> if (tintIndex == 1) color else -1 },
                fluid.bucketItem
            )
        }
    }

    private fun registerEvents() {

        ItemTooltipCallback.EVENT.register { stack: ItemStack, _, lines: MutableList<Text> ->

            HTPartManager.getPart(stack.item)?.appendTooltip(stack, lines)

            FluidStorage.ITEM.find(stack, ContainerItemContext.withInitial(stack))
                ?.iterable(getTransaction())
                ?.map(StorageView<FluidVariant>::getResource)
                ?.map(FluidVariant::getFluid)
                ?.mapNotNull(HTFluidManager::getMaterial)
                ?.map { HTPart(it, HTShapes.FLUID) }
                ?.forEach { it.appendTooltip(stack, lines) }

        }

    }

}