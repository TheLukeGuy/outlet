package sh.lpx.outlet.inject;

import org.jetbrains.annotations.Nullable;

public class InjectException extends Exception {
    public InjectException(@Nullable String message) {
        super(message);
    }

    public InjectException(@Nullable String message, @Nullable Throwable cause) {
        super(message, cause);
    }
}
