package org.mapuna.ch6;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import org.mapuna.utils.Supers;

public interface SuperAPI {
    /**
     * @return a random hero
     */
    Single<Supers> hero();

    /**
     * @return a random villain
     */
    Single<Supers> villain();

    /**
     * @return all heroes
     */
    Flowable<Supers> heroes();

    /**
     * @return all villains
     */
    Flowable<Supers> villains();

    /**
     * Looks for a character with the given name.
     *
     * @param name the name of the character. Must not be {@code null}
     * @return a {@code Maybe} completed with the found character, empty otherwise
     */
    Maybe<Supers> findByName(String name);

    /**
     * Looks for a character with the given name.
     *
     * @param name the name of the character. Must not be {@code null}
     * @return a {@code Single} completed with the found character, or failed if not found
     */
    Single<Supers> findByNameOrError(String name);
}
