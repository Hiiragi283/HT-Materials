package io.github.hiiragi283.material.impl

import com.google.common.collect.ImmutableMap
import com.google.common.collect.ImmutableMultimap
import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.extension.allModsId
import io.github.hiiragi283.api.extension.notEmptyOrNull
import io.github.hiiragi283.api.extension.values
import io.github.hiiragi283.api.fluid.HTFluidManager
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.material.HTMaterials
import net.fabricmc.fabric.api.resource.ResourceReloadListenerKeys
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener
import net.minecraft.fluid.Fluid
import net.minecraft.registry.Registries
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.tag.TagKey
import net.minecraft.resource.ResourceManager
import net.minecraft.util.Identifier

object HTFluidManagerImpl : HTFluidManager, SimpleSynchronousResourceReloadListener {
    //    HTFluidManager    //

    override var fluidToMaterialMap: ImmutableMap<Fluid, HTFluidManager.Entry> = ImmutableMap.of()
        private set

    override var materialToFluidsMap: ImmutableMultimap<HTMaterialKey, HTFluidManager.Entry> = ImmutableMultimap.of()
        private set

    //    SimpleSynchronousResourceReloadListener    //

    override fun reload(manager: ResourceManager) {
        HTFluidManager.Builder().run {
            // Reload from Tags
            HTMaterialsAPI.INSTANCE.materialRegistry().keys.forEach { materialKey: HTMaterialKey ->
                TagKey.of(RegistryKeys.FLUID, materialKey.getCommonId()).values(Registries.FLUID).forEach { fluid ->
                    add(materialKey, fluid)
                }
            }
            // Reload from Fluids
            allModsId.forEach { modId: String ->
                HTMaterialsAPI.INSTANCE.materialRegistry().keys.forEach { materialKey: HTMaterialKey ->
                    Registries.FLUID.get(Identifier(modId, materialKey.name)).notEmptyOrNull()?.run {
                        add(materialKey, this)
                    }
                }
            }
            // Reload from Addons
            HTMaterials.addons.forEach { it.modifyFluidManager(this) }
            // Initialize
            this@HTFluidManagerImpl.fluidToMaterialMap = this@run.fluidToMaterialMap
            this@HTFluidManagerImpl.materialToFluidsMap = this@run.materialToFluidsMap
        }
        HTMaterialsAPI.log("HTFluidManager initialized!")
    }

    override fun getFabricId(): Identifier = HTMaterialsAPI.id("fluid_manager")

    override fun getFabricDependencies(): Collection<Identifier> = listOf(
        HTShapeRegistryImpl.fabricId,
        HTMaterialRegistryImpl.fabricId,
        ResourceReloadListenerKeys.TAGS,
    )
}
