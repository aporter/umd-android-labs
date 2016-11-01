package course.labs.graphicslab.tests;

import course.labs.graphicslab.BubbleActivity;

import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;

public class BubbleActivityPop extends
		ActivityInstrumentationTestCase2<BubbleActivity> {
	private Solo solo;

	public BubbleActivityPop() {
		super(BubbleActivity.class);
	}

	public void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
	}

	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

	public void testRun() {

		int delay = 2000;

		// Wait for activity: 'course.labs.TouchLab.BubbleActivity'
		solo.waitForActivity(course.labs.graphicslab.BubbleActivity.class,
				delay);

		// Set Still Mode
		solo.clickOnMenuItem("Still Mode");

		// Click on action bar item
		solo.clickOnMenuItem("Add a Bubble");

		solo.sleep(delay);

		// Assert that a bubble was displayed
		assertEquals(
				"Bubble hasn't appeared",
				1,
				solo.getCurrentViews(
						course.labs.graphicslab.BubbleActivity.BubbleView.class)
						.size());

		// Click on action bar item
		solo.clickOnMenuItem("Delete a Bubble");

		solo.sleep(delay);

		// Assert that there are no more bubbles
		assertEquals(
				"The bubble was not popped",
				0,
				solo.getCurrentViews(
						course.labs.graphicslab.BubbleActivity.BubbleView.class)
						.size());

	}
}
