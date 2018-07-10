package comfizztheturtle.httpsgithub.liveweatherwallpaper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.theartofdev.edmodo.cropper.CropImage;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static com.theartofdev.edmodo.cropper.CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE;
import static comfizztheturtle.httpsgithub.liveweatherwallpaper.R.*;


public class tab1_sunny extends Fragment {

    int sunny_weather_id = 0;
    int PICK_IMAGE_REQUEST = 1;
    private View image;
    private Bitmap selectedImage;
    private Uri m_image_Uri;
    public MyDBHandler dbHandler;


    ImageView new_image_device;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here
             try {
                        main(R.raw.api_key);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

        }



        dbHandler = new MyDBHandler(this.getContext());
        View v = inflater.inflate(layout.tab1_sunny, container, false);
        image=v;

        new_image_device =image.findViewById(id.image_device);

        if (selectedImage != null) {

                         new_image_device.setImageBitmap(selectedImage);
        }

        else if (dbHandler.find_ID(sunny_weather_id) != null && selectedImage == null) {

            image_class result=dbHandler.find_ID(sunny_weather_id);
            Uri ltf = Uri.parse(result.get_link_to_file());

            Log.e("My App",result.get_link_to_file() );
            BitmapFactory.Options options = new BitmapFactory.Options();
            int uri_width= getIMGwidth(m_image_Uri);
            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            Log.e("MyApp", Integer.toString(uri_width));
            // downsizing image as it throws OutOfMemory Exception for larger images
            if (uri_width>=2560) {
                options.inSampleSize = 8;
            }
            else {
                options.inSampleSize=2;
            }
            selectedImage = BitmapFactory.decodeFile(ltf.getPath(),options);
            new_image_device.setImageBitmap(selectedImage);
        }

        Button get_image_button = v.findViewById(id.get_image_button);
        get_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent intent = new Intent();

            // Show only images, no videos or anything else
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);

            // Always show the chooser (if there are multiple options available)
                  startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

                }catch(Exception exp){
                    Log.e("Error",exp.toString());
                }
            }
    });
        Button set_sunny_wallpaper_button = v.findViewById(id.set_sunny_wallpaper);
        set_sunny_wallpaper_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if (m_image_Uri != null) {
//                    Intent intent2 = new Intent(WallpaperManager.ACTION_CROP_AND_SET_WALLPAPER);
//                    String mime = "image/*";


//                         Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
                         Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
//                        intent.setAction(WallpaperManager.ACTION_CROP_AND_SET_WALLPAPER);
                         intent.addCategory(Intent.CATEGORY_DEFAULT);
                         intent.setDataAndType(m_image_Uri, "image/*");
                         intent.putExtra("mimeType", "image/*");
                         try {
                             Objects.requireNonNull(getActivity()).startActivity(Intent.createChooser(intent, "Set as:"));
//                        intent2.setDataAndType(mSelectedImageUri, mime);

//                            startActivity(intent2);
                        } catch (Exception e) {
                            //handle error
                            Snackbar.make(image, "No image found", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();

                        }
                    }
                    else {
                        Snackbar.make(image, "No image found in image view", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }

                }catch(Exception exp){
                    Log.i("Error",exp.toString());
                }
            }
        });
        return v;


    }

//sunny=id_0

//etc...


//When removing, replaces current image for that id

