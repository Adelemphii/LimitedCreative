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
        	
        	// Need to implement a legit way to do this, instead of this
        	// This causes the player to fall through half-blocks, and spawn inside blocks
        	/*
            if(player.isFlying()) {
	            Location playerLoc = player.getLocation();
	            Block newLoc = playerLoc.getWorld().getHighestBlockAt(playerLoc);
	            
	            player.teleport(newLoc.getLocation());
	            player.sendMessage("Teleporting you to a safe location.");
            }
            */
        	
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
