package io.github.hiiragi283.material.api.event

import com.google.gson.JsonElement
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder
import com.simibubi.create.foundation.data.recipe.MechanicalCraftingRecipeBuilder
import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder
import net.minecraft.data.server.recipe.RecipeJsonProvider
import net.minecraft.util.Identifier

fun interface HTRecipeRegisterCallback {

    fun onRecipeRegister(handler: Handler)

    companion object {
        @JvmField
        val EVENT: Event<HTRecipeRegisterCallback> =
            EventFactory.createArrayBacked(HTRecipeRegisterCallback::class.java) { callbacks ->
                HTRecipeRegisterCallback { handler -> callbacks.forEach { it.onRecipeRegister(handler) } }
            }
    }

    class Handler(val map: MutableMap<Identifier, JsonElement>) {

        fun addVanilla(recipeId: Identifier, builder: CraftingRecipeJsonBuilder) {
            builder.offerTo({ provider: RecipeJsonProvider ->
                map.putIfAbsent(
                    provider.recipeId,
                    provider.toJson()
                )
            }, recipeId)
        }

        fun addCreate(builder: ProcessingRecipeBuilder<*>) {
            builder.build { provider: RecipeJsonProvider ->
                map.putIfAbsent(
                    provider.recipeId,
                    provider.toJson()
                )
            }
        }

        fun addMechanicalCrafting(recipeId: Identifier, builder: MechanicalCraftingRecipeBuilder) {
            builder.build({ provider: RecipeJsonProvider ->
                map.putIfAbsent(
                    provider.recipeId,
                    provider.toJson()
                )
            }, recipeId)
        }

        fun addJson(recipeId: Identifier, jsonElement: JsonElement) {
            map.putIfAbsent(recipeId, jsonElement)
        }

        fun remove(recipeId: Identifier) {
            map.remove(recipeId)
        }

    }

}