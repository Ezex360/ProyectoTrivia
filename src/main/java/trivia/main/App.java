package trivia.main;

import org.javalite.activejdbc.Base;
import static spark.Spark.*;
import static spark.Spark.staticFileLocation;
import java.util.List;

import trivia.model.Question;
import trivia.model.Game;

 class App{

     public static void main( String[] args ){
        
        //staticFiles.location("/public");
        String projectDir = System.getProperty("user.dir");
        String staticDir = "/src/main/resources/public";
        staticFiles.externalLocation(projectDir + staticDir);
        staticFiles.expireTime(600);
        webSocket("/battlesocket", BattlePlayWebSocket.class);
        init();
        
        before((req, res)->{
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/trivia", "proyecto", "felipe");
        });

        after((req, res) -> {
            Base.close();
        });


        Login.Welcome();
        Score.showScore();
        Score.showRanking();        
        FreePlay.IndividualGame();
        Menu.ShowMenu();

      }
      
      //Genera una pregunta aleatoria no respuesta anteriormente si es posible
      public static Question randomQuest(Integer cat_id,Integer user_id){
      	//Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/trivia", "proyecto", "felipe");
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
        //Base.close();
		return quest;
      }
      //Genera una pregunta aleatoria cualquiera
      public static Question randomQuest(){
        //Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/trivia", "proyecto", "felipe");
        List<Question> q = Question.findBySQL("SELECT * FROM questions Order by rand() LIMIT 1");
        //Base.close();
        return q.get(0);
      }


      //Devuelve una pregunta con un id de parametro
      public static Question questById(Integer id){
        //Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/trivia", "proyecto", "felipe");
        Question q = Question.findFirst("id = ?", id);
        //Base.close();
        return q;
      }

      //Retorna el ultimo juego activo, en caso de no existir, crea uno nuevo.
      public static Game lastGame(Integer user_id){
        //Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/trivia", "proyecto", "felipe");
        Game g;
        List<Game> games  = 
          Game.findBySQL("SELECT * FROM games WHERE(user_id = "+user_id+" AND status = true)");
        if (games.isEmpty()){
          g = new Game(); 
          g.set("user_id",user_id);
          g.save();
        }else
          g = games.get(0);
        //Base.close();
        return g;
      }    

      //Obtiene el juego con id pasada por paremetro.
      public static Game gameBySession(Integer game_id){
        //Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/trivia", "proyecto", "felipe");
        Game g = Game.findFirst("id = ?", game_id);
        //Base.close(); 
        return g;
      }


}
      
