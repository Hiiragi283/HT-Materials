package io.github.hiiragi283.material.api.shape

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class HTShapeNew private constructor(
    val key: HTShapeKey,
    val predicate: HTShapePredicate
) {

    companion object {

        private val LOGGER: Logger = LoggerFactory.getLogger(this::class.java)

        //    Registry    //

        private val registry: MutableMap<HTShapeKey, HTShapeNew> = linkedMapOf()

        @JvmField
        val REGISTRY: Map<HTShapeKey, HTShapeNew> = registry

        @JvmStatic
        fun getShape(key: HTShapeKey): HTShapeNew =
            registry[key] ?: throw IllegalStateException("Shape: $key is not registered!")

        @JvmStatic
        internal fun create(
            key: HTShapeKey,
            predicate: HTShapePredicate
        ): HTShapeNew = HTShapeNew(key, predicate).also {
            registry.putIfAbsent(key, it)
            LOGGER.info("Shape: $key registered!")
        }

    }

}