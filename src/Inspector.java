import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * CPSC 501
 * Inspector starter class
 *
 * @author Jonathan Hudson
 */
public class Inspector {

    private Object obj;

    public static void main(String[] args) throws IllegalAccessException {
        String g = "Hello, World!";
        Inspector ins = new Inspector();
        ins.inspect(g, false);
    }

    public void inspect(Object obj, boolean recursive) throws IllegalAccessException {
        Class c = obj.getClass();
        inspectClass(c, obj, recursive, 0);
    }

    private void inspectClass(Class c, Object obj, boolean recursive, int depth) throws IllegalAccessException {
        if (c == null) {
            return;
        }
        inspectName(c, obj, recursive, depth);
        inspectSuperclass(c, obj, recursive, depth);
        inspectInterface(c, obj, recursive, depth);
        inspectConstructor(c, obj, recursive, depth);
        inspectMethod(c, obj, recursive, depth);
        inspectFields(c, obj, recursive, depth);
    }

    private void inspectName(Class c, Object obj, boolean recursive, int depth) {
        String name = c.getName();
        System.out.println(dent(depth) + "CLASS\n" + dent(depth) + "Class: " + name);
    }

    private void inspectInterface(Class c, Object obj, boolean recursive, int depth) throws IllegalAccessException {
        System.out.println(dent(depth) + "INTERFACES( " + c + " )");
        Class[] interfaceA = c.getInterfaces();
        if (interfaceA.length == 0) {
            System.out.println(dent(depth) + "Interfaces-> NONE");
        } else {
            System.out.println(dent(depth) + "Interfaces->");
            for (int i = 0; i < interfaceA.length; i++) {
                System.out.println(dent(depth) + "SUPERCLASS -> Recursively Inspect\n" + dent(depth) + interfaceA[i]);
                inspectClass(interfaceA[i], null, recursive, depth + 1);

            }
        }

    }

    private void inspectSuperclass(Class c, Object obj, boolean recursive, int depth) throws IllegalAccessException {
        Class superC = c.getSuperclass();
        if (superC == null && c == Object.class) {
            System.out.println(dent(depth) + "SuperClass: NONE");
        } else {
            System.out.println(dent(depth) + "SUPERCLASS -> Recursively Inspect\n" + dent(depth) + "SuperClass: " + superC);
            inspectClass(superC, null, recursive, depth + 1);
        }
    }

    private void inspectConstructor(Class c, Object obj, boolean recursive, int depth) {
        System.out.println(dent(depth) + "CONSTRUCTORS( " + c + " )");
        Constructor[] constructorA = c.getDeclaredConstructors();
        if (constructorA.length == 0) {
            System.out.println(dent(depth) + "Constructors-> NONE");
        } else {

            System.out.println(dent(depth) + "Constructors->");
            for (int i = 0; i < constructorA.length; i++) {
                System.out.println(dent(depth + 1) + "CONSTRUCTORS->");
                Constructor temp = constructorA[i];
                System.out.println(dent(depth + 2) + "Name: " + temp.getName());

                Class[] exceptionA = temp.getExceptionTypes();
                if (exceptionA.length == 0) {
                    System.out.println(dent(depth + 2) + "Exceptions-> NONE");
                } else {
                    System.out.println(dent(depth + 2) + "Exceptions->");
                    for (int j = 0; j < exceptionA.length; j++) {
                        System.out.println(dent(depth + 2) + exceptionA[j]);
                    }
                }

                Class[] parameterA = temp.getParameterTypes();
                if (parameterA.length == 0) {
                    System.out.println(dent(depth + 2) + "Parameter types-> NONE");
                } else {
                    System.out.println(dent(depth + 2) + "Parameter types-> ");
                    for (int j = 0; j < parameterA.length; j++) {
                        System.out.println(dent(depth + 2) + parameterA[j].getName());
                    }
                }

                int modfi = temp.getModifiers();
                System.out.println(dent(depth + 2) + "Modifiers: " + Modifier.toString(modfi));
            }
        }
    }

