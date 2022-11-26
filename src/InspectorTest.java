import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

class InspectorTest {
    @Test
    void  getClassname(){
        ClassD d = new ClassD();
        Inspector ip = new Inspector();
        assertEquals(d.getClass().getName(), ip.inspectName(d.getClass(),d,false,1), "expected: ClassD");
    }
    @Test
    void  getSuperclass(){
        ClassD d = new ClassD();
        Inspector ip = new Inspector();
        assertEquals(Object.class, ip.inspectName(d.getClass().getSuperclass(),d,false,1), "expected: Object");
    }

}