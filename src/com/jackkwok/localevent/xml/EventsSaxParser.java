package com.jackkwok.localevent.xml;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import android.util.Log;

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
public class EventsSaxParser implements IEventsXMLParser {

	private String LOG_TAG = "EventsSaxParser";
	
	public EventCollection parserFromURL(URL url) throws IOException,
			EventsParseException {


		EventCollection events = new EventCollection();
		try {
			/* original working version */
			SAXParserFactory parserFactory = SAXParserFactory.newInstance();
			Log.i(LOG_TAG, "EdisSaxParser.parseFromUrl :" + url);
			SAXParser parser = parserFactory.newSAXParser();
			
			/* trying...
			String parserClass = "org.apache.xerces.parsers.SAXParser";
			Parser parser = ParserFactory.makeParser(parserClass);
			*/

			
			EventsHandler handler = new EventsHandler(events);

			InputStream in = url.openStream();
			Date startDate = new Date();
			Log.i(LOG_TAG, "parser start timestamp: " + startDate);
			parser.parse( in, handler );
			Date finishDate = new Date();
			long interval = (finishDate.getTime() - startDate.getTime()) / 1000;
			Log.i(LOG_TAG, "EDIS XMLParser TOOK THIS MUCH TIME (sec) TO FINISH : " + interval);
		} catch (UnknownHostException ue) {
			throw new IOException(ue.toString());
		} catch (IOException io) {
			throw new IOException(io.toString());
		} catch (SAXParseException saxe) {
			throw new EventsParseException(saxe.toString());
		} catch (ParserConfigurationException pce) {
			throw new EventsParseException(pce.toString());
		} catch (SAXException sae) {
			throw new EventsParseException(sae.toString());
		} 
		return events;
	}
	
}