    private void inspectMethod(Class c, Object obj, boolean recursive, int depth) {
        System.out.println(dent(depth) + "METHODS( " + c + " )");
        Method[] methodA = c.getDeclaredMethods();
        if (methodA.length == 0) {
            System.out.println(dent(depth) + "Methods-> NONE");
        } else {
            System.out.println(dent(depth) + "Methods->");
            for (int i = 0; i < methodA.length; i++) {
                Method temp = methodA[i];
                System.out.println(dent(depth + 1) + "METHOD\n" + dent(depth + 2) + "Name: " + temp.getName());

                Class[] exceptionA = temp.getExceptionTypes();
                if (exceptionA.length == 0) {
                    System.out.println(dent(depth + 2) + "Exceptions-> NONE");
                } else {
                    System.out.println(dent(depth + 2) + "Exceptions->");
                    for (int j = 0; j < exceptionA.length; j++) {
                        System.out.println(dent(depth + 2) + exceptionA[j]);
                    }
                }

                Class[] parameterA = temp.getParameterTypes();
                if (parameterA.length == 0) {
                    System.out.println(dent(depth + 2) + "Parameter types-> NONE");
                } else {
                    System.out.println(dent(depth + 2) + "Parameter types-> ");
                    for (int j = 0; j < parameterA.length; j++) {
                        System.out.println(dent(depth + 2) + parameterA[j].getName());
                    }
                }

                Class returnA = temp.getReturnType();
                System.out.println(dent(depth + 2) + "Return type: " + returnA);
                int modfi = temp.getModifiers();
                System.out.println(dent(depth + 2) + "Modifiers: " + Modifier.toString(modfi));
            }
        }
    }

    private void inspectFields(Class c, Object obj, boolean recursive, int depth) throws IllegalAccessException {
        System.out.println(dent(depth) + "FIELDS( " + c + " )");
        Field[] fieldsA = c.getDeclaredFields();
        if (fieldsA.length == 0) {
            System.out.println(dent(depth) + "Fields-> NONE");
        } else {
            System.out.println(dent(depth) + "Fields->");
            for (int i = 0; i < fieldsA.length; i++) {
                System.out.println(dent(depth + 1) + "FIELD");
                fieldsA[i].setAccessible(true);
                Field temp = fieldsA[i];
                System.out.println(dent(depth + 2) + "Name: " + temp.getName());
                Class type = temp.getType();
                System.out.println(dent(depth + 2) + "Type: " + type);
                int modfi = temp.getModifiers();
                System.out.println(dent(depth + 2) + "Modifiers: " + Modifier.toString(modfi));

                if (temp.getType().isPrimitive()){
                    if(temp.getType().toString().equals("short")){
                        System.out.println(dent(depth + 2)+" "+" "+"Value:  "+ temp.getShort(obj));
                    }
                    else if(temp.getType().toString().equals("int")){
                        System.out.println(dent(depth + 2)+" "+" "+"Value:  "+ temp.getInt(obj));
                    }
                    else if(temp.getType().toString().equals("long")){
                        System.out.println(dent(depth + 2)+" "+" "+"Value:  "+ temp.getLong(obj));
                    }
                    else if(temp.getType().toString().equals("float")){
                        System.out.println(dent(depth + 2)+" "+" "+"Value:  "+ temp.getFloat(obj));
                    }
                    else if(temp.getType().toString().equals("double")){
                        System.out.println(dent(depth + 2)+" "+" "+"Value:  "+ temp.getDouble(obj));
                    }
                    else if(temp.getType().toString().equals("byte")){
                        System.out.println(dent(depth + 2)+" "+" "+"Value:  "+ temp.getByte(obj));
                    }
                    else if(temp.getType().toString().equals("boolean")){
                        System.out.println(dent(depth + 2)+" "+" "+"Value:  "+ temp.getBoolean(obj));
                    }
                    else if(temp.getType().toString().equals("char")){
                        System.out.println(dent(depth + 2)+" "+" "+"Value:  "+ temp.getChar(obj));
                    }
                    else{
                        System.out.println("No matching primitive type");
                    }

                }else if(!temp.getType().isPrimitive()){
                    System.out.println(dent(depth+2)+"Value (ref): "+c+"\n"+dent(depth+3)+ "-> Recursively inspect");
//                    Object tempobj = temp.get(obj);
//                    inspectClass(temp.getType(), tempobj, recursive, depth+3);
                }
            }
        }
    }

    public String dent(int depth) {
        String temp = "";
        for (int i = 0; i < depth; i++) {
            temp += "   ";
        }
        return temp;
    }
}
