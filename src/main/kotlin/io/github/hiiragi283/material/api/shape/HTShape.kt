package io.github.hiiragi283.material.api.shape

import io.github.hiiragi283.material.api.material.HTMaterial
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.function.Predicate

class HTShape private constructor(
    val key: HTShapeKey,
    val predicate: HTShapePredicate
) : Predicate<HTMaterial> by predicate {

    companion object {

        private val LOGGER: Logger = LoggerFactory.getLogger(this::class.java)

        //    Registry    //

        private val registry: MutableMap<HTShapeKey, HTShape> = linkedMapOf()

        @JvmField
        val REGISTRY: Map<HTShapeKey, HTShape> = registry

        @JvmStatic
        fun getShape(key: HTShapeKey): HTShape =
            registry[key] ?: throw IllegalStateException("Shape: $key is not registered!")

        @JvmStatic
        fun getShapeOrNull(key: HTShapeKey): HTShape? = registry[key]

        @JvmStatic
        internal fun create(
            key: HTShapeKey,
            predicate: HTShapePredicate
        ): HTShape = HTShape(key, predicate).also {
            registry.putIfAbsent(key, it)
            LOGGER.info("Shape: $key registered!")
        }

    }

}