package io.github.hiiragi283.material.compat.rei

import io.github.hiiragi283.material.api.fluid.HTFluidManager
import io.github.hiiragi283.material.api.material.HTMaterialNew
import io.github.hiiragi283.material.api.part.HTPartManager
import me.shedaniel.rei.api.common.category.CategoryIdentifier
import me.shedaniel.rei.api.common.display.Display
import me.shedaniel.rei.api.common.entry.EntryIngredient
import me.shedaniel.rei.api.common.entry.EntryStack
import me.shedaniel.rei.api.common.util.EntryStacks
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item

class HTMaterialDisplay(val material: HTMaterialNew) : Display {

    private val entries: EntryIngredient = EntryIngredient.of(getEntries())

    override fun getInputEntries(): MutableList<EntryIngredient> = mutableListOf(entries)

    override fun getOutputEntries(): MutableList<EntryIngredient> = mutableListOf(entries)

    override fun getCategoryIdentifier(): CategoryIdentifier<*> = HMReiPlugin.MATERIAL_ID

    fun getEntries(): Collection<EntryStack<*>> = buildList {
        addAll(getItemEntries().map(EntryStacks::of))
        addAll(getFluidEntries().map(EntryStacks::of))
    }

    fun getFluidEntries(): Collection<Fluid> = HTFluidManager.getFluids(material)

    fun getItemEntries(): Collection<Item> = HTPartManager.getPartToItemTable().row(material).values.flatten().toSet()

}