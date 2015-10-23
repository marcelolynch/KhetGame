package poo.khet;

import poo.khet.gameutils.Direction;

/**
 * Clase experimental por ahi ni la usamos. Quiero ver si sale.
 * @author Marcelo
 *
 */
class Mirror {
    Direction[] receivableDirs = new Direction[2]; // Array para testear, puede ser distinto

    public Mirror(Direction dir1, Direction dir2) {
        receivableDirs[0] = dir1;
        receivableDirs[1] = dir2;
    }
    
    public void rotateClockwise() {
        receivableDirs[0] = receivableDirs[0].getClockwiseDirection();
        receivableDirs[1] = receivableDirs[1].getClockwiseDirection();
    }

    public void rotateCounterClockwise() {
        receivableDirs[0] = receivableDirs[0].getCounterClockwiseDirection();
        receivableDirs[1] = receivableDirs[0].getCounterClockwiseDirection();
    }

    /**
     * Si puede reflejar rayos que vengan desde cierta direccion
     * 
     * @param direction - la direccion de viaje del rayo
     * @return
     */
    public boolean canReflectFrom(Direction direction) {
        return direction.isOppositeDirection(receivableDirs[0])
                || direction.isOppositeDirection(receivableDirs[1]);
    }

    /**
     * Refleja el rayo desde la Orientacion desde que recibio
     * y en la direccion de la otra
     * @param beam
     * @return
     */
    public Beam reflect(Beam beam) {
        if (!canReflectFrom(beam.getDirection()))
            throw new IllegalArgumentException(); // TODO: Excepciones

        if (beam.getDirection().isOppositeDirection(receivableDirs[0])){
            beam.setDirection(receivableDirs[1]);
        }
        else{
            beam.setDirection(receivableDirs[0]);
        }
        
        return beam;

    }
    
    public static void main(String[] args) {
        Beam b = new Beam(Direction.WEST);
        
        System.out.println(b.getDirection());
        
        Mirror m = new Mirror(Direction.NORTH, Direction.EAST);
        
        System.out.println(m.canReflectFrom(Direction.WEST));
        m.reflect(b);
        
        
        System.out.println(b.getDirection()); // NORTH
    }
    
}
