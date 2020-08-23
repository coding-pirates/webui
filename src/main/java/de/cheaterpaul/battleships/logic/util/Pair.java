package de.cheaterpaul.battleships.logic.util;

import com.google.common.base.Objects;

/**
 * @author Paul Becker
 */
public class Pair<T, Z> {

    private final T key;
    private final Z value;

    public Pair(T key, Z value) {
        this.key = key;
        this.value = value;
    }

    public static <T, Z> Pair<T, Z> of(T first, Z second) {
        return new Pair<>(first, second);
    }

    public T getKey() {
        return key;
    }

    public Z getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equal(key, pair.key) &&
                Objects.equal(value, pair.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(key, value);
    }

    @Override
    public String toString() {
        return "Pair{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }
}
