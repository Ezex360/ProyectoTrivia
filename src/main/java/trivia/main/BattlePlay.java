package trivia.main;

import org.javalite.activejdbc.Base;
import java.sql.*;
import static spark.Spark.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.LinkedList;

import org.eclipse.jetty.websocket.api.Session;
import org.json.JSONObject;
import com.google.gson.Gson;



import trivia.model.Question;
import trivia.model.History;
import trivia.model.Game;

public class BattlePlay {


    public static Map<Session,String> userUsernameMap = new HashMap<>();
    public static List<OnlineGame> games = new ArrayList<>();
    public static Queue<Session> onHold = new LinkedList<>();
    public static int nextUserNumber = 1;
    public static int gameNumber = 1;


    public static void makeGame(){
    	if (onHold.size() >= 2) {
    		Session player1 = onHold.poll();
    		Session player2 = onHold.poll();
    		OnlineGame neww = new OnlineGame(player1,player2);
    		neww.setId(games.size()+1);
    		games.add(neww);
            System.out.println();
            System.out.println("GAME CREATED;");
            System.out.println();
    		for(int i=0;i<games.size();i++){
    			System.out.print("game id: "+games.get(i).getId()+"/");
    		}
    		makeQuestion(neww);
    	}
    }

    public static void makeQuestion(OnlineGame active){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/trivia", "proyecto", "felipe");
    	Question quest = App.randomQuest();
        Base.close();
        active.setQuestion(quest);
    	try {
            active.getPlayer1().getRemote().sendString(String.valueOf(new JSONObject()
                .put("type","1")
                .put("question",quest.getString("question"))
                .put("option1",quest.getString("answer1") )
                .put("option2",quest.getString("answer2") )
                .put("option3",quest.getString("answer3") )
                .put("option4",quest.getString("answer4") )
            ));
            active.getPlayer2().getRemote().sendString(String.valueOf(new JSONObject()
                .put("type","1")
                .put("question",quest.getString("question"))
                .put("option1",quest.getString("answer1") )
                .put("option2",quest.getString("answer2") )
                .put("option3",quest.getString("answer3") )
                .put("option4",quest.getString("answer4") )
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendResult(OnlineGame active, Integer res){
        String message;
        if(res == 1){
            message = "El jugador 1 respondio correctamente";
        } else if(res == 2){
            message = "El jugador 2 respondio correctamente";
        } else if(res == 3){
            message = "Ambos se equivocaron.";
        }else if(res == 4){
            message = "Ambos acertaron!";
        }else {
            message = "Hubo un error obteniendo el resultado :c";
        }
        try {
            active.getPlayer1().getRemote().sendString(String.valueOf(new JSONObject()
                .put("type","2")
                .put("result",message)
            ));
            active.getPlayer2().getRemote().sendString(String.valueOf(new JSONObject()
                .put("type","2")
                .put("result",message)
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static OnlineGame search(Session player){
    	System.out.println("Searching in "+games.size()+" Active Games.");
    	for (int i=0;i<games.size() ;i++) {
    		OnlineGame game = games.get(i);
    		if(game.isPlayer1(player) || game.isPlayer2(player)){
    			return game;
    		}
    	}
    	return null;
    }

	public static class OnlineGame {
		public Integer id;
		private Session player1;
		private Session player2;
        public Integer res1;
        public Integer res2;
		public Question active_quest;
		public Integer done_questions;

		public OnlineGame(Session one, Session two){
			player1 = one;
			player2 = two;
            res1 = 0;
            res2 = 0;
			active_quest = null;
			done_questions = 0;
		}

		public void setId(Integer i){
			id = i;
		}
		public Integer getId(){
			return id;
		}

		public boolean gameEnd(){
			return done_questions == 10;
		}

		public Session getPlayer1(){
			return player1;
		}
		public Session getPlayer2(){
			return player2;
		}
        public boolean isPlayer1(Session compare){
            return player1 == compare;
        }
        public boolean isPlayer2(Session compare){
            return player2 == compare;
        }

		public void setQuestion(Question q){
			active_quest = q;
		}

        public void setRes1(Integer r){
            res1 = r;
        }
        public void setRes2(Integer r){
            res2 = r;
        }


        public boolean connected(){
            boolean one = player1 != null;
            boolean two = player2 != null;
            return one && two;
            
        }

		public Integer compareAnswers(){
            System.out.println("res1 = "+res1);
            System.out.println("res2 = "+res2);
            if (res1==0 || res2==0)
                return 0;
            else{
    			boolean ans1 = active_quest.verificarRespuesta(res1);
    			boolean ans2 = active_quest.verificarRespuesta(res2);
    			done_questions++;
                res1=0;
                res2=0;
    			active_quest = null;
    			if(ans1 && !ans2){
    				return 1;
    			} else if (ans2 && !ans1){
    				return 2;
    			} else if(!ans1 && !ans2) {
    				return 3;
    			}else{
                    return 4;
                }
            }
		}
		
	}

	public static Session getKey(String value){
        for(Session key : userUsernameMap.keySet()){
            if(userUsernameMap.get(key).equals(value)){
                return key; //return the first found
            }
        }
        return null;
    }
}