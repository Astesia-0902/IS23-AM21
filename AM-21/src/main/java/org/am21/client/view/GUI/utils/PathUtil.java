package org.am21.client.view.GUI.utils;

/**
 * PathUtil is a class that is used to get the path of the image
 */
public class PathUtil {
    private static final String P_PATH = "AM-21/imgs/";

    /**
     * This method is used to get the path of the image
     *
     * @param relativePath the relative path of the image
     * @return the path of the image
     */
    public static String getPath(String relativePath) {
        return P_PATH + relativePath;
    }
}
