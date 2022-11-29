package tech.adelemphii.limitedcreative.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import tech.adelemphii.limitedcreative.LimitedCreative;
import tech.adelemphii.limitedcreative.objects.LCSession;
import tech.adelemphii.limitedcreative.utility.ChatUtility;

import java.util.HashMap;
import java.util.UUID;

@CommandAlias("playerlist|plist")
public class CommandPlayerList extends BaseCommand {

    private final LimitedCreative plugin;

    public CommandPlayerList(LimitedCreative plugin) {
        this.plugin = plugin;
    }

    @Default
    @CommandPermission("limitedcreative.mod|limitedcreative.admin")
    @Description("View a list of all players in LC")
    public void playerList(Player player) {
        HashMap<UUID, LCSession> sessions = plugin.getManager().getSessions();

        StringBuilder sb = new StringBuilder();

        for(UUID uuid : sessions.keySet()) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
            if(offlinePlayer.isOnline()) {
                sb.append(offlinePlayer.getName()).append(", ");
            }
        }
        if(sessions.keySet().size() > 1) {
            sb.delete(sb.length() - 2, sb.length() - 1);
        }

        player.sendMessage(ChatColor.GREEN + "Players in LC: ");
        ChatUtility.sendMessage(player, "&a" + sb);
    }
}
