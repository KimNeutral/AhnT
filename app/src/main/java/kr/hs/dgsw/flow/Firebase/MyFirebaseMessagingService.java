package kr.hs.dgsw.flow.Firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.List;
import java.util.Map;

import io.realm.Realm;
import kr.hs.dgsw.flow.Activity.MainActivity;
import kr.hs.dgsw.flow.Helper.SharedPreferencesHelper;
import kr.hs.dgsw.flow.Model.GoOut;
import kr.hs.dgsw.flow.Model.SleepOut;
import kr.hs.dgsw.flow.R;

import static android.content.ContentValues.TAG;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage);
        }
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     */
    private void sendNotification(RemoteMessage remoteMessage) {
        Map<String, String> data = remoteMessage.getData();
        String type = data.get("type");
        int requestID = (int) System.currentTimeMillis();
        Intent intent = new Intent(this, MainActivity.class);

        switch(type) {
            case "go_out":
                intent.putExtra("fragment", "GoOut");
                allowGoOut(Integer.parseInt(data.get("idx").toString()));
                break;
            case "sleep_out":
                intent.putExtra("fragment", "SleepOut");
                allowSleepOut(Integer.parseInt(data.get("idx").toString()));
                break;
            case "notice":
                intent.putExtra("fragment", "Notice");
                intent.putExtra("notice_idx", data.get("idx").toString());
                break;
            default:
        }

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestID /* Request code */, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);


        RemoteMessage.Notification noti = remoteMessage.getNotification();
//        String channelId = getString(R.string.default_notification_channel_id);
        String channelId = "default_channel_id";
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_stat_ic_notification)
                        .setContentTitle(noti.getTitle())
                        .setContentText(noti.getBody())
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "AhnT Message",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        increaseCount();
        showBadge();
    }

    private void allowGoOut(int index) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        GoOut goOut = realm.where(GoOut.class).equalTo("idx", index).findFirst();
        goOut.setAccept(1);
        realm.commitTransaction();
    }

    private void allowSleepOut(int index) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        SleepOut sleepOut = realm.where(SleepOut.class).equalTo("idx", index).findFirst();
        sleepOut.setAccept(1);
        realm.commitTransaction();
    }

    private void showBadge() {
        Intent badgeIntent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        badgeIntent.putExtra("badge_count", getCount());
        badgeIntent.putExtra("badge_count_package_name", getPackageName());
        badgeIntent.putExtra("badge_count_class_name", getLauncherClassName());
        sendBroadcast(badgeIntent);
    }

    private int getCount() {
        String str = SharedPreferencesHelper.getPreference("badge_count");
        if(!TextUtils.isEmpty(str))
            return Integer.parseInt(str);
        else {
            SharedPreferencesHelper.setPreference("badge_count", "0");
            return 0;
        }
    }

    private void increaseCount() {
        int ct = getCount();
        SharedPreferencesHelper.setPreference("badge_count", (ct - 1) + "");
    }

    private void decreaseCount() {
        int ct = getCount();
        SharedPreferencesHelper.setPreference("badge_count", (ct - 1) + "");
    }

    private String getLauncherClassName() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        PackageManager pm = getApplicationContext().getPackageManager();
        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resolveInfos) {
            String pkgName = resolveInfo.activityInfo.applicationInfo.packageName;
            if (pkgName.equalsIgnoreCase(getPackageName())) {
                return resolveInfo.activityInfo.name;
            }
        }
        return null;
    }
}
