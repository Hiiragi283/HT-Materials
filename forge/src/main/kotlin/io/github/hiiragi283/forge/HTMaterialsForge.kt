package io.github.hiiragi283.forge

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.HTPlatformHelper
import io.github.hiiragi283.api.material.HTMaterialRegistry
import io.github.hiiragi283.api.shape.HTShapeRegistry
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.util.Rarity
import net.minecraftforge.client.event.ColorHandlerEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.server.ServerAboutToStartEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import thedarkcolour.kotlinforforge.forge.MOD_BUS

@Mod(value = HTMaterialsAPI.MOD_ID)
object HTMaterialsForge {
    init {
        HTMaterialsAPIForge.itemGroup = object : ItemGroup("${HTMaterialsAPI.MOD_ID}.material") {
            override fun createIcon(): ItemStack = HTMaterialsAPI.INSTANCE.iconItem().defaultStack
        }
        HTMaterialsAPIForge.iconItem = HTPlatformHelper.INSTANCE.registerItem(
            "icon",
            Item(Item.Settings().group(HTMaterialsAPI.INSTANCE.itemGroup()).rarity(Rarity.EPIC)),
        )

        HTMaterialsCoreForge.initAddons()
        HTMaterialsAPIForge.shapeRegistry = HTShapeRegistry(HTMaterialsCoreForge.createShapeMap())
        HTMaterialsAPIForge.materialRegistry = HTMaterialRegistry(HTMaterialsCoreForge.createMaterialMap())
        HTMaterialsCoreForge.verifyMaterial()
        HTMaterialsCoreForge.initContents()

        MOD_BUS.run {
            addListener(::commonSetup)
            addListener(::blockColor)
            addListener(::itemColor)
        }

        MinecraftForge.EVENT_BUS.register(::aboutToServerStart)
    }

    private fun commonSetup(event: FMLCommonSetupEvent) {
        HTMaterialsCoreForge.postInitialize(HTPlatformHelper.INSTANCE.getSide())
    }

    private fun blockColor(event: ColorHandlerEvent.Block) {
        HTPlatformHelperForge.blockColors = event.blockColors
    }

    private fun itemColor(event: ColorHandlerEvent.Item) {
        HTPlatformHelperForge.itemColors = event.itemColors
    }

    @JvmStatic
    private fun aboutToServerStart(event: ServerAboutToStartEvent) {
        HTMaterialsCoreForge.registerRecipes()
    }
}
