package mock_data_generator.database;

import database.IQueryData;
import lombok.Value;

@Value
public class CreateUsersQueryData implements IQueryData<Void> {
    String m01Email = "user@f1.com";
    String m02Password = "$2a$10$GkvxeNddrfc3R8NRrdZdZ.xMK/.Q6vSQ.U3n9Te.rholzQjbA5m1i";

    @Override
    public String getStoredProcedureName() {
        return "mock_data_generator_create_users";
    }
}
