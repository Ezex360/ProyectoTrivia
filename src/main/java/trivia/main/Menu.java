package trivia.main;

import trivia.model.Game;
import static spark.Spark.*;
import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

public class Menu { 
//Permite el manejo de las opciones del juego
      public static void ShowMenu(){
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
          String value4 = req.queryParams("option4");
          String value5 = req.queryParams("option5");
          //Ingresa al juego
          if (value1 != null){
            Integer user_id = req.session().attribute("user_id");
            Game game = App.lastGame(user_id);
            req.session().attribute("game_id",game.getInteger("id"));
            res.redirect("/ruleta");
          }
          //Mueve al menu de multijugador
          else if (value2 != null)
            res.redirect("/multiplayermenu");
          //Consulta el puntaje
          else if (value3 != null)
            res.redirect("/score");
          //Ingresa al ranking
          else if (value4 != null){
            res.redirect("/ranking");
          }
          //Vuelve a la pagina de inicio
          else if (value5 != null){
            req.session().removeAttribute("user_id");
            res.redirect("/welcome");
          }
          return null;
        });        
        get("/multiplayermenu", (req,res)-> {
          if (req.session().attribute("user_id") == null)
            res.redirect("/welcome"); 
          return new MustacheTemplateEngine().render(
            new ModelAndView(null, "twoplayersmenu.mustache")
          );

        });
        post("/multiplayermenu", (req,res)-> {
          if (req.session().attribute("user_id") == null)
            res.redirect("/welcome");
          String value1 = req.queryParams("option1");
          String value2 = req.queryParams("option2");
          String value3 = req.queryParams("option3");
          //Ingresa al juego modo battalla
          if (value1 != null){
            res.redirect("/battlemode.html");
          }
          //Ingresa al juego modo turnos
          else if (value2 != null)
            res.redirect("/turnmode");
          else if (value3 != null){
            res.redirect("/menu");
          }
          return null;
        });




      }


}
