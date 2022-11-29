package tech.adelemphii.limitedcreative.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import tech.adelemphii.limitedcreative.LimitedCreative;
import tech.adelemphii.limitedcreative.objects.enums.LCPermission;
import tech.adelemphii.limitedcreative.utility.ChatUtility;

public class ArmorStandListener implements Listener {

    private final LimitedCreative plugin;
    public ArmorStandListener(LimitedCreative plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onArmorStandManipulate(PlayerArmorStandManipulateEvent event) {
        Player player = event.getPlayer();
        LCPermission permission = LCPermission.getPermission(player);
        if(permission == LCPermission.ADMIN) {
            return;
        }

        if(plugin.getManager().isInLC(player.getUniqueId())) {
            event.setCancelled(true);

            ChatUtility.sendMessage(player, plugin.getConfigHandler().getPlayerManipulateArmorStandMessage());
        }
    }
}
