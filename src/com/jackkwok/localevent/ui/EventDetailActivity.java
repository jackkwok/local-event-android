package com.jackkwok.localevent.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
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
public class EventDetailActivity extends Activity {

	private static final String LOG_TAG = "EventDetailActivity";
	public static final String KEY_EVENT_HTML = "com.jackkwok.localevent.event.html";
    
	//private TextView 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(LOG_TAG, "EventDetailActivity onCreate called");
		super.onCreate(savedInstanceState);
		 WebView webview = new WebView(this);
		 setContentView(webview);
		 Bundle extras = getIntent().getExtras();
		 String html = extras.getString(KEY_EVENT_HTML);
         if (html != null) {
        	 Log.i(LOG_TAG, html);
        	 webview.loadData(html, "text/html", "utf-8");
         }
		 
	}

	@Override
	protected void onResume() {
		Log.i(LOG_TAG, "EventDetailActivity onResume called");
		super.onResume();
		
	}

	
	
	
}
