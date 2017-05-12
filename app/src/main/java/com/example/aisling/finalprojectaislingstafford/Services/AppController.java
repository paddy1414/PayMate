/*
 * Copyright (c)  2017.  Patrick Norton  All Rights Reserved
 * Email: paddy1414@live.ie
 * Github: https://github.com/paddy1414
 * LinkedIn: www.linkedin.com/in/patricknorton9112
 * Youtube: https://www.youtube.com/channel/UCtYIreGkS7cQa_YwVR-Xqyw
 *
 *
 */

package com.example.aisling.finalprojectaislingstafford.Services;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * The type App controller.
 */
public class AppController extends Application {

    /**
     * The constant TAG.
     */
    public static final String TAG = AppController.class.getSimpleName();
    private static AppController mInstance;
    private RequestQueue mRequestQueue;

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static synchronized AppController getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    /**
     * Gets request queue.
     *
     * @return the request queue
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    /**
     * Add to request queue.
     *
     * @param <T> the type parameter
     * @param req the req
     * @param tag the tag
     */
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    /**
     * Add to request queue.
     *
     * @param <T> the type parameter
     * @param req the req
     */
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    /**
     * Cancel pending requests.
     *
     * @param tag the tag
     */
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}