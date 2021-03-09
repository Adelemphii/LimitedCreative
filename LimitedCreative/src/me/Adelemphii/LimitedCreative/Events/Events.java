package me.Adelemphii.LimitedCreative.Events;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffectType;

import me.Adelemphii.LimitedCreative.LimitedCreative;

public class Events implements Listener
{
	
	LimitedCreative plugin;
	public Events(LimitedCreative plugin) {
		this.plugin = plugin;
	}
    
	// Set player back to survival with their default inventory on leave.
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
    	Player player = event.getPlayer();
    	
        if (plugin.lc.containsValue(player.getUniqueId())) {
            plugin.restoreInventory(player);
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
            event.getPlayer().setGameMode(GameMode.SURVIVAL);
            plugin.lc.remove(event.getPlayer(), event.getPlayer().getUniqueId());
        }
    }
    
    // Runs when a player changes gamemode
    @EventHandler
    public void onGamemodeChange(PlayerGameModeChangeEvent event) {
    	if(plugin.lc.containsKey(event.getPlayer())) {
    		if(event.getNewGameMode() == GameMode.SURVIVAL || event.getNewGameMode() == GameMode.ADVENTURE) {
	    		Player player = event.getPlayer();
	    		
	    		plugin.lc.remove(player, player.getUniqueId());
	    		plugin.restoreInventory(player);
	        	if(player.isFlying()) {
	        		
	        		Location loc = player.getLocation();
	        		Block highestBlock;
	        		
	        		for(int y = loc.getBlockY() - 1; y > 0; y--) {
	        			loc.subtract(0, 1, 0);
	        			highestBlock = loc.getBlock();
	        			if(highestBlock.getType() != Material.AIR) {
	        				loc.add(0, 1, 0);
	        				player.teleport(loc);
	        				player.sendMessage(ChatColor.RED + "LC Warning: Detected player was flying! Teleporting you to a safe location.");
	        				break;
	        			}
	        		}
	        	}
    		}
    	}
    }
    
    // Don't allow people in LC to damage entities.
    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
    	boolean enabled = plugin.getConfig().getBoolean("enabled");
    	if(enabled) {
    		boolean entityDamage = plugin.getConfig().getBoolean("player-damage-entities");
    		if(!entityDamage) {
		        if (plugin.lc.containsKey(event.getDamager())) {
		            event.setCancelled(true);
		        }
    		}
    	}
    }
    
    // IF player does not have "limitedcreative.admin" permissions, do not let them place the blocks 
    // specified in config.yml 'blacklisted-blocks'
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        String block = event.getBlock().getBlockData().getMaterial().name();
        if (!player.hasPermission("limitedcreative.admin") || !player.isOp()) {
            if (plugin.lc.containsKey(event.getPlayer())) {
            	boolean enabled = plugin.getConfig().getBoolean("enabled");
                List<String> bBlocks = (List<String>)plugin.getConfig().getStringList("blacklisted-blocks");
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
        } else {
            event.setCancelled(false);
        }
    }
    
    // Don't let players in LC drop items.
    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        if (plugin.lc.containsValue(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
            Player player = event.getPlayer();
            player.sendMessage(ChatColor.RED + "You cannot drop items!");
        }
    }
    
    // IF player does not have "limitedcreative.admin" permissions, do not let them interact with blocks 
    // specified in config.yml 'blacklisted-interactables'
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (plugin.lc.containsKey(event.getPlayer())) {
            Player player = event.getPlayer();
            boolean enabled = plugin.getConfig().getBoolean("enabled");
            
            List<String> bBlocks = (List<String>)plugin.getConfig().getStringList("blacklisted-interactables");
            if (!event.getPlayer().hasPermission("limitedcreative.admin")) {
            	if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            		
            		if(event.getClickedBlock() == null) {
            			// do nothing
            		} else {
	            		String block = event.getClickedBlock().getBlockData().getMaterial().name();
	                	if (enabled) {
	                    	for (String blacklistedBlock : bBlocks) {
	                        	if (block.equalsIgnoreCase(blacklistedBlock)) {
	                            	event.setCancelled(true);
	                            	player.sendMessage(ChatColor.RED + "You cannot interact with that while in LC!");
	                        	}
	                    	} // End of interactables
	                    	
	                    	if(event.getItem() != null) {
		                    	String entityPlaced = event.getItem().getType().name();
		                    	List<String> bEntities = (List<String>)plugin.getConfig().getStringList("blacklisted-entities");
		                    	
		                    	if(!player.hasPermission("limitedcreative.admin")) {
		                    		for(String blacklistedEntity : bEntities) {
		                    			if(entityPlaced.equalsIgnoreCase(blacklistedEntity)) {
		                    				event.setCancelled(true);
		                    				player.sendMessage(ChatColor.RED + "You cannot place that while in LC!");
		                    			}	
		                    		}
		                    	} else { 
		                    		event.setCancelled(false);
		                    	}
	                    	} // End of blacklisted-entities
	                    	
	                    	//
	                    	// Add some way to stop villager clicks
	                    	//
	                    	
	                	} // End of 'Enabled'
            		}
            	} // end of right_click_block action
            	
            // If they have "limitedcreative.admin" don't stop the event.
            } else if (event.getPlayer().hasPermission("limitedcreative.admin") || player.isOp()) {
            	event.setCancelled(false);
            }
        }
    } // end of onInteract event
    
    // Prevent player in LC from breaking blocks specified in config
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        String block = event.getBlock().getBlockData().getMaterial().name();
        
        if (!player.hasPermission("limitedcreative.admin") || !player.isOp()) {
            if (plugin.lc.containsKey(event.getPlayer())) {
            	boolean enabled = plugin.getConfig().getBoolean("enabled");
                List<String> bBlocks = (List<String>)plugin.getConfig().getStringList("blacklisted-breakables");
                if (enabled) {
                    for (String blacklistedBlock : bBlocks) {
                        if (block.equalsIgnoreCase(blacklistedBlock)) {
                            event.setCancelled(true);
                            player.sendMessage(ChatColor.RED + "You cannot break that block while in LC!");
                        }
                    }
                }
            }
        } else {
        	event.setCancelled(false);
        }
    }
    
    // Don't let them remove the leather armor in LC (Its buggy, still appears in inventory.)
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (plugin.lc.containsKey(event.getWhoClicked()) && event.getSlotType() == InventoryType.SlotType.ARMOR) {
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
            if (plugin.lc.containsKey(player)) {
                event.setCancelled(true);
            }
        }
    }
    
    // Stop them from drinking milk if they have glowing effect in LC.
    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event) {
    	if(plugin.lc.containsKey(event.getPlayer())) {
    		Player player = event.getPlayer();
    		if(event.getItem().getType() == null) {
    			// do nothing
    		} else if(event.getItem().getType() == Material.MILK_BUCKET) {
    			if(player.hasPotionEffect(PotionEffectType.GLOWING)) {
    				event.setCancelled(true);
    			}
    		}
    	}
    }
}
