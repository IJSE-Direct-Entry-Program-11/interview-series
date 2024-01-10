import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class FailSafeVsFailFastDemo {

    public static void main(String[] args) {
        List<String> names = new CopyOnWriteArrayList<>();
        names.add("Kasun");
        names.add("Nuwan");
        names.add("Supun");
        names.add("Ruwan");

//        for (int i = 0; i < names.size(); i++) {
//            System.out.println(names.get(i));
//            names.remove(i);
//        }

        for (String name : names) {
            System.out.println(name);
            names.remove(name);
        }


    }
}
