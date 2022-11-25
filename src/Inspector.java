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
        inspectName(c, obj, recursive, depth);
        inspectInterface(c, obj, recursive, depth);
        inspectSuperclass(c, obj, recursive, depth);
        inspectConstructor(c, obj, recursive, depth);
        inspectMethod(c, obj, recursive, depth);
        inspectFields(c, obj, recursive, depth);
    }


    private void inspectName(Class c, Object obj, boolean recursive, int depth){
        String name = c.getName();
        System.out.println(name);

    }

    private void inspectInterface(Class c, Object obj, boolean recursive, int depth){
        Class[] interfaceA = c.getInterfaces();
        for (int i = 0; i < interfaceA.length; i++) {
            System.out.println(interfaceA[i]);
        }
    }

    private void inspectSuperclass(Class c, Object obj, boolean recursive, int depth){
        Class superC = c.getSuperclass();
        System.out.println(superC);
    }

    private void inspectConstructor(Class c, Object obj, boolean recursive, int depth){
        Constructor[] constructorA = c.getDeclaredConstructors();
        for (int i = 0; i < constructorA.length; i++) {
            Constructor temp =  constructorA[i];
            System.out.println(temp.getName());
            Class[] exceptionA = temp.getExceptionTypes();
            for (int j = 0; j < exceptionA.length; j++) {
                System.out.println(exceptionA[j]);
            }
            Class[] parameterA = temp.getParameterTypes();
            for (int j = 0; j < parameterA.length; j++) {
                System.out.println(parameterA);
            }
            int modfi = temp.getModifiers();
            System.out.println(Modifier.toString(modfi));
        }
    }
    private void inspectMethod(Class c, Object obj, boolean recursive, int depth){
        Method[] methodA = c.getDeclaredMethods();
        for (int i = 0; i < methodA.length; i++) {
            Method temp =  methodA[i];
            System.out.println(temp.getName());
            Class[] exceptionA = temp.getExceptionTypes();
            for (int j = 0; j < exceptionA.length; j++) {
                System.out.println(exceptionA[j]);
            }
            Class[] parameterA = temp.getParameterTypes();
            for (int j = 0; j < parameterA.length; j++) {
                System.out.println(parameterA[j]);
            }
            Class returnA = temp.getReturnType();
            System.out.println(returnA);
            int modfi = temp.getModifiers();
            System.out.println(Modifier.toString(modfi));
        }
    }

    private void inspectFields(Class c, Object obj, boolean recursive, int depth) throws IllegalAccessException {
        Field[] fieldsA = c.getFields();
        for (int i = 0; i < fieldsA.length; i++) {
            Field temp =  fieldsA[i];
            System.out.println(temp.getName());
            Class type = temp.getType();
            System.out.println(type);
            int modfi = temp.getModifiers();
            System.out.println(Modifier.toString(modfi));
            Object value = fieldsA[i].get(obj);
        }
    }
    public static void main(String[] args) throws IllegalAccessException {
        String g = "Hello, World!";
        Inspector ins = new Inspector();
        ins.inspect(g, false);
    }
}
