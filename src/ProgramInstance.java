
public class ProgramInstance {
    int [] vahy;
    int [] ceny;
    int id;
    int pocetVeci;
    int kapacitaBatohu;

    public ProgramInstance(int size){
        this.vahy = new int[size];
        this.ceny = new int[size];
    }
    
    public int[] getVahy() {
        return vahy;
    }

    public void addVaha(int position, int vaha) {
        this.vahy[position] = vaha;
    }

    public int[] getCeny() {
        return ceny;
    }

    public void addCena(int position, int cena) {
        this.ceny[position] = cena;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPocetVeci() {
        return pocetVeci;
    }

    public void setPocetVeci(int pocetVeci) {
        this.pocetVeci = pocetVeci;
    }

    public int getKapacitaBatohu() {
        return kapacitaBatohu;
    }

    public void setKapacitaBatohu(int kapacitaBatohu) {
        this.kapacitaBatohu = kapacitaBatohu;
    }
    
    @Override
    public String toString(){
        return "id: " + id + ", pocet: " + pocetVeci + ", kapacita batohu: " + kapacitaBatohu;
    }
}
