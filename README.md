local-event-android
===================

To run this app, you will need 2 keys:

Your Google Maps API key.  Apply at
    https://developers.google.com/android/maps-api-signup
Paste your key inside res/layout/event_map.xml :
android:apiKey="PASTE YOUR MAP KEY HERE"

Your Eventbrite API key. Apply at
    https://www.eventbrite.com/api/key
Paste your key inside QueryEngine.java :
private static final String EB_APP_KEY = "PASTE YOUR EVENTBRITE KEY HERE";

Compile and Run.