package poo.khet;

import java.util.Observable;
import java.util.Observer;

public class PharaohObserver implements Observer {
	//Por ahora los Booleans indican si alguna pieza esta muerta o no
	//No tuve mucha imaginacion con los nombres
	//NOTA: Acordarse de agregarle el Observer a cada Faraón en la clase que lo inicializa
	private Boolean redPharaohdead;
	private Boolean silverPharaohdead;
	
	public PharaohObserver(){
		redPharaohdead = false;
		silverPharaohdead = false;
	}
	
	/**
	 * Indica de que equipo muere el faraón. Cambia de <tt>False</tt> a <tt>True</tt>
	 * el estado.
	 */
	
	@Override
	public void update(Observable o, Object arg) {
		Team destroyedPieceTeam = (Team)arg;
		if(destroyedPieceTeam.equals(Team.RED)){
			setRedPharaohdead(true);
		} else if (destroyedPieceTeam.equals(Team.SILVER)){
			setSilverPharaohdead(true);
		}
	}

	public Boolean redPharaohIsdead() {
		return redPharaohdead;
	}

	public void setRedPharaohdead(Boolean redPharaohdead) {
		this.redPharaohdead = redPharaohdead;
	}

	public Boolean silverPharaohIsdead() {
		return silverPharaohdead;
	}

	public void setSilverPharaohdead(Boolean silverPharaohdead) {
		this.silverPharaohdead = silverPharaohdead;
	}
	
	/**
	 * Responde si el faraón de algun equipo murio.
	 * @return Team.RED si muere el faraón del equipo rojo, Team.SILVER 
	 * si muere el faraon del equipo plateado, o null en caso de que ninguno muera
	 */
	
	//queda default?
	Team destroyedPharaoh(){
		if(redPharaohIsdead()){
			return Team.RED;
		} else if (silverPharaohIsdead()) {
			return Team.SILVER;
		}
		else {
			return null; //Esto es medio feo
		}
	}
	

	
}
