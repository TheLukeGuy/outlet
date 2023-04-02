package sh.lpx.outlet.inject.impl;

import io.papermc.paper.plugin.manager.PaperPluginManagerImpl;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class OutletPluginManager extends PaperPluginManagerImpl {
    private OutletPluginManager() {
        super(null, null, null);
        throw new UnsupportedOperationException("This class can't be instantiated normally.");
    }

    @Override
    public void registerEvents(@NotNull Listener listener, @NotNull Plugin plugin) {
        System.out.println("Events are being registered! registerEvents(" + listener + ", " + plugin + ")");
        super.registerEvents(listener, plugin);
    }
}
