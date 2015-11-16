package course.labs.contentproviderlab;

import android.graphics.Bitmap;
import android.location.Location;

public class PlaceRecord {

	// URL for retrieving the flag image
	private String mFlagUrl;

	// path to flag image in external memory
	private String mFlagBitmapPath;

	private String mCountryName;
	private String mPlaceName;
	private Bitmap mFlagBitmap;
	private Location mLocation;

	public PlaceRecord(String flagUrl, String flagBitmapPath,
			String countryName, String placeName, Location location) {
		mFlagUrl = flagUrl;
		mFlagBitmapPath = flagBitmapPath;
		mCountryName = countryName;
		mPlaceName = placeName;
		mLocation = location;
	}

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

	public Location getLocation() {
		return mLocation;
	}

	public void setLocation(Location location) {
		mLocation = location;
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

	boolean intersects(Location location) {
		double tolerance = 1000;
		return (mLocation.distanceTo(location) <= tolerance);
	}

	@Override
	public String toString() {
		return "Place: " + mPlaceName + " Country: " + mCountryName;

	}

	public String getFlagBitmapPath() {
		return mFlagBitmapPath;
	}

	public void setFlagBitmapPath(String flagBitmapPath) {
		this.mFlagBitmapPath = flagBitmapPath;
	}

}