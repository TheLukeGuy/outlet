package sh.lpx.outlet;

import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.bootstrap.PluginProviderContext;
import io.papermc.paper.plugin.configuration.PluginMeta;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sh.lpx.outlet.inject.DeferredInit;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

@SuppressWarnings("UnstableApiUsage")
public class OutletBootstrap implements PluginBootstrap {
    private @Nullable Outlet outlet = null;

    @Override
    public void bootstrap(@NotNull PluginProviderContext context) {
        PluginMeta config = context.getConfiguration();
        String displayName = config.getName().replace('_', ' ');

        ComponentLogger logger = context.getLogger();
        logger.info("{} v{}", displayName, config.getVersion());

        Path dataDir = context.getDataDirectory();
        try {
            Files.createDirectories(dataDir);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to create the data directory.", e);
        }

        this.outlet = new Outlet(logger, context.getDataDirectory(), displayName);
        if (this.outlet.tryInit(null, "bootstrap").isStillUninitialized()) {
            logger.info("It's too early to initialize; using hacks to defer to a later point!");
            DeferredInit.install(this.outlet);
        }
    }

    @Override
    public @NotNull JavaPlugin createPlugin(@NotNull PluginProviderContext context) {
        if (this.outlet == null) {
            throw new IllegalStateException("`this.outlet` is null at plugin creation; was bootstrap not called?");
        }
        return new OutletPlugin(this.outlet);
    }
}
