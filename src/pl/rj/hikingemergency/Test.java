/**
 * created on 19:51:24 1 paź 2014 by Radoslaw Jarzynka
 * 
 * @author Radoslaw Jarzynka
 */
package pl.rj.hikingemergency;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.util.Enumeration;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Test {

    public static void showList() {
        Enumeration ports = CommPortIdentifier.getPortIdentifiers();

        while (ports.hasMoreElements())
            System.out.println(((CommPortIdentifier) ports.nextElement())
                    .getName());
    }

    void connect(String portName) throws Exception {
        CommPortIdentifier portIdentifier = CommPortIdentifier
                .getPortIdentifier(portName);
        if (portIdentifier.isCurrentlyOwned()) {
            System.out.println("Error: Port is currently in use");
        } else {
            int timeout = 2000;
            CommPort commPort = portIdentifier.open(this.getClass().getName(),
                    timeout);

            if (commPort instanceof SerialPort) {
                SerialPort serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(57600, SerialPort.DATABITS_8,
                        SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

                InputStream in = serialPort.getInputStream();
                OutputStream out = serialPort.getOutputStream();

                (new Thread(new SerialReader(in))).start();
                (new Thread(new SerialWriter(out))).start();

            } else {
                System.out
                        .println("Error: Only serial ports are handled by this example.");
            }
        }
    }

    public static class SerialReader implements Runnable {

        InputStream in;

        public SerialReader(InputStream in) {
            this.in = in;
        }

        public void run() {
            byte[] buffer = new byte[1024];
            int len = -1;
            try {
                while (true/* ( len = this.in.read( buffer ) ) /*&gt; -1 */) {
                    System.out.print(new String(buffer, 0, len));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static class SerialWriter implements Runnable {

        OutputStream out;

        public SerialWriter(OutputStream out) {
            this.out = out;
        }

        public void run() {
            try {
                int c = 0;
                while (true/* ( c = System.in.read() ) /*&gt; -1 */) {
                    this.out.write(c);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        try {
            CommPortIdentifier portId = CommPortIdentifier
                    .getPortIdentifier(Constants.SERIAL_PORT_NAME);
            SerialPort serialPort = (SerialPort) portId.open(
                    Constants.APPLICATION_NAME, Constants.TIMEOUT);
            // Set serial port to 57600bps-8N1..my favourite
            serialPort.setSerialPortParams(Constants.BAUD_RATE,
                    SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            serialPort.setFlowControlMode(
                    SerialPort.FLOWCONTROL_NONE);
            OutputStream outStream = serialPort.getOutputStream();
            InputStream inStream = serialPort.getInputStream();
            
        } catch (NoSuchPortException e) {
            e.printStackTrace();
        } catch (PortInUseException e) {
            e.printStackTrace();
        } catch (UnsupportedCommOperationException ex) {
            System.err.println(ex.getMessage());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
