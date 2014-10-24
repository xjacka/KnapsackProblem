import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.HashMap;

public class Ukol1 {

    private static final Parser parser = new Parser();
    private static final BagSolver bagSolver = new BagSolver();  
        
    public static void main(String[] args) {     
        
        File fileTemp = new File("result.dat");
        if (fileTemp.exists()){
           fileTemp.delete();
        }  
          
        ArrayList<ProgramInstance> instances;
        ArrayList<Result> results;        
        
        if("-to".equals(args[0])){
            String[] files = {"4","10","15","20","22","25","27","30","32","35","37","40"};
            for(int i = 0;Integer.parseInt(args[2]) >= Integer.parseInt(files[i]);i++){
                System.out.println("-------"+files[i]+"-------"); 
                instances = parser.parseInput(new File(args[1] + "knap_"+files[i]+".inst.dat"));
                results = parser.parseOutput(new File(args[1] + "knap_"+files[i]+".sol.dat"));
                solve(instances,results);
            }
        }else{
            instances = parser.parseInput(new File(args[0] + "knap_"+args[1]+".inst.dat"));
            results = parser.parseOutput(new File(args[0] + "knap_"+args[1]+".sol.dat"));
            solve(instances,results);
        }
        
    }
    
    private static void solve(ArrayList<ProgramInstance> instances,ArrayList<Result> results){        
//        ProgramInstance instance = instances.get(3);
        for(ProgramInstance instance : instances){   
            HashMap<Result.SolveMethod,Result> ResInstances = new HashMap<>();
            Long t1, t2;
            
            // heuristic
            t1 = getCpuTime();            
            Result heuristicResult = bagSolver.solveHeuristic(instance);       
            t2 = getCpuTime();
            heuristicResult.time = new Result.RunTime(t1, t2);
            ResInstances.put(Result.SolveMethod.HEURISTIC,heuristicResult);
            
            // brute force
            t1 = getCpuTime();
            Result bruteForceResult = bagSolver.solveBruteForce(instance);  
            t2 = getCpuTime();
            bruteForceResult.time = new Result.RunTime(t1, t2);
            ResInstances.put(Result.SolveMethod.BRUTE_FORCE,bruteForceResult);
                        
            // branch & bounds
            t1 = getCpuTime();
            Result branchAndBounds = bagSolver.solveBranchAndBound(instance);  
            t2 = getCpuTime();
            branchAndBounds.time = new Result.RunTime(t1, t2);
            ResInstances.put(Result.SolveMethod.BRANCH_AND_BOUND,branchAndBounds);
            
            // dynamic programming
            t1 = getCpuTime();
            Result dynamicProgrammingResult = bagSolver.solveDynamicProgramming(instance);
            t2 = getCpuTime();
            dynamicProgrammingResult.time = new Result.RunTime(t1, t2);
            ResInstances.put(Result.SolveMethod.DYNAMIC,dynamicProgrammingResult);
            
            // FPTAS
            t1 = getCpuTime();
            Result FPTASResult = bagSolver.solveFPTAS(instance, 4);
            t2 = getCpuTime();
            FPTASResult.time = new Result.RunTime(t1, t2);
            ResInstances.put(Result.SolveMethod.FPTAS,FPTASResult);           
            
            // reference
            Result result = null;
            for(Result res : results){
                if(res.getId() == instance.getId()){
                    result = res;
                    res.time = new Result.RunTime(t1, t2);
                    ResInstances.put(Result.SolveMethod.REFERENCE,res);
                    break;
                }
            }            
                        
            Reporter reporter = new Reporter(ResInstances);
            reporter.print(Reporter.reportDetail.DEVELOP);
        }
    }
    
    public static long getCpuTime( ) {
    ThreadMXBean bean = ManagementFactory.getThreadMXBean( );
    return bean.isCurrentThreadCpuTimeSupported( ) ?
        bean.getCurrentThreadCpuTime( ) : 0L;
}
       
}                  