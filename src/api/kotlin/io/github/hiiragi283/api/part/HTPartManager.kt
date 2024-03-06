package io.github.hiiragi283.api.part

import com.google.common.collect.ImmutableMap
import com.google.common.collect.ImmutableMultimap
import com.google.common.collect.LinkedHashMultimap
import com.google.common.collect.Multimap
import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.extension.id
import io.github.hiiragi283.api.extension.nonAirOrNull
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.material.HTMaterialKeys
import io.github.hiiragi283.api.shape.HTShape
import io.github.hiiragi283.api.shape.HTShapes
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.item.Items

interface HTPartManager {
    // Item -> Entry

    val itemToEntryMap: ImmutableMap<Item, Entry>

    fun getEntry(itemConvertible: ItemConvertible): Entry? = itemToEntryMap[itemConvertible.asItem()]

    fun hasEntry(itemConvertible: ItemConvertible): Boolean = itemConvertible.asItem() in itemToEntryMap

    // HTPart -> Entry

    fun getDefaultEntry(part: HTPart): Entry? = getDefaultEntry(part.materialKey, part.shapeKey)

    fun getDefaultEntry(materialKey: HTMaterialKey, shapeKey: HTShape): Entry? {
        val entries: Collection<Entry> = getEntries(materialKey, shapeKey)
        for (entry: Entry in entries) {
            val namespace: String = entry.item.id.namespace
            return when (namespace) {
                "minecraft" -> entry
                HTMaterialsAPI.MOD_ID -> entry
                else -> continue
            }
        }
        return entries.firstOrNull()
    }

    // HTPart -> Collection<Entry>

    val partToEntriesMap: ImmutableMultimap<HTPart, Entry>

    fun getAllEntries(): Collection<Entry> = partToEntriesMap.values()

    fun getEntries(materialKey: HTMaterialKey, shapeKey: HTShape): Collection<Entry> = getEntries(HTPart(materialKey, shapeKey))

    fun getEntries(part: HTPart): Collection<Entry> = partToEntriesMap.get(part)

    fun getEntries(itemConvertible: ItemConvertible): Collection<Entry> =
        getEntry(itemConvertible)?.part?.let { getEntries(it) } ?: listOf()

    fun hasEntry(materialKey: HTMaterialKey, shapeKey: HTShape): Boolean = hasEntry(HTPart(materialKey, shapeKey))

    fun hasEntry(part: HTPart): Boolean = partToEntriesMap.containsKey(part)

    //    Entry    //

    data class Entry(val materialKey: HTMaterialKey, val shapeKey: HTShape, val item: Item) {
        val part = HTPart(materialKey, shapeKey)
    }

    //    Builder    //

    class Builder {
        val itemToEntryMap: ImmutableMap<Item, Entry>
            get() = ImmutableMap.copyOf(itemToEntryMap1)
        private val itemToEntryMap1: MutableMap<Item, Entry> = mutableMapOf()

        val partToEntriesMap: ImmutableMultimap<HTPart, Entry>
            get() = ImmutableMultimap.copyOf(partToEntriesMap1)
        private val partToEntriesMap1: Multimap<HTPart, Entry> = LinkedHashMultimap.create()

        fun add(materialKey: HTMaterialKey, shapeKey: HTShape, itemConvertible: ItemConvertible) {
            val item: Item = itemConvertible.nonAirOrNull() ?: return
            val part = HTPart(materialKey, shapeKey)
            val entry = Entry(materialKey, shapeKey, item)
            itemToEntryMap1[item] = entry
            partToEntriesMap1.put(part, entry)
        }

        fun remove(materialKey: HTMaterialKey, shapeKey: HTShape, itemConvertible: ItemConvertible) {
            val item: Item = itemConvertible.nonAirOrNull() ?: return
            val part = HTPart(materialKey, shapeKey)
            val entry = Entry(materialKey, shapeKey, item)
            if (item in itemToEntryMap1 && partToEntriesMap1.containsKey(part)) {
                itemToEntryMap1.remove(item, entry)
                partToEntriesMap1.remove(part, entry)
            }
        }

