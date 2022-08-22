package USM;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.util.Vector;

public class USM {
    private String name_;
    private Map<String, IntSection> isecs_;
    private Map<String, StringSection> ssecs_;
    private boolean is_opened;
    public USM(final String name) {
        name_ = name;
        Path path = Paths.get("profiles/", name_ + ".uto");
        isecs_ = new HashMap<>();
        ssecs_ = new HashMap<>();
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
            is_opened = false;
        }
        if (!is_opened) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                System.exit(1);
            }
        }
    }
    public USM(String name, int flag) {
        if (flag == 1) {
            try {
                name_ = name;
                isecs_ = new HashMap<>();
                ssecs_ = new HashMap<>();
                Path path = Paths.get("profiles/", name_ + ".uto");
                Files.createFile(path);
            } catch (IOException ignored) {}
        }
    }
    public void to_file() {
        Path path = Paths.get("profiles/", name_ + ".uto");
        try (final OutputStream outputStream = Files.newOutputStream(path)) {
            StringBuilder text_buf = new StringBuilder();

            for (Map.Entry<String, StringSection> entry: ssecs_.entrySet()) {
                text_buf.append("s<").append(entry.getKey()).append(">");
                for (String obj: entry.getValue().getObjects_()) {
                    text_buf.append(obj).append("|<\\e>");
                }
                text_buf.append("\n");
            }

            for (Map.Entry<String, IntSection> entry: isecs_.entrySet()) {
                text_buf.append("i<").append(entry.getKey()).append(">");
                for (Integer obj: entry.getValue().getObjects_()) {
                    text_buf.append(obj).append("|<\\e>");
                }
                text_buf.append("\n");
            }

            outputStream.write(text_buf.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public IntSection geti(final String name) {
        return isecs_.get(name);
    }
    public StringSection gets(final String name) {
        return ssecs_.get(name);
    }
    public void create_isec(String name) {
        isecs_.put(name, new IntSection(name));
    }
    public void create_ssec(String name) {
        ssecs_.put(name, new StringSection(name));
    }
    public final boolean opened() {
        return is_opened;
    }
    public static List<USM> get_profiles() {
        List<USM> profiles = new Vector<>();
        Path path = Paths.get("profiles/profiles_list.txt");
        try {
            for (String s : Files.readAllLines(path, StandardCharsets.UTF_8)) {
                profiles.add(new USM(s));
            }
        } catch (IOException e) {
            try {
                Files.createFile(path);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        return profiles;
    }
}

class Arithmetics {
    public static int computeExpression(String expression) {
        List<StringBuilder> sb = new Vector<>();
        List<Integer> op = new Vector<>();
        int k = -1;
        boolean crStrBld= true;
        for (int i = 0; i < expression.length(); ++i) {
            char c = expression.charAt(i);
            if (c != ' ') {
                if (c == '+') {
                    op.add(0);
                    crStrBld = true;
                }  else if (c == '-') {
                    op.add(1);
                    crStrBld = true;
                }  else if (c == '*') {
                    op.add(2);
                    crStrBld = true;
                }  else if (c == '/') {
                    op.add(3);
                    crStrBld = true;
                } else {
                    if (crStrBld) {
                        sb.add(new StringBuilder());
                        ++k;
                        crStrBld = false;
                    } else {
                        sb.get(k).append(c);
                    }
                }
            }
        }
        int result = Integer.parseInt(sb.get(0).toString());
        for (int i = 1, j = 0; i < sb.size() && j < sb.size() - 1; ++i, ++j) {
            switch (op.get(j)) {
                case 0:
                    result += Integer.parseInt(sb.get(i).toString());
                    break;
                case 1:
                    result -= Integer.parseInt(sb.get(i).toString());
                    break;
                case 2:
                    result *= Integer.parseInt(sb.get(i).toString());
                    break;
                case 3:
                    result /= Integer.parseInt(sb.get(i).toString());
                    break;
            }
        }
        return result;
    }
}