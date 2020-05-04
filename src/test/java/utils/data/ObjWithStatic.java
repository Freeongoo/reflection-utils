package utils.data;

/**
 * @author dorofeev
 */
public class ObjWithStatic {
    private Long id;
    private static String PREFIX = "STAT";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static String getPREFIX() {
        return PREFIX;
    }

    public static void setPREFIX(String PREFIX) {
        ObjWithStatic.PREFIX = PREFIX;
    }
}
