package tech.adelemphii.limitedcreative.objects.enums;

import org.bukkit.entity.Player;

public enum LCPermission {

    ADMIN("limitedcreative.admin"),
    MOD("limitedcreative.mod"),
    USE("limitedcreative.use"),
    GIVE("limitedcreative.give");

    private final String permissionString;

    LCPermission(String permissionString) {
        this.permissionString = permissionString;
    }

    public String getPermissionString() {
        return permissionString;
    }

    public static LCPermission getPermission(Player player) {
        if(player == null) {
            return null;
        }

        for(LCPermission lcPermission : values()) {
            if(player.hasPermission(lcPermission.permissionString)) {
                return lcPermission;
            }
        }
        return null;
    }
}
