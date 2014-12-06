
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;
import java.util.TreeMap;

public class BagSolver {
    
    public Result solveHeuristic(ProgramInstance programInstance){
        Result result = new Result(programInstance,Result.SolveMethod.HEURISTIC);  
        result.navstivenychStavu = result.pocetVeci;
        
        ArrayList<Ratio> pomer = new ArrayList<>();
        
        for(int i = 0; i < programInstance.getPocetVeci(); i++){
            pomer.add(new Ratio(programInstance.getCeny()[i],programInstance.getVahy()[i],i));
        }
       
        Collections.sort(pomer);
               
        for(Ratio pom : pomer){
            if((result.getVahaVeci() + pom.vaha) <= programInstance.getKapacitaBatohu()){
                result.addRatio(pom);
            }
        }
        
        return result;
    }
    
    public Result solveBruteForce(ProgramInstance programInstance){
        Result result = new Result(programInstance,Result.SolveMethod.BRUTE_FORCE);
        
        ArrayList<Ratio> pomer = new ArrayList<>();
        
        for(int i = 0; i < programInstance.getPocetVeci(); i++){
            pomer.add(new Ratio(programInstance.getCeny()[i],programInstance.getVahy()[i],i));
        }
        
        int mocnina = (int)Math.pow(2,programInstance.getPocetVeci());
        result.navstivenychStavu = mocnina;
        
        for(long i = 0;i < mocnina;i++){
            Result pomocnyResult = new Result(programInstance,Result.SolveMethod.BRUTE_FORCE);
            pomocnyResult.navstivenychStavu = mocnina;

            long delenec = i;
            for(int j = 0; j < programInstance.getPocetVeci() && delenec > 0;j++){                
                if(delenec % 2 == 1){         
                    Ratio r = pomer.get(j);
                    pomocnyResult.addRatio(r);
                }   
                
                delenec /= 2;                
            }
            
            if(pomocnyResult.getVahaVeci() <= programInstance.getKapacitaBatohu() && pomocnyResult.getCenaReseni() > result.getCenaReseni()){
                result = pomocnyResult;
            }
        } 
        return result;
    }
    
    class Row {
        
        int position;
        int [] vector;
        int price;
        int maxPrice;
        int weight;
        
        public Row(int n, int val, int [] c, int [] v){
            vector = new int[n];
            position = 0;            
            vector[position] = val;
            price = 0;
            maxPrice = 0;
            weight = 0;
            int i = 0;
            if(val == 1){
                price += c[i];
                weight += v[i];
            }
            for(int ve : vector){
                if(ve == 1 || i > position){
                    maxPrice += c[i];
                } 
                i++;
            }            
        }
        
        public Row(int n, int val, Row row, int [] c, int [] v){
            vector = new int[n];
            System.arraycopy( row.vector, 0, vector, 0, row.vector.length );
            position = row.position + 1;
            vector[position] = val;
            if(val == 1){
                price = row.price + c[position];
                weight = row.weight + v[position];
                maxPrice = row.maxPrice;
            }else{
                maxPrice = row.maxPrice - c[position];
                price = row.price;
                weight = row.weight;
            }
        }
    }
    
    public Result solveBranchAndBound(ProgramInstance programInstance){
        Result result = new Result(programInstance,Result.SolveMethod.BRANCH_AND_BOUND);
        Stack<Row> stack = new Stack<>();
        int bestPrice = 0;       
        
        int n = programInstance.getPocetVeci();
        int m = programInstance.getKapacitaBatohu();
        int[] v = programInstance.getVahy();
        int[] c = programInstance.getCeny();
        
        stack.push(new Row(n, 0, c, v));
        stack.push(new Row(n, 1, c, v));
                
        while(!stack.empty()){
            Row r = stack.pop();
            result.navstivenychStavu++;
             
            if(r.price > bestPrice && r.weight <= m){
                bestPrice = r.price;
                result.setCenaReseni(r.price);
                result.setVahaVeci(r.weight);
                result.setReseni(r.vector);
            }
            if(r.position < n - 1){
                Row r0, r1;
                r0 = new Row(n, 0, r, c, v);
                r1 = new Row(n, 1, r, c, v);
                
                if(r0.maxPrice > bestPrice && r0.weight <= m){
                    stack.push(r0);
                }
                if(r1.maxPrice > bestPrice && r0.weight <= m){
                    stack.push(r1);
                }
            }            
        }
        
         return result;
    }
    
