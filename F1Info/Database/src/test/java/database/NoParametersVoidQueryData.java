package database;

class NoParametersVoidQueryData implements IQueryData<Void> {
    @Override
    public String getStoredProcedureName() {
        return DatabaseUtilTest.PROCEDURE_NAME;
    }
}
