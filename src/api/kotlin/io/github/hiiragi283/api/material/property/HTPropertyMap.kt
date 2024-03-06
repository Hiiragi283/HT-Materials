package io.github.hiiragi283.api.material.property

import com.mojang.serialization.Codec
import io.github.hiiragi283.api.extension.keyDispatchingCodec

class HTPropertyMap(builder: Builder = Builder()) : Map<HTPropertyType<*>, Any> by builder.backingMap {
    companion object {
        @JvmField
        val CODEC: Codec<HTPropertyMap> =
            keyDispatchingCodec(HTPropertyType.CODEC, HTPropertyType<*>::getCodecOrThrow).xmap(
                { map -> HTPropertyMap(Builder().addAll(map)) },
                { it },
            )
    }

    //    Builder    //

    class Builder {
        internal val backingMap: MutableMap<HTPropertyType<*>, Any> = mutableMapOf()

        fun <T : Any> add(type: HTPropertyType<T>, property: T) = apply {
            backingMap[type] = property
        }

        fun addAll(map: Map<HTPropertyType<*>, Any>) = apply {
            backingMap.putAll(map)
        }

        fun addAll(builder: Builder) = apply {
            backingMap.putAll(builder.backingMap)
        }

        fun remove(type: HTPropertyType<*>) = apply {
            backingMap.remove(type)
        }
    }
}
