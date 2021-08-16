import java.util.Objects;

public class Lesson {


    private int id;
    private String  name;
    private int grade;
    private int hours;


    public Lesson(int id, String name, int grade , int hours) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.hours = hours;
    }
    public Lesson(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return id == lesson.id &&
                grade == lesson.grade &&
                hours == lesson.hours &&
                name.equals(lesson.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, grade, hours);
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "name='" + name + '\'' +
                "id='" + id + '\'' +
                "grade='" + grade + '\'' +
                "hours='" + hours + '\'' +
                '}';
    }
    public String toShortString() {
        return name;
    }

    public String getName() {
        return this.name;
    }
    public int getId() {
        return this.id;
    }
    public int getHours() {
        return this.hours;
    }
    public int getGrade(){ return  this.grade;}
    public void setGrade(int grade) {
        this.grade=grade;
    }
    public void setHours(int hours) {
        this.hours=hours;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setId(int id) {
        this.id = id;
    }
    //Constants for the enum c
    public static final int A = 0;
    public static final int B = 1;
    public static final int C = 2;

}
