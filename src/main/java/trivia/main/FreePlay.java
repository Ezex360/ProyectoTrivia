package trivia.main;

import org.javalite.activejdbc.Base;
import java.sql.*;
import static spark.Spark.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

import trivia.model.User;
import trivia.model.Category;
import trivia.model.Question;
import trivia.model.History;
import trivia.model.Game;

public class FreePlay {

	  public static void IndividualGame(){
	  	Roulete();
	  	Play();
	  	AnswerResponse();
	  	GameEnd();
	  }


      //Elije categorias aleatorias
      public static void Roulete(){
      	//Muestra la ruleta o el bonus
        get("/ruleta", (req,res) -> {
          if (req.session().attribute("user_id") == null
            || req.session().attribute("game_id") == null)
            res.redirect("/welcome");
          Integer id_g = req.session().attribute("game_id");
          if(onFire(id_g)){
            return new MustacheTemplateEngine().render(
              new ModelAndView(null, "categories.mustache")
            );
          }else{
            return new MustacheTemplateEngine().render(
              new ModelAndView(null, "ruleta.mustache")
            );
          }
        });
        //Recibe la categoria y redirecciona al generador de preguntas aleatorias
        post("/categories", (req, res) -> {
	      if (req.session().attribute("user_id") == null
	        || req.session().attribute("game_id") == null)
	        res.redirect("/welcome");
          String value1 = req.queryParams("option1");
          String value2 = req.queryParams("option2");
          String value3 = req.queryParams("option3");
          String value4 = req.queryParams("option4");
          String value5 = req.queryParams("option5");
          String value6 = req.queryParams("option6");
          if (value1!=null)
            res.redirect("/category/:1");
          else if (value2!=null)
            res.redirect("/category/:2");
          else if (value3!=null)
            res.redirect("/category/:3");
          else if (value4!=null)
            res.redirect("/category/:4");
          else if (value5!=null)
            res.redirect("/category/:5");
          else
            res.redirect("/category/:6");
          return null;
        });

      }

      //Funciones encargadas del juego
      public static void Play(){
        //Genera pregunta aleatoria a partir del id de una categoria.
        get("/category/:id", (req, res) -> {
          if (req.session().attribute("user_id") == null
            || req.session().attribute("game_id") == null)
            res.redirect("/welcome");
          Integer cat_id = Integer.parseInt(req.params(":id").substring(1));
          Integer user_id = req.session().attribute("user_id");
          Question quest = App.randomQuest(cat_id,user_id); 
          Integer id_q = quest.getInteger("id");
          res.redirect("/play/:"+id_q);
          return null;
        });       
        //Muestra la pregunta pasada por parametro.
        get("/play/:id", (req, res) -> {
          if (req.session().attribute("user_id") == null
            || req.session().attribute("game_id") == null)
            res.redirect("/welcome"); 
          Question quest = App.questById(Integer.parseInt(req.params(":id").substring(1)));
          Map map = new HashMap();
          map.put("question",quest.getString("question"));
          map.put("option1",quest.getString("answer1"));
          map.put("option2",quest.getString("answer2"));
          map.put("option3",quest.getString("answer3"));
          map.put("option4",quest.getString("answer4"));
          map.put("question_id",quest.getInteger("id"));
          return new MustacheTemplateEngine().render(
            new ModelAndView(map, "play.mustache")
          );
        });
        //Comprueba que la pregunta pasada sea correcta.
        post("/play/:id", (req, res) -> {
          if (req.session().attribute("user_id") == null
            || req.session().attribute("game_id") == null)
            res.redirect("/welcome");
          Question quest = App.questById(Integer.parseInt(req.params(":id").substring(1)));
          Integer ans;
          String value1 = req.queryParams("option1");
          String value2 = req.queryParams("option2");
          String value3 = req.queryParams("option3");
          String value4 = req.queryParams("option4");
          if (value1!=null)
            ans = 1;
          else if (value2!=null)
            ans = 2;
          else if (value3!=null)
            ans = 3;
          else
            ans = 4;
          Integer id_g = req.session().attribute("game_id"); 
          boolean isCorrect = answerQuestion(App.gameBySession(id_g),quest,ans);
          if (isCorrect) 
            res.redirect("/correct");
          else
            res.redirect("/incorrect");
          return null;
        });
      }

