package com.jackkwok.localevent.xml;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.jackkwok.localevent.datamodel.Event;
import com.jackkwok.localevent.datamodel.EventCollection;
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
public class EventsHandler extends DefaultHandler {
	private String LOG_TAG = "EventsHandler";
	private StringBuffer buf = new StringBuffer();
	private EventCollection mCollection = null;
	private Event mCurrentEvent = null;
	
	public EventsHandler (EventCollection eventCollection) {
		super();
		mCollection = eventCollection;
	}

	public void startElement( String namespaceURI, String localname, String qname, 
            Attributes attributes ) {

        qname = qname.replaceFirst( ".*:", "" );
        
        if ( qname.equals( "events" ) ) { 
    		Log.i(LOG_TAG, "found events tag");
        }
        
        if ( qname.equals( "event" ) ) { 
        	Log.i(LOG_TAG, "found event tag");
        	mCurrentEvent = new Event(); 
        	mCollection.add( mCurrentEvent ); 
        }

        
        buf.setLength(0);
        
	}
	
    public void characters( char[] ch, int start, int length ) {
        buf.append( ch, start, length );
    }

    public void endElement( String uri, String localname, String qname ) {
    	String value = buf.toString();

    	// remove any namespace ids
    	qname = qname.replaceFirst( ".*:", "" );
    	if (mCurrentEvent != null) {
    		if ( qname.equals( "description" ) ) { 
    			Log.i(LOG_TAG, " description:" + value);
    			if (mCurrentEvent.getHtmlDescription() == null) {
    				mCurrentEvent.setHtmlDescription(value);
    			}
    		} else if ( qname.equals( "title" ) ) { 
    			Log.i(LOG_TAG, " event title:" + value);
    			mCurrentEvent.setTitle(value);
    		} else if ( qname.equals( "latitude" ) ) {
    			try {
    				mCurrentEvent.setLatitude(Float.valueOf(value.trim()));
    			}
    		    catch (NumberFormatException nfe)
    		    {
    		      Log.e(LOG_TAG, "latitude value caused NumberFormatException: " + nfe.getMessage());
    		    }
    		} else if ( qname.equals( "longitude" ) ) {
    			try {
    				mCurrentEvent.setLongitude(Float.valueOf(value.trim()));
    			}
    		    catch (NumberFormatException nfe)
    		    {
    		      Log.e(LOG_TAG, "longitude value caused NumberFormatException: " + nfe.getMessage());
    		    }
    		} else if ( qname.equals( "url" ) ) { 
    			mCurrentEvent.setUrl(value);
    		} else if ( qname.equals( "start_date" ) ) { 
    			if (mCurrentEvent.getStartDate() == null) {
    				mCurrentEvent.setStartDate(value);
    			}
    		} else if ( qname.equals( "distance" ) ) { 
    			String timmedString = value.trim();
    			try {
    				String distanceString = timmedString.substring(0, timmedString.length()-1);
    				Log.i(LOG_TAG, "event distance from user is:" + distanceString);
    				mCurrentEvent.setDistance(Float.valueOf(distanceString));
    			} catch (NumberFormatException nfe)
    		    {
    		      Log.e(LOG_TAG, "distance value caused NumberFormatException: " + nfe.getMessage());
    		    }
    		}
    	}
    }
}
