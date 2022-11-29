package tech.adelemphii.limitedcreative.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import tech.adelemphii.limitedcreative.LimitedCreative;
import tech.adelemphii.limitedcreative.objects.enums.LCPermission;
import tech.adelemphii.limitedcreative.utility.ChatUtility;

import java.util.Set;

public class BlockInteractionListener implements Listener {

    private final LimitedCreative plugin;
    public BlockInteractionListener(LimitedCreative plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        LCPermission permission = LCPermission.getPermission(player);
        if(permission == LCPermission.ADMIN) {
            return;
        }
        Block clickedBlock = event.getClickedBlock();
        if(clickedBlock == null) {
            return;
        }

        if(plugin.getManager().isInLC(player.getUniqueId())) {
            Set<Material> blacklist = plugin.getConfigHandler().getBlacklistedInteractables();
            if(blacklist.contains(clickedBlock.getType())) {
                event.setCancelled(true);

                String message = plugin.getConfigHandler().placeholder(plugin.getConfigHandler().getBlacklistInteractableMessage(),
                        clickedBlock.getType());
                ChatUtility.sendMessage(player, message);
            }
        }
    }
}
