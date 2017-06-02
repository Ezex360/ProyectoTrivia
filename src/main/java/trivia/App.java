package trivia;

import org.javalite.activejdbc.Base;
import static spark.Spark.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

import trivia.User;
import trivia.Category;
import trivia.Question;
import trivia.History;
import trivia.Game;

public class App{

    public static void main( String[] args ){

      Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/trivia", "proyecto", "felipe");
      
      //Test
      get("/hello", (req, res) -> {
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/trivia", "proyecto", "felipe");

        Map map = new HashMap();

        Game g = Game.findFirst("id = ?",1);
        map.put("game_id", g.get("id"));
        Base.close();  

        return new ModelAndView(map, "./views/test.mustache");
      }, new MustacheTemplateEngine());

      //Recibir usuario.
      get("/register", (req, res) -> {
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/trivia", "proyecto", "felipe");
        Map map = new HashMap();
        Base.close();  

        return new ModelAndView(null, "./views/register.mustache");
      }, new MustacheTemplateEngine());

      //Cargar Usuario
      post("/register", (req, res) -> {
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/trivia", "proyecto", "felipe");
        Map map = new HashMap();
        String usern = req.queryParams("username");
        String pass = req.queryParams("password");

        User user = new User();
        user.set("username",usern);
        user.set("password",pass);
        user.saveIt();
        Base.close();
        return new ModelAndView(map, "./views/test.mustache");
      }, new MustacheTemplateEngine());

/*      

      post("/games/:id/anwerquestion", (req, res) => {
        Game g = Game.find("id = ?", req.params(":id"));
        Question q = randomQuest();


      });

    */ 

      Base.close();


      }
/*
      get("/games/:id/question/:question-id", (req, res) => { 
        Map map = new HashMap();

        res,
      })
  
    SELECT * FROM users ORDER BY RAND() LIMIT 1;
        Question q = stm.executeQuery("SELECT * FROM users ORDER BY RAND() LIMIT 1");

        Random rand = new Random();
        long questionsCount = Question.count();
        int  questionRand = rand.nextInt(questionsCount) + 1;

  
*/
      private static Question randomQuest(){
        List<Question> questions  = Question.findBySQL("SELECT * FROM questions ORDER BY RAND() LIMIT 1");
        Question q = questions.get(0);
        System.out.println(q.getInteger("id"));
        return q;
      }



      private void answerQuestion(Game g,Question q,Integer res){
        Boolean ans = q.verificarRespuesta(res);
        if (!ans)
          g.restarVida();
        History record = new History();
        record.set("user_id",g.get("user_id"));
        record.set("game_id",g.get("id"));
        record.set("question_id",q.get("id"));
        record.set("isCorrect",ans);
        record.saveIt();
      }
}
