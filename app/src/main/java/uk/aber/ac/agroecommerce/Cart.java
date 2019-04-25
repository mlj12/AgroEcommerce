package uk.aber.ac.agroecommerce;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button confirmorder_btn;
    private TextView totalAmountInCart;
    private DatabaseReference cartRef;
    private String uid;
    private int totalAmount =0;
    private DatabaseReference pOrderRef;
    private String productID,productName,productPrice,productDescription,quantity_btn,imageurl,saveCurrentDate,saveCurrentTime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.recycler_cartlist);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
       confirmorder_btn = (Button) findViewById(R.id.confirm_order_btn);
        totalAmountInCart = (TextView) findViewById(R.id.cart_total_amount);
        cartRef = (DatabaseReference) FirebaseDatabase.getInstance().getReference().child("Cart List").child("User View");
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

       confirmorder_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                productOrder();

                Intent intent = new Intent(Cart.this, ConfirmOrder.class);
                intent.putExtra("Total Price", String.valueOf(totalAmount)); // sending total amount to the next activity
                startActivity(intent);
                finish();
            }
        });
    }

    private void productOrder() {



        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String pid = FirebaseDatabase.getInstance().getReference("Orders").child(uid).push().getKey();
        pOrderRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(uid).child(pid);


        cartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Map<String, Object> orderlist = (HashMap<String, Object>) dataSnapshot.getValue();

                List<Object> values = (List<Object>) orderlist.values();

                System.out.print(values);




                final HashMap<String, Object> cartMap = new HashMap<>();

                cartMap.put("pid", productID);
                cartMap.put("pname", productName);
                cartMap.put("price", productPrice);
                cartMap.put("description", productDescription);
                cartMap.put("quantity", quantity_btn);
                cartMap.put("image", imageurl);
                cartMap.put("date", saveCurrentDate);
                cartMap.put("time", saveCurrentTime);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<CartModel> options =
                new FirebaseRecyclerOptions.Builder<CartModel>()
                        .setQuery(cartRef.child(uid).child("Products"), CartModel.class)
                        .build(); // pass model class Cart.class


        FirebaseRecyclerAdapter<CartModel, CartViewHolder> adapter =
                new FirebaseRecyclerAdapter<CartModel, CartViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull final CartModel model)
                    {
                        holder.txtProductName.setText(model.getPname());
                        holder.txtProductDescription.setText(model.getDescription());
                        holder.txtProductQuantity.setText(model.getQuantity());
                        holder.txtProductPrice.setText("Price = " + model.getPrice() + "$");
                        Picasso.get().load(model.getImage()).into(holder.imageViewCart);

                        //calculate total price

                        int totalPriceProduct = ((Integer.valueOf(model.getPrice())))*Integer.valueOf(model.getQuantity()); // converting string to integer

                        //add total price of 1 product to total amount

                        totalAmount= totalPriceProduct + totalAmount;

                        totalAmountInCart.setText(String.valueOf("Total Price =$" + totalAmount));

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                CharSequence options[] = new CharSequence[]{

                                        "Edit", "Remove"
                                };

                                AlertDialog.Builder builder = new AlertDialog.Builder(Cart.this);
                                builder.setTitle("Cart Options");

                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) { //i is index of the two buttons

                                   if(i==0){

                                       Intent intent = new Intent(Cart.this,ProductDetail.class);
                                       intent.putExtra("pid",model.getPid());
                                       startActivity(intent);
                                   }

                                   if(i==1){

                                       cartRef.child(uid).child("Products").child(model.getPid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                           @Override
                                           public void onComplete(@NonNull Task<Void> task) {

                                               if(task.isSuccessful()){

                                                   Toast.makeText(Cart.this, "Item Removed", Toast.LENGTH_SHORT).show();
//                                                   Intent intent = new Intent (Cart.this, Cart.class);
//                                                   startActivity(intent);
                                               }
                                           }
                                       });


                                   }

                                    }

                                });
                                builder.show();

                            }
                        });


                    }

                    @NonNull
                    @Override
                    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_view, parent, false);
                        CartViewHolder holder = new CartViewHolder(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }


}
