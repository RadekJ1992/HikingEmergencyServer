/**
 * created on 19:51:24 1 paź 2014 by Radoslaw Jarzynka
 *
 * @author Radoslaw Jarzynka
 */
package pl.rj.hikingemergency;

import org.smslib.*;
import org.smslib.modem.SerialModemGateway;
public class Test
{
    public void doIt() throws Exception
    {
        OutboundNotification outboundNotification = new OutboundNotification();
        System.out.println("Example: Send message from a serial gsm modem.");
        System.out.println(Library.getLibraryDescription());
        System.out.println("Version: " + Library.getLibraryVersion());
        SerialModemGateway gateway = new SerialModemGateway("modem.com1", Constants.SERIAL_PORT_NAME, 115200, "D-Link", "DWM-156");
        gateway.setInbound(true);
        gateway.setOutbound(true);
        gateway.setSimPin("0000");
// Explicit SMSC address set is required for some modems.
// Below is for VODAFONE GREECE - be sure to set your own!
        gateway.setSmscNumber("+48601000310");
        Service.getInstance().setOutboundMessageNotification(outboundNotification);
        Service.getInstance().addGateway(gateway);
        Service.getInstance().startService();
        System.out.println();
        System.out.println("Modem Information:");
        System.out.println("  Manufacturer: " + gateway.getManufacturer());
        System.out.println("  Model: " + gateway.getModel());
        System.out.println("  Serial No: " + gateway.getSerialNo());
        System.out.println("  SIM IMSI: " + gateway.getImsi());
        System.out.println("  Signal Level: " + gateway.getSignalLevel() + " dBm");
        System.out.println("  Battery Level: " + gateway.getBatteryLevel() + "%");
        System.out.println();
// Send a message synchronously.
        OutboundMessage msg = new OutboundMessage("48605553905", "Hello from SMSLib!");
        Service.getInstance().sendMessage(msg);
        System.out.println(msg);
        System.out.println("Now Sleeping - Hit <enter> to terminate.");
        System.in.read();
        Service.getInstance().stopService();
    }
    public class OutboundNotification implements IOutboundMessageNotification
    {
        public void process(AGateway gateway, OutboundMessage msg)
        {
            System.out.println("Outbound handler called from Gateway: " + gateway.getGatewayId());
            System.out.println(msg);
        }
    }
    public static void main(String args[])
    {
        Test app = new Test();
        try
        {
            app.doIt();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}