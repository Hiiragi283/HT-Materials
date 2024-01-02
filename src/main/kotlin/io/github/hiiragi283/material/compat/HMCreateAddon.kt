package io.github.hiiragi283.material.compat

import com.simibubi.create.AllItems
import com.simibubi.create.content.contraptions.components.crusher.CrushingRecipe
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder
import com.simibubi.create.foundation.data.recipe.MechanicalCraftingRecipeBuilder
import io.github.hiiragi283.material.HTMaterialsCommon
import io.github.hiiragi283.material.api.HTMaterialsAddon
import io.github.hiiragi283.material.api.event.HTRecipeRegisterCallback
import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.material.materials.HTVanillaMaterials
import io.github.hiiragi283.material.api.registry.HTDefaultedTable
import io.github.hiiragi283.material.api.shape.HTShapeKey
import io.github.hiiragi283.material.api.shape.HTShapes
import net.minecraft.item.ItemConvertible
import net.minecraft.item.Items
import net.minecraft.recipe.Ingredient
import net.minecraft.tag.ItemTags

@Suppress("unused")
object HMCreateAddon : HTMaterialsAddon {

    override val modId: String = "create"

    override val priority: Int = 0

    override fun bindItemToPart(registry: HTDefaultedTable<HTMaterialKey, HTShapeKey, MutableCollection<ItemConvertible>>) {
        registry.getOrCreate(HTVanillaMaterials.NETHERRACK, HTShapes.DUST).add(AllItems.CINDER_FLOUR.get())
        registry.getOrCreate(HTVanillaMaterials.OBSIDIAN, HTShapes.DUST).add(AllItems.POWDERED_OBSIDIAN.get())
    }

    override fun commonSetup() {
        HTRecipeRegisterCallback.EVENT.register { handler ->

            handler.addCreate(
                ProcessingRecipeBuilder(::CrushingRecipe, HTMaterialsCommon.id("test1"))
                    .withItemIngredients(Ingredient.ofItems(Items.BEACON))
                    .duration(100)
                    .output(Items.NETHER_STAR)
            )

            handler.addMechanicalCrafting(
                HTMaterialsCommon.id("test"),
                MechanicalCraftingRecipeBuilder.shapedRecipe(Items.CHEST)
                    .patternLine("AAAAA")
                    .patternLine("A   A")
                    .patternLine("A   A")
                    .patternLine("A   A")
                    .patternLine("AAAAA")
                    .key('A', ItemTags.PLANKS)
            )
        }
    }

}