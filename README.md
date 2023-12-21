# HT Materials for Fabric

This is a simple Minecraft mod for **Fabric/Quilt 1.18.2** that provides _**Material System**_ and _**Tag Sync**_

## Material System

The Material System was invented to handle Tags more generally by decomposing them into `HTMaterial` and `HTShape`.  
`HTMaterial` represents the material of objects: _Iron_, _Gold_, _Copper_, _Stone_, _Wood_, ...  
`HTShape` represents the shape of objects: _Ingot_, _Nugget_, _Plate_, _Gear_, _Rod_, ...

![Material System](/images/material_system.png)

## Material Item

HT Materials can automatically generate simple ingredient items, called _Material Item_ based on the Material
System.

![Material Item](/images/material_item.png)

## Tag Sync

Many Fabric mods follows `Conventional Tag` format such as `c:zinc_ingots` or `c:raw_iron_ore`, but some mods
especially Create follows `Forge Tag` format such as `c:ingots/zinc` or `c:raw_materials/iron`. There is NO
compatability between them. HT Materials synchronizes these different format automatically by Material System.

![Tag Sync1](/images/tag_sync.png)

## Fluid Unification

HT Materials can sync not only tags but also fluids! After the flattening, fluid became vanilla feature and has been
managed with Identifier: namespace and path. This destructive change divided fluid with same name and different
namespace. Based on the Material System, there fluids are linked to Conventional Tags.

![Fluid Unification](/images/fluid_unification.png)

## How to create Addon

1. Add new entrypoint `ht_materials` in `fabric.mod.json`
2. Implement `io.github.hiiragi283.material.api.addon.HTMaterialsAddon`
    1. `String getModId()` ... Return the modid of dependency
    2. `void registerShapes()` ... Register your custom `HTShape`s
    3. `void registerMaterials()` ... Register your custom `HTMaterial`s
    4. `void modifyMaterials()` ... Modify registered `HTMaterial`s
    5. `void commonSetup()` ... Called after all mods initialized
    6. `void clientSetup()` ... Similar to `commonSetUp()` but only called in Client-Side

### Example

- [fabric.mod.json](/src/main/resources/fabric.mod.json)
- [HTTestAddon](/src/main/java/io/github/hiiragi283/material/HTTestAddon.java)