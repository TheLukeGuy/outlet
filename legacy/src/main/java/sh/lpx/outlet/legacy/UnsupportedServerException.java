package sh.lpx.outlet.legacy;

public class UnsupportedServerException extends RuntimeException {
    public UnsupportedServerException() {
        super("This server is unsupported.", null, true, false);
    }
}
