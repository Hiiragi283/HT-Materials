package io.github.hiiragi283.material.impl.prop

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.material.property.HTMaterialProperty
import io.github.hiiragi283.api.material.property.HTPropertyType
import net.minecraft.item.ToolMaterial
import net.minecraft.recipe.Ingredient
import net.minecraft.registry.Registry
import net.minecraft.util.dynamic.Codecs

class HTToolMaterialProperty(
    private val durability: Int,
    private val miningSpeed: Float,
    private val attackDamage: Float,
    private val miningLevel: Int,
    private val enchantability: Int,
    private val repairIngredient: Ingredient,
) : HTMaterialProperty, ToolMaterial {
    //    ToolMaterial    //

    override fun getDurability(): Int = durability

    override fun getMiningSpeedMultiplier(): Float = miningSpeed

    override fun getAttackDamage(): Float = attackDamage

    override fun getMiningLevel(): Int = miningLevel

    override fun getEnchantability(): Int = enchantability

    override fun getRepairIngredient(): Ingredient = repairIngredient

    //    Type    //

    companion object {
        @JvmField
        val TYPE: HTPropertyType<HTToolMaterialProperty> = HTPropertyType.build(
            RecordCodecBuilder.create { instance ->
                instance.group(
                    Codec.INT.fieldOf("durability").forGetter(HTToolMaterialProperty::durability),
                    Codec.FLOAT.fieldOf("mining_speed").forGetter(HTToolMaterialProperty::miningSpeed),
                    Codec.FLOAT.fieldOf("attack_damage").forGetter(HTToolMaterialProperty::attackDamage),
                    Codec.INT.fieldOf("mining_level").forGetter(HTToolMaterialProperty::miningLevel),
                    Codec.INT.fieldOf("enchantability").forGetter(HTToolMaterialProperty::enchantability),
                    Codecs.JSON_ELEMENT.fieldOf("repair").forGetter { it.repairIngredient.toJson() },
                ).apply(instance) { dur, spe, dam, lev, enc, rep ->
                    HTToolMaterialProperty(dur, spe, dam, lev, enc, Ingredient.fromJson(rep))
                }
            },
        )

        init {
            Registry.register(HTMaterialsAPI.Registries.PROPERTY_TYPE, HTMaterialsAPI.id("material_tool"), TYPE)
        }
    }
}