        init {
            // Amethyst
            // register(HTMaterialKeys.AMETHYST, HTShapes.BLOCK, Items.AMETHYST_BLOCK)
            // register(HTMaterialKeys.AMETHYST, HTShapes.GEM, Items.AMETHYST_SHARD)
            // Andesite
            add(HTMaterialKeys.ANDESITE, HTShapes.BLOCK, Items.ANDESITE)
            add(HTMaterialKeys.ANDESITE, HTShapes.BLOCK, Items.POLISHED_ANDESITE)
            // Basalt
            add(HTMaterialKeys.BASALT, HTShapes.BLOCK, Items.BASALT)
            add(HTMaterialKeys.BASALT, HTShapes.BLOCK, Items.POLISHED_BASALT)
            // Blackstone
            add(HTMaterialKeys.BLACKSTONE, HTShapes.BLOCK, Items.BLACKSTONE)
            add(HTMaterialKeys.BLACKSTONE, HTShapes.BLOCK, Items.POLISHED_BLACKSTONE)
            add(HTMaterialKeys.BLACKSTONE, HTShapes.BRICKS, Items.CHISELED_POLISHED_BLACKSTONE)
            add(HTMaterialKeys.BLACKSTONE, HTShapes.BRICKS, Items.CRACKED_POLISHED_BLACKSTONE_BRICKS)
            add(HTMaterialKeys.BLACKSTONE, HTShapes.BRICKS, Items.POLISHED_BLACKSTONE_BRICKS)
            // Brick
            add(HTMaterialKeys.BRICK, HTShapes.BRICKS, Items.BRICKS)
            add(HTMaterialKeys.BRICK, HTShapes.INGOT, Items.BRICK)
            // Calcite
            // add(HTMaterialKeys.CALCITE, HTShapes.BLOCK, Items.CALCITE)
            // Charcoal
            add(HTMaterialKeys.CHARCOAL, HTShapes.GEM, Items.CHARCOAL)
            // Clay
            add(HTMaterialKeys.CLAY, HTShapes.BLOCK, Items.CLAY)
            add(HTMaterialKeys.CLAY, HTShapes.GEM, Items.CLAY_BALL)
            // Coal
            add(HTMaterialKeys.COAL, HTShapes.GEM, Items.COAL)
            add(HTMaterialKeys.COAL, HTShapes.BLOCK, Items.COAL_BLOCK)
            // Copper
            // add(HTMaterialKeys.COPPER, HTShapes.BLOCK, Items.COPPER_BLOCK)
            // add(HTMaterialKeys.COPPER, HTShapes.INGOT, Items.COPPER_INGOT)
            // add(HTMaterialKeys.COPPER, HTShapes.ORE, Items.COPPER_ORE)
            // add(HTMaterialKeys.COPPER, HTShapes.RAW_BLOCK, Items.RAW_COPPER_BLOCK)
            // add(HTMaterialKeys.COPPER, HTShapes.RAW_ORE, Items.RAW_COPPER)
            // Deepslate
            // add(HTMaterialKeys.DEEPSLATE, HTShapes.BLOCK, Items.DEEPSLATE)*/
            // Diamond
            add(HTMaterialKeys.DIAMOND, HTShapes.BLOCK, Items.DIAMOND_BLOCK)
            add(HTMaterialKeys.DIAMOND, HTShapes.GEM, Items.DIAMOND)
            add(HTMaterialKeys.DIAMOND, HTShapes.ORE, Items.DIAMOND_ORE)
            // Diorite
            add(HTMaterialKeys.DIORITE, HTShapes.BLOCK, Items.DIORITE)
            add(HTMaterialKeys.DIORITE, HTShapes.BLOCK, Items.POLISHED_DIORITE)
            // Dripstone
            // add(HTMaterialKeys.DRIPSTONE, HTShapes.BLOCK, Items.DRIPSTONE_BLOCK)
            // Emerald
            add(HTMaterialKeys.EMERALD, HTShapes.BLOCK, Items.EMERALD_BLOCK)
            add(HTMaterialKeys.EMERALD, HTShapes.GEM, Items.EMERALD)
            add(HTMaterialKeys.EMERALD, HTShapes.ORE, Items.EMERALD_ORE)
            // End Stone
            add(HTMaterialKeys.END_STONE, HTShapes.BLOCK, Items.END_STONE)
            add(HTMaterialKeys.END_STONE, HTShapes.BRICKS, Items.END_STONE_BRICKS)
            // Ender Pearl
            add(HTMaterialKeys.ENDER_PEARL, HTShapes.GEM, Items.ENDER_PEARL)
            // Flint
            add(HTMaterialKeys.FLINT, HTShapes.GEM, Items.FLINT)
            // Iron
            add(HTMaterialKeys.IRON, HTShapes.BLOCK, Items.IRON_BLOCK)
            add(HTMaterialKeys.IRON, HTShapes.INGOT, Items.IRON_INGOT)
            add(HTMaterialKeys.IRON, HTShapes.NUGGET, Items.IRON_NUGGET)
            add(HTMaterialKeys.IRON, HTShapes.ORE, Items.IRON_ORE)
            // add(HTMaterialKeys.IRON, HTShapes.RAW_BLOCK, Items.RAW_IRON_BLOCK)
            // add(HTMaterialKeys.IRON, HTShapes.RAW_ORE, Items.RAW_IRON)
            // Glass
            add(HTMaterialKeys.GLASS, HTShapes.BLOCK, Items.GLASS)
            // Glowstone
            add(HTMaterialKeys.GLOWSTONE, HTShapes.BLOCK, Items.GLOWSTONE)
            add(HTMaterialKeys.GLOWSTONE, HTShapes.DUST, Items.GLOWSTONE_DUST)
            // Gold
            add(HTMaterialKeys.GOLD, HTShapes.BLOCK, Items.GOLD_BLOCK)
            add(HTMaterialKeys.GOLD, HTShapes.INGOT, Items.GOLD_INGOT)
            add(HTMaterialKeys.GOLD, HTShapes.NUGGET, Items.GOLD_NUGGET)
            add(HTMaterialKeys.GOLD, HTShapes.ORE, Items.GOLD_ORE)
            // add(HTMaterialKeys.GOLD, HTShapes.RAW_BLOCK, Items.RAW_GOLD_BLOCK)
            // add(HTMaterialKeys.GOLD, HTShapes.RAW_ORE, Items.RAW_GOLD)
            // Granite
            add(HTMaterialKeys.GRANITE, HTShapes.BLOCK, Items.GRANITE)
            add(HTMaterialKeys.GRANITE, HTShapes.BLOCK, Items.POLISHED_GRANITE)
            // Lapis
            add(HTMaterialKeys.LAPIS, HTShapes.BLOCK, Items.LAPIS_BLOCK)
            add(HTMaterialKeys.LAPIS, HTShapes.GEM, Items.LAPIS_LAZULI)
            add(HTMaterialKeys.LAPIS, HTShapes.ORE, Items.LAPIS_ORE)
            // Nether Brick
            add(HTMaterialKeys.NETHER_BRICK, HTShapes.BRICKS, Items.NETHER_BRICKS)
            add(HTMaterialKeys.NETHER_BRICK, HTShapes.INGOT, Items.NETHER_BRICK)
            // Netherite
            add(HTMaterialKeys.NETHERITE, HTShapes.BLOCK, Items.NETHERITE_BLOCK)
            add(HTMaterialKeys.NETHERITE, HTShapes.INGOT, Items.NETHERITE_INGOT)
            // Netherrack
            add(HTMaterialKeys.NETHERRACK, HTShapes.BLOCK, Items.NETHERRACK)
            // Obsidian
            add(HTMaterialKeys.OBSIDIAN, HTShapes.BLOCK, Items.OBSIDIAN)
            // Prismarine
            // add(HTMaterialKeys.PRISMARINE, HTShapes.DUST, Items.PRISMARINE_CRYSTALS)
            // add(HTMaterialKeys.PRISMARINE, HTShapes.GEM, Items.PRISMARINE_SHARD)
            // Quartz
            add(HTMaterialKeys.QUARTZ, HTShapes.BLOCK, Items.QUARTZ_BLOCK)
            add(HTMaterialKeys.QUARTZ, HTShapes.GEM, Items.QUARTZ)
            add(HTMaterialKeys.QUARTZ, HTShapes.ORE, Items.NETHER_QUARTZ_ORE)
            // Redstone
            add(HTMaterialKeys.REDSTONE, HTShapes.BLOCK, Items.REDSTONE_BLOCK)
            add(HTMaterialKeys.REDSTONE, HTShapes.DUST, Items.REDSTONE)
            add(HTMaterialKeys.REDSTONE, HTShapes.ORE, Items.REDSTONE_ORE)
            // Stone
            add(HTMaterialKeys.STONE, HTShapes.BLOCK, Items.STONE)
            add(HTMaterialKeys.STONE, HTShapes.BRICKS, Items.STONE_BRICKS)
            add(HTMaterialKeys.STONE, HTShapes.BRICKS, Items.MOSSY_STONE_BRICKS)
            add(HTMaterialKeys.STONE, HTShapes.BRICKS, Items.CRACKED_STONE_BRICKS)
            // Tuff
            // add(HTMaterialKeys.TUFF, HTShapes.BLOCK, Items.TUFF)
            // Wood
            listOf(
                Items.OAK_LOG,
                Items.BIRCH_LOG,
                Items.SPRUCE_LOG,
                Items.JUNGLE_LOG,
                Items.ACACIA_LOG,
                Items.DARK_OAK_LOG,
                Items.CRIMSON_HYPHAE,
                Items.WARPED_HYPHAE,
            ).forEach { add(HTMaterialKeys.WOOD, HTShapes.LOG, it) }
            listOf(
                Items.OAK_WOOD,
                Items.BIRCH_WOOD,
                Items.SPRUCE_WOOD,
                Items.JUNGLE_WOOD,
                Items.ACACIA_WOOD,
                Items.DARK_OAK_WOOD,
                Items.CRIMSON_STEM,
                Items.WARPED_STEM,
            ).forEach { add(HTMaterialKeys.WOOD, HTShapes.LOG, it) }
            listOf(
                Items.OAK_PLANKS,
                Items.BIRCH_PLANKS,
                Items.SPRUCE_PLANKS,
                Items.JUNGLE_PLANKS,
                Items.ACACIA_PLANKS,
                Items.DARK_OAK_PLANKS,
                Items.CRIMSON_PLANKS,
                Items.WARPED_PLANKS,
            ).forEach { add(HTMaterialKeys.WOOD, HTShapes.PLANKS, it) }
        }
    }
}
