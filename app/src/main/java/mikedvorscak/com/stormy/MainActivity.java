package mikedvorscak.com.stormy;

import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends ActionBarActivity {
    private CurrentWeather mCurrentWeather;

    @InjectView(R.id.timeLabel) TextView mTimeLabel;
    @InjectView(R.id.temperatureLabel) TextView mTemperatureLabel;
    @InjectView(R.id.humidityValue) TextView mHumidityValue;
    @InjectView(R.id.precipValue) TextView mPrecipValue;
    @InjectView(R.id.summaryLabel) TextView mSummaryLabel;
    @InjectView(R.id.iconImageView) ImageView mIconImageView;
    @InjectView(R.id.refreshImageView) ImageView mRefreshImageView;
    @InjectView(R.id.progressBar) ProgressBar mProgressBar;

    public static final String TAG = MainActivity.class.getSimpleName();

    SimpleCallback updateViewWithWeather = new SimpleCallback() {
        @Override
        public void call() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTemperatureLabel.setText(mCurrentWeather.getTemperature() + "");
                    mTimeLabel.setText(mCurrentWeather.getFormattedTime() + " it will be");
                    mHumidityValue.setText(mCurrentWeather.getHumidity() + "");
                    mPrecipValue.setText(mCurrentWeather.getPrecipChance() + "%");
                    mSummaryLabel.setText(mCurrentWeather.getSummary());

                    Drawable drawable = getResources().getDrawable(mCurrentWeather.getIconId());
                    mIconImageView.setImageDrawable(drawable);
                    toggleProgressBar(false);
                }
            });
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);
        double lat = 41.9747760;
        double lon = -87.6553560;
        mCurrentWeather = new CurrentWeather(this, getFragmentManager(),  lat, lon);
        toggleProgressBar(true);
        mCurrentWeather.whenWeatherIsReady(updateViewWithWeather);

        mRefreshImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSummaryLabel.setText("Getting current weather...");
                toggleProgressBar(true);
                mCurrentWeather.whenWeatherIsReady(updateViewWithWeather);
            }
        });
    }

    private void toggleProgressBar(boolean on) {
        if(on){
            mProgressBar.setVisibility(View.VISIBLE);
            mRefreshImageView.setVisibility(View.INVISIBLE);
        } else {
            mProgressBar.setVisibility(View.INVISIBLE);
            mRefreshImageView.setVisibility(View.VISIBLE);
        }
    }
}
