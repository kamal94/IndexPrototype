package indexprototype.com.kamal.indexprototype;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Kamal on 12/5/2014.
 */
public class SectionsAdapter extends FragmentPagerAdapter {

    private List mListArrayList = StoriesBank.getSections();

    public SectionsAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        switch(position){
            case(0):
                return StoryListFragment.newInstance(StoriesBank.NEWS);
            case(1):
                return StoryListFragment.newInstance(StoriesBank.FEATURES);
            case(2):
                return StoryListFragment.newInstance(StoriesBank.ARTS);
            case(3):
                return StoryListFragment.newInstance(StoriesBank.OPINIONS);
            case(4):
                return StoryListFragment.newInstance(StoriesBank.SPORTS);
            case(5):
                return StoryListFragment.newInstance(StoriesBank.BUZZKILL);
        }
        return null;
    }

    @Override
    public int getCount() {
        return StoriesBank.NUM_OF_SECTIONS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case(0):
                return "News";
            case(1):
                return "Features";
            case(2):
                return "Arts";
            case(3):
                return "Opinions";
            case(4):
                return "Sports";
            case(5):
                return "BUZZKILL";
            default:
                return "";
        }
    }
}
