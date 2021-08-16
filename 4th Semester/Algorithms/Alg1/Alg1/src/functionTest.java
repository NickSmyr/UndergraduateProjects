import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class functionTest {
    public static void main(String[] args) throws IOException{
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        ArrayList<Integer> newList = new ArrayList<>(list);
        System.out.println(newList);
    }
}
