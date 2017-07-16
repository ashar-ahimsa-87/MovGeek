package com.example.ainozen.movgeek.app;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by ainozen on 7/16/17.
 */

public class ConstantVariable {
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    public static final String URL_PHOTO = "http://image.tmdb.org/t/p/w185";

    public static final String MAX_RATING = "10";

    public static final String INTENT_OBJ_MOVIE = "MOVIE";

    public static boolean isNetworkAvailable(Context ctx) {
        ConnectivityManager connMgr = (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected() ||
                connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected()){
            return true;
        }

        return false;
    }
}
