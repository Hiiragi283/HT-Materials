# HT Materials

This is a simple Minecraft mod that provides _**Material System**_ and _**Tag Sync**_

---

## Fabric

![Requires Fabric API](https://i.imgur.com/Ol1Tcf8.png)
![Requires Fabric Language Kotlin](https://i.imgur.com/c1DH9VL.png)

### [Fabric+1.16.5](https://github.com/Robustum/HT-Materials)

### [Fabric+1.18.2](https://github.com/Hiiragi283/HT-Materials/tree/fabric_1182)

---

## [WIP] Forge

### [Forge+1.16.5](https://github.com/Hiiragi283/HT-Materials/tree/forge_1165)

---

## Material System

The Material System was invented to handle Tags more generally by decomposing them into `HTMaterial` and `HTShape`.  
`HTMaterial` represents the material of objects: _Iron_, _Gold_, _Copper_, _Stone_, _Wood_, ...  
`HTShape` represents the shape of objects: _Ingot_, _Nugget_, _Plate_, _Gear_, _Rod_, ...

![Material System](https://github.com/Hiiragi283/HT-Materials/blob/main/images/material_system.png?raw=true)

## Material Item

HT Materials can automatically generate simple ingredient items, called _Material Item_ based on the Material
System.

![Material Item](https://github.com/Hiiragi283/HT-Materials/blob/main/images/material_item.png?raw=true)

## Tag Sync

Many Fabric mods follows `Conventional Tag` format such as `c:zinc_ingots` or `c:raw_iron_ore`, but some mods
especially Create follows `Forge Tag` format such as `c:ingots/zinc` or `c:raw_materials/iron`. There is NO
compatability between them. HT Materials synchronizes these different format automatically by Material System.

![Tag Sync1](https://github.com/Hiiragi283/HT-Materials/blob/main/images/tag_sync.png?raw=true)

## Fluid Unification

HT Materials can sync not only tags but also fluids! After the flattening, fluid became vanilla feature and has been
managed with Identifier: namespace and path. This destructive change divided fluid with same name and different
namespace. Based on the Material System, there fluids are linked to Conventional Tags.

![Fluid Unification](https://github.com/Hiiragi283/HT-Materials/blob/main/images/fluid_unification.png?raw=true)