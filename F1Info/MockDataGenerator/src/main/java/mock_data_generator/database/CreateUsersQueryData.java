package mock_data_generator.database;

import database.IQueryData;
import lombok.Value;

@Value
public class CreateUsersQueryData implements IQueryData<Void> {
    String m01Email = "user@f1.com";
    String m02Password = "$2a$10$Wb1YibD19Az73ldC2n7TaOx0ftHNaggo3oXTHlN0Yy5sAR4d5p6q2";

    @Override
    public String getStoredProcedureName() {
        return "mock_data_generator_create_users";
    }
}