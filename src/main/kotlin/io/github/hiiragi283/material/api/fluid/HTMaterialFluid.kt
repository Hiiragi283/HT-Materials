package io.github.hiiragi283.material.api.fluid

import io.github.hiiragi283.material.HTMaterialsCommon
import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.part.HTPart
import io.github.hiiragi283.material.api.shape.HTShape
import io.github.hiiragi283.material.util.prefix
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.fluid.FlowableFluid
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.FluidState
import net.minecraft.item.BucketItem
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.state.StateManager
import net.minecraft.text.Text
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.registry.Registry
import net.minecraft.world.BlockView
import net.minecraft.world.WorldAccess
import net.minecraft.world.WorldView

abstract class HTMaterialFluid private constructor(val material: HTMaterialKey) : FlowableFluid() {

    companion object {

        private val fluidFlowing: MutableMap<HTMaterialKey, Flowing> = mutableMapOf()

        private val fluidStill: MutableMap<HTMaterialKey, Still> = mutableMapOf()

        private val fluidBucket: MutableMap<HTMaterialKey, Bucket> = mutableMapOf()

        @JvmStatic
        fun getFluids(): Collection<HTMaterialFluid> = HTFluidManager.getDefaultFluidMap().values.filterIsInstance<HTMaterialFluid>()

        @JvmStatic
        fun getBuckets(): Collection<Bucket> = getFluids().map(HTMaterialFluid::getBucketItem).filterIsInstance<Bucket>()

        @JvmStatic
        fun getFluid(material: HTMaterialKey): HTMaterialFluid? = fluidStill[material]

        @JvmStatic
        fun getBucket(material: HTMaterialKey): Bucket? = fluidBucket[material]

        private val blockSettings = FabricBlockSettings.copyOf(Blocks.WATER)

        private val itemSettings = FabricItemSettings()
            .group(HTMaterialsCommon.ITEM_GROUP)
            .maxCount(1)
            .recipeRemainder(Items.BUCKET)

    }

    //    FlowableFluid    //

    override fun matchesType(fluid: Fluid): Boolean = fluid == still || fluid == flowing

    override fun isInfinite(): Boolean = false

    override fun beforeBreakingBlock(world: WorldAccess, pos: BlockPos, state: BlockState) {
        net.minecraft.block.Block.dropStacks(state, world, pos, world.getBlockEntity(pos))
    }

    override fun canBeReplacedWith(
        state: FluidState,
        world: BlockView,
        pos: BlockPos,
        fluid: Fluid,
        direction: Direction
    ): Boolean = false

    override fun getFlowSpeed(world: WorldView): Int = 4

    override fun getLevelDecreasePerBlock(world: WorldView): Int = 1

    override fun getTickRate(world: WorldView): Int = 5

    override fun getBlastResistance(): Float = 100.0f

    override fun getStill(): Fluid = fluidStill[material]!!

    override fun getFlowing(): Fluid = fluidFlowing[material]!!

    override fun toBlockState(state: FluidState): BlockState = Blocks.AIR.defaultState

    override fun getBucketItem(): Item = fluidBucket[material]!!

    //    Flowing    //

    class Flowing internal constructor(material: HTMaterialKey) : HTMaterialFluid(material) {

        init {
            fluidFlowing.putIfAbsent(material, this)
            Registry.register(
                Registry.FLUID,
                material.getIdentifier().prefix("flowing_"),
                this
            )
            HTFluidManager.forceRegister(material, this)
        }

        override fun appendProperties(builder: StateManager.Builder<Fluid, FluidState>) {
            super.appendProperties(builder)
            builder.add(LEVEL)
        }

        override fun getLevel(state: FluidState): Int = state.get(LEVEL)

        override fun isStill(state: FluidState): Boolean = false

    }

    //    Still    //

    class Still internal constructor(material: HTMaterialKey) : HTMaterialFluid(material) {

        init {
            fluidStill.putIfAbsent(material, this)
            Registry.register(
                Registry.FLUID,
                material.getIdentifier(),
                this
            )
            HTFluidManager.forceRegister(material, this)
        }

        override fun getLevel(state: FluidState): Int = 8

        override fun isStill(state: FluidState): Boolean = true

    }

    //    Bucket    //

    class Bucket internal constructor(fluid: Still) : BucketItem(fluid, itemSettings) {

        companion object {
            private val shape: HTShape = HTShape.create("bucket")
        }

        private val part: HTPart = HTPart(fluid.material, shape)

        init {
            fluidBucket.putIfAbsent(fluid.material, this)
            Registry.register(Registry.ITEM, part.getIdentifier(), this)
        }

        override fun getName(): Text = part.getTranslatedText()

        override fun getName(stack: ItemStack): Text = part.getTranslatedText()

    }

}