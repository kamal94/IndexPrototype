package indexprototype.com.kamal.indexprototype;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import indexprototype.com.kamal.indexprototype.recyclerViewTesting.StoryRecyclerViewAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StoryListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StoryListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SECTION = "mSection";

    // TODO: Rename and change types of parameters
    private String mSection;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private StoryRecyclerViewAdapter storyRecyclerViewAdapter;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param section Parameter 1.
     * @return A new instance of fragment StoryListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StoryListFragment newInstance(String section) {
        StoryListFragment fragment = new StoryListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SECTION, section);
        fragment.setArguments(args);
        return fragment;
    }

    public StoryListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSection = getArguments().getString(ARG_SECTION);
        }
}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_story_list, container, false);


        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        storyRecyclerViewAdapter = new StoryRecyclerViewAdapter(getActivity(), mSection, StoryRecyclerViewAdapter.VIEW_MODE_LIST_VIEW);
        recyclerView.setAdapter(storyRecyclerViewAdapter);
        storyRecyclerViewAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
        });

        return v;
    }

    public void refreshList(){
        storyRecyclerViewAdapter.notifyDataSetChanged();
    }

    public RecyclerView getRecyclerView(){
        return recyclerView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("StoryListFragment", "onDetach() called for list " + mSection);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d("StoryListFragment", "onAttach() called for list " + mSection);
    }
}
