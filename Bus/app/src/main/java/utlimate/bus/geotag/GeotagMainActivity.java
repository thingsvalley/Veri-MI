package utlimate.bus.geotag;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import utlimate.bus.R;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;
import static utlimate.bus.MainActivity.isInGeoFence;

public class GeotagMainActivity extends AppCompatActivity implements LocationListener {
    final String TAG = "GPS";
    private final static int ALL_PERMISSIONS_RESULT = 101;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;

    ProgressBar progressBar;
    ArrayList<GeotaggingDataModel> imageArray=new ArrayList<>();
    // Activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;
    // directory name to store captured images and videos
    private static final String IMAGE_DIRECTORY_NAME = "Hello Camera";

    boolean isFormData=false;
    private Uri fileUri; // file url to store image/video

    TextView tvLatitude, tvLongitude, tvTime,mainLineTxt,sublineTxt;
    ImageButton btn1,btn2,btn3;
    Button uploadBtn,doneBtn;
    GridView gridView1,gridView2,gridView3;
    LocationManager locationManager;
    Location loc;
    ArrayList<String> permissions = new ArrayList<>();
    ArrayList<String> permissionsToRequest;
    ArrayList<String> permissionsRejected = new ArrayList<>();
    boolean isGPS = false;
    boolean isNetwork = false;
    boolean canGetLocation = true;
    Bitmap bitmap;

    ArrayList<GeotaggingDataModel> mainLineList=new ArrayList<>();
    ArrayList<GeotaggingDataModel> subLineList=new ArrayList<>();
    ArrayList<GeotaggingDataModel> otherList=new ArrayList<>();
    public static boolean isImageUploaded=false;
    boolean check = true;

    Button SelectImageGallery, UploadImageServer;

    ProgressDialog progressDialog ;

    String GetImageNameEditText;
    String mims="";
    double lat,lon;

//    String ImageName = "image_name" ;

    String ImagePath = "image_path" ;

    String img="1";
    String ServerUploadPath ="https://afshanshk17.000webhostapp.com/geotag.php" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.geotag_main);

        isFormData = getIntent().getBooleanExtra("isFormData",false);

        tvLatitude = (TextView) findViewById(R.id.tvLatitude);
        tvLongitude = (TextView) findViewById(R.id.tvLongitude);
        mainLineTxt = (TextView) findViewById(R.id.mainLinePipe);
        sublineTxt = (TextView) findViewById(R.id.sublinePipe);
        tvTime = (TextView) findViewById(R.id.tvTime);
        btn1=(ImageButton)findViewById(R.id.imageButton1);
        btn2=(ImageButton)findViewById(R.id.imageButton2);
        btn3=(ImageButton)findViewById(R.id.imageButton3);
        progressBar= findViewById(R.id.progressbar);
