package by.epam.esm.util;

import by.epam.esm.constant.WebConstant;

import java.util.Map;
import java.util.Set;

/**
 * class UrlBuilder
 * class contains methods for create url
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
public class UrlBuilder {
    /**
     * UrlBuilder
     * method is created url with all request parameters
     * @param webParams - request parameters
     * @return url
     */
    public static String buildParams(Map<String, String[]> webParams) {
        Set<String> keySet = webParams.keySet();
        if (keySet.size() == 0) {
            return "";
        }
        StringBuilder url = new StringBuilder("?");
        for (String key : keySet) {
            if (key.equals(WebConstant.PAGE) || key.equals(WebConstant.LIMIT)) {
                continue;
            }
            addParam(url, webParams.get(key), key);
        }
        if(url.lastIndexOf("&")!=-1){
            url.deleteCharAt(url.lastIndexOf("&"));
        }
        return url.toString();
    }

    private static void addParam(StringBuilder uri, String[] valueArray, String key) {
        for (String value : valueArray) {
            uri.append(key);
            uri.append("=");
            uri.append(value);
            uri.append("&");
        }
    }
}
