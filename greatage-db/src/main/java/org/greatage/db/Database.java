package org.greatage.db;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Database {

    Database dropFirst();

    Database clearCheckSums();

    Database context(String... context);

    Database script(String script);

    Database update();
}
