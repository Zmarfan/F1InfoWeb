package f1_Info.database;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QueryDataTest {
    @Test
    void should_return_templated_type_when_getting_record_class_when_not_void() {
        assertEquals(Integer.class, new ReturnQueryData().getRecordClass());
    }

    @Test
    void should_return_templated_type_when_getting_record_class_when_void() {
        assertEquals(Void.class, new NoParametersVoidQueryData().getRecordClass());
    }
}
