import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Scanner;

public class TelematicsService {

    public void report(VehicleInfo vehicleInfo) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(vehicleInfo);

            File file = new File(vehicleInfo.getVIN() + ".json");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(json);
            fileWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        jsonRead();
    }

    private void jsonRead() {
        DecimalFormat df = new DecimalFormat("#.#");
        ObjectMapper mapper = new ObjectMapper();
        int carCnt = 0;
        double odometerSum = 0;
        double consumeSum = 0;
        double oilSum = 0;
        double engineSum = 0;
        String history = "";
        String newHistLine = "";
        String histTemplate = "<tr>\n" +
                "<td align=\"center\">$vin</td>" +
                "<td align=\"center\">$odometer</td>" +
                "<td align=\"center\">$consume</td>" +
                "<td align=\"center\">$oil</td>" +
                "<td align=\"center\">$engine</td>" +
                "<td align=\"center\">$mpg</td>\n" +
                "<td align=\"center\">$time</td>" +
                "</tr>";

        File file = new File(".");
        try {
            for (File f : file.listFiles()) {
                if (f.getName().endsWith(".json")) {
                    Scanner fileScanner = new Scanner(f);
                    VehicleInfo vi = mapper.readValue(fileScanner.nextLine(), VehicleInfo.class);
                    newHistLine = histTemplate.replace("$vin", String.valueOf(vi.getVIN()));
                    newHistLine = newHistLine.replace("$odometer", String.valueOf(df.format(vi.getOdometer())));
                    newHistLine = newHistLine.replace("$consume", String.valueOf(df.format(vi.getConsumption())));
                    newHistLine = newHistLine.replace("$oil", String.valueOf(df.format(vi.getOdemeterForLastOilChange())));
                    newHistLine = newHistLine.replace("$engine", String.valueOf(df.format(vi.getEngineSize())));
                    newHistLine = newHistLine.replace("$mpg", String.valueOf(df.format(vi.getMilesPerGallon())));
                    newHistLine = newHistLine.replace("$time", vi.getCreatedAt());

                    odometerSum = odometerSum + vi.getOdometer();
                    consumeSum = consumeSum + vi.getConsumption();
                    oilSum = oilSum + vi.getOdemeterForLastOilChange();
                    engineSum = engineSum + vi.getEngineSize();
                    carCnt++;
                    history = history + newHistLine;
                }
            }
            writeDashboard(carCnt, odometerSum / carCnt, consumeSum / carCnt, oilSum / carCnt, engineSum / carCnt, history);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void writeDashboard(int numVehicles, double odometerAvg, double consumeAvg, double oilAvg, double engineAvg, String history) {
        String dashboard = "";
        String htmlString = getFileContents("dashboardTemplate.html");
        DecimalFormat df = new DecimalFormat("#.#");

        dashboard = htmlString.replace("$numVehicles", String.valueOf(numVehicles));
        dashboard = dashboard.replace("$odometerAvg", String.valueOf(df.format(odometerAvg)));
        dashboard = dashboard.replace("$consumeAvg", String.valueOf(df.format(consumeAvg)));
        dashboard = dashboard.replace("$oilAvg", String.valueOf(df.format(oilAvg)));
        dashboard = dashboard.replace("$engineAvg", String.valueOf(df.format(engineAvg)));
        dashboard = dashboard.replace("$history", history);
        try {
            File file = new File("dashboard.html");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(dashboard);
            fileWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }

    public static String getFileContents (String fileName) {
        File file = new File (fileName);
        try {
            Scanner fileScanner = new Scanner(file);
            String fileContents = "";
            while (fileScanner.hasNext()) {
                fileContents = fileContents + fileScanner.nextLine();
            }
            return fileContents; //Converts the list to an array
        } //Since this time we are asking for data back from the file
        //We have to specify what to do if it isn't found
        catch (FileNotFoundException ex) {
            System.out.println("Could not find file *" + fileName + "*");
            ex.printStackTrace();
            return null;
        }
    }
}
