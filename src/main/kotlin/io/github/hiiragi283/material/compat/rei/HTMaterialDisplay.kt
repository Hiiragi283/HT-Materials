package io.github.hiiragi283.material.compat.rei

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.material.HTMaterial
import io.github.hiiragi283.api.material.HTMaterialKey
import me.shedaniel.rei.api.common.category.CategoryIdentifier
import me.shedaniel.rei.api.common.display.Display
import me.shedaniel.rei.api.common.entry.EntryIngredient
import me.shedaniel.rei.api.common.entry.EntryStack
import me.shedaniel.rei.api.common.util.EntryStacks
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item

class HTMaterialDisplay(val material: HTMaterial) : Display {
    private val key: HTMaterialKey = material.key
    val entries: List<EntryStack<*>> = buildList {
        addAll(getItemEntries().map(EntryStacks::of))
        addAll(getFluidEntries().map(EntryStacks::of))
    }

    override fun getInputEntries(): List<EntryIngredient> = listOf(EntryIngredient.of(entries))

    override fun getOutputEntries(): List<EntryIngredient> = listOf(EntryIngredient.of(entries))

    override fun getCategoryIdentifier(): CategoryIdentifier<*> = HMReiPlugin.MATERIAL

    private fun getFluidEntries(): Collection<Fluid> = HTMaterialsAPI.INSTANCE.fluidManager()
        .getFluids(key)

    private fun getItemEntries(): Collection<Item> = HTMaterialsAPI.INSTANCE.partManager()
        .getAllEntries()
        .filter { it.materialKey == key }
        .map { it.item }
}
