
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class Reporter {

    public static enum reportDetail {DEVELOP, FILE, FULL};
    
    HashMap<Result.SolveMethod,Result> results;
    ProgramInstance instance;
    
    public Reporter(HashMap<Result.SolveMethod,Result> results, ProgramInstance instance){
        this.results = results;
        this.instance = instance;                
    }
    
    public void print(Reporter.reportDetail detail){
        if(detail == reportDetail.FULL){
            System.out.print(results.get(Result.SolveMethod.REFERENCE) .getId() + ">");
            for (Result res : results.values()){
                System.out.print(" " +res.getName().getName() + ": ");
                System.out.print(res.cenaReseni);
                System.out.print(" (" + res.getTime(Result.RunTime.Unit.MILLI) + ") ");
            }
            System.out.println("");
        }
        if(detail == reportDetail.DEVELOP){
//            System.out.print(results.get(Result.SolveMethod.BRUTE_FORCE).cenaReseni);
//            System.out.print(" (" + results.get(Result.SolveMethod.BRUTE_FORCE).getTime(Result.RunTime.Unit.MILLI) + ") ");
//
//            System.out.print(results.get(Result.SolveMethod.BRANCH_AND_BOUND).cenaReseni);
//            System.out.print(" (" + results.get(Result.SolveMethod.BRANCH_AND_BOUND).getTime(Result.RunTime.Unit.MILLI) + ") ");
//            
//            System.out.print("= " + (results.get(Result.SolveMethod.BRUTE_FORCE).getTime(Result.RunTime.Unit.MILLI) / results.get(Result.SolveMethod.BRANCH_AND_BOUND).getTime(Result.RunTime.Unit.MILLI)));
//            
//            if(results.get(Result.SolveMethod.BRUTE_FORCE).cenaReseni != results.get(Result.SolveMethod.BRANCH_AND_BOUND).cenaReseni){
//                System.out.print(" false");
//            }
//            System.out.println("");
//            System.out.print(results.get(Result.SolveMethod.REFERENCE).getCenaReseni() + " ");
//            System.out.print(results.get(Result.SolveMethod.FPTAS2).getCenaReseni() + " ");
//            System.out.print(results.get(Result.SolveMethod.FPTAS4).getCenaReseni() + " ");
//            System.out.print(results.get(Result.SolveMethod.FPTAS8).getCenaReseni() + " ");
//            System.out.print(results.get(Result.SolveMethod.FPTAS16).getCenaReseni() + " ");
//            System.out.print(results.get(Result.SolveMethod.FPTAS32).getCenaReseni() + " ");
//            System.out.println(results.get(Result.SolveMethod.FPTAS64).getCenaReseni());
            
            System.out.print(results.get(Result.SolveMethod.REFERENCE).getCenaReseni() + " ");
            System.out.print(results.get(Result.SolveMethod.FPTAS2).getCenaReseni() + " ");
            System.out.println(results.get(Result.SolveMethod.FPTAS8).getCenaReseni() + " ");
        }
        if(detail == Reporter.reportDetail.FILE){
            System.out.print(".");
            File resultFile = new File("result.dat");
            
            if (!resultFile.exists()) {
                try {
                    resultFile.createNewFile();
                } catch (IOException ex) {
                    System.out.println("Can not create a new file");
                }
            }

            try {                   
                FileWriter fw = new FileWriter(resultFile.getAbsoluteFile(),true);
                try (BufferedWriter bw = new BufferedWriter(fw)) {
                    // casy
                    bw.write(String.format("%1.5f ", results.get(Result.SolveMethod.HEURISTIC).getTime(Result.RunTime.Unit.MILLI))); // 1
                    bw.write(String.format("%11.5f ", results.get(Result.SolveMethod.BRUTE_FORCE).getTime(Result.RunTime.Unit.MILLI))); // 2
                    bw.write(String.format("%11.5f ", results.get(Result.SolveMethod.DYNAMIC).getTime(Result.RunTime.Unit.MILLI))); // 3
                    bw.write(String.format("%11.5f ", results.get(Result.SolveMethod.BRANCH_AND_BOUND).getTime(Result.RunTime.Unit.MILLI))); // 4
                    bw.write(String.format("%11.5f ", results.get(Result.SolveMethod.FPTAS2).getTime(Result.RunTime.Unit.MILLI))); // 5
                    bw.write(String.format("%11.5f ", results.get(Result.SolveMethod.FPTAS4).getTime(Result.RunTime.Unit.MILLI))); // 6
                    bw.write(String.format("%11.5f ", results.get(Result.SolveMethod.FPTAS8).getTime(Result.RunTime.Unit.MILLI))); // 7
                    bw.write(String.format("%11.5f ", results.get(Result.SolveMethod.FPTAS16).getTime(Result.RunTime.Unit.MILLI))); // 8
                    bw.write(String.format("%11.5f ", results.get(Result.SolveMethod.FPTAS32).getTime(Result.RunTime.Unit.MILLI))); // 9
                    bw.write(String.format("%11.5f ", results.get(Result.SolveMethod.FPTAS64).getTime(Result.RunTime.Unit.MILLI))); // 10
                    
                    // zrychleni
                    bw.write(String.format("%11.5f ", results.get(Result.SolveMethod.BRUTE_FORCE).getTime(Result.RunTime.Unit.MILLI) // 11
                                                    / results.get(Result.SolveMethod.HEURISTIC).getTime(Result.RunTime.Unit.MILLI)));
                    
                    bw.write(String.format("%11.5f ", results.get(Result.SolveMethod.BRUTE_FORCE).getTime(Result.RunTime.Unit.MILLI) // 12
                                                    / results.get(Result.SolveMethod.DYNAMIC).getTime(Result.RunTime.Unit.MILLI)));
                    
                    bw.write(String.format("%11.5f ", results.get(Result.SolveMethod.BRUTE_FORCE).getTime(Result.RunTime.Unit.MILLI) // 13
                                                    / results.get(Result.SolveMethod.BRANCH_AND_BOUND).getTime(Result.RunTime.Unit.MILLI)));
                    
                    bw.write(String.format("%11.5f ", results.get(Result.SolveMethod.BRUTE_FORCE).getTime(Result.RunTime.Unit.MILLI) // 14
                                                    / results.get(Result.SolveMethod.FPTAS2).getTime(Result.RunTime.Unit.MILLI)));
                    
                    bw.write(String.format("%11.5f ", results.get(Result.SolveMethod.BRUTE_FORCE).getTime(Result.RunTime.Unit.MILLI) // 15
                                                    / results.get(Result.SolveMethod.FPTAS4).getTime(Result.RunTime.Unit.MILLI)));
                    
                    bw.write(String.format("%11.5f ", results.get(Result.SolveMethod.BRUTE_FORCE).getTime(Result.RunTime.Unit.MILLI) // 16
                                                    / results.get(Result.SolveMethod.FPTAS8).getTime(Result.RunTime.Unit.MILLI)));
                    
                    bw.write(String.format("%11.5f ", results.get(Result.SolveMethod.BRUTE_FORCE).getTime(Result.RunTime.Unit.MILLI) // 17
                                                    / results.get(Result.SolveMethod.FPTAS16).getTime(Result.RunTime.Unit.MILLI)));
                    
                    bw.write(String.format("%11.5f ", results.get(Result.SolveMethod.BRUTE_FORCE).getTime(Result.RunTime.Unit.MILLI) // 18
                                                    / results.get(Result.SolveMethod.FPTAS32).getTime(Result.RunTime.Unit.MILLI)));
                    
                    bw.write(String.format("%11.5f ", results.get(Result.SolveMethod.BRUTE_FORCE).getTime(Result.RunTime.Unit.MILLI) // 19
                                                    / results.get(Result.SolveMethod.FPTAS64).getTime(Result.RunTime.Unit.MILLI)));
                    
                    // nepresnost
                    bw.write(String.format("%11.5f ", (double)(results.get(Result.SolveMethod.BRUTE_FORCE).getCenaReseni() // 20
                                                    - results.get(Result.SolveMethod.HEURISTIC).getCenaReseni())
                                                        / (double)results.get(Result.SolveMethod.BRUTE_FORCE).getCenaReseni()));
                    
                    bw.write(String.format("%11.5f ", (double)(results.get(Result.SolveMethod.BRUTE_FORCE).getCenaReseni() // 21
                                                    - results.get(Result.SolveMethod.FPTAS2).getCenaReseni())
                                                        / (double)results.get(Result.SolveMethod.BRUTE_FORCE).getCenaReseni()));
                    
                    bw.write(String.format("%11.5f ", (double)(results.get(Result.SolveMethod.BRUTE_FORCE).getCenaReseni() // 22
                                                    - results.get(Result.SolveMethod.FPTAS4).getCenaReseni())
                                                        / (double)results.get(Result.SolveMethod.BRUTE_FORCE).getCenaReseni()));
                    
                    bw.write(String.format("%11.5f ", (double)(results.get(Result.SolveMethod.BRUTE_FORCE).getCenaReseni() // 23
                                                    - results.get(Result.SolveMethod.FPTAS8).getCenaReseni())
                                                        / (double)results.get(Result.SolveMethod.BRUTE_FORCE).getCenaReseni()));
                    
                    bw.write(String.format("%11.5f", (double)(results.get(Result.SolveMethod.BRUTE_FORCE).getCenaReseni() // 24
                                                    - results.get(Result.SolveMethod.FPTAS16).getCenaReseni())
                                                        / (double)results.get(Result.SolveMethod.BRUTE_FORCE).getCenaReseni()));
                    
                    bw.write(String.format("%11.5f", (double)(results.get(Result.SolveMethod.BRUTE_FORCE).getCenaReseni() // 25
                                                    - results.get(Result.SolveMethod.FPTAS32).getCenaReseni())
                                                        / (double)results.get(Result.SolveMethod.BRUTE_FORCE).getCenaReseni()));
                    
                    bw.write(String.format("%11.5f ", (double)(results.get(Result.SolveMethod.BRUTE_FORCE).getCenaReseni() // 26
                                                    - results.get(Result.SolveMethod.FPTAS64).getCenaReseni())
                                                        / (double)results.get(Result.SolveMethod.BRUTE_FORCE).getCenaReseni()));
                    
                    // predpokladana chyba
                    bw.write(String.format("%11.5f ", ((results.get(Result.SolveMethod.FPTAS2).getPocetVeci() * Math.pow(2, 1)) / getMaxValue(instance.getCeny())))); // 27
                    bw.write(String.format("%11.5f ", ((results.get(Result.SolveMethod.FPTAS4).getPocetVeci() * Math.pow(2, 2)) / getMaxValue(instance.getCeny())))); // 28
                    bw.write(String.format("%11.5f ", ((results.get(Result.SolveMethod.FPTAS8).getPocetVeci() * Math.pow(2, 3)) / getMaxValue(instance.getCeny())))); // 29
                    bw.write(String.format("%11.5f ", ((results.get(Result.SolveMethod.FPTAS16).getPocetVeci() * Math.pow(2, 4)) / getMaxValue(instance.getCeny())))); // 30
                    bw.write(String.format("%11.5f ", ((results.get(Result.SolveMethod.FPTAS32).getPocetVeci() * Math.pow(2, 5)) / getMaxValue(instance.getCeny())))); // 31
                    bw.write(String.format("%11.5f ", ((results.get(Result.SolveMethod.FPTAS64).getPocetVeci() * Math.pow(2, 6)) / getMaxValue(instance.getCeny())))); // 32
                    
                    // pocet navstivenych stavu
                    bw.write(String.format("%d ", results.get(Result.SolveMethod.HEURISTIC).navstivenychStavu)); // 33
                    bw.write(String.format("%d ", results.get(Result.SolveMethod.BRUTE_FORCE).navstivenychStavu)); // 34
                    bw.write(String.format("%d ", results.get(Result.SolveMethod.DYNAMIC).navstivenychStavu)); // 35
                    bw.write(String.format("%d ", results.get(Result.SolveMethod.BRANCH_AND_BOUND).navstivenychStavu)); // 36
                    bw.write(String.format("%d ", results.get(Result.SolveMethod.FPTAS2).navstivenychStavu)); // 37
                    bw.write(String.format("%d ", results.get(Result.SolveMethod.FPTAS4).navstivenychStavu)); // 38
                    bw.write(String.format("%d ", results.get(Result.SolveMethod.FPTAS8).navstivenychStavu)); // 39
                    bw.write(String.format("%d ", results.get(Result.SolveMethod.FPTAS16).navstivenychStavu)); // 40
                    bw.write(String.format("%d ", results.get(Result.SolveMethod.FPTAS32).navstivenychStavu)); // 41
                    bw.write(String.format("%d %n", results.get(Result.SolveMethod.FPTAS64).navstivenychStavu)); // 42

                }
            } catch (IOException ex) {
                System.out.println("Can create file writer");
            }
        }        
    }
 
public static int getMaxValue(int[] array){  
    int maxValue = array[0];  
    for(int i=1;i < array.length;i++){  
      if(array[i] > maxValue){  
          maxValue = array[i];  
      }  
  }  
  return maxValue;  
} 
    
}

//cat ukol1/result.dat | sed -n 's/ \+/ /gp' | cut -d " " -f 2
// ./knap -n 20 -N 50 -m 1 -W 100 -C 250 -k 1 -d 1 > out ; java -jar ../prvni_ukol/ukol1/dist/ukol1.jar -file ../knapgen/out ; cat ../knapgen//result.dat | sed -n 's/ \+/ /gp' | cut -d " " -f 26
