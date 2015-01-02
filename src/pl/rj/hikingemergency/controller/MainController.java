package pl.rj.hikingemergency.controller;

import pl.rj.hikingemergency.manager.TCPManager;
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
        usersController.register(this);
        Thread usersControllerThread = new Thread(usersController);
        this.mainWindow = new MainWindow();
        usersControllerThread.start();
        this.mainWindow.setVisible(true);

    }

    public static void main(String[] args) {
        MainController mainController = new MainController();
    }

    //method to update the observer, used by subject
    public void update() {
        mainWindow.refresh();
        //mainWindow.getMapArea().refresh();
    }
}
