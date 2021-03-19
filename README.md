# LimitedCreative

## This project has been dropped, contact Adelemphii#6213 on discord if you wish to continue it. I'll hand over the Trello page of planned features/current bugs. (I'll help figure out issues with the current version, and even push bugfixes, however I wont be implementing new features after this point.)

Safely allow your players to access creative mode without fear of them abusing it. Constantly updated with suggested features and bugfixes.
Check out the video for this plugin!
[Youtube Showcase](https://youtu.be/Z21t_WqM8ho)

## Commands:
- /limitedcreative or /lc - give yourself LC (requires limitedcreative)
- /limitedcreative <Target> or /lc <Target> - give others LC (Requires limitedcreative.give)
- /limitedcreative nightvision/nv or /lc nightvision/nv - give yourself nightvision while in LC (requires limitedcreative)
- /limitedcreative reload or /lc reload - reload the config file to update any changes (Requires OP or limitedcreative.admin)

## Features:
- Custom blacklisted blocks
- Custom blacklisted interactables
- Custom blacklisted entities
- Custom blacklisted breakables (what blocks cannot be broken by players in LC without the "limitedcreative.admin" permission)
- Saves inventories between Survival and Creative gamemodes, restores them on crash/disconnect
- Players in LC are unable to deal damage to entities and players (Toggleable in config.yml using the "player-damage-entities:" option, false by default)
- Players in LC are distinguished from players that aren't with either a GLOWING effect, or RED leather armor. (This can be changed in the config, it is on GLOWING by default.
- Nightvision command using /lc nightvision
- A "gamemode-flycheck" configuration option to turn off the check if a player is flying or not when they change from LC to survival
- Placeholders for modular error messages when someone in LC tries to break/use/place blocked entities or blocks

## Planned Features:
I'm looking into the ability to block the creation of certain mobs via multiblock structures (i.e. wither, iron golem). Until then, I recommend blocking key blocks (wither skeleton skull) from being placed.
I am also learning various database programs to allow the tracking of blocks placed while in LC, alongside other features.

## Permissions:
- "limitedcreative" - the base permission allowing one to run the /limitedcreative or /lc command. Also allows them to give LC to other players.
- "limitedcreative.admin" - users with this are given permission to bypass the blacklist for blocks and interactables and give others LC.
- "limitedcreative.give" - users with this are able to give LC to others who do not have the "limitedcreative" permission.

## Known Bugs:
 None!
 
# Issues
  - To report a bug, either write an issue report on this repository or send me a DM on Discord **@Adelemphii#6213**
  - You can also join my [Discord](https://discord.com/invite/sX6FUau) for quicker support!

# How To Use
To use this plugin, all you, as the user, has to do is place the LimitedCreative.jar file into your "Plugins" folder on your 1.13+ server. After booting up the server for the first time, a folder within your plugins folder named "LimitedCreative" is made. Inside that folder is a config.yml file, which you can configure. 

1. Stop your server.
2. Download the .jar file and put it into the /plugins folder of your server
3. Start the server, let it generate files into /plugins/LimitedCreative folder and then shut it down again.
4. Edit the config.yml file to your liking and save the file.
5. Start the server and use the plugin!

### The "master" branch contains the code for this project.

### Resources
[Youtube Showcase](https://youtu.be/Z21t_WqM8ho)

# **The SpigotMC page is updated more often than the Github Readme!**
[SpigotMC Page](https://www.spigotmc.org/resources/limitedcreative.88444/)
