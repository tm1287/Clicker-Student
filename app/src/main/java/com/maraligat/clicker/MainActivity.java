package com.maraligat.clicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ImageButton b_agree;
    private ImageButton b_disagree;
    private Button b_reset;

    private TextView tv_agree;
    private TextView tv_disagree;
    private TextView tv_question;

    private DatabaseReference dr_firebase;
    private DatabaseReference dr_agreeChild;
    private DatabaseReference dr_disagreeChild;
    private DatabaseReference dr_questionChild;

    private static Random mRandom = new Random();
    private int mAgree;
    private int mDisagree;
    private String mQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        mAgree = 0;
        mDisagree = 0;

        b_agree = (ImageButton)findViewById(R.id.agree_b);
        b_disagree = (ImageButton)findViewById(R.id.disagree_b);
        b_reset = (Button)findViewById(R.id.reset_b);

        tv_agree = (TextView)findViewById(R.id.agree_tv);
        tv_disagree = (TextView)findViewById(R.id.disagree_tv);
        tv_question = (TextView)findViewById(R.id.question_tv);

        dr_firebase = FirebaseDatabase.getInstance().getReference();
        dr_agreeChild = dr_firebase.child("agree");
        dr_disagreeChild = dr_firebase.child("disagree");
        dr_questionChild = dr_firebase.child("question");

        tv_question.setText("");

        b_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dr_agreeChild.setValue(++mAgree);
            }
        });

        b_disagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dr_disagreeChild.setValue(++mDisagree);
            }
        });

        b_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dr_agreeChild.setValue(0);
                dr_disagreeChild.setValue(0);
            }
        });

        dr_agreeChild.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mAgree = dataSnapshot.getValue(Integer.class);
                tv_agree.setText(mAgree+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        dr_disagreeChild.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mDisagree = dataSnapshot.getValue(Integer.class);
                tv_disagree.setText(mDisagree+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        dr_questionChild.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mQuestion = dataSnapshot.getValue(String.class);
                tv_question.setText(mQuestion);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}