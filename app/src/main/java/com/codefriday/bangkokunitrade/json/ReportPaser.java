package com.codefriday.bangkokunitrade.json;

import android.util.Log;

import com.codefriday.bangkokunitrade.dataset.ReportlistEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReportPaser {
	private ArrayList<ReportlistEntry> arr;
	private JSONObject jObject;
	ReportlistEntry item;

	public ReportPaser() {
		arr = new ArrayList<ReportlistEntry>();
	}

	public ArrayList<ReportlistEntry> getData(JSONObject jsonObject) {
		arr.clear();

		try {

			jObject = jsonObject;

			JSONObject responseObject = jObject.getJSONObject("content");
			JSONArray itemsjsonArray = responseObject.getJSONArray("item");

			for (int i = 0; i < itemsjsonArray.length(); i++) {
				item = new ReportlistEntry();
				jObject = itemsjsonArray.getJSONObject(i);
				item.setId(jObject.getInt("id"));
				item.setTopic(jObject.getString("topic"));
				item.setSubject(jObject.getString("subject"));
				item.setDate(jObject.getString("date"));
				arr.add(item);
			}
			
		} catch (JSONException e) {
			Log.e("ReportPaser", "Error ", e);
		}
		return arr;
	}

}
