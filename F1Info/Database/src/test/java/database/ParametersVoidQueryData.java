package database;

import lombok.Value;

@Value
class ParametersVoidQueryData implements IQueryData<Void> {
    int mParameter1;
    String mParameter2;
    Long mParameter3;

    @Override
    public String getStoredProcedureName() {
        return DatabaseUtilTest.PROCEDURE_NAME;
    }
}
