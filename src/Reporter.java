
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Reporter {

    public static enum reportDetail {DEVELOP,FILE};
    
    ArrayList<Result> results;
    
    public Reporter(ArrayList<Result> results){
        this.results = results;
    }
    
    public void print(Reporter.reportDetail detail){
        if(detail == reportDetail.DEVELOP){
            System.out.print(results.get(0).getId() + ">");
            for (Result res : results){
                System.out.print(" " +res.getName().getName() + ": ");
                System.out.print(res.cenaReseni);
                System.out.print(" (" + res.time.getTime(Result.RunTime.Unit.MILLI) + ") ");
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
//                    bw.write(String.format("%11.5f ", .getTime(Result.RunTime.Unit.MILLI)));
//                    bw.write(String.format("%11.5f ", time.getBruteForceTime(RunTime.Unit.MILLI)));
//                    bw.write(String.format("%11.5f ", time.getBruteForceTime(RunTime.Unit.MILLI)/time.getHeuristicTime(RunTime.Unit.MILLI)));
//                    bw.write(String.format("%11.5f %n", (double)(bruteForceResult.getCenaReseni()-heuristicResult.getCenaReseni())/(double)bruteForceResult.getCenaReseni()));
                }
            } catch (IOException ex) {
                System.out.println("Can create file writer");
            }
        }
    }
    
    private Result getResult(Result.SolveMethod method){
        for (Result res : results){
            if(res.getName() == method){
                return res;
            }
        }
        throw new RuntimeException("no result for method" + method);
    }
}

//cat ukol1/result.dat | sed -n 's/ \+/ /gp' | cut -d " " -f 2
