package io.github.hiiragi283.fabric.compat

import com.google.common.collect.ImmutableSet
import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.HTMaterialsAddon
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.part.HTPartManager
import io.github.hiiragi283.api.shape.HTShapeKey
import io.github.hiiragi283.api.util.isAir
import net.minecraft.util.registry.Registry

@Suppress("unused")
object HMMIAddon : HTMaterialsAddon {
    override val modId: String = "modern_industrialization"
    override val priority: Int = 0

    override fun registerShape(registry: ImmutableSet.Builder<HTShapeKey>) {
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
            "wire",
        ).map(::HTShapeKey).forEach(registry::add)
    }

    override fun bindItemToPart(builder: HTPartManager.Builder) {
        // Register Tags for ALL MI Material Items
        HTMaterialsAPI.INSTANCE.materialRegistry().getKeys().forEach { material: HTMaterialKey ->
            HTMaterialsAPI.INSTANCE.shapeRegistry().getValues().forEach { shape ->
                Registry.ITEM.get(shape.getIdentifier(material, modId)).run {
                    if (!this.isAir()) {
                        builder.add(material, shape.key, this)
                    }
                }
            }
        }
    }
}
