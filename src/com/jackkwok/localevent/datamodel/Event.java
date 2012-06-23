package com.jackkwok.localevent.datamodel;
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
public class Event implements Comparable<Event> {
	private String mHtmlDescription = null;
	private String mTitle = null;
	private String mUrl = null;
	private float mLatitude = 0.0f;
	private float mLongitude = 0.0f;
	private String mStartDate = null;
	private float mDistance = 0.0f;  //in miles
	//private String = null;
	
	public Event (){
		super();
	}
	
	public void setHtmlDescription(String htmlDescription) {
		this.mHtmlDescription = htmlDescription;
	}
	public String getHtmlDescription() {
		return mHtmlDescription;
	}
	public void setTitle(String title) {
		this.mTitle = title;
	}
	public String getTitle() {
		return mTitle;
	}
	public void setUrl(String url) {
		this.mUrl = url;
	}
	public String getUrl() {
		return mUrl;
	}

	public void setStartDate(String startDate) {
		this.mStartDate = startDate;
	}
	public String getStartDate() {
		return mStartDate;
	}

	public void setLatitude(float latitude) {
		this.mLatitude = latitude;
	}

	public float getLatitude() {
		return mLatitude;
	}

	public void setLongitude(float longitude) {
		this.mLongitude = longitude;
	}

	public float getLongitude() {
		return mLongitude;
	}

	public void setDistance(float distance) {
		this.mDistance = distance;
	}

	public float getDistance() {
		return mDistance;
	}

	public int compareTo(Event anotherEvent) {
		if (this.mDistance < anotherEvent.getDistance()) {
			return -1;
		} else if (this.mDistance < anotherEvent.getDistance()) {
			return 1;
		} else {
			return 0;
		}
	}

	

}
