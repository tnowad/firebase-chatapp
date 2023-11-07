package com.firebase.chat.activities;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.chat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class GenerateQRCodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qr_code);

        ImageView qrCodeImageView = findViewById(R.id.GenerateQRCodeActivity_ImageView_GeneratedQRCode);

        String content =
                FirebaseAuth.getInstance().getCurrentUser().getUid();

        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();

        try {
            com.google.zxing.common.BitMatrix bitMatrix = barcodeEncoder.encode(content, BarcodeFormat.QR_CODE, 400, 400);
            android.graphics.Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);

            qrCodeImageView.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
