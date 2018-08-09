package comfizztheturtle.httpsgithub.liveweatherwallpaper;

import java.util.ArrayList;

public class weather_data {

    private String location;
    private ArrayList<String> period;
    private ArrayList<String> rep;
    private ArrayList<String> weather_type;
    private ArrayList<String> time_weather;


    weather_data() {}

    weather_data(String location, ArrayList<String> period, ArrayList<String> rep,
                 ArrayList<String> weather_type,ArrayList<String> time_weather) {
        this.location=location;
        this.period =period;
        this.rep=rep;
        this.weather_type=weather_type;
        this.time_weather=time_weather;

    }

    public void set_location(String location_this)
    {
        this.location = location_this;
    }

    public String get_location()
    {
        return this.location ;
    }

    public void set_period(ArrayList<String> period_this)
    {
        this.period = period_this;
    }

    public ArrayList<String> get_period()
    {
        return this.period ;
    }

    public void set_rep(ArrayList<String> rep_this)
    {
        this.rep = rep_this;
    }

    public ArrayList<String> get_rep()
    {
        return this.rep;
    }

    public void set_weather_type(ArrayList<String> weather_type_this)
    {
        this.weather_type = weather_type_this;
    }

    public ArrayList<String> get_weather_type()
    {
        return this.weather_type ;
    }

    public void set_time_weather(ArrayList<String> time_weather_this)
    {
        this.time_weather = time_weather_this;
    }

    public ArrayList<String> get_time_weather()
    {
        return this.time_weather;
    }





}
