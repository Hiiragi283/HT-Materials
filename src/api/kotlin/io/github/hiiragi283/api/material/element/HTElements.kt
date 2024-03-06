package io.github.hiiragi283.api.material.element

import io.github.hiiragi283.api.extension.HTColor
import io.github.hiiragi283.api.extension.averageColor
import io.github.hiiragi283.api.extension.commonId
import java.awt.Color

object HTElements {
    //    Elements    //

    @JvmField
    val H = HTElement.of(
        commonId("hydrogen"),
        HTColor.BLUE,
        "H",
        1.0,
    )

    @JvmField
    val He = HTElement.of(
        commonId("helium"),
        HTColor.YELLOW,
        "He",
        4.0,
    )

    @JvmField
    val Li = HTElement.of(
        commonId("lithium"),
        HTColor.GRAY,
        "Li",
        6.9,
    )

    @JvmField
    val Be = HTElement.of(
        commonId("beryllium"),
        HTColor.DARK_GREEN,
        "Be",
        9.0,
    )

    @JvmField
    val C = HTElement.of(
        commonId("carbon"),
        averageColor(HTColor.BLACK, HTColor.DARK_GRAY),
        "C",
        12.0,
    )

    @JvmField
    val N = HTElement.of(
        commonId("nitrogen"),
        HTColor.AQUA,
        "N",
        14.0,
    )

    @JvmField
    val O = HTElement.of(
        commonId("oxygen"),
        HTColor.WHITE,
        "O",
        16.0,
    )

    @JvmField
    val F = HTElement.of(
        commonId("fluorine"),
        HTColor.GREEN,
        "F",
        19.0,
    )

    @JvmField
    val Na = HTElement.of(
        commonId("sodium"),
        averageColor(HTColor.DARK_BLUE to 1, HTColor.BLUE to 4),
        "Na",
        23.0,
    )

    @JvmField
    val Mg = HTElement.of(
        commonId("magnesium"),
        HTColor.GRAY,
        "Mg",
        24.0,
    )

    @JvmField
    val Al = HTElement.of(
        commonId("aluminum"),
        averageColor(HTColor.BLUE to 1, HTColor.WHITE to 5),
        "Al",
        27.0,
    )

    @JvmField
    val Si = HTElement.of(
        commonId("silicon"),
        averageColor(HTColor.BLACK to 2, HTColor.GRAY to 1, HTColor.BLUE to 1),
        "Si",
        28.1,
    )

    @JvmField
    val P = HTElement.of(
        commonId("phosphorus"),
        HTColor.YELLOW,
        "P",
        31.0,
    )

    @JvmField
    val S = HTElement.of(
        commonId("sulfur"),
        averageColor(HTColor.GOLD, HTColor.YELLOW),
        "S",
        32.1,
    )

    @JvmField
    val Cl = HTElement.of(
        commonId("chlorine"),
        HTColor.YELLOW,
        "Cl",
        35.5,
    )

    @JvmField
    val K = HTElement.of(
        commonId("potassium"),
        averageColor(HTColor.DARK_BLUE to 2, HTColor.BLUE to 3),
        "K",
        39.1,
    )

    @JvmField
    val Ca = HTElement.of(
        commonId("calcium"),
        HTColor.GRAY,
        "Ca",
        40.1,
    )

    @JvmField
    val Ti = HTElement.of(
        commonId("titanium"),
        averageColor(HTColor.GOLD to 1, HTColor.WHITE to 2),
        "Ti",
        47.9,
    )

    @JvmField
    val Cr = HTElement.of(
        commonId("chromium"),
        HTColor.GREEN,
        "Cr",
        52.0,
    )

    @JvmField
    val Mn = HTElement.of(
        commonId("manganese"),
        HTColor.GRAY,
        "Mn",
        54.9,
    )

    @JvmField
    val Fe = HTElement.of(
        commonId("iron"),
        HTColor.WHITE,
        "Fe",
        55.8,
    )

    @JvmField
    val Co = HTElement.of(
        commonId("cobalt"),
        HTColor.BLUE,
        "Co",
        58.9,
    )

    @JvmField
    val Ni = HTElement.of(
        commonId("nickel"),
        averageColor(HTColor.GOLD to 2, HTColor.GREEN to 1, HTColor.WHITE to 1),
        "Ni",
        58.7,
    )

    @JvmField
    val Cu = HTElement.of(
        commonId("copper"),
        averageColor(HTColor.GOLD, HTColor.RED),
        "Cu",
        63.5,
    )

    @JvmField
    val Zn = HTElement.of(
        commonId("zinc"),
        averageColor(HTColor.GREEN to 1, HTColor.WHITE to 2),
        "Zn",
        65.4,
    )

    @JvmField
    val Ag = HTElement.of(
        commonId("silver"),
        averageColor(HTColor.AQUA to 1, HTColor.WHITE to 3),
        "Ag",
        107.9,
    )

    @JvmField
    val Sn = HTElement.of(
        commonId("tin"),
        averageColor(HTColor.BLUE to 1, HTColor.AQUA to 1, HTColor.WHITE to 3),
        "Sn",
        118.7,
    )

    @JvmField
    val W = HTElement.of(
        commonId("tungsten"),
        averageColor(HTColor.BLACK to 2, HTColor.DARK_GRAY to 1),
        "W",
        183.8,
    )

    @JvmField
    val Ir = HTElement.of(
        commonId("iridium"),
        HTColor.WHITE,
        "Ir",
        192.2,
    )

    @JvmField
    val Pt = HTElement.of(
        commonId("platinum"),
        Color(0x87cefa),
        "Pt",
        195.1,
    )

    @JvmField
    val Au = HTElement.of(
        commonId("gold"),
        averageColor(HTColor.GOLD, HTColor.YELLOW),
        "Au",
        197.0,
    )

    @JvmField
    val Hg = HTElement.of(
        commonId("mercury"),
        HTColor.WHITE,
        "Hg",
        200.6,
    )

    @JvmField
    val Pb = HTElement.of(
        commonId("lead"),
        averageColor(HTColor.DARK_BLUE, HTColor.DARK_GRAY, HTColor.WHITE),
        "Pb",
        207.2,
    )

    @JvmField
    val U = HTElement.of(
        commonId("uranium"),
        HTColor.GREEN,
        "U",
        238.0,
    )

    @JvmField
    val Pu = HTElement.of(
        commonId("plutonium"),
        HTColor.RED,
        "Pu",
        244.1,
    )

    @JvmField
    val Nr = HTElement.of(
        commonId("netherite"),
        averageColor(
            HTColor.BLACK to 5,
            HTColor.DARK_BLUE to 1,
            HTColor.DARK_RED to 1,
            HTColor.YELLOW to 1,
        ),
        "Nr",
        116.5,
    )

    //    Groups    //

    @JvmField
    val CO3 = HTElement.group(commonId("carbonate"), C to 1, O to 3)

    @JvmField
    val Al2O3 = HTElement.group(commonId("aluminum_oxide"), Al to 2, O to 3)

    @JvmField
    val SiO2 = HTElement.group(commonId("silicon_oxide"), Si to 1, O to 1)
}
