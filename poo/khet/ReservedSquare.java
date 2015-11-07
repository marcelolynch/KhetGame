package poo.khet;

/**
 * Casillero reservado: solamente puede alojar piezas de cierto equipo
 */
public class ReservedSquare extends Square {
    private Team team;

    /**
     * Construye un casillero reservado vac&iacute;o.
     * 
     * @param team - equipo para el cual se reserva el casillero
     */
    ReservedSquare(Team team) {
        super();
        setTeam(team);
    }

    /**
     * Construye un casillero reservado con una <tt>Piece</tt>.
     * 
     * @param piece - <tt>Piece</tt> a ocupar el casillero
     * @param team - equipo para el cual se reserva el casillero
     */
    ReservedSquare(Piece piece, Team team) {
        this(team);
        if (!getTeam().equals(piece.getTeam())) {
            throw new IllegalStateException();// TODO: excepciones
        }
        setOccupant(piece);
    }

    /**
     * Devuelve el equipo para el cual se reserva el casillero
     * 
     * @return el equipo para el cual se reserva el casillero
     */
    public Team getTeam() {
        return team;
    }

    private void setTeam(Team team) {
        if (team == null) {
            throw new IllegalArgumentException("El equipo especificado es null");
        }
        this.team = team;
    }

    @Override
    public void setOccupant(Piece piece) {
        if (!canAccomodate(piece)) {
            throw new IllegalArgumentException();
        }
        super.setOccupant(piece);
    }

    @Override
    public boolean canAccomodate(Piece piece) {
        return piece.getTeam().equals(this.getTeam()) && super.canAccomodate(piece);
    }


}
