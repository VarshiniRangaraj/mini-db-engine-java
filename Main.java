import java.io.*;
import java.util.*;

public class Main {

    static final String FILE_NAME = "database.txt";

    public static void insertRecord(String record) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true));
        bw.write(record);
        bw.newLine();
        bw.close();
        System.out.println("Record inserted.");
    }

    public static void selectAll() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
        String line;
        System.out.println("\n--- Records ---");
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
        br.close();
    }

    public static void selectWhere(String condition) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
        String line;
        System.out.println("\n--- Filtered Records ---");

        while ((line = br.readLine()) != null) {
            if (line.contains(condition)) {
                System.out.println(line);
            }
        }

        br.close();
    }

    public static void deleteWhere(String condition) throws IOException {
        File inputFile = new File(FILE_NAME);
        File tempFile = new File("temp.txt");

        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));

        String line;

        while ((line = br.readLine()) != null) {
            if (!line.contains(condition)) {
                bw.write(line);
                bw.newLine();
            }
        }

        br.close();
        bw.close();

        inputFile.delete();
        tempFile.renameTo(inputFile);

        System.out.println("Records deleted.");
    }

    public static void updateWhere(String condition, String newData) throws IOException {
        File inputFile = new File(FILE_NAME);
        File tempFile = new File("temp.txt");

        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));

        String line;

        while ((line = br.readLine()) != null) {
            if (line.contains(condition)) {
                bw.write(newData);
            } else {
                bw.write(line);
            }
            bw.newLine();
        }

        br.close();
        bw.close();

        inputFile.delete();
        tempFile.renameTo(inputFile);

        System.out.println("Records updated.");
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        System.out.println("Mini Database Engine (Type EXIT to quit)");

        while (true) {
            System.out.print("\nEnter Query: ");
            String input = sc.nextLine().trim();

            if (input.startsWith("INSERT")) {
                String record = input.substring(7).trim();
                insertRecord(record);

            } else if (input.equals("SELECT")) {
                selectAll();

            } else if (input.startsWith("SELECT")) {
                String condition = input.substring(7).trim();
                selectWhere(condition);

            } else if (input.startsWith("DELETE")) {
                String condition = input.substring(7).trim();
                deleteWhere(condition);

            } else if (input.startsWith("UPDATE")) {
                try {
                    String[] parts = input.split("SET");
                    String condition = parts[0].replace("UPDATE", "").trim();
                    String newData = parts[1].trim();
                    updateWhere(condition, newData);
                } catch (Exception e) {
                    System.out.println("Invalid UPDATE syntax.");
                }

            } else if (input.equals("EXIT")) {
                System.out.println("Exiting...");
                break;

            } else {
                System.out.println("Invalid Query.");
            }
        }

        sc.close();
    }
}