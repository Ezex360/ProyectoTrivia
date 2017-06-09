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

 class App{

     public static void main( String[] args ){
        staticFileLocation("/public");
        
        handleRegister();
        handleWelcome();
        handleMenu();

      }

      private static void handleRegister(){
        post("/register", (req, res) -> {
          Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/trivia", "proyecto", "felipe");
          String usern = req.queryParams("username");
          String pass = req.queryParams("password");

          User user = new User();
          user.set("username",usern);
          user.set("password",pass);
          user.saveIt();
          Base.close();

          res.redirect("/menu");
          return null;
        });
      }

      private static void handleWelcome(){
        get("/welcome", (req, res) -> {
          return new MustacheTemplateEngine().render(
            new ModelAndView(null, "index.html")
          );
        });
      }

      private static void handleMenu(){
        //Muestra el menu
        get("/menu", (req, res) -> {
          return new MustacheTemplateEngine().render(
            new ModelAndView(null, "menu.mustache")
          );
        });
        //Recibe la opcion elegida
        post("/menu", (req, res) -> {
          String value1 = req.queryParams("option1");
          String value2 = req.queryParams("option2");
          String value3 = req.queryParams("option3");
          if (value1 != null)
            res.redirect("/question");
          if (value2 != null)
            res.redirect("/score");
          if (value3 != null)
            res.redirect("/welcome");
          return null;
        });        
      }


      private static Question randomQuest(){
        List<Question> questions  = Question.findBySQL("SELECT * FROM questions ORDER BY RAND() LIMIT 1");
        Question q = questions.get(0);
        System.out.println(q.getInteger("id"));
        return q;
      }

      private static void answerQuestion(Game g,Question q,Integer res){
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
/*      
      post("/games/:id/anwerquestion", (req, res) => {
        Game g = Game.find("id = ?", req.params(":id"));
        Question q = randomQuest();
      });
      //Test
      get("/test/:id", (req, res) -> {
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/trivia", "proyecto", "felipe");

        Map map = new HashMap();
        User u = User.findFirst("id = ?", req.params(":id"));
        System.out.println("\n Id: "+req.params(":id")+"\n");
        map.put("user_id", u.get("id"));
        Base.close();
  
        return new MustacheTemplateEngine().render(
          new ModelAndView(map, "test.mustache")
        );
      });

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

}
