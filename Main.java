package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		
			Parent root = FXMLLoader.load(getClass().getResource("intro.fxml"));
			
			Scene scene = new Scene(root);
			
			primaryStage.setTitle("NASA API");
			
			primaryStage.setScene(scene);
			
			primaryStage.show();
			
			}
	
	public static void main(String[] args) {
		
		Main.launch(args);
		
	}
	
}
