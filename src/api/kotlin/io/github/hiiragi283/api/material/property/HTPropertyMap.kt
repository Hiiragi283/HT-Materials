package io.github.hiiragi283.api.material.property

import com.mojang.serialization.Codec
import io.github.hiiragi283.api.extension.keyDispatchingCodec

class HTPropertyMap(builder: Builder) : Map<HTPropertyType<*>, HTMaterialProperty> by builder.backingMap {
    companion object {
        @JvmField
        val CODEC: Codec<HTPropertyMap> = keyDispatchingCodec(HTPropertyType.CODEC) { it.getCodecOrThrow() }.xmap(
            { map -> HTPropertyMap(Builder().addAll(map)) },
            { _ -> null },
        )
    }

    //    Builder    //

    class Builder {
        internal val backingMap: MutableMap<HTPropertyType<*>, HTMaterialProperty> = mutableMapOf()

        fun <T : HTMaterialProperty> add(type: HTPropertyType<T>, property: T) = apply {
            backingMap[type] = property
        }

        fun addAll(map: Map<HTPropertyType<*>, HTMaterialProperty>) = apply {
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
