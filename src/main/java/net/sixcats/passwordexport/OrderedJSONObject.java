package net.sixcats.passwordexport;

import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;

/**
 * A subclass of JSONObject which preserves the property ordering.
 */
public class OrderedJSONObject extends JSONObject {
    public OrderedJSONObject() {
        super();
        try {
            final Field map = JSONObject.class.getDeclaredField("map");
            map.setAccessible(true);
            map.set(this, new LinkedHashMap<>());
            map.setAccessible(false);
        } catch (RuntimeException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
