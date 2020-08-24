package de.cheaterpaul.battleships.network.annotations.scope;

import com.google.inject.ScopeAnnotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation for {@link de.cheaterpaul.battleships.network.Connection} to apply
 * the {@link de.cheaterpaul.battleships.network.scope.ConnectionScope} to every object
 *
 * @author Paul Becker
 */
@Target({TYPE, METHOD})
@Retention(RUNTIME)
@ScopeAnnotation
public @interface ConnectionScoped {
}
