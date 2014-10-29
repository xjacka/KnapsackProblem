
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class Reporter {

    public static enum reportDetail {DEVELOP, FILE, FULL};
    
    HashMap<Result.SolveMethod,Result> results;
    
    public Reporter(HashMap<Result.SolveMethod,Result> results){
        this.results = results;
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
            System.out.print(results.get(Result.SolveMethod.REFERENCE).getCenaReseni() + " ");
            System.out.print(results.get(Result.SolveMethod.FPTAS2).getCenaReseni() + " ");
            System.out.print(results.get(Result.SolveMethod.FPTAS4).getCenaReseni() + " ");
            System.out.print(results.get(Result.SolveMethod.FPTAS8).getCenaReseni() + " ");
            System.out.print(results.get(Result.SolveMethod.FPTAS16).getCenaReseni() + " ");
            System.out.print(results.get(Result.SolveMethod.FPTAS32).getCenaReseni() + " ");
            System.out.println(results.get(Result.SolveMethod.FPTAS64).getCenaReseni());
        }
        if(detail == Reporter.reportDetail.FILE){
            System.out.print(".");
            File resultFile = new File("result1.dat");
            
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
                    bw.write(String.format("%11.5f ", results.get(Result.SolveMethod.HEURISTIC).getTime(Result.RunTime.Unit.MILLI)));
                    bw.write(String.format("%11.5f ", results.get(Result.SolveMethod.BRUTE_FORCE).getTime(Result.RunTime.Unit.MILLI)));
                    bw.write(String.format("%11.5f ", results.get(Result.SolveMethod.DYNAMIC).getTime(Result.RunTime.Unit.MILLI)));
                    bw.write(String.format("%11.5f ", results.get(Result.SolveMethod.BRANCH_AND_BOUND).getTime(Result.RunTime.Unit.MILLI)));
                    bw.write(String.format("%11.5f ", results.get(Result.SolveMethod.FPTAS2).getTime(Result.RunTime.Unit.MILLI)));
                    bw.write(String.format("%11.5f ", results.get(Result.SolveMethod.FPTAS4).getTime(Result.RunTime.Unit.MILLI)));
                    bw.write(String.format("%11.5f ", results.get(Result.SolveMethod.FPTAS8).getTime(Result.RunTime.Unit.MILLI)));
                    bw.write(String.format("%11.5f ", results.get(Result.SolveMethod.FPTAS16).getTime(Result.RunTime.Unit.MILLI)));
                    bw.write(String.format("%11.5f ", results.get(Result.SolveMethod.FPTAS32).getTime(Result.RunTime.Unit.MILLI)));
                    bw.write(String.format("%11.5f ", results.get(Result.SolveMethod.FPTAS64).getTime(Result.RunTime.Unit.MILLI)));
                    
                    // zrychleni
                    bw.write(String.format("%11.5f ", results.get(Result.SolveMethod.BRUTE_FORCE).getTime(Result.RunTime.Unit.MILLI)
                                                    / results.get(Result.SolveMethod.HEURISTIC).getTime(Result.RunTime.Unit.MILLI)));
                    
                    bw.write(String.format("%11.5f ", results.get(Result.SolveMethod.BRUTE_FORCE).getTime(Result.RunTime.Unit.MILLI)
                                                    / results.get(Result.SolveMethod.DYNAMIC).getTime(Result.RunTime.Unit.MILLI)));
                    
                    bw.write(String.format("%11.5f ", results.get(Result.SolveMethod.BRUTE_FORCE).getTime(Result.RunTime.Unit.MILLI)
                                                    / results.get(Result.SolveMethod.BRANCH_AND_BOUND).getTime(Result.RunTime.Unit.MILLI)));
                    
                    bw.write(String.format("%11.5f ", results.get(Result.SolveMethod.BRUTE_FORCE).getTime(Result.RunTime.Unit.MILLI)
                                                    / results.get(Result.SolveMethod.FPTAS2).getTime(Result.RunTime.Unit.MILLI)));
                    
                    bw.write(String.format("%11.5f ", results.get(Result.SolveMethod.BRUTE_FORCE).getTime(Result.RunTime.Unit.MILLI)
                                                    / results.get(Result.SolveMethod.FPTAS4).getTime(Result.RunTime.Unit.MILLI)));
                    
                    bw.write(String.format("%11.5f ", results.get(Result.SolveMethod.BRUTE_FORCE).getTime(Result.RunTime.Unit.MILLI)
                                                    / results.get(Result.SolveMethod.FPTAS8).getTime(Result.RunTime.Unit.MILLI)));
                    
                    bw.write(String.format("%11.5f ", results.get(Result.SolveMethod.BRUTE_FORCE).getTime(Result.RunTime.Unit.MILLI)
                                                    / results.get(Result.SolveMethod.FPTAS16).getTime(Result.RunTime.Unit.MILLI)));
                    
                    bw.write(String.format("%11.5f ", results.get(Result.SolveMethod.BRUTE_FORCE).getTime(Result.RunTime.Unit.MILLI)
                                                    / results.get(Result.SolveMethod.FPTAS32).getTime(Result.RunTime.Unit.MILLI)));
                    
                    bw.write(String.format("%11.5f ", results.get(Result.SolveMethod.BRUTE_FORCE).getTime(Result.RunTime.Unit.MILLI)
                                                    / results.get(Result.SolveMethod.FPTAS64).getTime(Result.RunTime.Unit.MILLI)));
                    
                    // nepresnost
                    bw.write(String.format("%11.5f ", (double)(results.get(Result.SolveMethod.REFERENCE).getCenaReseni()
                                                    - results.get(Result.SolveMethod.HEURISTIC).getCenaReseni())
                                                        / (double)results.get(Result.SolveMethod.REFERENCE).getCenaReseni()));
                    
                    bw.write(String.format("%11.5f ", (double)(results.get(Result.SolveMethod.REFERENCE).getCenaReseni()
                                                    - results.get(Result.SolveMethod.FPTAS2).getCenaReseni())
                                                        / (double)results.get(Result.SolveMethod.REFERENCE).getCenaReseni()));
                    
                    bw.write(String.format("%11.5f ", (double)(results.get(Result.SolveMethod.REFERENCE).getCenaReseni()
                                                    - results.get(Result.SolveMethod.FPTAS4).getCenaReseni())
                                                        / (double)results.get(Result.SolveMethod.REFERENCE).getCenaReseni()));
                    
                    bw.write(String.format("%11.5f ", (double)(results.get(Result.SolveMethod.REFERENCE).getCenaReseni()
                                                    - results.get(Result.SolveMethod.FPTAS8).getCenaReseni())
                                                        / (double)results.get(Result.SolveMethod.REFERENCE).getCenaReseni()));
                    
                    bw.write(String.format("%11.5f %n", (double)(results.get(Result.SolveMethod.REFERENCE).getCenaReseni()
                                                    - results.get(Result.SolveMethod.FPTAS16).getCenaReseni())
                                                        / (double)results.get(Result.SolveMethod.REFERENCE).getCenaReseni()));
                    
                    bw.write(String.format("%11.5f %n", (double)(results.get(Result.SolveMethod.REFERENCE).getCenaReseni()
                                                    - results.get(Result.SolveMethod.FPTAS32).getCenaReseni())
                                                        / (double)results.get(Result.SolveMethod.REFERENCE).getCenaReseni()));
                    
                    bw.write(String.format("%11.5f %n", (double)(results.get(Result.SolveMethod.REFERENCE).getCenaReseni()
                                                    - results.get(Result.SolveMethod.FPTAS64).getCenaReseni())
                                                        / (double)results.get(Result.SolveMethod.REFERENCE).getCenaReseni()));
                }
            } catch (IOException ex) {
                System.out.println("Can create file writer");
            }
        }
    }
    
}

//cat ukol1/result.dat | sed -n 's/ \+/ /gp' | cut -d " " -f 2
