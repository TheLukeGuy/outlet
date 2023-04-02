package sh.lpx.outlet.inject.injector;

import io.papermc.paper.plugin.manager.PaperPluginManagerImpl;
import org.bukkit.Server;
import org.bukkit.plugin.SimplePluginManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sh.lpx.outlet.inject.InjectException;
import sh.lpx.outlet.inject.impl.OutletPluginManager;

public class PluginManagerInjector extends Injector<PaperPluginManagerImpl, OutletPluginManager> {
    private final @NotNull Server server;
    private @Nullable SimplePluginManager simplePluginManager;

    public PluginManagerInjector(@NotNull Server server) {
        this.server = server;
    }

    @Override
    protected @NotNull PaperPluginManagerImpl getOriginal()
            throws InjectException
    {
        if (!(this.getSimplePluginManager().paperPluginManager instanceof PaperPluginManagerImpl original)) {
            throw new InjectException("`paperPluginManager` isn't a `PaperPluginManagerImpl`.");
        }
        return original;
    }

    @Override
    protected @NotNull OutletPluginManager createPayload(@NotNull PaperPluginManagerImpl original)
            throws InjectException
    {
        return this.shallowCloneAsSubclass(original, OutletPluginManager.class);
    }

    @Override
    protected void inject(@NotNull OutletPluginManager payload)
            throws InjectException
    {
        this.injectIntoFinalField(this.server, "paperPluginManager", payload);
        this.getSimplePluginManager().paperPluginManager = payload;
    }

    private @NotNull SimplePluginManager getSimplePluginManager()
            throws InjectException
    {
        if (this.simplePluginManager == null) {
            if (!(this.server.getPluginManager() instanceof SimplePluginManager simple)) {
                throw new InjectException("The server's plugin manager isn't a `SimplePluginManager`.");
            }
            this.simplePluginManager = simple;
        }
        return this.simplePluginManager;
    }
}
