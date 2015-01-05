package pl.rj.hikingemergency.controller;

import pl.rj.hikingemergency.manager.TCPManager;
import pl.rj.hikingemergency.utils.Observer;
import pl.rj.hikingemergency.view.MainWindow;

/**
 * Główny kontroler aplikacji uruchamiający wątki odbierania wiadomości, zarządcę użytkowników i tworzący widok okna aplikacji
 *
 * Implementuje wzorzec projektowy obserwatora
 * Created by radoslawjarzynka on 04.11.14.
 */
public class MainController implements Observer{

    //główne okno aplikacji
    private MainWindow mainWindow;
    //zarządca użytkowników
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

    /**
     * Punkt wejścia aplikacji
     * @param args
     */
    public static void main(String[] args) {
        MainController mainController = new MainController();
    }

    /**
     * metoda uruchamiana przez obiekt obserwowany w celu powiadomienia obserwatora
     */
    public void update() {
        mainWindow.refresh();
    }
}
