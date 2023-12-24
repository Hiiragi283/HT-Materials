package io.github.hiiragi283.material.compat.rei

import dev.architectury.fluid.FluidStack
import io.github.hiiragi283.material.HTMaterialsCommon
import io.github.hiiragi283.material.api.fluid.HTFluidManager
import io.github.hiiragi283.material.api.material.HTMaterial
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
@Suppress("UnstableApiUsage")
object HMReiPlugin : REIClientPlugin {

    val MATERIAL_ID: CategoryIdentifier<HTMaterialDisplay> = CategoryIdentifier.of(HTMaterialsCommon.id("material"))

    override fun registerEntryRenderers(registry: EntryRendererRegistry) {
        registry.transformTooltip(VanillaEntryTypes.FLUID) { fluidStack: EntryStack<FluidStack>, _, tooltip: Tooltip? ->
            HTFluidManager.getMaterialKey(fluidStack.value.fluid)?.getMaterial()?.run {
                val tooltipDummy: MutableList<Text> = mutableListOf()
                this.appendFluidTooltip(ItemStack.EMPTY, tooltipDummy)
                tooltip?.addAllTexts(tooltipDummy)
            }
            return@transformTooltip tooltip
        }
    }

    override fun registerCategories(registry: CategoryRegistry) {
        registry.add(HTMaterialCategory)
    }

    override fun registerDisplays(registry: DisplayRegistry) {
        HTMaterial.REGISTRY
            .map(::HTMaterialDisplay)
            .filterNot { it.getEntries().isEmpty() }
            .forEach(registry::add)
    }

    override fun registerEntries(registry: EntryRegistry) {

    }

}