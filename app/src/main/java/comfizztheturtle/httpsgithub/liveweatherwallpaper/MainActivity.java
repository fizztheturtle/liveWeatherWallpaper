//package comfizztheturtle.httpsgithub.liveweatherwallpaper;
//
//import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//
//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

//        Button showGalleryBtn = (Button) findViewById(R.id.btn_show_gallery);
//        showGalleryBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent galleryIntent = new Intent(MainActivity.this,
//                        SpaceGalleryActivity.class);
//                startActivity(galleryIntent);
//            }
//        });
//
//        Button showGif = (Button) findViewById(R.id.btn_show_gif);
//        showGif.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent gifIntent = new Intent(MainActivity.this,
//                        GifsActivity.class);
//                startActivity(gifIntent);
//            }
//        });

//    }
//}
//    ImageView imageView = (ImageView) findViewById(R.id.imageView);

//Loading image from below url into imageView
//        Glide.with(this)
//                .load("http://goo.gl/gEgYUd")
//                .override(200, 200)
//                .centerCrop()
//                .into(imageView);





