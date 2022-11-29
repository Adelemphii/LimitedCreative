package tech.adelemphii.limitedcreative.listeners;

import com.jeff_media.customblockdata.CustomBlockData;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Hanging;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import tech.adelemphii.limitedcreative.LimitedCreative;
import tech.adelemphii.limitedcreative.objects.enums.LCPermission;
import tech.adelemphii.limitedcreative.utility.ChatUtility;

import java.util.Set;

public class BlockListeners implements Listener {

    private final LimitedCreative plugin;
    public BlockListeners(LimitedCreative plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        LCPermission permission = LCPermission.getPermission(player);
        if(permission == LCPermission.ADMIN) {
            return;
        }

        if(plugin.getManager().isInLC(player.getUniqueId())) {
            Set<Material> blacklist = plugin.getConfigHandler().getBlacklistedPlaceables();

            if(!blacklist.contains(event.getBlockPlaced().getType())) {
                return;
            }

            String message = plugin.getConfigHandler().placeholder(plugin.getConfigHandler().getBlacklistBlockPlaceMessage(),
                    event.getBlockPlaced().getType());

            ChatUtility.sendMessage(player, message);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void preventBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        LCPermission permission = LCPermission.getPermission(player);
        if(permission == LCPermission.ADMIN) {
            return;
        }

        if(plugin.getManager().isInLC(player.getUniqueId())) {
            Set<Material> blacklist = plugin.getConfigHandler().getBlacklistedBreakables();

            if(!blacklist.contains(event.getBlock().getType())) {
                return;
            }

            String message = plugin.getConfigHandler().placeholder(plugin.getConfigHandler().getBlacklistBreakableMessage(),
                    event.getBlock().getType());

            ChatUtility.sendMessage(player, message);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handleBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();

        PersistentDataContainer customBlockData = new CustomBlockData(block, plugin);
        if(customBlockData.has(plugin.getPlacedInLCKey(), PersistentDataType.INTEGER)) {
            event.setDropItems(false);
            event.setExpToDrop(0);
        }
    }

    @EventHandler
    public void hangingPlace(HangingPlaceEvent event) {
        Player player = event.getPlayer();
        if(player == null) {
            return;
        }
        Hanging entity = event.getEntity();

        if(plugin.getManager().isInLC(player.getUniqueId())) {
            PersistentDataContainer persistentDataContainer = entity.getPersistentDataContainer();
            persistentDataContainer.set(plugin.getPlacedInLCKey(), PersistentDataType.INTEGER, 1);
        }
    }

    @EventHandler
    public void entityDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        PersistentDataContainer persistentDataContainer = entity.getPersistentDataContainer();

        if(persistentDataContainer.has(plugin.getPlacedInLCKey(), PersistentDataType.INTEGER)) {
            event.getDrops().clear();
        }
    }

    /* TODO: Find way of checking if a block if attached to broken block
        and if it is, check if it has custom block data and remove drops if placed in lc
    @EventHandler
    public void onPhysicsUpdate(BlockPhysicsEvent event) {
        PersistentDataContainer customBlockData = new CustomBlockData(event.getBlock(), plugin);
        if(customBlockData.has(plugin.getPlacedInLCKey(), PersistentDataType.INTEGER)) {
            event.getBlock().setType(Material.AIR);
        }
    }
     */
}
