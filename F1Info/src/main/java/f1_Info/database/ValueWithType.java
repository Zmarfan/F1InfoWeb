package f1_Info.database;

import lombok.Value;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Value
public class ValueWithType {
    String mGetterName;
    String mTypeName;
    Object mData;

    public static <T> ValueWithType fromMethod(final IQueryData<T> data, final Method method) {
        try {
            return new ValueWithType(method.getName(), method.getReturnType().getTypeName(), method.invoke(data));
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException(String.format("Could not invoke method %s", method.getName()));
        }
    }
}
