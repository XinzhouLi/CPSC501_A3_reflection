import javax.lang.model.type.NullType;
import java.lang.reflect.*;

/**
 * CPSC 501
 * Inspector starter class
 *
 * @author Jonathan Hudson
 */
public class Inspector {

    public void inspect(Object obj, boolean recursive) throws IllegalAccessException {
        Class c = obj.getClass();
        inspectClass(c, obj, recursive, 0);
    }

    private void inspectClass(Class c, Object obj, boolean recursive, int depth) throws IllegalAccessException {
        if (c == null){
            return;
        }
        inspectName(c, obj, recursive, depth);
        inspectSuperclass(c, obj, recursive, depth);
        inspectInterface(c, obj, recursive, depth);
        inspectConstructor(c, obj, recursive, depth);
        inspectMethod(c, obj, recursive, depth);
        inspectFields(c, obj, recursive, depth);
    }


    private void inspectName(Class c, Object obj, boolean recursive, int depth){
        String name = c.getName();
        System.out.println(dent(depth) + "CLASS\n" + dent(depth)+"Class: "+name);
    }

    private void inspectInterface(Class c, Object obj, boolean recursive, int depth) throws IllegalAccessException {
        Class[] interfaceA = c.getInterfaces();
        for (int i = 0; i < interfaceA.length; i++) {

            if (interfaceA[i] == Object.class && c == Object.class){
                System.out.println(	dent(depth)+"INTERFACES( java.lang.Object )\nInterfaces-> NONE\n");
                return;
            }else {
                System.out.println(dent(depth)+"INTERFACES( "+interfaceA[i]+" )\n"+dent(depth)+"Interfaces-> \n");
                inspectClass(interfaceA[i],null, recursive, depth+1);
            }
        }
    }

    private void inspectSuperclass(Class c, Object obj, boolean recursive, int depth) throws IllegalAccessException {
        Class superC = c.getSuperclass();
        if (superC == Object.class && c == Object.class) {
            System.out.println(dent(depth)+"SuperClass: NONE");
            return;
        }else {
            System.out.println(dent(depth) +"SUPERCLASS -> Recursively Inspect\n"  + dent(depth)+superC);
            inspectClass(superC, null, recursive,  depth+1);
        }
    }

    private void inspectConstructor(Class c, Object obj, boolean recursive, int depth){
        Constructor[] constructorA = c.getDeclaredConstructors();
        for (int i = 0; i < constructorA.length; i++) {
            Constructor temp =  constructorA[i];
            System.out.println(dent(depth) +temp.getName());
            Class[] exceptionA = temp.getExceptionTypes();
            for (int j = 0; j < exceptionA.length; j++) {
                System.out.println(dent(depth) +exceptionA[j]);
            }
            Class[] parameterA = temp.getParameterTypes();
            for (int j = 0; j < parameterA.length; j++) {
                System.out.println(dent(depth) +parameterA);
            }
            int modfi = temp.getModifiers();
            System.out.println(dent(depth) +Modifier.toString(modfi));
        }
    }
    private void inspectMethod(Class c, Object obj, boolean recursive, int depth){
        Method[] methodA = c.getDeclaredMethods();
        for (int i = 0; i < methodA.length; i++) {
            Method temp =  methodA[i];
            System.out.println(dent(depth) +temp.getName());
            Class[] exceptionA = temp.getExceptionTypes();
            for (int j = 0; j < exceptionA.length; j++) {
                System.out.println(dent(depth) +exceptionA[j]);
            }
            Class[] parameterA = temp.getParameterTypes();
            for (int j = 0; j < parameterA.length; j++) {
                System.out.println(dent(depth) +parameterA[j]);
            }
            Class returnA = temp.getReturnType();
            System.out.println(dent(depth) +returnA);
            int modfi = temp.getModifiers();
            System.out.println(dent(depth) +Modifier.toString(modfi));
        }
    }

    private void inspectFields(Class c, Object obj, boolean recursive, int depth) throws IllegalAccessException {
        Field[] fieldsA = c.getFields();
        for (int i = 0; i < fieldsA.length; i++) {
            Field temp =  fieldsA[i];
            System.out.println(dent(depth) +temp.getName());
            Class type = temp.getType();
            System.out.println(dent(depth) +type);
            int modfi = temp.getModifiers();
            System.out.println(dent(depth) +Modifier.toString(modfi));
            Object value = fieldsA[i].get(obj);
        }
    }

    public String dent(int depth){
        String temp = "";
        for (int i = 0; i < depth; i++) {
            temp += "   ";
        }
        return temp;
    }
    public static void main(String[] args) throws IllegalAccessException {
        String g = "Hello, World!";
        Inspector ins = new Inspector();
        ins.inspect(g, false);
    }
}
