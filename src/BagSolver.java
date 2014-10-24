
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class BagSolver {
    
    public Result solveHeuristic(ProgramInstance programInstance){
        Result result = new Result(programInstance,Result.SolveMethod.HEURISTIC);  
        
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
        
        long mocnina = (long)Math.pow(2,programInstance.getPocetVeci());
        
        for(long i = 0;i < mocnina;i++){
            Result pomocnyResult = new Result(programInstance,Result.SolveMethod.BRUTE_FORCE);

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
        
        public int getPrice(){
            return price;
        }
        
        public int getWeight(){
            return weight;
        }
        
        public int getMaxPrice(){
            return maxPrice;
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
            
//            for(int vr : r.vector){
//                System.out.print(vr + " ");
//            }
//            System.out.println("");
//            
//           
            if(r.getPrice() > bestPrice && r.getWeight() <= m){
                bestPrice = r.getPrice();
                result.setCenaReseni(r.getPrice());
                result.setVahaVeci(r.getWeight());
            }
            if(r.position < n - 1){
                Row r0, r1;
                r0 = new Row(n, 0, r, c, v);
                r1 = new Row(n, 1, r, c, v);
                
                if(r0.getMaxPrice() > bestPrice && r0.getWeight() <= m){
                    stack.push(r0);
                }
                if(r1.getMaxPrice() > bestPrice && r0.getWeight() <= m){
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
    
    public Result solveFPTAS(ProgramInstance programInstance,int bits){
        
        for(int i = 0; i < programInstance.getPocetVeci(); i++){
            programInstance.addCena(i, programInstance.getCeny()[i] / bits);
        }
        
        Result result = solveDynamicProgramming(programInstance); 
        result.setName(Result.SolveMethod.FPTAS);
        result.setCenaReseni(result.getCenaReseni() * bits);
        return result;
    }
}
