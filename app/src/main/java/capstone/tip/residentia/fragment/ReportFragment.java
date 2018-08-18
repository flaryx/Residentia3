package capstone.tip.residentia.fragment;

import android.app.LauncherActivity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import capstone.tip.residentia.R;


public class ReportFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    public TextView ReportText;
    public Button ReportBtn;
    public DatabaseReference mDB;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;





    public ReportFragment() {
        // Required empty public constructor
    }



    @IgnoreExtraProperties
    public  class reportMethod {

        public String report;


        // Default constructor required for calls to
        // DataSnapshot.getValue(User.class)
        public reportMethod() {
        }

        public reportMethod(String report) {
            this.report = report;

        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_report, container, false);

        final FirebaseDatabase mDB = FirebaseDatabase.getInstance();
       final DatabaseReference myRef = mDB.getReference("REPORTS");
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

         ReportText = (EditText) v.findViewById(R.id.report_text);
        ReportBtn =  v.findViewById(R.id.report_btn);

        ReportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Toast toast;
                Date currentTime = Calendar.getInstance().getTime();
                String date = currentTime.toString();
                String currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser().getUid() ;
                String cUser = currentFirebaseUser.toString();
                String userId = myRef.push().getKey();
                String reportTxt = ReportText.getText().toString();
                myRef.child(cUser).child("reportField").setValue(reportTxt);
                myRef.child(cUser).child("userId").setValue(currentFirebaseUser);
                myRef.child(cUser).child("date").setValue(date);


                if (reportTxt == null){
                    toast = Toast.makeText(getContext(), "Type a report first before submitting", Toast.LENGTH_SHORT);
                    toast.show();
                }else if(reportTxt != null){
                    toast = Toast.makeText(getContext(), "Thank you for submitting a report", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }


                                     }
        );


        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
