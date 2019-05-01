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
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ViewOrders extends AppCompatActivity {

    private RecyclerView orderlist;
    private DatabaseReference orderRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders);

        orderRef = FirebaseDatabase.getInstance().getReference().child("Orders");

        orderlist = findViewById(R.id.orderlist_seller);

        orderlist.setLayoutManager(new LinearLayoutManager(this));






    }

    @Override
    protected void onStart() {
        super.onStart();



        FirebaseRecyclerOptions<OrdersModel> options =
                new FirebaseRecyclerOptions.Builder<OrdersModel>()
                        .setQuery(orderRef, OrdersModel.class)
                        .build();


        FirebaseRecyclerAdapter<OrdersModel, OrderViewHolder> adapter = new FirebaseRecyclerAdapter<OrdersModel, OrderViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull OrderViewHolder holder, final int position, @NonNull final OrdersModel model) {

                holder.txtOrderUserName.setText(model.getPersonName() );
                holder.txtOrderPhoneNumber.setText( model.getPhoneNumber() );
                holder.txtOrderCity.setText(model.getCity() );
                holder.txtOrderPostalCode.setText(model.getPostalCode() );
                holder.txtOrderAddress.setText(model.getStreetAddress() );
                holder.txtOrderCountry.setText(model.getCountry() );
                holder.txtTotalAmount.setText(model.getTotalAmount());
                holder.txtOrderDate.setText(model.getDate());
                holder.txtOrderTime.setText(model.getTime());
                holder.viewProductOrderbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String uid = getRef(position).getKey();
                        Intent intent = new Intent (ViewOrders.this,UserProductsOrder.class);
                        intent.putExtra("uid",uid);
                        startActivity(intent);
                    }
                });

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        CharSequence options[] = new CharSequence[] {

                                "Yes","No"
                        };

                        AlertDialog.Builder builder= new AlertDialog.Builder(ViewOrders.this);
                        builder.setTitle("Have you shipped this order?");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {

                                if(i ==0){
                                    String uid = getRef(position).getKey();
                                    RemoveOrder(uid);


                                }
                                else if (i==1) {


                                }
                            }
                        });
                    }
                });

            }

            @NonNull
            @Override
            public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderslayout,parent,false);
                OrderViewHolder holder = new OrderViewHolder(view);
                return holder;
            }
        };

        orderlist.setAdapter(adapter);
        adapter.startListening();
    }

    private void RemoveOrder(String uid) {

        orderRef.child(uid).removeValue();
    }


}