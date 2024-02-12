package io.github.hiiragi283.fabric.compat.rei

import dev.architectury.fluid.FluidStack
import io.github.hiiragi283.api.HTMaterialsAPI
import me.shedaniel.rei.api.client.entry.renderer.EntryRendererRegistry
import me.shedaniel.rei.api.client.gui.widgets.Tooltip
import me.shedaniel.rei.api.client.plugins.REIClientPlugin
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry
import me.shedaniel.rei.api.client.registry.entry.EntryRegistry
import me.shedaniel.rei.api.common.category.CategoryIdentifier
import me.shedaniel.rei.api.common.entry.EntryStack
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.item.ItemStack
import net.minecraft.text.Text

@Environment(EnvType.CLIENT)
object HMReiPlugin : REIClientPlugin {
    val MATERIAL_ID: CategoryIdentifier<HTMaterialDisplay> = CategoryIdentifier.of(HTMaterialsAPI.id("material"))

    @Suppress("UnstableApiUsage")
    override fun registerEntryRenderers(registry: EntryRendererRegistry) {
        registry.transformTooltip(VanillaEntryTypes.FLUID) { fluidStack: EntryStack<FluidStack>, _, tooltip: Tooltip? ->
            HTMaterialsAPI.INSTANCE.fluidManager().getMaterialKey(fluidStack.value.fluid)?.getMaterial()?.run {
                val tooltipDummy: MutableList<Text> = mutableListOf()
                appendTooltip(null, ItemStack.EMPTY, tooltipDummy)
                tooltip?.addAllTexts(tooltipDummy)
            }
            return@transformTooltip tooltip
        }
    }

    override fun registerCategories(registry: CategoryRegistry) {
        registry.add(HTMaterialCategory)
    }

    override fun registerDisplays(registry: DisplayRegistry) {
        HTMaterialsAPI.INSTANCE.materialRegistry()
            .getValues()
            .map(::HTMaterialDisplay)
            .filterNot { it.getEntries().isEmpty() }
            .forEach(registry::add)
    }

    override fun registerEntries(registry: EntryRegistry) {
    }
}