    public Result solveDynamicProgramming(ProgramInstance programInstance){
        Result result = new Result(programInstance,Result.SolveMethod.DYNAMIC);
        
        int n = programInstance.getPocetVeci();
        int m = programInstance.getKapacitaBatohu();
        int[] v = programInstance.getVahy();
        int[] c = programInstance.getCeny();
        int sum = 0;
        for (int i : c)
            sum += i;
        
        int [][]w = new int[sum + 1][n + 1];    
        
        for(int i = 0; i <= sum; i++){
            for(int j = 0 ; j <= n; j++){
                if(i == 0){
                    w[i][j] = 0;
                }
                else if(j == 0){
                    w[i][j] = Integer.MAX_VALUE;
                }
                else{
                    if(i - c[j-1] <= sum && i - c[j-1] >= 0){
                        int x = w[i][j-1];
                        int y = w[i - c[j-1]][j-1] + v[j-1];
                        
                        if (y < 0){
                            y = Integer.MAX_VALUE;
                        }
                        w[i][j] = Math.min(x, y);                        
                    }
                    else{           
                        w[i][j] = w[i][j-1];
                    }
                }
            }
        }
        result.navstivenychStavu = sum * n;
        
        int sloupec = n;
        boolean start = false;
        for(int i = sum; i >= 0 && sloupec > 0; i--){
            if(w[i][sloupec] <= m && sloupec == n){               
                result.setCenaReseni(i);   
                start = true;
            }
            if(start){
                int pridano = w[i][sloupec-1]!=w[i][sloupec]?1:0;
                result.addReseni(sloupec-1, pridano);
                if(pridano == 1)
                    i = i - c[sloupec-1]+1;
                else
                    i++;
                sloupec--;                
            }                        
        }        
       
        return result;
    }
    
    public Result solveFPTAS(ProgramInstance programInstance,int bits, Result.SolveMethod method){
        
        // kopírování počáteční instance
        ProgramInstance modifiedInstance = new ProgramInstance(programInstance.getPocetVeci());
        modifiedInstance.setId(programInstance.getId());
        modifiedInstance.setKapacitaBatohu(programInstance.getKapacitaBatohu());
        modifiedInstance.setPocetVeci(programInstance.pocetVeci);        
        for(int i = 0; i < programInstance.getPocetVeci(); i++){
            modifiedInstance.addCena(i, (programInstance.getCeny()[i] / bits) + 1);
            modifiedInstance.addVaha(i, programInstance.getVahy()[i]);
        }
        
        // samotné řešení pomocí dynamického programování
        Result result = solveDynamicProgramming(modifiedInstance); 
        
        // nastavení výsledku
        result.setName(method);        
        int sum = 0;
        for(int i = 0; i < programInstance.getPocetVeci(); i++){
            if(result.reseni[i] == 1){
                sum += programInstance.getCeny()[i];
            }
        }        
        result.setCenaReseni(sum);
        
        return result;
    }
    
    public Result solveSimulatedAnnealin(ProgramInstance programInstance){
        Result result = new Result(programInstance,Result.SolveMethod.SIMULATED_ANNEALIN);
        result.navstivenychStavu = 0;
          
        // parametry alg.
        double coolingCoeficient = 0.95; // koefifient ochlazení
        int innerLoop = programInstance.getPocetVeci() * 100; // počet opakování vnitřní smyčky
        double temp = 0.5; // počáteční teplota
        double minTemp = 0.001; // minimální teplota

        Result bestResult = result;
        
        while(temp > minTemp) {   
            for(int i = 0;i < innerLoop;i++){
                result = getNewState(result, new Result(result), temp, programInstance);
                if(result.getCenaReseni() > bestResult.getCenaReseni()) {
                    result.navstivenychStavu = bestResult.navstivenychStavu;
                    bestResult = result;
                }
                bestResult.navstivenychStavu++;                                
            }            
            temp = temp * coolingCoeficient;
        }
        return bestResult;
    }
    
