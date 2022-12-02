import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Calories {
    private String dataPath;
    List<Elf> elfs = new ArrayList<>();

    public Calories(String dataPath) {
        this.dataPath = dataPath;

        try(Scanner scanner = new Scanner(new File(this.dataPath))) {
            int elfIdentity = 0;
            int calorie = 0;
            while(scanner.hasNext()) {                
                String line = scanner.nextLine();
                if(line.trim().length() > 0) {
                    calorie += Integer.parseInt(line);
                } else {
                    ++elfIdentity;
                    elfs.add(new Elf(elfIdentity, calorie));
                    calorie = 0;
                }
            }

            // sort the elfs in descending order of calories carried
            this.elfs.sort((e1, e2) -> Integer.compare(e2.getCalories(), e1.getCalories()));
        } catch(FileNotFoundException e) {
           System.err.println("File not found in classpath: "+this.dataPath);;
        }
    }

    public List<Elf> getTopNElves(int n) {
        return this.elfs.subList(0, n);
    }

    public List<Integer> getTopNCarries(int n) {
        return this.getTopNElves(n).stream().map(e -> e.id).collect(Collectors.toList());
    }

    public List<Integer> getTopNCalories(int n) {
        return this.getTopNElves(n).stream().map(e -> e.getCalories()).collect(Collectors.toList());
    }

    public int getTopNTotalCalories(int n) {
        return this.getTopNElves(n).stream().map(e -> e.getCalories()).collect(Collectors.summingInt(e -> e));
    }

    class Elf{
        private int id;
        private int totalCalories;

        public Elf(int id, int totalCalories) {
            this.id = id;
            this.totalCalories = totalCalories;
        }

        public int getId() {
            return id;
        }

        public int getCalories() {
            return totalCalories;
        }
    }

    public static void main(String args[]) {
        String filePath = "input.txt";
        int n = 3;

        if (args.length > 0) {
            filePath = args[0];
        }

        if (args.length > 1) {
            n = Integer.parseInt(args[1]);
        }

        Calories computer = new Calories(filePath);
        System.out.println(String.format("Total top %d Calories: %d", n, computer.getTopNTotalCalories(n)));
    }
}
