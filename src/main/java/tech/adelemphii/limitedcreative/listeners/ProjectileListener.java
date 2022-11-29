package tech.adelemphii.limitedcreative.listeners;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import tech.adelemphii.limitedcreative.LimitedCreative;

public class ProjectileListener implements Listener {

    private final LimitedCreative plugin;
    public ProjectileListener(LimitedCreative plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        Projectile projectile = event.getEntity();
        if(!(projectile.getShooter() instanceof Player)) {
            return;
        }
        Player player = (Player) projectile.getShooter();

        if(plugin.getManager().isInLC(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }
}
