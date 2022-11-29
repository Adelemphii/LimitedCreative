package tech.adelemphii.limitedcreative.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import tech.adelemphii.limitedcreative.LimitedCreative;
import tech.adelemphii.limitedcreative.objects.enums.LCPermission;
import tech.adelemphii.limitedcreative.utility.ChatUtility;

import java.util.Set;

public class ItemUseListener implements Listener {

    private final LimitedCreative plugin;
    public ItemUseListener(LimitedCreative plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onItemUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        LCPermission permission = LCPermission.getPermission(player);
        if(permission == LCPermission.ADMIN) {
            return;
        }

        ItemStack used = event.getItem();
        if(used == null) {
            return;
        }

        if(plugin.getManager().isInLC(player.getUniqueId())) {
            Set<Material> blacklist = plugin.getConfigHandler().getBlacklistedItems();
            if(blacklist.contains(used.getType())) {
                String message = plugin.getConfigHandler().placeholder(plugin.getConfigHandler().getBlacklistItemMessage(),
                        used.getType());

                ChatUtility.sendMessage(player, message);
                event.setCancelled(true);
            }
        }
    }
}
