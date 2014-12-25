package indexprototype.com.kamal.indexprototype;

import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;

import indexprototype.com.kamal.indexprototype.OnlineStoriesReader.DataFetcher;
import indexprototype.com.kamal.indexprototype.StorageManager.StorageManager;


public class MainActivity extends ActionBarActivity implements SwipeRefreshLayout.OnRefreshListener{

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView;
    private FragmentManager mFragmentManager;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private SectionsAdapter mSectionsAdapter;
    private ViewPager mViewPager;
    private StoriesSwipeToRefreshLayout swipeRefreshLayout;
    private SlidingTabLayout mSlidingTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("MainActivity", "onCreate called.");
        //set the orange background that goes behind the status bar
        getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primary_color)));
        //gets the supportFragmentManager
        if (mFragmentManager == null){
            mFragmentManager = getSupportFragmentManager();
        }
        //if activity is saved, then get back the homeFragment
        if(savedInstanceState!=null) {

        } else {
//            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//            if (networkInfo != null && networkInfo.isConnected()) {
//                for (int i = 0; i < 6; i++) {
//                    new DownloadData().execute(i);
//                }
//                for (int i = 0; i < 6; i++) {
//                    new DownloadStoryImages().execute(i);
//                }
//            } else {
//                Toast.makeText(this, "No network detected. Please connect to the internet and try again.", Toast.LENGTH_SHORT).show();
//            }
            StorageManager loadingManager = new StorageManager(getApplicationContext());
            loadingManager.loadStories();
        }


        if(mSectionsAdapter==null) {
            mSectionsAdapter = new SectionsAdapter(mFragmentManager);
        }


        mViewPager = (ViewPager) findViewById(R.id.main_activity_view_pager);
        mViewPager.setAdapter(mSectionsAdapter);
        //This listener implements an important override that prevents the
        //SwipeRefreshLayout(see below) from stealing the vertical operations
        //of the fragment inside the ViewPager. It simply disables the SwipeRefreshLayout
        //once the user decides to do anything but swipe-down/move-up in the screen.
        mViewPager.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                swipeRefreshLayout.setEnabled(false);
                if(event.getAction()==MotionEvent.ACTION_UP){
                    swipeRefreshLayout.setEnabled(true);}
                return false;
            }
        });

        //Sets the Swipe down to refresh layout
        swipeRefreshLayout = (StoriesSwipeToRefreshLayout) findViewById(R.id.main_activity_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setAdapterAndViewPager(mSectionsAdapter, mViewPager);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.primary_color));
        swipeRefreshLayout.requestDisallowInterceptTouchEvent(true);

        //Sets the Sliding tab layout
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.main_activity_sliding_tabs);
        mSlidingTabLayout.setCustomTabView(R.layout.scroll_tab, R.id.scroll_tab_text_view);
        mSlidingTabLayout.setViewPager(mViewPager);
        mSlidingTabLayout.setCustomTabColorizer(new CustomSlidingTabColors());
        mSlidingTabLayout.setBackgroundColor(getResources().getColor(R.color.primary_color));



        //Sets the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar!=null){setSupportActionBar(toolbar);}
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primary_color)));

        //Sets the drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_atctivity_drawer_layout);
        mDrawerListView = (ListView) findViewById(R.id.main_activity_drawer_listview);
        mDrawerListView.setAdapter(new ArrayAdapter<String>(this, R.layout.navigation_drawer_list_view_item, getResources().getStringArray(R.array.navigation_drawer_string_array)){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View choiceView = getLayoutInflater().inflate(R.layout.navigation_drawer_list_view_item, parent, false);
                ImageView choiceIcon = (ImageView) choiceView.findViewById(R.id.navigation_drawer_list_view_item_icon);
                TextView choiceText = (TextView) choiceView.findViewById(R.id.navigation_drawer_list_view_item_text_view);
                switch (position){
                    case(0):
                        choiceIcon.setImageDrawable(getResources().getDrawable(R.drawable.index_story_default));
                        choiceText.setText(this.getItem(position));
                        break;
                    case(1):
                        choiceIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_email));
                        choiceText.setText(this.getItem(position));
                        break;
                }
                return choiceView;
            }
        });
        mDrawerListView.setOnItemClickListener(new NavigationDrawerListener());


        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.accessibility_drawer_open, R.string.accessibility_drawer_close){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


    }


    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        refreshStories();
        swipeRefreshLayout.setRefreshing(false);
    }


    public void refreshStories(){
        mSectionsAdapter.notifyDataSetChanged();
        mSectionsAdapter.refreshFragment(mViewPager.getCurrentItem());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml..
        switch(item.getItemId()){
            case R.id.action_bar_refresh_feed:
                actionBarRefreshClicked();
                break;
            default:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        mFragmentManager.popBackStack();
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MainActivity", "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MainActivity", "onDestroy() Called");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("MainActivity", "onResume() called");
    }

    /**
     * Requests the HomeFragment to refresh its view and the stories within it.
     */
    public void requestDataRefresh(){
        refreshStories();
    }

    /**
     * A STUB method to indicate the clicking of the refresh button/icon
     * on the main activity
     */
    private void actionBarRefreshClicked(){
        Toast.makeText(this, "Refresh Button Click\nTry swiping down for fancy animation!", Toast.LENGTH_SHORT).show();
        requestDataRefresh();
    }


    @Override
    protected void onPause() {
        super.onPause();
        StorageManager storageManager = new StorageManager(getApplicationContext());
        storageManager.saveStoriesToMemory(StoriesBank.getStories());
        Log.d("MainActivity", "onPause() called");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("MainActivity", "onSaveInstanceState() called");
    }


    /**
     * A simple OnClickListener implementation for the list of items in the Navigation Drawer.
     */
    public class NavigationDrawerListener implements AdapterView.OnItemClickListener {


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch(position){
                case(0):
                    homeChosen();
                    Log.d("MainActivity", "HomeFragment fragment inflation called");
                    break;
                case(1):
                    contactUsChosen();
                    Log.d("MainActivity", "SendFeedbackFragment fragment inflation called");
                    break;
            }
        }


        public void homeChosen(){
            mFragmentManager.popBackStack();
            mDrawerLayout.closeDrawers();
        }

        public void contactUsChosen(){

            if(mFragmentManager.findFragmentByTag(SendFeedbackFragment.FRAGMENT_TAG)==null){
                android.support.v4.app.Fragment sendFeedbackFragment = new SendFeedbackFragment();
                mFragmentManager.beginTransaction()
                        .replace(R.id.activity_main_fragment_container, sendFeedbackFragment, SendFeedbackFragment.FRAGMENT_TAG)
                        .addToBackStack(null)
                        .commit();
                Log.d("MainActivity", "New SendFeedbackFragment fragment committed");
            } else {
                mFragmentManager.beginTransaction()
                        .replace(R.id.activity_main_fragment_container, mFragmentManager.findFragmentByTag(SendFeedbackFragment.FRAGMENT_TAG), SendFeedbackFragment.FRAGMENT_TAG)
                        .addToBackStack(null)
                        .commit();
                Log.d("MainActivity", "A previous SendFeedbackFragment fragment committed");
            }

            mDrawerLayout.closeDrawers();
        }
    }
    /**
     * A class that runs the download for the stories from the internet on a separate thread.
     */
    private class DownloadData extends AsyncTask<Integer, Integer, Boolean> {

        private int runningThread;
        @Override
        protected Boolean doInBackground(Integer... params) {
            runningThread = params[0];
            return DataFetcher.run(params[0]);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                requestDataRefresh();
                Log.d("MainActivity", "running thread: " + runningThread);
            }
        }
    }


    /**
     * A class that downloads images of stories.
     */
    private class DownloadStoryImages extends AsyncTask<Integer, Integer, Boolean>{

        private int runningThread;
        @Override
        protected Boolean doInBackground(Integer... params) {

            runningThread = params[0];
            for(Story story: StoriesBank.getStories()){
                if(!story.getImageURL().equals(Story.DEFAULT_IMAGE_URL))
                    try {
                        story.setImageBitmap(Picasso.with(getApplicationContext()).load(story.getImageURL()).get());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            Log.d("MainActivity", "Image download for list "+ runningThread + " is complete");
            requestDataRefresh();
        }
    }
}
