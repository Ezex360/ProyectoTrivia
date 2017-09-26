package trivia.main;

import org.javalite.activejdbc.Base;
import java.sql.*;
import static spark.Spark.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.math.BigDecimal;


import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

import trivia.model.User;
import trivia.model.Category;
import trivia.model.Question;
import trivia.model.History;
import trivia.model.Game;

 class App{

     public static void main( String[] args ){
        staticFileLocation("/public");
        
        Login.Welcome();
        FreePlay.IndividualGame();
        handleMenu();
        showScore();

      }

      //Permite el manejo de las opciones del juego
      private static void handleMenu(){
        //Muestra el menu
        get("/menu", (req, res) -> {
          if (req.session().attribute("user_id") == null)
            res.redirect("/welcome"); 
          return new MustacheTemplateEngine().render(
            new ModelAndView(null, "menu.mustache")
          );
        });
        //Recibe la opcion elegida
        post("/menu", (req, res) -> {
          if (req.session().attribute("user_id") == null)
            res.redirect("/welcome");
          String value1 = req.queryParams("option1");
          String value2 = req.queryParams("option2");
          String value3 = req.queryParams("option3");
          //Ingresa al juego
          if (value1 != null){
            Integer user_id = req.session().attribute("user_id");
            Game game = lastGame(user_id);
            req.session().attribute("game_id",game.getInteger("id"));
            res.redirect("/ruleta");
          }
          //Consulta el puntaje
          else if (value2 != null)
            res.redirect("/score");
          //Vuelve a la pagina de inicio
          else if (value3 != null){
            req.session().removeAttribute("user_id");
            res.redirect("/welcome");
          }
          return null;
        });        
      }

      //Muestra el puntaje.
      private static void showScore(){
        get("/score", (req, res) ->{
          if (req.session().attribute("user_id") == null)
            res.redirect("/welcome");
          //Traigo el id del usuario
          Integer user_id=req.session().attribute("user_id");
          Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/trivia", "proyecto", "felipe");
          User u=User.findFirst("id=?",user_id);
          Base.close();
          String score=calculateScore(user_id);
          Map map=new HashMap();
          map.put("user_score",score);
          return new MustacheTemplateEngine().render(
            new ModelAndView(map, "score.mustache")
          );
        });
        post("/score", (req, res) ->{
          res.redirect("/menu");
          return null;
        });   
      }

      
      private static String calculateScore(Integer user_id){        
        BigDecimal finalScore = BigDecimal.ZERO;
        try{
          Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/trivia", "proyecto", "felipe");
          User u = User.findFirst("id = ?",user_id);
          //Creo la conexion
          java.sql.Connection connection = Base.connection();
          //Query del conteo de correctas.
          String cantCorrectas = "select count(isCorrect) from histories where "+
          "(user_id = "+user_id+" and isCorrect = true) group by user_id";
          Statement st1 = connection.createStatement();
          ResultSet resultSet1 =st1.executeQuery(cantCorrectas);
          BigDecimal correctas = BigDecimal.ZERO;
          //Obtengo preguntas respuestas correctamente en ese juego.
          while(resultSet1.next()){
            correctas = new BigDecimal(resultSet1.getInt(1));
          }
          //Query del conteo de preguntas respuestas.
          String cantTotal = "select count(*) from histories where "+
          "(user_id = "+user_id+") group by user_id";
          Statement st2 = connection.createStatement();
          ResultSet resultSet2 =st2.executeQuery(cantTotal);
          BigDecimal total = BigDecimal.ZERO;
          //Obtengo preguntas respuestas en ese juego.
          while(resultSet2.next()){
            total = new BigDecimal(resultSet2.getInt(1));
          }
          //Calculo el porcentaje de aciertos
          if (total.compareTo(BigDecimal.ZERO) > 0){
            finalScore = correctas.divide(total,5,BigDecimal.ROUND_DOWN);
            BigDecimal hundread = new BigDecimal(100);
            finalScore = finalScore.multiply(hundread);
            finalScore = finalScore.setScale(2, BigDecimal.ROUND_HALF_UP);
	        if (finalScore.compareTo(hundread) < 0){
	            u.set("score", finalScore.toString());
	            u.save();
	          }         
          }
          Base.close();
        } catch(SQLException sqle) {
          sqle.printStackTrace();
          System.err.println("Error connecting: " + sqle);
        }; 
        return finalScore.toString();
      }


      //Genera una pregunta aleatoria no respuesta anteriormente si es posible
      public static Question randomQuest(Integer cat_id,Integer user_id){
      	Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/trivia", "proyecto", "felipe");
        List<Question> q = Question.findBySQL("SELECT * FROM questions where (category_id="+cat_id+") and"+
		" questions.id not in "+
		"(select question_id from histories join games on (game_id = games.id) "+
		"where (status=true and histories.user_id="+user_id+")) Order by rand() LIMIT 1");
        Question quest;
        if (!q.isEmpty()){
        		quest = q.get(0);
        }
        else{
        	List<Question> q1 = Question.findBySQL("SELECT * FROM questions where (category_id="+cat_id+")"+
        	" Order by rand() LIMIT 1");
        	quest = q1.get(0);
        }	
        Base.close();
		return quest;
      }

      //Devuelve una pregunta con un id de parametro
      public static Question questById(Integer id){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/trivia", "proyecto", "felipe");
        Question q = Question.findFirst("id = ?", id);
        Base.close();
        return q;
      }

      //Retorna el ultimo juego activo, en caso de no existir, crea uno nuevo.
      public static Game lastGame(Integer user_id){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/trivia", "proyecto", "felipe");
        Game g;
        List<Game> games  = 
          Game.findBySQL("SELECT * FROM games WHERE(user_id = "+user_id+" AND status = true)");
        if (games.isEmpty()){
          g = new Game(); 
          g.set("user_id",user_id);
          g.save();
        }else
          g = games.get(0);
        Base.close();
        return g;
      }    

      //Obtiene el juego con id pasada por paremetro.
      public static Game gameBySession(Integer game_id){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/trivia", "proyecto", "felipe");
        Game g = Game.findFirst("id = ?", game_id);
        Base.close(); 
        return g;
      }


}
      