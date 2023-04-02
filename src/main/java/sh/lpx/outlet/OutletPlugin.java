package sh.lpx.outlet;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class OutletPlugin extends JavaPlugin {
    private final @NotNull Outlet outlet;

    public OutletPlugin(@NotNull Outlet outlet) {
        outlet.initOrWarn(this.getServer(), "plugin construction");
        this.outlet = outlet;
    }

    @Override
    public void onLoad() {
        this.outlet.initOrWarn(this.getServer(), "plugin load");
    }

    @Override
    public void onEnable() {
        // TODO: Make sure this.outlet is initialized
    }
}
