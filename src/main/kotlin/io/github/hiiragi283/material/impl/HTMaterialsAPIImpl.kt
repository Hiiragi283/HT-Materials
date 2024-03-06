package io.github.hiiragi283.material.impl

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.fluid.HTFluidManager
import io.github.hiiragi283.api.material.HTMaterialRegistry
import io.github.hiiragi283.api.part.HTPartManager
import io.github.hiiragi283.api.shape.HTShapeRegistry
import io.github.hiiragi283.material.dictionary.MaterialDictionaryItem
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.text.Text
import net.minecraft.util.Rarity

internal class HTMaterialsAPIImpl : HTMaterialsAPI {
    companion object {
        private val iconItem: Item = Registry.register(
            Registries.ITEM,
            HTMaterialsAPI.id("icon"),
            Item(Item.Settings().rarity(Rarity.EPIC)),
        )
        private val dictionaryItem: Item = Registry.register(
            Registries.ITEM,
            HTMaterialsAPI.id("material_dictionary"),
            MaterialDictionaryItem,
        )
        private val itemGroup: ItemGroup = FabricItemGroup.builder()
            .icon { HTMaterialsAPI.INSTANCE.iconItem().defaultStack }
            .displayName(Text.translatable("itemGroup.${HTMaterialsAPI.MOD_ID}.material"))
            .entries { _, entries: ItemGroup.Entries ->
                entries.add(HTMaterialsAPI.INSTANCE.iconItem())
                entries.add(HTMaterialsAPI.INSTANCE.dictionaryItem())
            }
            .build()
    }

    override fun shapeRegistry(): HTShapeRegistry = HTShapeRegistryImpl

    override fun materialRegistry(): HTMaterialRegistry = HTMaterialRegistryImpl

    override fun itemGroup(): ItemGroup = itemGroup

    override fun iconItem(): Item = iconItem

    override fun dictionaryItem(): Item = dictionaryItem

    override fun fluidManager(): HTFluidManager = HTFluidManagerImpl

    override fun partManager(): HTPartManager = HTPartManagerImpl
}
