package org.javatribe.calculator.common;

public class Constant {

    public static final String SERVER_URL = "http://223.247.215.38:8000";
//        public static final String SERVER_URL = "http://119.147.209.163:9000";
    public static String token = "token";


    public static String ROOT;

    static {
        try {
            ROOT = Constant.class.getResource("/").getPath();
        } catch (Exception e) {
            ROOT = System.getProperty("user.dir");
//            ROOT = System.getProperty("user.dir") + "/src/main/resources";
        }
    }

    public static int FROM_X = 100;
    public static int FROM_Y = 100;
    //    public static int FROM_WIDTH = 1430;
    public static int FROM_WIDTH = 1200;
    //    public static int FROM_HEIGHT = 880;
    public static int FROM_HEIGHT = 700;
}
