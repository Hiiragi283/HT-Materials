package io.github.hiiragi283.material.api.registry

import io.github.hiiragi283.material.impl.registry.HTDefaultedMapImpl

interface HTDefaultedMap<K, V> {

    fun getOrCreate(key: K): V

    companion object {
        @JvmStatic
        fun <K, V> create(mapping: (K) -> V): HTDefaultedMap<K, V> = HTDefaultedMapImpl(mapping)
    }

}