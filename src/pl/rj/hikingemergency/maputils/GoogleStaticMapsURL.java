package pl.rj.hikingemergency.maputils;

/**
 * Klasa narzędziowa służąca do tworzenia adresów URL żądań wysyłanych do Google Maps API
 *
 * created on 16:36:33 15 paź 2014 by Radoslaw Jarzynka
 *
 * @author Radoslaw Jarzynka
 */
public class GoogleStaticMapsURL {
    
    private StringBuilder URLBuilder;
    
    public GoogleStaticMapsURL(double Latitude, double Longitude) {
        URLBuilder = new StringBuilder(MapUtils.GOOGLE_STATIC_MAPS_URL);
        URLBuilder.append(Latitude);
        URLBuilder.append(",");
        URLBuilder.append(Longitude);
    }
    
    public void setZoom(Integer zoom) {
        URLBuilder.append(MapUtils.ZOOM);
        URLBuilder.append(zoom);
    }
    
    public void setSize(int x, int y) {
        URLBuilder.append(MapUtils.SIZE);
        URLBuilder.append(x);
        URLBuilder.append("x");
        URLBuilder.append(y);
    }
    
    public void setKey() {
        URLBuilder.append(MapUtils.KEY);
        URLBuilder.append(MapUtils.GOOGLE_API_KEY);
    }
    
    public void setScale(MapUtils.MapScales scale) {
        URLBuilder.append(MapUtils.SCALE);
        URLBuilder.append(scale.toString());
    }
    
    public void setFormat(MapUtils.MapFormats format) {
        URLBuilder.append(MapUtils.FORMAT);
        URLBuilder.append(format.toString());
    }
    
    public void setMapType(MapUtils.MapTypes mapType) {
        URLBuilder.append(MapUtils.MAPTYPE);
        URLBuilder.append(mapType.toString());
        
    }
    
    public void addMarker(Marker marker) {
        URLBuilder.append(marker.getURL());
    }
    
    public void addPath() {
        
    }
    public String getURL() {
        return URLBuilder.toString();
    }
    

}
