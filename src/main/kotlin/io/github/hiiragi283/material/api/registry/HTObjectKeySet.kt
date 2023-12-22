package io.github.hiiragi283.material.api.registry

import io.github.hiiragi283.material.impl.registry.HTObjectKeySetImpl

interface HTObjectKeySet<T> : Iterable<HTObjectKey<T>> {

    fun add(key: HTObjectKey<T>): Boolean

    fun addAll(keys: Iterable<HTObjectKey<T>>) {
        keys.forEach(::add)
    }

    fun addAll(vararg keys: HTObjectKey<T>) {
        keys.forEach(::add)
    }

    companion object {
        @JvmStatic
        fun <T> create(): HTObjectKeySet<T> = HTObjectKeySetImpl()
    }

}