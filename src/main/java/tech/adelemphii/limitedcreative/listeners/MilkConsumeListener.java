package tech.adelemphii.limitedcreative.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import tech.adelemphii.limitedcreative.LimitedCreative;

public class MilkConsumeListener implements Listener {

    private final LimitedCreative plugin;
    public MilkConsumeListener(LimitedCreative plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        ItemStack consumed = event.getItem();

        if(plugin.getManager().isInLC(player.getUniqueId())) {
            if(consumed.getType() == Material.MILK_BUCKET) {
                event.setCancelled(true);
            }
        }
    }
}
