package sh.lpx.outlet;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class OutletPlugin
    extends JavaPlugin
{
    private final @NotNull Outlet outlet;

    public OutletPlugin(@NotNull Outlet outlet) {
        this.outlet = outlet;
    }
}
