package io.github.hiiragi283.material

import io.github.hiiragi283.material.api.addon.HTMaterialsAddons
import io.github.hiiragi283.material.api.item.HTMaterialItem
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.property.HTPropertyKey
import io.github.hiiragi283.material.api.part.HTPartManager
import io.github.hiiragi283.material.api.shape.HTShape
import io.github.hiiragi283.material.api.shape.HTShapes
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

    @JvmField
    val ITEM_GROUP: ItemGroup = FabricItemGroupBuilder.create(id("material"))
        .icon(Items.IRON_INGOT::getDefaultStack)
        .build()

    @JvmField
    val ICON: Item = Registry.register(
        Registry.ITEM,
        id("icon"),
        Item(FabricItemSettings().group(ITEM_GROUP).rarity(Rarity.EPIC))
    )

    override fun onInitialize() {

        //Collect Addons
        HTMaterialsAddons

        //Register Materials and Shapes
        HTMaterialsAddons.registerShapes()
        LOGGER.info("HTShape loaded!")
        HTMaterialsAddons.registerMaterials()
        LOGGER.info("HTMaterial loaded!")

        //Modify and Verify Material Properties and Flags
        HTMaterialsAddons.modifyMaterials()
        LOGGER.info("All Materials Verified!")

        //Initialize Game Objects
        //registerMaterialBlocks()
        //LOGGER.info("All Material Blocks Registered!")
        registerMaterialFluids()
        LOGGER.info("All Material Fluids Registered!")
        registerMaterialItems()
        LOGGER.info("All Material Items Registered!")

    }

    @JvmStatic
    fun id(path: String) = Identifier(MOD_ID, path)

    /*private fun registerMaterialBlocks() {
        HTShapes.REGISTRY
            .forEach { shape: HTShape ->
            HTMaterial.REGISTRY
                .filter { it.hasProperty(HTPropertyKey.SOLID) }
                .filter(shape::canGenerateBlock)
                .forEach material@{ material: HTMaterial ->
                    val identifier: Identifier = shape.getIdentifier(material)
                    val settings: FabricBlockSettings =
                        material.getProperty(HTPropertyKey.SOLID)?.blockSettings ?: return@material
                    val block = HTMaterialBlock(material, shape, settings)
                    //Register Block
                    Registry.register(Registry.BLOCK, identifier, block)
                    //Register BlockItem
                    HTMaterialBlockItem(block).run {
                        Registry.register(Registry.ITEM, identifier, this)
                        //Register as Default Item
                        HTPartManager.forceRegister(material, shape, this)
                    }
                }
        }
    }*/

    private fun registerMaterialItems() {
        HTShapes.REGISTRY.forEach { shape: HTShape ->
            HTMaterial.REGISTRY
                .filter(shape::canGenerateItem)
                .forEach { material: HTMaterial ->
                    val identifier: Identifier = shape.getIdentifier(material)
                    //Register Item
                    HTMaterialItem(material, shape).run {
                        Registry.register(Registry.ITEM, identifier, this)
                        //Register as Default Item
                        HTPartManager.forceRegister(material, shape, this)
                    }
                }
        }
    }

    private fun registerMaterialFluids() {
        HTMaterial.REGISTRY.forEach { material -> material.getProperty(HTPropertyKey.FLUID)?.init(material) }
    }

}