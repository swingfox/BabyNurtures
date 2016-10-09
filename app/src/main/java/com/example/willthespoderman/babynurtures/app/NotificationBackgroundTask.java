package com.example.willthespoderman.babynurtures.app;

import android.app.Activity;
import android.os.AsyncTask;

import java.io.IOException;

/**
 * Created by osias on 11/29/2015.
 */
public class NotificationBackgroundTask extends AsyncTask {
    String username;
    Activity current;
    Class next;

    public NotificationBackgroundTask(String username, Activity current, Class next) {
        this.username = username;
        this.current = current;
        this.next = next;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        while (!this.isCancelled()) {
            try {
                NotificationControl.getNotifications(params[0].toString(), current, next);
                Thread.sleep(1000);
            } catch (IOException e) {
                return  null;
            } catch (InterruptedException e) {
                return  null;
            }
        }
        return  null;
    }

}
