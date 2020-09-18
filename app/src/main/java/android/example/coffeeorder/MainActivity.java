package android.example.coffeeorder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    int quantity=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void increment(View view) {
        quantity++;
        displayQuantity(quantity);
    }
    public void decrement(View view) {
        quantity--;
        displayQuantity(quantity);
    }

    public void submitOrder(View view) {

        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_Cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        Log.v("MainActivity", "Has Whipped Cream: " + hasWhippedCream);

        CheckBox chocolateBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateBox.isChecked();
        Log.v("MainActivity","Has Chocolate? " + hasChocolate);

        int price = calculatePrice( hasWhippedCream, hasChocolate );

        String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate );

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Order Summary for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


        displayMessage(priceMessage);




    }
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate){
        int price = quantity*5;
        if(hasWhippedCream) price += quantity*1;
        if(hasChocolate) price += quantity*2;

        return price;
    }

    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate){
        String priceMessage = "Name:\t" + name;
        priceMessage += "\nQuantity:\t" + quantity;
        priceMessage += "\nWhipped Cream? " + addWhippedCream;
        priceMessage += "\nChocolate? " + addChocolate;
        priceMessage += "\nTotal: $ " + price;
        priceMessage += "\nThank You";
        return  priceMessage;

    }

    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }



    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.oder_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}
