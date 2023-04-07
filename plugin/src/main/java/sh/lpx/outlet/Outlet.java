package sh.lpx.outlet;

import org.jetbrains.annotations.NotNull;
import sh.lpx.outlet.storage.FileStorage;
import sh.lpx.outlet.storage.NopStorage;
import sh.lpx.outlet.storage.Storage;

import java.io.IOException;
import java.nio.file.Path;

public class Outlet {
    private final @NotNull Storage storage;

    private Outlet(@NotNull Storage storage) {
        this.storage = storage;
    }

    public static @NotNull Outlet load(@NotNull Path dataDir) {
        Storage storage;
        try {
            storage = FileStorage.prepare(dataDir);
        } catch (IOException e) {
            storage = new NopStorage();
        }
        return new Outlet(storage);
    }
}
