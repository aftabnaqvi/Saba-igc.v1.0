package com.saba.igc.org.activities;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.saba.igc.org.R;
import com.saba.igc.org.adapters.ProgramsArrayAdapter;
import com.saba.igc.org.adapters.WeeklyProgramsArrayAdapter;
import com.saba.igc.org.models.DailyProgram;

public class DailyProgramDetailActivity extends Activity {
	ListView mLvDailyPrograms;
	List<DailyProgram> mDailyPrograms;
	WeeklyProgramsArrayAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_daily_programs_detail);
		mLvDailyPrograms = (ListView)findViewById(R.id.lvDailyPrograms);
		
		String day = getIntent().getStringExtra("day");
		if(day != null)
			mDailyPrograms = DailyProgram.getPrograms(day);
		
		// removing program at 0 index which has the date info. Don't want to display here...
		if(mDailyPrograms != null && mDailyPrograms.size()>0)
			mDailyPrograms.remove(0);
		
		mAdapter = new WeeklyProgramsArrayAdapter(this, mDailyPrograms);
		mLvDailyPrograms.setAdapter(mAdapter);
	}
}
