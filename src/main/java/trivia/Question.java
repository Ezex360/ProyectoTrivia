package trivia;

import org.javalite.activejdbc.Model;

public class Question extends Model {
  static{
    validatePresenceOf("category_id").message("Please, provide the category name");
    validatePresenceOf("question").message("Please, provide the category name");
    validatePresenceOf("answer1").message("Please, provide the category name");
    validatePresenceOf("answer2").message("Please, provide the category name");
    validatePresenceOf("answer3").message("Please, provide the category name");
    validatePresenceOf("answer4").message("Please, provide the category name");
    validatePresenceOf("rightans").message("Please, provide the category name");
  }
}
