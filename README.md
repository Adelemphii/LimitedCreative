# LimitedCreative

### The rewrite is here!

Safely allow your players to access creative mode without fear of them abusing it. Constantly updated with suggested features and bugfixes.
This plugin will work on any update **AFTER 1.14**, and if there is a version specific bug, make an issue on github!
Check out the video for this plugin!
[YouTube Showcase](https://youtu.be/Z21t_WqM8ho)

## Commands:
- /limitedcreative, /lc, or /lc self - give yourself LC (requires limitedcreative.use)
- /limitedcreative give <target> - give others LC (Requires limitedcreative.give)
- /limitedcreative nightvision/nv - give yourself nightvision while in LC (requires limitedcreative.use)
- /limitedcreative give nightvision <target> - give others nightvision while in LC (requires limitedcreative.give)
- /limitedcreative playerlist/plist - view all players in LC (requires limitedcreative.mod)
- /limitedcreative reload - reload the config file to update any changes (limitedcreative.admin)

## Features:
- Custom blacklisted blocks!
- Custom blacklisted interactables!
- Custom blacklisted items!
- Custom blacklisted breakables (What blocks cannot be broken by players in LC)!
- Custom blacklisted commands (Prevent certain commands from being run while a player is in LC)!
- Saves inventory between Survival and Creative gamemodes, restores them on crash/disconnect!
- Players in LC are unable to deal damage to entities and players (Toggleable in config.yml, "player-damage-entities")!
- Players in LC are distinguished from players that aren't with a GLOWING effect and RED leather armor. (Configurable in config.yml)!
- Players in LC cannot build Withers, Iron Golems, or Snow Golems!
- Nightvision while in LC using "/limitedcreative nightvision"!
- A check to remove fall damage when a player leaves LC while flying!
- A check to give players fly back if they were flying when entering LC!
- Most messages configurable to send whatever message is desired!
- Placeholders for modular error messages when someone in LC tries to break/use/place blocked items or blocks!
- No file storage needed, using a custom method to store data in chunks, blocks placed by players in LC do not have drops when broken!

## Planned Features:

## Permissions:
- "limitedcreative.use" - the base permission allowing one to run the /limitedcreative command.
- "limitedcreative.admin" - users with this are given permission to bypass the blacklist for blocks and items and reload the config.
- "limitedcreative.mod" - users with this are able to run the /limitedcreative playerlist command.
- "limitedcreative.give" - users with this are able to give LC & nightvision to others who do not have the "limitedcreative" permission.
 
# Issues
  - Make an issue / feature request [here](https://github.com/Adelemphii/LimitedCreative/issues)! 

# How To Use
To use this plugin, all you, as the user, has to do is place the LimitedCreative.jar file into your "Plugins" folder on your 1.13+ server. After booting up the server for the first time, a folder within your plugins folder named "LimitedCreative" is made. Inside that folder is a config.yml file, which you can configure. 

1. Stop your server.
2. Download the .jar file and put it into the /plugins folder of your server
3. Start the server, let it generate files into /plugins/LimitedCreative folder and then shut it down again.
4. Edit the config.yml file to your liking and save the file.
5. Start the server and use the plugin!

### Resources
[YouTube Showcase](https://youtu.be/Z21t_WqM8ho)

# **The SpigotMC page!**
[SpigotMC Page](https://www.spigotmc.org/resources/limitedcreative.88444/)
