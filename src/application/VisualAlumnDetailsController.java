package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class VisualAlumnDetailsController implements Initializable {

	@FXML
	private Label lbEmail;

	@FXML
	private VBox vBoxId;
	
	@FXML
    private Button btActualizar;
	
	private ArrayList<TextField> txtVboxList=new ArrayList<>();
	
	private ArrayList<String> txtList=new ArrayList<>();

	private String email;

	private String jsonSTR = "{\r\n" + "    \"residenceCountry\": \"Espanya\",\r\n" + "    \"modality\": \"\",\r\n"
			+ "    \"birthdate\": \"30/11/2004\",\r\n" + "    \"firstGuardianSurname2\": \"Ramos\",\r\n"
			+ "    \"firstGuardianSurname1\": \"Soler\",\r\n" + "    \"secondGuardianSurname2\": \"\",\r\n"
			+ "    \"language\": \"Català i Castellà\",\r\n" + "    \"secondGuardianSurname1\": \"Gómez\",\r\n"
			+ "    \"docTypeFirstGuardian\": \"DNI\",\r\n"
			+ "    \"password\": \"098f6bcd4621d373cade4e832627b4f6\",\r\n"
			+ "    \"docTypeSecondGuardian\": \"DNI\",\r\n" + "    \"RACLIdentification\": \"620525022\",\r\n"
			+ "    \"convocatory\": \" Cicles mitjans d'FP 2020/2021\",\r\n" + "    \"regimeP1\": \"Diürn\",\r\n"
			+ "    \"originInstituteName\": \"El Pilar\",\r\n" + "    \"birthCountry\": \"Espanya\",\r\n"
			+ "    \"secondSurname\": \"Gómez\",\r\n" + "    \"residenceMunicipality\": \"Cornellà de Llobregat\",\r\n"
			+ "    \"secondGuardianName\": \"Aaron\",\r\n"
			+ "    \"termName\": \"Sistemes microinformàtics i xarxes\",\r\n"
			+ "    \"assignedInstituteCode\": \"08016781\",\r\n"
			+ "    \"otherInfo\": \"Bloc: -- Escala: -- Planta: baixos  Porta: 4 Altres dades: --\",\r\n"
			+ "    \"instituteNameP1\": \"Institut Esteve Terradas i Illa\",\r\n"
			+ "    \"numDocFirstGuardian\": \"45831142P\",\r\n" + "    \"numDocSecondGuardian\": \"41834442H\",\r\n"
			+ "    \"phoneNumber\": \"614365601\",\r\n" + "    \"scheduleP1\": \"Matí\",\r\n"
			+ "    \"PASS\": \"\",\r\n" + "    \"nationality\": \"Espanya\",\r\n"
			+ "    \"originInstituteCode\": \"08016461\",\r\n" + "    \"convocatoryCode\": \"PRE20-2100191921\",\r\n"
			+ "    \"courseP1\": \"1\",\r\n" + "    \"termStatus\": \"Validada\",\r\n" + "    \"name\": \"Núria\",\r\n"
			+ "    \"addressNumber\": \"11\",\r\n" + "    \"termType\": \"SI\",\r\n"
			+ "    \"originTitleCode\": \"7\",\r\n" + "    \"birthMunicipality\": \"Cornellà de Llobregat\",\r\n"
			+ "    \"resideceProvince\": \"Barcelona\",\r\n"
			+ "    \"instituteMunicipality\": \"Cornellà de Llobregat\",\r\n"
			+ "    \"residenceLocality\": \"Can Fatjó\",\r\n" + "    \"lastCourse\": \"4\",\r\n"
			+ "    \"firstSurname\": \"Soler\",\r\n" + "    \"email\": \"muzqtzsge1@caramail.com\",\r\n"
			+ "    \"instituteFinanciationP1\": \"Públic\",\r\n" + "    \"firstGuardianName\": \"Josep\",\r\n"
			+ "    \"originTitleName\": \"Educació Secundària Obligatòria\",\r\n"
			+ "    \"instituteSSTT\": \"Baix Llobregat\",\r\n" + "    \"sex\": \"Dona\",\r\n"
			+ "    \"acces\": \"CR\",\r\n" + "    \"NIE\": \"\",\r\n" + "    \"CP\": \"08940\",\r\n"
			+ "    \"religion\": \"\",\r\n"
			+ "    \"sessionToken\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYwNzVjOTRjNWY4YTEzMDAwNGFlNDY1YSIsImFkbWluIjpmYWxzZSwiaWF0IjoxNjE4NTgyNDA1LCJleHAiOjE2MTg1ODM4NDV9.Ewt3HdWl3HBj_Rvb7UYpycca6W89Jcr83BEpS_CjAQ8\",\r\n"
			+ "    \"alumnType\": \"Ordinari\",\r\n" + "    \"addressName\": \"Laureà Miró\",\r\n"
			+ "    \"instituteCodeP1\": \"08016781\",\r\n" + "    \"modalityCode\": \"\",\r\n"
			+ "    \"DNI\": \"83326696L\",\r\n" + "    \"termCode\": \"CFPM    IC10\",\r\n" + "    \"TIS\": \"\",\r\n"
			+ "    \"matriculatedUfs\": [\"IC1000101\", \"IC1000102\", \"IC1000103\", \"IC1000104\", \"IC1000105\", \"IC1000106\", \"IC1000201\", \"IC1000202\"],\r\n"
			+ "    \"selectedDocumentsProfile\": {\r\n" + "        \"_id\": \"6075c9975f8a130004ae465f\",\r\n"
			+ "        \"arrayDoc\": [{\r\n" + "            \"documentName\": \"DNI\",\r\n"
			+ "            \"filePath\": \"/app/uploads/muzqtzsge1caramailcom/Perfil Demo/DNI/a.java\"\r\n"
			+ "        }, {\r\n" + "            \"documentName\": \"PASS\",\r\n"
			+ "            \"filePath\": \"/app/uploads/muzqtzsge1caramailcom/Perfil Demo/DNI/b.java\"\r\n"
			+ "        }, {\r\n" + "            \"documentName\": \"asd\",\r\n"
			+ "            \"filePath\": \"/app/uploads/muzqtzsge1caramailcom/Perfil Demo/DNI/c.java\"\r\n"
			+ "        }, {\r\n" + "            \"documentName\": \"DNI REVERSO\",\r\n"
			+ "            \"filePath\": \"/app/uploads/muzqtzsge1caramailcom/Perfil Demo/DNI/d.java\"\r\n"
			+ "        }],\r\n" + "        \"name\": \"Perfil Demo\",\r\n"
			+ "        \"description\": \"Descripcion Perfil demo\\t\"\r\n" + "    }\r\n" + "}";

	JSONObject json = new JSONObject(jsonSTR);

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		vBoxId.setSpacing(7);

		Iterator<?> permisos = json.keys();
		while (permisos.hasNext()) {
			String key = (String) permisos.next();

			if (key.equals("matriculatedUfs") ) {
				int i=1;
				JSONArray jsonarray=(JSONArray) json.get(key);
				for (Object object : jsonarray) {
					String txt=object.toString();
					txtList.add(txt);
					Label l = new Label(key+" "+i);
					i++;
					TextField tf = new TextField(txt);
					txtVboxList.add(tf);
					vBoxId.getChildren().add(l);
					vBoxId.getChildren().add(tf);
				}
				
			} else if(key.equals("selectedDocumentsProfile")){

				/*
				 * JSONObject json2=json.getJSONObject(key); Iterator<?> permisos2 =
				 * json2.keys(); while (permisos2.hasNext()) { String key2 = (String)
				 * permisos2.next(); if(key2.equals("arrayDoc")) { int i=1; JSONArray
				 * jsonarray=(JSONArray) json2.get(key); for (Object object : jsonarray) {
				 * String txt=object.toString(); Label l = new Label(key+" "+i); i++; TextField
				 * tf = new TextField(txt); vBoxId.getChildren().add(l);
				 * vBoxId.getChildren().add(tf); } }else {
				 * 
				 * }String txt=json.getString(key); Label l = new Label(key); TextField tf = new
				 * TextField(txt); vBoxId.getChildren().add(l); vBoxId.getChildren().add(tf); }
				 */
				
			}else if(key.equals("password")||key.equals("sessionToken")){
				
			}
			else {
				String txt=json.getString(key);
				
				txtList.add(txt);
				Label l = new Label(key);
				TextField tf = new TextField(txt);
				txtVboxList.add(tf);
				vBoxId.getChildren().add(l);
				vBoxId.getChildren().add(tf);
				
			}
		}

	}


    @FXML
    void updateJSON(ActionEvent event) {

    	ArrayList<String> allModifications = new ArrayList<String>();
    	for(int i=0;i<txtVboxList.size();i++) {
    		String sTxt = txtVboxList.get(i).getText();
    		if (!sTxt.equals(txtList.get(i)))
    		{
    			//detecta los cambios que hay en los textfields y con la posición puedes pillar la key
    			//que le corresponde, y hacer el cambio en el json.
    			System.out.println("cambio");
    		}
		}

    }
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
