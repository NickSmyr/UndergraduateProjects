import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class SimpleHillClimb {
    public static State solve(State initialState){
        State currentState = initialState;
        ArrayList<State> children = currentState.getAllChildren();
        for(State s : children) {
            s.calcHeuristic();
        }
        children.sort(new Comparator<State>(){
            @Override
            public int compare(State t1 ,State t2){
                if(t1.heuristic  < t2.heuristic){
                    return -1;
                }
                else if(t1.heuristic  == t2.heuristic){
                    return 0;
                }
                else{
                    return 1;
                }
            }
        });
        while(!(children.get(0).heuristic >= currentState.heuristic) || children.size()==0){
            System.out.println("Current State heuristic is " +  currentState.heuristic);
            //Moving to the child of the node with the least heuristic score
            currentState = children.get(0);
            //Generating children
            children = currentState.getAllChildren();
            for(State s : children) {
                s.calcHeuristic();
            }
            children.sort(new Comparator<State>(){
                @Override
                public int compare(State t1 ,State t2){
                    if(t1.heuristic  < t2.heuristic){
                        return -1;
                    }
                    else if(t1.heuristic  == t2.heuristic){
                        return 0;
                    }
                    else{
                        return 1;
                    }
                }
            });
        }
        System.out.println("------------FOUND A LOCAL MINIMUM---------------");
        System.out.println("Final heuristic score = " + currentState.heuristic);
        return currentState;
    }
    public static void main(String[] args) throws IOException {
        ArrayList<Lesson> allLessons = LessonReader.readLessons("lessons.txt");
        ArrayList<Teacher> allTeachers = TeacherReader.readTeachers("teachers.txt");

        //InitialProblem state
        State initialState = State.createRandomState();
        initialState.calcHeuristic();

        System.out.println(SimpleHillClimb.solve(initialState));

    }
}