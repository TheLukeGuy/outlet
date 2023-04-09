package sh.lpx.outlet.legacy;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class LegacyPlugin extends JavaPlugin {
    public LegacyPlugin() {
        Logger logger = this.getLogger();
        String serverName = this.getServer().getName();
        String pluginName = this.getDescription().getName();
        if (this.isPaper()) {
            logger.severe("Your " + serverName + " version is too old to load " + pluginName + "! :(");
            if (this.isAcceptableBukkitVersion()) {
                logger.severe(
                    "You're already on Minecraft 1.19.3, so you just need to update to Paper build #405 or above."
                );
            } else {
                logger.severe("You need to update to the latest builds of " + serverName + " 1.19.3 or above.");
            }
        } else {
            logger.severe(pluginName + " can't be loaded on a " + serverName + " server! :(");
            logger.severe("You need to switch to Paper or a derivative of Paper.");
        }
        logger.severe("For exact server requirements, please see " + this.getDescription().getWebsite() + ".");

        throw new UnsupportedServerException();
    }

    private boolean isPaper() {
        return this.hasClass("com.destroystokyo.paper.PaperConfig")
            || this.hasClass("io.papermc.paper.configuration.Configuration");
    }

    private boolean hasClass(@NotNull String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    private boolean isAcceptableBukkitVersion() {
        return this.getServer().getBukkitVersion().equals("1.19.3-R0.1-SNAPSHOT");
    }
}
