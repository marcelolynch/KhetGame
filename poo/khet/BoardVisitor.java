package poo.khet;

public interface BoardVisitor {
    public boolean visit(Anubis anubis);
    public boolean visit(Pyramid pyramid);
    public boolean visit(Scarab scarab);
    public boolean visit(BeamCannon beamCannon);
    public boolean visit(Pharaoh pharaoh);
}
