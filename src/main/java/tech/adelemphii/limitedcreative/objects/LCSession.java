package tech.adelemphii.limitedcreative.objects;

import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class LCSession {

    private final UUID uuid;

    private final ItemStack[] inventoryContents;
    private final ItemStack[] armorContents;

    private final boolean allowFlight;

    public LCSession(UUID uuid, ItemStack[] inventoryContents, ItemStack[] armorContents, boolean allowFlight) {
        this.uuid = uuid;
        this.inventoryContents = inventoryContents;
        this.armorContents = armorContents;
        this.allowFlight = allowFlight;
    }

    public UUID getUuid() {
        return uuid;
    }

    public ItemStack[] getInventoryContents() {
        return inventoryContents;
    }

    public ItemStack[] getArmorContents() {
        return armorContents;
    }

    public boolean allowFlight() {
        return allowFlight;
    }
}
