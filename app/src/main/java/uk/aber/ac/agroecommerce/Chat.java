package uk.aber.ac.agroecommerce;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;

public class Chat extends AppCompatActivity {


    private Button send_message_btn;
    private EditText user_message_txt;
    private RecyclerView messages_list;
    private String messageSenderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        send_message_btn = (Button) findViewById(R.id.send_message_btn);
        user_message_txt = (EditText) findViewById(R.id.send_message_text);
        messages_list = (RecyclerView) findViewById(R.id.recycler_view_chat);


    }



}
