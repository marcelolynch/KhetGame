package poo.khet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Una pieza es un componente del juego que tiene un equipo,
 * y un conjunto de {@link Accessories}. La pieza puede "rotar"
 * rotando sus accesorios en ambas direcciones.
 * 
 * La pieza puede recibir un {@link Beam} y ser afectado por el o no,
 * segun si los accesorios que contiene pueden procesar el rayo entrante 
 * o no
 * 
 * @see Accessory
 */
public abstract class Piece2 implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Equipo al que pertenece la pieza
     */
    private final Team team;

    
    private final List<Accessory> accessories;
    
    /**
     * Construye una nueva pieza del equipo especificado
     * @param team - el equipo al que pertenece la pieza
     * @see Team
     */
    Piece2(Team team) {
        if (team == null) {
            throw new IllegalArgumentException();
        }
        accessories = new ArrayList<Accessory>();
        this.team = team;
    }

    /**
     * Indica el equipo al que pertenece esta pieza
     * @return - el equipo
     */
    public Team getTeam() {
        return team;
    }

    /**
     * Chequea si la casilla es un destino valido
     * 
     * @param square - la casilla destino
     * @return <tt>true</tt> si es valido, <tt>false</tt> si no.
     */
    boolean canMove(Square square) {
        return square.isEmpty();
    }

    /**
     * Procesa el <code>Beam</code> que se pasa como par&aacute;metro, modific&aacute;ndolo
     * de ser necesario. El valor de retorno indica si la pieza fue o no afectada por el rayo,
     * es decir, si algun accesorio pudo recibir el rayo o no
     * 
     * @param beam - el rayo a procesar
     * @returns <tt>true</tt> si la <tt>Pieza</tt> pudo contener o reflejar el rayo; <tt>false</tt> 
     *          se vio afectada por el rayo
     */
    boolean receiveBeam(Beam beam){
    	boolean contained = false;
    	for(int i = 0; !contained && i < accessories.size() ; i++){
    		Accessory a = accessories.get(i);
    		
    		if(a.canProcessBeam(beam)){
    			a.processBeam(beam);
    			contained = true;
    		}
    	}
    	return contained;
    }

    /**
     * Indica si la pieza es intercambiable (enrocable) ante una pieza que puede intercambiar
     * (como {@link Scarab})
     * @return - <code>true</code> si la pieza es enrocable, <code>false</code> de lo contrario
     */
    abstract boolean canBeSwapped();

    /**
     * Rota la pieza en el sentido de las agujas del reloj
     */
    public void rotateClockwise(){
    	for(Accessory each: accessories){
    		each.rotateClockwise();
    	}
    }

    /**
     * Rota la pieza en el sentido contrario a las agujas del reloj
     */
    public void rotateCounterClockwise(){
    	for(Accessory each: accessories){
    		each.rotateCounterClockwise();
    	}
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!this.getClass().equals(obj.getClass())) {
            return false;
        }

        Piece2 p = (Piece2) obj;
        return getTeam().equals(p.getTeam()) && accessories.equals(p.accessories);
    }

}
