import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TeacherReader {
    /**
     * Reads data from a file and outputs a list of Teachers
     * @param file the file from which data will be read
     * @return list of Teacher
     * @throws IOException if there is an IOError
     */
	public static ArrayList <Teacher> readTeachers (String file) throws IOException {
		Scanner sc=new Scanner (new File(file));
		ArrayList <Teacher>result =new ArrayList<>();
		int linenumber=0;
		try {
			while(sc.hasNextLine()) {
				//Every line represents one teacher
				String line = sc.nextLine();
				linenumber++;
				//If the line is a comment we ignore it
				if (line.trim().startsWith("//")) continue;
				
				String[] tokens=line.split(" ");
				int id =Integer.parseInt(tokens[0]);				
				String name=tokens[1];				
				int dailyHours = Integer.parseInt(tokens[2]);
				int weeklyHours = Integer.parseInt(tokens[3]);

				Teacher t =new Teacher(id,name,dailyHours,weeklyHours);
				//while loop to the lessons each teacher can teach
				for(int i = 4 ; i < tokens.length ; i++){
					t.getLessons().add(Integer.parseInt(tokens[i]));
				}
				//Adding teacher to the arraylist
				result.add(t);

			}
			return result;
		}
		catch(Exception e){
	        throw new RuntimeException("Invalid syntax at line: " + linenumber + " of file : " + file);
	    }
	}
	 public static void main(String[] args)throws IOException{
	        ArrayList<Teacher> res = readTeachers ("teachers.txt");
	        System.out.println("done in My teacher Reader");
	 }
}
