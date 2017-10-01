package trivia.main;

import trivia.model.User;
import org.javalite.activejdbc.Base;
import java.util.HashMap;
import java.util.Map;
import java.math.BigDecimal;
import java.sql.*;
import static spark.Spark.*;
import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

public class Score {
  
	//Muestra el puntaje.
    public static void showScore(){
   		get("/score", (req, res) ->{
        	if (req.session().attribute("user_id") == null)
          		res.redirect("/welcome");
        	//Traigo el id del usuario
        	Integer user_id=req.session().attribute("user_id");
        	Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/giache5_trivia", "giache5_proyecto", "felipe");
        	User u=User.findFirst("id=?",user_id);
        	Base.close();
        	String score=calculateScore(user_id);
        	Map map=new HashMap();
        	map.put("user_score",score);
        	return new MustacheTemplateEngine().render(
          	new ModelAndView(map, "score.mustache"));
      	});
       
      	post("/score", (req, res) ->{
        	res.redirect("/menu");
        	return null;
      	});   
     }

    public static String calculateScore(Integer user_id){        
    	BigDecimal finalScore = BigDecimal.ZERO;
    	try{
    		Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/giache5_trivia", "giache5_proyecto", "felipe");
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
        } 
        catch(SQLException sqle) {
        	sqle.printStackTrace();
        	System.err.println("Error connecting: " + sqle);
        }; 
        return finalScore.toString();
	}  
}