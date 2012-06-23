package com.jackkwok.localevent.ui;

import com.jackkwok.localevent.R;
import com.jackkwok.localevent.datamodel.EventCollection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
 *
 * Copyright (c) 2012 Jack Kwok http://www.mobileideafactory.com

 Permission is hereby granted, free of charge, to any person obtaining
 a copy of this software and associated documentation files (the
 "Software"), to deal in the Software without restriction, including
 without limitation the rights to use, copy, modify, merge, publish,
 distribute, sublicense, and/or sell copies of the Software, and to
 permit persons to whom the Software is furnished to do so, subject to
 the following conditions:

 The above copyright notice and this permission notice shall be
 included in all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

 * @author Jack Kwok
 *
 */
public class EventsListAdapter extends BaseAdapter {
	
	//private Context mContext = null;
	private EventCollection mCollection = null;
	private LayoutInflater mInflater = null;
	
	public EventsListAdapter (Context context, EventCollection collection) {
		//mContext = context;
		mInflater = LayoutInflater.from(context);

		mCollection = collection;
	}

	public int getCount() {
		return mCollection.getCount();
	}

	public Object getItem(int position) {
		return mCollection.getEntryAt(position);
	}

	public long getItemId(int position) {
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.event_listitem , parent, false);
			holder = new ViewHolder();
			holder.titleText = (TextView) convertView.findViewById(R.id.titleText);
			holder.distanceText = (TextView) convertView.findViewById(R.id.distanceText);
			holder.dateText = (TextView) convertView.findViewById(R.id.startDateText);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.titleText.setText(mCollection.getEntryAt(position).getTitle());
		holder.distanceText.setText(mCollection.getEntryAt(position).getDistance() + " M");
		holder.dateText.setText(mCollection.getEntryAt(position).getStartDate());
		return convertView;
	}
	
	static class ViewHolder {
		TextView titleText;
		TextView distanceText;
		TextView dateText;
	}
}
