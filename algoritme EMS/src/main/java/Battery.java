import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;

public class Battery {

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

    public Battery(float capaciteit, float vermogen, float capaciteitsLevel) {
        this.capaciteit = capaciteit;
        this.vermogen = vermogen;
        this.capaciteitsLevel = capaciteitsLevel;
    }
}
