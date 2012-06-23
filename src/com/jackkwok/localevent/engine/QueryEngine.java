package com.jackkwok.localevent.engine;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.location.Location;
import android.util.Log;


import com.jackkwok.localevent.datamodel.EventCollection;
import com.jackkwok.localevent.engine.IQueryEngineListener.EngineError;
import com.jackkwok.localevent.geo.GeoLocationManager;
import com.jackkwok.localevent.geo.NoLocationProviderException;
import com.jackkwok.localevent.xml.EventsParseException;
import com.jackkwok.localevent.xml.EventsXMLParserFactory;
import com.jackkwok.localevent.xml.IEventsXMLParser;
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
public class QueryEngine {

    private static final String EB_APP_KEY = "PASTE YOUR EVENTBRITE KEY HERE";
	private static final String URL_BASE = "https://www.eventbrite.com/xml/event_search?app_key=" + EB_APP_KEY + "&keywords=computer&within=10";//&date=2011-02-07+2011-05-14";
	private static final String LOG_TAG = "QueryEngine";
    private IEventsXMLParser mParser = null;
    private EventCollection mEvents = null; 
	private Context mContext = null;
	
	private static QueryEngine singleton = null;
	
	
	public static QueryEngine getInstance (Context context) {
		if (singleton == null) {
			singleton = new QueryEngine(context);
		}
		return singleton;
	}
	
	private QueryEngine (Context context) {
		super();
		mContext = context;
	}
	
	public EventCollection getEvents() {
		return mEvents;
	}
	
	
	private String getCompleteSearchUrl (double longitude, double latitude, String dateRange) {
		StringBuffer urlBuffer = new StringBuffer();
		urlBuffer.append(URL_BASE);
		urlBuffer.append("&date=");
		urlBuffer.append(dateRange);
		urlBuffer.append("&longitude="); 
		urlBuffer.append(longitude); 
		urlBuffer.append("&latitude=");
		urlBuffer.append(latitude);
		return urlBuffer.toString();
	}
	
	/**
	 * asynchronous network call to search for all local events within 10 miles of a user's location. 
	 * 
	 * @throws NoLocationProviderException 
	 * @throws EventsParseException 
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public void searchNearbyLocalEvents (IQueryEngineListener listener)  {
		GeoLocationManager geo;
		try {
			geo = GeoLocationManager.getInstance(mContext);
		} catch (NoLocationProviderException e2) {
			Log.e(LOG_TAG, e2.toString() + ", msg=" + e2.getMessage());
			listener.notifyProcessingFailure(EngineError.LOCATION_ERR);
			return;
		}
		
		Location currLocation;
		//try {
			currLocation = geo.getMyLocation();
	/*	
		} catch (NoLocationProviderException e1) {
			Log.e(LOG_TAG, e1.toString() + ", msg=" + e1.getMessage());
			listener.notifyProcessingFailure(EngineError.LOCATION_ERR);
			return;
		}*/
		Date now = new Date();
		Date after1week = new Date(new Date().getTime() + 7*24*60*60*1000);
		final String DATE_FORMAT = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		
		String dateRange = sdf.format(now) + "+" + sdf.format(after1week);
		Log.i(LOG_TAG, "query date range: " + dateRange);
		String urlString = getCompleteSearchUrl(currLocation.getLongitude(), currLocation.getLatitude(), dateRange);
		
		mParser = EventsXMLParserFactory.getInstance().getParser();
		
		try {
			mEvents = mParser.parserFromURL(new URL(urlString));
			Log.i(LOG_TAG, "sorting");
			mEvents.sort();
			listener.notifyProcessingSuccess(mEvents);
		} catch (MalformedURLException e) {
			Log.e(LOG_TAG, e.toString() + ", msg=" + e.getMessage());
			listener.notifyProcessingFailure(EngineError.URL_ERR);
		} catch (IOException e) {
			Log.e(LOG_TAG, e.toString() + ", msg=" + e.getMessage());
			listener.notifyProcessingFailure(EngineError.IO_ERR);
		} catch (EventsParseException e) {
			Log.e(LOG_TAG, e.toString() + ", msg=" + e.getMessage());
			listener.notifyProcessingFailure(EngineError.PARSING_ERR);
		}
		
	}
	
}
