package trivia;

import org.javalite.activejdbc.Base;
import static spark.Spark.*;

import trivia.User;
import trivia.Category;
import trivia.Question;
import trivia.History;
import trivia.Game;

public class App{

    public static void main( String[] args ){

      Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/trivia", "proyecto", "felipe");

      User u = new User();
      u.set("username", "Ezex360");
      u.set("password", "ratamanase123");
      u.saveIt();

      User u2 = new User();
      u2.set("username", "Joax360");
      u2.set("password", "ojalapupu");
      u2.saveIt();

      Category category = new Category();
      category.set("cat_name", "Deporte");
      category.saveIt();

      Question question = new Question();
      question.set("question", "Quien es el mejor equipo?");
      question.set("category_id", category.get("id"));
      question.set("answer1", "River");
      question.set("answer2", "Boca");
      question.set("rightans", 2);
      question.saveIt();

      Game game = new Game();
      game.set("user_id",u.get("id"));
      game.set("status",true);
      game.saveIt();

       get("/hello", (req, res) -> "Hello World");

      Base.close();


      }


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
