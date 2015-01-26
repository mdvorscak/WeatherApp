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

/**
 * Created by mike on 1/25/15.
 */
public class CurrentWeather {
    private final FragmentManager mFragmentManager;
    private String mIcon;
    private long mTime;
    private double mTemperature;
    private double mHumidity;
    private double mPrecipChance;
    private String mSummary;
    private Call mCall;
    private static final String API_KEY = "ffb886b97ade03498187e7197debddc6";

    public static final String TAG = MainActivity.class.getSimpleName();
    private Context mContext;

    public CurrentWeather(Context context, FragmentManager fragmentManager, double lat, double lon) {
        String forecastUrl = "https://api.forecast.io/forecast/" + API_KEY + "/" + lat + "," + lon;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(forecastUrl)
                .build();
        mCall = client.newCall(request);
        mContext = context;
        mFragmentManager = fragmentManager;
    }

    public void callWeatherService(){
        if(Utils.isNetworkAvailable(mContext)) {
            mCall.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(Response response) throws IOException {
                    try {
                        if (response.isSuccessful()) {
                            String jsonData = response.body().string();
                            getCurrentDetails(jsonData);
                        } else {
                            Utils.alertUserAboutError(mFragmentManager, mContext.getString(R.string.error_message));
                        }
                    } catch (JSONException e){
                        Log.e(TAG, "Exception caught: ", e);
                    }
                    catch (Exception e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }
                }
            });
        } else {
            Utils.alertUserAboutError(mFragmentManager, mContext.getString(R.string.network_unavailable_message));
        }
    }

    private void getCurrentDetails(String jsonData) throws JSONException{
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        JSONObject currently = forecast.getJSONObject("currently");

        Log.v(TAG, "JSON timezone: " + timezone);
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public long getTime() {
        return mTime;
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
}
