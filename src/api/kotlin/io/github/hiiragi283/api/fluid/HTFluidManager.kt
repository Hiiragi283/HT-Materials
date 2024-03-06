package io.github.hiiragi283.api.fluid

import com.google.common.collect.ImmutableMap
import com.google.common.collect.ImmutableMultimap
import com.google.common.collect.LinkedHashMultimap
import com.google.common.collect.Multimap
import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.extension.asFlowableOrNull
import io.github.hiiragi283.api.extension.id
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.material.HTMaterialKeys
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.Fluids

interface HTFluidManager {
    // Fluid -> Entry

    val fluidToMaterialMap: ImmutableMap<Fluid, Entry>

    fun getEntry(fluid: Fluid): Entry? = fluidToMaterialMap[fluid]

    fun hasEntry(fluid: Fluid): Boolean = fluid in fluidToMaterialMap

    // HTMaterialKey -> Entry

    fun getDefaultEntry(materialKey: HTMaterialKey): Entry? {
        val entries: Collection<Entry> = getEntries(materialKey)
        for (entry in entries) {
            val namespace = entry.fluid.id.namespace
            return when (namespace) {
                "minecraft" -> entry
                HTMaterialsAPI.MOD_ID -> entry
                else -> continue
            }
        }
        return entries.firstOrNull()
    }

    // HTMaterialKey -> Collection<Entry>

    val materialToFluidsMap: ImmutableMultimap<HTMaterialKey, Entry>

    fun getEntries(materialKey: HTMaterialKey): Collection<Entry> = materialToFluidsMap.get(materialKey)

    fun getFluids(materialKey: HTMaterialKey): Collection<Fluid> = getEntries(materialKey).map(Entry::fluid)

    fun hasEntry(materialKey: HTMaterialKey): Boolean = materialToFluidsMap.containsKey(materialKey)

    //    Entry    //

    data class Entry(val materialKey: HTMaterialKey, val fluid: Fluid)

    //    Builder    //

    class Builder {
        val fluidToMaterialMap: ImmutableMap<Fluid, Entry>
            get() = ImmutableMap.copyOf(fluidToMaterialMap1)
        private val fluidToMaterialMap1: MutableMap<Fluid, Entry> = mutableMapOf()

        val materialToFluidsMap: ImmutableMultimap<HTMaterialKey, Entry>
            get() = ImmutableMultimap.copyOf(materialToFluidMap1)
        private val materialToFluidMap1: Multimap<HTMaterialKey, Entry> = LinkedHashMultimap.create()

        fun add(materialKey: HTMaterialKey, fluid: Fluid) {
            fluid.asFlowableOrNull()?.still?.run {
                val entry = Entry(materialKey, this)
                fluidToMaterialMap1[this] = entry
                materialToFluidMap1.put(materialKey, entry)
            }
        }

        fun remove(materialKey: HTMaterialKey, fluid: Fluid) {
            fluid.asFlowableOrNull()?.still?.run {
                val entry = Entry(materialKey, this)
                if (fluidToMaterialMap1.contains(fluid) && materialToFluidMap1.containsKey(materialKey)) {
                    fluidToMaterialMap1.remove(fluid, entry)
                    materialToFluidMap1.remove(materialKey, entry)
                }
            }
        }

        init {
            add(HTMaterialKeys.WATER, Fluids.WATER)
            add(HTMaterialKeys.LAPIS, Fluids.LAVA)
        }
    }
}
