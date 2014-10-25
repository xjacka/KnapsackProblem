
import java.util.Arrays;

public class Result {
    final int id;
    int pocetVeci = 0;
    int cenaReseni = 0;
    int [] reseni;
    int vahaVeci = 0;
    SolveMethod name;
    public RunTime time;

    public static enum SolveMethod {
        BRUTE_FORCE("bruteForce"),
        REFERENCE("reference"),
        BRANCH_AND_BOUND("branchAndBound"),
        FPTAS("FPTAS"),
        DYNAMIC("dymanic"),
        HEURISTIC("heuristic");
    
        private final String name;

        SolveMethod(String name){
            this.name = name;
        }
        public String getName(){
            return this.name;
        }
    };
    
    public void setName(SolveMethod name) {
        this.name = name;
    }

    public SolveMethod getName() {
        return name;
    }
    
    public Result(int n,int id,SolveMethod name){
        this.reseni = new int[n];
        this.pocetVeci = n;
        this.id = id;
        this.name = name;
    }
    
    public Result(ProgramInstance programInstance,SolveMethod name){
        this.reseni = new int[programInstance.getPocetVeci()];
        this.pocetVeci = programInstance.getPocetVeci();
        this.id = programInstance.getId();
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getPocetVeci() {
        return pocetVeci;
    }

    public void setPocetVeci(int pocetVeci) {
        this.pocetVeci = pocetVeci;
    }

    public int getCenaReseni() {
        return cenaReseni;
    }

    public void setCenaReseni(int cenaReseni) {
        this.cenaReseni = cenaReseni;
    }

    public int[] getReseni() {
        return reseni;
    }

    public void addReseni(int position, int reseni) {
        this.reseni[position] = reseni;
    }
    
    public void setReseni(int [] reseni) {
        this.reseni = reseni;
    }
    
    @Override
    public String toString(){
        String reseni = "";
        for(int res : this.reseni){
            reseni += res + " ";
        }
        return "id: " + id + ", pocet: " + pocetVeci + ", cena_reseni: " + cenaReseni + " [" + reseni + "]";
    }

    public boolean equals(Result ot){
        return this.id == ot.id && this.cenaReseni == ot.cenaReseni && this.pocetVeci == ot.pocetVeci && Arrays.equals(this.reseni, ot.reseni);
    }
    
    public void addRatio(Ratio ratio){
        this.cenaReseni += ratio.getCena();
        this.reseni[ratio.puvodniPozice] = 1;
        this.vahaVeci += ratio.getVaha();
    }

    public int getVahaVeci() {
        return vahaVeci;
    }

    public void setVahaVeci(int vahaVeci) {
        this.vahaVeci = vahaVeci;
    }    

    public double getTime(RunTime.Unit in){
        return time.getTime(in);
    }
    
    public static class RunTime {

        public static enum Unit {

           NANO(1),
           MILLI(1000000),
           SECOND(1000000000);
           
           private final int rate;

           private Unit(int rate) {
               this.rate = rate;
           }

           public int getRate() {
               return this.rate;
           }
       }
        
        Long t1;
        Long t2;

        public RunTime(Long t1, Long t2) {
            super();
            this.t1 = t1;
            this.t2 = t2;
        }

        private double getTime(RunTime.Unit in) {
            return ((double) (this.t2 - this.t1)) / in.getRate();
        }
    }
}
