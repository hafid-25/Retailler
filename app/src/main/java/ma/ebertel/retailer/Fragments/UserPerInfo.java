package ma.ebertel.retailer.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ma.ebertel.retailer.Helpers.BitmapHelper;
import ma.ebertel.retailer.MainActivity;
import ma.ebertel.retailer.R;

public class UserPerInfo extends Fragment implements
        View.OnClickListener,
        RadioGroup.OnCheckedChangeListener {

    private String codeBare = "";
    private String pathToFile = "";
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Location onlyOneLocation;
    private final int REQUEST_FINE_LOCATION = 1234;

    private ImageView clientImage;
    private Bitmap myBitmap;
    private MainActivity activity;
    private RadioGroup rgPeriority, rgSatisfaction, rgInteressCnss;

    private EditText edtClientName, edtClientTel, edtClientEmail, edtClientAddress;

    private ImageButton btnNextSlide;

    public UserPerInfo(String Code, MainActivity activity) {
        this.codeBare = Code;
        this.activity = activity;
    }

    @SuppressLint("MissingPermission")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Toast.makeText(getContext(), "On Create", Toast.LENGTH_SHORT).show();
        Log.d("res", "onCreateView: on create ");
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.user_per_info, container, false);
        TextView txtCode = viewGroup.findViewById(R.id.txtScanCodeBar);
        clientImage = viewGroup.findViewById(R.id.clientImage);

        rgPeriority = viewGroup.findViewById(R.id.rgPeriority);
        rgSatisfaction = viewGroup.findViewById(R.id.rgSatisfaction);
        rgInteressCnss = viewGroup.findViewById(R.id.rgInteressCnss);

        btnNextSlide = viewGroup.findViewById(R.id.btnNextSlide);

        Button btnTakeClientPic = viewGroup.findViewById(R.id.btnTakeClientPic);

        edtClientName = viewGroup.findViewById(R.id.clientName);
        edtClientTel = viewGroup.findViewById(R.id.clientTel);
        edtClientEmail = viewGroup.findViewById(R.id.clientEmail);
        edtClientAddress = viewGroup.findViewById(R.id.clientAddress);

        txtCode.setText(codeBare);

        rgInteressCnss.setOnCheckedChangeListener(this);
        rgSatisfaction.setOnCheckedChangeListener(this);
        rgPeriority.setOnCheckedChangeListener(this);
        // set buttons onclick listner events
        btnTakeClientPic.setOnClickListener(this);
        btnNextSlide.setOnClickListener(this);

        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                onlyOneLocation = location;
                locationManager.removeUpdates(this);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Toast.makeText(activity, "Provider i sdesibled", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };

        locationManager.requestLocationUpdates("gps", 0, 0,locationListener);

        return viewGroup;
    }

    @Override
    public void onResume() {
        super.onResume();
        myBitmap = activity.clientImage;
        if(myBitmap != null){
            clientImage.setImageBitmap(myBitmap);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnTakeClientPic:
                openBackCamera(10);
                break;
            case R.id.btnNextSlide:
                saveDateAndSlide();
                break;
        }
    }

    @SuppressLint("MissingPermission")
    private void saveDateAndSlide() {
        if(!edtClientName.getText().toString().equals("")){
            activity.clientFullName = edtClientName.getText().toString();
        }
        if(!edtClientTel.getText().toString().equals("")){
            activity.clientPhoneNumber = edtClientTel.getText().toString();
        }
        if(!edtClientEmail.getText().toString().equals("")){
            activity.clientEmail = edtClientEmail.getText().toString();
        }
        if(!edtClientAddress.getText().toString().equals("")){
            activity.clientAddress = edtClientAddress.getText().toString();
        }

        locationManager.requestLocationUpdates("gps", 0, 0,locationListener);
        activity.clientLocation = onlyOneLocation.getLatitude()+","+onlyOneLocation.getLongitude();

        Log.d("data", "saveDateAndSlide: asdress "+activity.clientAddress);
        Log.d("data", "saveDateAndSlide: email "+activity.clientEmail);
        Log.d("data", "saveDateAndSlide: phone number "+activity.clientPhoneNumber);
        Log.d("data", "saveDateAndSlide: full name "+activity.clientFullName);
        Log.d("data", "saveDateAndSlide: interest cnss "+activity.clientInterssCnss);
        Log.d("data", "saveDateAndSlide: satisfaction "+activity.clientSatisfacion);
        Log.d("data", "saveDateAndSlide: periority "+activity.clientPeriority);
        Log.d("data", "saveDateAndSlide: city "+activity.clientCity);
        Log.d("data", "saveDateAndSlide: region "+activity.clientRegion);
        Log.d("data", "saveDateAndSlide: location "+activity.clientLocation);
    }

    private void openBackCamera(int requestCode){
        Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePic.resolveActivity(getActivity().getPackageManager()) != null){
            File photoFile = createPhotoFile();
            if(photoFile != null){
                pathToFile = photoFile.getAbsolutePath();
                Uri photoUri = FileProvider.getUriForFile(
                        getActivity().getApplicationContext(),
                        getActivity().getPackageName()+".fileprovider",
                        photoFile);
                takePic.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                startActivityForResult(takePic,requestCode);
            }else {
                Log.d("Image", "openBackCamera: photo temp file is not created");
            }

        }
    }

    private File createPhotoFile() {
        String name = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(name,".jpg",storageDir);
        } catch (IOException e) {
            Log.d("Image", "createPhotoFile: "+e);
        }

        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("res", "onCreateView: on activity ");
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            try{
                File imgFile = new  File(pathToFile);
                if(imgFile.exists()){
                    activity.clientImage = BitmapHelper.reizeBitmap(imgFile.getAbsolutePath(),200,200);
                }
            }catch (Exception ex){

            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (radioGroup.getCheckedRadioButtonId()){
            case R.id.btnPeriorityYes:
                activity.clientPeriority = true;
                break;
            case R.id.btnPeriorityNo:
                activity.clientPeriority = false;
                break;
            case R.id.btnSatisfactionYes:
                activity.clientSatisfacion = true;
                break;
            case R.id.btnSatisfactionNo:
                activity.clientSatisfacion = false;
                break;
            case R.id.btnInterestsYes:
                activity.clientInterssCnss = true;
                break;
            case R.id.btnInterestsNo:
                activity.clientInterssCnss = false;
                break;
        }
    }


}