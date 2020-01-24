package com.dinaro.fcm;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.util.Log;

import com.dinaro.R;
import com.dinaro.activities.DashBoardActivity;
import com.dinaro.mpesa.utils.NotificationUtils;
import com.dinaro.utils.AppConstant;
import com.dinaro.utils.LogUtils;
import com.dinaro.utils.PrefManagerNew;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONObject;

import java.util.Map;

import static com.dinaro.mpesa.utils.AppConstants.PUSH_NOTIFICATION;


@SuppressWarnings("ALL")
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    public static NotificationManager notificationManager;
    private String filterMessage = "";
    private String title = "Dinaro";
    private PendingIntent pendingIntent;
    private Intent intent;
    private NotificationUtils notificationUtils;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        try {
            if (remoteMessage.getData().size() > 0) {

                Object object = new Gson().fromJson(new Gson().toJsonTree(remoteMessage.getData()).toString(), Object.class);
                ActivityManager am = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
                ComponentName cn = am.getRunningTasks(1).get(0).topActivity;

                String currentTopActivity = cn.getClassName();
//                Object mpesa = new Gson().toJsonTree(((LinkedTreeMap) object).get("custom").toString(), Object.class);

                Map<String, String> params = remoteMessage.getData();
                JSONObject jsonobject = new JSONObject(params);
                Log.e("JSON_OBJECT", jsonobject.toString());
                Object custom = new Gson().fromJson(jsonobject.get("custom").toString(),Object.class);
                handleDataMessage(custom);
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        // Check if message contains a notification payload.
//        if (remoteMessage.getNotification() != null) {
//            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
//            handleNotification(remoteMessage.getNotification().getBody());
//        }

        // Check if message contains a data payload.
//        if (remoteMessage.getData().size() > 0) {
//            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
//            try {
//                JSONObject json = new JSONObject(remoteMessage.getData().toString());
//                handleDataMessage(json);
//            } catch (Exception e) {
//                Log.e(TAG, "Exception: " + e.getMessage());
//            }
//        }
    }

    private void setUpPendingIntent(final NotificationModel notificationModel) {

    }


    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        if (s != null && !s.equalsIgnoreCase("")) {
            PrefManagerNew.saveStringPreferences(getApplicationContext(), AppConstant.DEVICE_TOKEN, s);
            LogUtils.errorLog(/*TAG*/"token", s);

        } else {
            FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        @Override
                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "getInstanceId failed", task.getException());
                                return;
                            }
                            // Get newback Instance ID token
                            String token = task.getResult().getToken();
                            PrefManagerNew.saveStringPreferences(getApplicationContext(), AppConstant.DEVICE_TOKEN, token);
                            LogUtils.errorLog(/*TAG*/"token", token);
                        }
                    });
        }
    }


//******************* Call when generate Push notification **************************

    private void setUpNotifcaition(String messageBody, PendingIntent pendingIntent) {
        if (messageBody != null && messageBody.length() > 0) {

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        // .setColor(getResources().getColor(R.color.Yellow1))
                        .setContentTitle(title)
                        .setContentText(messageBody).setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(messageBody))
                        .setAutoCancel(true)
                        .setColor(getApplicationContext().getResources().getColor(R.color.colorPrimary))
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

                notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());


            } else {
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(messageBody).setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(messageBody))
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);
                notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

            }
        }
    }

    private void handleNotification(String message) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(PUSH_NOTIFICATION);
            pushNotification.putExtra("status", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();

    }

    private void handleDataMessage(Object object) {
//        Log.e(TAG, "push json: " + json.toString());

        try {
            double status1 = ((Double)((LinkedTreeMap)object).get("status"));
            int status = (int)status1;
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(PUSH_NOTIFICATION);
            pushNotification.putExtra("status", status);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
            // app is in background, show the notification in notification tray
            Intent resultIntent = new Intent(getApplicationContext(), DashBoardActivity.class);
            resultIntent.putExtra("status", status);
            if (status == 1) {
                showNotificationMessage(getApplicationContext(), title, "Your payment from mPesa is successful.", String.valueOf(System.currentTimeMillis()), resultIntent);
            } else {
                showNotificationMessage(getApplicationContext(), title, "Your payment from mPesa is failed.", String.valueOf(System.currentTimeMillis()), resultIntent);
            }

        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }
}
