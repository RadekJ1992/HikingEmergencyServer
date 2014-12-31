package pl.rj.hikingemergency.controller;

import pl.rj.hikingemergency.manager.DBManager;
import pl.rj.hikingemergency.model.Location;
import pl.rj.hikingemergency.model.Log;
import pl.rj.hikingemergency.model.Message;
import pl.rj.hikingemergency.model.User;
import pl.rj.hikingemergency.utils.MessagesHandler;
import pl.rj.hikingemergency.utils.Observer;
import pl.rj.hikingemergency.utils.Subject;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by radoslawjarzynka on 04.11.14.
 */
public class UsersController implements Subject, Runnable {

    private ArrayList<Observer> observers;
    private boolean doWork;
    @Override
    public void run() {
        try {
            while (doWork) {
                Message msg = MessagesHandler.getInstance().getMessage();
                if (msg != null) {
                    boolean found = false;
                    switch (msg.getMessageType()) {
                        case EMG:
                            for (User knownUser : DBManager.getInstance().getAllUsers()) {
                                if (knownUser.getPhoneNumber().equals(msg.getMyPhone())) {
                                    knownUser.setEmergencyPhoneNumber(msg.getEmgPhone());
                                    Location location = new Location(msg.getLatitude(), msg.getLongitude());
                                    knownUser.addLocation(location);
                                    knownUser.setInEmergency(true);
                                    found = true;
                                    DBManager.getInstance().setUserInEmergency(knownUser, location);
                                }
                            }
                            if (!found) {
                                User user = new User(msg.getMyPhone(),msg.getEmgPhone());
                                Location location = new Location(msg.getLatitude(), msg.getLongitude());
                                user.addLocation(location);
                                user.setInEmergency(true);
                                DBManager.getInstance().addNewUser(user);
                                DBManager.getInstance().setUserInEmergency(user, location);
                            }
                            notifyObservers();
                            break;
                        case HI:
                            for (User knownUser : DBManager.getInstance().getAllUsers()) {
                                if (knownUser.getPhoneNumber().equals(msg.getMyPhone())) {
                                    knownUser.setEmergencyPhoneNumber(msg.getEmgPhone());
                                    knownUser.setLocations(new Vector<>());
                                    Location location = new Location(msg.getLatitude(), msg.getLongitude());
                                    knownUser.addLocation(location);
                                    DBManager.getInstance().insertUserLocation(knownUser, location);
                                    found = true;
                                }
                            }
                            if (!found) {
                                User user = new User(msg.getMyPhone(),msg.getEmgPhone());
                                Location location = new Location(msg.getLatitude(), msg.getLongitude());
                                user.addLocation(location);
                                DBManager.getInstance().addNewUser(user);
                            }
                            notifyObservers();
                            break;
                        case LOC:
                            for (User knownUser : DBManager.getInstance().getAllUsers()) {
                                if (knownUser.getPhoneNumber().equals(msg.getMyPhone())) {
                                    Location location = new Location(msg.getLatitude(), msg.getLongitude());
                                    knownUser.addLocation(location);
                                    DBManager.getInstance().insertUserLocation(knownUser, location);
                                    found = true;
                                }
                            }
                            if (!found) {
                                User user = new User(msg.getMyPhone());
                                Location location = new Location(msg.getLatitude(), msg.getLongitude());
                                user.addLocation(location);
                                DBManager.getInstance().addNewUser(user);
                            }
                            notifyObservers();
                            break;
                        case UNDEFINED:
                        default:
                            break; //nic nie robimy gdy nie wiemy co to
                    }
                }
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.getInstance().addLine(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public UsersController() {
        observers = new ArrayList<>();
        doWork = true;
    }

    public void setWork(boolean doWork) {
        this.doWork = doWork;
    }

    public void register(Observer obj) {
        observers.add(obj);
    }
    public void unregister(Observer obj) {
        observers.remove(obj);
    }

    //method to notify observers of change
    public void notifyObservers() {
        for (Observer obj : observers) {
            obj.update();
        }
    }

}
