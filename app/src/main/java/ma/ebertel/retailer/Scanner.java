package ma.ebertel.retailer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Scanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView scannerView;
    private final int CAMERA_CODE = 100;
    private final int WRITE_CODE = 101;
    private final int GPS_CODE = 102;
    private final int CORE_CODE = 103;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        //setContentView(R.layout.activity_scanner);
        setContentView(scannerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestPer();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    protected void onStop() {
        super.onStop();
        scannerView.stopCamera();
    }

    public void requestPer(){
        if(checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.CAMERA},CAMERA_CODE);
            //Toast.makeText(this, "you must request the camera permoisson first", Toast.LENGTH_LONG).show();
        }
        if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},WRITE_CODE);
            //Toast.makeText(this, "you must request the camera permoisson first", Toast.LENGTH_LONG).show();
        }
        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},GPS_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        /*if(requestCode == CAMERA_CODE && permissions[0] == Manifest.permission.CAMERA && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "Camera Permission Granted", Toast.LENGTH_SHORT).show();
        }else if(requestCode == WRITE_CODE && permissions[0] == Manifest.permission.WRITE_EXTERNAL_STORAGE && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "Write Permission Granted", Toast.LENGTH_SHORT).show();
        }else if(requestCode == GPS_CODE && permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "GPS Permission Granted", Toast.LENGTH_SHORT).show();
        }else {
            finish();
        }*/
        /*if(grantResults[0] != PackageManager.PERMISSION_GRANTED){
            finish();
        }*/
    }

    @Override
    public void handleResult(Result rawResult) {
        String code = rawResult.getText();
        Intent resultIntent = new Intent();
        resultIntent.putExtra("code",code);
        setResult(RESULT_OK,resultIntent);
        finish();
    }
}
