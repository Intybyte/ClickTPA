package me.vaan.clickTpa.common;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.function.Supplier;

public class DefaultMap<K, V> extends HashMap<K, V> {
    private final @NotNull Supplier<V> defaultSupplier;

    public DefaultMap(@NotNull Supplier<V> defaultValue) {
        super();
        this.defaultSupplier = defaultValue;
    }

    @Override
    public V get(Object key) {
        V value = super.get(key);
        if (value != null) {
            return value;
        }

        V defaultValue = defaultSupplier.get();
        put((K) key, defaultValue);
        return defaultValue;
    }
}
