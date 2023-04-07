package sh.lpx.outlet.storage;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileStorage implements Storage {
    private final @NotNull Path dir;

    private FileStorage(@NotNull Path dir) {
        this.dir = dir;
    }

    public static FileStorage prepare(@NotNull Path dir)
        throws IOException
    {
        Files.createDirectories(dir);
        return new FileStorage(dir);
    }
}
