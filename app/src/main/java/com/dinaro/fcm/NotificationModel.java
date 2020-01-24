package com.dinaro.fcm;


public class NotificationModel {
    public NotificationMessageModel message_information;

    public String message;

    public class NotificationMessageModel {
        public String dateTime;
        public String quickblox_id;
        public String receiver_id;
        public String type;
        public String user_id;
        public String profile_image;
    }
}
