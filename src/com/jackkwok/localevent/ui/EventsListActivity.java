package com.jackkwok.localevent.ui;

import com.jackkwok.localevent.R;
import com.jackkwok.localevent.datamodel.EventCollection;
import com.jackkwok.localevent.engine.IQueryEngineListener;
import com.jackkwok.localevent.engine.QueryEngine;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
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
public class EventsListActivity extends Activity implements OnItemClickListener, IQueryEngineListener {
	
	private ListView mEventListView = null;
	private TextView mTitle = null;
	private EventsListAdapter mAdaptor = null;
	private String mLastErrorMessage = null;
	private QueryEngine mEngine = null;
	
	public static final int DIALOG_ERROR_ID = 0;
	private static final String LOG_TAG = "EventsListActivity";
	public static final int MENU_ITEM_MAP = 0;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        mEventListView = (ListView) findViewById(android.R.id.list);
        mEventListView.setOnItemClickListener(this);
        mTitle = (TextView) findViewById(R.id.title);
        mTitle.setText(R.string.events_list_title);
        
        mEngine = QueryEngine.getInstance(this.getApplicationContext());
        mEngine.searchNearbyLocalEvents(this);
    }

	@Override
	protected void onResume() {
		super.onResume();
	}
    
	@Override
	protected void onPause() {
		super.onPause();
	}

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		//show Event Detail Activity
		Intent intent = new Intent();
		intent.setClass(this, EventDetailActivity.class);
		intent.putExtra(EventDetailActivity.KEY_EVENT_HTML, mEngine.getEvents().getEntryAt(position).getHtmlDescription());
		startActivity(intent);
	}

	public void notifyProcessingSuccess(EventCollection collection) {
		Log.i(LOG_TAG, "notifyProcessingSuccess");
		
		mAdaptor = new EventsListAdapter(this, collection);
		mEventListView.setAdapter(mAdaptor);
	}

	
	protected Dialog onCreateDialog(int id) {
	    Dialog dialog;
	    switch(id) {
	    case DIALOG_ERROR_ID:
	    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    	builder.setMessage(mLastErrorMessage)
	    	       .setCancelable(false)
	    	       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	    	           public void onClick(DialogInterface dialog, int id) {
	    	                EventsListActivity.this.finish();
	    	           }
	    	       })
	    	       ;
	    	dialog = builder.create();
	    	return dialog;
	    	
	    }
	    return null;
	}
	
	public void notifyProcessingFailure(EngineError error) {
		switch (error) {
			case IO_ERR:
				mLastErrorMessage = this.getText(R.string.error_io).toString();
				break;
			case URL_ERR:
				mLastErrorMessage = this.getText(R.string.error_url).toString();
				break;
			case PARSING_ERR:
				mLastErrorMessage = this.getText(R.string.error_parse).toString();
				break;
			case LOCATION_ERR:
				mLastErrorMessage = this.getText(R.string.error_location).toString();
				break;
			case UNKNOWN_ERR:
			default:
				mLastErrorMessage = this.getText(R.string.error_unknown).toString();
				break;
		}
		showDialog(DIALOG_ERROR_ID);
		
	}

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    super.onCreateOptionsMenu(menu);
    
	    menu.add(0, MENU_ITEM_MAP, 1, "Map It");
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
	    switch (item.getItemId()) {
	    case MENU_ITEM_MAP:
			Intent i = new Intent();
			i.setClass(this, EventsMapActivity.class);
			startActivity(i);
	    	return true;
	    }
	    return false;
	}
	
}