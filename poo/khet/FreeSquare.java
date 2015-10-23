package poo.khet;

public class FreeSquare extends Square {

    FreeSquare(){
        super();
    }
    
    FreeSquare(Piece piece){
        super(piece);
    }
    
	@Override
	public boolean canAccomodate(Piece piece) {
		return false;
	}

}
