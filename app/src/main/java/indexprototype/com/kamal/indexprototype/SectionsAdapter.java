package indexprototype.com.kamal.indexprototype;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.util.SparseArray;

/**
 *
 * POSSIBLE ERRORS:
 * http://stackoverflow.com/questions/14035090/how-to-get-existing-fragments-when-using-fragmentpageradapter/23843743#23843743
 *
 * Created by Kamal on 12/5/2014.
 */
public class SectionsAdapter extends FragmentPagerAdapter {

    private SparseArray<StoryListFragment> mSparseArray
            = new SparseArray<StoryListFragment>();

    public SectionsAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {

        if(mSparseArray.get(position)!=null)
            return mSparseArray.get(position);
        StoryListFragment storyListFragment = null;
        switch(position){
            case(0):
                storyListFragment = StoryListFragment.newInstance(StoriesBank.NEWS);
                break;
            case(1):
                storyListFragment = StoryListFragment.newInstance(StoriesBank.FEATURES);
                break;
            case(2):
                 storyListFragment=  StoryListFragment.newInstance(StoriesBank.ARTS);
                break;
            case(3):
                 storyListFragment = StoryListFragment.newInstance(StoriesBank.OPINIONS);
                break;
            case(4):
                 storyListFragment = StoryListFragment.newInstance(StoriesBank.SPORTS);
                break;
            case(5):
                 storyListFragment =  StoryListFragment.newInstance(StoriesBank.BUZZKILL);
                break;
        }

        mSparseArray.put(position, storyListFragment);
        return storyListFragment;
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

    /**
     * Returns the fragment at the indicated position of the
     * @param fragmentPosition
     * @return
     */
    public StoryListFragment getFragment(int fragmentPosition){
        StoryListFragment fragment = mSparseArray.get(fragmentPosition);
        return fragment;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        Log.d("SectionsAdapter", "NotifyDataSetChanged");
    }

    public void refreshFragment(int position){
        if(getFragment(position)!=null) {
            getFragment(position).refreshList();
            Log.d("SectionsAdapter", "Refreshed list " + position);
        } else {

            Log.d("SectionsAdapter", " List " + position  + " was found to be null.");
        }
    }



}
