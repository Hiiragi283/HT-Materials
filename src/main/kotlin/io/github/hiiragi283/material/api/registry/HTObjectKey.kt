package io.github.hiiragi283.material.api.registry

data class HTObjectKey<T>(val name: String, val objClass: Class<T>) {

    companion object {
        @JvmStatic
        inline fun <reified T> create(name: String): HTObjectKey<T> = HTObjectKey(name, T::class.java)
    }

    //    Any    //

    override fun toString(): String = name

}