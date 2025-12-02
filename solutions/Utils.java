package solutions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Utils {
    public static ArrayList<String> getAllInputLines(String fileName) {
        ArrayList<String> toReturn = new ArrayList<>();

        try {
            Scanner sc = new Scanner(new File("./inputs/" + fileName));
            while (sc.hasNextLine()) {
                toReturn.add(sc.nextLine());
            }

            sc.close();
        } catch (IOException e) {
            System.out.println("Sorry, couldn't open that file. \nException trace:\n" + e);
        }
        
        return toReturn;
    }

    public static ArrayList<String> getAllLinesSeperatedByComma(String fileName) {
        ArrayList<String> toReturn = new ArrayList<>();
        try {
            Scanner sc = new Scanner(new File("./inputs/" + fileName));
            
            // there should be a single line here.
            String singleLine = sc.nextLine();
            String[] split = singleLine.split(",");

            for (String s : split) {
                toReturn.add(s);
            }

            sc.close();
        } catch (IOException e) {
            System.out.println("Sorry, couldn't open that file. \nException trace:\n" + e);
        }
        
        return toReturn;
    }
}
