package com.ankit.invoiceapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BillActivity extends AppCompatActivity {

    private TableLayout tableLayout;
    private ImageView qrCodeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        tableLayout = findViewById(R.id.table_layout);
        qrCodeImage = findViewById(R.id.qr_code);

        // Get the invoice details and QR code bitmap
        String invoiceDetails = getIntent().getStringExtra("invoiceDetails");
        byte[] qrCodeData = getIntent().getByteArrayExtra("qrCode");

        // Populate the table with invoice details
        populateInvoiceDetails(invoiceDetails);

        // Set the QR code image
        if (qrCodeData != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(qrCodeData, 0, qrCodeData.length);
            qrCodeImage.setImageBitmap(bitmap);
        }
    }

    private void populateInvoiceDetails(String invoiceDetails) {
        String[] lines = invoiceDetails.split("\n");
        for (String line : lines) {
            TableRow tableRow = new TableRow(this);
            TextView textView = new TextView(this);
            textView.setText(line);
            textView.setTextSize(18); // Larger text size
            textView.setPadding(8, 8, 8, 8); // Add padding for better readability
            textView.setTextColor(0xFF000000); // Black text color
            tableRow.addView(textView);
            tableLayout.addView(tableRow);
        }
    }
}
