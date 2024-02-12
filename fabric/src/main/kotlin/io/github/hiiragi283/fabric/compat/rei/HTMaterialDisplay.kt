package io.github.hiiragi283.fabric.compat.rei

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.material.HTMaterial
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.part.HTPartManager
import me.shedaniel.rei.api.common.category.CategoryIdentifier
import me.shedaniel.rei.api.common.display.Display
import me.shedaniel.rei.api.common.entry.EntryIngredient
import me.shedaniel.rei.api.common.entry.EntryStack
import me.shedaniel.rei.api.common.util.EntryStacks

class HTMaterialDisplay(val material: HTMaterial, val key: HTMaterialKey = material.key) : Display {


    private val entries: EntryIngredient = EntryIngredient.of(getEntries())

    override fun getInputEntries(): MutableList<EntryIngredient> = mutableListOf(entries)

    override fun getOutputEntries(): MutableList<EntryIngredient> = mutableListOf(entries)

    override fun getCategoryIdentifier(): CategoryIdentifier<*> = HMReiPlugin.MATERIAL_ID

    fun getEntries(): Collection<EntryStack<*>> = buildList {
        addAll(
            HTMaterialsAPI.INSTANCE.partManager()
                .getFilteredItems { it.materialKey == key }
                .map(HTPartManager.Entry::item)
                .map(EntryStacks::of)
        )
        addAll(HTMaterialsAPI.INSTANCE.fluidManager().getFluids(key).map(EntryStacks::of))
    }

}