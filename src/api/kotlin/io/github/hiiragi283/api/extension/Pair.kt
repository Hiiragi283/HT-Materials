package io.github.hiiragi283.api.extension

import com.mojang.datafixers.util.Pair

//    mojang -> kotlin    //

fun <A, B> Pair<A, B>.convert(): kotlin.Pair<A, B> = this.first to this.second

fun <A, B> List<Pair<A, B>>.toKotlinList(): List<kotlin.Pair<A, B>> = map(Pair<A, B>::convert)

fun <A, B> Map<A, B>.toMojangList(): List<Pair<A, B>> = toList().toMojangList()

//    kotlin -> mojang    //

fun <A, B> kotlin.Pair<A, B>.convert(): Pair<A, B> = Pair(this.first, this.second)

fun <A, B> List<kotlin.Pair<A, B>>.toMojangList(): List<Pair<A, B>> = map(kotlin.Pair<A, B>::convert)

fun <A, B> List<Pair<A, B>>.toMap(): Map<A, B> = toKotlinList().toMap()
