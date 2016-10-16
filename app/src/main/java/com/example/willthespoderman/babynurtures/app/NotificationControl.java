package com.example.willthespoderman.babynurtures.app;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.StrictMode;
import android.support.v4.app.NotificationCompat;

import com.example.willthespoderman.babynurtures.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Created by osias on 11/28/2015.
 */

public class NotificationControl {
    public static final int MESSAGES_NOTIFICATION = 1001;

    public static void clearNotifications(Activity activity) {
        ((NotificationManager)activity.getSystemService(activity.NOTIFICATION_SERVICE)).cancel(NotificationControl.MESSAGES_NOTIFICATION);
    }
    public static void createInboxStyleNotification(Activity activity, String title,String []messages,Class next){
        Intent i = new Intent(activity,next);
        i.putExtra("messages", messages);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent intent = PendingIntent.getActivity(activity,0,i,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle(title);
        inboxStyle.addLine("Notifications:");
        for(String mes:messages)
            inboxStyle.addLine(mes);
        android.app.Notification notif = new NotificationCompat.Builder(activity)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.notification)
                .setContentIntent(intent)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setStyle(inboxStyle).build();

        ((NotificationManager)activity.getSystemService(activity.NOTIFICATION_SERVICE)).notify(NotificationControl.MESSAGES_NOTIFICATION, notif);

    }

    public static boolean sendNotificationSender(String message, String user){
        StrictMode.ThreadPolicy s = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(s);
        HttpClient client = new DefaultHttpClient();
        message = message.replace(" ","%20");
        user = user.replace(" ","%20");
        String url = AppConfig.URL_SENDNOTIFICATION+"?username="+ user +"&message="+message;
        HttpGet get = new HttpGet(url);
        try {
            HttpResponse response = client.execute(get);
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder stringBuilder = new StringBuilder();
            String str = "";
            while(true){
                str  = br.readLine();
                if(str == null)
                    break;
                stringBuilder.append(str);
            }
            str = stringBuilder.toString();
            return str.contentEquals("OK");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void seenNotifications(String username,String id) {
        StrictMode.ThreadPolicy s = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(s);
        HttpClient client = new DefaultHttpClient();
        String url = AppConfig.URL_SEENNOTIFICATION+"?username="+username+"&"+"id="+id;
        HttpGet get = new HttpGet(url);
        try {
            client.execute(get);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JSONArray getALlNotificationInJSONArray(String user) throws IOException, JSONException {
        StrictMode.ThreadPolicy s = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(s);
        String []messages = {};
        HttpClient client = new DefaultHttpClient();
        String url = AppConfig.ip+"android_login_api/allNotifications.php?user="+user;
        HttpGet get = new HttpGet(url);
        HttpResponse response = client.execute(get);
        BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder stringBuilder = new StringBuilder();
        String str = "";
        while(true){
            str  = br.readLine();
            if(str == null)
                break;
            stringBuilder.append(str);
        }
        str = stringBuilder.toString();
        return new JSONArray(str);
    }
    public static  String[] getAllNotificationsInMessage(String user) throws IOException {
        String []messages = new String[0];
        try {
            JSONArray array = getALlNotificationInJSONArray(user);
            messages = new String[array.length()];

            if (messages.length > 0) {
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    messages[i] = obj.getString("sender") + ": " + obj.getString("message");
                }
                return messages;
            }
            previouseNotifs = messages.length;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  messages;

    }

    public static  HashMap<String,String> getAllNotificationsWithIdInMessage(String user) throws IOException {
        String []messages = new String[0];
        HashMap<String,String> notif = new HashMap<String,String>();
        try {
            JSONArray array = getALlNotificationInJSONArray(user);
            messages = new String[array.length()];

            if (messages.length > 0) {
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    messages[i] = obj.getString("sender") + ": " + obj.getString("message");
                    notif.put(messages[i],obj.getString("id"));
                }
                return notif;
            }
            previouseNotifs = messages.length;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  notif;

    }
    public static int previouseNotifs = 0;
    public static String[] getNotifications(String user) throws IOException {
        StrictMode.ThreadPolicy s = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(s);
        String []messages = {};
        HttpClient client = new DefaultHttpClient();
        String url = AppConfig.ip+"android_login_api/MyNotify.php?user="+user;
        HttpGet get = new HttpGet(url);
        HttpResponse response = client.execute(get);
        BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder stringBuilder = new StringBuilder();
        String str = "";
        while(true){
            str  = br.readLine();
            if(str == null)
                break;
            stringBuilder.append(str);
        }
        str = stringBuilder.toString();
        try {
            JSONArray array = new JSONArray(str);
            messages = new String[array.length()];

                if (messages.length > 0) {
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        messages[i] = obj.getString("sender") + ": " + obj.getString("message");
                    }
                    return messages;
                }
                previouseNotifs = messages.length;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  messages;
    }
    public static void getNotifications(String user,Activity actFrom,Class next) throws IOException {

        String []messages = getNotifications(user);
        if(messages.length>0 && messages.length != previouseNotifs)
            NotificationControl.createInboxStyleNotification(actFrom, "Baby Nurture", messages, next);
        /*
        StrictMode.ThreadPolicy s = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(s);
        HttpClient client = new DefaultHttpClient();
        String url = AppConfig.ip+"android_login_api/MyNotify.php?user="+user;
        HttpGet get = new HttpGet(url);
        HttpResponse response = client.execute(get);
        BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder stringBuilder = new StringBuilder();
        String str = "";
        while(true){
            str  = br.readLine();
            if(str == null)
                break;
            stringBuilder.append(str);
        }
        str = stringBuilder.toString();
        try {
            JSONArray array = new JSONArray(str);
            String []messages = new String[array.length()];
            if(messages.length != previouseNotifs) {
                if (messages.length > 0) {
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        messages[i] = obj.getString("sender") + ": " + obj.getString("message");
                    }

                }
                previouseNotifs = messages.length;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
    }
}
