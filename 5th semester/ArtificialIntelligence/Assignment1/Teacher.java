import java.util.ArrayList;
import java.util.Objects;

public class Teacher {
	private int id;
	private String name;

	private int dailyHours;
	private int weeklyHours;

	public ArrayList<Integer> lessons = new ArrayList<>();


	public Teacher(int id, String name, int dailyHours, int weeklyHours) {
		this.id = id;
		this.dailyHours = dailyHours;
		this.weeklyHours = weeklyHours;
		this.name = name;
	}
	public Teacher(){}

	/**
	 * Method for clarity . Retruns true if lesson l is in the teachers lesson list
	 */
	public boolean canTeach(Lesson l){
		return lessons.contains(l.getId());
	}
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Teacher teacher = (Teacher) o;
		//Checking that they have they same lessons
		if(this.lessons.size() != teacher.lessons.size()) return false;
		for(int i = 0 ; i < lessons.size(); i++){
			if(!this.lessons.get(i).equals(teacher.getLessons().get(i))) return false;
		}
		return id == teacher.id &&
				dailyHours == teacher.dailyHours &&
				weeklyHours == teacher.weeklyHours &&
				name.equals(teacher.name) &&
				lessons.equals(teacher.lessons);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, dailyHours, weeklyHours, lessons);
	}

	@Override
	public String toString() {
		return "Teacher{" +
				"name='" + name + '\'' +
				'}';
	}

	public String toShortString() {
		return name;
	}

	//BOILERPLATE CODE
	public int getId() {
		return id;
	}

	public int getDailyHours() {
		return dailyHours;
	}

	public int getWeeklyHours() {
		return weeklyHours;
	}

	public String getName() {
		return name;
	}

	public ArrayList<Integer> getLessons() {
		return lessons;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setDailyHours(int dailyHours) {
		this.dailyHours = dailyHours;
	}

	public void setWeeklyHours(int weeklyHours) {
		this.weeklyHours = weeklyHours;
	}

	public void setName(String name) {
		this.name = name;
	}
}
