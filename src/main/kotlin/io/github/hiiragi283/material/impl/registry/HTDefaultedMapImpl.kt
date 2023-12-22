package io.github.hiiragi283.material.impl.registry

import io.github.hiiragi283.material.api.registry.HTDefaultedMap

internal class HTDefaultedMapImpl<K, V>(private val mapping: (K) -> V) : HTDefaultedMap<K, V> {

    private val backingMap: HashMap<K, V> = hashMapOf()

    override fun getOrCreate(key: K): V = backingMap.computeIfAbsent(key, mapping)

}