package com.example.android.justjava;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2, basePrice = 5, whippedCream = 1, chocolate = 2;
    String name;
    String[] email = {"obamalik@yahoo.com"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        //Log.v("MainActivity", "Has whipped cream: " + hasWhippedCream);

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        EditText name1 = (EditText) findViewById(R.id.name_view);
        name = name1.getText().toString();

        displayQuantity(quantity);
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, email);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        //displayMessage(priceMessage);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        CharSequence msg = "You cannot have more than 100 coffees";
        quantity = quantity + 1;
        if (quantity >= 100) {
            quantity = 100;

            Context context = getApplicationContext();
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, msg, duration);
            toast.setGravity(Gravity.CENTER, 0,0);
            toast.show();
            //Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        }
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        CharSequence msg = "You cannot have less than 1 coffee" ;
        quantity = quantity - 1;
        if (quantity <= 1) {
            quantity = 1;

            Context context = getApplicationContext();
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, msg, duration);
            toast.setGravity(Gravity.CENTER, 0,0);
            toast.show();
            //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int unit) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + unit);
    }

    /**
     * This method displays the given price on the screen.
     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }

    /**
     * Calculates the price of the order based on the current quantity
     *
     * @param addWhippedCream is whether or not user wants whipped cream topping
     * @param addChocolate    is whether or not user wants chocolate topping
     * @return the price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        if (addWhippedCream) {
            basePrice = basePrice + whippedCream;
        }

        if (addChocolate) {
            basePrice = basePrice + chocolate;
        }

        return quantity * basePrice;
    }

    /**
     * @param price:           price of cup of coffee
     * @param addWhippedCream: determines if option whippedCream is checked
     * @param addChocolate     : determines if option addChocolate is checked
     * @return priceMessage, containing order summary
     */

    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String name) {

        String priceMessage = getString(R.string.order_summary_name) + name;
        priceMessage += "\n" + getString(R.string.add_whipped_cream) + addWhippedCream;
        priceMessage += "\n" + getString(R.string.add_chocolate) + addChocolate;
        priceMessage += "\n" + getString(R.string.quantity) + quantity;
        priceMessage += "\n" + getString(R.string.price_value) + price;
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

}