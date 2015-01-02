package com.saba.igc.org.fragments;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.saba.igc.org.R;
import com.saba.igc.org.application.SabaClient;
import com.saba.igc.org.extras.LocationBasedCityName;
import com.saba.igc.org.extras.SimpleLocation;
import com.saba.igc.org.listeners.SabaServerResponseListener;
import com.saba.igc.org.models.PrayerTimes;

/**
 * @author Syed Aftab Naqvi
 * @create December, 2014
 * @version 1.0
 */
public class PrayerTimesFragment extends Fragment implements SabaServerResponseListener{
	
	private final String TAG = "PrayerTimesFragment";
	private TextView mTvCityName;
	private TextView mTvImsaac;
	private TextView mTvFajar;
	private TextView mTvSunrise;
	private TextView mTvZohar;
	private TextView mTvSunset;
	private TextView mTvMaghrib;
	private TextView mTvMidnight;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getLocationBasedAddress();
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState);
		
		View view = inflater.inflate(R.layout.fragment_prayer_times, container, false);		
		setupUI(view);
		
		return view;
	}

	private void setupUI(View view) {
		mTvCityName = (TextView) view.findViewById(R.id.tvCityName);
		TextView tvTodayDate = (TextView) view.findViewById(R.id.tvTodayDate);
		
		mTvImsaac = (TextView) view.findViewById(R.id.tvImsaacValue);
		mTvFajar = (TextView) view.findViewById(R.id.tvFajarValue);
		mTvSunrise = (TextView) view.findViewById(R.id.tvSunriseValue);
		mTvZohar = (TextView) view.findViewById(R.id.tvZoharValue);
		mTvSunset = (TextView) view.findViewById(R.id.tvSunsetValue);
		mTvMaghrib = (TextView) view.findViewById(R.id.tvMaghribValue);
		mTvMidnight = (TextView) view.findViewById(R.id.tvMidnightValue);
		
		Calendar calendar = Calendar.getInstance();
	    int day = calendar.get(Calendar.DAY_OF_MONTH); //Day of the month :)
	    int month = calendar.get(Calendar.MONTH); //number of seconds
	    
		List<PrayerTimes> items = PrayerTimes.getTodayPrayerTimes("San Jose", ""+month+"-"+day);
		
		if(mTvCityName != null)
			mTvCityName.setText("San Jose");

		if(tvTodayDate != null){
			DateFormat dateInstance = SimpleDateFormat.getDateInstance(DateFormat.FULL);
			tvTodayDate.setText(dateInstance.format(Calendar.getInstance().getTime()));
			System.out.println(dateInstance.format(Calendar.getInstance().getTime()));
		}
		
		if(mTvImsaac != null)
			mTvImsaac.setText(items.get(0).getImsaak());
		
		if(mTvFajar != null)
			mTvFajar.setText(items.get(0).getFajar());
		
		if(mTvSunrise != null)
			mTvSunrise.setText(items.get(0).getSunrise());
		
		if(mTvZohar != null)
			mTvZohar.setText(items.get(0).getZohar());
		
		if(mTvSunset != null)
			mTvSunset.setText(items.get(0).getSunset());
		
		if(mTvMaghrib != null)
			mTvMaghrib.setText(items.get(0).getMaghrib());
		
		if(mTvMidnight != null)
			mTvMidnight.setText(items.get(0).getMidnight());
	}

	private void getLocationBasedAddress() {
		SimpleLocation location = new SimpleLocation(getActivity());
		
		double latitude = 0.0d;
		double longitude = 0.0d;
		if (location.hasLocationEnabled()) {
		    // ask the device to update the location data
		    location.beginUpdates();
			
		    // get the location from the device (alternative A)
		    latitude = location.getLatitude();
		    longitude = location.getLongitude();

		    // get the location from the device (alternative B)
		    // SimpleLocation.Point coords = location.getPosition();

		    // ask the device to stop location updates to save battery
		    location.endUpdates();
		}
		else {
		    // ask the user to enable location access
			SimpleLocation.openSettings(getActivity());
		}
		
		SabaClient client = SabaClient.getInstance(getActivity());
		client.getPrayTimes(longitude, latitude, this);
		
		// initiate the request to get the city name based of current latitude and longitude. 
		LocationBasedCityName locationBasedCityName = new LocationBasedCityName();
		locationBasedCityName.getAddressFromLocation(latitude, longitude,
                getActivity(), new GeocoderHandler());	
	}
	@Override
	public void processJsonObject(String programName, JSONObject response) {
		// parse pray times and populate in UI.
	}

	@Override
	public void processJsonObject(String programName, JSONArray response) {
		// we are not expecting any data here in this case....
	}
	
	private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String cityName;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    cityName = bundle.getString("cityname");
                    break;
                default:
                	Log.d(TAG, "city name not found.");
                	cityName = null;
            }
            mTvCityName.setText(cityName);
        }
    }
}
