package io.github.hiiragi283.api.material.property

import com.mojang.serialization.Codec
import com.mojang.serialization.DataResult
import com.mojang.serialization.DynamicOps
import io.github.hiiragi283.api.HTMaterialsAPI
import net.minecraft.util.dynamic.Codecs

interface HTPropertyType<T : HTMaterialProperty> {
    val codec: Codec<T>?

    fun hasCodec(): Boolean = codec != null

    fun getCodecOrThrow() = checkNotNull(codec)

    fun <D> encode(dynamicOps: DynamicOps<D>, property: T): DataResult<D> = codec?.encodeStart(dynamicOps, property)
        ?: DataResult.error { "Cannot serialize property because codec is null!" }

    @Suppress("UNCHECKED_CAST")
    fun cast(obj: Any?): T? = obj as? T

    companion object {
        @JvmStatic
        val CODEC: Codec<HTPropertyType<*>> = Codecs.createLazy { HTMaterialsAPI.Registries.PROPERTY_TYPE.codec }

        @JvmStatic
        fun <T : HTMaterialProperty> build(codec: Codec<T>?): HTPropertyType<T> = Impl(codec)
    }

    private class Impl<T : HTMaterialProperty>(override val codec: Codec<T>?) : HTPropertyType<T>
}
