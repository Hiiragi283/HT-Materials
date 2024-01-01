package io.github.hiiragi283.material

import io.github.hiiragi283.material.api.fluid.HTFluidManager
import io.github.hiiragi283.material.api.fluid.HTMaterialFluid
import io.github.hiiragi283.material.api.item.HTMaterialItem
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.part.HTPartManager
import io.github.hiiragi283.material.util.getTransaction
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
import net.minecraft.client.color.item.ItemColorProvider
import net.minecraft.client.render.RenderLayer
import net.minecraft.fluid.Fluid
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Suppress("unused", "UnstableApiUsage")
@Environment(EnvType.CLIENT)
object HTMaterialsClient : ClientModInitializer {

    private val LOGGER: Logger = LoggerFactory.getLogger("${HTMaterialsCommon.MOD_NAME}/Client")

    override fun onInitializeClient() {

        //Register Render Handler for Material Fluid
        registerFluidRenderHandler()
        LOGGER.info("Material Fluid Renderer Registered!")

        //Register Item Color Provider
        registerItemColorProvider()
        LOGGER.info("Item Color Provider Registered!")

        //Register Client Events
        registerEvents()
        LOGGER.info("Client Events Registered!")

    }

    private fun registerFluidRenderHandler() {
        HTMaterial.REGISTRY.forEach { (key: HTMaterialKey, material: HTMaterial) ->
            val fluid: HTMaterialFluid = HTMaterialFluid.getFluid(key) ?: return@forEach
            val flowing: Fluid = fluid.flowing
            val still: Fluid = fluid.still
            //Register Fluid Renderer
            FluidRenderHandlerRegistry.INSTANCE.register(
                still, flowing, SimpleFluidRenderHandler(
                    Identifier("minecraft:block/white_concrete"),
                    Identifier("minecraft:block/white_concrete"),
                    material.info.color.rgb
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
                val color: Int = item.materialKey.getMaterial().info.color.rgb
                ColorProviderRegistry.ITEM.register(
                    ItemColorProvider { _, tintIndex: Int -> if (tintIndex == 0) color else -1 },
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

            HTPartManager.getPart(stack.item)?.let {
                HTMaterial.appendTooltip(it.getMaterial(), it.getShape(), stack, lines)
            }

            FluidStorage.ITEM.find(stack, ContainerItemContext.withInitial(stack))
                ?.iterable(getTransaction())
                ?.map(StorageView<FluidVariant>::getResource)
                ?.map(FluidVariant::getFluid)
                ?.mapNotNull(HTFluidManager::getMaterialKey)
                ?.forEach { HTMaterial.appendTooltip(it.getMaterial(), null, stack, lines) }

        }

    }

}