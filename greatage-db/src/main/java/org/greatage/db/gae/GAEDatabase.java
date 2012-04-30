package org.greatage.db.gae;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import org.greatage.db.Database;
import org.greatage.util.CollectionUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GAEDatabase implements Database {
    private final DatastoreService store;

    private final Set<String> context = CollectionUtils.newSet();
    private boolean dropFirst;
    private boolean clearCheckSums;
    private String script = "change_log.groovy";

    public GAEDatabase() {
        this(DatastoreServiceFactory.getDatastoreService());
    }

    public GAEDatabase(final DatastoreService store) {
        this.store = store;
    }

    public Database dropFirst() {
        dropFirst = true;
        return this;
    }

    public Database clearCheckSums() {
        clearCheckSums = true;
        return this;
    }

    public Database context(final String... context) {
        Collections.addAll(this.context, context);
        return this;
    }

    public Database script(final String script) {
        this.script = script;
        return this;
    }

    public Database update() {
        final GAEChangeLog log = new GAEChangeLog(store, context, clearCheckSums);
        log.name(script);

        new GAELock().execute(store);
        try {
            if (dropFirst) {
                new GAEDeleteAll().execute(store);
            }

            final Binding binding = new Binding();
            binding.setVariable("log", log);

            try {
                final GroovyScriptEngine engine = new GroovyScriptEngine("db");
                engine.run(script, binding);
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (ResourceException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (ScriptException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            log.end();
        } finally {
            new GAEUnlock().execute(store);
        }
        return this;
    }
}
