package tech.adelemphii.limitedcreative.managers;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import tech.adelemphii.limitedcreative.LimitedCreative;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConfigHandler {

    private final LimitedCreative plugin;

    private final boolean playerDamageEntities;
    private final boolean glowing;
    private final boolean armor;
    private final String armorColor;

    private final String blacklistBreakableMessage;
    private final String blacklistInteractableMessage;
    private final String blacklistItemMessage;
    private final String blacklistBlockPlaceMessage;
    private final String blacklistedCommandMessage;

    private final Set<Material> blacklistedPlaceables;
    private final Set<Material> blacklistedInteractables;
    private final Set<Material> blacklistedItems;
    private final Set<Material> blacklistedBreakables;
    private final Set<String> blacklistedCommands;

    private final String playerExitLCMessage;
    private final String playerEnterLCMessage;
    private final String playerToggleLCOtherMessage;
    private final String playerDropItemMessage;
    private final String playerInteractEntitiesMessage;
    private final String playerManipulateArmorStandMessage;
    private final String playerNoPermissionMessage;
    private final String playerToggleNightvisionMessage;
    private final String playerToggleNightvisionOtherMessage;
    private final String configReloadedMessage;

    public ConfigHandler(LimitedCreative plugin) {
        this.plugin = plugin;
        FileConfiguration config = plugin.getConfig();

        this.playerDamageEntities = config.getBoolean("player-damage-entities", false);
        this.glowing = config.getBoolean("glowing", false);
        this.armor = config.getBoolean("armor", false);
        this.armorColor = config.getString("armor-color", "#fc0703");

        this.blacklistBreakableMessage = config.getString("blacklisted-breakable-message", "&cYou cannot break <type> while in LC!");
        this.blacklistInteractableMessage = config.getString("blacklisted-interactable-message", "&cYou cannot interact with <type> while in LC!");
        this.blacklistItemMessage = config.getString("blacklisted-item-message", "&cYou cannot use <type> while in LC!");
        this.blacklistBlockPlaceMessage = config.getString("blacklisted-block-message", "&cYou cannot place <type> while in LC!");
        this.blacklistedCommandMessage = config.getString("blacklisted-commmand-message", "&cThis command is disabled while in LC!");

        this.blacklistedPlaceables = getMaterialSet(config, "blacklisted-blocks");
        this.blacklistedInteractables = getMaterialSet(config, "blacklisted-interactables");
        this.blacklistedItems = getMaterialSet(config, "blacklisted-items");
        this.blacklistedBreakables = getMaterialSet(config, "blacklisted-breakables");
        this.blacklistedCommands = getStringSet(config, "blacklisted-commands");

        this.playerExitLCMessage = config.getString("player-exit-lc-message", "&aYou have been returned to survival mode. Inventory restored.");
        this.playerEnterLCMessage = config.getString("player-enter-lc-message", "&aLimitedCreative activated. Survival inventory saved.");
        this.playerToggleLCOtherMessage = config.getString("player-toggle-lc-other-message", "&aLimitedCreative toggled for <player>.");
        this.playerDropItemMessage = config.getString("player-drop-item-message", "&cYou cannot drop items while in LC.");
        this.playerInteractEntitiesMessage = config.getString("player-interact-entities-message", "&cYou cannot interact with entities while in LC!");
        this.playerManipulateArmorStandMessage = config.getString("player-manipulate-armorstand-message", "&cYou cannot use Armor Stands while in LC!");
        this.playerNoPermissionMessage = config.getString("player-no-permission-message", "&cYou do not have permission to do use that command!");
        this.playerToggleNightvisionMessage = config.getString("player-nightvision-toggle", "&aNightvision effect toggled.");
        this.playerToggleNightvisionOtherMessage = config.getString("player-nightvision-toggle-other", "&aNightvision effect toggled for: <player>");
        this.configReloadedMessage = config.getString("config-reloaded-message", "&aLimitedCreative: Config Reloaded.");

    }

    private Set<Material> getMaterialSet(FileConfiguration config, String path) {
        List<String> strings = config.getStringList(path);
        Set<Material> materials = new HashSet<>();

        for (String string : strings) {
            Material material = Material.getMaterial(string.toUpperCase());

            if(material != null) {
                materials.add(material);
            }
        }
        return materials;
    }

    private Set<String> getStringSet(FileConfiguration config, String path) {
        List<String> stringList = config.getStringList(path);

        return new HashSet<>(stringList);
    }

    public String placeholder(String string, Material material) {
        if(material != null) {
            string = string.replaceAll("<type>", material.name());
        }
        return string;
    }

    public String placeholder(String string, Player player) {
        if(player != null) {
            string = string.replaceAll("<player>", player.getName());
        }
        return string;
    }

    public LimitedCreative getPlugin() {
        return plugin;
    }

    public boolean playerDamageEntities() {
        return playerDamageEntities;
    }

    public boolean isGlowing() {
        return glowing;
    }

    public boolean isArmor() {
        return armor;
    }

    public String getArmorColor() {
        return armorColor;
    }

    public String getBlacklistBreakableMessage() {
        return blacklistBreakableMessage;
    }

    public String getBlacklistInteractableMessage() {
        return blacklistInteractableMessage;
    }

    public String getBlacklistItemMessage() {
        return blacklistItemMessage;
    }

    public String getBlacklistBlockPlaceMessage() {
        return blacklistBlockPlaceMessage;
    }

    public Set<Material> getBlacklistedPlaceables() {
        return blacklistedPlaceables;
    }

    public Set<Material> getBlacklistedInteractables() {
        return blacklistedInteractables;
    }

    public Set<Material> getBlacklistedItems() {
        return blacklistedItems;
    }

    public Set<Material> getBlacklistedBreakables() {
        return blacklistedBreakables;
    }

    public String getPlayerExitLCMessage() {
        return playerExitLCMessage;
    }

    public String getPlayerEnterLCMessage() {
        return playerEnterLCMessage;
    }

    public String getPlayerDropItemMessage() {
        return playerDropItemMessage;
    }

    public String getPlayerInteractEntitiesMessage() {
        return playerInteractEntitiesMessage;
    }

    public String getPlayerManipulateArmorStandMessage() {
        return playerManipulateArmorStandMessage;
    }

    public Set<String> getBlacklistedCommands() {
        return blacklistedCommands;
    }

    public String getBlacklistedCommandMessage() {
        return blacklistedCommandMessage;
    }

    public String getPlayerNoPermissionMessage() {
        return playerNoPermissionMessage;
    }

    public String getPlayerToggleNightvisionMessage() {
        return playerToggleNightvisionMessage;
    }

    public String getPlayerToggleNightvisionOtherMessage() {
        return playerToggleNightvisionOtherMessage;
    }

    public String getPlayerToggleLCOtherMessage() {
        return playerToggleLCOtherMessage;
    }

    public String getConfigReloadedMessage() {
        return configReloadedMessage;
    }
}
