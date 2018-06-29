package comfizztheturtle.httpsgithub.liveweatherwallpaper;

import android.media.Image;

import java.net.URI;

public class fireBS {

    private URI link_to_file;
    private String name;

    public void image_location(String name, URI link_to_file)
    {
        this.name =name;
        this.link_to_file= link_to_file;

    }
}
//https://firebase.google.com/docs/auth/android/custom-auth
//https://firebase.google.com/docs/database/web/read-and-write