package trivia.model;

import trivia.model.User;

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
    public void validatePrescenceOfPasswords(){
        User user = new User();
        user.set("password", "");
        assertEquals(user.isValid(), false);
    }

    @Test
    public void validateUniquenessOfUsernames(){
        User u1 = new User();
        u1.set("username", "Eze").save();
        // attempt creating another user with the same username
        User u2 = new User();
        u2.set("username", "Eze").save();
        assertEquals(u2.isValid(), false);
    }
}
