package io.github.hiiragi283.material.compat

import com.simibubi.create.AllItems
import io.github.hiiragi283.material.api.HTMaterialsAddon
import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.material.materials.HTVanillaMaterials
import io.github.hiiragi283.material.api.registry.HTDefaultedTable
import io.github.hiiragi283.material.api.shape.HTShapeKey
import io.github.hiiragi283.material.api.shape.HTShapes
import net.minecraft.item.ItemConvertible

@Suppress("unused")
object HMCreateAddon : HTMaterialsAddon {

    override val modId: String = "create"

    override val priority: Int = 0

    override fun bindItemToPart(registry: HTDefaultedTable<HTMaterialKey, HTShapeKey, MutableCollection<ItemConvertible>>) {
        registry.getOrCreate(HTVanillaMaterials.NETHERRACK, HTShapes.DUST).add(AllItems.CINDER_FLOUR.get())
        registry.getOrCreate(HTVanillaMaterials.OBSIDIAN, HTShapes.DUST).add(AllItems.POWDERED_OBSIDIAN.get())
    }

}