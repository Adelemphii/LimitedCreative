package me.Adelemphii.LimitedCreative.Events;

import me.Adelemphii.LimitedCreative.*;
import org.bukkit.event.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import java.util.*;
import org.bukkit.event.player.*;
import org.bukkit.event.block.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.entity.*;

public class Events implements Listener
{
	Main main;
	
	public Events(Main main) {
		this.main = main;
	}
    
	// Set player back to survival with their default inventory on leave.
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        if (main.lc.containsValue(event.getPlayer().getUniqueId())) {
            main.restoreInventory(event.getPlayer());
            event.getPlayer().setGameMode(GameMode.SURVIVAL);
            main.lc.remove(event.getPlayer(), event.getPlayer().getUniqueId());
        }
    }
    
    // Don't allow people in LC to damage entities.
    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (main.lc.containsKey(event.getDamager())) {
            event.setCancelled(true);
        }
    }
    
    // IF player does not have "limitedcreative.admin" permissions, do not let them place the blocks 
    // specified in config.yml 'blacklisted-blocks'
    @EventHandler
    public void checkBlockAllowed(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        String block = event.getBlock().getBlockData().getMaterial().name();
        if (!event.getPlayer().hasPermission("limitedcreative.admin")) {
            if (main.lc.containsKey(event.getPlayer())) {
                boolean enabled = main.getConfig().getBoolean("enabled");
                List<String> bBlocks = (List<String>)main.getConfig().getStringList("blacklisted-blocks");
                if (enabled) {
                    for (String blacklistedBlock : bBlocks) {
                        if (block.equalsIgnoreCase(blacklistedBlock)) {
                            event.setCancelled(true);
                            player.sendMessage(ChatColor.RED + "You cannot place that block!");
                        }
                    }
                }
            }
        // If they have "limitedcreative.admin" don't stop the event.
        } else if (event.getPlayer().hasPermission("limitedcreative.admin")) {
            event.setCancelled(false);
        }
    }
    
    // Don't let players in LC drop items.
    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (main.lc.containsValue(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
            Player player = event.getPlayer();
            player.sendMessage(ChatColor.RED + "You cannot drop items!");
        }
    }
    
    // IF player does not have "limitedcreative.admin" permissions, do not let them interact with blocks 
    // specified in config.yml 'blacklisted-interactables'
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (main.lc.containsKey(event.getPlayer())) {
            Player player = event.getPlayer();
            boolean enabled = main.getConfig().getBoolean("enabled");
            List<String> bBlocks = (List<String>)main.getConfig().getStringList("blacklisted-interactables");
            if (!event.getPlayer().hasPermission("limitedcreative.admin")) {
            	if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            		String block = event.getClickedBlock().getBlockData().getMaterial().name();
                	if (enabled) {
                    	for (String blacklistedBlock : bBlocks) {
                        	if (block.equalsIgnoreCase(blacklistedBlock)) {
                            	event.setCancelled(true);
                            	player.sendMessage(ChatColor.RED + "You cannot interact with that while in LC!");
                        	}
                    	} // End of interactables
                    	
                    	String entityPlaced = event.getItem().getType().name();
                    	List<String> bEntities = (List<String>)main.getConfig().getStringList("blacklisted-entities");
                    	
                    	if(!player.hasPermission("limitedcreative.admin")) {
                    		for(String blacklistedEntity : bEntities) {
                    			if(entityPlaced.equalsIgnoreCase(blacklistedEntity)) {
                    				event.setCancelled(true);
                    				player.sendMessage(ChatColor.RED + "You cannot place that while in LC!");
                    			}
                    		}
                    	} else { event.setCancelled(false); } // End of blacklisted-entities
                	} // End of 'Enabled'
            	} // end of right_click_block action
            	
            // If they have "limitedcreative.admin" don't stop the event.
            } else if (event.getPlayer().hasPermission("limitedcreative.admin")) {
            	event.setCancelled(false);
            }
        }
    }
    
    // Don't let them remove the leather armor in LC (Its buggy, still appears in inventory.)
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (main.lc.containsKey(event.getWhoClicked()) && event.getSlotType() == InventoryType.SlotType.ARMOR) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            player.updateInventory();
        }
    }
    
    // People in LC can't pick up items.
    @EventHandler
    public void onItemPickup(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player)event.getEntity();
            if (main.lc.containsKey(player)) {
                event.setCancelled(true);
            }
        }
    }
}
