# QForgeMod  

***
## Pages
The GitHub pages are here: https://qboi123.github.io/QForgeMod/.

## Running from source
1) Clone the [**QModLib**](https://github.com/Qboi123/QModLib) repository.  
2) Change the variable `local_maven` in the `gradle.properties` file of your QModLib cloned repo to an empty folder, or a local maven repo.
3) Execute gradle command `uploadJars`, or the command `build :jar`. *This will upload it to the local maven repo.*
4) Clone the [**QForgeMod**](https://github.com/Qboi123/QForgeMod) repository.
5) Change the variable `local_maven` in the `gradle.properties` file of your QForgeMod cloned repo to the same folder set in step 2.
6) Run gradle command runClient, or use the runClient run config in your IDE.

## Using as API
1) Set environment variables for your OS. ([Windows](https://www.tenforums.com/tutorials/121855-edit-user-system-environment-variables-windows.html), [Linux](https://www.serverlab.ca/tutorials/linux/administration-linux/how-to-set-environment-variables-in-linux/), [Mac](https://medium.com/@himanshuagarwal1395/setting-up-environment-variables-in-macos-sierra-f5978369b255#:~:text=If%20the%20environment%20variable%20you,variable%20name%20and%20its%20value.))  
   Add one with the name `GITHUB_USERNAME` and the value as your github username  
   Add one with the name `GITHUB_TOKEN` and the value as your [token](https://github.com/settings/tokens).
2) Add the repository (`https://maven.pkg.github.com/Qboi123/QForgeMod`)
   ```gradle
   repositories {
       maven {
           name = "GitHubPackages"
           url = uri("https://maven.pkg.github.com/Qboi123/QForgeMod")
           credentials {
               username = System.getenv("GITHUB_USERNAME")
               password = System.getenv("GITHUB_TOKEN")
           }
       }
   }
   ```
3) Add the dependencies (`com.qsoftware:qforgemod`, `com.qsoftware:qmodlib`)
   ```gradle
   dependencies {
       // Other dependencies here. //
       
       compileOnly fg.deobf("com.qsoftware:qmodlib")
       runtimeOnly fg.deobf("com.qsoftware:qmodlib")
       
       // Other dependencies here. //
   }
   ```
6) Reload gradle.
7) You're done!

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
 - [x] Javascript console ingame. (An alternative to commands)
