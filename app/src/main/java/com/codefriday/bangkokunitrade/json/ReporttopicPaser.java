package com.codefriday.bangkokunitrade.json;

import android.util.Log;

import com.codefriday.bangkokunitrade.dataset.ReportToppicEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReporttopicPaser {
	private ArrayList<ReportToppicEntry> arr;
	private JSONObject jObject;
	ReportToppicEntry item;

	public ReporttopicPaser() {
		arr = new ArrayList<ReportToppicEntry>();
	}

	public ArrayList<ReportToppicEntry> getData(JSONObject jsonObject) {
		arr.clear();

		try {

			jObject = jsonObject;

			JSONObject responseObject = jObject.getJSONObject("content");
			JSONArray itemsjsonArray = responseObject.getJSONArray("item");

			for (int i = 0; i < itemsjsonArray.length(); i++) {
				item = new ReportToppicEntry();
				jObject = itemsjsonArray.getJSONObject(i);
				item.setId(jObject.getInt("id"));
				item.setTopic(jObject.getString("topic"));
				arr.add(item);
			}
			
			arr.add(item);
		} catch (JSONException e) {
			Log.e("ReporttopicPaser", "Error ", e);
		}
		return arr;
	}

}
