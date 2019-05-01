package uk.aber.ac.agroecommerce;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {

  private  String sellerid,sellerName = "";
    private DatabaseReference profileRef;
    private TextView txt_p_name,txt_p_address, txt_p_email;
    private ImageView profile_image;
    private Button chat_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);



        txt_p_address = (TextView) findViewById(R.id.profile_adress);
        txt_p_name = (TextView) findViewById(R.id.profile_name);
        txt_p_email = (TextView) findViewById(R.id.profile_email);
        profile_image = (ImageView) findViewById(R.id.profile_image);
        chat_btn = (Button) findViewById(R.id.profile_chat_btn);
        sellerid= getIntent().getStringExtra("sellerId"); // get seller id from previous activity



        retrieveUserDetails();


    }

    public  void retrieveUserDetails(){

        profileRef = FirebaseDatabase.getInstance().getReference().child("Users").child(sellerid);

        profileRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){



                    User users = dataSnapshot.getValue(User.class);
                    txt_p_name.setText(users.getName());
                    txt_p_address.setText(users.getAddress());
                    txt_p_email.setText(users.getEmail());
                    Picasso.get().load(users.getImage()).into(profile_image);

                    sellerName = users.getName();

                    chat_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(Profile.this,Chat.class);
                            intent.putExtra("sellerId", sellerid);//passing seller uid to chat activity
                            intent.putExtra("sellerName",sellerName);
                            startActivity(intent);
                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
