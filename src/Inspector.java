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

    public String inspectName(Class c, Object obj, boolean recursive, int depth) {
        String name = c.getName();
        System.out.println(dent(depth) + "CLASS\n" + dent(depth) + "Class: " + name);
        return name;
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
                inspectClass(interfaceA[i], obj, recursive, depth + 1);

            }
        }

    }

    public Class inspectSuperclass(Class c, Object obj, boolean recursive, int depth) throws IllegalAccessException {

        Class superC = c.getSuperclass();
        if (superC == null && c == Object.class) {
            System.out.println(dent(depth) + "SuperClass: NONE");
        } else {
            System.out.println(dent(depth) + "SUPERCLASS -> Recursively Inspect\n" + dent(depth) + "SuperClass: " + superC);
            inspectClass(c.getSuperclass(), obj, recursive, depth + 1);
        }
        return superC;
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

    private Field[] inspectFields(Class c, Object obj, boolean recursive, int depth) throws IllegalAccessException {
        Field[] fields = c.getDeclaredFields();
        System.out.println(dent(depth) + "FIELDS( " + c + " )");
        if (fields.length == 0) {
            System.out.println(dent(depth) + "Fields: " + "NONE");
            return fields;
        }
        System.out.println(dent(depth) + "Fields-> ");
        for (int i = 0; i < fields.length; i++) {
            System.out.println(dent(depth+1)+ "FIELD");
            String fieldName = fields[i].getName();
            Class fieldType = fields[i].getType();
            int modifier = fields[i].getModifiers();
            fields[i].setAccessible(true);


            System.out.println(dent(depth+1) + "Name: " + fieldName);
            System.out.println(dent(depth+1) + "Type: " + fieldType);
            System.out.println(dent(depth+1) + "Modifiers: " + Modifier.toString(modifier));
            if ((!fieldType.isPrimitive()) && (!recursive)) {
                Object value = fields[i].get(obj);
                System.out.println(dent(depth+1) + "Value: " + value);
            } else if (!fieldType.isPrimitive()) {
                Object value = fields[i].get(obj);
                System.out.println(dent(depth+1) + "Value: " + value);
                System.out.println(dent(depth+2) + "-> Recursively inspect");
                if (value != null) {
                    inspectClass(value.getClass(), value, recursive, depth+2);
                }

            }
            /////////////////GET PRIMITIVE DATA TYPE/////////////////////
            else if (fieldType.isPrimitive()) {
                //System.out.println(fields[i].getType());
                //System.out.println(fields[i].getInt(obj));

                if (fields[i].getType().toString().equals("short")) {
                    System.out.println(dent(depth+1) + "Value:  " + fields[i].getShort(obj));
                } else if (fields[i].getType().toString().equals("int")) {
                    System.out.println(dent(depth+1)+ "Value:  " + fields[i].getInt(obj));
                } else if (fields[i].getType().toString().equals("long")) {
                    System.out.println(dent(depth+1) + "Value:  " + fields[i].getLong(obj));
                } else if (fields[i].getType().toString().equals("float")) {
                    System.out.println(dent(depth+1)+ "Value:  " + fields[i].getFloat(obj));
                } else if (fields[i].getType().toString().equals("double")) {
                    System.out.println(dent(depth+1) + "Value:  " + fields[i].getDouble(obj));
                } else if (fields[i].getType().toString().equals("byte")) {
                    System.out.println(dent(depth+1) + "Value:  " + fields[i].getByte(obj));
                } else if (fields[i].getType().toString().equals("boolean")) {
                    System.out.println(dent(depth+1) + "Value:  " + fields[i].getBoolean(obj));
                } else if (fields[i].getType().toString().equals("char")) {
                    System.out.println(dent(depth+1)+ "Value:  " + fields[i].getChar(obj));
                } else {
                    System.out.println("No matching primitive type");
                }
            } else {
                System.out.println(dent(depth+2) + "Error in getFields");
            }
        }

        return fields;
    }

    public String dent(int depth) {
        String temp = "";
        for (int i = 0; i < depth; i++) {
            temp += "   ";
        }
        return temp;
    }
}