package io.github.hiiragi283.material.api.addon

import io.github.hiiragi283.material.HTMaterialsCommon
import io.github.hiiragi283.material.HTRecipeManager
import io.github.hiiragi283.material.api.fluid.HTFluidManager
import io.github.hiiragi283.material.api.material.*
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlagsNew
import io.github.hiiragi283.material.api.material.property.HTMaterialPropertiesNew
import io.github.hiiragi283.material.api.part.HTPartManager
import io.github.hiiragi283.material.api.registry.HTDefaultedMap
import io.github.hiiragi283.material.api.registry.HTObjectKeySet
import io.github.hiiragi283.material.api.shape.HTShape
import io.github.hiiragi283.material.api.shape.HTShapes
import io.github.hiiragi283.material.util.isModLoaded
import io.github.hiiragi283.material.util.suffix
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.data.server.RecipeProvider
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder
import net.minecraft.item.Item
import net.minecraft.tag.TagKey
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.awt.Color

object HTMaterialsAddonManager {

    private val LOGGER: Logger = LoggerFactory.getLogger("${HTMaterialsCommon.MOD_NAME}/Addons")

    private val cache: List<HTMaterialsAddon> = FabricLoader.getInstance()
        .getEntrypoints(HTMaterialsCommon.MOD_ID, HTMaterialsAddon::class.java)
        .filter { isModLoaded(it.modId) }
        .sortedWith(compareBy<HTMaterialsAddon> { it.priority }.thenBy { it.modId })

    //    Pre Launch    //

    private val shapeKeySet: HTObjectKeySet<HTShape> = HTObjectKeySet.create()

    fun registerShape() {
        HTShapes
        cache.forEach { it.registerShape(shapeKeySet) }
    }

    private val materialKeySet: HTMaterialKeySet = HTMaterialKeySet()

    fun registerMaterialKey() {
        cache.forEach { it.registerMaterialKey(materialKeySet) }
    }

    private val propertyMap: HTDefaultedMap<HTMaterialKey, HTMaterialPropertiesNew.Builder> =
        HTDefaultedMap.create { HTMaterialPropertiesNew.Builder() }

    fun modifyMaterialProperty() {
        cache.forEach { it.modifyMaterialProperty(propertyMap) }
    }

    private val flagMap: HTDefaultedMap<HTMaterialKey, HTMaterialFlagsNew.Builder> =
        HTDefaultedMap.create { HTMaterialFlagsNew.Builder() }

    fun modifyMaterialFlag() {
        cache.forEach { it.modifyMaterialFlag(flagMap) }
    }

    private val colorMap: HashMap<HTMaterialKey, ColorConvertible> = hashMapOf()

    fun modifyMaterialColor() {
        cache.forEach { it.modifyMaterialColor(colorMap) }
    }

    private val formulaMap: HashMap<HTMaterialKey, FormulaConvertible> = hashMapOf()

    fun modifyMaterialFormula() {
        cache.forEach { it.modifyMaterialFormula(formulaMap) }
    }

    private val molarMap: HashMap<HTMaterialKey, MolarMassConvertible> = hashMapOf()

    fun modifyMaterialMolar() {
        cache.forEach { it.modifyMaterialMolar(molarMap) }
    }

    fun createMaterial() {
        materialKeySet.forEach { key: HTMaterialKey ->
            val property: HTMaterialPropertiesNew = propertyMap.getOrCreate(key).build()
            val flags: HTMaterialFlagsNew = flagMap.getOrCreate(key).build()
            val color: Color = colorMap.getOrDefault(key, ColorConvertible.EMPTY).asColor()
            val formula: String = formulaMap.getOrDefault(key, FormulaConvertible.EMPTY).asFormula()
            val molar: Double = molarMap.getOrDefault(key, MolarMassConvertible.EMPTY).asMolarMass()
            val info = HTMaterialInfo(color, formula, molar)
            HTMaterialNew.create(key, info, property, flags)
        }
    }

    fun verifyMaterial() {
        HTMaterialNew.REGISTRY.values.forEach(HTMaterialNew::verify)
    }

    //    Initialization    //

    fun bindItemToPart() {
        cache.forEach(HTMaterialsAddon::bindItemToPart)
    }

    fun bindFluidToPart() {
        cache.forEach(HTMaterialsAddon::bindFluidToPart)
    }

    //    Post Initialization    //

    fun commonSetup() {
        cache.forEach(HTMaterialsAddon::commonSetup)

        registerRecipes()
        LOGGER.info("Recipes Registered!")

        HTFluidManager.registerAllFluids()
        LOGGER.info("All Fluids Registered to HTFluidManager!")
    }

    private fun registerRecipes() {
        HTMaterialNew.REGISTRY.keys.forEach { key ->
            HTPartManager.getDefaultItem(key, HTShapes.INGOT)?.let { ingotRecipe(key, it) }
            HTPartManager.getDefaultItem(key, HTShapes.NUGGET)?.let { nuggetRecipe(key, it) }
        }
    }

    private fun ingotRecipe(material: HTMaterialKey, item: Item) {
        //9x Nugget -> 1x Ingot
        if (!HTPartManager.hasDefaultItem(material, HTShapes.NUGGET)) return
        val nuggetTag: TagKey<Item> = HTShapes.NUGGET.getCommonTag(material)
        HTRecipeManager.registerVanillaRecipe(
            HTShapes.INGOT.getIdentifier(material, HTMaterialsCommon.MOD_ID).suffix("_shaped"),
            ShapedRecipeJsonBuilder.create(item)
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .input('A', nuggetTag)
                .criterion("has_nugget", RecipeProvider.conditionsFromTag(nuggetTag))
        )
    }

    private fun nuggetRecipe(material: HTMaterialKey, item: Item) {
        //1x Ingot -> 9x Nugget
        if (!HTPartManager.hasDefaultItem(material, HTShapes.INGOT)) return
        val ingotTag = HTShapes.INGOT.getCommonTag(material)
        HTRecipeManager.registerVanillaRecipe(
            HTShapes.NUGGET.getIdentifier(material, HTMaterialsCommon.MOD_ID).suffix("_shapeless"),
            ShapelessRecipeJsonBuilder.create(item, 9)
                .input(ingotTag)
                .criterion("has_ingot", RecipeProvider.conditionsFromTag(ingotTag))
        )
    }

    @Environment(EnvType.CLIENT)
    fun clientSetup() {
        commonSetup()
        cache.forEach(HTMaterialsAddon::clientSetup)
        LOGGER.info("BlockStates and Models Registered!")
    }

}