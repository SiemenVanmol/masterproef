import com.opencsv.CSVWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Float[] afname = afname();
        Float[] injectie = injectie();
        Date[] time = time();
        PriceCalculator priceCalculator = new PriceCalculator();
        Battery batterij = new Battery(4,1,2f);
        Vehicle EV = new Vehicle(60,11,60);
        LinkedHashMap<Date,Float> afnamePerTijd = new LinkedHashMap<Date, Float>();
        LinkedHashMap<Date,Float> injectiePerTijd = new LinkedHashMap<Date, Float>();
        LinkedHashMap<Date,Float> verloopBatterij = new LinkedHashMap<Date, Float>();
        LinkedHashMap<Date,Float> verloopWagen = new LinkedHashMap<Date, Float>();
        Algorithm algorithm  = new Algorithm();
        float piekLimiet = 2.5f;
        SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Date[] vertrekuren = new Date[]{ format.parse( "25/05/2023 08:00"),format.parse( "26/05/2023 08:00"),format.parse( "27/05/2023 08:00"),format.parse( "28/05/2023 08:00"),format.parse( "29/05/2023 08:00"),format.parse( "30/05/2023 08:00"),format.parse( "31/05/2023 08:00")};//getVertrekuur();
        int[] startLevels = new int[]{30,30,30,30,50,50,60 };
        int gewensteCapaciteit = 100;//getGewensteCapaciteit();
        int k = 0;
        int currenDate = time[0].getDate();
        LinkedHashMap<Date, Float> planning = new LinkedHashMap<Date, Float>();
        LinkedHashMap<Date,Float> priceToday = priceCalculator.APIPrices(time[0], time[0]);
        for (int i=0; i<afname.length;i++){
            System.out.println("this is loopnumber: "+ (i+2));
            Date vertrekuur = format.parse( "17/05/2023 08:00");
            if(i>0 && EV.carsIsAtChargeStation(time[i]) ==true && EV.carsIsAtChargeStation(time[i-1]) == false){
                vertrekuur = vertrekuren[k];
                EV.setCapaciteitsLevel(startLevels[k]);
                planning = planningCarCharging(time[i], EV,vertrekuur, gewensteCapaciteit, piekLimiet);
                System.out.println(planning);
                k = k +1;
            }

            for(int j = 0; j< planning.size(); j++){
                Date arr[] = planning.keySet().toArray(new Date[0]);
                if( time[i].getDate() == arr[j].getDate() && time[i].getHours() == arr[j].getHours()){
                    System.out.println("this is EV level: " + EV.getCapaciteitsLevel());
                        float teVeelAfname = EV.overLimit(planning.get(arr[j])/12f, EV);
                        EV.setCapaciteitsLevel(EV.EVLevelSim(planning.get(arr[j]), EV));
                    afname[i] = afname[i] + (planning.get(arr[j])/12f) - teVeelAfname;
                    // EV level simulation
                }
            }
            if( time[i].getDate() != currenDate){
                 priceToday= priceCalculator.APIPrices(time[i], time[i]);
                currenDate = time[i].getDate();
            }
            float[] returnvalues = algorithm.algorithm(afname[i], injectie[i], EV, time[i], batterij, piekLimiet, priceToday);
            batterij.setCapaciteitsLevel(returnvalues[0]);
            EV.setCapaciteitsLevel(returnvalues[1]);
            afnamePerTijd.put(time[i], afname[i]+returnvalues[2]);
            injectiePerTijd.put(time[i], injectie[i]+ returnvalues[3]);
            verloopBatterij.put(time[i], batterij.getCapaciteitsLevel());
            verloopWagen.put(time[i], EV.getCapaciteitsLevel());
            piekLimiet = Math.max(piekLimiet, ((afname[i]+returnvalues[2])*12f));
            System.out.println("pieklimiet: "+piekLimiet);
        }
        LinkedHashMap<Date, Float> prices = priceCalculator.APIPrices(time[0], time[time.length-1]);
        System.out.println("einde afname: "+afnamePerTijd);
        System.out.println("einde injectie: "+injectiePerTijd);
        System.out.println("einde batterij: "+verloopBatterij);
        System.out.println("einde voertuig: " +verloopWagen);
        writeDataLineByLine("D:\\school\\master\\masterproef\\project\\algoritme EMS\\src\\main\\java\\generatedData.csv", afnamePerTijd,injectiePerTijd, verloopBatterij,verloopWagen, prices);
    }
    public static void writeDataLineByLine(String filePath,LinkedHashMap<Date,Float> afnamePerTijd,LinkedHashMap<Date,Float> injectiePerTijd,LinkedHashMap<Date,Float> verloopBatterij,LinkedHashMap<Date,Float> verloopWagen,LinkedHashMap<Date,Float> prices )
    {
        // first create file object for file placed at location
        // specified by filepath
        File file = new File(filePath);
        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);

            // adding header to csv
            String[] header = { "Time", "afname", "injectie", "batterij", "voertuig", "prijs" };
            writer.writeNext(header);

            for(int i = 0;i< afnamePerTijd.size() ;i++){
                var key = afnamePerTijd.keySet().toArray()[i];
                List<Date> keys = new ArrayList<>(afnamePerTijd.keySet());
                Date date = new Date(keys.get(i).getYear(), keys.get(i).getMonth(), keys.get(i).getDate(), keys.get(i).getHours(), 00,00);
               String[] data = new String[]{key.toString(), afnamePerTijd.get(key).toString(), injectiePerTijd.get(key).toString(), verloopBatterij.get(key).toString(), verloopWagen.get(key).toString(), prices.get(date).toString()};
                writer.writeNext(data);
            }
            // add data to csv
            // closing writer connection
            writer.close();
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static Float[] afname() {
        String csvFile = "D:\\school\\master\\masterproef\\project\\algoritme EMS\\src\\main\\java\\testdataweek2405.csv";
        int columnNumber = 0; // The index of the column you want to retrieve (starting from 0)
        String[] data = ReadCol(columnNumber, csvFile, ";");
        Float[] floatData = new Float[(data.length-1)];
        for(int i =1; i< data.length; i++){
            floatData[i-1] = Float.parseFloat(data[i].replace(',', '.'));
        }
        return floatData;
    }
    public  static Float[] injectie() {
        String csvFile = "D:\\school\\master\\masterproef\\project\\algoritme EMS\\src\\main\\java\\testdataweek2405.csv";
        int columnNumber = 1; // The index of the column you want to retrieve (starting from 0)
        String[] data = ReadCol(columnNumber, csvFile, ";");
        Float[] floatData = new Float[(data.length-1)];
        for(int i =1; i< data.length; i++){
            floatData[i-1] = Float.parseFloat(data[i].replace(',', '.'));
        }
        return floatData;
    }
    public  static Date[] time() {
        String csvFile = "D:\\school\\master\\masterproef\\project\\algoritme EMS\\src\\main\\java\\testdataweek2405.csv";
        int columnNumber = 2; // The index of the column you want to retrieve (starting from 0)
        String[] data = ReadCol(columnNumber, csvFile, ";");
        Date[] floatData = new Date[(data.length-1)];
        for(int i =1; i< data.length; i++){
            try {
                floatData[i-1] =new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(data[i]);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        return floatData;
    }
    public static String[] ReadCol( int col, String filepath, String delimiter){

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
    public static Date getVertrekuur(){

        Scanner myObj = new Scanner(System.in);
        System.out.println("hoelaat vertrekken?(dd/MM/yyyy hh:mm)");
        String time=myObj.nextLine();
        SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Date date = new Date();
        try {
            date=format.parse(time);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        System.out.println("uur van vertrek: " + date);
        return date;
    }
    public static Integer getGewensteCapaciteit(){

        Scanner myObj = new Scanner(System.in);
        System.out.println("hoe vol moet auto geladen zijn? (in %)");
        int gewensteCapaciteit = myObj.nextInt();
        System.out.println("capacitiet bij vertrek: " +gewensteCapaciteit+ "%");
        return gewensteCapaciteit;
    }
    public static LinkedHashMap<Date, Float> planningCarCharging(Date now, Vehicle vehicle, Date vertrekuur, int gewensteCapaciteit, float pieklimiet){
        LinkedHashMap<Date,Float> planningOfCar = new LinkedHashMap<>();
        Verbruiksprofiel verbruiksprofiel = new Verbruiksprofiel();
        PriceCalculator priceCalculator  = new PriceCalculator();
        LinkedHashMap<Date, Float> prices  = priceCalculator.prijzenVoorAuto(now, vertrekuur);
        LinkedHashMap<Date, Float> verbruik = verbruiksprofiel.verbruikVoorAuto(now, vertrekuur, 3500*3);
        float laagsteVermogen  = 4.2f;
        Date arr[] = prices.keySet().toArray(new Date[0]);
         float min = 100000f;
         for( float a = laagsteVermogen; a <= vehicle.getVermogen(); a = Math.round((a+0.2f)*100f)/100f){
             int hoursToCharge = gethoursToChargeEV(vehicle, gewensteCapaciteit, a);
             List<List<Date>> combinations = printCombination(arr, arr.length, hoursToCharge);
        for(int i = 0; i < combinations.size(); i++){
            float som = 0;
            float geladen = vehicle.getCapaciteitsLevel();
           List<Float> afnames = new ArrayList<>();
            for(int j = 0; j< combinations.get(i).size();j++){
                if(a + geladen < vehicle.getCapaciteit()) {
                    geladen = geladen + a;
                    som = som+ priceOfChargingCar(prices,combinations.get(i).get(j),a);
                    int minutes = 0;
                    for(int k = 0; k <4; k++) {
                        Date date = new Date(combinations.get(i).get(j).getYear(), combinations.get(i).get(j).getMonth(), combinations.get(i).get(j).getDate(), combinations.get(i).get(j).getHours(), minutes, 00);
                        afnames.add(verbruik.get(date) + (a / 4));
                        minutes = minutes + 15;
                    }
                }
                else{
                    float difference = a-(a+geladen -vehicle.getCapaciteit());
                    som = som+ priceOfChargingCar(prices,combinations.get(i).get(j),difference);
                        int minutes = 0;
                        for(int k = 0; k <4; k++){
                            Date date = new Date(combinations.get(i).get(j).getYear(), combinations.get(i).get(j).getMonth(), combinations.get(i).get(j).getDate(), combinations.get(i).get(j).getHours(), minutes, 00);
                            afnames.add(verbruik.get(date)+(difference/4));
                            minutes = minutes +15;
                }


               }

            }
            float captarprijs = captarPrice(afnames, pieklimiet);
            if(captarprijs+ som < min){
                planningOfCar.clear();
                min = Math.min(min, captarprijs+som);
                for(int p = 0; p< combinations.get(i).size();p++) {
                    planningOfCar.put(combinations.get(i).get(p), a);
                }
            }

        }}
        return planningOfCar;
    }

static void combinationUtil(Date[] arr, Date[] data, int start,
                              int end, int index, int r, List<List<Date>> combinations)
{
    List<Date> dates = new ArrayList<>();
    // Current combination is ready to be printed, print it
    if (index == r)
    {
        for (int j=0; j<r; j++) {
            dates.add(data[j]);
        }
        combinations.add(dates);
        return;
    }

    // replace index with all possible elements. The condition
    // "end-i+1 >= r-index" makes sure that including one element
    // at index will make a combination with remaining elements
    // at remaining positions
    for (int i=start; i<=end && end-i+1 >= r-index; i++)
    {
        data[index] = arr[i];
        combinationUtil(arr, data, i+1, end, index+1, r, combinations);
    }
}

    // The main function that prints all combinations of size r
    // in arr[] of size n. This function mainly uses combinationUtil()
    static List<List<Date>> printCombination(Date[] arr, int n, int r)
    {
        // A temporary array to store all combination one by one
        Date data[]=new Date[r];
        List<List<Date>> combinations = new ArrayList<>();
        // Print all combination using temporary array 'data[]'
        combinationUtil(arr, data, 0, n-1, 0, r, combinations);
    return combinations;
    }

    /*Driver function to check for above function*/

    public static int gethoursToChargeEV(Vehicle vehicle, int gewensteCapaciteit, float vermogen){
        float gewensteEVLevel = vehicle.getCapaciteit()*(gewensteCapaciteit/100);
        float teLaden = gewensteEVLevel-vehicle.getCapaciteitsLevel();
        return teLaden/vermogen>0? (int) Math.ceil((teLaden / vermogen)) :0;
    }
    public static float tijdOver(Date vertrekuur, Date now){
        long Time_difference = vertrekuur.getTime() - now.getTime();
        long minutes_difference = (Time_difference / (1000 * 60)) % 60;
        long Hours_difference = (Time_difference / (1000 * 60 * 60)) % 24;
        long Days_difference = (Time_difference / (1000 * 60 * 60 * 24)) % 365;
        return Days_difference*24 + Hours_difference + (minutes_difference/60f);

    }

    public static float captarPrice(List<Float> afname, float pieklimiet){

        float maxOfNewMonth = newMaxOfMonth(afname, pieklimiet);
        float mean = maxOfNewMonth;

        float totalCaptar = mean*(40f/12f);
        totalCaptar = Math.round(totalCaptar*100f)/100f;
        return  totalCaptar;
    }
    public static float newMaxOfMonth(List<Float> afname, float pieklimiet){
        float maxOfNewMonth = Math.max(2.5f, pieklimiet);
        for (int i = 0; i < afname.size();i++){
            if (afname.get(i)*4> maxOfNewMonth){
                maxOfNewMonth = afname.get(i)*4;
            }

        }
        return maxOfNewMonth;
    }
    public static float priceOfChargingCar(LinkedHashMap<Date, Float> prices, Date now, float vermogen){
//            int hour = now.getHours();
//            int year = now.getYear();
//            int month = now.getMonth();
//            int day = now.getDate();
//            Date date = new Date(year, month, day, hour, 00, 00);
            float price = prices.get(now);
            price = ((1.06f*price)+ 108.8f)/1000f;
           float som = vermogen*price;
        return som;
    }
}