//when entering, replaces current default image for id

    @Override
    @SuppressLint("NewApi")
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == Activity.RESULT_CANCELED) {
            // code to handle cancelled state
            Log.e("MyApp","I have failed");
        }
        else if (requestCode == CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(intent);

            if (resultCode == RESULT_OK) {
                try {

                    m_image_Uri = result.getUri();
                    // bimatp factory
                    BitmapFactory.Options options = new BitmapFactory.Options();

                    int uri_width= getIMGwidth(m_image_Uri);
                    int uri_height= getIMGheight(m_image_Uri);

                    Log.e("MyApp", Integer.toString(uri_width));
                    Log.e("MyApp", Integer.toString(uri_height));
                    // downsizing image as it throws OutOfMemory Exception for larger
                    // images
                    if (uri_width>=2560) {
                        options.inSampleSize = 8;
                    }
                    else {
                        options.inSampleSize=2;
                    }
                    selectedImage = BitmapFactory.decodeFile(m_image_Uri.getPath(),options);
                if(m_image_Uri == null)
                {
                    add_weather_image();
                }
                else{
                    update_weather_image(sunny_weather_id,m_image_Uri.toString());
                }

                    new_image_device.setImageBitmap(selectedImage);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("MyApp","I have failed");

            }
            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.e("MyApp",error.toString());
            }
        }
        else if (requestCode == PICK_IMAGE_REQUEST) {

                m_image_Uri  = intent.getData();
            startCropImageActivity(m_image_Uri);


//                try {
//                Bitmap mBitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), mSelectedImageUri);
////            Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.id.image_device);
//            WallpaperManager myWallpaperManager = WallpaperManager
//                    .getInstance(getActivity().getApplicationContext());
//
//                myWallpaperManager.setBitmap(mBitmap);
//                Snackbar.make(image, "Image set as wallpaper", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            } catch (IOException e) {
//                Snackbar.make(image, "error setting wallpaper", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
        }



    }

    private void startCropImageActivity(Uri imageUri) {
        Intent intent = CropImage.activity(imageUri)
                .getIntent(Objects.requireNonNull(getContext()));
        startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);


    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE) {
            if (m_image_Uri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // required permissions granted, start crop image activity
                startCropImageActivity(m_image_Uri);
            } else {
                Snackbar.make(image, "Permission required", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }
    }

//    public Bitmap decodeSampledBitmapFromByte(byte[] res,
//                                              int reqWidth, int reqHeight) {
//
//        // First decode with inJustDecodeBounds=true to check dimensions
//        final BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeByteArray(res, 0, res.length,options);
//
//        // Calculate inSampleSize
//        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
//
//        // Decode bitmap with inSampleSize set
//        options.inJustDecodeBounds = false;
//        return BitmapFactory.decodeByteArray(res, 0, res.length,options);
//    }


//    public Bitmap scale_image(Uri resultUri) throws Exception {
//        //Scale image
//        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
////        int scaleFactor=CalculateInSampleSize(bitmapOptions, 368, 253);
////        bitmapOptions.inSampleSize = scaleFactor;
////        InputStream inputStream = getContext().getApplicationContext().getContentResolver().openInputStream(resultUri);
////        Bitmap scaledBitmap= getResizedBitmap();
////        return scaledBitmap;
//
//    }



    private int getIMGwidth(Uri uri){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(new File(uri.getPath()).getAbsolutePath(), options);

        return options.outWidth;

    }
    private int getIMGheight(Uri uri){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(new File(uri.getPath()).getAbsolutePath(), options);

        return options.outHeight;

    }


    //need one for update,remove,delete... everything
    public void add_weather_image() {

       if(m_image_Uri != null) {

           int id = sunny_weather_id;
           String link_to_file =(m_image_Uri.toString());
//        Uri ltf = Uri.parse(link_to_file);
           image_class weather_image = new image_class(id, link_to_file);
           dbHandler.addHandler(weather_image);
           String result=dbHandler.loadHandler();
           Log.e("MyApp",result);

       }
    }



    public void update_weather_image(int ID, String ltf) {


        if(ltf!=null) {
            dbHandler.update_ltf(ID, ltf);
        }
            String result=dbHandler.loadHandler();
            Log.e("MyApp",result);

    }

    public void delete_weather_image(int ID){

            dbHandler.deleteHandler(ID);

        String result=dbHandler.loadHandler();
        Log.e("MyApp",result);
    }

//    private void reloadWallpaper(Bitmap bm){
//        if(bm != null){
//            WallpaperManager myWallpaperManager =
//                    WallpaperManager.getInstance(getApplicationContext());
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                if(myWallpaperManager.isWallpaperSupported()){
//                    try {
//                        myWallpaperManager.setBitmap(bm);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }else{
//                    Toast.makeText(MainActivity.this,
//                            "isWallpaperSupported() NOT SUPPORTED",
//                            Toast.LENGTH_LONG).show();
//                }
//            }else{
//                try {
//                    myWallpaperManager.setBitmap(bm);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }else{
//            Toast.makeText(MainActivity.this, "bm == null", Toast.LENGTH_LONG).show();
//        }
//    }

// onActivityResult
//        else  if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
//            Uri imageUri = CropImage.getPickImageResultUri(getContext(), intent);
//         // no permissions required or already granted, can start crop image activity
//            // For API >= 23 we need to check specifically that we have permissions to read external storage.
//                if (CropImage.isReadExternalStoragePermissionsRequired(getContext(), imageUri)) {
//                    // request permissions and handle the result in onRequestPermissionsResult()
//                    mSelectedImageUri = imageUri;
//                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
//                } else {
//
//                    ImageView new_image_device =image.findViewById(R.id.image_device);
//                    new_image_device.setImageURI(mSelectedImageUri );
//
//                 }
//            mSelectedImageUri  = intent.getData();


//        else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK) {
//                Uri resultUri = result.getUri();
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Exception error = result.getError();
//            }
//        }














































    // write your code here
public  void main(int resourceId) throws Exception {

//    getResources().openRawResource(R.raw.api_key);
//    URL url =  Find_Data.class.getResource("raw/api_key.txt");
//        System.out.println(getResources().openRawResource(R.raw.api_key));
//    String API_Key= Paths.get(url.toURI()).toFile().toString();


//    String contents =new String(Files.readAllBytes(Paths.get(API_Key)));
//        String contents = BuildConfig.ApiKey;
//

//        String link ="C:/AndroidApplications/Weather_0.1/MetOfficeList/weather_3hours.json";
//
//        URL url_temp =  Find_Data.class.getResource("weather_3hours.json");
//        String link =Paths.get(url.toURI()).toFile().toString();


//    File file = new File("assets/QuotesMonkeyBusiness.txt");
//    InputStream is = this.getResources().openRawResource(resourceId);
//    BufferedReader br = new BufferedReader(new InputStreamReader(is));
    String readLine = null;
//
//
//
//    try {
//        // While the BufferedReader readLine is not null
//        while ((readLine = br.readLine()) != null) {
//            Log.d("TEXT", readLine);
//        }
//
//        // Close the InputStream and BufferedReader
//        is.close();
//        br.close();
//
//    } catch (IOException e) {
//        e.printStackTrace();
//    }




    try {
        Resources res = getResources();
        InputStream in_s = res.openRawResource(resourceId);

        byte[] b = new byte[in_s.available()];
        in_s.read(b);
        readLine=new String(b);
    } catch (Exception e) {
         e.printStackTrace();
//        txtHelp.setText("Error: can't show help.");
    }









    // Make a URL to the web page
    String url_API = "http://datapoint.metoffice.gov.uk/public/data/val/wxfcs/all/json/310013?res=3hourly&key="+readLine;

    JSONObject jsonObject = readJsonFromUrl(url_API);

    JSONObject SiteRep = (JSONObject) jsonObject.get("SiteRep");
        System.out.println("SiteRep: " + SiteRep);
    JSONObject dv = (JSONObject) SiteRep.get("DV");
        System.out.println("DV: " + dv);

    JSONObject location = (JSONObject) dv.get("Location");
        System.out.println("location: " + location);


    JSONArray Period = (JSONArray) location.get("Period");



        for(Object periodObj: Period.toArray()){
        JSONObject period_2 = (JSONObject)periodObj;
        JSONArray Rep = (JSONArray) period_2.get("Rep");
        System.out.println("\tPeriod: " + periodObj);
        for(Object repObj: Rep.toArray()){
            JSONObject rep_2 = (JSONObject) repObj;
            System.out.println("\t\t rep: " + rep_2);
            String weather_type = (String) rep_2.get("W");
            System.out.println("\t\t\t weather_type: " + weather_type);
            String $_temp = (String) rep_2.get("$");
            int $= Integer.parseInt($_temp);
            String myTime = "00:00";
            @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("HH:mm");
            Date d = df.parse(myTime);
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            cal.add(Calendar.MINUTE, $);
            String newTime = df.format(cal.getTime());
            System.out.println("\t\t\t $_temp: " + newTime);
            //do something with the issue
        }

    }
}

    public static JSONObject readJsonFromUrl(String url) throws Exception {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONParser jsonParser = new JSONParser();
            Object object ;
            object = jsonParser.parse(jsonText);


            return (JSONObject) object;
        } finally {
            is.close();
        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
    public String ReadFromfile(String fileName, Context context) {
        StringBuilder returnString = new StringBuilder();
        InputStream fIn = null;
        InputStreamReader isr = null;
        BufferedReader input = null;
        try {
            fIn = context.getResources().getAssets()
                    .open(fileName, Context.MODE_WORLD_READABLE);
            isr = new InputStreamReader(fIn);
            input = new BufferedReader(isr);
            String line = "";
            while ((line = input.readLine()) != null) {
                returnString.append(line);
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            try {
                if (isr != null)
                    isr.close();
                if (fIn != null)
                    fIn.close();
                if (input != null)
                    input.close();
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
        return returnString.toString();
    }
























}