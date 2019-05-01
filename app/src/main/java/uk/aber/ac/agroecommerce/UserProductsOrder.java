package uk.aber.ac.agroecommerce;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

public class UserProductsOrder extends AppCompatActivity {

    private RecyclerView productorderedlist;
   private RecyclerView.LayoutManager layoutManager;
   private DatabaseReference cartlistref,productRef;
   private String userId ="";
   private Query sellerID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_products_order);

      String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

       cartlistref = FirebaseDatabase.getInstance().getReference().child("Seller Orders").child(uid);




        productorderedlist = findViewById(R.id.productOrderlist_seller);
        layoutManager = new LinearLayoutManager(this);
       productorderedlist.setLayoutManager(layoutManager);


       userId = getIntent().getStringExtra("uid"); // id of buyer who passed an order.



    }

    @Override
    protected void onStart() {
        super.onStart();




        FirebaseRecyclerOptions<CartModel> options = new  FirebaseRecyclerOptions.Builder<CartModel>()
                .setQuery(cartlistref,CartModel.class).build();


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
       productorderedlist.setAdapter(adapter);
        adapter.startListening();


    }


        }
