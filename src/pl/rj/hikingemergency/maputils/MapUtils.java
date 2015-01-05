package pl.rj.hikingemergency.maputils;

/**
 * Klasa przechowująca stałe potrzebne do generowania adresów URL żądań Google Maps API
 *
 * created on 16:57:09 15 paź 2014 by Radoslaw Jarzynka
 *
 * @author Radoslaw Jarzynka
 */
public class MapUtils {

    public static String GOOGLE_API_KEY = "AIzaSyDDxvU-KXoiTqR9ptg_KpDAo1nRmQDFwFQ";
    public static String GOOGLE_STATIC_MAPS_URL = "https://maps.googleapis.com/maps/api/staticmap?center=";
    public static String FORMAT = "&format=";
    public static String MAPTYPE = "&maptype=";
    public static String SCALE = "&scale=";
    public static String KEY = "&key=";
    public static String SIZE = "&size=";
    public static String ZOOM = "&zoom=";

    /**
     * Klasa wyliczeniowa zawierająca dostępne formaty map
     */
    public static enum MapFormats {
        PNG8("png8"),
        PNG32("png32"),
        GIF("gif"),
        JPG("jpg"),
        JPG_BASELINE("jpg-baseline");
        
        private String parameter;
        
        private MapFormats(String parameter) {
            this.parameter = parameter;
        }
        
        @Override
        public String toString() {
            return parameter;
        }
    }

    /**
     * Klasa wyliczeniowa zawierająca typy map
     */
    public static enum MapTypes {
        ROADMAP("roadmap"),
        SATTELITE("satellite"),
        TERRAIN("terrain"),
        HYBRID("hybrid");
        
        private String parameter;
        
        private MapTypes(String parameter) {
            this.parameter = parameter;
        }
        
        @Override
        public String toString() {
            return parameter;
        }
    }

    /**
     * Klasa wyliczeniowa zawierająca skale map
     */
    public static enum MapScales {
        ONE(1),
        TWO(2);
        private Integer parameter;
        private MapScales(Integer parameter) {
            this.parameter = parameter;
        }
        @Override
        public String toString() {
            return parameter.toString();
        }
    }

    /**
     * klasa wyliczeniowa zawierająca kolory map
     */
    public static enum Colors {
        black, 
        brown, 
        green, 
        purple, 
        yellow, 
        blue, 
        gray, 
        orange, 
        red, 
        white
    }

    /**
     * klasa wyliczeniowa zawierająca rozmiary markerów
     */
    public static enum Sizes {
        tiny, 
        mid, 
        small
    }
    
}
