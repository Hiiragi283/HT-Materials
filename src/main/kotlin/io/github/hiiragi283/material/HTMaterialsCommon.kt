package io.github.hiiragi283.material

import io.github.hiiragi283.material.api.addon.HTMaterialsAddonManager
import io.github.hiiragi283.material.api.item.HTMaterialItem
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.material.property.HTPropertyKey
import io.github.hiiragi283.material.api.part.HTPartManager
import io.github.hiiragi283.material.api.shape.HTShape
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.Items
import net.minecraft.util.Identifier
import net.minecraft.util.Rarity
import net.minecraft.util.registry.Registry
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object HTMaterialsCommon : ModInitializer {

    const val MOD_ID: String = "ht_materials"
    const val MOD_NAME: String = "HT Materials"

    private val LOGGER: Logger = LoggerFactory.getLogger(MOD_NAME)

    @get:JvmName("ITEM_GROUP")
    val ITEM_GROUP: ItemGroup by lazy {
        FabricItemGroupBuilder.create(id("material")).icon(Items.IRON_INGOT::getDefaultStack).build()
    }

    @get:JvmName("ICON")
    val ICON: Item by lazy {
        Registry.register(
            Registry.ITEM,
            id("icon"),
            Item(FabricItemSettings().group(ITEM_GROUP).rarity(Rarity.EPIC))
        )
    }

    override fun onInitialize() {
        //Bind Game Objects to HTPart
        HTMaterialsAddonManager.bindItemToPart()
        HTMaterialsAddonManager.bindFluidToPart()
        //Initialize Game Objects
        ITEM_GROUP
        ICON
        registerMaterialFluids()
        LOGGER.info("All Material Fluids Registered!")
        registerMaterialItems()
        LOGGER.info("All Material Items Registered!")
    }

    @JvmStatic
    fun id(path: String) = Identifier(MOD_ID, path)

    private fun registerMaterialFluids() {
        HTMaterial.REGISTRY.forEach { (key: HTMaterialKey, material: HTMaterial) ->
            material.getProperty(HTPropertyKey.FLUID)?.init(key)
        }
    }

    private fun registerMaterialItems() {
        HTShape.REGISTRY.forEach { shape: HTShape ->
            HTMaterial.REGISTRY
                .filter { shape.canGenerateItem(it.value) }
                .keys
                .forEach { key: HTMaterialKey ->
                    //Register Item
                    HTMaterialItem(key, shape).run {
                        Registry.register(Registry.ITEM, shape.getIdentifier(key), this)
                        //Register as Default Item
                        HTPartManager.forceRegister(key, shape, this)
                    }
                }
        }
    }

}