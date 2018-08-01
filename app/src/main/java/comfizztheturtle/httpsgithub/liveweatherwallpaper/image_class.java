package comfizztheturtle.httpsgithub.liveweatherwallpaper;


public class image_class {

    private String link_to_file;
    private int id_weather_type;

    image_class() {}

    image_class(int id_weather_type, String link_to_file) {
        this.id_weather_type =id_weather_type;
        this.link_to_file= link_to_file;
    }


    public void set_ID(int id)
    {
     this.id_weather_type = id;
    }

    public int get_ID()
    {
        return this.id_weather_type ;
    }

    public void set_link_to_file(String ltf)
    {
        this.link_to_file = ltf;
    }

    public String get_link_to_file()
    {
        return this.link_to_file;
    }






}
