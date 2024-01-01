package io.github.hiiragi283.material.compat

import io.github.hiiragi283.material.api.HTMaterialsAddon
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.registry.HTDefaultedTable
import io.github.hiiragi283.material.api.registry.HTObjectKeySet
import io.github.hiiragi283.material.api.shape.HTShape
import io.github.hiiragi283.material.api.shape.HTShapeKey
import io.github.hiiragi283.material.util.isAir
import net.minecraft.item.ItemConvertible
import net.minecraft.util.registry.Registry

@Suppress("unused")
object HMMIAddon : HTMaterialsAddon {

    override val modId: String = "modern_industrialization"

    override val priority: Int = 0

    override fun registerShape(registry: HTObjectKeySet<HTShapeKey>) {
        listOf(
            "blade",
            "bolt",
            "crushed_dust",
            "curved_plate",
            "double_ingot",
            "drill_head",
            "hot_ingot",
            "large_plate",
            "ring",
            "rotor",
            "tiny_dust",
            "wire"
        ).map(::HTShapeKey).forEach(registry::add)
    }

    override fun bindItemToPart(registry: HTDefaultedTable<HTMaterialKey, HTShapeKey, MutableCollection<ItemConvertible>>) {
        //Register Tags for ALL MI Material Items
        HTMaterial.REGISTRY.keys.forEach { material: HTMaterialKey ->
            HTShape.REGISTRY.keys.forEach { shape ->
                Registry.ITEM.get(shape.getIdentifier(material, modId)).run {
                    if (!this.isAir()) {
                        registry.getOrCreate(material, shape).add(this)
                    }
                }
            }
        }
    }

}