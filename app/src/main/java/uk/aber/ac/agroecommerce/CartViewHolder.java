package uk.aber.ac.agroecommerce;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtProductName, txtProductDescription, txtProductPrice,txtProductQuantity;
    public ImageView imageView;
    public ItemClickListner listner;





    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.p_image_cart);
        txtProductName = (TextView) itemView.findViewById(R.id.p_name_cart);
        txtProductDescription = (TextView) itemView.findViewById(R.id.p_description_cart);
        txtProductPrice = (TextView) itemView.findViewById(R.id.p_price_cart);
        txtProductQuantity = (TextView) itemView.findViewById(R.id.p_quantity_cart);
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