    private Result getNewState(Result oldResult, Result newResult, double temp, ProgramInstance instance) {
        
        // změna prvku v novém stavu        
        do{
            int index = new Random().nextInt(instance.getPocetVeci());
            newResult.reseni[index] = (newResult.reseni[index] + 1) % 2;
            newResult.updateVelues(instance);
        } while (newResult.vahaVeci > instance.getKapacitaBatohu());
        
        double x = new Random().nextInt(oldResult.getPocetVeci())/oldResult.getPocetVeci();
        double delta = newResult.getCenaReseni() - oldResult.getCenaReseni();
        if(delta > 0 || x < Math.exp(-delta/temp)){
            return newResult;
        }
        else {             
            return oldResult;
        }
    }
    
    public Result solveGeneric(ProgramInstance programInstance){       
        // parametry
        int populationSize = 80;
        int generationsCount = 150;
        int pocetKrizeni = generationsCount / 10;
        float mutationProbability = 0.6f;
        int selectionSize = 10;
        int penalization = 5;
        
        ArrayList<Result> population = new ArrayList<>(populationSize);        
        
        // počáteční populace
        for(int i = 0; i < populationSize ; i++){
            Result result = new Result(programInstance,Result.SolveMethod.GENETIC);
            result.navstivenychStavu = 0; 
            if(i%20 == 0){
                for(int j = 0; j < programInstance.getPocetVeci(); j++){                
                    if(new Random().nextInt(result.getPocetVeci()) < (result.getPocetVeci()/4)){
                        if((result.vahaVeci + programInstance.getVahy()[j]) <= programInstance.kapacitaBatohu){
                            result.reseni[j] = 1;            
                            result.cenaReseni += programInstance.getCeny()[j];
                            result.vahaVeci += programInstance.getVahy()[j];
                        }
                    }
                }   
            }else {
                result = solveHeuristic(programInstance);
            }
            population.add(result);
        }
        
        // určení nové generace
        for(int i = 0; i < generationsCount; i++){
            
            // selekce
            ArrayList<Result> newPopulation = new ArrayList<>(populationSize);
            for(int j = 0; j < populationSize ; j++){
                TreeMap<Integer, Result> selection = new TreeMap<>();
                ArrayList<Integer> randoms = new ArrayList<>(selectionSize);
                for(int k = 0; k < selectionSize ; k++){
                    int random;
                    do{
                        random = new Random().nextInt(populationSize);
                    } while(randoms.contains(random));
                    randoms.add(random);
                    Result r = population.get(random);
                    // fitness funkce
                    int rank = r.vahaVeci > programInstance.kapacitaBatohu ? 
                        r.cenaReseni - (r.vahaVeci - programInstance.kapacitaBatohu) * penalization : 
                        r.cenaReseni;
                    selection.put(rank, r);
                }
                newPopulation.add(new Result(selection.get(selection.lastKey())));
            }
            population = newPopulation;
               
            // křížení
            for(int j = 0; j < pocetKrizeni ; j++){
                Result c1 = population.get(new Random().nextInt(populationSize));
                Result c2 = population.get(new Random().nextInt(populationSize));
                int point = new Random().nextInt(programInstance.pocetVeci) / 4;
                for(int k = 0 ; k < point ; k++){
                    int store = c1.reseni[k];
                    c1.reseni[k] = c2.reseni[k];
                    c2.reseni[k] = store;
                }
                c1.updateVelues(programInstance);
                c2.updateVelues(programInstance);
            }
            
            // mutace
            for(Result c1 : population){
                int mutate = new Random().nextInt(100);
                if(mutate < (mutationProbability * 100)){
                    int point = new Random().nextInt(programInstance.pocetVeci);
                    c1.reseni[point] = (c1.reseni[point] + 1) % 2;
                    c1.updateVelues(programInstance);
                }
            }            
        
        }
        
        // vybrání nejlepšího reprezentanta z celé generace
        Result best = null;
        for(Result res : population){
            if(best == null){
                if(res.vahaVeci <= programInstance.kapacitaBatohu){
                    best = res;
                }
                continue;
            }
            if(res.cenaReseni > best.cenaReseni && res.vahaVeci <= programInstance.kapacitaBatohu){
                best = res;                                
            }                
        }        
        return best == null ? population.get(0) : best;
    }
}
