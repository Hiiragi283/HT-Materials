package io.github.hiiragi283.material

import io.github.hiiragi283.material.api.HTMaterialsAddon
import io.github.hiiragi283.material.api.fluid.HTFluidManager
import io.github.hiiragi283.material.api.item.HTMaterialItem
import io.github.hiiragi283.material.api.material.*
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlagSet
import io.github.hiiragi283.material.api.material.property.HTComponentProperty
import io.github.hiiragi283.material.api.material.property.HTMaterialPropertyMap
import io.github.hiiragi283.material.api.material.property.HTPropertyKey
import io.github.hiiragi283.material.api.part.HTPartManager
import io.github.hiiragi283.material.api.registry.HTDefaultedMap
import io.github.hiiragi283.material.api.registry.HTDefaultedTable
import io.github.hiiragi283.material.api.registry.HTObjectKeySet
import io.github.hiiragi283.material.api.shape.HTShape
import io.github.hiiragi283.material.api.shape.HTShapeKey
import io.github.hiiragi283.material.api.shape.HTShapePredicate
import io.github.hiiragi283.material.api.shape.HTShapes
import io.github.hiiragi283.material.util.isModLoaded
import io.github.hiiragi283.material.util.suffix
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.data.server.RecipeProvider
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.tag.TagKey
import net.minecraft.util.registry.Registry
import org.slf4j.Logger
import org.slf4j.LoggerFactory

internal object HTMaterialsCore {

    private val LOGGER: Logger = LoggerFactory.getLogger("${HTMaterialsCommon.MOD_NAME}/Addons")

    private val cache: List<HTMaterialsAddon> = FabricLoader.getInstance()
        .getEntrypoints(HTMaterialsCommon.MOD_ID, HTMaterialsAddon::class.java)
        .filter { isModLoaded(it.modId) }
        .sortedWith(compareBy(HTMaterialsAddon::priority).thenBy { it.javaClass.name })

    //    Pre Launch - HTShape    //

    private val shapeKeySet: HTObjectKeySet<HTShapeKey> = HTObjectKeySet.create()

    fun registerShape() {
        cache.forEach { it.registerShape(shapeKeySet) }
    }

    private val predicateMap: HTDefaultedMap<HTShapeKey, HTShapePredicate.Builder> =
        HTDefaultedMap.create { HTShapePredicate.Builder() }

    fun modifyShapePredicate() {
        cache.forEach { it.modifyShapePredicate(predicateMap) }
    }

    fun createShape() {
        shapeKeySet.forEach { key: HTShapeKey ->
            val predicate: HTShapePredicate = predicateMap.getOrCreate(key).build()
            HTShape.create(key, predicate)
        }
    }

    //    Pre Launch - HTMaterial    //

    private val materialKeySet: HTObjectKeySet<HTMaterialKey> = HTObjectKeySet.create()

    fun registerMaterialKey() {
        cache.forEach { it.registerMaterialKey(materialKeySet) }
    }

    private val propertyMap: HTDefaultedMap<HTMaterialKey, HTMaterialPropertyMap.Builder> =
        HTDefaultedMap.create { HTMaterialPropertyMap.Builder() }

    fun modifyMaterialProperty() {
        cache.forEach { it.modifyMaterialProperty(propertyMap) }
    }

    private val flagMap: HTDefaultedMap<HTMaterialKey, HTMaterialFlagSet.Builder> =
        HTDefaultedMap.create { HTMaterialFlagSet.Builder() }

    fun modifyMaterialFlag() {
        cache.forEach { it.modifyMaterialFlag(flagMap) }
    }

    private val colorMap: MutableMap<HTMaterialKey, ColorConvertible> = hashMapOf()

    fun modifyMaterialColor() {
        cache.forEach { it.modifyMaterialColor(colorMap) }
    }

    private val formulaMap: MutableMap<HTMaterialKey, FormulaConvertible> = hashMapOf()

    fun modifyMaterialFormula() {
        cache.forEach { it.modifyMaterialFormula(formulaMap) }
    }

    private val molarMap: MutableMap<HTMaterialKey, MolarMassConvertible> = hashMapOf()

    fun modifyMaterialMolar() {
        cache.forEach { it.modifyMaterialMolar(molarMap) }
    }

