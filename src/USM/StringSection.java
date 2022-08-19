package USM;
import java.util.List;
import java.util.Arrays;

public class StringSection {
    private final String name_;
    private List<String> objects_;
    public StringSection(String name) {
        name_ = name;
    }
    public StringSection(String name, String[] vec) {
        name_ = name;
        objects_ = Arrays.asList(vec);
    }
    public void add(String object) {
        objects_.add(object);
    }
    public int size() {
        return objects_.size();
    }
    public final String get(int index) {
        return objects_.get(index);
    }
}
