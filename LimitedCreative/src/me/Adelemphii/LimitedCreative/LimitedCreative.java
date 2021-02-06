package me.Adelemphii.LimitedCreative;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

import me.Adelemphii.LimitedCreative.Events.Events;

public class LimitedCreative extends JavaPlugin {
	
	/*
	 *	LimitedCreative by Adelemphii
	 *	Check out my github for any updates to the code!
	 *	https://github.com/Adelemphii/LimitedCreative
	 */
	
	// Should eventually change it from Player, UUID to String, UUID.
    public HashMap<Player, UUID> lc = new HashMap<>();
    public HashMap<UUID, ItemStack[]> invs = new HashMap<>();
    
    public void onEnable() {
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new Events(this), this);
        
        this.getCommand("LimitedCreative").setExecutor(new Creative(this));
        
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
    }
    
    public void onDisable() {
        restoreInventoryOnCrash();
    }
    
    public void saveInventory(Player player) {
        if (!player.getInventory().isEmpty()) {
            this.invs.put(player.getUniqueId(), player.getInventory().getContents());
            player.getInventory().clear();
        }
    }
    
    public void restoreInventory(Player player) {
        if (!invs.containsKey(player.getUniqueId())) {
            return;
        }
        player.getInventory().setContents(invs.get(player.getUniqueId()));
        player.updateInventory();
        invs.remove(player.getUniqueId());
    }
    
    public void restoreInventoryOnCrash() {
        for (Player player : this.lc.keySet()) {
            player.setGameMode(GameMode.SURVIVAL);
            player.removePotionEffect(PotionEffectType.GLOWING);
            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
        }
        for (Map.Entry<UUID, ItemStack[]> entry : this.invs.entrySet()) {
            Player player2 = Bukkit.getPlayer(entry.getKey());
            player2.getInventory().setContents(entry.getValue());
        }
    }
}
