package com.example.willthespoderman.babynurtures.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.willthespoderman.babynurtures.R;
import com.example.willthespoderman.babynurtures.app.AppController;
import com.example.willthespoderman.babynurtures.helper.SessionManager;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class profile extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = profile.class.getSimpleName();

    private static String http = "http://";
    private static  String ip = "172.31.11.32:80";
    private String picturedirectory=http+ip+"/android_login_api/";
    private static final String SERVER_ADDRESS =  http+ip+"/android_login_api/Savepictures.php";
    private Uri selectedImage;
    private String imagepath=null;
    private int serverResponseCode = 0;
    ImageView viewImage;
    private static  final int RESULT_LOAD_IMAGE = 1;
    private ProgressDialog dialog = null;
    private ProgressDialog pDialog;
    private NetworkImageView thumbNail;
    Button btnUploadPhoto;
    Button btnUpdateProfile;
    private String babyId;
    TextView setName, setCountry, setCity, setBirthday,setNationality,setGender, setHeight, setWeight;
    private SessionManager session;
    private static int request = 2;

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnUploadPhoto = (Button) findViewById(R.id.btnUploadPhoto);
        btnUploadPhoto.setOnClickListener(this);

        btnUpdateProfile = (Button) findViewById(R.id.btnUpadteProfile);
        btnUpdateProfile.setOnClickListener(this);


        setCity = (TextView) findViewById(R.id.setCity);
        setCountry = (TextView) findViewById(R.id.setCountry);
        setBirthday = (TextView) findViewById(R.id.setBirthday);
        setNationality = (TextView) findViewById(R.id.setNationality);
        setHeight = (TextView) findViewById(R.id.setHeight);
        setName = (TextView) findViewById(R.id.setName);
        setWeight = (TextView) findViewById(R.id.setWeight);
        setGender = (TextView) findViewById(R.id.setGender);
        imageLoader = AppController.getInstance().getImageLoader();

        thumbNail = (NetworkImageView) findViewById(R.id.thumbnails);

        session = new SessionManager(getApplicationContext());
        SharedPreferences sharedPreferences = getSharedPreferences("BabyLogin", Context.MODE_PRIVATE);
        String type = sharedPreferences.getString("keyType", "");
        // Check if user is already logged in or not
        if (!session.isLoggedIn() && type.equals("0")) {
            logoutUser();
        } else {

            String dp;
            dp = getIntent().getExtras().getString("keyProfilePic");
            thumbNail.setImageUrl(dp, imageLoader);
            babyId = getIntent().getExtras().getString("babyId");
            setName.setText(getIntent().getExtras().getString("keyFullname"));
            setBirthday.setText(getIntent().getExtras().getString("keyBday"));
            setHeight.setText(getIntent().getExtras().getString("keyHeight"));
            setWeight.setText(getIntent().getExtras().getString("keyWeight"));
            setNationality.setText(getIntent().getExtras().getString("keyNationality"));
            setGender.setText(getIntent().getExtras().getString("keyGender"));
            setCountry.setText(getIntent().getExtras().getString("keyCountry"));
            setCity.setText(getIntent().getExtras().getString("keyCity"));

        }
    }


    private void logoutUser() {
        session.setLogin(false,"", "","","");


        // Launching the login activity
        Intent intent = new Intent(profile.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds itq
        // ems to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK && data != null){
            //  imgView2.setVisibility(View.GONE);
            //  thumbNail.setVisibility(View.VISIBLE);
            selectedImage = data.getData();
            thumbNail.setImageURI(selectedImage);
           /* Bitmap bitmap=BitmapFactory.decodeFile(imagepath);
            imgView2.setImageBitmap(bitmap);*/

            imagepath = getRealPathFromURI(selectedImage);

            dialog = ProgressDialog.show(profile.this, "", "Updating Picture...", true);
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {

                            new Thread(new Runnable() {
                                public void run() {

                                   uploadFile(imagepath);

                                }

                            }).start();

                            dialog.dismiss();

                        }
                    }, 3000);

