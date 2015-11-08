package poo.khet;

/**
 * BeamFate representa el destino que tuvo un rayo en su recorrido por el
 * tablero, dado por el manejo en {@link BeamManager}
 * 
 * @see BeamManager
 */
public enum BeamFate {
    /** El rayo alcanz&oacute; una pieza y logr&oacute; impactarla */
	IMPACTED_PIECE, 
    
	/** El rayo termin&oacute; por irse de los l&iacute;mites del tablero */
	OUT_OF_BOUNDS, 
    
	/** El rayo fue desactivado por alg&uacute;n accesorio de alguna pieza */
	WAS_CONTAINED;
}
