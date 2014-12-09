package pl.rj.hikingemergency.sms;

import org.smslib.*;
import org.smslib.modem.SerialModemGateway;
import pl.rj.hikingemergency.Constants;
import pl.rj.hikingemergency.utils.MessagesHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by radoslawjarzynka on 09.12.14.
 */
public class SmsDispatcher {

    private static OutboundNotification outboundNotification;

    private volatile List<InboundMessage> messagesList;

    private MessagesReader messagesReader;
    private Thread messagesReaderThread;

    public static SmsDispatcher getInstance() {
        return instance;
    }

    private static volatile SmsDispatcher instance = new SmsDispatcher();

    private SmsDispatcher() {
        messagesList = new ArrayList<InboundMessage>();
        setup();
        messagesReader = new MessagesReader();
        messagesReaderThread = new Thread(messagesReader);
        messagesReaderThread.start();
    }

    private void setup() {
        try {
            outboundNotification = new OutboundNotification();
            System.out.println("Example: Send message from a serial gsm modem.");
            System.out.println(Library.getLibraryDescription());
            System.out.println("Version: " + Library.getLibraryVersion());
            SerialModemGateway gateway = new SerialModemGateway("modem.com1", Constants.SERIAL_PORT_NAME, 115200, "D-Link", "DWM-156");
            gateway.setInbound(true);
            gateway.setOutbound(true);
            gateway.setSimPin("0000");
            gateway.setSmscNumber("+48602951111");
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
        } catch (SMSLibException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String number, String message) {
        if (Service.getInstance().getServiceStatus().equals(Service.ServiceStatus.STARTED)) {
            OutboundMessage msg = new OutboundMessage(number, message);
            try {
                Service.getInstance().sendMessage(msg);
            } catch (TimeoutException e) {
                e.printStackTrace();
            } catch (GatewayException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            setup();
            sendMessage(number, message);
        }
    }

    private class MessagesReader implements Runnable {
        @Override
        public void run() {
            try {
                if (Service.getInstance().getServiceStatus().equals(Service.ServiceStatus.STARTED)) {
                    Service.getInstance().readMessages(messagesList, InboundMessage.MessageClasses.ALL);
                    for (InboundMessage msg : messagesList) {
                        pl.rj.hikingemergency.model.Message message = new pl.rj.hikingemergency.model.Message(msg.getText());
                        MessagesHandler.getInstance().addMessage(message);
                    }
                } else {
                    Service.getInstance().startService();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (GatewayException e) {
                e.printStackTrace();
            } catch (SMSLibException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private class OutboundNotification implements IOutboundMessageNotification
    {
        public void process(AGateway gateway, OutboundMessage msg)
        {
            System.out.println("Outbound handler called from Gateway: " + gateway.getGatewayId());
            System.out.println(msg);
        }
    }

    private class InboundNotification implements IInboundMessageNotification
    {
        public void process(AGateway gateway, Message.MessageTypes msgType, InboundMessage msg)
        {
            if (msgType == Message.MessageTypes.INBOUND) System.out.println(">>> New Inbound message detected from Gateway: " + gateway.getGatewayId());
            else if (msgType == Message.MessageTypes.STATUSREPORT) System.out.println(">>> New Inbound Status Report message detected from Gateway: " + gateway.getGatewayId());
            System.out.println(msg);
        }
    }

    private class CallNotification implements ICallNotification
    {
        public void process(AGateway gateway, String callerId)
        {
            System.out.println(">>> New call detected from Gateway: " + gateway.getGatewayId() + " : " + callerId);
        }
    }

    private class GatewayStatusNotification implements IGatewayStatusNotification
    {
        public void process(AGateway gateway, AGateway.GatewayStatuses oldStatus, AGateway.GatewayStatuses newStatus)
        {
            System.out.println(">>> Gateway Status change for " + gateway.getGatewayId() + ", OLD: " + oldStatus + " -> NEW: " + newStatus);
        }
    }

    private class OrphanedMessageNotification implements IOrphanedMessageNotification
    {
        public boolean process(AGateway gateway, InboundMessage msg)
        {
            System.out.println(">>> Orphaned message part detected from " + gateway.getGatewayId());
            System.out.println(msg);
            // Since we are just testing, return FALSE and keep the orphaned message part.
            return false;
        }
    }
}
