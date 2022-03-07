package application;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.EventListener;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Controller {

	@FXML
	private ImageView imageV;
	@FXML
	private DatePicker dateInput;
	
	public void setImage() throws MalformedURLException{
	
	Image image = new Image("https://mars.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/01004/opgs/edr/fcam/FRB_486615455EDR_F0481570FHAZ00323M_.JPG");
	
	imageV.setImage(image);
	
	LocalDate sDate = dateInput.getValue();
	
	System.out.println(sDate);
	
	String apiKey = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?earth_date="+sDate+"2015-3-3&api_key=Ri7l2K0C8IcYrKQRAtbYGekgYZekkJqS8G1ZMBkU";
		
	System.out.println(apiKey);
	
	}
}
