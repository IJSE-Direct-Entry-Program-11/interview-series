package lk.ijse.dep11;

public class NestedClassesDemo {

    public static void main(String[] args) {
        var staticNested = new NestedClasses.StaticNestedClasses();
        var regularNested = new NestedClasses().new RegularInnerClasses();
    }
}
