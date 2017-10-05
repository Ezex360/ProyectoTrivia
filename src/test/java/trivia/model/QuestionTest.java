package trivia.model;

import trivia.model.User;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class QuestionTest{
    @Before
    public void before(){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/trivia_test", "proyecto", "felipe");
        System.out.println("QuestionTest setup");
        Base.openTransaction();
    }

    @After
    public void after(){
        System.out.println("QuestionTest tearDown");
        Base.rollbackTransaction();
        Base.close();
    }

    
    @Test
    public void validatePresenceOf(){
        Question question = new Question();
        question.set("question", "Cual es la diferencia entre una piedra y un bebe?");
        question.set("answer1", "La piedra no llora cuando la patean");
        question.set("answer2", "El bebe no esta re duro");
        question.set("rightans", 1);
        assertEquals(question.isValid(), true);
    }

    @Test
    public void validatePresenceOf2(){
        Question question = new Question();
        question.set("question", "");
        assertEquals(question.isValid(), false);
    }

    @Test
    public void validateUniquenessOfQuestions(){
        Question q1 = new Question();
        q1.set("question", "cual es el diametro terrestre?").save();
        // attempt creating another question with the same question
        Question q2 = new Question();
        q2.set("question", "cual es el diametro terrestre?").save();
        assertEquals(q2.isValid(), false);
    }

}
