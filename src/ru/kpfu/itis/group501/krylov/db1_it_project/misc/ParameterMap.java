package ru.kpfu.itis.group501.krylov.db1_it_project.misc;

import java.util.HashMap;
import java.util.Map;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/21/16 12:34 PM
 */
public class ParameterMap extends HashMap<String, String> {

    public ParameterMap(Map<String, String> map) {
        super(map);
    }

    public ParameterMap() {
        super();
    }

    @Override
    public String put(String key, String value) {
        if(value.equals(""))
            value = null;

        return super.put(key, value);
    }

    @Override
    public boolean containsKey(Object key) {
        return super.containsKey(key) && get(key) != null;
    }

    @Override
    public String get(Object key) {
        String value = super.get(key);
        if(value == null || value.equals(""))
            return null;

        return value;
    }

    public Integer getInt(String key) {
        String str = get(key);
        if(str == null)
            return null;
        return Integer.parseInt(str);
    }

}
