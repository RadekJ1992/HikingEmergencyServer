/**
 * created on 13:47:03 18 paź 2014 by Radoslaw Jarzynka
 * 
 * @author Radoslaw Jarzynka
 */
package pl.rj.hikingemergency.view;

import pl.rj.hikingemergency.Constants;
import pl.rj.hikingemergency.manager.DBManager;
import pl.rj.hikingemergency.maputils.GoogleStaticMapsURL;
import pl.rj.hikingemergency.maputils.MapUtils;
import pl.rj.hikingemergency.maputils.Marker;
import pl.rj.hikingemergency.model.User;
import pl.rj.hikingemergency.utils.MapDownloader;
import pl.rj.hikingemergency.utils.ThreadCompleteListener;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MapArea extends JLabel implements ThreadCompleteListener {

    private double centerLatitude;
    private double centerLongitude;
    private int currentZoomLevel;
    private List<Marker> markers;
    private boolean busy;

    public MapArea(double centerLatitude, double centerLongitude, int currentZoomLevel) {
        this.centerLatitude = centerLatitude;
        this.centerLongitude = centerLongitude;
        this.currentZoomLevel = currentZoomLevel;
        this.markers = new ArrayList<>();
        this.busy = false;
    }

    public void zoomIn() {
        currentZoomLevel++;
        refresh();
    }

    public void zoomOut() {
        currentZoomLevel--;
        refresh();
    }

    public void moveUp() {
        centerLatitude+= Constants.MAP_MOVE_STEP;
        refresh();
    }

    public void moveDown() {
        centerLatitude-= Constants.MAP_MOVE_STEP;
        refresh();
    }

    public void moveRight() {
        centerLongitude+= Constants.MAP_MOVE_STEP;
        refresh();
    }

    public void moveLeft() {
        centerLongitude-= Constants.MAP_MOVE_STEP;
        refresh();
    }

    public void addMarker(Marker marker) {
        this.markers.add(marker);
    }

    public void refresh() {
        if (!busy) {
            busy = true;
            GoogleStaticMapsURL staticMapsURL = new GoogleStaticMapsURL(centerLatitude, centerLongitude);
            staticMapsURL.setFormat(MapUtils.MapFormats.JPG);
            staticMapsURL.setMapType(MapUtils.MapTypes.TERRAIN);
            staticMapsURL.setScale(MapUtils.MapScales.TWO);
            staticMapsURL.setSize(Constants.MAP_WIDTH, Constants.MAP_HEIGHT);
            staticMapsURL.setZoom(currentZoomLevel);
            markers = new ArrayList<>();
            Vector<User> allUsers = DBManager.getInstance().getAllUsers();
            if (!allUsers.isEmpty()) {
                for (User user : allUsers) {
                    if (!user.getLocations().isEmpty()) {
                        Marker m = new Marker(user.getLocations().lastElement().getLatitude(), user.getLocations().lastElement().getLongitude());
                        if (MainWindow.getSelectedUser() == null || !user.getPhoneNumber().equals(MainWindow.getSelectedUser().getPhoneNumber())) {
                            m.setSize(MapUtils.Sizes.small);
                        }
                        m.setColor(MapUtils.Colors.green);
                        markers.add(m);
                    }
                }
                for (Marker marker : markers) {
                    staticMapsURL.addMarker(marker);
                }
            }
            MapDownloader downloader = new MapDownloader(staticMapsURL.getURL());
            downloader.addListener(this);
            downloader.start();
        }
    }

    @Override
    public void notifyOfThreadComplete(Thread thread) {
        this.busy = false;
        this.setIcon(new ImageIcon((new ImageIcon(Constants.DESTINATION_MAP_LOCATION)).getImage().getScaledInstance(Constants.MAP_WIDTH, Constants.MAP_HEIGHT,
                java.awt.Image.SCALE_SMOOTH)));
        this.repaint();
    }
}
