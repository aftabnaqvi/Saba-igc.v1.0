package com.saba.igc.org.fragments;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.saba.igc.org.R;
import com.saba.igc.org.adapters.PrayTimeAdapter;
import com.saba.igc.org.application.SabaClient;
import com.saba.igc.org.extras.LocationBasedCityName;
import com.saba.igc.org.extras.LocationService;
import com.saba.igc.org.listeners.SabaServerResponseListener;
import com.saba.igc.org.models.PrayTime;

/**
 * @author Syed Aftab Naqvi
 * @create December, 2014
 * @version 1.0
 */
public class PrayerTimesFragment extends Fragment implements SabaServerResponseListener{
	
	private final String TAG = "PrayerTimesFragment";
	private TextView 			mTvCityName;
	private LocationService 	mLocationService;
	private List<PrayTime> 		mPrayTimes;
	private ListView	 		mLvPrayTimes;
	private PrayTimeAdapter 	mAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mLocationService = new LocationService(
                getActivity());
		getLocationBasedAddress();
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		View view = inflater.inflate(R.layout.fragment_pray_times, container, false);		
		setupUI(view);
		
		return view;
	}

	private void setupUI(View view) {
		mTvCityName = (TextView) view.findViewById(R.id.tvCityName);
		TextView tvTodayDate = (TextView) view.findViewById(R.id.tvEnglishDate);
		mLvPrayTimes = (ListView) view.findViewById(R.id.lvPrayTimes);
		
		if(tvTodayDate != null){
			DateFormat dateInstance = SimpleDateFormat.getDateInstance(DateFormat.FULL);
			tvTodayDate.setText(dateInstance.format(Calendar.getInstance().getTime()));
		}
	}

	private void getLocationBasedAddress() {
		double latitude = 0.0d;
		double longitude = 0.0d;
		Location gpsLocation = mLocationService
                .getLocation(LocationManager.GPS_PROVIDER);
        if (gpsLocation != null) {
            latitude = gpsLocation.getLatitude();
            longitude = gpsLocation.getLongitude();
            String result = "Latitude: " + gpsLocation.getLatitude() +
                    " Longitude: " + gpsLocation.getLongitude();
            Log.d(TAG, result);
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
		//{"Fajr":"05:59","Isha":"18:18","Asr":"14:43","Dhuhr":"12:11","Sunset":"17:01","Sunrise":"07:21","Maghrib":"17:19","Imsaak":"05:48"}
		if(response == null){
			Log.d(TAG, "pray times - json object is null");
			return;
		}
		
		mPrayTimes = PrayTime.fromJSON(response);
		mAdapter = new PrayTimeAdapter(getActivity(), mPrayTimes); 
		mLvPrayTimes.setAdapter(mAdapter);
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
