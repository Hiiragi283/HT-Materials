package io.github.hiiragi283.api.extension

import com.google.common.collect.ImmutableMap
import com.mojang.datafixers.util.Pair
import com.mojang.datafixers.util.Unit
import com.mojang.serialization.*
import java.util.function.Function
import java.util.stream.Stream

fun <T : Encodable<T>, U> encode(data: T, dynamicOps: DynamicOps<U>): U = data.codec.encodeStart(dynamicOps, data).result().orElseThrow()

fun <T, U> decode(decoder: Decoder<T>, data: U, dynamicOps: DynamicOps<U>): T = decoder.parse(dynamicOps, data).result().orElseThrow()

interface Encodable<T : Encodable<T>> {
    val codec: Encoder<T>
}

fun <K : Any, V : Any> keyDispatchingCodec(keyCodec: Codec<K>, keyToValue: (K) -> Codec<V>): Codec<Map<K, V>> = object : Codec<Map<K, V>> {
    override fun <T> encode(input: Map<K, V>, ops: DynamicOps<T>, prefix: T): DataResult<T> = ops.mapBuilder().apply {
        input.forEach { (key: K, value: V) ->
            add(
                keyCodec.encodeStart(ops, key),
                keyToValue(key).encodeStart(ops, value),
            )
        }
    }.build(prefix)

    override fun <T> decode(ops: DynamicOps<T>, input: T): DataResult<Pair<Map<K, V>, T>> =
        ops.getMap(input).flatMap { mapLike: MapLike<T> ->
            val builder: ImmutableMap.Builder<K, V> = ImmutableMap.builder()
            val streamBuilder: Stream.Builder<Pair<T, T>> = Stream.builder()
            val dataResult: DataResult<Unit> = mapLike.entries().reduce(
                DataResult.success(Unit.INSTANCE, Lifecycle.stable()),
                { result: DataResult<Unit>, entry: Pair<T, T> ->
                    val keyResult: DataResult<K> = keyCodec.parse(ops, entry.first)
                    val valueResult: DataResult<V> = keyResult.map(keyToValue).flatMap {
                        it.parse(ops, entry.second).map(Function.identity())
                    }
                    val pairResult: DataResult<Pair<K, V>> =
                        keyResult.apply2stable({ key, value -> Pair.of(key, value) }, valueResult)
                    pairResult.result().ifPresent { builder.put(it.first!!, it.second!!) }
                    pairResult.error().ifPresent { streamBuilder.add(entry) }
                    result.apply2stable({ unit: Unit, _ -> unit }, pairResult)
                },
            ) { a, b -> a.apply2stable({ unit, _ -> unit }, b) }
            val map: Map<K, V> = builder.build()
            val obj: T = ops.createMap(streamBuilder.build())
            dataResult.map { Pair.of(map, input) }.setPartial(Pair.of(map, input))
                .mapError { "$it missed input; $obj" }
        }
}
