import static java.lang.Math.max;
import static java.lang.Math.min;

public class batteryCalculator {

    public float batLevelSim( float huidigeCapaciteit, Battery battery){
        float maxPower = battery.getVermogen()/12f;

            float currentlevel = battery.getCapaciteitsLevel();
            float batteryUsage = huidigeCapaciteit<0f ? max(-maxPower,huidigeCapaciteit) : min(maxPower, huidigeCapaciteit);

           float batlevel = currentlevel-batteryUsage;
            batlevel= Math.max(batlevel, 0f);
            batlevel= min(batlevel, battery.getCapaciteit());

        return batlevel;
    }
    public float overLimit(float huidigeCapaciteit, Battery battery){

        float maxPower = battery.getVermogen()/12f;
        float currentlevel = battery.getCapaciteitsLevel();
        float batteryUsage = huidigeCapaciteit < 0f ? max(-maxPower, huidigeCapaciteit) : min(maxPower, huidigeCapaciteit);
        float batlevel = currentlevel-batteryUsage;
        if( batlevel> battery.getCapaciteit()) {
            return batlevel-battery.getCapaciteit();
        } else if (batlevel<= 0) {
         return batlevel;
        }else{
            return 0;
        }
    }
}

