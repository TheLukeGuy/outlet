package sh.lpx.outlet.inject.injector;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sh.lpx.outlet.inject.InjectException;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

public abstract class Injector<O, P> {
    private static @Nullable Unsafe unsafe;

    public @NotNull P inject()
            throws InjectException
    {
        P payload;
        try {
            O original = this.getOriginal();
            payload = this.createPayload(this.getOriginal());
            if (!original.getClass().isInstance(payload)) {
                throw new InjectException("The payload type can't be assigned to the original type.");
            }
            this.inject(payload);
        } catch (RuntimeException e) {
            // There's so much that could go wrong
            throw new InjectException("An unchecked exception occurred while attempting to inject.", e);
        }
        return payload;
    }

    protected abstract @NotNull O getOriginal()
            throws InjectException;

    protected abstract @NotNull P createPayload(@NotNull O original)
            throws InjectException;

    protected abstract void inject(@NotNull P payload)
            throws InjectException;

    protected @NotNull P shallowCloneAsSubclass(@NotNull O original, @NotNull Class<P> payloadClass)
            throws InjectException
    {
        Unsafe unsafe = Injector.getUnsafe();
        P clone;
        try {
            clone = payloadClass.cast(unsafe.allocateInstance(payloadClass));
        } catch (InstantiationException | ClassCastException e) {
            throw new InjectException("Failed to allocate an instance of `" + payloadClass + "`.", e);
        }

        // Let's walk up the class hierarchy :)
        Class<?> current = original.getClass();
        do {
            for (Field field : current.getDeclaredFields()) {
                // TODO: IS THIS SAFE?>??!?!?!? I LITERALLY DON'T KNOW THE JAVA MEMORY MODEL
                // TODO: catch UnsupportedOperationException
                // TODO: Should these be volatile
                long offset = this.getObjectFieldOffset(field);
                Class<?> type = field.getType();
                if (type == byte.class) {
                    byte copy = unsafe.getByte(original, offset);
                    unsafe.putByte(clone, offset, copy);
                } else if (type == short.class) {
                    short copy = unsafe.getShort(original, offset);
                    unsafe.putShort(clone, offset, copy);
                } else if (type == int.class) {
                    int copy = unsafe.getInt(original, offset);
                    unsafe.putInt(clone, offset, copy);
                } else if (type == long.class) {
                    long copy = unsafe.getLong(original, offset);
                    unsafe.putLong(clone, offset, copy);
                } else if (type == float.class) {
                    float copy = unsafe.getFloat(original, offset);
                    unsafe.putFloat(clone, offset, copy);
                } else if (type == double.class) {
                    double copy = unsafe.getDouble(original, offset);
                    unsafe.putDouble(clone, offset, copy);
                } else if (type == boolean.class) {
                    boolean copy = unsafe.getBoolean(original, offset);
                    unsafe.putBoolean(clone, offset, copy);
                } else if (type == char.class) {
                    char copy = unsafe.getChar(original, offset);
                    unsafe.putChar(clone, offset, copy);
                } else {
                    Object refCopy = unsafe.getObject(original, offset);
                    unsafe.putObject(clone, offset, refCopy);
                }
            }
        } while ((current = current.getSuperclass()) != null);

        return clone;
    }

    protected <T> void injectIntoFinalField(@NotNull T target, @NotNull String fieldName, @NotNull P payload)
            throws InjectException
    {
        Unsafe unsafe = Injector.getUnsafe();
        Field field = this.getDeclaredField(target, fieldName);
        long offset = this.getObjectFieldOffset(field);
        unsafe.putObject(target, offset, payload);
    }

    private long getObjectFieldOffset(@NotNull Field field)
            throws InjectException
    {
        try {
            return Injector.getUnsafe().objectFieldOffset(field);
        } catch (UnsupportedOperationException e) {
            throw new InjectException("Unable to get the offset of `" + field + "`.");
        }
    }

    private <T> @NotNull Field getDeclaredField(@NotNull T target, @NotNull String fieldName)
            throws InjectException
    {
        try {
            return target.getClass().getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            throw new InjectException("Failed to get the declared field `" + fieldName + "` from `" + target + "`.", e);
        }
    }

    private static @NotNull Unsafe getUnsafe()
            throws InjectException
    {
        if (Injector.unsafe == null) {
            try {
                Field field = Unsafe.class.getDeclaredField("theUnsafe");
                field.setAccessible(true);

                Unsafe unsafe = (Unsafe) field.get(null);
                if (unsafe == null) {
                    throw new InjectException("`theUnsafe` is null.");
                }
                Injector.unsafe = unsafe;
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new InjectException("Failed to retrieve `theUnsafe`.", e);
            }
        }
        return Injector.unsafe;
    }
}
