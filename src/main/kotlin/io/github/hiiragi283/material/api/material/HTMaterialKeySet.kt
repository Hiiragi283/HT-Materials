package io.github.hiiragi283.material.api.material

class HTMaterialKeySet : Iterable<HTMaterialKey> {

    private val backingSet: HashSet<HTMaterialKey> = hashSetOf()

    fun add(key: HTMaterialKey): Boolean = backingSet.add(key)

    fun addAll(keys: Iterable<HTMaterialKey>) {
        keys.forEach(::add)
    }

    fun addAll(vararg keys: HTMaterialKey) {
        keys.forEach(::add)
    }

    override fun iterator(): Iterator<HTMaterialKey> = backingSet.iterator()

}