/**
 * created on 15:15:22 8 paź 2014 by Radoslaw Jarzynka
 * 
 * @author Radoslaw Jarzynka
 */
package pl.rj.hikingemergency;

public final class Constants {
    public static String SERIAL_PORT_NAME = "/dev/tty.USB Modem Port_";
    public static int TIMEOUT = 5000;
    public static int BAUD_RATE = 57600;
    public static float MAP_MOVE_STEP = 0.05f;
    public static String APPLICATION_NAME = "Hiking Emergency";

    public static double DEFAULT_LATITUDE = 52.2296756;
    public static double DEFAULT_LONGITUDE = 21.0122287;
    public static int DEFAULT_ZOOM_LVL = 10;

    public static int WINDOW_WIDTH = 1000;
    public static int WINDOW_HEIGHT = 750;
    public static int MAP_WIDTH = 650;
    public static int MAP_HEIGHT = 750;
    public static int BUTTON_SIZE = 50;
    public static int GUI_STEP = WINDOW_WIDTH/20;

    public static String MOVE_UP_ACTION = "move_up";
    public static String MOVE_DOWN_ACTION = "move_down";
    public static String MOVE_RIGHT_ACTION = "move_right";
    public static String MOVE_LEFT_ACTION = "move_left";
    public static String ZOOM_IN_ACTION = "zoom_in";
    public static String ZOOM_OUT_ACTION = "zoom_out";
}
