/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/22/16 6:37 PM
 * 11-501
 * Task
 */
package ru.kpfu.itis.group501.krylov.db1_it_project;

import java.net.URLEncoder;
import java.util.Map;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/22/16 6:37 PM
 * 11-501
 * Task 
 */
public class QueryString {
    public static String encodeStrings(Map<String, String> map) {
        String res = "?";
        for(Map.Entry<String, String> entry: map.entrySet()) {
            if(entry.getValue() == null) continue;
            res += entry.getKey() + "=" + URLEncoder.encode( entry.getValue()).replaceAll("\\+", "%20") + "&";
        }

        return res;
    }
}
