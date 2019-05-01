package uk.aber.ac.agroecommerce;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageViewHolder> {

    private FirebaseAuth mAuth;
    private DatabaseReference usersDatabaseRef;
    private List<Message> user_messages_list;
    public MessagesAdapter (List<Message> user_messages_list){

        this.user_messages_list=user_messages_list;

    }

    // class for View Holder - Contains all the layout initiated
public class MessageViewHolder extends RecyclerView.ViewHolder{

        public TextView senderMessageText,receiverMessageText,senderName;




        public MessageViewHolder(View itemView){

            super(itemView);

            senderMessageText = (TextView) itemView.findViewById(R.id.sender_message);
            receiverMessageText = (TextView) itemView.findViewById(R.id.receiver_message);
            senderName = (TextView) itemView.findViewById(R.id.sender_name);


        }
}

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message_user,viewGroup,false);

        mAuth= FirebaseAuth.getInstance();

        return new MessageViewHolder(v);

    }

    // Method will bind the text to the view holder

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder messageViewHolder, int i) {

        String messageSenderID= mAuth.getCurrentUser().getUid();
        Message messages = user_messages_list.get(i);
        String fromUserID  = messages.getFrom();

        messageViewHolder.receiverMessageText.setVisibility(View.INVISIBLE);
        messageViewHolder.senderName.setVisibility(View.INVISIBLE);

        if(fromUserID.equals(messageSenderID)) {


            messageViewHolder.senderMessageText.setBackgroundResource(R.drawable.sender_message_background);
            messageViewHolder.senderMessageText.setTextColor(Color.WHITE);
            messageViewHolder.senderMessageText.setGravity(Gravity.LEFT);
            messageViewHolder.senderMessageText.setTextSize(15);
            messageViewHolder.senderMessageText.setText(messages.getMessage());



        }
        else {
            messageViewHolder.senderMessageText.setVisibility(View.INVISIBLE);
            messageViewHolder.receiverMessageText.setVisibility(View.VISIBLE);

            messageViewHolder.receiverMessageText.setBackgroundResource(R.drawable.receiver_message_background);
            messageViewHolder.receiverMessageText.setTextColor(Color.WHITE);
            messageViewHolder.receiverMessageText.setTextSize(15);
            messageViewHolder.receiverMessageText.setGravity(Gravity.RIGHT);
            messageViewHolder.receiverMessageText.setText(messages.getMessage());


        }




    }

    @Override
    public int getItemCount() {
        return user_messages_list.size();
    }
}
