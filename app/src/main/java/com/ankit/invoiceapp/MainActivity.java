package com.ankit.invoiceapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText etCustomerName, etServiceDescription, etAmount, etModeOfPayment, etAddress;
    private Button btnGenerateInvoice;
    private ImageView qrCodeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the views
        etCustomerName = findViewById(R.id.et_customer_name);
        etServiceDescription = findViewById(R.id.et_serice_description);
        etAmount = findViewById(R.id.et_amount);
        etModeOfPayment = findViewById(R.id.et_mode_of_payment);
        etAddress = findViewById(R.id.et_address);
        btnGenerateInvoice = findViewById(R.id.btn_generate_invoice);
        qrCodeImage = findViewById(R.id.qr_code);

        // Set click listener for the button
        btnGenerateInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateInvoice();
            }
        });
    }

    private void generateInvoice() {
        // Get user input
        String customerName = etCustomerName.getText().toString();
        String serviceDescription = etServiceDescription.getText().toString();
        String amount = etAmount.getText().toString();
        String modeOfPayment = etModeOfPayment.getText().toString();
        String address = etAddress.getText().toString();

        // Check if fields are empty
        if (customerName.isEmpty() || serviceDescription.isEmpty() || amount.isEmpty() ||
                modeOfPayment.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, "Please fill in all details", Toast.LENGTH_LONG).show();
            return; // Exit the method to avoid further execution
        }

        // Get current date and time
        String currentDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(new Date());

        // Generate invoice details string
        String invoiceDetails = "Invoice for: " + customerName + "\n" +
                "Services: " + serviceDescription + "\n" +
                "Amount: INR " + amount + "\n" +
                "Mode of Payment: " + modeOfPayment + "\n" +
                "Address: " + address + "\n" +
                "Date and Time: " + currentDateTime;

        // Generate the QR code
        Bitmap qrCodeBitmap = generateQRCode(invoiceDetails);
        if (qrCodeBitmap != null) {
            // Start BillActivity and pass data
            Intent intent = new Intent(MainActivity.this, BillActivity.class);
            intent.putExtra("invoiceDetails", invoiceDetails);
            intent.putExtra("qrCode", bitmapToByteArray(qrCodeBitmap));
            startActivity(intent);
        }
    }

    private Bitmap generateQRCode(String data) {
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            return barcodeEncoder.encodeBitmap(data, BarcodeFormat.QR_CODE, 400, 400);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error generating QR code", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    private byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}

