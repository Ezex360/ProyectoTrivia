package trivia.model;

import trivia.model.Category;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CategoryTest{
    @Before
    public void before(){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/trivia_test", "proyecto", "felipe");
        System.out.println("CategoryTest setup");
        Base.openTransaction();
    }

    @After
    public void after(){
        System.out.println("CategoryTest tearDown");
        Base.rollbackTransaction();
        Base.close();
    }

    @Test
    public void validatePresenceOfCategoryName(){
        Category cat = new Category();
        cat.set("cat_name", "");
        assertEquals(cat.isValid(), false);
    }
}
