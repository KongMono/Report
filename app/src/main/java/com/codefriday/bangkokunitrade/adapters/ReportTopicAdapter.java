package com.codefriday.bangkokunitrade.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.codefriday.bangkokunitrade.R;
import com.codefriday.bangkokunitrade.dataset.ReportToppicEntry;
import com.codefriday.bangkokunitrade.util.Util;

import java.util.ArrayList;
import java.util.List;

public class ReportTopicAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<ReportToppicEntry> items = new ArrayList<ReportToppicEntry>();
	protected Context context;
	ViewHolder holder;
	AQuery aQuery;

	public class ViewHolder {
		public TextView name;
		public int position;
	}

	public ReportTopicAdapter(Context context,ArrayList<ReportToppicEntry> items) {
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
		ReportToppicEntry item = items.get(position);
		View view = convertView;
		if (convertView == null) {
			view = mInflater.inflate(R.layout.item_hospital_spinner, parent,
					false);
			holder = new ViewHolder();
			holder.name = (TextView) view.findViewById(R.id.title);
			holder.name.setTypeface(Util.getBoldFont(context));
			holder.name.setTextSize(25f);
			
			holder.position = position;
			view.setTag(holder);

		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.name.setText(item.getTopic());

		return view;
	}


}
