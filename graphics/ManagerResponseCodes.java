package graphics;

/**
 * Constantes devueltas por los metodos de {@link GameManager} ante est&iacute;mulos
 * del usuario. Consultar los m&eacute;todos correspondientes.
 * 
 * @see GameManager#handle(Position)
 * @see GameManager#handleRotation(boolean)
 */
public enum ManagerResponseCodes {
	
    /**
     * Se proces&oacute; la acci&oacute;n de manera correcta
     */
	OK, 
    
    /**
     * Se quiere seleccionar una pieza de un equipo incorrecto
     */
    INVALID_TEAM_SELECTED, 
    
    /**
     * El movimiento requerido es inv&aacute;lido
     */
    INVALID_MOVE_SELECTED, 
    
    /**
     * Devuelto si no corresponde rotar (etapa de selecci&oacute;n)
     */
    CANT_ROTATE_RIGHT_NOW
}
