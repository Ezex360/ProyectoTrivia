package trivia.main;

import trivia.model.User;
import org.javalite.activejdbc.Base;
import java.util.HashMap;
import java.util.Map;
import static spark.Spark.*;
import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;
import trivia.model.Utilidades;


public class Login {
	
	public static void Welcome(){
		get("/", (req, res) -> {
			res.redirect("/welcome");
			return null;
		});
		//Muestra la pagina principal
		get("/welcome", (req, res) -> {
			Map map = new HashMap();
			return new MustacheTemplateEngine().render(
			new ModelAndView(map, "index.mustache")
		);
		});
		//Permite el registro de nuevo usuario
		post("/register", (req, res) -> {
			//Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/trivia", "proyecto", "felipe");
			String usern = req.queryParams("username");
			String passwithoutEncryption = req.queryParams("password");
			String pass = Utilidades.Encriptar(passwithoutEncryption);
			Map map = new HashMap();

			Long repetido = User.count("username = ?",usern);
			if (repetido != 0)
			map.put("error_register",true);
			else{
			map.put("register_ok",true);
			User user = new User();
			user.set("username",usern);
			user.set("password",pass);
			user.set("score",0.0);
			user.save();
			}
			//Base.close();
			return new MustacheTemplateEngine().render(
			new ModelAndView(map, "index.mustache")
			);
		});
		//Permite el ingreso de un usuario al sistema
		post("/login", (req, res) -> {
			//Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/trivia", "proyecto", "felipe");
			Map map = new HashMap();
			String usern = req.queryParams("username");
			String pass = req.queryParams("password");
			User user = User.findFirst("username = ?", usern);
			String passWithEncryption="";
			String passwithoutEncryption="";
			//Base.close();
			if (user != null)
				passWithEncryption = user.getString("password");
				System.out.println(passWithEncryption);
				passwithoutEncryption = Utilidades.Desencriptar(passWithEncryption);
				if (pass.equals(passwithoutEncryption)){
					req.session(true);
					req.session().attribute("user_id",user.getInteger("id"));  
					res.redirect("/menu");
				}

			map.put("error_login",true);
			return new MustacheTemplateEngine().render(
			new ModelAndView(map, "index.mustache")
			);
		});
	}


}
