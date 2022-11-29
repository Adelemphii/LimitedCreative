package tech.adelemphii.limitedcreative.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import tech.adelemphii.limitedcreative.LimitedCreative;
import tech.adelemphii.limitedcreative.utility.ChatUtility;

public class EntityClickListener implements Listener {

    private final LimitedCreative plugin;
    public EntityClickListener(LimitedCreative plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityClick(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();

        if(plugin.getManager().isInLC(player.getUniqueId())) {
            event.setCancelled(true);

            ChatUtility.sendMessage(player, plugin.getConfigHandler().getPlayerInteractEntitiesMessage());
        }
    }
}
