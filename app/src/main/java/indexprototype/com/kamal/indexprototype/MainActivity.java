package indexprototype.com.kamal.indexprototype;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import indexprototype.com.kamal.indexprototype.StorageManager.StorageManager;


public class MainActivity extends ActionBarActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView;
    private FragmentManager mFragmentManager;
    private HomeFragment homeFragment;
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("MainActivity", "onCreate called.");

        mFragmentManager = getSupportFragmentManager();
        getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primary_color)));

        //Sets the beginning fragment
        homeFragment =  new HomeFragment();
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
//                setTitle("The Index");
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
//                setTitle("Options");
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
//        int id = item.getItemId();

        //Settings and Remove button are not implemented at the moment.
        switch(item.getItemId()){
//            case R.id.action_bar_settings:
//                actionBarSettingsClicked();
//                break;
            case R.id.action_bar_refresh_feed:
                actionBarRefreshClicked();
                break;
//            case R.id.action_bar_remove_feed:
//                actionBarRemoveClicked();
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
        Toast.makeText(this, "Refresh Button Click\nTry swiping down for fancy animation!", Toast.LENGTH_SHORT).show();
        homeFragment.refreshStories();
    }

    /**
     * A method that deletes all entries in the feed
     */
    private void actionBarRemoveClicked(){
        Toast.makeText(this, "This button does nothing for now :(", Toast.LENGTH_SHORT).show();
        homeFragment.refreshStories();
    }

    @Override
    protected void onPause() {
        super.onPause();
        StorageManager storageManager = new StorageManager(getApplicationContext());
        storageManager.saveStoriesToMemory(StoriesBank.getStories());
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
            mFragmentManager.popBackStack();
            Log.d("MainActivity", "BackStack popped");
            mDrawerLayout.closeDrawers();
        }

        public void contactUsChoosen(){
            FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.Fragment fragment = new SendFeedbackFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment, SendFeedbackFragment.FRAGMENT_TAG)
                    .addToBackStack(null)
                    .commit();
            mDrawerLayout.closeDrawers();
            Log.d("MainActivity", "Contact us fragment committed");
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
                convertView = View.inflate(getApplicationContext(),R.layout.story_list_view_item, null);
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
