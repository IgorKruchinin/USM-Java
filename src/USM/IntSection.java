package USM;
import java.util.List;
import java.util.Arrays;

public class IntSection {
    private final String name_;
    private List<Integer> objects_;
    public IntSection(String name) {
        name_ = name;
    }
    public IntSection(String name, Integer[] vec) {
        name_ = name;
        objects_ = Arrays.asList(vec);
    }
    public void add(int object) {
        objects_.add(object);
    }
    public int size() {
        return objects_.size();
    }
    public final int get(int index) {
        return objects_.get(index);
    }
}
