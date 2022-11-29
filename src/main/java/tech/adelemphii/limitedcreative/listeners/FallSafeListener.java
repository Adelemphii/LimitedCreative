package tech.adelemphii.limitedcreative.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import tech.adelemphii.limitedcreative.LimitedCreative;

public class FallSafeListener implements Listener {

    private final LimitedCreative plugin;
    public FallSafeListener(LimitedCreative plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onFallDamage(EntityDamageEvent event) {
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            if(event.getCause() == EntityDamageEvent.DamageCause.FALL && plugin.getManager().isInFallSafe(player.getUniqueId())) {
                event.setCancelled(true);
                plugin.getManager().removeFallSafe(player.getUniqueId());
            }
        }
    }
}
