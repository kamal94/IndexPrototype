package indexprototype.com.kamal.indexprototype;

import android.graphics.Color;

/**
 * Created by Kamal on 12/6/2014.
 */
public class CustomSlidingTabColors implements SlidingTabLayout.TabColorizer {
    @Override
    public int getIndicatorColor(int position) {

        switch (position){
            case(0):
                return Color.CYAN;
            case(1):
                return Color.YELLOW;
            case(2):
                return Color.RED;
            case(3):
                return Color.DKGRAY;
            case(4):
                return Color.WHITE;
            case(5):
                return Color.BLACK;
            default:
                return Color.CYAN;
        }
    }

    @Override
    public int getDividerColor(int position) {
        switch (position){
            case(0):
                return Color.GRAY;
            case(1):
                return Color.GRAY;
            case(2):
                return Color.GRAY;
            case(3):
                return Color.GRAY;
            case(4):
                return Color.GRAY;
            case(5):
                return Color.GRAY;
            default:
                return Color.GRAY;
        }
    }
}
