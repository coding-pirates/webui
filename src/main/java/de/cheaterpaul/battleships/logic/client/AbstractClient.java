package de.cheaterpaul.battleships.logic.client;

import com.google.common.base.Objects;

import javax.annotation.Nonnull;
import java.io.Serializable;

/**
 * Represents a client.
 *
 * @author Interdoc committee
 * @author Paul Becker
 * @author Andre Blanke
 */
public abstract class AbstractClient implements Serializable {

    /**
     * The unique ID of this {@code Client}.
     */
    protected final int id;

    /**
     * The user name selected by this {@code Client}.
     */
    @Nonnull
    protected final String name;

    protected AbstractClient(final int id, @Nonnull final String name) {
        this.id   = id;
        this.name = name;
    }

    /**
     * Return the specific Client ID
     * @return {@link #id}
     */
    public int getId() {
        return id;
    }

    /**
     * Return the specific name of the client
     * @return {@link #name}
     */
    @Nonnull
    public String getName() {
        return name;
    }

    @Nonnull
    public abstract ClientType getClientType();

    @Nonnull
    public abstract ClientType handleClientAs();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractClient client = (AbstractClient) o;
        return id == client.id &&
                Objects.equal(name, client.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name);
    }

    @Override
    public String toString() {
        return name;
    }
}
