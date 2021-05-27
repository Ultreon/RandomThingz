# QForgeMod
*Probably just another random things mod.*

***
## Pages
The GitHub pages are here: https://qtechcommunity.github.io/QForgeMod/.

## Running from source
1) Clone the [**QModLib**](https://randomthingzQModLib) repository.  
2) Change the variable `local_maven` in the `gradle.properties` file of your QModLib cloned repo to an empty folder, or a local maven repo.
3) Execute gradle command `uploadJars`, or the command `build :jar`. *This will upload it to the local maven repo.*
4) Clone the [**Random Thingz**](https://github.com/QTechCommunity/RandomThingz) repository.
5) Change the variable `local_maven` in the `gradle.properties` file of your Random Thingz cloned repo to the same folder set in step 2.
6) Run gradle command runClient, or use the runClient run config in your IDE.

## Planned features.
 - [ ] Spell checking in the chat, based on current selected language, uses libreoffice `.dic` files.
 - [ ] Blaze knight.

    
### Planned but not sure.
 - [ ] Advanced machines.
 - [ ] API
 - [ ] Skin editor.

## Features.
 - [x] Moobloom entity.
 - [x] Multiple moobloom variants.
 - [x] Cluckshroom.
 - [x] Mod updater for **QForgeMod** including api.
 - [ ] ~~Javascript console ingame. (An alternative to commands)~~

## Build numbers.
On a merge, the build number will be recalculated. *See image below:*  
![Workflow](https://github.com/QTechCommunity/RandomThingz/raw/master/img/Image1.png)
