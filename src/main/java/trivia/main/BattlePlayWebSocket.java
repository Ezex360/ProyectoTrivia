package trivia.main;

import org.eclipse.jetty.websocket.api.*;
import org.eclipse.jetty.websocket.api.annotations.*;

@WebSocket
public class BattlePlayWebSocket {

    private String sender, msg;

    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {
        String username = "User" + BattlePlay.nextUserNumber++;
        System.out.println();
        System.out.println(username+" connected +++");
        System.out.println();
        BattlePlay.userUsernameMap.put(user, username);
        BattlePlay.onHold.add(user);
        BattlePlay.makeGame();
    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        String username = BattlePlay.userUsernameMap.get(user);
        System.out.println();
        System.out.println(username+" disconnected ---");
        System.out.println();
        BattlePlay.userUsernameMap.remove(user);        
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
        String type = message.substring(0,3);
        String option = "";
        try{
            option = message.substring(3);
        }catch(Exception e){System.err.println("No se coloco opcion");}
        BattlePlay.OnlineGame active = BattlePlay.search(user);
        if (active == null){
        	System.out.println("Game not found!");
        }else{
        	System.out.println("Game found! ");
        }
        System.out.println();
        System.out.println(message + "/" + type + "/" + Integer.parseInt(option));
        System.out.println();
        if(type.equals("ans")){
            if(active.isPlayer1(user)){
                active.setRes1(Integer.parseInt(option));
            }else if (active.isPlayer2(user)){
                active.setRes2(Integer.parseInt(option));
            }else{
                System.out.println();
                System.out.println("EL JUGADOR NO FORMA PARTE DEL JUEGO");
                System.out.println();
            }
            Integer whoWin = active.compareAnswers();
            System.out.println();
            System.out.println(whoWin);
            System.out.println();
            if(active.gameEnd()){
                BattlePlay.sendEnd(active,active.getCorrects1(),active.getCorrects2());
            }else if(whoWin!=0){
                BattlePlay.sendResult(active,whoWin);
            }
        }else if(type.equals("qst")){
            if(active.isPlayer1(user)){
                BattlePlay.makeQuestion(active);
            }
        }else{
            System.out.println();
            System.out.println("NO SE ENTENDIO EL MENSAJE");
            System.out.println();   
        }

    
    }

}
