package io.github.hiiragi283.material.addon

import io.github.hiiragi283.material.api.addon.HTMaterialsAddon
import io.github.hiiragi283.material.api.material.HTMaterialNew
import io.github.hiiragi283.material.api.part.HTPartManager
import io.github.hiiragi283.material.api.shape.HTShape
import io.github.hiiragi283.material.util.isAir
import net.minecraft.util.registry.Registry

object HMMIAddon : HTMaterialsAddon {

    override val modId: String = "modern_industrialization"

    override fun commonSetup() {
        //Register Tags for ALL MI Material Items
        HTMaterialNew.REGISTRY.forEach { material ->
            HTShape.REGISTRY.forEach { shape ->
                Registry.ITEM.get(shape.getIdentifier(material, modId)).run {
                    if (!this.isAir()) {
                        HTPartManager.register(material, shape, this)
                    }
                }
            }
        }
    }

}