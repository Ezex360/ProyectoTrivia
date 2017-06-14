package trivia;

import org.javalite.activejdbc.Base;
import java.sql.*;
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
        
        handleWelcome();
        handleMenu();
        showScore();
        handlePlay();
        handleResponse();
        handleRoulete();
        handleEnd();
        
      }


      
      private static void handleWelcome(){
        get("/welcome", (req, res) -> {
          return new MustacheTemplateEngine().render(
            new ModelAndView(null, "index.mustache")
          );
        });
        post("/register", (req, res) -> {
          Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/trivia", "proyecto", "felipe");
          String usern = req.queryParams("username");
          String pass = req.queryParams("password");

          User user = new User();
          user.set("username",usern);
          user.set("password",pass);
          user.set("score",0.0);
          if(!user.isValid())
            System.out.println("\n NO ES VALIDO WACHIN \n");
          user.save();
          Base.close();

          res.redirect("/welcome");
          return null;
        });
        post("/login", (req, res) -> {
          Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/trivia", "proyecto", "felipe");
          String usern = req.queryParams("username");
          String pass = req.queryParams("password");
          User user = User.findFirst("username = ?", usern);
          Base.close();
          if (user != null)
            if (pass.equals(user.getString("password"))){
              req.session(true);
              req.session().attribute("user_id",user.getInteger("id"));  
              res.redirect("/menu");
            }
            else res.redirect("/welcome");
          else res.redirect("/welcome");

          return null;
        });
      }

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
          if (value1 != null){
            Integer user_id = req.session().attribute("user_id");
            Game game = lastGame(user_id);
            req.session().attribute("game_id",game.getInteger("id"));
            res.redirect("/ruleta");
          }
          if (value2 != null)
            res.redirect("/score");
          if (value3 != null){
            req.session().removeAttribute("user_id");
            res.redirect("/welcome");
          }
          return null;
        });        
      }

      public static void handleRoulete(){
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
        post("/categories", (req, res) -> {
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

      private static void handlePlay(){
        //PLAY:ID ES NECESARIO
        get("/category/:id", (req, res) -> {
          if (req.session().attribute("user_id") == null
            || req.session().attribute("game_id") == null)
            res.redirect("/welcome");
          Integer cat_id = Integer.parseInt(req.params(":id").substring(1));
          Question quest = randomQuest(cat_id);
          Integer id_q = quest.getInteger("id");
          res.redirect("/play/:"+id_q);
          return null;
        });        
        get("/play/:id", (req, res) -> {
          if (req.session().attribute("user_id") == null
            || req.session().attribute("game_id") == null)
            res.redirect("/welcome");
          Question quest = questById(Integer.parseInt(req.params(":id").substring(1)));
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
        //fijar opcion nula?
        post("/play/:id", (req, res) -> {
          if (req.session().attribute("user_id") == null
            || req.session().attribute("game_id") == null)
            res.redirect("/welcome");
          Question quest = questById(Integer.parseInt(req.params(":id").substring(1)));
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
          boolean isCorrect = answerQuestion(gameBySession(id_g),quest,ans);
          if (isCorrect) 
            res.redirect("/correct");
          else
            res.redirect("/incorrect");
          return null;
        });
      }


      private static void handleResponse(){
        get("/correct", (req,res) -> {
         
          Map map = new HashMap();
          Integer id_g = req.session().attribute("game_id");
          Game g = gameBySession(id_g);
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
        get("/incorrect", (req,res) -> {
          Map map = new HashMap();
          Integer id_g = req.session().attribute("game_id");
          Game g = gameBySession(id_g);
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

      private static void handleEnd(){
        post("/final", (req,res) -> {
          String value1 = req.queryParams("option1");
          String value2 = req.queryParams("option2");
          String value3 = req.queryParams("option3");
          if (value1!=null)
            res.redirect("/ruleta");
          if (value2!=null)
            res.redirect("/score");
          else 
            res.redirect("/menu");
          return null;
        });

      }

      private static void showScore(){
        get("/score", (req, res) ->{
          if (req.session().attribute("user_id") == null)
            res.redirect("/welcome");
          //Traigo el id del usuario
          Integer user_id=req.session().attribute("user_id");
          Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/trivia", "proyecto", "felipe");
          User u=User.findFirst("id=?",user_id);
          Base.close();
          Float score=calculateScore(user_id);
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

      private static boolean youWin(Game game){
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

      private static Float calculateScore(Integer user_id){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/trivia", "proyecto", "felipe");
        User u = User.findFirst("id = ?",user_id);
        String cantCorrectas = "select histories.* from histories join games on (game_id = games.id)"+
        " where (histories.user_id = "+user_id+" and status = false and isCorrect = true)";
        String cantRespuestas = "select histories.* from histories join games on (game_id = games.id)"+
        " where (histories.user_id = "+user_id+" and status = false)";
        List<History> listaCorrectas = History.findBySQL(cantCorrectas);
        List<History> listaTotal = History.findBySQL(cantRespuestas);
        Integer correctas = listaCorrectas.size();
        Integer total = listaCorrectas.size();
        Float finalScore;
        if (total == 0)
          finalScore = new Float(0);
        else{
          finalScore = (float) correctas / total;
          u.set("score",finalScore);
          u.save();
        }
        Base.close(); 
        return finalScore;
      }



      private static Question randomQuest(Integer cat_id){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/trivia", "proyecto", "felipe");
        List<Question> questions  = Question.findBySQL("SELECT * FROM questions WHERE"+
        " (category_id = "+cat_id+")ORDER BY RAND() LIMIT 1");
        Question q = questions.get(0);
        Base.close();
        return q;
      }

      private static Question questById(Integer id){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/trivia", "proyecto", "felipe");
        Question q = Question.findFirst("id = ?", id);
        Base.close();
        return q;
      }

      private static Game lastGame(Integer user_id){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/trivia", "proyecto", "felipe");
        Game g;
        List<Game> games  = 
          Game.findBySQL("SELECT * FROM games WHERE(user_id = "+user_id+" AND status = true)");
        if (games.isEmpty()){
          g = new Game(); //MARK
          g.set("user_id",user_id);
          g.save();
        }else
          g = games.get(0);
        Base.close();
        return g;
      }    

      private static Game gameBySession(Integer game_id){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/trivia", "proyecto", "felipe");
        Game g = Game.findFirst("id = ?", game_id);
        Base.close();
        return g;
      }

      private static boolean onFire(Integer game_id){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/trivia", "proyecto", "felipe");
        Game g = Game.findFirst("id = ?", game_id);
        boolean fire = g.catchingFire();
        Base.close();
        return fire;
      }

      private static boolean answerQuestion(Game g,Question q,Integer res){
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

}
      
// (1,' ',' ',' ',' ',' ',1,Now(),Now()),

