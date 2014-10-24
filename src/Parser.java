
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Parser {
     public ArrayList<ProgramInstance> parseInput(File file){
        BufferedReader reader = null;
        ArrayList<ProgramInstance> programInstances = new ArrayList();
                
        try {
            String text;
            reader = new BufferedReader(new FileReader(file));
            while((text = reader.readLine()) != null){
                
                String [] tokens = text.split(" ");
                
                ProgramInstance programInstance = new ProgramInstance(Integer.parseInt(tokens[1]));
                programInstance.setId(Integer.parseInt(tokens[0]));
                programInstance.setPocetVeci(Integer.parseInt(tokens[1]));
                programInstance.setKapacitaBatohu(Integer.parseInt(tokens[2]));
                int position = 0;
                for (int i = 3; i < tokens.length; i++ ) {
                    programInstance.addVaha(position, Integer.parseInt(tokens[i]));
                    programInstance.addCena(position, Integer.parseInt(tokens[++i]));
                    position++;
                }
                programInstances.add(programInstance);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Ukol1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Ukol1.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                }
        }
        return programInstances;
    }
    
    public ArrayList<Result> parseOutput(File file){
        BufferedReader reader = null;
        ArrayList<Result> results = new ArrayList();
                
        try {
            String text;
            reader = new BufferedReader(new FileReader(file));
            while((text = reader.readLine()) != null){
                
                String [] tokens = text.split(" ");
                
                Result result = new Result(Integer.parseInt(tokens[1]),Integer.parseInt(tokens[0]),Result.SolveMethod.REFERENCE);
                result.setPocetVeci(Integer.parseInt(tokens[1]));
                result.setCenaReseni(Integer.parseInt(tokens[2]));
                int position = 0;
                for (int i = 4; i < tokens.length; i++ ) {
                    result.addReseni(position, Integer.parseInt(tokens[i]));                    
                    position++;
                }
                results.add(result);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Ukol1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Ukol1.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                }
        }
        return results;
    }
}
