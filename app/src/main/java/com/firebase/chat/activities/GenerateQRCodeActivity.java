package com.firebase.chat.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.firebase.chat.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class GenerateQRCodeActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 100;
    private String content;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qr_code);

        ImageView qrCodeImageView = findViewById(R.id.GenerateQRCodeActivity_ImageView_GeneratedQRCode);
        Button saveQRCodeButton = findViewById(R.id.GenerateQRCodeActivity_Button_SaveQRCode);
        Button backButton = findViewById(R.id.GenerateQRCodeActivity_Button_Back);

        backButton.setOnClickListener(v -> finish());

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("content")) {
            content = intent.getStringExtra("content");

            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();

            try {
                com.google.zxing.common.BitMatrix bitMatrix = barcodeEncoder.encode(content, BarcodeFormat.QR_CODE, 400, 400);
                android.graphics.Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);

                qrCodeImageView.setImageBitmap(bitmap);
                this.bitmap = bitmap;

                saveQRCodeButton.setOnClickListener(v -> {
                    if (hasStoragePermission()) {
                        saveQRCodeToStorage(bitmap);
                    } else {
                        requestStoragePermission();
                    }
                });
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean hasStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestStoragePermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // Display a rationale to the user
            new AlertDialog.Builder(this)
                    .setTitle("Permission Needed")
                    .setMessage("This permission is required to save the QR Code.")
                    .setPositiveButton("OK", (dialog, which) -> {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> {
                        Toast.makeText(this, "Permission denied. Cannot save QR Code.", Toast.LENGTH_SHORT).show();
                    })
                    .show();
        } else {
            // Request the permission without showing a rationale
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveQRCodeToStorage(bitmap);
            } else {
                Toast.makeText(this, "Permission denied. Cannot save QR Code.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveQRCodeToStorage(Bitmap bitmap) {
        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + File.separator + "QRCode.png";
        File qrCodeFile = new File(filePath);

        try {
            FileOutputStream outputStream = new FileOutputStream(qrCodeFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();

            Toast.makeText(this, "QR Code saved to Pictures folder", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save QR Code", Toast.LENGTH_SHORT).show();
        }
    }
}
