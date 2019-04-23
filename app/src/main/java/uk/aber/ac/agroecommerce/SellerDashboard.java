package uk.aber.ac.agroecommerce;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class SellerDashboard extends AppCompatActivity {

    private Button view_product_btn,view_orders_btn,manage_product_btn,home_btn,sign_out_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_dashboard);

        view_orders_btn = (Button) findViewById(R.id.dashboard_viewOrders);
        view_product_btn = (Button) findViewById(R.id.dashboard_viewProducts);
        manage_product_btn = (Button) findViewById(R.id.dashboard_manageProducts);
        home_btn = (Button) findViewById(R.id.dashboard_Home);
       sign_out_btn = (Button) findViewById(R.id.dashboard_Signout);

       view_orders_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Intent intent = new Intent(SellerDashboard.this,ViewOrders.class);
               startActivity(intent);
           }
       });

       view_product_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Intent intent = new Intent(SellerDashboard.this,ViewSellerProducts.class);
               startActivity(intent);
           }
       });


        manage_product_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SellerDashboard.this,Product_Category.class);
                startActivity(intent);
            }
        });



        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SellerDashboard.this,Home2.class);
                startActivity(intent);
            }
        });

        sign_out_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(SellerDashboard.this,MainActivity.class);
                startActivity(intent);
            }
        });






    }
}
