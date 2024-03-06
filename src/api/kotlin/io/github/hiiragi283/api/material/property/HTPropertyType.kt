package io.github.hiiragi283.api.material.property

import com.mojang.serialization.Codec
import io.github.hiiragi283.api.HTMaterialsAPI
import net.minecraft.util.dynamic.Codecs

interface HTPropertyType<T> {
    val codec: Codec<T>?

    fun hasCodec(): Boolean = codec != null

    @Suppress("UNCHECKED_CAST")
    fun getCodecOrThrow(): Codec<Any> = checkNotNull(codec as Codec<Any>)

    @Suppress("UNCHECKED_CAST")
    fun cast(obj: Any?): T? = obj as? T

    companion object {
        @JvmStatic
        val CODEC: Codec<HTPropertyType<*>> = Codecs.createLazy { HTMaterialsAPI.Registries.PROPERTY_TYPE.codec }

        @JvmStatic
        fun <T> build(codec: Codec<T>?): HTPropertyType<T> = Impl(codec)
    }

    private class Impl<T>(override val codec: Codec<T>?) : HTPropertyType<T>
}
