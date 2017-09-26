package trivia.model;

import trivia.model.User;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HistoryTest{
    @Before
    public void before(){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/trivia_test", "proyecto", "felipe");
        System.out.println("HistoryTest setup");
        Base.openTransaction();
    }

    @After
    public void after(){
        System.out.println("HistoryTest tearDown");
        Base.rollbackTransaction();
        Base.close();
    }

}