      //Maneja si una respuesta es correcta o no.
      public static void AnswerResponse(){
      	//Acciones de una respuesta correcta.
        get("/correct", (req,res) -> {         
          Map map = new HashMap();
          Integer id_g = req.session().attribute("game_id");
          Game g = App.gameBySession(id_g);
          map.put("res"," Correcta!");
          map.put("lifes",g.getInteger("lifes"));
          if (youWin(g)){ 
            map.put("result","Ganaste :)");
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/trivia", "proyecto", "felipe");
            g.set("status",false);
            g.save();
            Base.close();     
            return new MustacheTemplateEngine().render(
              new ModelAndView(map, "winnerOrLosser.mustache")
            );
          }else{
            return new MustacheTemplateEngine().render(
              new ModelAndView(map, "result.mustache")
            );
          }
        });
        //Acciones de una respuesta incorrecta.
        get("/incorrect", (req,res) -> {
          Map map = new HashMap();
          Integer id_g = req.session().attribute("game_id");
          Game g = App.gameBySession(id_g);
          map.put("res"," incorrecta :(");
          Integer lifes = g.getInteger("lifes");
          map.put("lifes",lifes);
          if (lifes<=0){
            map.put("result","Perdiste!");
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/trivia", "proyecto", "felipe");
            g.set("status",false);
            g.save();
            Base.close(); 
            return new MustacheTemplateEngine().render(
              new ModelAndView(map, "winnerOrLosser.mustache")
            );
          }else{
            return new MustacheTemplateEngine().render(
              new ModelAndView(map, "result.mustache")
            );
          }
        });
        //Permite volver al menu o seguir jugando
        post("/result", (req,res) -> {
          String value1 = req.queryParams("option1");
          String value2 = req.queryParams("option2");
          if (value1!=null)
            res.redirect("/ruleta");
          else 
            res.redirect("/menu");
          return null;
        });
      }

      //Maneja el fin del juego
      public static void GameEnd(){
        post("/final", (req,res) -> {
          req.session().removeAttribute("game_id");
          String value1 = req.queryParams("option1");
          String value2 = req.queryParams("option2");
          String value3 = req.queryParams("option3");
          if (value1!=null){
            Integer user_id = req.session().attribute("user_id");
            Game game = App.lastGame(user_id);
            req.session().attribute("game_id",game.getInteger("id"));
            res.redirect("/ruleta"); 
          }else if (value2!=null)
            res.redirect("/score");
          else 
            res.redirect("/menu");
          return null;
        });
      }

      //Verifica si la respuesta a la pregunta en ese juego es correcta y la carga al historial.
      public static boolean answerQuestion(Game g,Question q,Integer res){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/trivia", "proyecto", "felipe");
        Boolean ans = q.verificarRespuesta(res);
        boolean fire;
        if (!ans){
          g.restarVida();
          g.resetFire();
        }else{
          g.addFire();
        }
        History record = new History();
        record.set("user_id",g.get("user_id"));
        record.set("game_id",g.get("id"));
        record.set("question_id",q.get("id"));
        record.set("isCorrect",ans);
        record.save();
        Base.close();
        return ans;
      }  

      public static boolean youWin(Game game){
        boolean win = true;
        try{
          Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/trivia", "proyecto", "felipe");
          java.sql.Connection connection = Base.connection();
          String query = "select category_id from histories join questions join games on "+
          "(question_id = questions.id and game_id = games.id)"+
          " where (histories.user_id ="+game.getInteger("user_id")+
          " and status = true and isCorrect = true)";
          Statement st = connection.createStatement();
          st.executeQuery(query); 
          ResultSet resultSet = st.executeQuery(query);
          List<Integer> array = new ArrayList<Integer>();
          while (resultSet.next()) {
            array.add(resultSet.getInt(1));
          }
          Integer count = 0;
          for(int j=1;j<=6;j++){
            for(int i=0;i<array.size();i++){
              if (j==array.get(i))
                count++;
              if (count==3)
                break;
            }
            if (count<3){
              win = false;
              break;
            }
            count = 0;
          }
        Base.close();
        } catch(SQLException sqle) {
          sqle.printStackTrace();
          System.err.println("Error connecting: " + sqle);
        }
        return win;
      }


      //Verifica si tuvo 3 respuestas correctas consecutivas.
      private static boolean onFire(Integer game_id){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/trivia", "proyecto", "felipe");
        Game g = Game.findFirst("id = ?", game_id);
        boolean fire = g.catchingFire();
        Base.close();
        return fire;
      }

  

}
