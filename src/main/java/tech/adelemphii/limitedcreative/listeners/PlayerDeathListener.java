package tech.adelemphii.limitedcreative.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import tech.adelemphii.limitedcreative.LimitedCreative;

public class PlayerDeathListener implements Listener {

    private final LimitedCreative plugin;
    public PlayerDeathListener(LimitedCreative plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        if(plugin.getManager().isInLC(player.getUniqueId())) {
            plugin.getManager().leaveLC(player, false);
            event.getDrops().clear();
        }
    }
}
