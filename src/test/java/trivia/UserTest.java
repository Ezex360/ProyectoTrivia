package trivia;

import trivia.User;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest{
    @Before
    public void before(){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/trivia_test", "proyecto", "felipe");
        System.out.println("UserTest setup");
        Base.openTransaction();
    }

    @After
    public void after(){
        System.out.println("UserTest tearDown");
        Base.rollbackTransaction();
        Base.close();
    }

    @Test
    public void validatePrescenceOfUsernames(){
        User user = new User();
        user.set("username", "");
        assertEquals(user.isValid(), false);
    }

    @Test
    public void validateUniquenessOfUsernames(){
        new User().set("username", "Eze").saveIt();
        // attempt creating another user with the same username
        User u = new User();
        u.set("username", "Eze").save();
        assertEquals(u.isValid(), false);
    }
}
