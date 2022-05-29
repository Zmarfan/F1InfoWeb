package database.sql_parsing;

import lombok.Value;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.List;

@Value
public class RecordParsingInfo {
    Constructor<?> mConstructor;
    List<Parameter> mParameters;
}
