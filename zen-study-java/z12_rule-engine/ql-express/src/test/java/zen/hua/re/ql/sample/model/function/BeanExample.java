package zen.hua.re.ql.sample.model.function;

public class BeanExample {
    public static String upper(String abc) {
        return abc.toUpperCase();
    }

    public boolean anyContains(String str, String searchStr) {
        char[] s = str.toCharArray();
        for (char c : s) {
            if (searchStr.contains(c + "")) {
                return true;
            }
        }
        return false;
    }
}