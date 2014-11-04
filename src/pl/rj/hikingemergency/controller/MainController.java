package pl.rj.hikingemergency.controller;

import pl.rj.hikingemergency.manager.TCPManager;
import pl.rj.hikingemergency.maputils.MapUtils;
import pl.rj.hikingemergency.maputils.Marker;
import pl.rj.hikingemergency.model.User;
import pl.rj.hikingemergency.utils.Observer;
import pl.rj.hikingemergency.view.MainWindow;

/**
 * Created by radoslawjarzynka on 04.11.14.
 */
public class MainController implements Observer{

    //main application window
    private MainWindow mainWindow;

    private UsersController usersController;



    public MainController() {
        TCPManager.getInstance();
        usersController = new UsersController();
        this.mainWindow = new MainWindow();
        this.mainWindow.setVisible(true);

    }

    public static void main(String[] args) {
        MainController mainController = new MainController();
    }

    //method to update the observer, used by subject
    public void update() {
        for (User user : usersController.getUsers()) {
            Marker m = new Marker(user.getLocations().lastElement().getLatitude(), user.getLocations().lastElement().getLongitude());
            m.setColor(MapUtils.Colors.green);
            mainWindow.getMapArea().addMarker(m);
        }
        mainWindow.getMapArea().refresh();
    }
}
