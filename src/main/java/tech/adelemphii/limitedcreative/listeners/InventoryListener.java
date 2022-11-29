package tech.adelemphii.limitedcreative.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import tech.adelemphii.limitedcreative.LimitedCreative;

public class InventoryListener implements Listener {

    private final LimitedCreative plugin;
    public InventoryListener(LimitedCreative plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if(plugin.getManager().isInLC(player.getUniqueId())) {
            if(!plugin.getConfigHandler().isArmor()) {
                return;
            }
            if(event.getSlotType() == InventoryType.SlotType.ARMOR) {
                event.setCancelled(true);
            }
        }
    }
}
