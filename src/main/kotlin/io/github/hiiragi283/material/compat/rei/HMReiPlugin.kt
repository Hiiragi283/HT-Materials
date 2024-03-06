package io.github.hiiragi283.material.compat.rei

import dev.architectury.fluid.FluidStack
import io.github.hiiragi283.api.HTMaterialsAPI
import me.shedaniel.rei.api.client.entry.renderer.EntryRendererRegistry
import me.shedaniel.rei.api.client.gui.widgets.Tooltip
import me.shedaniel.rei.api.client.plugins.REIClientPlugin
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry
import me.shedaniel.rei.api.common.category.CategoryIdentifier
import me.shedaniel.rei.api.common.entry.EntryStack
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.item.ItemStack
import net.minecraft.text.Text

@Suppress("UnstableApiUsage")
@Environment(EnvType.CLIENT)
object HMReiPlugin : REIClientPlugin {
    val MATERIAL: CategoryIdentifier<HTMaterialDisplay> = CategoryIdentifier.of(HTMaterialsAPI.id("material"))

    override fun registerEntryRenderers(registry: EntryRendererRegistry) {
        registry.transformTooltip(VanillaEntryTypes.FLUID) { fluidStack: EntryStack<FluidStack>, _, tooltip: Tooltip? ->
            HTMaterialsAPI.INSTANCE.fluidManager()
                .getEntry(fluidStack.value.fluid)
                ?.materialKey
                ?.getMaterialOrNull()
                ?.run {
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
        HTMaterialsAPI.INSTANCE.materialRegistry().values
            .map(::HTMaterialDisplay)
            .filterNot { it.entries.isEmpty() }
            .forEach(registry::add)
    }
}
