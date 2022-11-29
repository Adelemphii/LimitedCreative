package tech.adelemphii.limitedcreative.listeners;

import com.jeff_media.customblockdata.CustomBlockData;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import tech.adelemphii.limitedcreative.LimitedCreative;

public class BlockPlaceLogger implements Listener {

    private final LimitedCreative plugin;
    public BlockPlaceLogger(LimitedCreative plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if(plugin.getManager().isInLC(player.getUniqueId())) {
            if(!event.isCancelled()) {
                PersistentDataContainer customBlockData = new CustomBlockData(block, plugin);
                customBlockData.set(plugin.getPlacedInLCKey(), PersistentDataType.INTEGER, 1);
            }
        }
    }
}
