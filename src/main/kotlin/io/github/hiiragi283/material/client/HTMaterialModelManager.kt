package io.github.hiiragi283.material.client

import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.property.HTPropertyKey
import io.github.hiiragi283.material.api.shape.HTShape
import io.github.hiiragi283.material.api.shape.HTShapes
import io.github.hiiragi283.material.common.HTMaterialsCommon
import io.github.hiiragi283.material.common.util.prefix
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.data.client.BlockStateSupplier
import net.minecraft.data.client.Models
import net.minecraft.data.client.TextureKey
import net.minecraft.util.Identifier
import pers.solid.brrp.v1.api.RuntimeResourcePack
import pers.solid.brrp.v1.fabric.api.RRPCallback
import pers.solid.brrp.v1.model.ModelJsonBuilder
import java.util.function.BiConsumer

@Environment(EnvType.CLIENT)
object HTMaterialModelManager {

    private val RESOURCE_PACK: RuntimeResourcePack = RuntimeResourcePack.create(HTMaterialsCommon.id("runtime_client"))

    private val registry: MutableMap<HTShape, BiConsumer<HTMaterial, HTShape>> = mutableMapOf()

    init {
        //Block
        /*registerConsumer(HTShapes.BLOCK) { material: HTMaterial, shape: HTShape ->
            val block: Block = Blocks.AIR
            val blockModelId: Identifier = getBlockModelId(material, shape)
            val itemModelId: Identifier = getItemModelId(material, shape)
            //BlockState
            addBlockState(
                shape.getIdentifier(material),
                BlockStateModelGenerator.createSingletonBlockState(block, blockModelId)
            )
            //Block Model
            addModel(
                blockModelId,
                ModelJsonBuilder.create(HTMaterialsCommon.id("block/all_tinted"))
                    .addTexture(TextureKey.ALL, Identifier("block/iron_block"))
            )
            //Item Model
            addModel(itemModelId, ModelJsonBuilder.create(blockModelId))
        }*/
        //Dust
        registerSimpleConsumer(HTShapes.DUST, Identifier("item/sugar"))
        //Gear
        registerSimpleConsumer(HTShapes.GEAR)
        //Gem
        registerConsumer(HTShapes.GEM) { material: HTMaterial, shape: HTShape ->
            material.getProperty(HTPropertyKey.GEM)?.type?.let { type ->
                addModel(
                    getItemModelId(material, shape),
                    ModelJsonBuilder.create(Models.GENERATED)
                        .addTexture(TextureKey.LAYER0, type.textureId)
                )
            }
        }
        //Ingot
        registerSimpleConsumer(HTShapes.INGOT)
        //Nugget
        registerSimpleConsumer(HTShapes.NUGGET)
        //Plate
        registerSimpleConsumer(HTShapes.PLATE)
        //Rod
        registerSimpleConsumer(HTShapes.ROD)
    }

    @JvmStatic
    fun addBlockState(id: Identifier, state: BlockStateSupplier) {
        RESOURCE_PACK.addBlockState(id, state)
    }

    @JvmStatic
    fun addModel(id: Identifier, model: ModelJsonBuilder) {
        RESOURCE_PACK.addModel(id, model)
    }

    @JvmStatic
    fun getBlockModelId(material: HTMaterial, shape: HTShape): Identifier =
        shape.getIdentifier(material).prefix("block/")

    @JvmStatic
    fun getItemModelId(material: HTMaterial, shape: HTShape): Identifier =
        shape.getIdentifier(material).prefix("item/")

    @JvmStatic
    fun registerSimpleConsumer(shape: HTShape, textureId: Identifier = HTMaterialsCommon.id("item/${shape.name}")) {
        registerConsumer(shape) { material: HTMaterial, _: HTShape ->
            addModel(
                getItemModelId(material, shape),
                ModelJsonBuilder.create(Models.GENERATED)
                    .addTexture(TextureKey.LAYER0, textureId)
            )
        }
    }

    @JvmStatic
    fun registerConsumer(shape: HTShape, biConsumer: BiConsumer<HTMaterial, HTShape>) {
        registry[shape] = biConsumer
    }

    internal fun register() {
        RRPCallback.AFTER_VANILLA.register {
            //Register models to resource pack
            registry.forEach { (shape: HTShape, biConsumer: BiConsumer<HTMaterial, HTShape>) ->
                HTMaterial.REGISTRY.forEach { material: HTMaterial -> biConsumer.accept(material, shape) }
            }
            //Clear caches
            registry.clear()
            //Register Resource Pack
            it.add(RESOURCE_PACK)
        }
    }

}