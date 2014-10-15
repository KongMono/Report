package com.codefriday.bangkokunitrade.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.codefriday.bangkokunitrade.R;
import com.codefriday.bangkokunitrade.dataset.ReportlistEntry;
import com.codefriday.bangkokunitrade.util.Util;

import java.util.ArrayList;
import java.util.List;

public class ReportListAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<ReportlistEntry> items = new ArrayList<ReportlistEntry>();
	protected Context context;
	ViewHolder holder;
	AQuery aQuery;

	public class ViewHolder {
		public TextView title;
		public TextView topic;
		public TextView date;
		public int position;
	}

	public ReportListAdapter(Context context,ArrayList<ReportlistEntry> items) {
		this.mInflater = LayoutInflater.from(context);
		this.items = items;
		this.context = context;
		aQuery = new AQuery(context);
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int pos) {
		return items.get(pos);
	}

	@Override
	public long getItemId(int position) {
		return items.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ReportlistEntry item = items.get(position);
		View view = convertView;
		if (convertView == null) {
			view = mInflater.inflate(R.layout.item_report, parent,
					false);
			holder = new ViewHolder();
			holder.topic = (TextView) view.findViewById(R.id.topic);
			holder.topic.setTypeface(Util.getBoldFont(context));
			holder.topic.setTextSize(25f);
			
			holder.title = (TextView) view.findViewById(R.id.title);
			holder.title.setTypeface(Util.getBoldFont(context));
			holder.title.setTextSize(25f);
			
			holder.date = (TextView) view.findViewById(R.id.date);
			holder.date.setTypeface(Util.getBoldFont(context));
			holder.date.setTextSize(25f);
			
			holder.position = position;
			view.setTag(holder);

		} else {
			holder = (ViewHolder) view.getTag();
		}
		
		holder.title.setText("Subject: " + item.getSubject());
		holder.topic.setText("Topic: " + item.getTopic());
		holder.date.setText(item.getDate());

		return view;
	}


}
