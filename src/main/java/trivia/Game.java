package trivia;

import org.javalite.activejdbc.Model;

public class Game extends Model {


	public void restarVida(){
		set("lifes",(getInteger("lifes")-1) );
	}

	public void terminarJuego(){
		set("status",false);
	}	

	

}


