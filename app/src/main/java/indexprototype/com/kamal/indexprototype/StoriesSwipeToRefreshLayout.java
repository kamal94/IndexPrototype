package indexprototype.com.kamal.indexprototype;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * A subclass of SwipeRefreshLayout {@link android.support.v4.widget.SwipeRefreshLayout}
 * which provides support for scrolling upwards in a Fragment ListView.
 * Created by Kamal on 12/18/2014.
 */
public class StoriesSwipeToRefreshLayout extends SwipeRefreshLayout{
    private SectionsAdapter mSectionsAdapter;
    private ViewPager mViewPager;

    public StoriesSwipeToRefreshLayout(Context context, SectionsAdapter sectionsAdapter, ViewPager viewPager) {
        super(context);
        mSectionsAdapter = sectionsAdapter;
        mViewPager = viewPager;
    }

    public StoriesSwipeToRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StoriesSwipeToRefreshLayout(Context context){
        super(context);
    }

    /**
     * Mandatory use of this method in order for the class to work.
     * Sets the Adapter and the Viewpager that are used to make the list
     * of stories scrollable.
     * @param sectionsAdapter   The sectionAdapter hosting the fragments of the sections.
     * @param viewPager     The ViewPager used to switch between sections.
     */
    public void setAdapterAndViewPager( SectionsAdapter sectionsAdapter, ViewPager viewPager){
        mSectionsAdapter = sectionsAdapter;
        mViewPager = viewPager;
    }

    @Override
    public boolean canChildScrollUp() {
        final RecyclerView storyListView = mSectionsAdapter.getFragment(mViewPager.getCurrentItem()).getRecyclerView();
        if (android.os.Build.VERSION.SDK_INT >= 14) {
            // For ICS and above we can call canScrollVertically() to determine this
            return storyListView.canScrollVertically(-1);
        } else {
            // Pre-ICS we need to manually check the first visible item and the child view's top
            // value
            return storyListView.getChildCount() > 0 &&
                    (((LinearLayoutManager)storyListView.getLayoutManager())
                            .findFirstVisibleItemPosition()>0 ||
                            storyListView.getChildAt(0).getTop() < storyListView.getPaddingTop());
        }
    }
}
