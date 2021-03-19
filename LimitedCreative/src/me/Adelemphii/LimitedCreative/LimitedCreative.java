package me.Adelemphii.LimitedCreative;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

import me.Adelemphii.LimitedCreative.Events.Events;
import me.Adelemphii.LimitedCreative.Metrics.Metrics;

public class LimitedCreative extends JavaPlugin {
	
	/*
	 * 
	 *	LimitedCreative by Adelemphii
	 *	v1.4.1
	 *  This project has been dropped, contact Adelemphii#6213 on discord if you wish to re-continue it.
	 *  I'll hand over the Trello page of planned features/current bugs.
	 *
	 *	Check out my SpigotMC for any updates to the code!
	 *	https://www.spigotmc.org/resources/limitedcreative.88444/
	 *
	 *	Or my Github!
	 *	https://github.com/Adelemphii/LimitedCreative
	 *
	 *  Or my Discord!
	 *  https://discord.com/invite/sX6FUau
	 *
	 */
	
	// Should eventually change it from Player, UUID to String, UUID. Or a hashset I guess?
    public HashMap<Player, UUID> lc = new HashMap<>();
    public HashMap<UUID, ItemStack[]> invs = new HashMap<>();
    private int pluginID = 10548;
    
    public void onEnable() {	
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new Events(this), this);
        
        this.getCommand("LimitedCreative").setExecutor(new Creative(this));
        
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        
        @SuppressWarnings("unused")
		Metrics metrics = new Metrics(this, pluginID);
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
        	Boolean flyCheck = this.getConfig().getBoolean("gamemode-flycheck");
        	if(flyCheck) {
	        	if(player.isFlying()) {
	        		Location loc = player.getLocation();
	        		Block highestBlock;
	        		
	        		for(int y = loc.getBlockY() - 1; y > 0; y--) {
	        			loc.subtract(0, 1, 0);
	        			highestBlock = loc.getBlock();
	        			if(highestBlock.getType() != Material.AIR) {
	        				loc.add(0, 1, 0);
	        				player.teleport(loc);
	        				player.sendMessage(ChatColor.RED + "Warning: Detected player in air! Teleporting you to a safe location.");
	        				break;
	        			}
	        		}
	        	}
        	}
        	
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
