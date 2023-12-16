package io.github.hiiragi283.material.api.addon

import io.github.hiiragi283.material.api.fluid.HTFluidManager
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.part.HTPartManager
import io.github.hiiragi283.material.api.part.HTPartManager.hasDefaultItem
import io.github.hiiragi283.material.api.shape.HTShapes
import io.github.hiiragi283.material.client.HTMaterialModelManager
import io.github.hiiragi283.material.common.HTMaterialsCommon
import io.github.hiiragi283.material.common.HTRecipeManager
import io.github.hiiragi283.material.common.util.isModLoaded
import io.github.hiiragi283.material.common.util.suffix
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder
import net.minecraft.item.Item

object HTMaterialsAddons : HTMaterialsAddon {

    private var cache: List<HTMaterialsAddon> = FabricLoader.getInstance()
        .getEntrypoints(HTMaterialsCommon.MOD_ID, HTMaterialsAddon::class.java)
        .filter { isModLoaded(it.modId) }

    //    HTMaterialsAddon    //

    override val modId: String = HTMaterialsCommon.MOD_ID

    override fun registerShapes() {
        HTShapes
        cache.forEach(HTMaterialsAddon::registerShapes)
    }

    override fun registerMaterials() {
        HTMaterial
        cache.forEach(HTMaterialsAddon::registerMaterials)
    }

    override fun modifyMaterials() {
        cache.forEach(HTMaterialsAddon::modifyMaterials)
        HTMaterial.REGISTRY.forEach(HTMaterial::verify)
        HTShapes.canModify = false
        HTMaterial.canModify = false
        HTMaterial.REGISTRY.forEach(HTMaterial::asColor)
        HTMaterial.REGISTRY.forEach(HTMaterial::asFormula)
        HTMaterial.REGISTRY.forEach(HTMaterial::asMolarMass)
    }

    //private val RESOURCE_PACK: RuntimeResourcePack = RuntimeResourcePack.create(HTMaterialsCommon.id("runtime"))

    override fun commonSetup() {
        cache.forEach(HTMaterialsAddon::commonSetup)

        //registerLootTables()
        //HTMaterialsCommon.LOGGER.info("Loot Tables Registered!")

        registerRecipes()
        HTMaterialsCommon.LOGGER.info("Recipes Registered!")

        HTFluidManager.registerAllFluids()
        HTMaterialsCommon.LOGGER.info("All Fluids Registered to HTFluidManager!")

        /*RRPCallback.BEFORE_USER.register {
            //Register Resource Pack
            it.add(RESOURCE_PACK)
            HTMaterialsCommon.LOGGER.info("Dynamic Data Pack Registered!")
        }*/
    }

    /*private fun registerLootTables() {
        HTPartManager.getDefaultItemTable().values()
            .map(Item::asBlock)
            .filterIsInstance<HTMaterialBlock>()
            .forEach { block -> RESOURCE_PACK.addLootTable(block.lootTableId, BlockLootTableGenerator.drops(block)) }
    }*/

    private fun registerRecipes() {
        HTMaterial.REGISTRY.forEach { material ->
            //materialRecipe(material)
            //HTPartManager.getDefaultItem(material, HTShapes.BLOCK)?.let { blockRecipe(material, it) }
            HTPartManager.getDefaultItem(material, HTShapes.INGOT)?.let { ingotRecipe(material, it) }
            HTPartManager.getDefaultItem(material, HTShapes.NUGGET)?.let { nuggetRecipe(material, it) }
        }
    }

    /*private fun materialRecipe(material: HTMaterial) {
        //1x Block -> 9x Ingot/Gem
        val shape: HTShape = material.getDefaultShape() ?: return
        val result: Item = HTPartManager.getDefaultItem(material, shape) ?: return
        HTRecipeManager.registerVanillaRecipe(
            shape.getIdentifier(material).suffix("_shapeless"),
            ShapelessRecipeJsonBuilder.create(result, 9)
                .input(HTShapes.BLOCK.getCommonTag(material))
                .setBypassesValidation(true)
        )
    }

    private fun blockRecipe(material: HTMaterial, item: Item) {
        //9x Ingot/Gem -> 1x Block
        val shape: HTShape = material.getDefaultShape() ?: return
        HTRecipeManager.registerVanillaRecipe(
            HTShapes.BLOCK.getIdentifier(material).suffix("_shaped"),
            ShapedRecipeJsonBuilder.create(item)
                .patterns("AAA", "AAA", "AAA")
                .input('A', shape.getCommonTag(material))
                .setBypassesValidation(true)
        )
    }*/

    private fun ingotRecipe(material: HTMaterial, item: Item) {
        //9x Nugget -> 1x Ingot
        if (!hasDefaultItem(material, HTShapes.NUGGET)) return
        HTRecipeManager.registerVanillaRecipe(
            HTShapes.INGOT.getIdentifier(material, HTMaterialsCommon.MOD_ID).suffix("_shaped"),
            ShapedRecipeJsonBuilder.create(item)
                .patterns("AAA", "AAA", "AAA")
                .input('A', HTShapes.NUGGET.getCommonTag(material))
                .setBypassesValidation(true)
        )
    }

    private fun nuggetRecipe(material: HTMaterial, item: Item) {
        //1x Ingot -> 9x Nugget
        if (!hasDefaultItem(material, HTShapes.INGOT)) return
        HTRecipeManager.registerVanillaRecipe(
            HTShapes.NUGGET.getIdentifier(material, HTMaterialsCommon.MOD_ID).suffix("_shapeless"),
            ShapelessRecipeJsonBuilder.create(item, 9)
                .input(HTShapes.INGOT.getCommonTag(material))
                .setBypassesValidation(true)
        )
    }

    @Environment(EnvType.CLIENT)
    override fun clientSetup() {
        commonSetup()
        cache.forEach(HTMaterialsAddon::clientSetup)
        //Register Models and BlockStates
        HTMaterialModelManager.register()
        HTMaterialsCommon.LOGGER.info("BlockStates and Models Registered!")
    }

}