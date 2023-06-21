import java.util.Date;
import java.util.LinkedHashMap;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Vehicle {
    private float capaciteit;

    private float vermogen;

    private float capaciteitsLevel;

    public void setCapaciteit(float capaciteit) {
        this.capaciteit = capaciteit;
    }

    public void setVermogen(float vermogen) {
        this.vermogen = vermogen;
    }

    public void setCapaciteitsLevel(float capaciteitsLevel) {
        this.capaciteitsLevel = capaciteitsLevel;
    }

    public float getCapaciteit() {
        return capaciteit;
    }

    public float getVermogen() {
        return vermogen;
    }

    public float getCapaciteitsLevel() {
        return capaciteitsLevel;
    }
    public boolean carsIsAtChargeStation(Date now){
        int currentDay = now.getDay();
        int currentHour = now.getHours();
        if(currentDay == 0 || currentDay == 6){
            if (currentHour <= 8 || currentHour >= 18) {
                return true;
            } else {
                return false;
            }

        }else {
            if (currentHour <= 8 || currentHour >= 18) {
                return true;
            } else {
                return false;
            }
        }
    }
    public float EVLevelSim( float vermogen, Vehicle vehicle){
        float maxPower = vehicle.getVermogen()/12f;

        float currentlevel = vehicle.getCapaciteitsLevel();
        float batteryUsage = min(maxPower, max(0.35f, vermogen/12f));  //0.35 = 4.2/12

        float EVLevel = currentlevel+batteryUsage;
        EVLevel= Math.max(EVLevel, 0f);
        EVLevel= min(EVLevel, vehicle.getCapaciteit());

        return EVLevel;
    }
    public float overLimit(float huidigeqCapaciteit, Vehicle vehicle){
        float maxPower = vehicle.getVermogen()/12f;
        float currentlevel = vehicle.getCapaciteitsLevel();
        float batteryUsage = min(maxPower, huidigeqCapaciteit);
        float batlevel = currentlevel+batteryUsage;
        if( batlevel> vehicle.getCapaciteit()) {
            return batlevel-vehicle.getCapaciteit();
        } else{
            return 0;
        }
    }

    public Vehicle(float capaciteit, float vermogen, float capaciteitsLevel) {
        this.capaciteit = capaciteit;
        this.vermogen = vermogen;
        this.capaciteitsLevel = capaciteitsLevel;
    }
}
