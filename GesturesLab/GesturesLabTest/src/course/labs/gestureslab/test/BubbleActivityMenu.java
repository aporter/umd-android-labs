package course.labs.gestureslab.test;

import android.test.ActivityInstrumentationTestCase2;
import android.view.WindowManager;

import com.robotium.solo.Solo;

import course.labs.gestureslab.BubbleActivity;


public class BubbleActivityMenu extends ActivityInstrumentationTestCase2<BubbleActivity> {
  	private Solo solo;
  	
  	public BubbleActivityMenu() {
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
		
		solo.clickOnActionBarItem(course.labs.gestureslab.R.id.menu_still_mode);

		solo.sleep(delay);
		
		//Gesture starting top left to open menu
		solo.drag(0,300,300,350, 10);
		
		solo.sleep(delay);
		
		//checking if menu opened by clicking on a menu item 
		//without opening the menu.
		assertTrue("Menu did not appear", solo.waitForText("Random Speed Mode"));
		
	}
}
