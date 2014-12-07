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
                System.out.println("\n-------"+files[i]+"-------"); 
                instances = parser.parseInput(new File(args[1] + "knap_"+files[i]+".inst.dat"));
                results = parser.parseOutput(new File(args[1] + "knap_"+files[i]+".sol.dat"));
                solve(instances,results);
            }
        }else if("-file".equals(args[0])){
            instances = parser.parseInput(new File(args[1]));
            solve(instances,null);
        }
        else{
            instances = parser.parseInput(new File(args[0] + "knap_"+args[1]+".inst.dat"));
            results = parser.parseOutput(new File(args[0] + "knap_"+args[1]+".sol.dat"));
            solve(instances,results);
        }
        
    }
    
    private static void solve(ArrayList<ProgramInstance> instances,ArrayList<Result> results){        
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
            Result bruteForceResult = bagSolver.solveDynamicProgramming(instance);//bagSolver.solveBruteForce(instance);  
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
            
            // FPTAS 1bit
            t1 = getCpuTime();
            Result FPTASResult1 = bagSolver.solveFPTAS(instance, 2, Result.SolveMethod.FPTAS2);
            t2 = getCpuTime();
            FPTASResult1.time = new Result.RunTime(t1, t2);
            ResInstances.put(Result.SolveMethod.FPTAS2,FPTASResult1);           
            
            // FPTAS 2bity
            t1 = getCpuTime();
            Result FPTASResult2 = bagSolver.solveFPTAS(instance, 4, Result.SolveMethod.FPTAS4);
            t2 = getCpuTime();
            FPTASResult2.time = new Result.RunTime(t1, t2);
            ResInstances.put(Result.SolveMethod.FPTAS4,FPTASResult2);      
            
            // FPTAS 3bity
            t1 = getCpuTime();
            Result FPTASResult3 = bagSolver.solveFPTAS(instance, 8, Result.SolveMethod.FPTAS8);
            t2 = getCpuTime();
            FPTASResult3.time = new Result.RunTime(t1, t2);
            ResInstances.put(Result.SolveMethod.FPTAS8,FPTASResult3);      
            
            // FPTAS 4bity
            t1 = getCpuTime();
            Result FPTASResult4 = bagSolver.solveFPTAS(instance, 16, Result.SolveMethod.FPTAS16);
            t2 = getCpuTime();
            FPTASResult4.time = new Result.RunTime(t1, t2);
            ResInstances.put(Result.SolveMethod.FPTAS16,FPTASResult4); 
            
            // FPTAS 5bity
            t1 = getCpuTime();
            Result FPTASResult5 = bagSolver.solveFPTAS(instance, 32, Result.SolveMethod.FPTAS32);
            t2 = getCpuTime();
            FPTASResult5.time = new Result.RunTime(t1, t2);
            ResInstances.put(Result.SolveMethod.FPTAS32,FPTASResult5); 
            
            // FPTAS 6bity
            t1 = getCpuTime();
            Result FPTASResult6 = bagSolver.solveFPTAS(instance, 64, Result.SolveMethod.FPTAS64);
            t2 = getCpuTime();
            FPTASResult6.time = new Result.RunTime(t1, t2);
            ResInstances.put(Result.SolveMethod.FPTAS64,FPTASResult6);
            
            // Simulované ochlazování
            t1 = getCpuTime();
            Result simulatedAnnealinResult = bagSolver.solveSimulatedAnnealin(instance);
            t2 = getCpuTime();
            simulatedAnnealinResult.time = new Result.RunTime(t1, t2);
            ResInstances.put(Result.SolveMethod.SIMULATED_ANNEALIN,simulatedAnnealinResult);
            
            // Generický algoritmus
            t1 = getCpuTime();
            Result genericResult = bagSolver.solveGeneric(instance);
            t2 = getCpuTime();
            genericResult.time = new Result.RunTime(t1, t2);
            ResInstances.put(Result.SolveMethod.GENETIC,genericResult);
            
            // reference
            Result result = null;
            if(results != null){
                for(Result res : results){
                    if(res.getId() == instance.getId()){
                        result = res;
                        res.time = new Result.RunTime(t1, t2);
                        ResInstances.put(Result.SolveMethod.REFERENCE,res);
                        break;
                    }
                } 
            }else{
                ResInstances.put(Result.SolveMethod.REFERENCE,bruteForceResult);
            }
                        
            Reporter reporter = new Reporter(ResInstances,instance);
            reporter.print(Reporter.reportDetail.DEVELOP);
        }
    }
    
    public static long getCpuTime( ) {
    ThreadMXBean bean = ManagementFactory.getThreadMXBean( );
    return bean.isCurrentThreadCpuTimeSupported( ) ?
        bean.getCurrentThreadCpuTime( ) : 0L;
}
       
}                  