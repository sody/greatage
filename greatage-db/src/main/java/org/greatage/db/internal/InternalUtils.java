package org.greatage.db.internal;

import org.greatage.db.ChangeLog;
import org.greatage.db.Database;
import org.greatage.util.EncodeUtils;
import org.greatage.util.StringUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class InternalUtils {
    private static final String DEFAULT_ALGORITHM = "MD5";

    public static String calculateCheckSum(final String compositeCheckSum) {
        final byte[] encoded = EncodeUtils.encode(compositeCheckSum.getBytes(), DEFAULT_ALGORITHM);
        return DEFAULT_ALGORITHM + ":" + StringUtils.toHexString(encoded) + ";";
    }

    public static boolean isValidCheckSum(final String checkSum) {
        return checkSum != null && checkSum.startsWith(DEFAULT_ALGORITHM);
    }

    public static Database.Script createScript(final String script) {
        final int dotIndex = script.lastIndexOf(".");
        final String scriptName = dotIndex < 0 ? script : script.substring(0, dotIndex);
        final String scriptType = dotIndex < 0 ? null : script.substring(dotIndex + 1);

        return new Database.Script() {
            public void execute(final ChangeLog log) {
                log.name(scriptName);
                try {
                    final ScriptEngineManager manager = new ScriptEngineManager();
                    final ScriptEngine groovy = manager.getEngineByName(scriptType);
                    groovy.put("log", log);
                    final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                    final InputStream stream = classLoader.getResourceAsStream(script);
                    groovy.eval(new InputStreamReader(stream));
                } catch (ScriptException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        };
    }
}
