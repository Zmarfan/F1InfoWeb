package mock_data_generator.database;

import database.IQueryData;
import lombok.Value;

import java.io.InputStream;

@Value
public class CreateUsersQueryData implements IQueryData<Void> {
    String m01Email = "user@f1.com";
    String m02Password = "$2a$10$Wb1YibD19Az73ldC2n7TaOx0ftHNaggo3oXTHlN0Yy5sAR4d5p6q2";
    InputStream m03DefaultProfileIcon1 = getClass().getResourceAsStream("/profile_pic_1.png");
    InputStream m04DefaultProfileIcon2 = getClass().getResourceAsStream("/profile_pic_2.png");
    InputStream m05DefaultProfileIcon3 = getClass().getResourceAsStream("/profile_pic_3.png");
    InputStream m06DefaultProfileIcon4 = getClass().getResourceAsStream("/profile_pic_4.png");
    InputStream m07DefaultProfileIcon5 = getClass().getResourceAsStream("/profile_pic_5.png");
    InputStream m08DefaultProfileIcon6 = getClass().getResourceAsStream("/profile_pic_6.png");
    InputStream m09DefaultProfileIcon7 = getClass().getResourceAsStream("/profile_pic_7.png");
    InputStream m10DefaultProfileIcon8 = getClass().getResourceAsStream("/profile_pic_8.png");
    InputStream m10DefaultProfileIcon9 = getClass().getResourceAsStream("/profile_pic_9.png");
    InputStream m10DefaultProfileIcon10 = getClass().getResourceAsStream("/profile_pic_10.png");

    @Override
    public String getStoredProcedureName() {
        return "mock_data_generator_create_users";
    }
}
