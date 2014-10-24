
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class Reporter {

    public static enum reportDetail {DEVELOP,FILE};
    
    HashMap<Result.SolveMethod,Result> results;
    
    public Reporter(HashMap<Result.SolveMethod,Result> results){
        this.results = results;
    }
    
    public void print(Reporter.reportDetail detail){
        if(detail == reportDetail.DEVELOP){
            System.out.print(results.get(Result.SolveMethod.REFERENCE) .getId() + ">");
            for (Result res : results.values()){
                System.out.print(" " +res.getName().getName() + ": ");
                System.out.print(res.cenaReseni);
                System.out.print(" (" + res.getTime(Result.RunTime.Unit.MILLI) + ") ");
            }
            System.out.println("");
        }
        if(detail == Reporter.reportDetail.FILE){
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
                    bw.write(String.format("%11.5f ", results.get(Result.SolveMethod.HEURISTIC).getTime(Result.RunTime.Unit.MILLI)));
//                    bw.write(String.format("%11.5f ", time.getBruteForceTime(RunTime.Unit.MILLI)));
//                    bw.write(String.format("%11.5f ", time.getBruteForceTime(RunTime.Unit.MILLI)/time.getHeuristicTime(RunTime.Unit.MILLI)));
//                    bw.write(String.format("%11.5f %n", (double)(bruteForceResult.getCenaReseni()-heuristicResult.getCenaReseni())/(double)bruteForceResult.getCenaReseni()));
                }
            } catch (IOException ex) {
                System.out.println("Can create file writer");
            }
        }
    }
    
}

//cat ukol1/result.dat | sed -n 's/ \+/ /gp' | cut -d " " -f 2
