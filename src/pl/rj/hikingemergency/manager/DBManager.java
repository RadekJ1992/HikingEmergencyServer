package pl.rj.hikingemergency.manager;

import pl.rj.hikingemergency.Constants;
import pl.rj.hikingemergency.model.Location;
import pl.rj.hikingemergency.model.Log;
import pl.rj.hikingemergency.model.User;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Vector;

/**
 * Created by radoslawjarzynka on 29.10.14.
 */
public class DBManager {

    private static volatile DBManager instance = new DBManager();

    // private constructor
    private DBManager() {
        Connection c;
        Statement stmt1;
        Statement stmt2;
        Statement stmt3;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(Constants.DATABASE_LOCATION);
            Log.getInstance().addLine("Opened database successfully");

            stmt1 = c.createStatement();
            String sql1 = "CREATE TABLE IF NOT EXISTS USERS " +
                    "(USER_ID INTEGER PRIMARY KEY AUTOINCREMENT    NOT NULL," +
                    " PHONE_NUMBER     TEXT NOT NULL, " +
                    " EMERGENCY_PHONE_NUMBER     TEXT)";
            stmt1.executeUpdate(sql1);
            stmt1.close();

            stmt2 = c.createStatement();
            String sql2= "CREATE TABLE IF NOT EXISTS LOCATIONS" +
                    "(LOCATION_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "USER_ID INTEGER NOT NULL," +
                    "LATITUDE REAL NOT NULL," +
                    "LONGITUDE REAL NOT NULL," +
                    "LOCATION_DATE TEXT NOT NULL," +
                    "FOREIGN KEY(USER_ID) REFERENCES USERS(USER_ID) ON UPDATE CASCADE) ";
            stmt2.executeUpdate(sql2);
            stmt2.close();

            stmt3 = c.createStatement();
            String sql3 = "CREATE TABLE IF NOT EXISTS EMERGENCIES " +
                    "(EMERGENCY_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "USER_ID INTEGER NOT NULL," +
                    "LOCATION_ID INTEGER NOT NULL," +
                    "IS_ACTIVE INTEGER NOT NULL," + //nie ma boolean w sqlite więc jest integer 0 false 1 true
                    "FOREIGN KEY (USER_ID) REFERENCES USERS(USER_ID) ON UPDATE CASCADE," +
                    "FOREIGN KEY (LOCATION_ID) REFERENCES LOCATIONS(LOCATION_ID) ON UPDATE CASCADE)";
            stmt3.executeUpdate(sql3);
            stmt3.close();
            c.close();
        } catch ( Exception e ) {
            e.printStackTrace();
            Log.getInstance().addLine(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Tables created successfully");
    }

    public static DBManager getInstance() {
        return instance;
    }

    public void insertUserLocation(User user, Location location) {
        Connection c;
        PreparedStatement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(Constants.DATABASE_LOCATION);
            int userID = user.getUserID();
            float latitude = location.getLatitude();
            float longitude = location.getLongitude();
            String locationDate = new SimpleDateFormat(Constants.DATE_FORMAT).format(location.getDate());
            String sql = "INSERT INTO LOCATIONS (USER_ID, LATITUDE, LONGITUDE, LOCATION_DATE) " +
                    "VALUES (?,?,?,?);";
            stmt = c.prepareStatement(sql);
            stmt.setInt(1, userID);
            stmt.setFloat(2, latitude);
            stmt.setFloat(3, longitude);
            stmt.setString(4, locationDate);
            stmt.executeUpdate();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            e.printStackTrace();
            Log.getInstance().addLine( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    public Vector<Location> getUserLocations(User user) {
        Connection c;
        PreparedStatement preparedStatement = null;
        Vector<Location> result = new Vector<Location>();
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(Constants.DATABASE_LOCATION);

            String selectSQL = "SELECT LOCATION_ID, LATITUDE, LONGITUDE, LOCATION_DATE FROM LOCATIONS WHERE USER_ID = ? order by LOCATION_ID ASC";
            preparedStatement = c.prepareStatement(selectSQL);
            preparedStatement.setInt(1, user.getUserID());
            ResultSet rs = preparedStatement.executeQuery();

            while ( rs.next() ) {
                int id = rs.getInt("LOCATION_ID");
                float latitude = rs.getFloat("LATITUDE");
                float longitude = rs.getFloat("LONGITUDE");
                String dateString = rs.getString("LOCATION_DATE");
                java.util.Date date = new SimpleDateFormat(Constants.DATE_FORMAT).parse(dateString);
                result.add(new Location(id, latitude, longitude, date));
            }
            rs.close();
            preparedStatement.close();
            c.close();
        } catch ( Exception e ) {
            e.printStackTrace();
            Log.getInstance().addLine(e.getClass().getName() + ": " + e.getMessage());
        }
        return result;
    }

    public void addNewUser(User user) {
        Connection c;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(Constants.DATABASE_LOCATION);
            String sql = "INSERT INTO USERS (PHONE_NUMBER, EMERGENCY_PHONE_NUMBER) " +
                    "VALUES (?,?);";
            preparedStatement = c.prepareStatement(sql);
            preparedStatement.setString(1, user.getPhoneNumber());
            preparedStatement.setString(2, user.getEmergencyPhoneNumber());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            c.close();
            DBManager.getInstance().setUserID(user);
            DBManager.getInstance().insertUserLocation(user, user.getLocations().lastElement());
        } catch ( Exception e ) {
            e.printStackTrace();
            Log.getInstance().addLine( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    public void setUserID(User user) {
        Connection c;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(Constants.DATABASE_LOCATION);

            String selectSQL = "SELECT USER_ID FROM USERS WHERE PHONE_NUMBER = ? AND EMERGENCY_PHONE_NUMBER = ?";
            preparedStatement = c.prepareStatement(selectSQL);
            preparedStatement.setString(1, user.getPhoneNumber());
            preparedStatement.setString(2, user.getEmergencyPhoneNumber());
            ResultSet rs = preparedStatement.executeQuery();

            while ( rs.next() ) {
                int id = rs.getInt("USER_ID");
                user.setUserID(id);
            }
            rs.close();
            preparedStatement.close();
            c.close();
        } catch ( Exception e ) {
            e.printStackTrace();
            Log.getInstance().addLine(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public Vector<User> getAllUsers() {
        Connection c;
        Vector<User> result = new Vector<User>();
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(Constants.DATABASE_LOCATION);

            String selectSQL = "SELECT USER_ID, PHONE_NUMBER, EMERGENCY_PHONE_NUMBER FROM USERS order by USER_ID ASC";
            PreparedStatement preparedStatement = c.prepareStatement(selectSQL);
            ResultSet rs = preparedStatement.executeQuery();

            while ( rs.next() ) {
                int userId = rs.getInt("USER_ID");
                String phoneNumber = rs.getString("PHONE_NUMBER");
                String emergencyPhoneNumber = rs.getString("EMERGENCY_PHONE_NUMBER");
                result.add(new User(userId, phoneNumber, emergencyPhoneNumber));
            }
            rs.close();
            preparedStatement.close();
            c.close();

            for (User u : result) {
                u.setLocations(DBManager.getInstance().getUserLocations(u));
                u.setInEmergency(isUserInEmergency(u));
            }
        } catch ( Exception e ) {
            e.printStackTrace();
            Log.getInstance().addLine(e.getClass().getName() + ": " + e.getMessage());
        }
        return result;
    }

    public void setUserInEmergency(User user, Location location) {
        insertUserLocation(user, location);
        Vector<Location> locations = getUserLocations(user);
        int locationId = locations.lastElement().getId();
        int userId = user.getUserID();
        Connection c;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(Constants.DATABASE_LOCATION);
            String sql = "INSERT INTO EMERGENCIES (USER_ID, LOCATION_ID, IS_ACTIVE) " +
                    "VALUES (?,?, 1);";
            preparedStatement = c.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, locationId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            c.close();
        } catch ( Exception e ) {
            e.printStackTrace();
            Log.getInstance().addLine( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    public void setUserNotInEmergency(User user) {
        Connection c;
        PreparedStatement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(Constants.DATABASE_LOCATION);
            int userID = user.getUserID();
            String sql = "UPDATE EMERGENCIES SET IS_ACTIVE = 0 WHERE USER_ID = ?;";
            stmt = c.prepareStatement(sql);
            stmt.setInt(1, userID);
            stmt.executeUpdate();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            e.printStackTrace();
            Log.getInstance().addLine( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    public boolean isUserInEmergency(User user) {
        Connection c;
        boolean result = false;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(Constants.DATABASE_LOCATION);

            String selectSQL = "SELECT 1 FROM EMERGENCIES WHERE USER_ID = ? AND IS_ACTIVE = 1";
            preparedStatement = c.prepareStatement(selectSQL);
            preparedStatement.setInt(1, user.getUserID());
            ResultSet rs = preparedStatement.executeQuery();

            while ( rs.next() ) {
                result = true;
            }
            rs.close();
            preparedStatement.close();
            c.close();
        } catch ( Exception e ) {
            e.printStackTrace();
            Log.getInstance().addLine(e.getClass().getName() + ": " + e.getMessage());
        }
        return result;
    }
}
