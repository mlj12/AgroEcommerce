package uk.aber.ac.agroecommerce;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private DatabaseReference ProductsRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private Query q;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        ProductsRef = (DatabaseReference) FirebaseDatabase.getInstance().getReference().child("Products");
       q = FirebaseDatabase.getInstance().getReference("Products").orderByChild("Price");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.home_cart);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Cart.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(ProductsRef, Products.class)
                        .build();


        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Products model)
                    {
                        holder.txtProductName.setText(model.getName());
                        holder.txtProductDescription.setText(model.getDescription());
                        holder.txtProductPrice.setText("Price = " + model.getPrice() + "$");
                        Picasso.get().load(model.getImage()).into(holder.imageView);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MainActivity.this,ProductDetail.class);
                                intent.putExtra("pid",model.getPid()); //get specific id of product when user clicks on an item picture
                                startActivity(intent);
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.



        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_sortbyprice) {

            FirebaseRecyclerOptions<Products> options =
                    new FirebaseRecyclerOptions.Builder<Products>()
                            .setQuery(ProductsRef, Products.class)
                            .build();


            FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
                    new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Products model)
                        {
                            holder.txtProductName.setText(model.getName());
                            holder.txtProductDescription.setText(model.getDescription());
                            holder.txtProductPrice.setText("Price = " + model.getPrice() + "$");
                            Picasso.get().load(model.getImage()).into(holder.imageView);

                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(MainActivity.this,ProductDetail.class);
                                    intent.putExtra("pid",model.getPid()); //get specific id when user clicks on an item picture
                                    startActivity(intent);
                                }
                            });

                        }

                        @NonNull
                        @Override
                        public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                        {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                            ProductViewHolder holder = new ProductViewHolder(view);
                            return holder;
                        }
                    };
            recyclerView.setAdapter(adapter);
            adapter.startListening();


        }
        if (id == R.id.action_sortbyprice) {

                FirebaseRecyclerOptions<Products> options =
                        new FirebaseRecyclerOptions.Builder<Products>()
                                .setQuery(q, Products.class)
                                .build();


                FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
                        new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                            @Override
                            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Products model)
                            {
                                holder.txtProductName.setText(model.getName());
                                holder.txtProductDescription.setText(model.getDescription());
                                holder.txtProductPrice.setText("Price = " + model.getPrice() + "$");
                                Picasso.get().load(model.getImage()).into(holder.imageView);

                                holder.itemView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(MainActivity.this,ProductDetail.class);
                                        intent.putExtra("pid",model.getPid()); //get specific id when user clicks on an item picture
                                        startActivity(intent);
                                    }
                                });

                            }

                            @NonNull
                            @Override
                            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                            {
                                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                                ProductViewHolder holder = new ProductViewHolder(view);
                                return holder;
                            }
                        };
                recyclerView.setAdapter(adapter);
                adapter.startListening();

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.drawer_selling) {

            Intent intent = new Intent(MainActivity.this, Product_Category.class);
            startActivity(intent);

            // Handle the camera action
        } else if (id == R.id.drawer_cart) {
            Intent intent = new Intent(MainActivity.this, LogIn.class);
            startActivity(intent);

        } else if (id == R.id.drawer_home) {
            Intent intent = new Intent(MainActivity.this, Register.class);
            startActivity(intent);
        } else if (id == R.id.drawer_myAccount) {
            Intent intent = new Intent(MainActivity.this, Account.class);
            startActivity(intent);
        } else if (id == R.id.drawer_purchases) {

        }
        else if (id == R.id.drawer_logout) {

            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    
}
