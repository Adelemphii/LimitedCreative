package tech.adelemphii.limitedcreative.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import tech.adelemphii.limitedcreative.LimitedCreative;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PreventGolemCreationListener implements Listener {

    private final Map<UUID, Location> lastPlaced = new HashMap<>();
    private final LimitedCreative plugin;
    public PreventGolemCreationListener(LimitedCreative plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if(plugin.getManager().isInLC(player.getUniqueId())) {
            if(block.getType() != Material.CARVED_PUMPKIN && block.getType() != Material.WITHER_SKELETON_SKULL
                    && block.getType() != Material.WITHER_SKELETON_WALL_SKULL) {
                return;
            }

            lastPlaced.put(player.getUniqueId(), block.getLocation());

            Bukkit.getScheduler().runTaskLater(plugin, () -> lastPlaced.remove(player.getUniqueId()), 20 * 3L);
        }
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        CreatureSpawnEvent.SpawnReason spawnReason = event.getSpawnReason();
        if(spawnReason != CreatureSpawnEvent.SpawnReason.BUILD_IRONGOLEM && spawnReason != CreatureSpawnEvent.SpawnReason.BUILD_WITHER
                && spawnReason != CreatureSpawnEvent.SpawnReason.BUILD_SNOWMAN) {
            return;
        }

        Player creator = findNearestPlayerPlacedBlock(event.getLocation(), 10);
        if(creator != null) {
            event.setCancelled(true);
        }
    }

    private Player findNearestPlayerPlacedBlock(Location location, int maximumDistance) {
        if(location == null || location.getWorld() == null) return null;

        Player closest = null;
        double closestDistance = maximumDistance + 1;

        List<Player> players = location.getWorld().getPlayers();
        for (Player player : players) {
            Location lastPlacedBlock = lastPlaced.get(player.getUniqueId());

            if (lastPlacedBlock != null) {
                double distance = location.distanceSquared(lastPlacedBlock);

                if (distance < closestDistance && distance < maximumDistance) {
                    closest = player;
                }
            }
        }
        return closest;
    }
}
