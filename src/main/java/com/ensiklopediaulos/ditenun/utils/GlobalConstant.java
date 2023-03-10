package com.ensiklopediaulos.ditenun.utils;

public class GlobalConstant {

    public static String CURRENT_PROJECT_DIRECTORY = System.getProperty("user.dir");

    public static String NEW_FORMAT_CURRENT_PROJECT_DIRECTORY = String.join("\\", CURRENT_PROJECT_DIRECTORY.split("/"));

    public static String ULOS_MAIN_IMAGES_PATH = "\\src\\main\\resources\\static\\images\\ulospedia\\ulos\\main-images\\";

    public static String WEAVER_IMAGES = "\\src\\main\\resources\\static\\images\\ulospedia\\weaver\\";

    public static String ULOS_IMAGES_PATH = "\\src\\main\\resources\\static\\images\\ensiklopedia\\ulos\\";

    public static String ULOS_CUT_IMAGES_PATH = "\\src\\main\\resources\\static\\images\\ensiklopedia\\ulos-cut\\";

}
