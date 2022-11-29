package tech.adelemphii.limitedcreative;

import co.aikar.commands.BukkitCommandManager;
import com.tchristofferson.configupdater.ConfigUpdater;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import tech.adelemphii.limitedcreative.commands.CommandLC;
import tech.adelemphii.limitedcreative.commands.CommandPlayerList;
import tech.adelemphii.limitedcreative.listeners.*;
import tech.adelemphii.limitedcreative.managers.ConfigHandler;
import tech.adelemphii.limitedcreative.managers.LimitedCreativeManager;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

public final class LimitedCreative extends JavaPlugin {

    // https://github.com/aikar/commands/wiki/Using-ACF
    private BukkitCommandManager commandManager;
    private ConfigHandler configHandler;
    private LimitedCreativeManager manager;

    private NamespacedKey placedInLCKey;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        try {
            ConfigUpdater.update(this, "config.yml", new File(getDataFolder(), "config.yml"),
                    Collections.singletonList("placeholder123"));
        } catch (IOException e) {
            System.out.println("An error occurred while attempting to update config.");
            e.printStackTrace();
        }
        reloadConfig();

        placedInLCKey = new NamespacedKey(this, "placed-in-lc");
        commandManager = new BukkitCommandManager(this);
        commandManager.enableUnstableAPI("help");

        this.configHandler = new ConfigHandler(this);

        manager = new LimitedCreativeManager(this);

        registerCommands();
        registerListeners();
    }

    @Override
    public void onDisable() {
        getServer().getOnlinePlayers().stream()
                .filter(player -> manager.isInLC(player.getUniqueId()))
                .forEach(player -> manager.leaveLC(player, true));
    }

    private void registerCommands() {
        commandManager.registerCommand(new CommandLC(this));
        commandManager.registerCommand(new CommandPlayerList(this));
    }

    private void registerListeners() {
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new FallSafeListener(this), this);
        pm.registerEvents(new ArmorStandListener(this), this);
        pm.registerEvents(new BlacklistedCommandListener(this), this);
        pm.registerEvents(new BlockInteractionListener(this), this);
        pm.registerEvents(new BlockListeners(this), this);
        pm.registerEvents(new BlockPlaceLogger(this), this);
        pm.registerEvents(new ContainerListeners(this), this);
        pm.registerEvents(new DroppedItemListeners(this), this);
        pm.registerEvents(new EntityClickListener(this), this);
        pm.registerEvents(new InventoryListener(this), this);
        pm.registerEvents(new ItemUseListener(this), this);
        pm.registerEvents(new PlayerDamageListener(this), this);
        pm.registerEvents(new PlayerDeathListener(this), this);
        pm.registerEvents(new PlayerLeaveListener(this), this);
        pm.registerEvents(new MilkConsumeListener(this), this);
        pm.registerEvents(new ProjectileListener(this), this);
        pm.registerEvents(new PreventGolemCreationListener(this), this);
    }

    public ConfigHandler getConfigHandler() {
        return configHandler;
    }

    public void setConfigHandler(ConfigHandler configHandler) {
        this.configHandler = configHandler;
    }

    public LimitedCreativeManager getManager() {
        return manager;
    }

    public NamespacedKey getPlacedInLCKey() {
        return placedInLCKey;
    }
}
