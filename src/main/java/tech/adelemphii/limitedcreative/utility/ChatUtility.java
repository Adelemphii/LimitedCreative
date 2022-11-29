package tech.adelemphii.limitedcreative.utility;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatUtility {

    public static void sendMessage(CommandSender sender, String string) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', string));

        if(sender instanceof Player) {
            Player player = (Player) sender;
            player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, SoundCategory.BLOCKS, 1, 1);
        }
    }
}
