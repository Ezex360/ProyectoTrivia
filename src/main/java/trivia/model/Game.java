package trivia.model;

import org.javalite.activejdbc.Model;

public class Game extends Model {


	public void restarVida(){
		set("lifes",(getInteger("lifes")-1) );
		save();
	}

	
	public void resetFire(){
		set("consecutives",0);
		save();
	}

	public void addFire(){
		Integer cons = getInteger("consecutives");
		if (cons >3)
			resetFire();
		else{
			set("consecutives",(getInteger("consecutives")+1) );
			save();
		}
	}

	public boolean catchingFire(){
		Integer cons = getInteger("consecutives");
		return cons==3;
	}


	public void terminarJuego(){
		set("status",false);
		save();
	}	

	

}


