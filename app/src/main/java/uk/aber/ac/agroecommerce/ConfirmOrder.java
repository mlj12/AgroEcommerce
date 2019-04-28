package uk.aber.ac.agroecommerce;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.data.DataBufferObserverSet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmOrder extends AppCompatActivity {

    private EditText ship_name_txt, ship_phone_txt, ship_address_txt, ship_postal_txt, ship_country_txt, ship_city_txt;
    private Button confirmOrderbtn;
    private String totalAmountconfirmed = "";
    private String uid;
    private DatabaseReference cartRef;
    private DatabaseReference orderRef;
    private String order_id;



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_confirm_order);


        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    orderRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(uid);

        confirmOrderbtn = (Button) findViewById(R.id.proceed_to_checkout_btn);
        ship_name_txt = (EditText) findViewById(R.id.shipment_name);
        ship_phone_txt = (EditText) findViewById(R.id.shipment_phone);
        ship_address_txt = (EditText) findViewById(R.id.shipment_adress);
        ship_postal_txt = (EditText) findViewById(R.id.shipment_postalcode);
        ship_country_txt = (EditText) findViewById(R.id.shipment_country);
        ship_city_txt = (EditText) findViewById(R.id.shipment_city);


        totalAmountconfirmed = getIntent().getStringExtra("Total Price");


        confirmOrderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDetails();
            }
        });
    }

    private void checkDetails() {

     if (TextUtils.isEmpty(ship_name_txt.getText().toString())){

         Toast.makeText(this, "Enter your full name", Toast.LENGTH_SHORT).show();
     }

        else if (TextUtils.isEmpty(ship_address_txt.getText().toString())){

            Toast.makeText(this, "Enter your street address", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(ship_country_txt.getText().toString())){

            Toast.makeText(this, "Enter your country", Toast.LENGTH_SHORT).show();
        }
      else  if (TextUtils.isEmpty(ship_phone_txt.getText().toString())){

            Toast.makeText(this, "Enter your phone number", Toast.LENGTH_SHORT).show();
        }

        else {

            confirmOrder();
     }
    }

    private void confirmOrder() {

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("dd/mm/yyyy");
        String saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");
       String saveCurrentTime = currentTime.format(calendar.getTime());



        HashMap<String,Object> orderMap = new HashMap<>();

        orderMap.put("totalAmount", totalAmountconfirmed);
        orderMap.put("personName", ship_name_txt.getText().toString());
        orderMap.put("phoneNumber", ship_phone_txt.getText().toString());
        orderMap.put("streetAddress", ship_address_txt.getText().toString());

        orderMap.put("country", ship_country_txt.getText().toString());
        orderMap.put("postalCode", ship_postal_txt.getText().toString());
        orderMap.put("state", "Not Shipped");
        orderMap.put("date", saveCurrentDate);
        orderMap.put("time", saveCurrentTime);




        orderRef.updateChildren(orderMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){

                    Toast.makeText(ConfirmOrder.this, "Order Confirmed", Toast.LENGTH_SHORT).show();

                    // Remove items from cart

                    FirebaseDatabase.getInstance().getReference().child("Cart List").child("User View")
                            .child(uid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){

                                Toast.makeText(ConfirmOrder.this, "Your order has been placed successfully", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(ConfirmOrder.this,Home2.class);

                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // allows user to return to activity
                                startActivity(intent);
                                finish();
                            }

                        }
                    });


                }
            }
        });



    }


}