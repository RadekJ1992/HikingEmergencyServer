/**
 * created on 15:49:21 15 paź 2014 by Radoslaw Jarzynka
 * 
 * @author Radoslaw Jarzynka
 */
package pl.rj.hikingemergency.view;

import pl.rj.hikingemergency.*;
import pl.rj.hikingemergency.logger.Logger;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.swing.*;

public class MainWindow extends JFrame implements ActionListener {

    private MapArea mapArea;

    private JButton upButton;
    private JButton downButton;
    private JButton leftButton;
    private JButton rightButton;
    private JButton zoomInButton;
    private JButton zoomOutButton;

    private JPanel contentPane;

    private Logger logger;

    
    
    /**
     * @throws HeadlessException
     */
    public MainWindow() throws HeadlessException {
        setTitle(Constants.APPLICATION_NAME);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(700, 500);
        

        try {
            String imageUrl = "https://maps.googleapis.com/maps/api/staticmap?center=40,26&zoom=10&size=600x300";
            String destinationFile = "image.jpg";
            String str = destinationFile;
            URL url = new URL(imageUrl);
            InputStream is = url.openStream();
            OutputStream os = new FileOutputStream(destinationFile);

            byte[] b = new byte[2048];
            int length;

            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }

            is.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        this.add(new JLabel(new ImageIcon((new ImageIcon("image.jpg")).getImage().getScaledInstance(630, 450,
                java.awt.Image.SCALE_SMOOTH))));

        this.setVisible(true);
        this.pack();


    }

    /**
     * @param gc
     */
    public MainWindow(GraphicsConfiguration gc) {
        super(gc);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param title
     * @throws HeadlessException
     */
    public MainWindow(String title) throws HeadlessException {
        super(title);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param title
     * @param gc
     */
    public MainWindow(String title, GraphicsConfiguration gc) {
        super(title, gc);
        // TODO Auto-generated constructor stub
    }

public static void main(String[] args) {
        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainWindow ex = new MainWindow();
                ex.setVisible(true);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
