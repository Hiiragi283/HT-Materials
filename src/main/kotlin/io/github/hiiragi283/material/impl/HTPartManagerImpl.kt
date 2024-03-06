package io.github.hiiragi283.material.impl

import com.google.common.collect.ImmutableMap
import com.google.common.collect.ImmutableMultimap
import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.extension.allModsId
import io.github.hiiragi283.api.extension.nonAirOrNull
import io.github.hiiragi283.api.extension.runTryAndCatch
import io.github.hiiragi283.api.extension.values
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.part.HTPart
import io.github.hiiragi283.api.part.HTPartManager
import io.github.hiiragi283.api.shape.HTShape
import io.github.hiiragi283.material.HTMaterials
import net.fabricmc.fabric.api.resource.ResourceReloadListenerKeys
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.resource.ResourceManager
import net.minecraft.util.Identifier

object HTPartManagerImpl : HTPartManager, SimpleSynchronousResourceReloadListener {
    //    HTPartManager    //

    override var itemToEntryMap: ImmutableMap<Item, HTPartManager.Entry> = ImmutableMap.of()
        private set

    override var partToEntriesMap: ImmutableMultimap<HTPart, HTPartManager.Entry> = ImmutableMultimap.of()
        private set

    //    SimpleSynchronousResourceReloadListener    //

    override fun reload(manager: ResourceManager?) {
        HTPartManager.Builder().run {
            // Reload from Tags
            HTMaterialsAPI.INSTANCE.materialRegistry().keys.forEach { materialKey: HTMaterialKey ->
                HTMaterialsAPI.INSTANCE.shapeRegistry().values.forEach { shapeKey: HTShape ->
                    HTPart(materialKey, shapeKey).getPartTag().values(Registries.ITEM).forEach { item: Item ->
                        runTryAndCatch { add(materialKey, shapeKey, item) }
                    }
                }
            }
            // Reload from Items
            allModsId.forEach { modid: String ->
                HTMaterialsAPI.INSTANCE.materialRegistry().keys.forEach { materialKey: HTMaterialKey ->
                    HTMaterialsAPI.INSTANCE.shapeRegistry().values.forEach { shapeKey: HTShape ->
                        Registries.ITEM.get(shapeKey.getId(materialKey, modid)).nonAirOrNull()?.run {
                            runTryAndCatch { add(materialKey, shapeKey, this) }
                        }
                    }
                }
            }
            // Reload from Addons
            HTMaterials.addons.forEach { runTryAndCatch { it.modifyPartManager(this) } }
            // Initialize
            this@HTPartManagerImpl.itemToEntryMap = this@run.itemToEntryMap
            this@HTPartManagerImpl.partToEntriesMap = this@run.partToEntriesMap
        }
        HTMaterialsAPI.log("HTPartManager initialized!")
    }

    override fun getFabricId(): Identifier = HTMaterialsAPI.id("part_manager")

    override fun getFabricDependencies(): Collection<Identifier> = listOf(
        HTShapeRegistryImpl.fabricId,
        HTMaterialRegistryImpl.fabricId,
        ResourceReloadListenerKeys.TAGS,
    )
}
