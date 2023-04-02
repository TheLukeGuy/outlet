package sh.lpx.outlet;

import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sh.lpx.outlet.inject.InjectException;
import sh.lpx.outlet.inject.injector.PluginManagerInjector;

import java.nio.file.Files;
import java.nio.file.Path;

// rules to figure out:
// - what qualifies as a constant
// - order of annotations
// - line width limit (and what's the deal with +/- the limit by 1)
// - annotation placement on local variables
// - use the default values for types when declaring fields
// - should we throw unchecked exceptions
// - backticks in exception messages
// - deal with deprecation warnings
// - splitting up string lines
// - suppressing deprecations/removal
public class Outlet {
    private final @NotNull ComponentLogger logger;
    private final @NotNull Path dataDir;
    private final @NotNull String displayName;

    private @Nullable Server server;
    private boolean initialized = false;

    public Outlet(@NotNull ComponentLogger logger, @NotNull Path dataDir, @NotNull String displayName) {
        if (!Files.isDirectory(dataDir)) {
            throw new IllegalArgumentException("The data directory doesn't exist.");
        }
        this.logger = logger;
        this.dataDir = dataDir;
        this.displayName = displayName;
    }

    public @NotNull InitResult tryInit(@Nullable Server server, @NotNull String point) {
        if (this.initialized) {
            return InitResult.ALREADY_INITIALIZED;
        }

        server = server != null ? server : Outlet.getServerFromBukkit();
        if (server == null) {
            return InitResult.NO_SERVER;
        }

        this.init(server, point);
        return InitResult.OK;
    }

    public enum InitResult {
        OK,
        ALREADY_INITIALIZED,
        NO_SERVER;

        public boolean isStillUninitialized() {
            return this == InitResult.NO_SERVER;
        }
    }

    public void initOrWarn(@Nullable Server server, @NotNull String point) {
        if (this.tryInit(server, point).isStillUninitialized()) {
            // TODO: Mention the possible issues when they exist
            this.logger.warn("Failed to initialize during {}!", point);
        }
    }

    private void init(@NotNull Server server, @NotNull String point) {
        this.initialized = true;
        this.server = server;

        this.logger.info("Initializing during {}...", point);
        try {
            new PluginManagerInjector(server).inject();
        } catch (InjectException e) {
            throw new RuntimeException(e);
        }

        this.logger.info("{} has been successfully initialized!", this.displayName);
    }

    // Isolate the need to suppress warnings related to Bukkit.getServer() nullability
    // Bukkit.getServer() CAN return null if invoked before the end of server main (e.g. from a plugin bootstrapper)
    @SuppressWarnings("DataFlowIssue")
    private static @Nullable Server getServerFromBukkit() {
        return Bukkit.getServer();
    }
}
