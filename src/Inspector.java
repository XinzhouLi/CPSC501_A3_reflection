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
//        inspectName(c, obj, recursive, depth);
//        inspectSuperclass(c, obj, recursive, depth);
//        inspectInterface(c, obj, recursive, depth);
//        inspectConstructor(c, obj, recursive, depth);
//        inspectMethod(c, obj, recursive, depth);
        inspectFields(c, obj, false, depth);
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

    private Field[] inspectFields(Class c, Object obj, boolean recursive, int depth) throws IllegalAccessException {
        Field[] fields =c.getDeclaredFields();
        System.out.println(depth+"FIELDS( "+c+" )");
        System.out.print(depth+"Fields->");
        if(fields.length==0){
            System.out.println("None");
            return fields;
        }
        System.out.println();
        for (int i=0;i<fields.length;i++){
            System.out.println(depth+" "+"FIELD");
            String fieldName = fields[i].getName();
            Class fieldType = fields[i].getType();
            int modifier = fields[i].getModifiers();
            System.out.println(Modifier.toString(modifier));
            fields[i].setAccessible(true);


// Array type handling:
            if(fieldType.isArray()){
                Object value = fields[i].get(obj);
                System.out.println(depth+" "+" "+"Name:  "+ fieldName);
                System.out.println(depth+" "+" "+"Type:  "+ fieldType.toString());
                System.out.println(depth+" "+" "+"Modifiers:  "+ Modifier.toString(modifier));
                String component = value.getClass().getComponentType().toString();
                System.out.println(depth+" "+" "+"Component Type:  "+ component);

                if(value.getClass().getComponentType().isPrimitive()){
                    byte[] result = (byte[]) value;
                    System.out.println(depth+" "+" "+"Length:  "+ result.length);
                    System.out.println(depth+" "+" "+"Entries->");
                    for(int j=0;j<result.length;j++){
                        System.out.println(depth+" "+" "+"Value:  "+result[j]);
                    }
                }
                else {
                    Object[] result = (Object[]) value;
                    System.out.println(depth+" "+" "+"Length:  "+ result.length);
                    System.out.println(depth+" "+" "+"Entries->");
                    for(int j=0;j<result.length;j++){
                        if(result[j]!=null) {
//                            inspectClass(result[j].getClass(), result[j], recursive, 1);
                        }
                        else{
                            System.out.println(depth+" "+" "+"Value:  "+result[j]);
                        }
                    }
                }
            }
            else{
                System.out.println(depth+" "+" "+"Name:  "+ fieldName);
                System.out.println(depth+" "+" "+"Type:  "+ fieldType.toString());
                System.out.println(depth+" "+" "+"Modifiers:  "+ Modifier.toString(modifier));
                if((!fieldType.isPrimitive()) && (!recursive)){
                    Object value = fields[i].get(obj);
                    System.out.println(depth+" "+" "+"Value:  "+ value);
                }
                else if(!fieldType.isPrimitive()){
                    Object value = fields[i].get(obj);
                    System.out.println(depth+" "+" "+"Value:  "+ value);
                    System.out.println(depth+" "+" "+" "+"-> Recursively inspect");
                    if(value!=null){
//                        inspectClass(value.getClass(),value,recursive,1);
                    }
                }
                /////////////////GET PRIMITIVE DATA TYPE/////////////////////
                else if(fieldType.isPrimitive()){
                    //System.out.println(fields[i].getType());
                    //System.out.println(fields[i].getInt(obj));

                    if(fields[i].getType().toString().equals("short")){
                        System.out.println(depth+" "+" "+"Value:  "+ fields[i].getShort(obj));
                    }
                    else if(fields[i].getType().toString().equals("int")){
                        System.out.println(depth+" "+" "+"Value:  "+ fields[i].getInt(obj));
                    }
                    else if(fields[i].getType().toString().equals("long")){
                        System.out.println(depth+" "+" "+"Value:  "+ fields[i].getLong(obj));
                    }
                    else if(fields[i].getType().toString().equals("float")){
                        System.out.println(depth+" "+" "+"Value:  "+ fields[i].getFloat(obj));
                    }
                    else if(fields[i].getType().toString().equals("double")){
                        System.out.println(depth+" "+" "+"Value:  "+ fields[i].getDouble(obj));
                    }
                    else if(fields[i].getType().toString().equals("byte")){
                        System.out.println(depth+" "+" "+"Value:  "+ fields[i].getByte(obj));
                    }
                    else if(fields[i].getType().toString().equals("boolean")){
                        System.out.println(depth+" "+" "+"Value:  "+ fields[i].getBoolean(obj));
                    }
                    else if(fields[i].getType().toString().equals("char")){
                        System.out.println(depth+" "+" "+"Value:  "+ fields[i].getChar(obj));
                    }
                    else{
                        System.out.println("No matching primitive type");
                    }
                }
                else{
                    System.out.println(depth+" "+" "+"Error in getFields");
                }
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