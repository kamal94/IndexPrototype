package indexprototype.com.kamal.indexprototype;

import android.content.Context;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

/**
 * Created by Kamal on 10/19/2014.
 */
public class StoriesBank {

    private static ArrayList<Story> stories = new ArrayList<Story>();;

    public static ArrayList<Story> getStories() {
        return stories;
    }

    public static void addStory(Story story) {
        stories.add(story);

    }

    /**
     * Finds the story in the bank that has the same UUID
     *
     * @param uuid The UUID of the story to be found
     * @return
     */
    public static Story findById(UUID uuid) {
        for (Story story : stories) {
            if (story.getID().equals(uuid))
                return story;
        }

        return null;
    }

    /**
     * Finds the story in the bank with the story index.
     * Used mainly for the <Code>StoryRecyclerViewAdaoter</Code> to
     * keep the cards and stories in synch.
     *
     * @param index The index of the story in the stories arraylist
     * @return
     */
    public static Story findByIndex(int index) {
        return stories.get(index);
    }

    public static void clear(){
        Iterator<Story> itr = stories.iterator();
        while(itr.hasNext()){
            itr.next();
            itr.remove();
        }

    }

    public static int howMany(){
        int count  = 0;
        for(Story story: stories){
            count++;
        }
        return count;
    }
}
