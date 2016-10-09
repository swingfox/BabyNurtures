package com.example.willthespoderman.babynurtures.receiver;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.willthespoderman.babynurtures.activity.Notification;
import com.example.willthespoderman.babynurtures.helper.NotificationsUtils;
import com.example.willthespoderman.babynurtures.utils.Tracker;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePushBroadcastReceiver;
import com.parse.SaveCallback;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dave on 11/14/2015.
 */
public class CustomPushReceiver extends ParsePushBroadcastReceiver {
    private NotificationsUtils notificationUtils;
    private Intent parseIntent;

    public CustomPushReceiver() {
        super();
    }

    @Override
    protected void onPushReceive(Context context, Intent intent) {
        super.onPushReceive(context, intent);
        Tracker.counter++;
        Toast.makeText(context,Integer.toString(Tracker.counter),Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPushDismiss(Context context, Intent intent) {
        super.onPushDismiss(context, intent);
        Toast.makeText(context,"PushDismiss",Toast.LENGTH_LONG).show();

    }

    private void showNotificationMessage(Context context, String title, String message, Intent intent) {

        notificationUtils = new NotificationsUtils(context);
        notificationUtils = new NotificationsUtils(context);

        //intent.putExtras(parseIntent.getExtras());
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Intent notifIntent = new Intent(context, Notification.class);
        notifIntent.putExtras(intent.getExtras());
        notificationUtils.showNotificationMessage(title, message, notifIntent);
    }

    @Override
    protected void onPushOpen(Context context, Intent intent) {
        ParseAnalytics.trackAppOpenedInBackground(intent);
        Intent i = new Intent(context, Notification.class);
        i.putExtras(intent.getExtras());
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(i);
        SharedPreferences sharedPreferences = context.getSharedPreferences("BabyLogin", Context.MODE_PRIVATE);
        String message = "";
        //if(Tracker.counter > 0)
        //    Tracker.counter--;
        // Toast.makeText(context,Integer.toString(Tracker.counter),Toast.LENGTH_LONG).show();
 //       Tracker.switchTextView = true;
        try {
            JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));
        //    Log.e("Message", "Push received: " + json.getString("alert"));

            message = json.getString("alert");
        } catch (JSONException e) {
            Log.e("Error", "Push message json exception: " + e.getMessage());
        }
    /*    super.onPushOpen(context,intent);
        ParseAnalytics.trackAppOpenedInBackground(intent);
        Intent i = new Intent(conte xt, Choose_Child_Task.class);
        i.putExtras(intent.getExtras());
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
*/
        //PARENT
        //if (sharedPreferences.getString("keyType","").equals("0")) {
        //   Intent i = new Intent(context, Notification.class);
        //   i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //    context.startActivity(i);
        //    Toast.makeText(context, "Parent", Toast.LENGTH_LONG).show();
            ParseObject parseObject = ParseObject.create("Activities");
            parseObject.put("parent", sharedPreferences.getString("keyWhoCreated", ""));
            parseObject.put("activity", message);
            parseObject.put("babysitter", sharedPreferences.getString("keyName", ""));
            parseObject.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    Log.e("Response: ", "Successfully created message on Parse");
                }
            });
//        } else {
//            Toast.makeText(context,"BabySitter",Toast.LENGTH_LONG).show();
//            Intent i = new Intent(context, LoginActivity.class);
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            context.startActivity(i);
     /*     Intent i = new Intent(context, Notification.class);
            i.setFlags(Intent.FLAG_ATIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(i);*/
//            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
//            ParseObject parseObject = ParseObject.create("Activities");
//            parseObject.put("parent", sharedPreferences.getString("keyWhoCreated", ""));
//            parseObject.put("activity", message);
//            parseObject.put("babysitter", sharedPreferences.getString("keyName", ""));
//            parseObject.saveInBackground(new SaveCallback() {
//                @Override
//                public void done(ParseException e) {
//                    Log.e("Response: ", "Successfully created message on Parse");
//                }
//            });
    //    }
    }
}