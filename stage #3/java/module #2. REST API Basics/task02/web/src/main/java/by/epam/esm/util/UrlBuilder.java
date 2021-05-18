package by.epam.esm.util;

import java.util.Map;
import java.util.Set;

public class UrlBuilder {
    public static String buildParams(Map<String, String[]> webParams) {
        Set<String> keySet = webParams.keySet();
        if (keySet.size() == 0) {
            return "";
        }
        StringBuilder url = new StringBuilder("?");
        for (String key : keySet) {
            addParam(url, webParams.get(key), key);
        }
        url.deleteCharAt(url.lastIndexOf("&"));
        return url.toString();
    }

    public static void addParam(StringBuilder uri, String[] valueArray, String key) {
        for (String value : valueArray) {
            uri.append(key);
            uri.append("=");
            uri.append(value);
            uri.append("&");
        }
    }
}
