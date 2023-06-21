import java.io.BufferedReader;
import java.io.FileReader;
import java.text.ParseException;
import java.time.LocalDate;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class API {

   //"https://api.forecast.solar/estimate/lat/long/dec/az/kwp"
   //lat = 51.3304311  (latitude of location)
   //long = 4.5521836  (longitude of location)
   //dec = 39          (plane declination: 0 = horizontal and 90 = vertical)
   //az = 13           (plane azimuth: (-)180 = north, -90 = east, 0 = south, 90 = west)
   //kwp = 6.2         (installed modules power in kilo Watt)
   static String solarUrl= " https://api.forecast.solar/estimate/51.3304311/4.5521836/39/13/6.2";
   private static final SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   public LinkedHashMap<Date, Float> getSolarForecast( Date now) {

      LinkedHashMap<Date, Float> dayForecast = getDayForecast(solarForcast(), "today", now); // today or tomorrow are the options

      return dayForecast;
   }
   /*public  LinkedHashMap<Date, Integer> getAPISolarForecast()  {
      HttpRequest getRequest = null;
      try {
         getRequest = HttpRequest.newBuilder()
                 .uri(new URI(solarUrl))
                 .GET().build();
      } catch (URISyntaxException e) {
         throw new RuntimeException(e);
      }
      HttpClient httpClient = HttpClient.newHttpClient();
      HttpResponse<String> getResponse = null;
      try {
         getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
      } catch (IOException e) {
         throw new RuntimeException(e);
      } catch (InterruptedException e) {
         throw new RuntimeException(e);
      }
      // string manipulatie om hashMap te creëren
System.out.println(getResponse.body());
      String text = getResponse.body().split("watt_hours_period")[1].split("watt_hours")[0];
      text = text.substring(3, text.length()-3);

      LinkedHashMap<Date, Integer> hashMap = new LinkedHashMap<Date, Integer>();

      // split the String by a comma
      String parts[] = text.split(",");

      // iterate the parts and add them to a HashMap
      for (String part : parts) {
         // reverse string and split in 2 parts
         StringBuilder stringBuildervarible = new StringBuilder();
         stringBuildervarible.delete(0, stringBuildervarible.length());
         stringBuildervarible.append(part);
         stringBuildervarible.reverse();
         String data[] = stringBuildervarible.toString().split(":", 2);
         // reverse second part of string again to get time
         stringBuildervarible.delete(0, stringBuildervarible.length());
         stringBuildervarible.append(data[1]);
         stringBuildervarible.reverse();
         String dataTime = stringBuildervarible.toString().trim();
         Date date = null;
         try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dataTime.substring(1, dataTime.length()-1));
         } catch (ParseException e) {
            throw new RuntimeException(e);
         }
         // reverse first part of string again to get energyvalue
         stringBuildervarible.delete(0, stringBuildervarible.length());
         stringBuildervarible.append(data[0]);
         stringBuildervarible.reverse();
         Integer dataEnergy = Integer.valueOf(stringBuildervarible.toString());

         hashMap.put(date, dataEnergy);
        }

         return hashMap;
   }*/
   public  LinkedHashMap<Date, Float> getDayForecast(LinkedHashMap<Date, Integer> solarForecastFromAPI, String day, Date now)  {
      List<Date> keys = new ArrayList<Date>(solarForecastFromAPI.keySet());
      LinkedHashMap<Date, Float> listToday = new LinkedHashMap<Date,Float>();
      LinkedHashMap<Date, Float> listTomorrow = new LinkedHashMap<Date,Float>();
      //LocalDate currentdate = LocalDate.now();
      int currentDay = now.getDate();//currentdate.getDayOfMonth();
      String test = solarUrl;
      for(int i = 0; i < keys.size(); i++){
         if(keys.get(i).getDate()== currentDay){

            listToday.put(keys.get(i), Float.valueOf(solarForecastFromAPI.get(keys.get(i)))/1000);
         }else{
            listTomorrow.put(keys.get(i), Float.valueOf(solarForecastFromAPI.get(keys.get(i))));
         }
      }

      if(day == "today"){
         return listToday;
      }else{
        return listTomorrow;
      }
   }
   public  LinkedHashMap<Date,Float> getQuarterForecast(LinkedHashMap<Date,Float> dayForecast){
      LinkedHashMap<Date, Float> quarterForecast = new LinkedHashMap<Date,Float>();
      List<Date> keys = new ArrayList<Date>(dayForecast.keySet());
       int startHour = keys.get(1).getHours();
       int year = keys.get(1).getYear();
       int month = keys.get(1).getMonth();
       int day = keys.get(1).getDate();
       int lastHour = keys.get(keys.size()-1).getHours() +1 ;
       for(int i=0; i< startHour; i++){
          int hour = i;
          int min = 0;
          for(int j = 0; j < 4;j++){

             Date date = new Date(year, month, day, hour, min);
             quarterForecast.put(date,0f); // technique to get 2 decimals
             min = min +15;
          }
       }
      for(int i=startHour; i< lastHour; i++) {
         int hour = i;
         int min = 0;
         for (int j = 0; j < 4; j++) {

            Date date = new Date(year, month, day, hour, min);
            quarterForecast.put(date,Float.valueOf(dayForecast.get(keys.get(i-startHour+1)))/4000); // technique to get 2 decimals
            min = min +15;
         }
      }
         for(int i=lastHour; i< 24; i++){
         int hour = i;
            int min = 0;
            for (int j = 0; j < 4; j++) {
            Date date = new Date(year, month, day, hour, min);
            quarterForecast.put(date,0f); // technique to get 2 decimals
            min = min +15;
         }
      }
      return quarterForecast;
   }

   public float expectedEnergy(Date now){
      LinkedHashMap<Date, Float> solarForecast = getSolarForecast(now);
      int beginUur = now.getHours() ;
      int year = now.getYear();
      int month = now.getMonth();
      int day = now.getDate();
      for(int i=0; i< beginUur; i++){
         int hour = i;
            Date date1 = new Date(year, month, day, hour, 00, 00);
            solarForecast.remove(date1);


      }
         float result = 0;
         for (Float values : solarForecast.values()) {
            float floatValue = values.floatValue();
            result += floatValue;
         }
         return result;

   }
   public   LinkedHashMap<Date, Integer> solarForcast() {
      String csvFile = "D:\\school\\master\\masterproef\\project\\algoritme EMS\\src\\main\\java\\expectedEnergyweek2405.csv";
       // The index of the column you want to retrieve (starting from 0)
      String[] time = ReadCol(0, csvFile, ";");
      String[] data = ReadCol(1, csvFile, ";");
      LinkedHashMap<Date,Integer> forecast = new LinkedHashMap<>();
      for(int i =1; i< time.length; i++){
         try {
            forecast.put(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(time[i]), (int) Float.parseFloat(data[i].replace(',', '.')));
         } catch (ParseException e) {
            throw new RuntimeException(e);
         }
      }
      return forecast;
   }
   public  String[] ReadCol( int col, String filepath, String delimiter){

      String[] data;
      String currentLine;
      ArrayList<String> colData = new ArrayList<String>();
      try{

         FileReader fr =  new FileReader(filepath);

         BufferedReader br =  new BufferedReader(fr);

         while((currentLine= br.readLine()) != null){
            data =  currentLine.split(delimiter);
            colData.add(data[col]);

         }
      }
      catch (Exception e){

         System.out.println(e);
         return null;
      }
      return colData.toArray(new String[0]);
   }

}