package mikedvorscak.com.stormy;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;


public class MainActivity extends ActionBarActivity {
    private CurrentWeather mCurrentWeather;

    public static final String TAG = MainActivity.class.getSimpleName();

    SimpleCallback updateViewWithWeather = new SimpleCallback() {
        @Override
        public void call() {
            //TODO: Implement me please!
            mCurrentWeather.getIcon();
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        double lat = 37.8267;
        double lon = -122.423;
        mCurrentWeather = new CurrentWeather(this, getFragmentManager(),  lat, lon);
        mCurrentWeather.whenWeatherIsReady(updateViewWithWeather);
    }
}
