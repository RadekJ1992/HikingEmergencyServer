package pl.rj.hikingemergency;

import java.io.File;

/**
 * Klasa zawierająca stałe używane przez aplikację
 * created on 15:15:22 8 paź 2014 by Radoslaw Jarzynka
 *
 * @author Radoslaw Jarzynka
 */
public final class Constants {
    /**
     * Port szeregowy, do którego podłączony jest modem 3G
     */
    public static String SERIAL_PORT_NAME = "/dev/tty.USB Modem Port_";
    /**
     * timeout modemu
     */
    public static int TIMEOUT = 5000;
    /**
     * częstotliwość przesyłu danych do modemu
     */
    public static int BAUD_RATE = 57600;
    /**
     * krok przesuwania mapy przyciskami
     */
    public static float MAP_MOVE_STEP = 0.05f;
    /**
     * Nazwa aplikacji
     */
    public static String APPLICATION_NAME = "Hiking Emergency";
    /**
     * Szerokość geograficzna centrum mapy po otwarciu aplikacji
     */
    public static double DEFAULT_LATITUDE = 52.2296756;
    /**
     * Długość geograficzna centrum mapy po otwarciu aplikacji
     */
    public static double DEFAULT_LONGITUDE = 21.0122287;
    /**
     * Domyślny poziom przybliżenia mapy
     */
    public static int DEFAULT_ZOOM_LVL = 10;
    /**
     * szerokość okna
     */
    public static int WINDOW_WIDTH = 960;
    /**
     * wysokość okna
     */
    public static int WINDOW_HEIGHT = 750;
    /**
     * szerokość pola z mapą
     */
    public static int MAP_WIDTH = 650;
    /**
     * wysokość pola z mapą
     */
    public static int MAP_HEIGHT = 750;
    /**
     * szerokość przycisku w pixelach
     */
    public static int BUTTON_SIZE = 50;
    /**
     * krok używany przy tworzeniu gui aplikacji
     */
    public static int GUI_STEP = 50;

    //nazwy akcji wywoływanych przez przycisku
    public static String MOVE_UP_ACTION = "move_up";
    public static String MOVE_DOWN_ACTION = "move_down";
    public static String MOVE_RIGHT_ACTION = "move_right";
    public static String MOVE_LEFT_ACTION = "move_left";
    public static String ZOOM_IN_ACTION = "zoom_in";
    public static String ZOOM_OUT_ACTION = "zoom_out";
    public static String DISABLE_EMERGENCY_ACTION = "disable_emergency";
    public static String CONNECT_ACTION = "connect";

    /**
     * lokalizacja zapisywanych plików z mapami
     */
    public static String DESTINATION_MAP_LOCATION = "maps" + File.separator + "map.jpg";
    /**
     * adres bazy
     */
    public static String DATABASE_LOCATION = "jdbc:sqlite:HIKINGEMERGENCY.db";
    /**
     * format dat
     */
    public static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * adres IP przekaźnika
     */
    public static String SERVER_IP = "178.62.93.103";
    /**
     * Numer portu przekaźnika
     */
    public static int SERVER_PORT = 8000;
}
