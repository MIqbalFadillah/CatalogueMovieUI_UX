package com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.AlertNofication;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.DetailHome;
import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.R;
import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.model.ItemsListMovie;

import java.util.Calendar;
import java.util.List;

public class UpcomingReminder extends BroadcastReceiver {
    private static int notifId = 1000;
    public final static int NOTIFICATION_ID = 100;
    public static String NOTIFICATION_CHANEL_ID = "101";
    public static CharSequence NOTIFICATION_CHANEL = "Movies channel";
    static final String EXTRA_MOVIE = "EXTRA_MOVIE";

    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra("title_movie");
        int id = intent.getIntExtra("id",0);
        long id_movie = intent.getLongExtra("id_movie", 0);
        String image = intent.getStringExtra("poster_movie");
        String date = intent.getStringExtra("date_movie");
        String rate = intent.getStringExtra("rating_movie");
        String description = intent.getStringExtra("overview_movie");
        ItemsListMovie movieResult = new ItemsListMovie();
//        String desc =String.valueOf(String.format(context.getString(R.string.notify_daily), movieTitle));
//        sendNotification(context, context.getString(context.getString(R.string.app_name), desc, id, movieResult));
    }

    private void sendNotification(Context context, String title, String desc, int id, ItemsListMovie movieResult){

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(
                Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, DetailHome.class);
        intent.putExtra(EXTRA_MOVIE, movieResult);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri uriTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.baseline_video_library_black_24dp)
                .setContentTitle(title)
                .setContentText(desc)
                .setContentIntent(pendingIntent)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setSound(uriTone);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANEL_ID,
                    NOTIFICATION_CHANEL, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.YELLOW);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            builder.setChannelId(NOTIFICATION_CHANEL_ID);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(id, builder.build());
    }


    public static void setAlarm(Context context, List<ItemsListMovie> movieResults) {
        int delay = 0;

        for (ItemsListMovie movie : movieResults) {
            cancelAlarm(context);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, DetailHome.class);
            intent.putExtra("title_movie", movie.getTitle_movie());
            intent.putExtra("movieid", movie.getId_movie());
            intent.putExtra("poster_movie", movie.getImage_movie());
            intent.putExtra("date_movie", movie.getRealese_movie());
            intent.putExtra("rating_movie", movie.getRate_movie());
            intent.putExtra("overview_movie", movie.getDescription_movie());
            intent.putExtra("id", notifId);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 8);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                alarmManager.setInexactRepeating(
                        AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis() + delay,
                        AlarmManager.INTERVAL_DAY,
                        pendingIntent
                );
            } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis() + delay, pendingIntent);
            }
            notifId += 1;
            delay += 3000;
            Log.v("title", movie.getTitle_movie());
        }
    }
    public static void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(getPendingIntent(context));
    }
    private static PendingIntent getPendingIntent(Context context) {
        Intent intent = new Intent(context, UpcomingReminder.class);
        return PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
