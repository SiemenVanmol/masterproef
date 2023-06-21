import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

public class PriceCalculator {
    static String pricesUrl= "https://api.my.yuso.io/prices?area=BE&market=DA&start=2023-05-24&end=2023-05-30&api_key=Mv5p7J5TKgMxn";
    public  static final float[]  vorigePiekVermogens = {2.5f, 2.6f, 2.8f, 3.2f, 3.8f, 2.5f, 2.9f, 4.1f, 3.4f, 2.8f, 3.6f};
    public LinkedHashMap<Date, Float> APIPrices(Date now, Date end){
        HttpRequest getRequest = null;
        try {
            getRequest = HttpRequest.newBuilder()
                    .uri(new URI(String.format("https://api.my.yuso.io/prices?area=BE&market=DA&start=2023-05-%s&end=2023-05-%s&api_key=Mv5p7J5TKgMxn",String.valueOf(now.getDate()), String.valueOf(end.getDate()))))
                    .GET().build();
        } catch (URISyntaxException e) {
           System.out.println(e);
        }
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> getResponse = null;
        try {
            getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            System.out.println(e);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        LinkedHashMap<Date,Float> dayPrices = new LinkedHashMap<>();
        // string manipulatie om hashMap te creëren
       String[] timeparts = getResponse.body().split("delivery_start_local");
       String[] prices = getResponse.body().split("price");
       for(int i = 1; i< timeparts.length; i++) {
           Date date1;
           try {
               date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timeparts[i].substring(3, 22));
           } catch (ParseException e) {
               throw new RuntimeException(e);
           }
           String price = prices[i + 1].substring(2, 8);
           String correctPrice = cutString(price);
           dayPrices.put(date1, Float.valueOf(correctPrice));
       }

        return dayPrices;
    }
    public String cutString(String price){
        if (price.substring(price.length() - 1).equals("0") == true||price.substring(price.length() - 1).equals("1") == true||price.substring(price.length() - 1).equals("2") == true||
                price.substring(price.length() - 1).equals("3") == true||price.substring(price.length() - 1).equals("4") == true||price.substring(price.length() - 1).equals("5") == true||
                price.substring(price.length() - 1).equals("6") == true||price.substring(price.length() - 1).equals("7") == true||price.substring(price.length() - 1).equals("8") == true||
                price.substring(price.length() - 1).equals("9") == true) {
            return price;

        }else{
            return cutString( price.substring(0, price.length() - 1));
        }

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
                    colData.add(data[col].substring(4));

            }
        }
        catch (Exception e){

            System.out.println(e);
            return null;
        }
        return colData.toArray(new String[0]);
    }
    public  String[] ReadColTime( int col, String filepath, String delimiter){

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

    public  float maxPrice(Date now, LinkedHashMap<Date, Float> prices){
        List<Date> keys = new ArrayList<Date>(prices.keySet());
        LinkedHashMap<Date, Float> listToday = new LinkedHashMap<Date,Float>();
        int currentDay = now.getDate();//currentdate.getDayOfMonth();

        for(int i = 0; i < keys.size(); i++){
            if(keys.get(i).getDate()== currentDay){

                listToday.put(keys.get(i), Float.valueOf(prices.get(keys.get(i))));
            }
        }

        Float[] Todayprices = listToday.values().toArray(new Float[0]);
        float max = 0f;
        for(int i = 0; i < Todayprices.length; i++){
            max = Float.max(max, Todayprices[i]);
        }
        return max;
    }
    public  float minPrice(Date now,LinkedHashMap<Date, Float> prices){
        List<Date> keys = new ArrayList<Date>(prices.keySet());
        LinkedHashMap<Date, Float> listToday = new LinkedHashMap<Date,Float>();
        int currentDay = now.getDate();//currentdate.getDayOfMonth();

        for(int i = 0; i < keys.size(); i++){
            if(keys.get(i).getDate()== currentDay){

                listToday.put(keys.get(i), Float.valueOf(prices.get(keys.get(i))));
            }
        }

        Float[] Todayprices = listToday.values().toArray(new Float[0]);
        float min = 100000f;
        for(int i = 0; i < Todayprices.length; i++){
            min = Float.min(min, Todayprices[i]);
        }
        return min;
    }
    public  boolean isPrijsGoedkoop(float price, Date now,LinkedHashMap<Date, Float> prices){
        float maxPrice = maxPrice(now, prices);
        float minPrice = minPrice(now, prices);
        float priceRange = maxPrice-minPrice;
        float referencevalue = minPrice + priceRange*0.2f;

        return price <= referencevalue? true : false;
    }
    public  boolean isPrijsduur(float price, Date now , LinkedHashMap<Date, Float> prices){
        float maxPrice = maxPrice(now, prices);
        float minPrice = minPrice(now, prices);
        float priceRange = maxPrice-minPrice;
        float referencevalue = minPrice + priceRange*0.8f;
        return price >= referencevalue? true : false;
    }
    public LinkedHashMap<Date, Float> prijzenVoorAuto(Date now, Date vertrekuur){
        LinkedHashMap<Date, Float> prices = APIPrices(now, vertrekuur);
        for(int i = 0; i < 18; i++){
            int hour = i;
            Date date1 = new Date(now.getYear(), now.getMonth(), now.getDate(), hour, 00, 00);
            prices.remove(date1);
        }
        for (int k = vertrekuur.getHours(); k<24; k++){
            int hour = k;
            Date date2 = new Date(vertrekuur.getYear(), vertrekuur.getMonth(), vertrekuur.getDate(), hour, 00, 00);
            prices.remove(date2);
        }
        return prices;
    }
}
