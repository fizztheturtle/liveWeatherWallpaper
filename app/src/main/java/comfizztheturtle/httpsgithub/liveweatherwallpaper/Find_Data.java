package comfizztheturtle.httpsgithub.liveweatherwallpaper;


//"Contains public sector information licensed under the Open Government Licence"
// add a 10 counter per minute for requests
// add a 100000 per day counter

import android.content.Context;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.URL;

import java.nio.charset.Charset;
import java.nio.file.Files;

import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



public class Find_Data  {


//Need to implement this into one of the android classes
    public static void main(String[] args) throws Exception {

        // write your code here

        URL url =  Find_Data.class.getResource("raw/api_key.txt");
        System.out.println(url);
        String API_Key=Paths.get(url.toURI()).toFile().toString();


        String contents =new String(Files.readAllBytes(Paths.get(API_Key)));
//        String contents = BuildConfig.ApiKey;
//

//        String link ="C:/AndroidApplications/Weather_0.1/MetOfficeList/weather_3hours.json";
//
//        URL url_temp =  Find_Data.class.getResource("weather_3hours.json");
//        String link =Paths.get(url.toURI()).toFile().toString();


        // Make a URL to the web page
        String url_API = "http://datapoint.metoffice.gov.uk/public/data/val/wxfcs/all/json/350473?res=3hourly&key="+contents;

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
                SimpleDateFormat df = new SimpleDateFormat("HH:mm");
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
