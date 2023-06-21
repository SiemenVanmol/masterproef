import java.util.Date;
import java.util.LinkedHashMap;
public class Algorithm {
    public  static final float  TimeInterval = 12f;
    public static final int jaarverbruik = 3500;

    // beginnen met per 15 min
    public float[] algorithm(float afname, float injectie, Vehicle EV, Date now, Battery battery, float limit, LinkedHashMap<Date, Float> prices) {
        float[] returns = new float[4];
        batteryCalculator batteryCalculator = new batteryCalculator();
        PriceCalculator priceCalculator = new PriceCalculator();
        Verbruiksprofiel verbruiksprofiel = new Verbruiksprofiel();
        API API = new API();
        float extraAfname = 0f;
        float extraInjectie = 0f;
        Date hourlyDate = new Date(now.getYear(),now.getMonth(), now.getDate(),now.getHours(),00,00);
        boolean isEVAanwezig = EV.carsIsAtChargeStation(now);
        if (injectie >= afname && battery.getCapaciteitsLevel() != battery.getCapaciteit()) {
            if(isEVAanwezig == true && (injectie*12 >=4.2f)&& EV.getCapaciteitsLevel() != EV.getCapaciteit()){
                EV.setCapaciteitsLevel(EV.EVLevelSim(injectie, EV));
                extraInjectie = -4.2f/12f;
                System.out.println("this is scenario 1: "+EV.getCapaciteitsLevel());
            }else{
            if (battery.getCapaciteit() == battery.getCapaciteitsLevel()) {
                System.out.println("this is scenario 2: injecteren");
            } else {
                // kan verkeerd berekend zijn, injectie < 0 voor batSim
                extraInjectie = injectie > (battery.getVermogen() / 12f) ? -battery.getVermogen() / 12f : -injectie;
                if (batteryCalculator.overLimit(-injectie, battery) > 0) {
                    extraInjectie = extraInjectie + batteryCalculator.overLimit(-injectie, battery);
                }
                battery.setCapaciteitsLevel(batteryCalculator.batLevelSim(-injectie, battery));
                System.out.println("this is scenario 3: "+ battery.getCapaciteitsLevel());
            }
            }

        } else {
//
            if(overLimiet(afname*12, limit)>= 0){
                extraAfname = afname < battery.getVermogen()/12f ? -afname : -(battery.getVermogen()/12f);
                if(batteryCalculator.overLimit(afname, battery) < 0){
                    extraAfname = extraAfname -batteryCalculator.overLimit(afname, battery);
                }
                battery.setCapaciteitsLevel(batteryCalculator.batLevelSim(afname, battery));
                System.out.println("this is scenario 4: "+battery.getCapaciteitsLevel());

            }else{
                float currentPrice = prices.get(hourlyDate);
                if (priceCalculator.isPrijsGoedkoop(currentPrice, now, prices)== true){
                    var komendeUren  = verbruiksprofiel.komendeUren(verbruiksprofiel.dayValues(now,jaarverbruik), now);
                    float verwachteVerbruik =isEVAanwezig==false? verbruiksprofiel.verwachtVerbruik(komendeUren):  verbruiksprofiel.verwachtVerbruik(komendeUren)+(EV.getCapaciteit()-EV.getCapaciteitsLevel());
                    float verwachteOpgewekteEnergie = API.expectedEnergy(now) + battery.getCapaciteitsLevel();
                    System.out.println("verwachtverbruik is: " + verwachteVerbruik + " en verwachte opgewekte energie is: "+ verwachteOpgewekteEnergie);
                    if(verbruiksprofiel.komtEreenVerbruikspiek(komendeUren)== true && verwachteOpgewekteEnergie < verwachteVerbruik ) { // &&  energy check for enough energy

                        extraAfname = battery.getVermogen() / 12f;
                        if (batteryCalculator.overLimit(-(battery.getVermogen() / 12f), battery) > 0) {
                            extraAfname = extraAfname - batteryCalculator.overLimit(-(battery.getVermogen() / 12f), battery);
                        }
                        System.out.println("this is scenario 4: " + battery.getCapaciteitsLevel());
                        battery.setCapaciteitsLevel(batteryCalculator.batLevelSim(-(battery.getVermogen() / 12f), battery));
                    }else{
                        System.out.println("this is scenario 5: wait for next cyclus");
                    }
                }else{
                    var komendeUren  = verbruiksprofiel.komendeUren(verbruiksprofiel.dayValues(now,jaarverbruik), now);
                    if(verbruiksprofiel.isEreenVerbruikspiek(komendeUren)== true){
                        extraAfname = afname < battery.getVermogen()/12f ? -afname : -(battery.getVermogen()/12f);
                        if(batteryCalculator.overLimit(afname, battery) < 0){
                            extraAfname = extraAfname -batteryCalculator.overLimit(afname, battery);
                        }
                        battery.setCapaciteitsLevel(batteryCalculator.batLevelSim(afname, battery));
                        System.out.println("this is scenario 6: "+battery.getCapaciteitsLevel());
                    }else{
                        if(priceCalculator.isPrijsduur(currentPrice, now, prices)== true){
                            extraAfname = afname < battery.getVermogen()/12f ? -afname : -(battery.getVermogen()/12f);
                            if(batteryCalculator.overLimit(afname, battery) < 0){
                                extraAfname = extraAfname -batteryCalculator.overLimit(afname, battery);
                            }
                            battery.setCapaciteitsLevel(batteryCalculator.batLevelSim(afname, battery));
                            System.out.println("this is scenario 9: "+battery.getCapaciteitsLevel());
                        }else{
                        if(verbruiksprofiel.komtEreenVerbruikspiek(komendeUren)== true){
                            float verwachteVerbruik =isEVAanwezig==false? verbruiksprofiel.verwachtVerbruik(komendeUren):  verbruiksprofiel.verwachtVerbruik(komendeUren)+(EV.getCapaciteit()-EV.getCapaciteitsLevel());
                            float verwachteOpgewekteEnergie = API.expectedEnergy(now) + battery.getCapaciteitsLevel();
                            System.out.println("verwachtverbruik is: " + verwachteVerbruik + " en verwachte opgewekte energie is: "+ verwachteOpgewekteEnergie);
                            if(verwachteOpgewekteEnergie > verwachteVerbruik){

                                extraAfname = afname < battery.getVermogen()/12f ? -afname : -(battery.getVermogen()/12f);
                                if(batteryCalculator.overLimit(afname, battery) < 0){
                                    extraAfname = extraAfname -batteryCalculator.overLimit(afname, battery);
                                }
                                battery.setCapaciteitsLevel(batteryCalculator.batLevelSim(afname, battery));
                                System.out.println("this is scenario 7: "+battery.getCapaciteitsLevel());
                            }else{
                                    System.out.println("this is scenario 8: wait for next cyclus");
                                }

                        }else{
                            extraAfname = afname < battery.getVermogen()/12f ? -afname : -(battery.getVermogen()/12f);
                            if(batteryCalculator.overLimit(afname, battery) < 0){
                                extraAfname = extraAfname -batteryCalculator.overLimit(afname, battery);
                            }
                            battery.setCapaciteitsLevel(batteryCalculator.batLevelSim(afname, battery));// ontladen
                            System.out.println("this is scenario 10: "+battery.getCapaciteitsLevel());
                        }
                    }
                }
            }
            }
       }
        returns[0] = battery.getCapaciteitsLevel();
        returns[1] = EV.getCapaciteitsLevel();
        returns[2] = extraAfname;
        returns[3] = extraInjectie;
        return returns;
    }
    public float getMinimumHoursToChargeEV(Vehicle vehicle, int gewensteCapaciteit){
        float gewensteEVLevel = vehicle.getCapaciteit()*(gewensteCapaciteit/100);
        float teLaden = gewensteEVLevel-vehicle.getCapaciteitsLevel();
        return teLaden/vehicle.getVermogen()>0?teLaden/vehicle.getVermogen():0;
    }
    public boolean snelLadenEV(Date vertrekuur, float minimaleOplaadTijd, Date now){
            long Time_difference = vertrekuur.getTime() - now.getTime();
            long minutes_difference = (Time_difference / (1000 * 60)) % 60;
            long Hours_difference = (Time_difference / (1000 * 60 * 60)) % 24;
            long Days_difference = (Time_difference / (1000 * 60 * 60 * 24)) % 365;
            float timeLeftForCharging = Days_difference*24 + Hours_difference + (minutes_difference/60f);
        float opLaadTijd = minimaleOplaadTijd+0.25f; // extra marge van 1/4 uur
        return timeLeftForCharging<=opLaadTijd? true: false;
    }
    public boolean batterijEVFull(Vehicle vehicle, int gewensteCapaciteit){
        float gewensteminimumCapaciteit = vehicle.getCapaciteit()*(gewensteCapaciteit/100);
        return  vehicle.getCapaciteitsLevel()>= gewensteminimumCapaciteit? true: false;
    }
    public  float overLimiet(float currentvalue, float limit){
        float difference = currentvalue - (limit*0.95f) ;
        return difference;
    }

}
