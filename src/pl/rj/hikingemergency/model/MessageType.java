package pl.rj.hikingemergency.model;

/**
 * enumeracja przechowywująca typ odebranego komunikatu
 * Created by radoslawjarzynka on 04.11.14.
 */
public enum MessageType {
    HI, //HI;myPhone;emgPhone;lat;lng
    LOC, //LOC;myPhone;lat;lng
    EMG, //EMG;myPhone;lat;lng
    UNDEFINED //inne
}
