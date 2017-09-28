package trivia.model;

import org.javalite.activejdbc.Model;

public class Category extends Model {
  static{
    validatePresenceOf("cat_name").message("Please, provide the category name");
  }
}
