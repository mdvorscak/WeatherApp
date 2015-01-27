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
 * Created by mike on 1/26/15.
 */
public class WeatherService {
    public static final String TAG = MainActivity.class.getSimpleName();

    private final FragmentManager mFragmentManager;
    private Call mCall;
    private Context mContext;
    private CurrentWeather mCurrentWeather;
    private static final String API_KEY = "ffb886b97ade03498187e7197debddc6";

    public WeatherService(Context context, FragmentManager fragmentManager, double lat, double lon, CurrentWeather currentWeather){
        String forecastUrl = "https://api.forecast.io/forecast/" + API_KEY + "/" + lat + "," + lon;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(forecastUrl)
                .build();
        mCall = client.newCall(request);
        mContext = context;
        mFragmentManager = fragmentManager;
        mCurrentWeather = currentWeather;
    }

    public void call(final SimpleCallback simpleCallback){
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
                            //Set the weather object
                            getCurrentDetails(jsonData);
                        } else {
                            Utils.alertUserAboutError(mFragmentManager, mContext.getString(R.string.error_message));
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }
                    //Let everyone know we are donezo
                    simpleCallback.call();
                }
            });
        } else {
            Utils.alertUserAboutError(mFragmentManager, mContext.getString(R.string.network_unavailable_message));
        }
    }

    private void getCurrentDetails(String jsonData) throws JSONException{
        JSONObject forecast = new JSONObject(jsonData);
        JSONObject currently = forecast.getJSONObject("currently");
        //Set the weather
        mCurrentWeather.setHumidity(currently.getDouble("humidity"));
        mCurrentWeather.setTime(currently.getLong("time"));
        mCurrentWeather.setIcon(currently.getString("icon"));
        mCurrentWeather.setPrecipChance(currently.getDouble("precipProbability"));
        mCurrentWeather.setSummary(currently.getString("summary"));
        mCurrentWeather.setTemperature(currently.getDouble("temperature"));
        mCurrentWeather.setTimezone(forecast.getString("timezone"));
    }

}
