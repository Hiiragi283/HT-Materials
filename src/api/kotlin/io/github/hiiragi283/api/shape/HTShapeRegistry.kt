package io.github.hiiragi283.api.shape

import com.google.common.collect.ImmutableMap

interface HTShapeRegistry {
    val registry: ImmutableMap<String, HTShape>

    val keys: Collection<String>
        get() = registry.keys

    val values: Collection<HTShape>
        get() = registry.values

    operator fun get(key: String) = registry[key]

    operator fun contains(key: String) = key in registry

    //    Builder    //

    class Builder {
        val registry: ImmutableMap<String, HTShape>
            get() = ImmutableMap.copyOf(_registry)
        private val _registry: MutableMap<String, HTShape> = mutableMapOf()

        fun add(key: HTShape) {
            _registry[key.name] = key
        }

        fun add(name: String) {
            _registry[name] = HTShape(name)
        }
    }
}
