import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class TestField {

    private static String str;
    private static Person person;

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public static void main(String[] args) throws NoSuchFieldException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //判断哪个最小的没被使用
//        Class<?> c = Class.forName("TestField");
//        Field field = c.getDeclaredField("str");
//        Constructor con = c.getConstructor();
//        Object obj = con.newInstance();
//        field.set(obj,"xxx");
//        System.out.println(str);
        Class c = new TestField().getClass();
        Field f = c.getDeclaredField("person");
        f.set(c, new Person("xxxx"));
        System.out.println(person.getName());
        Map<Integer,String> map = new HashMap<>();
        map.put(1,"a");
        map.put(1,"b");
        System.out.println(map.get(1));
    }

}
