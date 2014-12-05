package indexprototype.com.kamal.indexprototype.recyclerViewTesting;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.UUID;

import indexprototype.com.kamal.indexprototype.R;
import indexprototype.com.kamal.indexprototype.StoriesBank;
import indexprototype.com.kamal.indexprototype.Story;
import indexprototype.com.kamal.indexprototype.StoryReaderActivity;

/**
 * A <code>RecyclerView.Adapter</code> that can handle stories and display them in a cardview.
 * Created by Kamal on 10/23/2014.
 *
 * @author Kamal Kamalaldin
 * @version 10/29/2014
 *
 * Modifications:
 * Name             Date            Modified
 * --------------------------------------------------------------------------------------------
 *
 */
public class StoryRecyclerViewAdapter extends RecyclerView.Adapter<StoryRecyclerViewAdapter.ViewHolder> {

    //private instance fields
    private Context mContext;                   //the context of the activity that calls/creates the adapter
    private String mSection;
    /**
     * simple constructor that
     *
     * @param context   The context of the activity that created the adapter
     */
    public StoryRecyclerViewAdapter(Context context, String section) {
        mContext = context;
        mSection = section;
    }

    /**
     *Creates a <Code>ViewHolder</Code> that holds a <Code>CardView</Code> and returns it.
     * @param viewGroup     The <Code>ViewGroup</Code> that will serve as the parent of the list
     * @param i             <Code>Unknown</Code>
     * @return  vh  The <Code>ViewHolder</Code> that holds a cardviews of the list
     */
    @Override
    public StoryRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        CardView v = (CardView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_implementation, viewGroup, false);

        ViewHolder vh = new ViewHolder(v, mContext);
        return vh;
    }

    /**
     * Defines what and what data is assigned for each <Code>ViewHolder</Code>.
     * This goes through the storiesList arraylist and assigns each card a story
     * based on the index value of the card and the story. For example, the first
     * card gets the story storiesList.get(i)
     *
     * @param viewHolder    The viewHolder at position i
     * @param i             The position of the viewHolder
     */
    @Override
    public void onBindViewHolder(StoryRecyclerViewAdapter.ViewHolder viewHolder, int i) {
        Story story = StoriesBank.findByIndex(i, mSection);
        viewHolder.largeText.setText(story.getTitle());
//        viewHolder.smallText.setText(story.getContent()); deactivated to test for faster, less involved story download
        viewHolder.image.setImageDrawable(new BitmapDrawable(mContext.getResources(),story.getImageBitmap()));
        viewHolder.id = story.getID();
    }

    /**
     * Returns the number of stories for the adapter to view and consider.
     *
     * @return  StoriesBank.size    The number of stories in the StoriesBank
     */
    @Override
    public int getItemCount() {
        return StoriesBank.getSection(mSection).size();
    }


    /**
     * An inner class that extends <Code>RecyclerView.ViewHolder</Code> and overrides some methods.
     * it takes in a cardview and sets it to display a story's information.
     * <p/>
     * It includes a <Code>View.OnClickListener</Code> that handles click events on story cards.
     * This class creates an intent and starts a <Code>StoryReaderActivity</Code> activity
     * when the <Code>OnClickListener</Code> is called.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        //local fields
        TextView largeText; //the title  of the story
        TextView smallText; //the content of the story
        Intent intent;      //the intent that gets called when a story is clicked
        UUID id;            //the id of the story...used to create an intent to the StoryReaderActivity
        ImageView image;     //the image of the story
        //class to locate the specific story that was clicked

        /**
         * A <code>ViewHolder</code> constructor that sets a <Code>CardView</Code> to
         * display story information. This constructor also employs an <Code>OnClickListener</Code>
         * that listens to clicks on the <Code>CardView</Code>.
         *
         * @param storyCardView A <Code>CardView</Code> that represents information of a story
         * @param context       Context of the activity that called the <Code>StoryRecyclerViewAdapter</Code>.
         *                      It is used to create and launch an intent for when the onClickListener is called.
         */
        public ViewHolder(CardView storyCardView, final Context context) {
            super(storyCardView);

            //Sets the textviews and creates an intent to launch a StoryReaderActivity if the cardview is clicked.
            //STUB NEEDS TO ADD AN IMPLEMENTATION OF SETTING THE IMAGE.
            largeText = (TextView) storyCardView.findViewById(R.id.story_list_view_large_text_view);
            smallText = (TextView) storyCardView.findViewById(R.id.story_list_view_small_text_view);
            image = (ImageView) storyCardView.findViewById(R.id.story_list_view_image_view);
            intent = new Intent(context, StoryReaderActivity.class);

            //sets an OnClickListener for the cardview to handle clicks.
            storyCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, StoryReaderActivity.class);
                    intent.putExtra("STORY_ID", id);
                    context.startActivity(intent);
                }
            });
        }
    }
}
