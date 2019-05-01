package uk.aber.ac.agroecommerce;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Chat extends AppCompatActivity {


    private Button send_message_btn;
    private EditText user_message_txt;
    private RecyclerView messages_list;
    private TextView seller_name;
    private  String sellerid,sellername = "";
    private String senderId,key;
    private final List<Message> messageList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private MessagesAdapter messagesAdapter;

    private DatabaseReference messageRef;
    private String saveCurrentDate,saveCurrentTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        sellerid= getIntent().getStringExtra("sellerId");
        sellername= getIntent().getStringExtra("sellerName");


       seller_name = (TextView) findViewById(R.id.chat_seller_name);

       seller_name.setText("Chat with "+ sellername);


        send_message_btn = (Button) findViewById(R.id.send_message_btn);
        user_message_txt = (EditText) findViewById(R.id.send_message_text);

        messages_list = (RecyclerView) findViewById(R.id.recycler_view_chat);

        messagesAdapter = new MessagesAdapter(messageList);

        linearLayoutManager = new LinearLayoutManager(this);
        messages_list.setHasFixedSize(true);
        messages_list.setLayoutManager(linearLayoutManager);

        messages_list.setAdapter(messagesAdapter);


        send_message_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendMessage(); // Allows user to send message
            }
        });

        retrieveMessages();


    }

    private void retrieveMessages() {

        messageRef = FirebaseDatabase.getInstance().getReference().child("Messages");
        senderId = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        messageRef.child(senderId).child(sellerid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Message message = dataSnapshot.getValue(Message.class);
                messageList.add(message);
                messagesAdapter.notifyDataSetChanged();


                if (dataSnapshot.exists()){


                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void sendMessage() {

        messageRef = FirebaseDatabase.getInstance().getReference().child("Messages");

        senderId = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();


        String messageText = user_message_txt.getText().toString();

        if (TextUtils.isEmpty(messageText)) {

            Toast.makeText(this, "Please enter message...", Toast.LENGTH_SHORT).show();
        }

        else {

            String message_sender_ref = senderId +"/" + sellerid; // path for sender - buyer
            String message_receiver_ref =  sellerid +"/" + senderId; // path for receiver - seller

            key = messageRef.child("Messages").child(senderId).child(sellerid).push().getKey().toString();

            Calendar calendar = Calendar.getInstance();

            SimpleDateFormat currentDate = new SimpleDateFormat("dd/M/yyyy");
           saveCurrentDate = currentDate.format(calendar.getTime());


            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
           saveCurrentTime = currentTime.format(calendar.getTime());


           Map messageTextBody = new HashMap<>();

           messageTextBody.put("message",messageText);
            messageTextBody.put("time",saveCurrentTime);
            messageTextBody.put("date",saveCurrentDate);
            messageTextBody.put("from",sellerid);

           Map messageBodyDetails = new HashMap<>();

           messageBodyDetails.put(message_sender_ref + "/" + key, messageTextBody);
           messageBodyDetails.put(message_receiver_ref + "/"+key,messageTextBody);

           messageRef.updateChildren(messageBodyDetails).addOnCompleteListener(new OnCompleteListener() {
               @Override
               public void onComplete(@NonNull Task task) {

                   if(task.isSuccessful()){

                       Toast.makeText(Chat.this, "Message sent successfully", Toast.LENGTH_SHORT).show();
                       user_message_txt.setText(""); //set input text null after message has been sent.
                   }

                   else {

                       String errorMessage = task.getException().getMessage();
                       Toast.makeText(Chat.this, "Error, please try again", Toast.LENGTH_SHORT).show();
                       user_message_txt.setText(""); //set input text null after message has been sent.
                   }


               }
           });

        }
   }


}