//        }  else if (requestCode == request) {
//            if (resultCode == Activity.RESULT_OK) {
//                //Intent i = getIntent();
////                Bundle res = data.getExtras();
//
//                setName.setText(data.getStringExtra("fname"));
//                Log.e("Res", data.getStringExtra("res"));
//                //finish();
//                //startActivity(i);
//
//            }
        }
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.btnUploadPhoto:
                Intent gallaryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallaryIntent, RESULT_LOAD_IMAGE);
                break;

            case R.id.btnUpadteProfile:
                Intent i = new Intent(this,EditProfile.class);
                i.putExtra("keyFullname", (getIntent().getExtras().getString("keyFullname")));
                i.putExtra("keyBday", getIntent().getExtras().getString("keyBday"));
                i.putExtra("keyHeight", getIntent().getExtras().getString("keyHeight"));
                i.putExtra("keyNationality", getIntent().getExtras().getString("keyNationality"));
                i.putExtra("keyWeight", getIntent().getExtras().getString("keyWeight"));
                i.putExtra("keyId", getIntent().getExtras().getString("keyId"));
                i.putExtra("keyGender", getIntent().getExtras().getString("keyGender"));
                i.putExtra("keyCity", getIntent().getExtras().getString("keyCity"));
                i.putExtra("keyCountry", getIntent().getExtras().getString("keyCountry"));
//                startActivityForResult(i, request);
                startActivity(i);
                break;
        }
    }



    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor =getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    public int uploadFile(String sourceFileUri) {
        String upLoadServerUri = SERVER_ADDRESS;
        String fileName = sourceFileUri;

        HttpURLConnection conn = null;
        InputStream isr = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        if (!sourceFile.isFile()) {
            Log.e("uploadFile", "Source File Does not exist");
            return 0;
        }
        try { // open a URL connection to the Servlet
            FileInputStream fileInputStream = new FileInputStream(sourceFile);
            URL url = new URL(upLoadServerUri);
            conn = (HttpURLConnection) url.openConnection(); // Open a HTTP  connection to  the URL
            conn.setDoInput(true); // Allow Inputs
            conn.setDoOutput(true); // Allow Outputs
            conn.setUseCaches(false); // Don't use a Cached Copy
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            conn.setRequestProperty("uploaded_file", fileName);



            dos = new DataOutputStream(conn.getOutputStream());


            //add parameters..
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"babyId\"" + lineEnd);
            dos.writeBytes(lineEnd);
            // end add parameters..
            //start assign value..
            dos.writeBytes(babyId);
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            //end assign value..



            //add parameters..
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"directorypicture\"" + lineEnd);
            dos.writeBytes(lineEnd);
            // end add parameters..
            //start assign value..
            dos.writeBytes(picturedirectory);
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            //end assign value..



            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data;  name=\"uploaded_file\";filename=\""+ fileName + "\"" + lineEnd);
            dos.writeBytes(lineEnd);

            bytesAvailable = fileInputStream.available(); // create a buffer of  maximum size

            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            // send multipart form data necesssary after file data...
            // bufferedWriter.write(data);
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // Responses from the server (code and message)
            serverResponseCode = conn.getResponseCode();
            String serverResponseMessage = conn.getResponseMessage();

            Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);
            if(serverResponseCode == 200){
                runOnUiThread(new Runnable() {
                    public void run() {

                        Toast.makeText(getBaseContext(), "Pictures Updated Successfully... ", Toast.LENGTH_SHORT).show();

                    }
                });
            }

            //close the streams //
            fileInputStream.close();
          /*  bufferedWriter.flush();
            bufferedWriter.close();*/
            dos.flush();
            dos.close();
            isr = conn.getInputStream();

        } catch (MalformedURLException ex) {
            dialog.dismiss();
            ex.printStackTrace();
            //   Toast.makeText(UploadImageDemo.this, "MalformedURLException", Toast.LENGTH_SHORT).show();
            Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
        } catch (Exception e) {
            dialog.dismiss();
            e.printStackTrace();

        }


        return serverResponseCode;
    }

}
