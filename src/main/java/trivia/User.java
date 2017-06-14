package trivia;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.validation.UniquenessValidator;

public class User extends Model {
  static{
    validatePresenceOf("username").message("Please, provide your username");
    validateWith(new UniquenessValidator("username")).message("This username is already taken.");
  }

  public float obtenerPuntaje(){
  	return (getFloat("score"));
  }
}
