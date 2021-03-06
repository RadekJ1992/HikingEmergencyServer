package pl.rj.hikingemergency.model;

/**
 * Klasa przechowująca informację o przychodzącej wiadomości, niezależnie od tego, czy wiadomość została dostarczona
 * za pomocą protokołu TCP, UDP czy też za pomocą wiadomości SMS
 * Created by radoslawjarzynka on 04.11.14.
 */
public class Message {

    /**
     * enumeracja określająca typ wiadomości
     */
    private MessageType messageType;
    /**
     * Ciąg znaków zawierający całą treść wiadomości
     */
    private String wholeMessage;
    /**
     * numer telefonu osoby wysyłającej zgłoszenie
     */
    private String myPhone;
    /**
     * numer telefonu, na który ma się zgłosić dyspozytor
     */
    private String emgPhone;
    /**
     * szerokość geograficzna przesłana w wiadomości
     */
    private float latitude;
    /**
     * długość geograficzna przesłana w wiadomości
     */
    private float longitude;

    /**
     * konstruktor przyjmujący całą treść wiadomości i tworzący na jej podstawie obiekt Message
     * @param str
     */
    public Message(String str) {
        try {
            String[] msgParts = str.split(";");
            switch (msgParts[0]) {
                case "HI":
                    this.wholeMessage = str;
                    if (msgParts.length == 5) {
                        this.messageType = MessageType.HI;
                        this.myPhone = msgParts[1];
                        this.emgPhone = msgParts[2];
                        this.latitude = Float.valueOf(msgParts[3]);
                        this.longitude = Float.valueOf(msgParts[4]);
                    } else {
                        this.messageType = MessageType.UNDEFINED;
                    }
                    break;
                case "EMG":
                    this.wholeMessage = str;
                    if (msgParts.length == 4) {
                        this.messageType = MessageType.EMG;
                        this.myPhone = msgParts[1];
                        this.latitude = Float.valueOf(msgParts[2]);
                        this.longitude = Float.valueOf(msgParts[3]);
                    } else {
                        this.messageType = MessageType.UNDEFINED;
                    }
                    break;
                case "LOC":
                    this.wholeMessage = str;
                    if (msgParts.length == 4) {
                        this.messageType = MessageType.LOC;
                        this.myPhone = msgParts[1];
                        this.latitude = Float.valueOf(msgParts[2]);
                        this.longitude = Float.valueOf(msgParts[3]);
                    } else {
                        this.messageType = MessageType.UNDEFINED;
                    }
                    break;
                default:
                    this.wholeMessage = str;
                    this.messageType = MessageType.UNDEFINED;
                    break;
            }
        } catch (Exception e) {
            Log.getInstance().addLine("Wrong message format! " + str);
        }
    }
    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getWholeMessage() {
        return wholeMessage;
    }

    public void setWholeMessage(String wholeMessage) {
        this.wholeMessage = wholeMessage;
    }

    public String getMyPhone() {
        return myPhone;
    }

    public void setMyPhone(String myPhone) {
        this.myPhone = myPhone;
    }

    public String getEmgPhone() {
        return emgPhone;
    }

    public void setEmgPhone(String emgPhone) {
        this.emgPhone = emgPhone;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
