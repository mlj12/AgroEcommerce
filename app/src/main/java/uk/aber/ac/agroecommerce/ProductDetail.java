package uk.aber.ac.agroecommerce;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProductDetail extends AppCompatActivity {

    private Button add_to_cart_btn;
    private ImageView productImage;
    private ElegantNumberButton quantity_btn;
    private TextView productPrice,productDescription,productName;
    private String productID ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        productID = getIntent().getStringExtra("pid");

        add_to_cart_btn = (Button) findViewById(R.id.add_to_cart_btn);
        productImage = (ImageView) findViewById(R.id.p_image);
        quantity_btn = (ElegantNumberButton) findViewById(R.id.quantity_btn);
        productDescription = (TextView) findViewById(R.id.p_description);
        productPrice = (TextView) findViewById(R.id.p_price);
        productName = (TextView) findViewById(R.id.p_name);


        //Retrieve data from product id
        getProductDetails(productID);
    }

    private void getProductDetails(String productID) {

        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Products");
        productRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    Products products = dataSnapshot.getValue(Products.class);
                    productName.setText(products.getName());
                    productPrice.setText(products.getPrice());
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
