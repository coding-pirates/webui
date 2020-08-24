package de.cheaterpaul.battleships.network.network.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import de.cheaterpaul.battleships.network.Connection;
import de.cheaterpaul.battleships.network.annotations.bindings.CachedThreadPool;
import de.cheaterpaul.battleships.network.annotations.bindings.FixedThreadPool;
import de.cheaterpaul.battleships.network.annotations.scope.ConnectionScoped;
import de.cheaterpaul.battleships.network.scope.ConnectionScope;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This Network Module binds classes for all distributions to their interface. Also declares {@link com.google.inject.Provider} for ThreadPools.
 *
 * @author Paul Becker
 */
@SuppressWarnings("WeakerAccess")
public class NetworkModule extends AbstractModule {
    @Override
    protected void configure() {
        ConnectionScope connectionScope = new ConnectionScope();
        this.bindScope(ConnectionScoped.class, connectionScope);
        this.bind(ConnectionScope.class).toInstance(connectionScope);
        this.bind(Connection.class).toProvider(() -> {
            throw new IllegalStateException("Cannot load Client instance which is only available in connection scope.");
        }).in(ConnectionScoped.class);
    }

    /**
     * @return A ThreadPool with just one Thread.
     */
    @Provides
    @FixedThreadPool(size = 1)
    protected ExecutorService provideFixedThreadPool() {
        return Executors.newFixedThreadPool(1);
    }

    /**
     * @return new CachedThreadPool.
     */
    @Provides
    @CachedThreadPool
    protected ExecutorService provideCachedThreadPool() {
        return Executors.newCachedThreadPool();
    }
}
