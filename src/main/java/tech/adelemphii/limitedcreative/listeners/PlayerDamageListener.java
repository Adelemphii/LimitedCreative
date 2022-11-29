package tech.adelemphii.limitedcreative.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import tech.adelemphii.limitedcreative.LimitedCreative;

public class PlayerDamageListener implements Listener {

    private final LimitedCreative plugin;
    public PlayerDamageListener(LimitedCreative plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if(!(event.getDamager() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getDamager();

        boolean enabled = plugin.getConfigHandler().playerDamageEntities();
        if(!enabled) {
            if(plugin.getManager().isInLC(player.getUniqueId())) {
                event.setCancelled(true);
            }
        }
    }
}
