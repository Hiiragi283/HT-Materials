package io.github.hiiragi283.material.impl.registry

import io.github.hiiragi283.material.api.registry.HTObjectKey
import io.github.hiiragi283.material.api.registry.HTObjectKeySet

internal class HTObjectKeySetImpl<T> : HTObjectKeySet<T> {

    private val backingSet: HashSet<HTObjectKey<T>> = hashSetOf()

    override fun add(key: HTObjectKey<T>): Boolean = backingSet.add(key)

    override fun iterator(): Iterator<HTObjectKey<T>> = backingSet.iterator()

}