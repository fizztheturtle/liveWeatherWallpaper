package comfizztheturtle.httpsgithub.liveweatherwallpaper;

import android.media.Image;

import java.net.URI;

public class image_class {

    private URI link_to_file;
    private String id_weather_type;

    public image_class() {}

    public void image_location(String id_weather_type, URI link_to_file)
    {
        this.id_weather_type =id_weather_type;
        this.link_to_file= link_to_file;
    }








}
