package com.example.android.justjava2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * This method is called when the order button is clicked.
     */
    int quantity = 2;
    public int priceOfOrder(){
        return quantity*5;
    }
    public void submitOrder(View view) {
        CheckBox WhippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = WhippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckbox.isChecked();
        int price = calculatePrice(hasWhippedCream,hasChocolate);
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();
        String priceMessage = createOrderSummary(price,hasWhippedCream,hasChocolate,name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.orderFor)+name);
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }




    }
    private int calculatePrice(boolean addWhippedCream,boolean addChocolate){
        int basePrice = 5;
        if(addWhippedCream){
            basePrice = basePrice + 1;
        }
        if(addChocolate){
            basePrice = basePrice +  2;
        }
        return  quantity * basePrice;
    }
    private  String createOrderSummary(int price,boolean addWhippedCream,boolean addChocolate,String name){
        String priceMessage = " Name: " + name;
        priceMessage+= "\n Add Whipped Cream ? " + addWhippedCream;

        priceMessage+= "\n Add Chocolate ? "+ addChocolate;

        priceMessage+="\n Quantity:"+quantity;
        priceMessage+="\n Total :$" + price;
        priceMessage+= "\n" + getString(R.string.thank_you);
        return priceMessage;
    }
    private void displayMessage(String message){
        TextView printTextView = (TextView)findViewById(R.id.price_text_view);
        printTextView.setText(message);
    }
    public void increment(View view) {

        if(quantity == 100){
            Toast.makeText(this,"You cannot have more than 100 coffees",Toast.LENGTH_SHORT).show();
            return;
        }else {
            quantity = quantity + 1;
        }
        displayQuantity(quantity);
    }
    public void decrement(View view) {

        if(quantity == 1){
            Toast.makeText(this,"You cannot have less than 1 coffee",Toast.LENGTH_SHORT).show();
            return;
        }else{
            quantity = quantity - 1;
        }
        displayQuantity(quantity);
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}
