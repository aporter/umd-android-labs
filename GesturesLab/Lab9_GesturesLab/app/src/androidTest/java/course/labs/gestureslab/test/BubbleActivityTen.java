package course.labs.gestureslab.test;

import android.test.ActivityInstrumentationTestCase2;
import android.view.WindowManager;

import com.robotium.solo.Solo;

import course.labs.gestureslab.BubbleActivity;

public class BubbleActivityTen extends
		ActivityInstrumentationTestCase2<BubbleActivity> {
	private Solo solo;

	public BubbleActivityTen() {
		super(BubbleActivity.class);
	}

	public void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
		getInstrumentation().runOnMainSync(new Runnable() {
			@Override
			public void run() {
				getActivity().getWindow().addFlags(
						WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
			}
		});
	}

	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

	public void testRun() {
		
		int delay = 2000; 
		
		// Wait for activity: 'course.labs.TouchLab.BubbleActivity'
		solo.waitForActivity(course.labs.gestureslab.BubbleActivity.class, 2000);

		// Set Still Mode
		solo.clickOnActionBarItem(course.labs.gestureslab.R.id.menu_still_mode);
		
		solo.sleep(delay);
		
		//Gesture starting in top right to add ten bubbles
		solo.drag(300,0,200,300,10);
		
		solo.sleep(delay);
	
		// Give misbehaving bubbles a chance to move off screen
		// Assert that there are two bubbles on the screen
		assertEquals(
				"There should be ten bubbles on the screen",
				10,
				solo.getCurrentViews(
						course.labs.gestureslab.BubbleActivity.BubbleView.class)
						.size());

	}
}
