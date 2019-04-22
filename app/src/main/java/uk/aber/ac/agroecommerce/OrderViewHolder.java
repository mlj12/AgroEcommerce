package uk.aber.ac.agroecommerce;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView txtOrderUserName, txtOrderPhoneNumber, txtOrderCity,txtOrderAddress,txtOrderPostalCode,txtOrderCountry,txtTotalAmount,txtOrderTime,txtOrderDate;
        public ItemClickListner listner;
        public Button viewProductOrderbtn;





    public OrderViewHolder (@NonNull View itemView) {
        super(itemView);


        txtOrderUserName = (TextView) itemView.findViewById(R.id.order_username);
        txtOrderPhoneNumber = (TextView) itemView.findViewById(R.id.order_phoneNumber);
        txtOrderAddress = (TextView) itemView.findViewById(R.id.order_streetAddress);
        txtOrderCity = (TextView) itemView.findViewById(R.id.order_city);
        txtOrderPostalCode = (TextView) itemView.findViewById(R.id.order_postalCode);
        txtOrderCountry = (TextView) itemView.findViewById(R.id.order_country);
        txtTotalAmount = (TextView) itemView.findViewById(R.id.order_totalPrice);
        txtOrderDate = (TextView) itemView.findViewById(R.id.order_date);
        txtOrderTime = (TextView) itemView.findViewById(R.id.order_time);
        viewProductOrderbtn = (Button) itemView.findViewById(R.id.view_products_ordered_btn);





    }

        public void setItemClickListner(ItemClickListner listner)
        {
            this.listner = listner;
        }

        @Override
        public void onClick(View view)
        {
            listner.onClick(view, getAdapterPosition(), false);
        }
    }

