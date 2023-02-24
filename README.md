This is a minecraft mod built for 1.18.2.

This mod uses Fabric.

The idea of this mod is to allow the player to maintain a checklist of all the items in the game.

The checklist state will be stored in local storage, and the player can give a name for each checklist.

Optionally, if the required API keys are provided, I'm planning to clone the state into a google sheet.

Everything will be running on the client only.

-----

Since I'm developing this on WSL, I can use this command to move the built JAR over to my mods folder.

```sh
cp ./build/libs/item-collection-tracker-1.0.0.jar /mnt/c/Users/VRAC4/AppData/Roaming/.minecraft/mods/item-collection-tracker-1.0.0.jar
```
