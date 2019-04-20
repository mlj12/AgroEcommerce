package uk.aber.ac.agroecommerce;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.google.firebase.database.FirebaseDatabase.getInstance;

public class Chat extends AppCompatActivity {



    private FirebaseListAdapter<ChatMessage> adapter;
   private FloatingActionButton send_btn;
    private EditText inputMessage;

    DatabaseReference chatref;
    FirebaseAuth firebaseAuth;
    private String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        send_btn =(FloatingActionButton) findViewById(R.id.send_message_btn);

      uid= firebaseAuth.getCurrentUser().getEmail();

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText input = (EditText) findViewById(R.id.input_message);

              FirebaseDatabase.getInstance().getReference().push().setValue(new ChatMessage(input.getText().toString(),uid));



                input.setText("");


            }
        });



        //load content



    }




}
