package course.labs.locationlab;

import java.util.Date;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class PlaceViewActivity extends ListActivity implements LocationListener {
	private static final long FIVE_MINS = 5 * 60 * 1000;

	private static String TAG = "Lab-Location";

	public static String PACKAGE_NAME = "course.labs.locationlab.placerecord.PlaceDetail";
	public static String PLACE_DETAIL = "place_detail";
	public static String INTENT_DATA = "course.labs.locationlab.placerecord.IntentData";

	private Location mLastLocationReading;
	private PlaceViewAdapter mAdapter;

	// False if you don't have network access
	public static boolean sHasNetwork = false;

	// default minimum time between new readings
	private long mMinTime = 5000;

	// default minimum distance between old and new readings.
	private float mMinDistance = 1000.0f;

	private LocationManager mLocationManager;

	// A fake location provider used for testing
	private MockLocationProvider mMockLocationProvider;

	private boolean mockLocationOn = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Set up the app's user interface
		// This class is a ListActivity, so it has its own ListView
		// ListView's adapter should be a PlaceViewAdapter

		// TODO - acquire reference to the LocationManager
		mLocationManager = null;
		
		ListView placesListView = getListView();

		// TODO - Set an OnItemClickListener on the ListView to open a detail view when the user
		// clicks on a Place Badge. Pay attention to the helper methods in PlaceRecord.
		placesListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.i(TAG,
						"Entered placesListView.OnItemClickListener.onItemClick()");


			
			
			
			
			}
		});

		// TODO - add a footerView to the ListView
		// You can use footer_view.xml to define the footer

		View footerView = null;

		
		
		// Can remove once footerView is implemented 
		if (null == footerView) {
			return;
		}
		
		// TODO - When the footerView's onClick() method is called, it must
		// issue the
		// following log call
		// Log.i(TAG,"Entered footerView.OnClickListener.onClick()");

		// footerView must respond to user clicks.
		// Must handle 3 cases:
		// 1) The current location is new - download new Place Badge. Issue the
		// following log call:
		// Log.i(TAG,"Starting Place Download");

		// 2) The current location has been seen before - issue Toast message.
		// Issue the following log call:
		// Log.i(TAG,"You already have this location badge");

		// 3) There is no current location - response is up to you. The best
		// solution is to disable the footerView until you have a location.
		// Issue the following log call:
		// Log.i(TAG,"Location data is not available");

		footerView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Log.i(TAG, "Entered footerView.OnClickListener.onClick()");

				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
			}

		});

		placesListView.addFooterView(footerView);
		mAdapter = new PlaceViewAdapter(getApplicationContext());
		setListAdapter(mAdapter);
	}

	@Override
	protected void onResume() {
		super.onResume();

		startMockLocationManager();

		// TODO - Check NETWORK_PROVIDER and GPS_PROVIDER for an existing
		// location reading.
		// Only keep this last reading if it is fresh - less than 5 minutes old.



		
		
		
		
		
		

		// TODO - register to receive location updates from NETWORK_PROVIDER

		
		
		

	}

	@Override
	protected void onPause() {
		shutdownMockLocationManager();

		// TODO - unregister for location updates


		
		super.onPause();
	}

	// Callback method used by PlaceDownloaderTask
	public void addNewPlace(PlaceRecord place) {
		Log.i(TAG, "Entered addNewPlace()");
		
		if (place.getCountryName() == null || place.getCountryName().isEmpty()) {
			showToast(getString(R.string.no_country_string));
		} else if (!mAdapter.intersects(place.getLocation())) {
			place.setDateVisited(new Date());
			mAdapter.add(place);
			mAdapter.notifyDataSetChanged();
		} else {
			showToast(getString(R.string.duplicate_location_string));
		}
	}

	@Override
	public void onLocationChanged(Location currentLocation) {

		// TODO - Handle location updates
		// Cases to consider
		// 1) If there is no last location, keep the current location.
		// 2) If the current location is older than the last location, ignore
		// the current location
		// 3) If the current location is newer than the last locations, keep the
		// current location.



		
		
		

	}

	@Override
	public void onProviderDisabled(String provider) {
		// not implemented
	}

	@Override
	public void onProviderEnabled(String provider) {
		// not implemented
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// not implemented
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.delete_badges:
			mAdapter.removeAllViews();
			return true;
		case R.id.place_one:
			mMockLocationProvider.pushLocation(37.422, -122.084);
			return true;
		case R.id.place_no_country:
			mMockLocationProvider.pushLocation(0, 0);
			return true;
		case R.id.place_two:
			mMockLocationProvider.pushLocation(38.996667, -76.9275);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void showToast(final String toast) {
				Toast.makeText(PlaceViewActivity.this, toast, Toast.LENGTH_LONG)
						.show();
	}

	private void shutdownMockLocationManager() {
		if (mockLocationOn) {
			mMockLocationProvider.shutdown();
			mockLocationOn = false;
		}
	}

	private void startMockLocationManager() {
		if (!mockLocationOn) {
			mMockLocationProvider = new MockLocationProvider(
					LocationManager.NETWORK_PROVIDER, this);
			mockLocationOn = true;
		}
	}
}
