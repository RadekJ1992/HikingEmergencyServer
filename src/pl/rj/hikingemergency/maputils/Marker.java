/**
 * created on 17:25:20 15 paź 2014 by Radoslaw Jarzynka
 * 
 * @author Radoslaw Jarzynka
 */
package pl.rj.hikingemergency.maputils;

public class Marker {
    

    private static String MARKERS = "&markers=";
    private static String COLOR = "color:";
    private static String LABEL = "label:";
    private static String SIZE = "size:";
    private static String DELIMITER = "%7C";
    
    MapUtils.Colors color;
    MapUtils.Sizes size;
    Double latitude;
    Double longitude;
    char label;
    StringBuilder MarkerURLBuilder;
    
    public Marker (double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        MarkerURLBuilder = new StringBuilder(MARKERS);
    }
    /**
     * @return the color
     */
    public MapUtils.Colors getColor() {
        return color;
    }
    /**
     * @param color the color to set
     */
    public void setColor(MapUtils.Colors color) {
        this.color = color;
    }
    /**
     * @return the size
     */
    public MapUtils.Sizes getSize() {
        return size;
    }
    /**
     * @param size the size to set
     */
    public void setSize(MapUtils.Sizes size) {
        this.size = size;
    }
    /**
     * @return the latitude
     */
    public Double getLatitude() {
        return latitude;
    }
    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    /**
     * @return the longitude
     */
    public Double getLongitude() {
        return longitude;
    }
    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    /**
     * @return the label
     */
    public char getLabel() {
        return label;
    }
    /**
     * @param label the label to set
     */
    public void setLabel(char label) {
        this.label = label;
    }
    public String getURL() {
        boolean argumentExists = false;
        if (color != null) {
            MarkerURLBuilder.append(COLOR);
            MarkerURLBuilder.append(color);
            argumentExists = true;
        }
        if (size != null) {
            if (argumentExists) {
                MarkerURLBuilder.append(DELIMITER);
            }
            MarkerURLBuilder.append(SIZE);
            MarkerURLBuilder.append(size);
            argumentExists = true;
        }
        if (label != '\0') {
            if (argumentExists) {
                MarkerURLBuilder.append(DELIMITER);
            }
            MarkerURLBuilder.append(LABEL);
            MarkerURLBuilder.append(label);
            argumentExists = true;
        }
        if (argumentExists){
            MarkerURLBuilder.append(DELIMITER);
        }
        MarkerURLBuilder.append(latitude);
        MarkerURLBuilder.append(",");
        MarkerURLBuilder.append(longitude);
        
        return MarkerURLBuilder.toString();
    }
}
