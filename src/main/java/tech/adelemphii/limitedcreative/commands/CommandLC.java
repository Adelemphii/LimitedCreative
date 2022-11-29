package tech.adelemphii.limitedcreative.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import tech.adelemphii.limitedcreative.LimitedCreative;
import tech.adelemphii.limitedcreative.managers.ConfigHandler;
import tech.adelemphii.limitedcreative.utility.ChatUtility;

@SuppressWarnings("unused")
@CommandAlias("limitedcreative|lc")
public class CommandLC extends BaseCommand {

    private final LimitedCreative plugin;
    public CommandLC(LimitedCreative plugin) {
        this.plugin = plugin;
    }

    @Default
    @CommandPermission("limitedcreative.use")
    @Description("Toggle LC for yourself")
    public void invoke(Player player) {
        if(plugin.getManager().isInLC(player.getUniqueId())) {
            plugin.getManager().leaveLC(player, false);
        } else {
            plugin.getManager().enterLC(player);
        }
    }

    @Subcommand("self")
    @CommandPermission("limitedcreative.use")
    @Description("Toggle LC for yourself (Alt: /lc)")
    public void toggle(Player player) {
        if(plugin.getManager().isInLC(player.getUniqueId())) {
            plugin.getManager().leaveLC(player, false);
        } else {
            plugin.getManager().enterLC(player);
        }
    }

    @Subcommand("give")
    @Description("Toggle LC for another player")
    @Syntax("<name>")
    @CommandPermission("limitedcreative.give")
    public void giveLC(Player player, Player other) {
        if(plugin.getManager().isInLC(other.getUniqueId())) {
            plugin.getManager().leaveLC(other, false);
        } else {
            plugin.getManager().enterLC(other);
        }
        String message = plugin.getConfigHandler().placeholder(plugin.getConfigHandler().getPlayerToggleLCOtherMessage(), other);
        ChatUtility.sendMessage(player, message);
    }

    @Subcommand("nightvision|nv")
    @Description("Toggle nightvision for yourself")
    @CommandPermission("limitedcreative.use")
    public void onNightvision(Player player) {
        if(plugin.getManager().isInLC(player.getUniqueId())) {
            if(player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                player.removePotionEffect(PotionEffectType.NIGHT_VISION);
            } else {
                player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 1));
            }
        }
        ChatUtility.sendMessage(player, plugin.getConfigHandler().getPlayerToggleNightvisionMessage());
    }

    @Subcommand("give nightvision")
    @Description("Toggle nightvision for another player")
    @Syntax("<name>")
    @CommandPermission("limitedcreative.give")
    public void onNightvisionGive(Player sender, Player other) {
        if(plugin.getManager().isInLC(other.getUniqueId())) {
            if(other.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                other.removePotionEffect(PotionEffectType.NIGHT_VISION);
            } else {
                other.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 1));
            }
        }
        String message = plugin.getConfigHandler().placeholder(plugin.getConfigHandler().getPlayerToggleNightvisionOtherMessage(), other);
        ChatUtility.sendMessage(sender, message);
        ChatUtility.sendMessage(other, plugin.getConfigHandler().getPlayerToggleNightvisionMessage());
    }

    @Subcommand("reload")
    @Description("Reload the config file")
    @CommandPermission("limitedcreative.admin")
    public void onReload(Player player) {
        plugin.saveConfig();
        plugin.reloadConfig();
        plugin.setConfigHandler(new ConfigHandler(plugin));

        ChatUtility.sendMessage(player, plugin.getConfigHandler().getConfigReloadedMessage());
    }

    @Subcommand("info")
    @Description("Get info about the plugin")
    @CommandPermission("limitedcreative.admin")
    public void onInfo(Player player) {
        ChatUtility.sendMessage(player, "&cLimitedCreative: Version-" + plugin.getDescription().getVersion());
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c  Description: " + plugin.getDescription().getDescription()));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c  Author: " + plugin.getDescription().getAuthors().get(0)));
    }

    @HelpCommand
    public void onHelp(CommandHelp help) {
        help.showHelp();
    }
}
