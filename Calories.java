import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Calories {
    private String dataPath;
    private int highestCalorie;
    private int highestCalorieElf;

    public Calories(String dataPath) {
        this.dataPath = dataPath;

        int calorie = 0;
        int max = -1;

        try(Scanner scanner = new Scanner(new File(this.dataPath))) {
            int elfIdentity = 0;

            while(scanner.hasNext()) {
                String line = scanner.nextLine();
                if(line.trim().length() > 0) {
                    calorie += Integer.parseInt(line);
                } else {
                    max = Math.max(calorie, max);
                    ++elfIdentity;
                    if (max == calorie) {
                        this.highestCalorieElf =  elfIdentity;
                    }

                    calorie = 0;
                }
            }
        } catch(FileNotFoundException e) {
           System.err.println("File not found in classpath: "+this.dataPath);;
        }

        this.highestCalorie = max;
    }

    public int getElf() {
        return this.highestCalorieElf;
    }

    public int getHighestCalories() {
        return this.highestCalorie;
    }

    public static void main(String args[]) {
        String filePath = "input.txt";
        if (args.length > 0) {
            filePath = args[0];
        }

        Calories computer = new Calories(filePath);
        System.out.println(String.format("Highest Calories: %d, Carried by: %d", computer.getHighestCalories(), computer.getElf()));
    }
}
