
import java.util.Arrays;

public class Result {
    final int id;
    int pocetVeci = 0;
    int cenaReseni = 0;
    int [] reseni;
    int vahaVeci = 0;
    int navstivenychStavu = 0;
    SolveMethod name;
    public RunTime time;

    void updateVelues(ProgramInstance programInstance) {
        cenaReseni = 0;
        vahaVeci = 0;
        for(int j = 0; j < pocetVeci; j++){        
            if(reseni[j] == 1){
                cenaReseni += programInstance.ceny[j];
                vahaVeci += programInstance.vahy[j];                
            }
        }
    }

    public static enum SolveMethod {
        BRUTE_FORCE("bruteForce"),
        REFERENCE("reference"),
        BRANCH_AND_BOUND("branchAndBound"),
        FPTAS2("FPTAS2"),
        FPTAS4("FPTAS4"),
        FPTAS8("FPTAS8"),
        FPTAS16("FPTAS16"),
        FPTAS32("FPTAS32"),
        FPTAS64("FPTAS64"),
        DYNAMIC("dymanic"),
        SIMULATED_ANNEALIN("simulatedAnnealin"),
        GENETIC("genetic"),
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
    
    public Result(Result result){
        id = result.id;
        pocetVeci = result.pocetVeci;
        cenaReseni = result.cenaReseni;
        reseni = new int[result.getPocetVeci()];
        System.arraycopy(result.reseni, 0, reseni, 0, result.getPocetVeci());               
        vahaVeci = result.vahaVeci;
        navstivenychStavu = result.navstivenychStavu;
        name = result.name;
        time = result.time;
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
        String reslt = "";
        for(int res : this.reseni){
            reslt += res + " ";
        }
        return "id: " + id + ", pocet: " + pocetVeci + ", cena_reseni: " + cenaReseni + " [" + reslt + "]";
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
