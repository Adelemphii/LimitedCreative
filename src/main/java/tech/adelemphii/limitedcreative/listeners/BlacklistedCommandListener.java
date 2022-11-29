package tech.adelemphii.limitedcreative.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import tech.adelemphii.limitedcreative.LimitedCreative;
import tech.adelemphii.limitedcreative.objects.enums.LCPermission;
import tech.adelemphii.limitedcreative.utility.ChatUtility;

import java.util.Set;

public class BlacklistedCommandListener implements Listener {

    private final LimitedCreative plugin;
    public BlacklistedCommandListener(LimitedCreative plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        LCPermission permission = LCPermission.getPermission(player);
        if(permission == LCPermission.ADMIN) {
            return;
        }

        if(plugin.getManager().isInLC(player.getUniqueId())) {
            String[] args = event.getMessage().substring(1).split(" ");
            String command = args[0];

            Set<String> blacklist = plugin.getConfigHandler().getBlacklistedCommands();
            if(blacklist.contains(command)) {
                event.setCancelled(true);

                ChatUtility.sendMessage(player, plugin.getConfigHandler().getBlacklistedCommandMessage());
            }
        }
    }
}
