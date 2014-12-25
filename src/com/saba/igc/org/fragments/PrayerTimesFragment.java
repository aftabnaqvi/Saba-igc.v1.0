package com.saba.igc.org.fragments;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.saba.igc.org.R;
import com.saba.igc.org.models.PrayerTimes;

/**
 * @author Syed Aftab Naqvi
 * @create December, 2014
 * @version 1.0
 */
public class PrayerTimesFragment extends Fragment{
	
	GridView gvPrayerTimes;
	TextView tvDate;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState);
		
		View view = inflater.inflate(R.layout.fragment_prayer_times, container, false);		
		TextView tvCityName = (TextView) view.findViewById(R.id.tvCityName);
		TextView tvTodayDate = (TextView) view.findViewById(R.id.tvTodayDate);
		
		TextView tvImsaac = (TextView) view.findViewById(R.id.tvImsaacValue);
		TextView tvFajar = (TextView) view.findViewById(R.id.tvFajarValue);
		TextView tvSunrise = (TextView) view.findViewById(R.id.tvSunriseValue);
		TextView tvZohar = (TextView) view.findViewById(R.id.tvZoharValue);
		TextView tvSunset = (TextView) view.findViewById(R.id.tvSunsetValue);
		TextView tvMaghrib = (TextView) view.findViewById(R.id.tvMaghribValue);
		TextView tvMidnight = (TextView) view.findViewById(R.id.tvMidnightValue);
		
		Calendar calendar = Calendar.getInstance();
	    int day = calendar.get(Calendar.DAY_OF_MONTH); //Day of the month :)
	    int month = calendar.get(Calendar.MONTH); //number of seconds
	    
		List<PrayerTimes> items = PrayerTimes.getTodayPrayerTimes("San Jose", ""+month+"-"+day);
		
		if(tvCityName != null)
			tvCityName.setText("San Jose");
//		DateFormat dateInstance = SimpleDateFormat.getDateInstance(DateFormat.FULL);
//		tvTodayDate.setText(dateInstance.format(Calendar.getInstance().getTime()));
		if(tvTodayDate != null){
			DateFormat dateInstance = SimpleDateFormat.getDateInstance(DateFormat.FULL);
			tvTodayDate.setText(dateInstance.format(Calendar.getInstance().getTime()));
			System.out.println(dateInstance.format(Calendar.getInstance().getTime()));
		}
		
		if(tvImsaac != null)
			tvImsaac.setText(items.get(0).getImsaak());
		
		if(tvFajar != null)
			tvFajar.setText(items.get(0).getFajar());
		
		if(tvSunrise != null)
			tvSunrise.setText(items.get(0).getSunrise());
		
		if(tvZohar != null)
			tvZohar.setText(items.get(0).getZohar());
		
		if(tvSunset != null)
			tvSunset.setText(items.get(0).getSunset());
		
		if(tvMaghrib != null)
			tvMaghrib.setText(items.get(0).getMaghrib());
		
		if(tvMidnight != null)
			tvMidnight.setText(items.get(0).getMidnight());
		
		System.out.println("records: " + items.size());
		System.out.println("Date: " + items.get(0).getDate());
		System.out.println("Imsaak: " + items.get(0).getImsaak());
		System.out.println("Fajar: " + items.get(0).getFajar());
		System.out.println("Sunrise: " + items.get(0).getSunrise());
		System.out.println("Zohar: " + items.get(0).getZohar());
		System.out.println("Maghrib: " + items.get(0).getMaghrib());
		System.out.println("Midnight: " + items.get(0).getMidnight());
		
		return view;
	}
}