    private fun getColor(key: HTMaterialKey, property: HTMaterialPropertyMap): ColorConvertible {
        var color: ColorConvertible? = property.values.filterIsInstance<HTComponentProperty<*>>().getOrNull(0)
        colorMap[key]?.let { color = it }
        return color ?: ColorConvertible.EMPTY
    }

    private fun getFormula(key: HTMaterialKey, property: HTMaterialPropertyMap): FormulaConvertible {
        var formula: FormulaConvertible? = property.values.filterIsInstance<HTComponentProperty<*>>().getOrNull(0)
        formulaMap[key]?.let { formula = it }
        return formula ?: FormulaConvertible.EMPTY
    }

    private fun getMolar(key: HTMaterialKey, property: HTMaterialPropertyMap): MolarMassConvertible {
        var molar: MolarMassConvertible? = property.values.filterIsInstance<HTComponentProperty<*>>().getOrNull(0)
        molarMap[key]?.let { molar = it }
        return molar ?: MolarMassConvertible.EMPTY
    }

    fun createMaterial() {
        materialKeySet.forEach { key: HTMaterialKey ->
            val property: HTMaterialPropertyMap = propertyMap.getOrCreate(key).build()
            val flags: HTMaterialFlagSet = flagMap.getOrCreate(key).build()
            val color: ColorConvertible = getColor(key, property)
            val formula: FormulaConvertible = getFormula(key, property)
            val molar: MolarMassConvertible = getMolar(key, property)
            val info = HTMaterialInfo(
                color.asColor(),
                formula.asFormula(),
                "%.1f".format(molar.asMolarMass()).toDouble()
            )
            HTMaterial.create(key, info, property, flags)
        }
    }

    fun verifyMaterial() {
        HTMaterial.REGISTRY.values.forEach(HTMaterial::verify)
    }

    //    Initialization    //

    fun registerMaterialFluids() {
        HTMaterial.REGISTRY.forEach { (key: HTMaterialKey, material: HTMaterial) ->
            material.getProperty(HTPropertyKey.FLUID)?.init(key)
        }
    }

    fun registerMaterialItems() {
        HTShape.REGISTRY.forEach { (shapeKey: HTShapeKey, shape: HTShape) ->
            HTMaterial.REGISTRY
                .filter { shape.test(it.value) }
                .filterNot { HTPartManager.hasDefaultItem(it.key, shapeKey) }
                .keys
                .forEach { materialKey: HTMaterialKey ->
                    //Register Item
                    HTMaterialItem(materialKey, shapeKey).run {
                        Registry.register(Registry.ITEM, shapeKey.getIdentifier(materialKey), this)
                        //Register as Default Item
                        HTPartManager.forceRegister(materialKey, shapeKey, this)
                    }
                }
        }
    }

    //    Post Initialization    //

    fun commonSetup() {

        //Bind Game Objects to HTPart
        bindItemToPart()
        bindFluidToPart()

        cache.forEach(HTMaterialsAddon::commonSetup)

        registerRecipes()
        LOGGER.info("Recipes Registered!")

        HTFluidManager.registerAllFluids()
        LOGGER.info("All Fluids Registered to HTFluidManager!")
    }

    private val itemTable: HTDefaultedTable<HTMaterialKey, HTShapeKey, MutableCollection<ItemConvertible>> =
        HTDefaultedTable.create { _, _ -> mutableSetOf() }

    private fun bindItemToPart() {
        cache.forEach { it.bindItemToPart(itemTable) }
        itemTable.forEach { materialKey: HTMaterialKey, shapeKey: HTShapeKey, items: MutableCollection<ItemConvertible> ->
            items.forEach { item: ItemConvertible -> HTPartManager.register(materialKey, shapeKey, item) }
        }
    }

    private val fluidMap: HTDefaultedMap<HTMaterialKey, MutableCollection<Fluid>> =
        HTDefaultedMap.create { mutableSetOf() }

    private fun bindFluidToPart() {
        cache.forEach { it.bindFluidToPart(fluidMap) }
        fluidMap.forEach { materialKey: HTMaterialKey, fluids: MutableCollection<Fluid> ->
            fluids.forEach { fluid: Fluid -> HTFluidManager.register(materialKey, fluid) }
        }
    }

    private fun registerRecipes() {
        HTMaterial.REGISTRY.keys.forEach { key: HTMaterialKey ->
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
        val ingotTag: TagKey<Item> = HTShapes.INGOT.getCommonTag(material)
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