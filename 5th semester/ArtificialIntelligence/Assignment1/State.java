import javafx.geometry.Pos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class State {
    /**
     * The lecture for the i'th day on the j'th hour on the grade g and department d will be
     * genes[i + j*HOURS + d*DAYS*HOURS + g*DAYS*HOURS*DEPS]
     *
     * alternative
     *
     * genes[d + g*DEPS + h*DEPS*GRADES + day*HOURS*DEPS*GRADES]
     */
    public State() {
        this.genes = new Gene[DAYS * HOURS * DEPS * GRADES];
        for (int i = 0; i < genes.length; i++) {
            genes[i] = new Gene();
        }
    }
    //Copy constructor
    public State(State other){
        this.genes = new Gene[DAYS * HOURS * DEPS * GRADES];
        for (int i = 0; i < genes.length; i++) {
            genes[i] = other.genes[i];
        }
    }
    Gene[] genes;
    public Gene getGene(int hour,int day,int dep,int grade){
        return genes[hour+day*HOURS+ dep*DAYS*HOURS + grade*DAYS*HOURS*DEPS];
        //Alternative
        //return genes[dep + grade*DEPS + hour*DEPS*GRADES + day*HOURS*DEPS*GRADES];
    }
    public void setGene(int hour,int day,int dep,int grade,Gene gene){
        genes[hour+day*HOURS+ dep*DAYS*HOURS + grade*DAYS*HOURS*DEPS] = gene;
        //Alternative
        //return genes[dep + grade*DEPS + hour*DEPS*GRADES + day*HOURS*DEPS*GRADES];
    }
    public static class Gene {

        Lesson lesson;
        Teacher teacher;

        public Gene() {}
        public Gene(Lesson lesson, Teacher teacher) {
            this.lesson=lesson;
            this.teacher=teacher;
        }
        //Copy constructor
        public Gene(Gene other) {
            this.lesson=other.lesson;
            this.teacher=other.teacher;
        }

    }
    //Static fields
    //Some constants for readability
    static final int DAYS = 5;
    static final int HOURS = 7;
    static final int DEPS= 3;
    static final int GRADES = 3;

    static ArrayList<Lesson> allLessons = null;
    static ArrayList<Teacher> allTeachers = null;
    //We need to initialize with a predefined seed
    static Random random = new Random(123);


    ///IMPORTANT METHODS FOR HILL CLIMB

    /**
     * Transition type 1
     */
    public void swap(int h1,int d1,int h2,int d2,int dep,int g){
        if(h1 > 6 || h1 < 0 || d1 > 4 || d1 < 0 || h2 > 6 || h2 < 0 || d2 > 4 || d2 < 0 || g > 8
                || g < 0){
            throw new RuntimeException("Invalid parameters on swap");
        }
        Gene gene1 = getGene(h1,d1,dep,g);
        Gene gene2 = getGene(h2,d2,dep,g);
        setGene(h1,d1,dep,g,gene2);
        setGene(h2,d2,dep,g,gene1);
    }

    /**
     * Transition type 2
     */
    public void changeTeacher(int h, int d ,int dep , int g , Teacher t){
        if(getGene(h,d,dep,g).lesson == null ||getGene(h,d,dep,g).teacher == null  ){
            throw new RuntimeException("changeteacher on empty lecture");
        }
        if(!getGene(h,d,dep,g).teacher.canTeach(getGene(h,d,dep,g).lesson)){
            throw new RuntimeException("invalid teacher for lesson");
        }
        Gene temp = new Gene(getGene(h,d,dep,g));
        temp.teacher = t;
        setGene(h,d,dep,g,temp);
    }
    public ArrayList<State> getAllChildren(){
        ArrayList<State> children = new ArrayList<>();
        //Generating all children possible with swaps
        //for every grade
        for(int g= 0 ; g < GRADES ; g++) {
            for(int dep= 0 ; dep < DEPS ; dep++) {
                //For every combination of days
                for (int d1 = 0; d1 < DAYS; d1++) {
                    for (int d2 = d1; d2 < DAYS; d2++) {
                        for (int h1 = 0; h1 < HOURS; h1++) {
                            for (int h2 = h1 + 1; h2 < HOURS; h2++) {
                                State child = new State(this);
                                child.swap(h1, d1, h2, d2,dep, g);
                                children.add(child);
                            }
                        }
                    }
                }
            }
        }
        //Generating all children possible with teacher change
        for(int g = 0 ; g < GRADES ; g++) {
            for (int dep = 0; dep < DEPS; dep++) {
                //For every day of the week
                for (int d = 0; d < DAYS; d++) {
                    //For every hour
                    for (int h = 0; h < HOURS; h++) {
                        Gene currentGene = getGene(h, d, dep, g);
                        if (currentGene.lesson == null || currentGene.teacher == null) {
                            continue;
                        }
                        ArrayList<Teacher> possibleTeachers = new ArrayList<>();
                        for (Teacher t : allTeachers) {
                            possibleTeachers.add(t);
                        }
                        for (Teacher t : possibleTeachers) {
                            State child = new State(this);
                            //System.out.printf("Generating changeteach on hour %d day %d grade %d with teacher %s\n", k, j, i, t.getName());
                            child.changeTeacher(h, d, dep, g, t);
                            child.calcHeuristic();
                            children.add(child);
                        }
                    }
                }
            }
        }
        return children;
    }
    public State getRandomChild(){
        //random variable to decide the type of transition used
        State child = new State(this);
        int transition = random.nextInt(2);
        if(transition == 1){
            //swapping random lessons
            int h1 = random.nextInt(7);
            int h2 = random.nextInt(7);
            int d1 = random.nextInt(5);
            int d2 = random.nextInt(5);
            int dep = random.nextInt(3);
            int grade = random.nextInt(3);
            child.swap(h1,d1,h2,d2,dep,grade);
        }
        else{
            //Changing the lessons teacher
            int h = random.nextInt(7);
            int d = random.nextInt(5);
            int dep = random.nextInt(3);
            int grade = random.nextInt(3);
            Gene randomGene = getGene(h,d,dep,grade);
            if(randomGene.lesson == null || randomGene.teacher == null){
                return child;
            }
            ArrayList<Teacher> possibleTeachers = new ArrayList<Teacher>();
            for(Teacher t : allTeachers){
                if(t.canTeach(randomGene.lesson)){
                    possibleTeachers.add(t);
                }
            }
            int randomTeacher = random.nextInt(possibleTeachers.size());
            Teacher selectedTeacher = possibleTeachers.get(randomTeacher);
            child.changeTeacher(h,d,dep,grade,selectedTeacher);

        }
        return child;

    }
    double heuristic;
    public void calcHeuristic(){
        //constraints31-35 and 21-22 and 12
        heuristic = 100 * (constraint11()+constraint12()+constraint13() +constraint14() )+
                10 * ( constraint21() + constraint22() ) +
                constraint31() + constraint32() + constraint33() + constraint34() + constraint35();
    }
    //INITIALIZATION ETC
    /**
     * Creates a random initial state ( the state will have all the appropriate lesson hours but with random teachers)
     **/
    public static State createRandomState() {
        State result = new State();
        Gene[] resultGenes  = result.genes;
        for(int d = 0 ; d < DEPS ; d++){
            for(int g = 0 ; g < GRADES; g++){
                //FILL THE DEPARTMENT LECTURES
                ArrayList<Lesson> departmentLectures = new ArrayList<>();
                for(Lesson l : allLessons){
                    if(l.getGrade() == g){
                        for(int i = 0 ; i < l.getHours() ; i++){
                            departmentLectures.add(l);
                        }
                    }
                }
                while(departmentLectures.size() < HOURS*DAYS){
                    departmentLectures.add(null);
                }
                //Deciding who will teach each subject
                HashMap<Lesson,Teacher> whoTeaches = new HashMap<>();
                for(Lesson l : allLessons){
                    ArrayList<Teacher> possibleTeachers = new ArrayList<>();
                    for(Teacher t: allTeachers){
                        if (t.canTeach(l)) {
                            possibleTeachers.add(t);
                        }

                    }
                    int randomTeacher = random.nextInt(possibleTeachers.size());
                    whoTeaches.put(l,possibleTeachers.get(randomTeacher));
                }
                //Filling the result's schedule
                for(int day = 0 ; day < DAYS ; day++){
                    for(int hour= 0; hour < HOURS ; hour++){
                        Gene targetGene = result.getGene(hour,day,d,g);
                        int randomLecture = random.nextInt(departmentLectures.size());
                        Lesson randomLesson = departmentLectures.get(randomLecture);
                        departmentLectures.remove(randomLecture);
                        if(randomLesson == null){
                            targetGene.lesson= null;
                            targetGene.teacher= null;
                        }
                        else{
                            targetGene.lesson=randomLesson;
                            targetGene.teacher= whoTeaches.get(randomLesson);
                        }
                    }
                }
            }
        }
        return result;
    }
    //Auxillary methods
    @Override
    public String toString(){
        String result = "";
        for(int g = 0 ; g < GRADES ; g++){
            for(int d = 0 ; d < DEPS ; d++){
                result += String.format("Schedule for grade %d , department %d\n",g,d);
                result += scheduleString(g,d);
            }
            System.out.println();
        }
        return result;
    }
    //Lower level utility methods
    public String scheduleString(int grade,int department){
        //grade must be int [0,2]
        //department must be int [0,2]
        String result = "";
        for(int i = 0 ; i < HOURS ; i++){
            for(int j = 0 ; j < DAYS ; j++){
                Gene currGene = getGene(i,j,department,grade);
                String lessonString = currGene.lesson == null? "NONE" :currGene.lesson.getName();
                String teacherString = currGene.teacher ==null? "NONE":currGene.teacher.getName();
                result += "( "+ lessonString + "- "+ teacherString +" ) ";
            }
            result += "\n";
        }
        return result;
    }
    public static void main(String[] args) throws IOException {
        State.allLessons = LessonReader.readLessons("lessons.txt");
        State.allTeachers = TeacherReader.readTeachers("teachers.txt");
        State res = State.createRandomState();
        System.out.println(res);
    }
    ///Constraints
    //1 level : validity checks
    //	1.1 Every teacher teaches at most one lesson at a time
    // 	1.2 Every lesson scheduled can be taught by the teacher scheduled to teach it
    //	1.3 Every lesson is taught for the appropriate amount of hours on every grade
    // 	1.4 At every hour of the schedule there can't be a lesson scheduled without a teacher and neither
    //		a teacher scheduled without a lesson

    //2 level: basic constrains
    // 2.1 Every teacher teaches for at most his daily hours limit per day
    // 2.2 Every teacher teaches for at most his weekly hours limit per week

    //3 level: soft constraints
    // 3.1 no empty lecture hours
    // 3.2 no teacher teaches for more than 2 consecutive hours
    // 3.3 every department's school hours are equally distributed within the week
    // 3.4 Every lesson's hours are equally distributed within the week
    // 3.5 Lecture hours for every teacher are equally distributed within the week
    public int constraint11(){
        int result = 0;
        for(Teacher t : allTeachers){
            for(int day = 0 ; day < DAYS ; day++){
                for(int h = 0 ; h < HOURS ; h++){
                    boolean alreadyTaught = false;
                    for(int d = 0 ; d < DEPS ; d++){
                        for(int g = 0 ; g < GRADES ; g++){
                            Teacher currTeacher = getGene(h,day,d,g).teacher;
                            if(currTeacher == null) continue;
                            if(currTeacher.equals(t)) {
                                if (!alreadyTaught) alreadyTaught = true;
                                else result++;
                            }
                        }
                    }
                }
            }
        }
        return result;
    }
    public int constraint12(){
        int result = 0;
        for(int day = 0 ; day < DAYS ; day++){
            for(int h = 0 ; h < HOURS ; h++){
                for(int d = 0 ; d < DEPS ; d++){
                    for(int g = 0 ; g < GRADES ; g++){
                        Teacher currTeacher = getGene(h,day,d,g).teacher;
                        Lesson currLesson = getGene(h,day,d,g).lesson;
                        if(currTeacher == null || currLesson == null) continue;
                        if(!currTeacher.canTeach(currLesson)) result += 1;
                    }
                }
            }
        }
        return result;
    }
    public int constraint13(){
        int result = 0;
        for(Lesson l : allLessons){
            for(int g = 0 ; g < GRADES ; g++){
                //If the lesson is not taught in this grade we ignore this grades schedule
                if(l.getGrade() != g) continue;
                for(int d = 0 ; d < DEPS ; d++){
                    int hoursTaughtForThisDep=  0;
                    for(int day = 0 ; day < DAYS ; day++){
                        for(int h = 0 ; h < HOURS ; h++){
                            Teacher currTeacher = getGene(h,day,d,g).teacher;
                            Lesson currLesson = getGene(h,day,d,g).lesson;
                            if(currTeacher == null || currLesson == null) continue;
                            if(currLesson.equals(l)) hoursTaughtForThisDep++;
                        }
                    }
                    //System.out.printf("Hours taught for lesson %s in dep %d :  %d , ideal %d ,\n" , l.getName(),d,hoursTaughtForThisDep,l.getHours() );
                    //System.out.println("Result before = " + result);
                    result += Math.abs(hoursTaughtForThisDep - l.getHours());
                    //System.out.println("Result after = " + result);
                }
            }
        }
        return result;
    }
    public int constraint14(){
        int result = 0;
        for(int g = 0 ; g < GRADES ; g++){
            for(int d = 0 ; d < DEPS ; d++){
                for(int day = 0 ; day < DAYS ; day++){
                    for(int h = 0 ; h < HOURS ; h++){
                        Teacher currTeacher = getGene(h,day,d,g).teacher;
                        Lesson currLesson = getGene(h,day,d,g).lesson;
                        if(currTeacher == null && currLesson == null) continue;
                        if(currTeacher != null && currLesson != null) continue;
                        //Lesson = null and teacher != null or reverse
                        result++;
                    }
                }
            }
        }

        return result;
    }
    //2nd Level Constraints
    //Daily hour limit
    public int constraint21(){
        ArrayList<Integer>kapos=new ArrayList<>();
        int result = 0;
        for(Teacher t : allTeachers){
            for(int day = 0 ; day < DAYS ; day++){
                int hoursTaughtInTheDay = 0;
                for(int h = 0 ; h < HOURS ; h++){
                    for(int d = 0 ; d < DEPS ; d++){
                        for(int g = 0 ; g < GRADES ; g++){
                            Teacher currTeacher = getGene(h,day,d,g).teacher;
                            if(currTeacher == null) continue;
                            if(currTeacher.equals(t)) {
                                hoursTaughtInTheDay++;
                            }
                        }
                    }
                }
                //Adding the difference to the result
                if(hoursTaughtInTheDay > t.getDailyHours()){
                    kapos.add(t.getId());
                    //System.out.println("ID= "+t.getId() +" NAME = "+t.getName());
                    //result+= hoursTaughtInTheDay - t.getDailyHours();
                    result+=20;
                }
            }

        }
        //for (Integer inte: kapos){
           // System.out.println(inte);
      //  }
        return result;
    }
    //Weekly hour limit
    public int constraint22(){
        int result = 0;
        for(Teacher t : allTeachers){
            int hoursTaughtInTheWeek = 0;
            for(int day = 0 ; day < DAYS ; day++) {
                for (int h = 0; h < HOURS; h++) {
                    for (int d = 0; d < DEPS; d++) {
                        for (int g = 0; g < GRADES; g++) {
                            Teacher currTeacher = getGene(h, day, d, g).teacher;
                            if (currTeacher == null) continue;
                            if (currTeacher.equals(t)) {
                                hoursTaughtInTheWeek++;
                            }
                        }
                    }
                }
            }
            //Adding the difference to the result
            if(hoursTaughtInTheWeek > t.getWeeklyHours()){
               // result+= hoursTaughtInTheWeek - t.getWeeklyHours();
                result+=20;
            }

        }
        return result;
    }
    //Level 3 Constraints
    //empty lecture hours
    public int constraint31(){
        int result = 0;
        for(int g = 0 ; g < GRADES ; g++){
            for(int d = 0 ; d < DEPS ; d++){
                for(int day = 0 ; day < DAYS ; day++){
                    boolean firstLessonConducted = false;
                    int emptyLectureStreak = 0;
                    for(int h = 0 ; h < HOURS ; h++){
                        Teacher currTeacher = getGene(h,day,d,g).teacher;
                        Lesson currLesson = getGene(h,day,d,g).lesson;
                        //Empty lesson
                        if(currTeacher == null && currLesson == null){
                            if(firstLessonConducted){
                                emptyLectureStreak++;
                            }
                        }
                        else{
                            if(!firstLessonConducted) firstLessonConducted = true;
                            result += emptyLectureStreak;
                            emptyLectureStreak = 0;
                        }
                    }
                }
            }
        }

        return result;
    }
    //Teacher consecutive hours
    public int constraint32(){
        int result = 0;
        for(Teacher t : allTeachers){
            int hoursTaughtInTheWeek = 0;
            for(int day = 0 ; day < DAYS ; day++) {
                int consecutiveHours = 0;
                for (int h = 0; h < HOURS; h++) {
                    boolean taughtInThisHour = false;
                    for (int d = 0; d < DEPS; d++) {
                        for (int g = 0; g < GRADES; g++) {
                            Teacher currTeacher = getGene(h, day, d, g).teacher;
                            if (currTeacher == null) continue;
                            if (currTeacher.equals(t)) {
                                taughtInThisHour = true;
                            }
                        }
                    }
                    if(taughtInThisHour){
                        consecutiveHours++;
                        if(consecutiveHours>2){
                            result++;
                        }
                    }
                    else{
                        consecutiveHours = 0;
                    }
                }
            }

        }
        return result;
    }
    //Uniform  distribution of department school hours
    public double constraint33(){
        double result = 0;
        for(int g = 0 ; g < GRADES ; g++){
            for(int d = 0 ; d < DEPS ; d++){
                int[] hoursPerDay  = new int[5];
                for(int day = 0 ; day < DAYS ; day++){
                    int firstLessonHour = -1;
                    int lastLessonHour = -1;
                    for(int h = 0 ; h < HOURS ; h++){
                        Teacher currTeacher = getGene(h,day,d,g).teacher;
                        Lesson currLesson = getGene(h,day,d,g).lesson;
                        if(currLesson != null && currTeacher != null){
                            if(firstLessonHour == -1){
                                firstLessonHour = h;
                            }
                            lastLessonHour = h;
                        }
                    }
                    //Empty day
                    if(firstLessonHour == -1){
                        hoursPerDay[day] = 0;
                    }
                    else {
                        hoursPerDay[day] = lastLessonHour - firstLessonHour + 1;
                    }
                }
                //Adding the variance of school hours for the ceratin department to the result
                result += Utils.var(hoursPerDay);
            }
        }

        return result;
    }
    //Uniform distribution of a lesson within its departments that is being taught
    public double constraint34(){
        double result = 0;
        for(Lesson l : allLessons){
            for(int g = 0 ; g < GRADES ; g++){
                //If the lesson is not taught in this grade we ignore this grades schedule
                if(l.getGrade() != g) continue;
                for(int d = 0 ; d < DEPS ; d++){
                    int hoursPerDay[] = new int[5];
                    for(int day = 0 ; day < DAYS ; day++){
                        int hoursTaughtThisDay = 0;
                        for(int h = 0 ; h < HOURS ; h++){
                            Teacher currTeacher = getGene(h,day,d,g).teacher;
                            Lesson currLesson = getGene(h,day,d,g).lesson;
                            if(currTeacher == null || currLesson == null) continue;
                            if(currLesson.equals(l)) hoursTaughtThisDay++;
                        }
                        hoursPerDay[day] = hoursTaughtThisDay;
                    }
                    //Adding the variance of the distribution of hours of the lesson in the departments week in the result
                    result += Utils.var(hoursPerDay);
                }
            }
        }
        return result;
    }
    //Uniform distribution of lecture hours across the week for every teacher
    public double constraint35(){
        double result = 0;
        for(Teacher t : allTeachers){
            int hoursPerDay[] = new int[5];
            for(int day = 0 ; day < DAYS ; day++) {
                for (int h = 0; h < HOURS; h++) {
                    boolean taughtInThisHour = false;
                    for (int d = 0; d < DEPS; d++) {
                        for (int g = 0; g < GRADES; g++) {
                            Teacher currTeacher = getGene(h, day, d, g).teacher;
                            if (currTeacher == null) continue;
                            if (currTeacher.equals(t)) {
                                taughtInThisHour = true;
                            }
                        }
                    }
                    if(taughtInThisHour){
                        hoursPerDay[day]++;
                    }

                }
            }
            result += Utils.var(hoursPerDay);

        }
        return result;
    }
    public void printConstraintSummary(){
        System.out.printf(" constraint11() %d + constraint12() %d + constraint13() %d + constraint14() %d + constraint21()%d + constraint22()%d + constraint31() %d + constraint32() %d  + constraint33() %.2f + constraint34() %.2f + constraint35() %.2f\n" ,
               constraint11(),constraint12(), constraint13() ,constraint14(), constraint21() , constraint22() ,
                constraint31() , constraint32() , constraint33() , constraint34() , constraint35());
    }
}
