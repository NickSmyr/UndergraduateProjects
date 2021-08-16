import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class LessonReader {
    /**
     * Reads data from a file and outputs a list of lessons
     * @param file the file from which data will be read
     * @return list of Lessons
     * @throws IOException if there is an IOError
     */
    public static ArrayList<Lesson> readLessons(String file) throws IOException {
        Scanner sc = new Scanner(new File(file));
        ArrayList<Lesson> result = new ArrayList<>();
        int lineNumber = 0;
        try {
            //If at any point the syntax is unexpected then an exception is thrown
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                lineNumber++;
                //If the line is a comment
                if (line.trim().startsWith("//")) continue;

                String[] tokens = line.split(" ");
                int id = Integer.parseInt(tokens[0]);
                String name = tokens[1];
                int grade = -1;
                switch (tokens[2]) {
                    case "A":
                        grade = Lesson.A;
                        break;
                    case "B":
                        grade = Lesson.B;
                        break;
                    case "C":
                        grade = Lesson.C;
                        break;
                    default:
                        throw new RuntimeException("Invalid syntax at line:" + lineNumber + " of file :" + file);
                }
                int hours = Integer.parseInt(tokens[3]);
                result.add(new Lesson(id,name,grade,hours));
            }
            return result;
        }
        catch(Exception e){
            throw new RuntimeException("Invalid syntax at line:" + lineNumber + " of file :" + file);
        }

    }
    public static void main(String[] args)throws IOException{
        ArrayList<Lesson> res = readLessons("lessons.txt");

        System.out.println("done");
    }
}
