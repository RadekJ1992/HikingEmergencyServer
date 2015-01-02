/**
 * created on 15:49:21 15 paź 2014 by Radoslaw Jarzynka
 * 
 * @author Radoslaw Jarzynka
 */
package pl.rj.hikingemergency.view;

import pl.rj.hikingemergency.Constants;
import pl.rj.hikingemergency.manager.DBManager;
import pl.rj.hikingemergency.model.Location;
import pl.rj.hikingemergency.model.Log;
import pl.rj.hikingemergency.model.User;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class MainWindow extends JFrame implements ActionListener, ListSelectionListener {

    private MapArea mapArea;

    private JButton upButton;
    private JButton downButton;
    private JButton leftButton;
    private JButton rightButton;
    private JButton zoomInButton;
    private JButton zoomOutButton;

    private JLabel telephoneNumberLabel;
    private JLabel telephoneNumberField;
    private JLabel emergencyTelephoneNumberLabel;
    private JLabel emergencyTelephoneNumberField;
    private JLabel locationLabel;
    private JLabel dateLabel;
    private JLabel dateField;
    private JLabel latitudeLabel;
    private JLabel latitudeField;
    private JLabel longitudeLabel;
    private JLabel longitudeField;

    private JLabel allUsersLabel;
    private JTable allUsersTable;
    private JScrollPane allUsersScrollPane;

    private JPanel contentPane;

    private static User selectedUser;
    
    /**
     * @throws HeadlessException
     */
    public MainWindow() throws HeadlessException {
        setTitle(Constants.APPLICATION_NAME);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        contentPane = new JPanel();
        contentPane.setLayout(null);

        this.setResizable(false);

        //dodanie window listenera
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                //przy wylaczeniu aplikacji zapisanie logu do pliku
                Log.getInstance().saveLogToFile();
                System.exit(0);
            }
        });

        mapArea = new MapArea(Constants.DEFAULT_LATITUDE, Constants.DEFAULT_LONGITUDE, Constants.DEFAULT_ZOOM_LVL);
        mapArea.setSize(Constants.MAP_WIDTH, Constants.MAP_HEIGHT);
        mapArea.setBounds(0, 0, Constants.MAP_WIDTH, Constants.MAP_HEIGHT);
        mapArea.setVisible(true);

        mapArea.refresh();
        contentPane.add(mapArea);

        //tworzenie przyciskow
        upButton = new JButton("^");
        upButton.setActionCommand(Constants.MOVE_UP_ACTION);
        upButton.setBounds((15 * Constants.GUI_STEP), Constants.GUI_STEP, Constants.BUTTON_SIZE, Constants.BUTTON_SIZE);
        upButton.setVisible(true);
        upButton.addActionListener(this);
        contentPane.add(upButton);

        downButton = new JButton("v");
        downButton.setActionCommand(Constants.MOVE_DOWN_ACTION);
        downButton.setBounds( 15*Constants.GUI_STEP, 3*Constants.GUI_STEP, Constants.BUTTON_SIZE, Constants.BUTTON_SIZE);
        downButton.setVisible(true);
        downButton.addActionListener(this);
        contentPane.add(downButton);

        rightButton = new JButton(">");
        rightButton.setActionCommand(Constants.MOVE_RIGHT_ACTION);
        rightButton.setBounds( 16*Constants.GUI_STEP, 2*Constants.GUI_STEP, Constants.BUTTON_SIZE, Constants.BUTTON_SIZE);
        rightButton.setVisible(true);
        rightButton.addActionListener(this);
        contentPane.add(rightButton);

        leftButton = new JButton("<");
        leftButton.setActionCommand(Constants.MOVE_LEFT_ACTION);
        leftButton.setBounds(14 * Constants.GUI_STEP, 2 * Constants.GUI_STEP, Constants.BUTTON_SIZE, Constants.BUTTON_SIZE);
        leftButton.setVisible(true);
        leftButton.addActionListener(this);
        contentPane.add(leftButton);

        zoomInButton = new JButton("+");
        zoomInButton.setActionCommand(Constants.ZOOM_IN_ACTION);
        zoomInButton.setBounds( 18 * Constants.GUI_STEP, 1*Constants.GUI_STEP, Constants.BUTTON_SIZE, Constants.BUTTON_SIZE);
        zoomInButton.setVisible(true);
        zoomInButton.addActionListener(this);
        contentPane.add(zoomInButton);

        zoomOutButton = new JButton("-");
        zoomOutButton.setActionCommand(Constants.ZOOM_OUT_ACTION);
        zoomOutButton.setBounds( 18 * Constants.GUI_STEP, 3*Constants.GUI_STEP, Constants.BUTTON_SIZE, Constants.BUTTON_SIZE);
        zoomOutButton.setVisible(true);
        zoomOutButton.addActionListener(this);
        contentPane.add(zoomOutButton);

        telephoneNumberLabel = new JLabel("Telephone number:");
        telephoneNumberLabel.setBounds(14*Constants.GUI_STEP, 5*Constants.GUI_STEP, 5*Constants.GUI_STEP, Constants.GUI_STEP);
        telephoneNumberLabel.setVisible(true);
        contentPane.add(telephoneNumberLabel);

        telephoneNumberField = new JLabel("-");
        telephoneNumberField.setBounds(14*Constants.GUI_STEP, (int)(5.5*Constants.GUI_STEP), 5*Constants.GUI_STEP, Constants.GUI_STEP);
        telephoneNumberField.setVisible(true);
        contentPane.add(telephoneNumberField);

        emergencyTelephoneNumberLabel = new JLabel("Emergency Telephone number");
        emergencyTelephoneNumberLabel.setBounds(14*Constants.GUI_STEP, 6*Constants.GUI_STEP, 5*Constants.GUI_STEP, Constants.GUI_STEP);
        emergencyTelephoneNumberLabel.setVisible(true);
        contentPane.add(emergencyTelephoneNumberLabel);

        emergencyTelephoneNumberField = new JLabel("-");
        emergencyTelephoneNumberField.setBounds(14*Constants.GUI_STEP, (int)(6.5*Constants.GUI_STEP), 5*Constants.GUI_STEP, Constants.GUI_STEP);
        emergencyTelephoneNumberField.setVisible(true);
        contentPane.add(emergencyTelephoneNumberField);

        locationLabel = new JLabel("Location:");
        locationLabel.setBounds(14*Constants.GUI_STEP, 7*Constants.GUI_STEP, 5*Constants.GUI_STEP, Constants.GUI_STEP);
        locationLabel.setVisible(true);
        contentPane.add(locationLabel);

        dateLabel = new JLabel("Date:");
        dateLabel.setBounds(14*Constants.GUI_STEP, (int)(7.5*Constants.GUI_STEP), Constants.GUI_STEP, Constants.GUI_STEP);
        dateLabel.setVisible(true);
        contentPane.add(dateLabel);

        dateField = new JLabel("-");
        dateField.setBounds(15*Constants.GUI_STEP, (int)(7.5*Constants.GUI_STEP), 4*Constants.GUI_STEP, Constants.GUI_STEP);
        dateField.setVisible(true);
        contentPane.add(dateField);

        latitudeLabel = new JLabel("Lat:");
        latitudeLabel.setBounds(14*Constants.GUI_STEP, 8*Constants.GUI_STEP, Constants.GUI_STEP, Constants.GUI_STEP);
        latitudeLabel.setVisible(true);
        contentPane.add(latitudeLabel);

        latitudeField = new JLabel("-");
        latitudeField.setBounds(15*Constants.GUI_STEP, 8*Constants.GUI_STEP, 4*Constants.GUI_STEP, Constants.GUI_STEP);
        latitudeField.setVisible(true);
        contentPane.add(latitudeField);

        longitudeLabel = new JLabel("Lng:");
        longitudeLabel.setBounds(14*Constants.GUI_STEP, (int)(8.5*Constants.GUI_STEP), Constants.GUI_STEP, Constants.GUI_STEP);
        longitudeLabel.setVisible(true);
        contentPane.add(longitudeLabel);

        longitudeField = new JLabel("-");
        longitudeField.setBounds(15*Constants.GUI_STEP, (int)(8.5*Constants.GUI_STEP), 4*Constants.GUI_STEP, Constants.GUI_STEP);
        longitudeField.setVisible(true);
        contentPane.add(longitudeField);

        allUsersLabel = new JLabel("All Users:");
        allUsersLabel.setBounds(14*Constants.GUI_STEP, (int)(9.5*Constants.GUI_STEP), 5*Constants.GUI_STEP, Constants.GUI_STEP);
        allUsersLabel.setVisible(true);
        contentPane.add(allUsersLabel);

        Vector<Vector<String>> tableData = getUsersForTable();
        Vector<String> columnNames = new Vector<String>();
        columnNames.add("Phone Number");
        columnNames.add("Needs Help?");

        allUsersTable = new JTable(tableData, columnNames);
        allUsersTable.setVisible(true);
        allUsersTable.getSelectionModel().addListSelectionListener(this);

        allUsersScrollPane = new JScrollPane(allUsersTable);
        allUsersScrollPane.setBounds(14*Constants.GUI_STEP, (int)(10.5*Constants.GUI_STEP), 5*Constants.GUI_STEP, 4*Constants.GUI_STEP);
        allUsersScrollPane.setVisible(true);
        contentPane.add(allUsersScrollPane);

     //   private JTable allUsersTable;
        //private JScrollPane allUsersScrollPane;

        this.setContentPane(contentPane);
        this.pack();
        this.setLocationByPlatform(true);
        this.setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        this.setVisible(true);

    }

    public MapArea getMapArea() {
        return mapArea;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(Constants.MOVE_UP_ACTION)) {
            mapArea.moveUp();
        }
        if (e.getActionCommand().equals(Constants.MOVE_DOWN_ACTION)) {
            mapArea.moveDown();
        }
        if (e.getActionCommand().equals(Constants.MOVE_LEFT_ACTION)) {
            mapArea.moveLeft();
        }
        if (e.getActionCommand().equals(Constants.MOVE_RIGHT_ACTION)) {
            mapArea.moveRight();
        }
        if (e.getActionCommand().equals(Constants.ZOOM_IN_ACTION)) {
            mapArea.zoomIn();
        }
        if (e.getActionCommand().equals(Constants.ZOOM_OUT_ACTION)) {
            mapArea.zoomOut();
        }
    }

    private Vector<Vector<String>> getUsersForTable() {
        Vector<User> usersVector = DBManager.getInstance().getAllUsers();
        Vector<Vector<String>> result = new Vector<>();
        for (User user : usersVector) {
            Vector<String> row = new Vector<>();
            row.add(user.getPhoneNumber());
            row.add(user.isInEmergency() ? "YES" : "NO");
            result.add(row);
        }
        return result;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        String phoneNumber = allUsersTable.getValueAt(allUsersTable.getSelectedRow(), 0).toString();
        for (User user : DBManager.getInstance().getAllUsers()) {
            if (user.getPhoneNumber().equals(phoneNumber)) {
                MainWindow.selectedUser = user;
                this.telephoneNumberField.setText(user.getPhoneNumber());
                this.emergencyTelephoneNumberField.setText(user.getEmergencyPhoneNumber() == null ? "-" : user.getEmergencyPhoneNumber());
                Vector<Location> locations = user.getLocations();
                if (!locations.isEmpty()) {
                    this.dateField.setText(locations.lastElement().getDate().toString());
                    this.latitudeField.setText("" + locations.lastElement().getLatitude());
                    this.longitudeField.setText("" + locations.lastElement().getLongitude());
                }
                mapArea.refresh();
            }
        }
    }

    public static User getSelectedUser() {
        return selectedUser;
    }

    public void refresh() {
        Vector<Vector<String>> tableData = getUsersForTable();
        Vector<String> columnNames = new Vector<String>();
        columnNames.add("Phone Number");
        columnNames.add("Needs Help?");

        contentPane.remove(allUsersScrollPane);

        this.allUsersTable = new JTable(tableData, columnNames);
        this.allUsersTable.setVisible(true);
        this.allUsersTable.getSelectionModel().addListSelectionListener(this);

        this.allUsersScrollPane = new JScrollPane(allUsersTable);
        this.allUsersScrollPane.setBounds(14*Constants.GUI_STEP, (int)(10.5*Constants.GUI_STEP), 5*Constants.GUI_STEP, 4*Constants.GUI_STEP);
        this.allUsersScrollPane.setVisible(true);
        this.contentPane.add(allUsersScrollPane);
        this.mapArea.refresh();
        this.repaint();
    }
}
