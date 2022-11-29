package tech.adelemphii.limitedcreative.listeners;

import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.block.EnderChest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import tech.adelemphii.limitedcreative.LimitedCreative;
import tech.adelemphii.limitedcreative.objects.enums.LCPermission;
import tech.adelemphii.limitedcreative.utility.ChatUtility;

public class ContainerListeners implements Listener {

    private final LimitedCreative plugin;
    public ContainerListeners(LimitedCreative plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onContainerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block clickedBlock = event.getClickedBlock();
        if(clickedBlock == null) {
            return;
        }

        if(plugin.getManager().isInLC(player.getUniqueId())) {
            if(clickedBlock.getState() instanceof Container || clickedBlock.getState() instanceof EnderChest) {
                event.setCancelled(true);

                String message = plugin.getConfigHandler().placeholder(plugin.getConfigHandler().getBlacklistInteractableMessage(),
                        clickedBlock.getType());

                ChatUtility.sendMessage(player, message);
            }
        }
    }

    @EventHandler
    public void removeNBTOnPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        LCPermission permission = LCPermission.getPermission(player);
        if(permission == LCPermission.ADMIN) {
            return;
        }

        Block placedBlock = event.getBlock();

        if(plugin.getManager().isInLC(player.getUniqueId())) {
            if(placedBlock.getState() instanceof Container) {
                Container container = (Container) placedBlock.getState();

                plugin.getServer().getScheduler().runTaskLater(plugin, () -> container.getInventory().clear(), 1L);
            }
        }
    }
}
