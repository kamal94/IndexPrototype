package indexprototype.com.kamal.indexprototype;

import android.graphics.Color;

/**A class that customizes the colors on the sliding tabs of the Index sections.
 *
 * Created by Kamal on 12/6/2014.
 * @author Kamal Kamalaldin
 * @version 12/06/2014
 */
public class CustomSlidingTabColors implements SlidingTabLayout.TabColorizer {


    /**
     * @param position The position of the tab.
     * @return return the color of the indicator used when {@code position} is selected.
     */
    @Override
    public int getIndicatorColor(int position) {

        switch (position){
            case(0):
                return Color.WHITE;
            case(1):
                return Color.WHITE;
            case(2):
                return Color.WHITE;
            case(3):
                return Color.WHITE;
            case(4):
                return Color.WHITE;
            case(5):
                return Color.WHITE;
            default:
                return Color.WHITE;
        }
    }

    /**
     * Sets the color of the dividers between each tab.
     * @param position  The position of the divider
     * @return  return the color of the divider drawn to the right of {@code position}.
     */
    @Override
    public int getDividerColor(int position) {
        switch (position){
            case(0):
                return 0xFF5722;
            case(1):
                return 0xFF5722;
            case(2):
                return 0xFF5722;
            case(3):
                return 0xFF5722;
            case(4):
                return 0xFF5722;
            case(5):
                return 0xFF5722;
            default:
                return 0xFF5722;
        }
    }
}
