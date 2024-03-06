package io.github.hiiragi283.api.tag

import io.github.hiiragi283.api.extension.createEvent
import net.fabricmc.fabric.api.event.Event
import net.minecraft.registry.tag.TagGroupLoader
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Identifier

fun interface GlobalTagEvent {
    fun register(handler: Handler)

    companion object {
        private fun createEvent(): Event<GlobalTagEvent> = createEvent { callbacks ->
            GlobalTagEvent { handler -> callbacks.forEach { it.register(handler) } }
        }

        @JvmStatic
        val BLOCK: Event<GlobalTagEvent> = createEvent()

        @JvmStatic
        val ITEM: Event<GlobalTagEvent> = createEvent()

        @JvmStatic
        val FLUID: Event<GlobalTagEvent> = createEvent()

        @JvmStatic
        val ENTITY_TYPE: Event<GlobalTagEvent> = createEvent()
    }

    class Handler(val map: MutableMap<Identifier, MutableList<TagGroupLoader.TrackedEntry>>) {
        inline fun <reified T> builder(tagKey: TagKey<T>): MutableList<TagGroupLoader.TrackedEntry> = builder(tagKey.id)

        fun builder(id: Identifier): MutableList<TagGroupLoader.TrackedEntry> = map.computeIfAbsent(id) { mutableListOf() }
    }
}
