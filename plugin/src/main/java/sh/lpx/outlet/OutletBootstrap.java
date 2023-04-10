package sh.lpx.outlet;

import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.bootstrap.PluginProviderContext;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("UnstableApiUsage")
public class OutletBootstrap
    implements PluginBootstrap
{
    private @Nullable Outlet outlet = null;

    @Override
    public void bootstrap(@NotNull PluginProviderContext context) {
        context.getLogger().info(context.getConfiguration().getDisplayName());
        this.outlet = Outlet.load(context.getDataDirectory());
    }

    @Override
    public @NotNull JavaPlugin createPlugin(@NotNull PluginProviderContext context) {
        if (this.outlet == null) {
            throw new IllegalStateException("`this.outlet` is null at plugin creation; was bootstrap not called?");
        }
        return new OutletPlugin(this.outlet);
    }
}
