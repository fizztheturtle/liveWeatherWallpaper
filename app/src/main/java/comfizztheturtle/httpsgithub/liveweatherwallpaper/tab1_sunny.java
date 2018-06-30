package comfizztheturtle.httpsgithub.liveweatherwallpaper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
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

import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;
import static com.theartofdev.edmodo.cropper.CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE;

//NEED TO ADD COMPRESS IMAGE FEATURE
public class tab1_sunny extends Fragment {

    int sunny_weather_id = 0;
    int PICK_IMAGE_REQUEST = 1;
    private View image;
    private Uri m_image_Uri;
    public MyDBHandler dbHandler;
    public static final String DATABASE_NAME = "studentDB.db";
    ImageView new_image_device;

//    add a method which makes the image at max 2k before being set in image view

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dbHandler = new MyDBHandler(this.getContext());
        View v = inflater.inflate(R.layout.tab1_sunny, container, false);
        image=v;

        new_image_device =image.findViewById(R.id.image_device);

        if (m_image_Uri != null) {
            new_image_device.setImageURI(m_image_Uri);
        }
        else if (dbHandler.find_ID(sunny_weather_id) != null && m_image_Uri == null) {
            image_class result=dbHandler.find_ID(sunny_weather_id);
            Uri ltf = Uri.parse(result.get_link_to_file());
            Log.e("My App",result.get_link_to_file() );
            m_image_Uri=ltf;
            new_image_device.setImageURI(m_image_Uri);
        }

        Button get_image_button = v.findViewById(R.id.get_image_button);
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
        Button set_sunny_wallpaper_button = v.findViewById(R.id.set_sunny_wallpaper);
        set_sunny_wallpaper_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if (m_image_Uri != null) {
//                    Intent intent2 = new Intent(WallpaperManager.ACTION_CROP_AND_SET_WALLPAPER);
//                    String mime = "image/*";


//                         Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
                         Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
//                         intent.setAction(WallpaperManager.ACTION_CROP_AND_SET_WALLPAPER);
                         intent.addCategory(Intent.CATEGORY_DEFAULT);
                         intent.setDataAndType(m_image_Uri, "image/*");
                         intent.putExtra("mimeType", "image/*");
                         try {
                             getActivity().startActivity(Intent.createChooser(intent, "Set as:"));
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
                Uri resultUri = result.getUri();

                //needs to be a scale factor of 2



                try {
//                    only scale if over 1080
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    int scaleFactor=CalculateInSampleSize(bitmapOptions, 368, 253);
                    bitmapOptions.inSampleSize = scaleFactor;
                    InputStream inputStream = getActivity().getApplicationContext().getContentResolver().openInputStream(resultUri);
                    Bitmap scaledBitmap = BitmapFactory.decodeStream(inputStream, null, bitmapOptions);


                    if(m_image_Uri == null)
                    {
                        m_image_Uri=resultUri;
                        ImageView new_image_device =image.findViewById(R.id.image_device);
                        new_image_device.setImageBitmap(scaledBitmap);
                        add_weather_image();
                    }
                    else{
                        m_image_Uri=resultUri;
                        ImageView new_image_device =image.findViewById(R.id.image_device);
                        new_image_device.setImageBitmap(scaledBitmap);
                        update_weather_image(sunny_weather_id,m_image_Uri.toString());

                    }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.e("MyApp","I have failed");
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
                .getIntent(getContext());
        startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);


    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], int[] grantResults) {
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

    // A method which will calculate the InSampleSize value as a power of 2 based on target width and height
    public static int CalculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
    {
        // Raw height and width of image
        float height = options.outHeight;
        float width = options.outWidth;
        double inSampleSize = 1D;

        if (height > reqHeight || width > reqWidth)
        {
            int halfHeight = (int)(height / 2);
            int halfWidth = (int)(width / 2);

            // Calculate a inSampleSize that is a power of 2 - the decoder will use a value that is a power of two anyway.
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth)
            {
                inSampleSize *= 2;
            }
        }

        return (int)inSampleSize;
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

