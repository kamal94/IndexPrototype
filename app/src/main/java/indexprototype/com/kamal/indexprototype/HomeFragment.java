package indexprototype.com.kamal.indexprototype;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;

import indexprototype.com.kamal.indexprototype.OnlineStoriesReader.DataFetcher;
import indexprototype.com.kamal.indexprototype.StorageManager.LoadingManager;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final String TAG = "HOME_FRAGMENT";
    public static final String BUNDLE_KEY = "HOME_FRAGMENT_BUNDLE_KEY";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private static SectionsAdapter mSectionsAdapter;
    private static ViewPager mViewPager;
    private StoriesSwipeToRefreshLayout swipeRefreshLayout;
    private SlidingTabLayout mSlidingTabLayout;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        LoadingManager loadingManager = new LoadingManager(getActivity().getApplicationContext());
        loadingManager.loadStories();

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo!= null && networkInfo.isConnected()) {
            for (int i = 0; i < 6; i++) { new DownloadData().execute(i);}
            for (int i = 0; i < 6; i++) {new DownloadStoryImages().execute(i);}
        } else {
            Toast.makeText(getActivity(), "No network detected. Please connect to the internet and try again.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.fragment_home, container, false);



        //Sets the ViewPager and the Adapter for it.
        mSectionsAdapter = new SectionsAdapter(getChildFragmentManager());
        mViewPager = (ViewPager) v.findViewById(R.id.home_fragment_view_pager);
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
        swipeRefreshLayout = (StoriesSwipeToRefreshLayout) v.findViewById(R.id.home_fragment_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setAdapterAndViewPager(mSectionsAdapter, mViewPager);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.primary_color));
        swipeRefreshLayout.requestDisallowInterceptTouchEvent(true);

        //Sets the Sliding tab layout
        mSlidingTabLayout = (SlidingTabLayout) v.findViewById(R.id.home_fragment_sliding_tabs);
        mSlidingTabLayout.setCustomTabView(R.layout.scroll_tab, R.id.scroll_tab_text_view);
        mSlidingTabLayout.setViewPager(mViewPager);
        mSlidingTabLayout.setCustomTabColorizer(new CustomSlidingTabColors());
        mSlidingTabLayout.setBackgroundColor(getResources().getColor(R.color.primary_color));


        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void refreshStories(){

        mSectionsAdapter.notifyDataSetChanged();
        mSectionsAdapter.refreshFragment(mViewPager.getCurrentItem());
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        refreshStories();
        swipeRefreshLayout.setRefreshing(false);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);


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
                mSectionsAdapter.notifyDataSetChanged();
                mSectionsAdapter.refreshFragment(runningThread);
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
                        story.setImageBitmap(Picasso.with(getActivity().getApplicationContext()).load(story.getImageURL()).get());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            mSectionsAdapter.notifyDataSetChanged();
            mSectionsAdapter.refreshFragment(runningThread);
        }
    }

}
