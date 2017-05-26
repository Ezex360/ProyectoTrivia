package trivia;

import org.javalite.activejdbc.Base;
import static spark.Spark.*;
import java.util.HashMap;
import java.util.Map;

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
      
      get("/hello", (req, res) -> {
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/trivia", "proyecto", "felipe");

        Map map = new HashMap();

        Game g = Game.findFirst("id = ?",1);
        map.put("game_id", g.get("id"));
        Base.close();  

        return new ModelAndView(map, "./views/test.mustache");
      }, new MustacheTemplateEngine());

      Base.close();


      }
/*
      get("/games/:id/question/:question-id", (req, res) => { 
        Map map = new HashMap();

        res,
      })

     post("/games/:id/anwerquestion", (req, res) => {
        Game g = Game.find("id = ?", req.params(":id"))
        Question q = Question.find("id = ?", req.queryParams("question"))
      });
*/
      public void answerQuestion(Game g,Question q,Integer res){
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

      // List<User> users = User.where("username = ?", "Maradona");
      // User u = users.get(0);
      // g.set("user1_id", u.get("id"));
      // User u2 = User.findFirst("username = ?", "Pele");
      // g.set("user2_id", u2.get("id"));
      // g.saveIt();


      // g.getQuestion()
      // g.answerQuestio(USEr, Question, OPtion)

      //Map map = new HashMap();
      //map.put("name", "Sam");
      //map.put("value", 1000);
      //map.put("taxed_value", 1000 - (1000 * 0.4));
      //map.put("in_ca", true);

      //get("/hello", (req, res) -> {
      //  return new ModelAndView(map, "./views/test.mustache");
      //}, new MustacheTemplateEngine()
      //);
}
