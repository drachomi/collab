package com.richard.imoh.collab.Property;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.richard.imoh.collab.Pojo.Property;
import com.richard.imoh.collab.R;

import com.richard.imoh.collab.Utils.GetFilePath;
import com.richard.imoh.collab.Utils.Location;
import com.richard.imoh.collab.Utils.SaveImageInFireStore;
import com.richard.imoh.collab.Utils.SavePropertyInFireStore;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class AddProperty extends AppCompatActivity {
    String propertyImage1;
    String propertyImage2;
    String propertyImage3;
    Spinner letTypeSpinner, stateSpinner, citySpinner, purposeSpinner, propertyTySpinner,suitableForSpinner;
    EditText plotEditText, addInfo, priceEdit;
    String agentName, agentDp, price, state, city, plotNo, suitableFor, propertyType, letType, additionalInfo,userId;
    private ArrayAdapter<String> cityArrayAdapter;
    private ArrayAdapter<String> stateArrayAdapter;
    private ArrayAdapter<String> purposeAdapter;
    private ArrayAdapter<String> propertyTypeAdapter;

    Location location = new Location();
    ArrayList<String> cityArray;
    ArrayList<String> stateArray;
    List<String> propertyTypeList = new ArrayList<>();
    List<String> purposeList = new ArrayList<String>();
    List<String> residentialList = new ArrayList<>();
    List<String> commercialList = new ArrayList<>();
    ImageView image1,image2,image3;
    RelativeLayout firstR,secondR,thirdR;
    static final int IMAGE_PICKER = 9;
    int imgCounter = 0;
    SaveImageInFireStore imageInFireStore = new SaveImageInFireStore();

    GetFilePath getFilePath = new GetFilePath();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);
        firstR = findViewById(R.id.add_prop_first_relative);
        secondR = findViewById(R.id.add_prop_second_relative);
        thirdR = findViewById(R.id.add_prop_third_relative);
        plotEditText = findViewById(R.id.add_prop_plots);
        addInfo = findViewById(R.id.add_prop_description);
        priceEdit = findViewById(R.id.add_prop_price);
        letTypeSpinner = findViewById(R.id.add_prop_let_type);
        stateSpinner = findViewById(R.id.add_prop_state);
        citySpinner = findViewById(R.id.add_prop_city);
        purposeSpinner = findViewById(R.id.add_prop_purpose);
        propertyTySpinner = findViewById(R.id.add_prop_prop_type);
        suitableForSpinner = findViewById(R.id.add_prop_suitable);
        image1 = findViewById(R.id.add_prop_image1);
        image2 = findViewById(R.id.add_prop_image2);
        image3 = findViewById(R.id.add_prop_image3);
        propertyImage1 = "";
        propertyImage2 = "";
        propertyImage3 = "";
          getUserName();
          addSpinnerString();
          stateSpinner();
          purposeSpinner();

    }

    public void addPictureBtn(View view) {


    }

    public void addPropertyImages(View view) {
        // TODO: Fire an intent to show an image picker
        Log.d("dayo", "Got to the onclick listerner method");
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/jpeg");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), IMAGE_PICKER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        imageView.setVisibility(View.GONE);

        if (requestCode == IMAGE_PICKER && resultCode == RESULT_OK) {
            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount(); //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
                for (int i = 0; i < count; i++) {
                    Uri selectedImageUri = data.getClipData().getItemAt(i).getUri();
                    showDialog(selectedImageUri);
//                    getMultipleImage(selectedImageUri, i, count);
                }
            } else if (data.getData() != null) {
                Uri selectedImageUri = data.getData();
                showDialog(selectedImageUri);
//                getMultipleImage(selectedImageUri, 0, 1);

            }

        }
    }

    public void sendPropToServer(View view) {
        if(TextUtils.isEmpty(propertyImage1)||TextUtils.isEmpty(propertyImage2)||TextUtils.isEmpty(propertyImage3)){
            Toast.makeText(getApplicationContext(), "Please Select 3 Pictures", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            sendToGetImage();
        }




    }



    void getUserName() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        agentName = preferences.getString("myFullname", "");
        agentDp = preferences.getString("myDp", "");
        userId = preferences.getString("myUserId", "");

    }


    void stateSpinner() {
        stateArray = location.getLocation("States");
        stateArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stateArray);
        stateArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(stateArrayAdapter);
        stateSpinner.setOnItemSelectedListener(stateClickListerner);

    }

    void citySpinner() {
        cityArray = location.getLocation(stateSpinner.getSelectedItem().toString());

        cityArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cityArray);
        cityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(cityArrayAdapter);
        citySpinner.setOnItemSelectedListener(cityClickListerner);
    }

    void purposeSpinner() {
        purposeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, purposeList);
        purposeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        purposeSpinner.setAdapter(purposeAdapter);
        purposeSpinner.setOnItemSelectedListener(purposeClickListerner);

    }

    void propertyTySpinner() {
        propertyTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, propertyTypeList);
        propertyTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        propertyTySpinner.setAdapter(propertyTypeAdapter);
        propertyTySpinner.setOnItemSelectedListener(propertyClickListerner);
    }

    private AdapterView.OnItemSelectedListener purposeClickListerner = new AdapterView.OnItemSelectedListener() {


        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if (purposeSpinner.getSelectedItem().toString().equals("Residential")) {
                propertyTypeList = residentialList;
            }
            if (purposeSpinner.getSelectedItem().toString().equals("Commercial")) {
                propertyTypeList = commercialList;
            }
            propertyTySpinner();

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
    private AdapterView.OnItemSelectedListener propertyClickListerner = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            propertyType = String.valueOf(propertyTySpinner.getSelectedItem());

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
    private AdapterView.OnItemSelectedListener stateClickListerner = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            citySpinner();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
    private AdapterView.OnItemSelectedListener cityClickListerner = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            state = String.valueOf(stateSpinner.getSelectedItem());
            city = String.valueOf(citySpinner.getSelectedItem());
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    void addSpinnerString() {
        purposeList.add("Commercial");
        purposeList.add("Residential");
        residentialList.add("Single Room");
        residentialList.add("Self Contain");
        residentialList.add("Mini Flats");
        residentialList.add("1 Bed Room Flats");
        residentialList.add("2 Bed Room Flats");
        residentialList.add("3 Bed Room Flats");
        residentialList.add("4 Bed Room Flats");
        residentialList.add("Bungalow");
        residentialList.add("Duplex");
        residentialList.add("Estate Block");
        residentialList.add("Land");
        commercialList.add("Office Space");
        commercialList.add("Co Working Space");
        commercialList.add("Shop");
        commercialList.add("Ware House");
        commercialList.add("Cemetery");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("name", agentName);
        outState.putString("agentDp", agentDp);
        outState.putString("price", price);
        outState.putString("state", state);
        outState.putString("city", city);
        outState.putString("plotNo", plotNo);
        outState.putString("suitableFor", suitableFor);
        outState.putString("propertyType", propertyType);
        outState.putString("letType", letType);
        outState.putString("addInfo", additionalInfo);
        super.onSaveInstanceState(outState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        agentName = savedInstanceState.getString("name");
        agentDp = savedInstanceState.getString("agentDp");
        price = savedInstanceState.getString("price");
        state = savedInstanceState.getString("state");
        city = savedInstanceState.getString("city");
        plotNo = savedInstanceState.getString("plotNo");
        suitableFor = savedInstanceState.getString("suitableFor");
        propertyType = savedInstanceState.getString("propertyType");
        letType = savedInstanceState.getString("letType");
        additionalInfo = savedInstanceState.getString("addInfo");

    }


    private long getTime() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp.getTime();

    }

    public void addBtn1(View view){
        plotNo = plotEditText.getText().toString();
        state = String.valueOf(stateSpinner.getSelectedItem());
        city = String.valueOf(citySpinner.getSelectedItem());
        if (TextUtils.isEmpty(state)) {
            Toast.makeText(getApplicationContext(), "Choose State Please", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(city)) {
            Toast.makeText(getApplicationContext(), "Choose City Please", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(plotNo)) {
            Toast.makeText(getApplicationContext(), "Please enter roomNo or PlotNo Please", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(propertyType)) {
            Toast.makeText(getApplicationContext(), "Choose Property Type Please", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            firstR.setVisibility(View.GONE);
            secondR.setVisibility(View.VISIBLE);
        }



    }
    public void addBtn2(View view){
        suitableFor = String.valueOf(suitableForSpinner.getSelectedItem());
        propertyType = String.valueOf(propertyTySpinner.getSelectedItem());
        price = priceEdit.getText().toString();
        letType = String.valueOf(letTypeSpinner.getSelectedItem());
        additionalInfo = addInfo.getText().toString();
        if (TextUtils.isEmpty(price)) {
            Toast.makeText(getApplicationContext(), "Choose Price Please", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(suitableFor)|| suitableFor.equals("Suitable For")){
            Toast.makeText(getApplicationContext(), "Choose Suitable For Please", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(letType)) {
            Toast.makeText(getApplicationContext(), "Choose Let Type Please", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(additionalInfo)) {
            Toast.makeText(getApplicationContext(), "Additional Info Should not be blank", Toast.LENGTH_SHORT).show();
            return;
        }else {
            secondR.setVisibility(View.GONE);
            thirdR.setVisibility(View.VISIBLE);
        }



    }

    void showDialog(Uri uri){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog alertDialog;
        View view = getLayoutInflater().inflate(R.layout.image_dialog,null);
        ImageView img = view.findViewById(R.id.image_dialog_image);
        Button ok = view.findViewById(R.id.image_dialog_ok);
        Button nok = view.findViewById(R.id.image_dialog_nok);
        Glide.with(img.getContext())
                .load(uri)
                .into(img);
        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgCounter = imgCounter + 1;
                switch (imgCounter){
                    case 1:
                        Log.d("imagecounter","Image counter is:  " +uri.toString());
                        propertyImage1 = getFilePath.getPath(getApplicationContext(),uri);
                       // propertyImage1 = getPath(uri);
                        Log.d("imagecounter","Image counter is:  " +propertyImage1);
                        break;
                    case 2:
                        Log.d("imagecounter","Image counter is:  " +uri.toString());
                        propertyImage2 = getFilePath.getPath(getApplicationContext(),uri);
                        Log.d("imagecounter","Image counter is:  " +propertyImage2);
                        break;
                    case 3:
                        Log.d("imagecounter","Image counter is:  " +uri.toString());
                        propertyImage3 = getFilePath.getPath(getApplicationContext(),uri);
                        Log.d("imagecounter","Image counter is:  " +propertyImage3);
                        break;

                }
                alertDialog.dismiss();
                displayImages();
            }
        });

        nok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });


    }
    void displayImages(){
        Glide.with(image1.getContext())
                .load(propertyImage1)
                .apply(RequestOptions.placeholderOf(R.drawable.addhome))
                .into(image1);
        Glide.with(image2.getContext())
                .load(propertyImage2)
                .apply(RequestOptions.placeholderOf(R.drawable.addhome))
                .into(image2);
        Glide.with(image3.getContext())
                .load(propertyImage3)
                .apply(RequestOptions.placeholderOf(R.drawable.addhome))
                .into(image3);
    }
    void sendToGetImage(){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = df.format(Calendar.getInstance().getTime());
        String propId = getTime() + userId;
        String propertyPurpose = purposeSpinner.getSelectedItem().toString();

        Log.d("propimage","image is :"+propertyImage1);
        Property property = new Property(agentName, agentDp, propertyImage1, propertyImage2, propertyImage3, price, state, city, plotNo, suitableFor, propertyType, propertyPurpose, letType, additionalInfo, userId, currentDate, propId);
        imageInFireStore.saveImage(property);
        finish();
    }


//    public String getPath(Uri uri){
//        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
//
//        cursor.moveToFirst();
//        String document_id = cursor.getString(0);
//        document_id = document_id.substring(document_id.lastIndexOf(":")+1);
//        cursor.close();
//        cursor = getContentResolver().query(
//                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
//        cursor.moveToFirst();
//        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
//        if(cursor.moveToNext()){
//            cursor.close();
//        }
//
//
//        return path;
//    }

}
