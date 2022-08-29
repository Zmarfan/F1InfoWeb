package database;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public interface IQueryData<T> {
    String getStoredProcedureName();

    default Class<T> getRecordClass() {
        final Type[] interfaces = this.getClass().getGenericInterfaces();
        for (final Type interfaceIt : interfaces) {
            if (interfaceIt.getTypeName().startsWith("database.IQueryData")) {
                return (Class<T>) ((ParameterizedType) interfaces[0]).getActualTypeArguments()[0];
            }
        }
        throw new IllegalArgumentException("The class should implement IQueryData! Maybe the interface or package name changed ?");
    }
}
