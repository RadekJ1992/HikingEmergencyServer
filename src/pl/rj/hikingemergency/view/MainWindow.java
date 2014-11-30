/**
 * created on 15:49:21 15 paź 2014 by Radoslaw Jarzynka
 * 
 * @author Radoslaw Jarzynka
 */
package pl.rj.hikingemergency.view;

import pl.rj.hikingemergency.Constants;
import pl.rj.hikingemergency.model.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame implements ActionListener {

    private MapArea mapArea;

    private JButton upButton;
    private JButton downButton;
    private JButton leftButton;
    private JButton rightButton;
    private JButton zoomInButton;
    private JButton zoomOutButton;

    private JPanel contentPane;
    
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
        /*Marker m = new Marker(Constants.DEFAULT_LATITUDE, Constants.DEFAULT_LONGITUDE);
        m.setLabel('x');
        m.setColor(MapUtils.Colors.blue);
        m.setSize(MapUtils.Sizes.mid);
        mapArea.addMarker(m);
        Marker n = new Marker(Constants.DEFAULT_LATITUDE+0.02, Constants.DEFAULT_LONGITUDE-0.1);
        n.setLabel('y');
        n.setColor(MapUtils.Colors.green);
        mapArea.addMarker(n);*/
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
}
