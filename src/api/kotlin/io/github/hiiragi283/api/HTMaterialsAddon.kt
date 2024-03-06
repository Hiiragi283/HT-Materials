package io.github.hiiragi283.api

import io.github.hiiragi283.api.fluid.HTFluidManager
import io.github.hiiragi283.api.material.HTMaterialRegistry
import io.github.hiiragi283.api.part.HTPartManager
import io.github.hiiragi283.api.shape.HTShapeRegistry
import net.fabricmc.api.EnvType
import net.minecraft.util.Identifier

@JvmDefaultWithCompatibility
interface HTMaterialsAddon {
    val modId: String

    fun id(path: String) = Identifier(modId, path)

    val priority: Int

    fun postInitialize(envType: EnvType) {}

    fun modifyMaterialRegistry(builder: HTMaterialRegistry.Builder) {}

    fun modifyShapeRegistry(builder: HTShapeRegistry.Builder) {}

    fun modifyFluidManager(builder: HTFluidManager.Builder) {}

    fun modifyPartManager(builder: HTPartManager.Builder) {}
}
