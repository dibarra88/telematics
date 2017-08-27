import java.text.SimpleDateFormat;
import java.util.Date;

public class VehicleInfo {
    private int VIN;
    private double odometer;
    private double consumption;
    private double odemeterForLastOilChange;
    private double engineSize;
    private String createdAt;

    public VehicleInfo() {
        this.createdAt = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
    }

    public int getVIN() {
        return VIN;
    }

    public void setVIN(int VIN) {
        this.VIN = VIN;
    }

    public double getOdometer() {
        return odometer;
    }

    public void setOdometer(double odometer) {
        this.odometer = odometer;
    }

    public double getConsumption() {
        return consumption;
    }

    public void setConsumption(double consumption) {
        this.consumption = consumption;
    }

    public double getOdemeterForLastOilChange() {
        return odemeterForLastOilChange;
    }
    public String getCreatedAt() {
        return createdAt;
    }
    public void setOdemeterForLastOilChange(double odemeterForLastOilChange) {
        this.odemeterForLastOilChange = odemeterForLastOilChange;
    }

    public double getEngineSize() {
        return engineSize;
    }

    public void setEngineSize(double engineSize) {
        this.engineSize = engineSize;
    }

    protected double getMilesPerGallon(){
        return this.odometer / this.consumption;
    }
}
