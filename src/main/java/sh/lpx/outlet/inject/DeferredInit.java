package sh.lpx.outlet.inject;

import com.destroystokyo.paper.event.server.WhitelistToggleEvent;
import io.papermc.paper.plugin.configuration.PluginMeta;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sh.lpx.outlet.Outlet;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

public class DeferredInit {
    private static final @NotNull Plugin FAKE_PLUGIN = new DeferredInit.FakePlugin();

    public static void install(@NotNull Outlet outlet) {
        RegisteredListener registeredListener = new RegisteredListener(
                new Listener() {},
                (listener, event) -> DeferredInit.uninstallAndInit(listener, outlet),
                EventPriority.LOWEST,
                FAKE_PLUGIN,
                false
        );
        WhitelistToggleEvent.getHandlerList().register(registeredListener);
    }

    private static void uninstallAndInit(@NotNull Listener listener, @NotNull Outlet outlet) {
        WhitelistToggleEvent.getHandlerList().unregister(listener);
        outlet.initOrWarn(null, "normal deferred initialization");
    }

    private static class FakePlugin extends PluginBase {
        private static final @NotNull PluginDescriptionFile DESCRIPTION =
                new PluginDescriptionFile("Outlet Fake Plugin", "1.0", "fake");

        @Override
        public @NotNull File getDataFolder() {
            throw new UnsupportedOperationException("Not supported.");
        }

        @Override
        public @NotNull PluginDescriptionFile getDescription() {
            return FakePlugin.DESCRIPTION;
        }

        @SuppressWarnings("UnstableApiUsage")
        @Override
        public @NotNull PluginMeta getPluginMeta() {
            return FakePlugin.DESCRIPTION;
        }

        @Override
        public @NotNull FileConfiguration getConfig() {
            throw new UnsupportedOperationException("Not supported.");
        }

        @Override
        public @Nullable InputStream getResource(@NotNull String filename) {
            throw new UnsupportedOperationException("Not supported.");
        }

        @Override
        public void saveConfig() {
            throw new UnsupportedOperationException("Not supported.");
        }

        @Override
        public void saveDefaultConfig() {
            throw new UnsupportedOperationException("Not supported.");
        }

        @Override
        public void saveResource(@NotNull String resourcePath, boolean replace) {
            throw new UnsupportedOperationException("Not supported.");
        }

        @Override
        public void reloadConfig() {
            throw new UnsupportedOperationException("Not supported.");
        }

        @SuppressWarnings("removal")
        @Override
        public @NotNull PluginLoader getPluginLoader() {
            throw new UnsupportedOperationException("Not supported.");
        }

        @Override
        public @NotNull Server getServer() {
            throw new UnsupportedOperationException("Not supported.");
        }

        @Override
        public boolean isEnabled() {
            return true;
        }

        @Override
        public void onDisable() {
            throw new UnsupportedOperationException("Not supported.");
        }

        @Override
        public void onLoad() {
            throw new UnsupportedOperationException("Not supported.");
        }

        @Override
        public void onEnable() {
            throw new UnsupportedOperationException("Not supported.");
        }

        @Override
        public boolean isNaggable() {
            return false;
        }

        @Override
        public void setNaggable(boolean canNag) {
            throw new UnsupportedOperationException("Not supported.");
        }

        @Override
        public @Nullable ChunkGenerator getDefaultWorldGenerator(@NotNull String worldName, @Nullable String id) {
            throw new UnsupportedOperationException("Not supported.");
        }

        @Override
        public @Nullable BiomeProvider getDefaultBiomeProvider(@NotNull String worldName, @Nullable String id) {
            throw new UnsupportedOperationException("Not supported.");
        }

        @Override
        public @NotNull Logger getLogger() {
            throw new UnsupportedOperationException("Not supported.");
        }

        @Override
        public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
            throw new UnsupportedOperationException("Not supported.");
        }

        @Override
        public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
            throw new UnsupportedOperationException("Not supported.");
        }
    }
}
