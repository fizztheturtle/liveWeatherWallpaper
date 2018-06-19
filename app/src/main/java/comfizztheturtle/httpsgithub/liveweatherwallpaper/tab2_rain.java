package comfizztheturtle.httpsgithub.liveweatherwallpaper;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class tab2_rain extends Fragment {

    int PICK_IMAGE_REQUEST = 1;
    private View image;
    private Uri mSelectedImageUri;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab2_rain, container, false);
        image=v;
        ImageView new_image_device =image.findViewById(R.id.image_device_rain);
        if (mSelectedImageUri != null) {
            new_image_device.setImageURI(mSelectedImageUri);
        }

        Button get_image_button = v.findViewById(R.id.get_image_button_rain);
        get_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{Intent intent = new Intent();
// Show only images, no videos or anything else
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)

//            image_device.setImageResource(R.drawable.myimage);

                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


                }catch(Exception exp){
                    Log.i("Error",exp.toString());
                }
            }
        });

        return v;


    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == Activity.RESULT_CANCELED) {
            // code to handle cancelled state
        }
        else if (requestCode == PICK_IMAGE_REQUEST) {
            mSelectedImageUri  = intent.getData();
            ImageView new_image_device =image.findViewById(R.id.image_device_rain);
            new_image_device.setImageURI(mSelectedImageUri );

        }


    }

}