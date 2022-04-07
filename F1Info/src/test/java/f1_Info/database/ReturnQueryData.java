package f1_Info.database;

class ReturnQueryData implements IQueryData<Integer> {
    @Override
    public String getStoredProcedureName() {
        return DatabaseUtilTest.PROCEDURE_NAME;
    }
}
