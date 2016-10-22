/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/22/16 6:37 PM
 * 11-501
 * Task
 */
package app;

import java.util.Map;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/22/16 6:37 PM
 * 11-501
 * Task 
 */
public class QueryString {

    public static String encodeStringArrays(Map<String, String[]> map) {
        String res = "?";
        for(Map.Entry<String, String[]> entry: map.entrySet()) {
            String key = entry.getKey();
            for(String value: entry.getValue()) {
                res += key + "=" + value + "&";
            }
        }
        return res;
    }

    public static String encodeStrings(Map<String, String> map) {
        String res = "?";
        for(Map.Entry<String, String> entry: map.entrySet()) {
            res += entry.getKey() + "=" + entry.getValue() + "&";
        }

        return res;
    }
}
