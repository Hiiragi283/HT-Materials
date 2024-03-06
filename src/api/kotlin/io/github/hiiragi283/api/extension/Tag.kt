package io.github.hiiragi283.api.extension

import net.minecraft.registry.Registry
import net.minecraft.registry.entry.RegistryEntry
import net.minecraft.registry.tag.TagKey

fun <T> TagKey<T>.values(registry: Registry<T>) = registry.getOrCreateEntryList(this).map(RegistryEntry<T>::value)

fun test() {
}
