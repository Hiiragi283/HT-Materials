package io.github.hiiragi283.material.addon

import io.github.hiiragi283.material.api.addon.HTMaterialsAddon
import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.material.HTMaterialNew
import io.github.hiiragi283.material.api.part.HTPartManager
import io.github.hiiragi283.material.api.shape.HTShape
import io.github.hiiragi283.material.util.isAir
import net.minecraft.util.registry.Registry

@Suppress("unused")
object HMMIAddon : HTMaterialsAddon {

    override val modId: String = "modern_industrialization"

    override val priority: Int = 0

    override fun commonSetup() {
        //Register Tags for ALL MI Material Items
        HTMaterialNew.REGISTRY.keys.forEach { key: HTMaterialKey ->
            HTShape.REGISTRY.forEach { shape ->
                Registry.ITEM.get(shape.getIdentifier(key, modId)).run {
                    if (!this.isAir()) {
                        HTPartManager.register(key, shape, this)
                    }
                }
            }
        }
    }

}