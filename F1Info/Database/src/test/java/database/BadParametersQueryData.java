package database;

import lombok.Value;

@Value
class BadParametersQueryData implements IQueryData<Void> {
    NoParametersVoidQueryData mParameter1;

    @Override
    public String getStoredProcedureName() {
        return DatabaseUtilTest.PROCEDURE_NAME;
    }
}
