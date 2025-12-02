import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVCombiner {

    public static void combineCSVs(String[] csvNames, String finalCSVName) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(finalCSVName))) {
            for (String csvFile : csvNames) {
                try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
                    
                    String line;
                    boolean headerSkipped = false;
                    while ((line = reader.readLine()) != null) {
                        if (!headerSkipped) {
                            headerSkipped = true;
                            continue;
                        }
                        writer.write(line);
                        writer.newLine();
                    }
                } catch (IOException e) {
                    System.out.println("Error reading file: " + csvFile);
                } 
            }
        } catch (IOException e) {
            System.out.println("Error writing to final output file. " + finalCSVName);
        }
    }

    public static void sortCSV(String csvName) {
        List<String> pLines = new ArrayList<>();
        List<String> lLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(csvName))) {
            pLines = new ArrayList<>();
            lLines = new ArrayList<>();

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("\"P")) {
                    pLines.add(line);
                } else if (line.startsWith("\"L")) {
                    lLines.add(line);
                }
            }

            reader.close();
        }
        catch (IOException e) {
            System.out.println("File could not be found. " + csvName);
        }

       try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvName))) {

            for (String s : pLines) {
                writer.write(s);
                writer.newLine();
            }

            for (String s : lLines) {
                writer.write(s);
                writer.newLine();
            }

            writer.close();
       }
       catch (IOException e) {
        System.out.println("File could not be found. " + csvName);
       }

        
    }
}
