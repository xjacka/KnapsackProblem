public class Ratio implements Comparable {

     Double ratio;
     int cena;
     int vaha;
     int puvodniPozice;
     
     Ratio(int cena,int vaha,int puvodniPozice){
         this.cena = cena;
         this.vaha = vaha;
         this.puvodniPozice = puvodniPozice;
         this.ratio = (double)cena / (double)vaha;
     }

    @Override
    public int compareTo(Object o) {
        return -this.ratio.compareTo(((Ratio)o).ratio);
    }

    public double getRatio() {
        return ratio;
    }

    public int getCena() {
        return cena;
    }

    public int getVaha() {
        return vaha;
    }
}
