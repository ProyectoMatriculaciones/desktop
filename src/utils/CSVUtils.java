package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;



public class CSVUtils {

	public static ArrayList<JSONObject> parseGrade(ArrayList<String> codelist,File csvFile){
		String claveCiclo;
		String claveCicloAnterior = "";
		String claveModulo;
		String claveModuloAnterior = "";
		ArrayList<JSONObject> alCiclos = new ArrayList<JSONObject>();
		ArrayList<JSONObject> alSelectedCiclos = new ArrayList<JSONObject>();
		ArrayList<JSONObject> alModulos = new ArrayList<JSONObject>();
		ArrayList<JSONObject> alUFs = new ArrayList<JSONObject>();
		 
		 try (CSVReader reader = new CSVReader(new FileReader (csvFile,StandardCharsets.UTF_8))) {
		      List<String[]> r = reader.readAll();
		   
		     claveCiclo = r.get(1)[0];
		     claveModulo = r.get(1)[6];
		      for (int i = 1; i < r.size(); i++) {
		    	  if(!claveCiclo.equals(claveCicloAnterior)) {
		    		  JSONObject ciclo = new JSONObject();
		    		  ciclo.put("careerCode", r.get(i)[0]);
		    		  ciclo.put("careerName", r.get(i)[1]);
		    		  ciclo.put("curricularAdaptationCode", r.get(i)[2]);
		    		  ciclo.put("careerHours", r.get(i)[3]);
		    		  ciclo.put("careerStartDate", r.get(i)[4]);
		    		  ciclo.put("careerFinishDate", r.get(i)[5]);
		    		  if(alCiclos.size()>0) {
		    			  alCiclos.get(alCiclos.size()-1).put("arrayMO", alModulos);
		    		  }
		    		  if(alModulos.size()>0) {
		    			  alModulos.get(alModulos.size()-1).put("arrayUF", alUFs);
		    		  }
		    		  alModulos.clear();
		    		  alCiclos.add(ciclo);
		    		  
		    	  };
		    	  
		    	  if(!claveModulo.equals(claveModuloAnterior)) {
		    		  JSONObject modulo = new JSONObject();
		    		  modulo.put("MOCode", r.get(i)[6]);
		    		  modulo.put("MOName", r.get(i)[7]);
		    		  modulo.put("MOMinDuration", r.get(i)[8]);
		    		  modulo.put("MOMaxDuration", r.get(i)[9]);
		    		  modulo.put("MOSartDate", r.get(i)[10]);
		    		  modulo.put("MOFinishDate", r.get(i)[11]);
		    		  if(alModulos.size()>0) {
		    			  alModulos.get(alModulos.size()-1).put("arrayUF", alUFs);
		    		  }
		    		  alUFs.clear();
		    		  alModulos.add(modulo);
		    		  
		    	  }
		    	  
		    	  JSONObject uf = new JSONObject();
		    	  uf.put("UFCode", r.get(i)[12]);
		    	  uf.put("UFName", r.get(i)[13]);
		    	  uf.put("UFDuration", r.get(i)[14]);
		    	  uf.put("FCTIndicator", r.get(i)[15]);
		    	  uf.put("synthesisIndicator", r.get(i)[16]);
		    	  uf.put("languageIndicator", r.get(i)[17]);
		    	  uf.put("projectIndicator", r.get(i)[18]);
		    	  alUFs.add(uf);
		    	 claveCicloAnterior = claveCiclo; 
		    	 claveModuloAnterior = claveModulo;
		    	 if(i!=r.size()&&i!=r.size()-1) {
		    		 claveCiclo = r.get(i+1)[0];
			    	 claveModulo =  r.get(i+1)[6];
		    	 }
		    	 
			}
		      alModulos.get(alModulos.size()-1).put("arrayUF", alUFs);
		      alCiclos.get(alCiclos.size()-1).put("arrayMO", alModulos);
		      System.out.println(alCiclos.get(alCiclos.size()-1).toString());
		      
		      
		      for (int j = 0; j < alCiclos.size(); j++) {
				String codeCiclo = alCiclos.get(j).getString("careerCode");
				if (codelist.contains(codeCiclo)) {
					alSelectedCiclos.add(alCiclos.get(j));
				}
			}
		    
		  } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CsvException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
		return alSelectedCiclos;
	}
	
}
