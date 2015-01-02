package pl.rj.hikingemergency.sms;

import org.smslib.*;
import org.smslib.modem.SerialModemGateway;
import pl.rj.hikingemergency.Constants;
import pl.rj.hikingemergency.model.Log;
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

    private SerialModemGateway gateway;

    public boolean isConnected() {
        if (Service.getInstance().getServiceStatus().equals(Service.ServiceStatus.STARTED)
                || Service.getInstance().getServiceStatus().equals(Service.ServiceStatus.STARTING)) {
            return true;
        }
        return false;
    }

    public static SmsDispatcher getInstance() {
        return instance;
    }

    private static volatile SmsDispatcher instance = new SmsDispatcher();

    private SmsDispatcher() {
        messagesList = new ArrayList<InboundMessage>();
        //connect();
        messagesReader = new MessagesReader();
        messagesReaderThread = new Thread(messagesReader);
        messagesReaderThread.start();
    }

    public List<InboundMessage> getMessagesList() {
        return messagesList;
    }

    public void connect() {
        try {
            outboundNotification = new OutboundNotification();
            Log.getInstance().addLine("Example: Send message from a serial gsm modem.");
            Log.getInstance().addLine(Library.getLibraryDescription());
            Log.getInstance().addLine("Version: " + Library.getLibraryVersion());
            gateway = new SerialModemGateway("modem.com1", Constants.SERIAL_PORT_NAME, 115200, "D-Link", "DWM-156");
            gateway.setInbound(true);
            gateway.setOutbound(true);
            gateway.setSimPin("0000");
            gateway.setSmscNumber("+48602951111");
            Service.getInstance().setOutboundMessageNotification(outboundNotification);
            Service.getInstance().addGateway(gateway);
            Service.getInstance().startService();
            Log.getInstance().addLine("Modem Information:");
            Log.getInstance().addLine("  Manufacturer: " + gateway.getManufacturer());
            Log.getInstance().addLine("  Model: " + gateway.getModel());
            Log.getInstance().addLine("  Serial No: " + gateway.getSerialNo());
            Log.getInstance().addLine("  SIM IMSI: " + gateway.getImsi());
            Log.getInstance().addLine("  Signal Level: " + gateway.getSignalLevel() + " dBm");
            Log.getInstance().addLine("  Battery Level: " + gateway.getBatteryLevel() + "%");
        } catch (SMSLibException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            Service.getInstance().stopService();
            gateway.stopGateway();
            Service.getInstance().removeGateway(gateway);
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
            connect();
            sendMessage(number, message);
        }
    }

    private class MessagesReader implements Runnable {
        @Override
        public void run() {
            try {
                while(true) {
                    if (Service.getInstance().getServiceStatus().equals(Service.ServiceStatus.STARTED)) {
                        Service.getInstance().readMessages(messagesList, InboundMessage.MessageClasses.ALL);
                        for (InboundMessage msg : messagesList) {
                            pl.rj.hikingemergency.model.Message message = new pl.rj.hikingemergency.model.Message(msg.getText());
                            Log.getInstance().addLine("Received SMS message : " + msg.getText());
                            MessagesHandler.getInstance().addMessage(message);
                            Service.getInstance().deleteMessage(msg);
                        }
                        messagesList = new ArrayList<InboundMessage>();
                    }
                    Thread.sleep(3000);
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
        }
    }

    private class OutboundNotification implements IOutboundMessageNotification
    {
        public void process(AGateway gateway, OutboundMessage msg)
        {
            Log.getInstance().addLine("Outbound handler called from Gateway: " + gateway.getGatewayId());
            Log.getInstance().addLine(msg.toString());
        }
    }

    private class InboundNotification implements IInboundMessageNotification
    {
        public void process(AGateway gateway, Message.MessageTypes msgType, InboundMessage msg)
        {
            if (msgType == Message.MessageTypes.INBOUND) Log.getInstance().addLine(">>> New Inbound message detected from Gateway: " + gateway.getGatewayId());
            else if (msgType == Message.MessageTypes.STATUSREPORT) Log.getInstance().addLine(">>> New Inbound Status Report message detected from Gateway: " + gateway.getGatewayId());
            Log.getInstance().addLine(msg.toString());
        }
    }

    private class CallNotification implements ICallNotification
    {
        public void process(AGateway gateway, String callerId)
        {
            Log.getInstance().addLine(">>> New call detected from Gateway: " + gateway.getGatewayId() + " : " + callerId);
        }
    }

    private class GatewayStatusNotification implements IGatewayStatusNotification
    {
        public void process(AGateway gateway, AGateway.GatewayStatuses oldStatus, AGateway.GatewayStatuses newStatus)
        {
            Log.getInstance().addLine(">>> Gateway Status change for " + gateway.getGatewayId() + ", OLD: " + oldStatus + " -> NEW: " + newStatus);
        }
    }

    private class OrphanedMessageNotification implements IOrphanedMessageNotification
    {
        public boolean process(AGateway gateway, InboundMessage msg)
        {
            Log.getInstance().addLine(">>> Orphaned message part detected from " + gateway.getGatewayId());
            Log.getInstance().addLine(msg.toString());
            // Since we are just testing, return FALSE and keep the orphaned message part.
            return false;
        }
    }
}
