package com.example.willthespoderman.babynurtures.app;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.willthespoderman.babynurtures.utils.LruBitmapCache;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.SaveCallback;

/**
 * Created by WILL THE SPODERMAN on 8/19/2015.
 */
public class AppController extends Application{


    public static final String TAG = AppController.class.getSimpleName();

    private ImageLoader mImageLoader;


    private RequestQueue mRequestQueue;

    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "igRNX7jzc4CFPNNH72b5NKhX06ufgJ7ZoU39Ro3G", "Z7Z8NK4pEavOpiM6zDCHFuhY3igMvqp2fIP6Hbt5");
        ParseInstallation.getCurrentInstallation().saveInBackground();
        ParsePush.subscribeInBackground("BabyNurture", new SaveCallback() {

            @Override
            public void done(ParseException e) {
                Log.e("ParseConfig", "Success save to parse");
            }
        });
        mInstance = this;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }



}
