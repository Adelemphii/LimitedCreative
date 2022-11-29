package tech.adelemphii.limitedcreative.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import tech.adelemphii.limitedcreative.LimitedCreative;
import tech.adelemphii.limitedcreative.utility.ChatUtility;

public class DroppedItemListeners implements Listener {

    private final LimitedCreative plugin;
    public DroppedItemListeners(LimitedCreative plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        if(plugin.getManager().isInLC(player.getUniqueId())) {
            ChatUtility.sendMessage(player, plugin.getConfigHandler().getPlayerDropItemMessage());
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent event) {
        if(!(event.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getEntity();

        if(plugin.getManager().isInLC(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }
}
