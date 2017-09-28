package trivia.model;

import org.javalite.activejdbc.Model;

public class Question extends Model {
  static{
    validatePresenceOf("question").message("Please, provide the category name");
    validatePresenceOf("answer1").message("Please, provide the category name");
    validatePresenceOf("answer2").message("Please, provide the category name");
    validatePresenceOf("rightans").message("Please, provide the category name");
  }

  public Boolean verificarRespuesta(Integer res){
  	return (res.equals(getInteger("rightans")));

  }

}
