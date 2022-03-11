package application;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Controller {

	@FXML
	private ImageView imageV;
	@FXML
	private DatePicker dateInput;
	@FXML
	private TextArea imageInfo;
	@FXML
	private ListView<String> photoList;
	
	ArrayList<Photo>photos = new ArrayList<Photo>();
	
	HttpURLConnection conn;
	
	ArrayList<String> photoIds = new ArrayList<String>();
	
	HashMap<String, Integer> idToPhoto = new HashMap<String,Integer>();
	
	private int photoIdSelected;
	
	public void setImage() throws IOException{
		
	// Creation of the Date the User Inputs	
	LocalDate sDate = dateInput.getValue();
	
	// Formation of apiKey for the date entered
	String apiKey = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?earth_date="+sDate+"&api_key=Ri7l2K0C8IcYrKQRAtbYGekgYZekkJqS8G1ZMBkU";
	
	
	//URL Connection
	URL url = new URL(apiKey.toString());
	
	conn = (HttpURLConnection) url.openConnection();
	
	conn.setRequestMethod("GET");
	
	conn.connect();
	
	// Result returned from the API
	StringBuffer apiResult = new StringBuffer();
	
	Scanner scan = new Scanner(url.openStream());
	
	while (scan.hasNext()) {
		
		apiResult.append(scan.nextLine());
		
	}
	
	scan.close();
	
	//JSON String from the Result Returned
	
	String apiJSON = apiResult.toString();

	JSONObject apiReturn = new JSONObject(apiJSON);
	
	JSONArray apiPhotos = apiReturn.getJSONArray("photos");
	
	// Setting the Photos in the Photo List
	for (int i = 0;i<apiPhotos.length();i++) {
		
		JSONObject apiPhoto = (JSONObject)apiPhotos.get(i);
		
		Photo rPhoto = new Photo();
		rPhoto.setId(Integer.toString(apiPhoto.getInt("id")));
		
		rPhoto.setSol(Integer.toString(apiPhoto.getInt("sol")));
		
		JSONObject camera  = (JSONObject) apiPhoto.get("camera");
		
		rPhoto.setCameraId(Integer.toString(camera.getInt("id")));
		
		rPhoto.setRoverId(Integer.toString(camera.getInt("rover_id")));
		
		rPhoto.setCameraName(camera.getString("full_name"));
		
		rPhoto.setImgSrc(apiPhoto.getString("img_src"));
		
		rPhoto.setDate(apiPhoto.getString("earth_date"));
		
		JSONObject rover = (JSONObject) apiPhoto.get("rover");
		
		rPhoto.setRoverName(rover.getString("name"));
		
		rPhoto.setRoverStatus("status");
		
		rPhoto.setLandD(rover.getString("landing_date"));
		
		rPhoto.setLaunchD(rover.getString("launch_date"));
		
		photos.add(rPhoto);		
	}
	
	// Change our Data Structure
	
	photoIds.clear();
	
	idToPhoto.clear();
	
	for (int i = 0;i<photos.size();i++) {
		
		photoIds.add(photos.get(i).getId());
		
		idToPhoto.put(photos.get(i).getId(), i);
		
	}
	
	
	// Update the Image List
	photoList.getItems().clear();
	photoList.getItems().addAll(photoIds);
	
	conn.disconnect();
	
	}
	// Update the Image after selection
	public void imageSelect() throws IOException {
		
		photoIdSelected = photoList.getSelectionModel().getSelectedIndex();
		
		Image image = new Image(photos.get(photoIdSelected).getImgSrc());
		
		imageV.setImage(image);
		
		imageInfo.setText("Photo ID: "+photos.get(photoIdSelected).getId()+"\n"+
		"Sol: "+photos.get(photoIdSelected).getSol()+"\n"
		+"Camera ID: "+photos.get(photoIdSelected).getCameraId()+"\n"
		+"Camera Name: "+photos.get(photoIdSelected).getCameraName()+"\n"
		+"Rover ID: "+photos.get(photoIdSelected).getRoverId()+"\n"
		+"Date: "+photos.get(photoIdSelected).getDate()+"\n"
		+"Rover Name: "+photos.get(photoIdSelected).getRoverName()+"\n"
		+"Rover Landing Date"+photos.get(photoIdSelected).getLandD()+"\n"
		+"Rover Launching Date: "+photos.get(photoIdSelected).getLaunchD()+"\n"
		+"Rover Status"+photos.get(photoIdSelected).getRoverStatus()+"\n"
		);
		
	}
	
}
