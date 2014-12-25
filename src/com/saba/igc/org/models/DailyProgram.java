package com.saba.igc.org.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

//{
//    "day": "Tuesday",
//    "englishdate": "'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''December 9",
//    "hijridate": "Safar 16",
//    "time": "",
//    "program": ""
//},
//{
//    "day": "",
//    "englishdate": "",
//    "hijridate": "",
//    "time": " ",
//    "program": "Maghrib Prayer"
//}

@Table(name = "DailyProgram")
public class DailyProgram extends Model {
	@Column(name = "day")
	private String mDay;
	
	@Column(name = "englishDate")
	private String mEnglishDate;
	
	@Column(name = "hijriDate")
	private String mHijriDate;
	
	@Column(name = "time")
	private String mTime;
	
	@Column(name = "program")
	private String mProgram;

	public String getDay() {
		return mDay;
	}

	public void setDay(String mDay) {
		this.mDay = mDay;
	}

	public String getEnglishDate() {
		return mEnglishDate;
	}

	public void setEnglishDate(String mEnglishDate) {
		this.mEnglishDate = mEnglishDate;
	}

	public String getHijriDate() {
		return mHijriDate;
	}

	public void setHijriDate(String mHijriDate) {
		this.mHijriDate = mHijriDate;
	}

	public String getTime() {
		return mTime;
	}

	public void setTime(String mTime) {
		this.mTime = mTime;
	}

	public String getProgram() {
		return mProgram;
	}

	public void setProgram(String mProgram) {
		this.mProgram = mProgram;
	}
	
	public static DailyProgram fromProgramJSON(JSONObject json){
		DailyProgram weeklyProgram = new DailyProgram();
		
		try {
			System.out.println("JSON: " + json.toString());
			weeklyProgram.mDay = json.getString("day");
			weeklyProgram.mEnglishDate = json.getString("englishdate").replace("'","");
			weeklyProgram.mHijriDate = json.getString("hijridate");
			weeklyProgram.mTime = json.getString("time").replace("'","");;
			weeklyProgram.mProgram = json.getString("program");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return weeklyProgram;
	}
	
	public static ArrayList<DailyProgram> fromJSONArray(String programName, JSONArray jsonArray){
		ArrayList<DailyProgram> programs = new ArrayList<DailyProgram>();
		int length = jsonArray.length();
		
		JSONObject programJson = null;
		
		String lastDay = "";
		String lastEnglishDate = "";
		String lastHijriDate = "";
		
		for(int index=0; index<length; index++){
			try{
				programJson = jsonArray.getJSONObject(index);
			} catch(JSONException e){
				e.printStackTrace();
				continue;
			}
			
			DailyProgram weeklyProgram = DailyProgram.fromProgramJSON(programJson);
			if(!weeklyProgram.getDay().isEmpty()){
				lastDay = weeklyProgram.getDay();
			} else { 
				weeklyProgram.setDay(lastDay);
			}
			
			if(!weeklyProgram.getEnglishDate().isEmpty()){
				lastEnglishDate = weeklyProgram.getEnglishDate();
			} else { 
				weeklyProgram.setEnglishDate(lastEnglishDate);
			}
			
			if(!weeklyProgram.getHijriDate().isEmpty()){
				lastHijriDate = weeklyProgram.getHijriDate();
			} else { 
				weeklyProgram.setHijriDate(lastHijriDate);
			}
			
			programs.add(weeklyProgram);
		}
		
		return programs;
	}
	
	public static List<List<DailyProgram>> fromJSONArray1(String programName, JSONArray jsonArray){
		// Weekly Programs represents an array of array of daily programs.
		// List<DailyProgram> - represents programs for one day.
		// List<List<DailyProgram>> represents the programs for the whole week.
		List<List<DailyProgram>> weeklyPrograms = new ArrayList<List<DailyProgram>>();
		int length = jsonArray.length();
		
		JSONObject programJson = null;
		
		String lastDay = "";
		String lastEnglishDate = "";
		String lastHijriDate = "";
		List<DailyProgram> dailyPrograms = null; 
		for(int index=0; index<length; index++){
			try{
				programJson = jsonArray.getJSONObject(index);
			} catch(JSONException e){
				e.printStackTrace();
				continue;
			}
			
			DailyProgram weeklyProgram = DailyProgram.fromProgramJSON(programJson);
			if(!weeklyProgram.getDay().isEmpty()){
				dailyPrograms = new ArrayList<DailyProgram>();
				weeklyPrograms.add(dailyPrograms);
				lastDay = weeklyProgram.getDay();
			} else { 
				weeklyProgram.setDay(lastDay);
			}
			
			if(!weeklyProgram.getEnglishDate().isEmpty()){
				lastEnglishDate = weeklyProgram.getEnglishDate();
			} else { 
				weeklyProgram.setEnglishDate(lastEnglishDate);
			}
			
			if(!weeklyProgram.getHijriDate().isEmpty()){
				lastHijriDate = weeklyProgram.getHijriDate();
			} else { 
				weeklyProgram.setHijriDate(lastHijriDate);
			}
			
			dailyPrograms.add(weeklyProgram);
		}
		
		return weeklyPrograms;
	}

	// Persistence methods.
    public void saveProgram(){
    	this.save();
    }
    
    public static void deletePrograms(){
    	new Delete()
    	.from(DailyProgram.class)
    	.execute();
    }
    
	// Get all times.
    public static List<DailyProgram> getAll() {
        return new Select().from(DailyProgram.class).execute();
    }
    
    public static List<DailyProgram> getPrograms(String day) {
        return new Select()
        .from(DailyProgram.class)
        .where("day = ?", day)
        .execute();
    }
}