//        imageView = findViewById(R.id.image);
        uploadBtn = findViewById(R.id.uploadBtn);
        doneBtn = findViewById(R.id.doneBtn);
        gridView1 = (GridView) findViewById(R.id.gridView1);
        gridView2 = (GridView) findViewById(R.id.gridView2);
        gridView3 = (GridView) findViewById(R.id.gridView3);

         mims=getIntent().getStringExtra("mims");
        locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
        isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissions.add(Manifest.permission.CAMERA);
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissionsToRequest = findUnAskedPermissions(permissions);

        if (!isGPS && !isNetwork) {
            Log.d(TAG, "Connection off");
            showSettingsAlert();
            getLastLocation();
        } else {
            Log.d(TAG, "Connection on");
            // check permissions
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (permissionsToRequest.size() > 0) {
                    requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]),
                            ALL_PERMISSIONS_RESULT);
                    Log.d(TAG, "Permission requests");
                    canGetLocation = false;
                }
            }

            // get location
            getLocation();
        }
        btn1.setOnClickListener(new OnClickListener() {
            public void onClick(View v){


//                    if (isInGeoFence) {
                        if (mainLineList.size()<2) {
                            img = "1";
                            captureImage();
                        }else {
                            Toast.makeText(GeotagMainActivity.this, "Maximum  2 images.", Toast.LENGTH_LONG).show();

                        }
//                    } else {
//                        Toast.makeText(GeotagMainActivity.this, "You are outside of Geofence.", Toast.LENGTH_LONG).show();
//                    }

//                Intent myintent= new Intent(GeoTaggingActivity.this,CameraMainActivity.class);
//                startActivity(myintent);
            }
        });
        btn2.setOnClickListener(new OnClickListener() {
            public void onClick(View v){


//                if (isInGeoFence) {
                    if (subLineList.size()<2) {
                        img = "2";
                        captureImage();
                    }else {
                        Toast.makeText(GeotagMainActivity.this, "Maximum  2 images.", Toast.LENGTH_LONG).show();

                    }
//                } else {
//                    Toast.makeText(GeotagMainActivity.this, "You are outside of Geofence.", Toast.LENGTH_LONG).show();
//                }

//                Intent myintent= new Intent(GeoTaggingActivity.this,CameraMainActivity.class);
//                startActivity(myintent);
            }
        });
        btn3.setOnClickListener(new OnClickListener() {
            public void onClick(View v){


//                if (isInGeoFence) {
                    img="3";
                    captureImage();
//                } else {
//                    Toast.makeText(GeotagMainActivity.this, "You are outside of Geofence.", Toast.LENGTH_LONG).show();
//                }

//                Intent myintent= new Intent(GeoTaggingActivity.this,CameraMainActivity.class);
//                startActivity(myintent);
            }
        });
        uploadBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                // get values from imageArray
                for (int i=0;i<imageArray.size();i++)
                {
                    GeotaggingDataModel geotaggingDataModel=imageArray.get(i);
                    Bitmap bitmap=geotaggingDataModel.getImage();

                    if(bitmap!=null || !geotaggingDataModel.getLatitude().equals("") || !geotaggingDataModel.getLongitude().equals("")) {
                        lat= Double.parseDouble(geotaggingDataModel.getLatitude());
                        lon = Double.parseDouble(geotaggingDataModel.getLongitude());

                        ImageUploadToServerFunction(bitmap, lat, lon);
                    }else {
                        Toast.makeText(GeotagMainActivity.this,"Please add image.",Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });
        doneBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    /*
     * Capturing Camera Image will lauch camera app requrest image capture
     */
    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        if(Build.VERSION.SDK_INT>=24){
            try{
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    /**
     * Receiving activity result method will be called after closing the camera
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // successfully captured the image
                // display it in image view
                previewCapturedImage();
            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
    /*
     * Display image from a path to ImageView
     */
    private void previewCapturedImage() {
        try {


            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;

            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                    options);

//            imageView.setImageBitmap(bitmap)     ;

            GeotaggingDataModel geotaggingDataModel=new GeotaggingDataModel();
            geotaggingDataModel.setLatitude(tvLatitude.getText().toString());
            geotaggingDataModel.setLongitude(tvLongitude.getText().toString());
            geotaggingDataModel.setTime(tvTime.getText().toString());
            geotaggingDataModel.setImage(bitmap);

            imageArray.add(geotaggingDataModel);

            if (img.equals("1")) {
                setImagesToGrid1();
                mainLineList.add(geotaggingDataModel);
                if (mainLineList.size()==2) {
                    float[] results = new float[1];
                    Location.distanceBetween(30.6863625,76.6644293,30.6864565,76.6642455, results);
                    mainLineTxt.setText("Length of pipline :"+String.valueOf(results[0]));
                }
            }
            if (img.equals("2")) {
                setImagesToGrid2();
                subLineList.add(geotaggingDataModel);
                if (subLineList.size()==2) {
                    float[] results = new float[1];
                    Location.distanceBetween(30.686411,76.6642911,30.6863132,76.6644028, results);
                    sublineTxt.setText("Length of pipline :"+String.valueOf(results[0]));
                }
            }
            if (img.equals("3")) {
                setImagesToGrid3();
                otherList.add(geotaggingDataModel);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void setImagesToGrid3() {
        GridImageAdapter recyclerView_Adapter=new GridImageAdapter(this,otherList);
        gridView3.setAdapter(recyclerView_Adapter);
    }

    private void setImagesToGrid1() {
        GridImageAdapter recyclerView_Adapter=new GridImageAdapter(this,mainLineList);
        gridView1.setAdapter(recyclerView_Adapter);
    }

    private void setImagesToGrid2() {
        GridImageAdapter recyclerView_Adapter=new GridImageAdapter(this,subLineList);
        gridView2.setAdapter(recyclerView_Adapter);
    }

    private void setImagesToGrid() {

    }
    //@Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged");
        updateUI(location);
    }

    //@Override
    public void onStatusChanged(String s, int i, Bundle bundle) {}

    //@Override
    public void onProviderEnabled(String s) {
        getLocation();
    }

    //@Override
    public void onProviderDisabled(String s) {
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }

    private void getLocation() {
        try {
            if (canGetLocation) {
                Log.d(TAG, "Can get location");
                if (isGPS) {
                    // from GPS
                    Log.d(TAG, "GPS on");
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, (LocationListener) this);

                    if (locationManager != null) {
                        loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (loc != null)
                            updateUI(loc);
                    }
                } else if (isNetwork) {
                    // from Network Provider
                    Log.d(TAG, "NETWORK_PROVIDER on");
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, (LocationListener) this);

                    if (locationManager != null) {
                        loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (loc != null)
                            updateUI(loc);
                    }
                } else {
                    loc.setLatitude(0);
                    loc.setLongitude(0);
                    updateUI(loc);
                }
            } else {
                Log.d(TAG, "Can't get location");
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void getLastLocation() {
        try {
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, false);
            Location location = locationManager.getLastKnownLocation(provider);
            Log.d(TAG, provider);
            Log.d(TAG, location == null ? "NO LastLocation" : location.toString());
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private ArrayList findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList result = new ArrayList();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canAskPermission()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canAskPermission() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }




    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case ALL_PERMISSIONS_RESULT:
                Log.d(TAG, "onRequestPermissionsResult");
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }
                } else {
                    Log.d(TAG, "No rejected permissions.");
                    canGetLocation = true;
                    getLocation();
                }
                break;
        }
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("GPS is not Enabled!");
        alertDialog.setMessage("Do you want to turn on GPS?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(GeotagMainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void updateUI(Location loc) {
        Log.d(TAG, "updateUI");
        tvLatitude.setText(Double.toString(loc.getLatitude()));
        tvLongitude.setText(Double.toString(loc.getLongitude()));
        tvTime.setText(DateFormat.getTimeInstance().format(loc.getTime()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            locationManager.removeUpdates((LocationListener) this);
        }
    }
    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * returning image / video
     */
    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    public void ImageUploadToServerFunction(Bitmap bitmap, final double lat, final double lon){

        ByteArrayOutputStream byteArrayOutputStreamObject ;

        byteArrayOutputStreamObject = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObject);

        byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();

        final String ConvertImage = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);

        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();
                progressBar.setVisibility(View.VISIBLE);
                }

            @Override
            protected void onPostExecute(String string1) {

                super.onPostExecute(string1);

                // Dismiss the progress dialog after done uploading.

                progressBar.setVisibility(View.GONE);
                // Printing uploading success message coming from server on android app.
                Toast.makeText(GeotagMainActivity.this,string1, Toast.LENGTH_LONG).show();
                isImageUploaded=true;
//

                // Setting image as transparent after done uploading.
//                imageView.setImageResource(android.R.color.transparent);


            }

            @Override
            protected String doInBackground(Void... params) {

                ImageProcessClass imageProcessClass = new ImageProcessClass();

                HashMap<Object,Object> HashMapParams = new HashMap<Object,Object>();


                HashMapParams.put("lat", lat);
                HashMapParams.put("lon", lon);
                HashMapParams.put("mims", mims);
//                HashMapParams.put("path_to_img", ConvertImage);

                HashMapParams.put(ImagePath, ConvertImage);


                String FinalData = imageProcessClass.ImageHttpRequest(ServerUploadPath, HashMapParams);

                return FinalData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();

        AsyncTaskUploadClassOBJ.execute();
    }

    public class ImageProcessClass{

        public String ImageHttpRequest(String requestURL, HashMap<Object, Object> PData) {

            StringBuilder stringBuilder = new StringBuilder();

            try {

                URL url;
                HttpURLConnection httpURLConnectionObject ;
                OutputStream OutPutStream;
                BufferedWriter bufferedWriterObject ;
                BufferedReader bufferedReaderObject ;
                int RC ;

                url = new URL(requestURL);

                httpURLConnectionObject = (HttpURLConnection) url.openConnection();

                httpURLConnectionObject.setReadTimeout(19000);

                httpURLConnectionObject.setConnectTimeout(19000);

                httpURLConnectionObject.setRequestMethod("POST");

                httpURLConnectionObject.setDoInput(true);

                httpURLConnectionObject.setDoOutput(true);

                OutPutStream = httpURLConnectionObject.getOutputStream();

                bufferedWriterObject = new BufferedWriter(

                        new OutputStreamWriter(OutPutStream, "UTF-8"));

                bufferedWriterObject.write(bufferedWriterDataFN(PData));

                bufferedWriterObject.flush();

                bufferedWriterObject.close();

                OutPutStream.close();

                RC = httpURLConnectionObject.getResponseCode();

                if (RC == HttpsURLConnection.HTTP_OK) {

                    bufferedReaderObject = new BufferedReader(new InputStreamReader(httpURLConnectionObject.getInputStream()));

                    stringBuilder = new StringBuilder();

                    String RC2;

                    while ((RC2 = bufferedReaderObject.readLine()) != null){

                        stringBuilder.append(RC2);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }


        private String bufferedWriterDataFN(HashMap<Object, Object> HashMapParams) throws UnsupportedEncodingException {

            StringBuilder stringBuilderObject;

            stringBuilderObject = new StringBuilder();

            for (Map.Entry<Object, Object> KEY : HashMapParams.entrySet()) {

                if (check)

                    check = false;
                else
                    stringBuilderObject.append("&");

                stringBuilderObject.append(URLEncoder.encode(String.valueOf(KEY.getKey()), "UTF-8"));

                stringBuilderObject.append("=");

                stringBuilderObject.append(URLEncoder.encode(String.valueOf(KEY.getValue()), "UTF-8"));
            }

            return stringBuilderObject.toString();
        }

    }
}



