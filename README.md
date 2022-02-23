# Ultreon Team
*Probably just another random things mod.*

***
## Ideas:
[Google Docs](https://docs.google.com/document/d/1z3MKoFRFzNu_Qmz0HYUsLOh5f-y4033F9UBfSapATMY/edit?usp=sharing)

## Pages
The GitHub pages are here: https://qtechcommunity.github.io/RandomThingz/.

## Running from source
1) Clone the [**QModLib**](https://github.com/Ultreon/ultreon-mod-lib) repository.  
2) Change the variable `local_maven` in the `gradle.properties` file of your QModLib cloned repo to an empty folder, or a local maven repo.
3) Execute gradle command `uploadJars`, or the command `build :jar`. *This will upload it to the local maven repo.*
4) Clone the [**Random Thingz**](https://github.com/Ultreon/RandomThingz) repository.
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
 - [x] Mod updater for **Ultreon Team** including api.
 - [ ] ~~Javascript console ingame. (An alternative to commands)~~

## Build numbers.
On a merge, the build number will be recalculated. *See image below:*  
![Workflow](https://github.com/Ultreon/RandomThingz/raw/master/img/Image1.png)
