package course.labs.locationlab;

import java.util.Date;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;

public class PlaceRecord {

	private String mFlagUrl;
	private String mCountryName;
	private String mPlaceName;
	private Bitmap mFlagBitmap;
	private Location mLocation;
	private Date mDateVisited;

	public PlaceRecord(String flagUrl, String country, String place) {
		this.mFlagUrl = flagUrl;
		this.mCountryName = country;
		this.mPlaceName = place;
	}

	public PlaceRecord(Location location) {
		mLocation = location;
	}

	public PlaceRecord() {

	}

	public PlaceRecord(Intent intent) {
		this.mFlagUrl = intent.getStringExtra("mFlagUrl");
		this.mCountryName = intent.getStringExtra("mCountryName");
		this.mPlaceName = intent.getStringExtra("mPlaceName");
		this.mFlagBitmap = (Bitmap) intent.getParcelableExtra("mFlagBitmap");
		this.mLocation = (Location) intent.getParcelableExtra("mLocation");
		this.mDateVisited = (Date) intent.getSerializableExtra("mDateVisited");
	}

	public Intent packageToIntent() {
		Intent intent = new Intent();
		intent.putExtra("mFlagUrl", mFlagUrl);
		intent.putExtra("mCountryName", mCountryName);
		intent.putExtra("mPlaceName", mPlaceName);
		intent.putExtra("mFlagBitmap", mFlagBitmap);
		intent.putExtra("mLocation", mLocation);
		intent.putExtra("mDateVisited", mDateVisited);
		return intent;
	}

	public String getFlagUrl() {
		return mFlagUrl;
	}

	public void setFlagUrl(String flagUrl) {
		this.mFlagUrl = flagUrl;
	}

	public String getCountryName() {
		return mCountryName;
	}

	public void setCountryName(String country) {
		this.mCountryName = country;
	}

	public String getPlace() {
		return mPlaceName;
	}

	public void setPlace(String place) {
		this.mPlaceName = place;
	}

	public Bitmap getFlagBitmap() {
		return mFlagBitmap;
	}

	public void setFlagBitmap(Bitmap mFlagBitmap) {
		this.mFlagBitmap = mFlagBitmap;
	}

	public boolean intersects(Location location) {

		double tolerance = 1000;

		return (mLocation.distanceTo(location) <= tolerance);

	}

	public void setLocation(Location location) {
		mLocation = location;
	}

	public Location getLocation() {
		return mLocation;
	}

	public Date getDateVisited() {
		return mDateVisited;
	}

	public void setDateVisited(Date mDateVisited) {
		this.mDateVisited = mDateVisited;
	}

	@Override
	public String toString() {
		return "Place: " + mPlaceName + " Country: " + mCountryName;

	}
}
