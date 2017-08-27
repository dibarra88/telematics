import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        VehicleInfo newVehicle = new VehicleInfo();
        TelematicsService tel = new TelematicsService();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter VIN :");
        newVehicle.setVIN(Integer.parseInt(scanner.nextLine()));
        System.out.println("Enter odometer:");
        newVehicle.setOdometer(Double.parseDouble(scanner.nextLine()));
        System.out.println("Enter consumption:");
        newVehicle.setConsumption(Double.parseDouble(scanner.nextLine()));
        System.out.println("Enter odemeter for last oil change:");
        newVehicle.setOdemeterForLastOilChange(Double.parseDouble(scanner.nextLine()));
        System.out.println("Enter engine size:");
        newVehicle.setEngineSize(Double.parseDouble(scanner.nextLine()));

        tel.report(newVehicle);


    }
}
