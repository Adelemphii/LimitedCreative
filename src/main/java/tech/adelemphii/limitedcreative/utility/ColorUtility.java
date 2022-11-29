package tech.adelemphii.limitedcreative.utility;

import org.bukkit.Color;

public class ColorUtility {

    public static Color convertToColor(String colorStr) {
        return Color.fromRGB(
                Integer.valueOf(colorStr.substring(1, 3), 16),
                Integer.valueOf(colorStr.substring(3, 5), 16),
                Integer.valueOf(colorStr.substring(5, 7), 16)
        );
    }
}
