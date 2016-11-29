package course.labs.contentproviderlab.tests;

import android.database.Cursor;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.robotium.solo.Solo;

import course.labs.contentproviderlab.PlaceViewActivity;
import course.labs.contentproviderlab.provider.PlaceBadgesContract;

public class TestAccessContentProvider extends
		ActivityInstrumentationTestCase2<PlaceViewActivity> {
	private Solo solo;
	private Cursor mCursor;

	public TestAccessContentProvider() {
		super(PlaceViewActivity.class);
	}

	public void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
		PlaceViewActivity.sHasNetwork = false;
	}

	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

	public void testRun() {

	mCursor = getActivity().getContentResolver().query(
			PlaceBadgesContract.CONTENT_URI, 
			    null,                       
			    null,                   
			    null,                  
			    null);   
	
	assertNotNull(mCursor);
		
	}
}
