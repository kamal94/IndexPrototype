package indexprototype.com.kamal.indexprototype;

import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements ContactUs.OnFragmentInteractionListener, HomeFragment.OnFragmentInteractionListener{

    private SectionsAdapter mSectionsAdapter;
    private ViewPager mViewPager;
    private SlidingTabLayout mSlidingTabLayout;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView;
    private boolean downloadingData;
    private FragmentManager mFragmentManager;
    private Bundle mFragmentManagementBundle;
    private android.support.v4.app.Fragment homeFragment;
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("MainActivity", "onCreate called.");

        mFragmentManager = getSupportFragmentManager();

        //Sets the beginning fragment
        homeFragment = (android.support.v4.app.Fragment) new HomeFragment();
        mFragmentManager.beginTransaction()
                .add(R.id.fragment_container, homeFragment, HomeFragment.TAG)
                .commit();


        //Sets the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar!=null){setSupportActionBar(toolbar);}
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primary_color)));
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(getResources().getColor(R.color.primary_color_dark));
        }

        //Sets the drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_atctivity_drawer_layout);
        mDrawerListView = (ListView) findViewById(R.id.main_activity_drawer_listview);
        mDrawerListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.navigation_drawer_string_array)));
        mDrawerListView.setOnItemClickListener(new NavigationDrawerListener());


        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.accessibility_drawer_open, R.string.accessibility_drawer_close){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                setTitle("The Index");
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                setTitle("Options");
            }

        };
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


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
        int id = item.getItemId();
        switch(item.getItemId()){
            case R.id.action_bar_settings:
                actionBarSettingsClicked();
                break;
            case R.id.action_bar_refresh_feed:
                actionBarRefreshClicked();
                break;
            case R.id.action_bar_remove_feed:
                actionBarRemoveClicked();
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

    /**
     * A STUB method to indicate the clicking of the settings button/icon
     * on the main activity
     */
    private void actionBarSettingsClicked(){
        Toast.makeText(getApplicationContext(),"Settings Clicked", Toast.LENGTH_SHORT).show();
    }

    /**
     * A STUB method to indicate the clicking of the refresh button/icon
     * on the main activity
     */
    private void actionBarRefreshClicked(){

        mSectionsAdapter.notifyDataSetChanged();
        mSectionsAdapter.refreshFragment(mViewPager.getCurrentItem());
    }

    /**
     * A method that deletes all entries in the feed
     */
    private void actionBarRemoveClicked(){
        StoriesBank.clear();
        mSectionsAdapter.notifyDataSetChanged();
        mSectionsAdapter.refreshFragment(mViewPager.getCurrentItem());
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }



    /**
     * Created by Kamal on 12/13/2014.
     */
    public class NavigationDrawerListener implements AdapterView.OnItemClickListener {


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch(position){
                case(0):
                    homeChosen();
                    Log.d("MainActivity", "Home fragment inflation called");
                    break;
                case(1):
                    contactUsChoosen();
                    Log.d("MainActivity", "ContactUS fragment inflation called");
                    break;
            }
        }


        public void homeChosen(){
//            mFragmentManager.beginTransaction()
//                    .replace(R.id.fragment_container, homeFragment)
//                    .commit();
            mFragmentManager.popBackStack();
            getSupportActionBar().setTitle("The Index");
            mDrawerLayout.closeDrawers();
        }

        public void contactUsChoosen(){

            FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.Fragment fragment = new ContactUs();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment, ContactUs.FRAGMENT_TAG)
                    .addToBackStack(null)
                    .commit();
            getSupportActionBar().setTitle("Contact Us");
            mDrawerLayout.closeDrawers();
            Log.d("MainActivity", "Contact us fragment commited");
        }
    }
}






/*

STORY ADAPTER IF NEEDED
    private class StoryArrayAdapter extends ArrayAdapter<Story> {

        public StoryArrayAdapter(Context context, int resource, List<Story> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView ==null){
                convertView = View.inflate(getApplicationContext(),R.layout.story_list_view, null);
            }
            Story story = getItem(position);

            ImageView imageView  = (ImageView) convertView.findViewById(R.id.story_list_view_image_view);
            imageView.setImageResource(R.drawable.image_clip);

            TextView largeTextView = (TextView) convertView.findViewById(R.id.story_list_view_large_text_view);
            largeTextView.setText(story.getTitle());

            TextView smallTextView = (TextView) convertView.findViewById(R.id.story_list_view_small_text_view);
            smallTextView.setText(story.getContent());

            return convertView;
        }

 */


//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.e("MainActivity", "OnPause called.");
//    }
//
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    @Override
//    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
//        if(Build.VERSION.SDK_INT> Build.VERSION_CODES.FROYO)
//            super.onSaveInstanceState(outState, outPersistentState);
//
//        Log.e("MainActivity", "onSaveInstanceState called.");
//    }
