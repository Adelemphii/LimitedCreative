package tech.adelemphii.limitedcreative.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import tech.adelemphii.limitedcreative.LimitedCreative;

public class PlayerLeaveListener implements Listener {

    private final LimitedCreative plugin;
    public PlayerLeaveListener(LimitedCreative plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if(plugin.getManager().isInLC(player.getUniqueId())) {
            plugin.getManager().leaveLC(player, false);
        }
    }
}
