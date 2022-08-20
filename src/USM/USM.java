package USM;
import java.util.HashMap;
import java.util.Map;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;

public class USM {
    private String name_;
    private Map<String, IntSection> isecs_;
    private Map<String, StringSection> ssecs_;
    private boolean is_opened;
    public USM(final String name) {
        name_ = name;
        Path path = Paths.get("profiles/", name_ + ".uto");
        isecs_ = new HashMap<String, IntSection>();
        ssecs_ = new HashMap<String, StringSection>();
        try {
            is_opened = true;
            for (String s: Files.readAllLines(path, StandardCharsets.UTF_8)) {
                if (s.charAt(0) == 'i') {
                    IntSection auto = new IntSection("auto");
                    auto.parse(s);
                    isecs_.put(auto.get_name(), auto);
                } else if (s.charAt(0) == 's') {
                    StringSection auto = new StringSection("auto");
                    auto.parse(s);
                    ssecs_.put(auto.get_name(), auto);
                }
            }
        } catch (java.io.IOException | USMSectionException e) {
            System.exit(1);
        }

    }
    public void to_file() {

    }
    public IntSection geti(final String name) {
        return isecs_.get(name);
    }
    public StringSection gets(final String name) {
        return ssecs_.get(name);
    }
    public void create_isec(String name) {

    }
    public void create_ssec(String name) {

    }
    public final boolean opened() {
        return is_opened;
    }
}
