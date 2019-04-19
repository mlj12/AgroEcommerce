package uk.aber.ac.agroecommerce;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ConfirmOrder extends AppCompatActivity {

    private EditText ship_name_txt, ship_phone_txt, ship_address_txt,ship_postal_txt,ship_country_txt,ship_city_txt;
    private Button confirmOrderbtn;
    private String totalAmountconfirmed = "";


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        confirmOrderbtn = (Button) findViewById(R.id.confirmDetailsBtn);
        ship_name_txt = (EditText) findViewById(R.id.shipment_name);
        ship_phone_txt = (EditText) findViewById(R.id.shipment_phone);
        ship_address_txt = (EditText) findViewById(R.id.shipment_adress);
        ship_postal_txt = (EditText) findViewById(R.id.shipment_postalcode);
        ship_country_txt = (EditText) findViewById(R.id.shipment_country);
        ship_city_txt = (EditText) findViewById(R.id.shipment_city);
        totalAmountconfirmed = getIntent().getStringExtra("Total Price");


    }
}
