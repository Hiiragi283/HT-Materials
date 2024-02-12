package io.github.hiiragi283.forge

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.HTPlatformHelper
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.client.color.block.BlockColorProvider
import net.minecraft.client.color.block.BlockColors
import net.minecraft.client.color.item.ItemColorProvider
import net.minecraft.client.color.item.ItemColors
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.Fluids
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.tag.BlockTags
import net.minecraft.tag.TagKey
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.common.Tags
import net.minecraftforge.fml.ModList
import net.minecraftforge.fml.loading.FMLLoader
import net.minecraftforge.forgespi.language.IModInfo
import net.minecraftforge.registries.ForgeRegistries

class HTPlatformHelperForge : HTPlatformHelper {
    companion object {
        internal lateinit var blockColors: BlockColors
        internal lateinit var itemColors: ItemColors
    }

    override fun getAllModId(): Collection<String> = ModList.get().mods.map(IModInfo::getModId)

    override fun isDevelop(): Boolean = !FMLLoader.isProduction()

    override fun isModLoaded(id: String): Boolean = ModList.get().isLoaded(id)

    override fun getSide(): HTPlatformHelper.Side = when (FMLLoader.getDist()) {
        Dist.CLIENT -> HTPlatformHelper.Side.CLIENT
        Dist.DEDICATED_SERVER -> HTPlatformHelper.Side.SERVER
        null -> throw IllegalStateException("")
    }

    override fun getLoaderType(): HTPlatformHelper.Loader = HTPlatformHelper.Loader.FORGE

    override fun getBlock(id: Identifier): Block = ForgeRegistries.BLOCKS.getValue(id) ?: Blocks.AIR

    override fun getFluid(id: Identifier): Fluid = ForgeRegistries.FLUIDS.getValue(id) ?: Fluids.EMPTY

    override fun getItem(id: Identifier): Item = ForgeRegistries.ITEMS.getValue(id) ?: Items.AIR

    override fun <T : Block> registerBlock(id: String, block: T): T = block
        .apply { setRegistryName(HTMaterialsAPI.MOD_ID, id) }
        .also(ForgeRegistries.BLOCKS::register)

    override fun <T : Fluid> registerFluid(id: String, fluid: T): T = fluid
        .apply { setRegistryName(HTMaterialsAPI.MOD_ID, id) }
        .also(ForgeRegistries.FLUIDS::register)

    override fun <T : Item> registerItem(id: String, item: T): T = item
        .apply { setRegistryName(HTMaterialsAPI.MOD_ID, id) }
        .also(ForgeRegistries.ITEMS::register)

    override fun registerBlockColor(provider: BlockColorProvider, block: Block) {
        blockColors.registerColorProvider(provider, block)
    }

    override fun registerItemColor(provider: ItemColorProvider, item: Item) {
        itemColors.register(provider, item)
    }

    override fun getMiningLevelTag(level: Int): TagKey<Block> = when {
        level == 0 -> Tags.Blocks.NEEDS_WOOD_TOOL
        level == 1 -> BlockTags.NEEDS_STONE_TOOL
        level == 2 -> BlockTags.NEEDS_IRON_TOOL
        level == 3 -> BlockTags.NEEDS_DIAMOND_TOOL
        level == 4 -> Tags.Blocks.NEEDS_NETHERITE_TOOL
        level > 5 -> TagKey.of(Registry.BLOCK_KEY, Identifier("forge", "needs_tool_level_$level"))
        else -> throw IllegalStateException()
    }
}
