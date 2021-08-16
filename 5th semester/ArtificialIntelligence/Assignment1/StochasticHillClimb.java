import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class StochasticHillClimb {
    /**
     * Hyperparameter maxSteps : maximum number of transitions it checks
     */
    public static State solve(State initialState, int maxSteps){
        //Initial state we start on
        State currentState = initialState;
        currentState.calcHeuristic();
        while(true){
            //currentState.printConstraintSummary();
            // Generating a random child
            State randomChild = currentState.getRandomChild();
            randomChild.calcHeuristic();
            int stepsDone = 0;
            // Until we find a better child
            while(randomChild.heuristic >= currentState.heuristic) {
                randomChild = currentState.getRandomChild();
                randomChild.calcHeuristic();
                stepsDone++;
                if(stepsDone >= maxSteps){
                    System.out.println("------------FOUND A LOCAL MINIMUM---------------");
                    return currentState;
                }
            }
            //We found a better child
            currentState = randomChild;
        }
    }
    public static void main(String[] args) throws IOException {
        //Parsing hyperparameters from the command line
        int MAX_STEPS = Integer.parseInt(args[0]);
        int NRESTARTS = Integer.parseInt(args[1]);
        int SEED = 123;
        if(args.length > 2){
            SEED = Integer.parseInt(args[2]);
        }
        //reading the text files
        ArrayList<Lesson> allLessons = LessonReader.readLessons("lessons.txt");
        ArrayList<Teacher> allTeachers = TeacherReader.readTeachers("teachers.txt");
        //passing infos to State as ArrayList of lessons and teachers
        State.allLessons = allLessons;
        State.allTeachers = allTeachers;
        //Setting the random number generator to the specified seed
        State.random = new Random(SEED);
        //InitialProblem state: create every time a random starting state
        State[] pool=new State[NRESTARTS];
        for(int i=0 ; i< NRESTARTS ;i++){
            System.out.println("Restart number " + i);
            pool[i]=StochasticHillClimb.solve(State.createRandomState(),MAX_STEPS);
        }
        double minHeuristic = 100000;
        State bestState = null;
        for(State drop:pool) {
            System.out.println(drop.heuristic);
            if(drop.heuristic < minHeuristic){
                bestState = drop;
                minHeuristic = drop.heuristic;
            }
        }
        System.out.println("Best state heuristic = " + minHeuristic);
        System.out.println("Best state :");
        System.out.println(bestState);
    }
}