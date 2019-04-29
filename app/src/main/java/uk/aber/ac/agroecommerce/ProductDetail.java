package uk.aber.ac.agroecommerce;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetail extends AppCompatActivity {

    private Button add_to_cart_btn;
    private ImageView closeTextBtn;
    private ImageView productImage;
    private ElegantNumberButton quantity_btn;
    private TextView productPrice, productDescription, productName;
    private String productID = "";
    private String pUID = "";
    private String uid;
    private String imageurl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        productID = getIntent().getStringExtra("pid");

        pUID = getIntent().getStringExtra("uid");



        add_to_cart_btn = (Button) findViewById(R.id.add_to_cartlist_btn);
        closeTextBtn = (ImageView) findViewById(R.id.close_settings_btn);
        productImage = (ImageView) findViewById(R.id.p_image);
        quantity_btn = (ElegantNumberButton) findViewById(R.id.elegant_quantity_btn);
        productDescription = (TextView) findViewById(R.id.p_description);
        productPrice = (TextView) findViewById(R.id.p_price);
        productName = (TextView) findViewById(R.id.p_name);


        //Retrieve data from product id
        getProductDetails(productID);
        getProductDetails(pUID);

        add_to_cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addingToCartList();
            }
        });


        closeTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    // When clicking on add to cart button, adding the items in the cart list.

    private void addingToCartList() {

        final String saveCurrentTime, saveCurrentDate;
        final ProgressDialog progressDialog = ProgressDialog.show(ProductDetail.this, "Wait", "Adding to cart..", true);


        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        // creating instance for database
        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();




        final DatabaseReference imageRef = FirebaseDatabase.getInstance().getReference().child("Products");

        imageRef.child(productID).child("image").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                imageurl= dataSnapshot.getValue().toString();

                final HashMap<String, Object> cartMap = new HashMap<>();


                cartMap.put("pid", productID);
                cartMap.put("uid", pUID);
                cartMap.put("pname", productName.getText().toString());
                cartMap.put("price",productPrice.getText().toString());
                cartMap.put("description", productDescription.getText().toString());
                cartMap.put("quantity", quantity_btn.getNumber());
                cartMap.put("image", imageurl);
                cartMap.put("date", saveCurrentDate);
                cartMap.put("time", saveCurrentTime);




                cartListRef.child("User View").child(uid).child("Products").child(productID).updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            cartListRef.child("Seller View").child(uid).child("Products").child(productID).updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    //         progressDialog.dismiss();

                                    Toast.makeText(ProductDetail.this, "Product has been added to cart", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ProductDetail.this, Home2.class);
                                    startActivity(intent);
                                }
                            });


                        }
                    }
                });




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


          //  System.out.print(imageurl);



    }

// working
    private void getProductDetails(final String productID) {

        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Products");



        productRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // System.out.print("id is equal to" + productID);

                if (dataSnapshot.exists()) {

                    Products products = dataSnapshot.getValue(Products.class);
                    productName.setText(products.getName());
                    productPrice.setText(String.valueOf(products.getPrice())); // Should convert int price from model to String.
                    productDescription.setText(products.getDescription());
                    Picasso.get().load(products.getImage()).into(productImage);



                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}