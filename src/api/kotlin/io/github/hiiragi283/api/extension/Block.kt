package io.github.hiiragi283.api.extension

import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.registry.Registries
import net.minecraft.util.Identifier

val Block.id: Identifier
    get() = Registries.BLOCK.getId(this)

fun Block.isAir(): Boolean = this == Blocks.AIR

fun Block.nonAirOrNull(): Block? = takeUnless { isAir() }
