package mikedvorscak.com.stormy;

import android.app.FragmentManager;
import android.content.Context;
import android.util.Log;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

/**
 * Created by mike on 1/25/15.
 */
public class CurrentWeather {
    private String mIcon;
    private long mTime;
    private double mTemperature;
    private double mHumidity;
    private double mPrecipChance;
    private String mTimezone;

    private String mSummary;
    private WeatherService mWeatherService;
    private static final HashMap<String, Integer> mIconMap;
    static {
        mIconMap = new HashMap<String, Integer>();
        mIconMap.put("clear-day", R.drawable.clear_day);
        mIconMap.put("clear-night", R.drawable.clear_night);
        mIconMap.put("rain", R.drawable.rain);
        mIconMap.put("snow", R.drawable.snow);
        mIconMap.put("sleet", R.drawable.sleet);
        mIconMap.put("wind", R.drawable.wind);
        mIconMap.put("fog", R.drawable.fog);
        mIconMap.put("cloudy", R.drawable.cloudy);
        mIconMap.put("partly-cloudy-day", R.drawable.partly_cloudy);
        mIconMap.put("partly-cloudy-night", R.drawable.cloudy_night);
    }

    public static final String TAG = MainActivity.class.getSimpleName();

    public CurrentWeather(Context context, FragmentManager fragmentManager, double lat, double lon) {
        mWeatherService = new WeatherService(context, fragmentManager, lat, lon, this);
    }

    public void whenWeatherIsReady(SimpleCallback callback){
        //Call the weatherservice and pass the callback, the weatherservice will call when done
        mWeatherService.call(callback);
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public int getIconId(){
        return mIconMap.get(mIcon);
    }

    public long getTime() {
        return mTime;
    }

    public String getFormattedTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
        formatter.setTimeZone(TimeZone.getTimeZone(mTimezone));
        Date dateTime = new Date(mTime * 1000);
        String timeString = formatter.format(dateTime);
        return timeString;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public double getTemperature() {
        return mTemperature;
    }

    public void setTemperature(double temperature) {
        mTemperature = temperature;
    }

    public double getHumidity() {
        return mHumidity;
    }

    public void setHumidity(double humidity) {
        mHumidity = humidity;
    }

    public double getPrecipChance() {
        return mPrecipChance;
    }

    public void setPrecipChance(double precipChance) {
        mPrecipChance = precipChance;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public String getTimezone() {
        return mTimezone;
    }

    public void setTimezone(String timezone) {
        mTimezone = timezone;
    }
}
