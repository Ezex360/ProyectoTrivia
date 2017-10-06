package trivia.model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.validation.UniquenessValidator;

public class User extends Model {
  
	//public User(Integer id,String user,String pass){
	//	set("id", id);
    //	set("username", user);
    //	set("password", pass);
    //	set("score",0);
  	//}

  static{
    validatePresenceOf("username").message("Please, provide your username");
    validateWith(new UniquenessValidator("username")).message("This username is already taken.");
  }

}
