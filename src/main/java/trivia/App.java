package trivia;
import java.util.Scanner;
import org.javalite.activejdbc.Base;
import trivia.User;
import trivia.Category;
import trivia.Question;

public class App{

    public static void main( String[] args ){
    	clearScreen();
    	cargarUsuario();

    }

    private static void cargarUsuario(){
    	clearScreen();
    	System.out.println("Interfaz de Creacion de Usuario");
    	Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/trivia", "proyecto", "felipe");
      
      User u = new User();
    	System.out.println("Ingrese Nombre de Usuario");
    	String uname = leerTeclado();
      System.out.println("Ingrese contraseña");
      String upass = leerTeclado();    	
      u.set("username", uname);
      u.set("password", upass);
      u.saveIt();

      Base.close();
    }

    public static String leerTeclado(){
			String entradaTeclado = "";
    	Scanner entradaEscaner = new Scanner (System.in); 
    	entradaTeclado = entradaEscaner.nextLine();
    	return entradaTeclado;
		}

		public static void clearScreen() {  
	    System.out.print("\033[H\033[2J");  
	    System.out.flush();  
	  }  

}
