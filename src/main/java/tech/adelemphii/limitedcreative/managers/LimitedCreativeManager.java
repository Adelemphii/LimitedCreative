package tech.adelemphii.limitedcreative.managers;

import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import tech.adelemphii.limitedcreative.LimitedCreative;
import tech.adelemphii.limitedcreative.objects.LCSession;
import tech.adelemphii.limitedcreative.objects.enums.LCError;
import tech.adelemphii.limitedcreative.utility.ChatUtility;
import tech.adelemphii.limitedcreative.utility.ColorUtility;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.logging.Level;

public class LimitedCreativeManager {

    private final LimitedCreative plugin;

    private final HashMap<UUID, LCSession> sessions;
    private final HashSet<UUID> fallSafe;

    private int taskID = 0;

    public LimitedCreativeManager(LimitedCreative plugin) {
        this.plugin = plugin;
        this.sessions = new HashMap<>();
        this.fallSafe = new HashSet<>();
    }

    public void leaveLC(final Player player, boolean onDisable) {
        LCSession session = getSession(player.getUniqueId());

        if(session == null) {
            plugin.getLogger().log(Level.WARNING, LCError.SESSION_NULL.getError());
            return;
        }

        player.getInventory().clear();

        for(PotionEffect potionEffect : player.getActivePotionEffects()) {
            player.removePotionEffect(potionEffect.getType());
        }

        player.setAllowFlight(session.allowFlight());
        player.setFlying(session.allowFlight());

        player.getInventory().setContents(session.getInventoryContents());
        player.getInventory().setArmorContents(session.getArmorContents());

        sessions.remove(player.getUniqueId());
        player.setGameMode(GameMode.SURVIVAL);

        ChatUtility.sendMessage(player, plugin.getConfigHandler().getPlayerExitLCMessage());

        if(!onDisable) {
            if(player.getLocation().getBlock().getRelative(BlockFace.DOWN, 3).getType() == Material.AIR) {
                fallSafe.add(player.getUniqueId());
                fallSafeTask();
            }
        } else {
            Location teleportLocation = Bukkit.getWorld(player.getWorld().getUID()).getHighestBlockAt(player.getLocation()).getLocation().add(0, 1, 0);

            player.teleport(teleportLocation);
        }
    }

    public void enterLC(Player player) {
        LCSession session = new LCSession(player.getUniqueId(), player.getInventory().getContents(),
                player.getInventory().getArmorContents(), player.getAllowFlight());

        sessions.put(player.getUniqueId(), session);

        player.getOpenInventory().close();

        ChatUtility.sendMessage(player, plugin.getConfigHandler().getPlayerEnterLCMessage());

        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        player.setGameMode(GameMode.CREATIVE);

        if(plugin.getConfigHandler().isArmor()) {
            player.getInventory().setArmorContents(getArmor());
        }
        if(plugin.getConfigHandler().isGlowing()) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, Integer.MAX_VALUE, 1));
        }
    }

    private ItemStack[] getArmor() {
        ItemStack[] armor = new ItemStack[4];
        armor[3] = new ItemStack(Material.LEATHER_HELMET);
        armor[2] = new ItemStack(Material.LEATHER_CHESTPLATE);
        armor[1] = new ItemStack(Material.LEATHER_LEGGINGS);
        armor[0] = new ItemStack(Material.LEATHER_BOOTS);

        for(ItemStack itemStack : armor) {
            LeatherArmorMeta meta = (LeatherArmorMeta) itemStack.getItemMeta();

            Color color = ColorUtility.convertToColor(plugin.getConfigHandler().getArmorColor());
            assert meta != null;
            meta.setColor(color);

            itemStack.setItemMeta(meta);
        }
        return armor;
    }

    private void fallSafeTask() {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            if(fallSafe.isEmpty()) {
                Bukkit.getScheduler().cancelTask(taskID);
                return;
            }

            for(UUID uuid : fallSafe) {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
                if(!offlinePlayer.isOnline()) {
                    continue;
                }
                Player player = offlinePlayer.getPlayer();
                if(player == null) {
                    continue;
                }

                if(player.getLocation().getBlock().getRelative(BlockFace.DOWN, 3).getType() != Material.AIR
                    && offlinePlayer.getPlayer().getFallDistance() < 1) {
                    fallSafe.remove(offlinePlayer.getUniqueId());
                }
            }
        }, 20 * 5, 20 * 5);
    }

    public HashMap<UUID, LCSession> getSessions() {
        return sessions;
    }

    public HashSet<UUID> getFallSafe() {
        return fallSafe;
    }

    public int getTaskID() {
        return taskID;
    }

    public boolean isInLC(UUID player) {
        return sessions.containsKey(player);
    }

    public boolean isInFallSafe(UUID player) {
        return fallSafe.contains(player);
    }

    public void removeFallSafe(UUID player) {
        fallSafe.remove(player);
    }

    public LCSession getSession(UUID uuid) {
        return sessions.get(uuid);
    }
}
