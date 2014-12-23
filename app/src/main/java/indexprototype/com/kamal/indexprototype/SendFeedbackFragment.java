package indexprototype.com.kamal.indexprototype;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class SendFeedbackFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final String FRAGMENT_TAG = "ContactUS";

    // TODO: Rename and change types of parameters
    private Button sendMessageButton;
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SendFeedbackFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SendFeedbackFragment newInstance(String param1, String param2) {
        SendFeedbackFragment fragment = new SendFeedbackFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public SendFeedbackFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_contact_us, container, false);

        final EditText mFeedBackMessage = (EditText) v.findViewById(R.id.contact_us_message);

        sendMessageButton = (Button) v.findViewById(R.id.contact_us_send_button);
        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                String[] emails = new String[1];
                emails[0] = EMAIL_ADDRESS_TO_CONTACT;
                intent.putExtra(Intent.EXTRA_EMAIL, emails);
                intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback for The Index App");
                intent.putExtra(Intent.EXTRA_TEXT, "Dear Index,\n\n" + mFeedBackMessage.getText() + "\n \nThank you!");
                getActivity().startActivity(intent);
                Toast.makeText(getActivity(), "Press send to help us with your awesome feedback!", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    private final String EMAIL_ADDRESS_TO_CONTACT = "kamal@kamalaldin.com";
}
