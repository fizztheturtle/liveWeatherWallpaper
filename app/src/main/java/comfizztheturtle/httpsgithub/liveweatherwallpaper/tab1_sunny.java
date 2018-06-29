package comfizztheturtle.httpsgithub.liveweatherwallpaper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import static android.app.Activity.RESULT_OK;
import static com.theartofdev.edmodo.cropper.CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE;


public class tab1_sunny extends Fragment {

    int sunny_weather_id = 0;
    int PICK_IMAGE_REQUEST = 1;
    private View image;
    private Uri mSelectedImageUri;
    public MyDBHandler dbHandler;
    public static final String DATABASE_NAME = "studentDB.db";
    ImageView new_image_device;

//    1st= image
//    studentname=mSelectedImageUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dbHandler = new MyDBHandler(this.getContext());
        View v = inflater.inflate(R.layout.tab1_sunny, container, false);
        image=v;

        new_image_device =image.findViewById(R.id.image_device);

        if (mSelectedImageUri != null) {
            new_image_device.setImageURI(mSelectedImageUri);
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
                    if (mSelectedImageUri != null) {
//                    Intent intent2 = new Intent(WallpaperManager.ACTION_CROP_AND_SET_WALLPAPER);
//                    String mime = "image/*";


//                         Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
                         Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
//                         intent.setAction(WallpaperManager.ACTION_CROP_AND_SET_WALLPAPER);
                         intent.addCategory(Intent.CATEGORY_DEFAULT);
                         intent.setDataAndType(mSelectedImageUri, "image/*");
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
                mSelectedImageUri=resultUri;
                ImageView new_image_device =image.findViewById(R.id.image_device);
                new_image_device.setImageURI(resultUri );
                add_weather_image(image);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.e("MyApp","I have failed");
            }
        }
        else if (requestCode == PICK_IMAGE_REQUEST) {

                mSelectedImageUri  = intent.getData();
            startCropImageActivity(mSelectedImageUri);


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
            if (mSelectedImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // required permissions granted, start crop image activity
                startCropImageActivity(mSelectedImageUri);
            } else {
                Snackbar.make(image, "Permission required", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }
    }


//need one for update,remove,delete... everything
    public void add_weather_image(View view) {

       if(mSelectedImageUri != null) {



           int id = sunny_weather_id;
           String link_to_file =(mSelectedImageUri.toString());
//        Uri ltf = Uri.parse(link_to_file);
           image_class weather_image = new image_class(id, link_to_file);
           dbHandler.addHandler(weather_image);
           String result=dbHandler.loadHandler();
           Log.e("MyApp",result);

       }
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

