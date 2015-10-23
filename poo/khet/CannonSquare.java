package poo.khet;

@Deprecated
public class CannonSquare extends Square {
    
    CannonSquare(Team team){
        super(new BeamCannon(team));
    }

    @Override
    public boolean canTakeTeam(Team team) {
        return false;
    }

}
