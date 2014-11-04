package pl.rj.hikingemergency.controller;

import pl.rj.hikingemergency.manager.TCPManager;
import pl.rj.hikingemergency.model.Log;
import pl.rj.hikingemergency.model.Message;
import pl.rj.hikingemergency.model.User;
import pl.rj.hikingemergency.utils.Observer;
import pl.rj.hikingemergency.utils.Subject;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by radoslawjarzynka on 04.11.14.
 */
public class UsersController implements Subject, Runnable {

    private ArrayList<User> users;
    private ArrayList<Observer> observers;
    private boolean doWork;
    @Override
    public void run() {
        try {
            while (doWork) {
                Message msg = TCPManager.getInstance().getMessage();
                if (msg != null) {
                    switch (msg.getMessageType()) {
                        case EMG:for (User knownUser : users) {
                            if (knownUser.getPhoneNumber().equals(msg.getMyPhone())) {
                                knownUser.setEmergencyPhoneNumber(msg.getEmgPhone());
                                knownUser.addLocation(msg.getLatitude(), msg.getLongitude());
                                knownUser.setInEmergency(true);
                            } else {
                                User user = new User(msg.getMyPhone(),msg.getEmgPhone());
                                user.addLocation(msg.getLatitude(), msg.getLongitude());
                                user.setInEmergency(true);
                                users.add(user);
                            }
                            notifyObservers();
                        }
                            break;
                        case HI:
                            for (User knownUsers : users) {
                                if (knownUsers.getPhoneNumber().equals(msg.getMyPhone())) {
                                    knownUsers.setEmergencyPhoneNumber(msg.getEmgPhone());
                                    knownUsers.setLocations(new Vector<>());
                                    knownUsers.addLocation(msg.getLatitude(), msg.getLongitude());
                                } else {
                                    User user = new User(msg.getMyPhone(),msg.getEmgPhone());
                                    user.addLocation(msg.getLatitude(), msg.getLongitude());
                                    users.add(user);
                                }
                                notifyObservers();
                            }
                            break;
                        case LOC:
                            for (User knownUsers : users) {
                                if (knownUsers.getPhoneNumber().equals(msg.getMyPhone())) {
                                    knownUsers.addLocation(msg.getLatitude(), msg.getLongitude());
                                } else {
                                    User user = new User(msg.getMyPhone());
                                    user.addLocation(msg.getLatitude(), msg.getLongitude());
                                    users.add(user);
                                }
                                notifyObservers();
                            }
                            break;
                        case UNDEFINED:
                        default:
                            break; //nic nie robimy gdy nie wiemy co to
                    }
                }
                this.wait(1000);
            }
        } catch (InterruptedException e) {
            Log.getInstance().addLine(e.getClass().getName() + ": " + e.getMessage());
        }
    }


    public ArrayList<User> getUsers() {
        return users;
    }

    public UsersController() {
        observers = new ArrayList<>();
        doWork = true;
        users = new ArrayList<>();
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
