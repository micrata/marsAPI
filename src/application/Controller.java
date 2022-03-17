package application;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import org.json.JSONArray;
import org.json.JSONObject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 * JavaFX elements and ids with interactions
 * <li> JavaFX dateInput @fx:id DatePicker - Allows user to select date from calendar.
 *      All photoIDs from selected day are added to photoList and displayed in ListView.
 * <li> JavaFX ListView @fx:id photoList - Displays list of photoIDs from date selected.
 * <li> JavaFX imageView @fx:id imageV - Displays images associated with selected photoID in Listview from specified date.
 * <li> JavaFX TextArea @fx:id imageInfo - Displays all metadata associated with selected photo. Data displayed is pulled
 *      from JSONObject apiPhotos using keyset from photoID.
 * <li> JavaFX TextField @fx:id imgSrcText - Displays link from String ApiKey. Returns link with image from
 *      selected date.
 * <li> JavaFX Button @fx:id downloadbutton - Executes method downloadImage();
 * <li> JavaFX Button @fx:id copyButton - Executes method copyText();
 * <li> JavaFX Hypyerlink @fx:id historybutton - Sets int mode to -1 to reverse stack Photohistory. Executes
 *      method historyButtonM().
 */
public class Controller {
    /**
     * Private instance variables are the actual buttons and displays in the javafx application.
     */
    @FXML
    private ImageView imageV;
    @FXML
    private DatePicker  dateInput;
    @FXML
    private TextArea imageInfo;
    @FXML
    private ListView<String> photoList;
    @FXML
    private TextField imgSrcText;
    @FXML
    private Button copyButton;
    @FXML
    private AnchorPane anchorpane;
    @FXML
    private Hyperlink historyButton = new Hyperlink();

    /**
     * photos objects stored as photo elements in an array list.
     * Establish Connection to API.
     * Store PhotoIDs as a linked Hashmap to recall photos from LinkedHashmap idToPhoto.
     * PhotoHistory stored as stack to easily reverse order.
     * int mode determines if viewing keyset in original order or reversed order. @historyButton()
     * int photoIDSelected is key of photo selected from Photolist
     */

    ArrayList<Photo>photos = new ArrayList<Photo>();

    HttpURLConnection conn;

    HashMap<String, Integer> idToPhoto = new LinkedHashMap<String,Integer>();

    Stack<Photo> photoHistory = new Stack<Photo>();

    int mode = 1;

    private int photoIdSelected;

    /**
     * setImage() generates Photo Objects from the JSON Result that is returned from the API. The Photo generated is added to
     *      the photos ArrayList. The PhotoIDs are then paired with the index of the photo object in photos arraylist.
     *      JavaFX ListView @fx:id photoList then displays the photoIDs as a list. An HTTP Connection is established
     *      to the generated URL. The URL generated contains the date specified from dateInput. String Buffer holds
     *      the generated URL. Scanner is used to read JSON data from API URL. String JSON then converts JSON data
     *      into a usable string. String is then parsed and builds photo objects. The information is then sorted
     *      into instance variables of the specific photo objects. If the user wants to view a new date, the data
     *      is then cleared. In the event a new list is generated, new IDs are assigned to new photos from the new
     *      specified date.
     */

    public void setImage() throws IOException{

        // Creation of the Date the User Inputs
        LocalDate sDate = dateInput.getValue();

        // Formation of apiKey for the date entered
        String apiKey = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?earth_date="+sDate+
                "&api_key=Ri7l2K0C8IcYrKQRAtbYGekgYZekkJqS8G1ZMBkU";


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

        photos.clear();

        // Setting the Photos in the Photo List from JSON for API
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

            rPhoto.setRoverStatus(rover.getString("status"));

            rPhoto.setLandD(rover.getString("landing_date"));

            rPhoto.setLaunchD(rover.getString("launch_date"));

            photos.add(rPhoto);
        }

        // Change Data Structure
        idToPhoto.clear();

        for (int i = 0;i<photos.size();i++) {

            idToPhoto.put(photos.get(i).getId(), i);

        }

        // Update the Image List
        photoList.getItems().clear();

        photoList.getItems().addAll(idToPhoto.keySet());

        conn.disconnect();

    }
    // Update the Image after selection

    /**
     * imageSelect() sets JavaFX imageView @fx:id imageV to the selected image. Selected image ID is pulled from
     *      list generated by method setImage(). imageInfo pulls photo metadata using specified methods from
     *      Photo.java Class. In the event mode is -1, the source for the methods changes to the stack.
     */

    public void imageSelect() throws IOException {

        copyButton.setText("Copy");

        photoIdSelected = photoList.getSelectionModel().getSelectedIndex();

        if (photoIdSelected >=0) {
            Image image = new Image(photos.get(photoIdSelected).getImgSrc());

            imageV.setImage(image);

            imageInfo.setText("Photo ID: "+photos.get(photoIdSelected).getId()+"\n"+
                    "Sol: "+photos.get(photoIdSelected).getSol()+"\n"
                    +"Camera ID: "+photos.get(photoIdSelected).getCameraId()+"\n"
                    +"Camera Name: "+photos.get(photoIdSelected).getCameraName()+"\n"
                    +"Rover ID: "+photos.get(photoIdSelected).getRoverId()+"\n"
                    +"Date: "+photos.get(photoIdSelected).getDate()+"\n"
                    +"Rover Name: "+photos.get(photoIdSelected).getRoverName()+"\n"
                    +"Rover Landing Date: "+photos.get(photoIdSelected).getLandD()+"\n"
                    +"Rover Launching Date: "+photos.get(photoIdSelected).getLaunchD()+"\n"
                    +"Rover Status: "+photos.get(photoIdSelected).getRoverStatus()+"\n"
            );

            imgSrcText.setText(photos.get(photoIdSelected).getImgSrc());

            // If Mode is greater than 1 than we are in normal viewing mode so need to push to histroy
            if(mode>0)
                photoHistory.push(photos.get(photoIdSelected));
        }
    }

    /**
     * downloadImage() sets a new URL as the URL from the selected image. It then opens the directory selector GUI.
     * The selected directory (folder) is then saved as a file. An input stream is opened and recalls the image from
     * the url specified. The output stream is then opened and the location is then fed in to the designated pathname
     * provided by the directory selector gui. The output stream writer then writes the bytes array directly into
     * the file. Both the input and output streams are then closed.
     */

    public void downloadImage() throws IOException {
        // Sets the URL
        URL url = new URL(photos.get(photoIdSelected).getImgSrc());

        DirectoryChooser f = new DirectoryChooser();

        Stage s = (Stage) anchorpane.getScene().getWindow();

        File f1  = f.showDialog(s);

        String filePath = f1.getAbsolutePath();


        // Open input stream to initiate transfer
        InputStream is = url.openStream();

        // Open output stream to determine destination
        OutputStream os = new FileOutputStream(filePath+"/Roverimage(" + photos.get(photoIdSelected).getId() + ").jpg");

        // setting length of while loop. 1048576 = 1MB.
        byte[] b = new byte[1048576];
        int length;

        // Writing directly into the output stream.
        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }

        // Close both streams.
        is.close();
        os.close();

    }

    /**
     * copyText() pulls the String from JavaFX TextField @fx:id imgSrcText and puts that string into the clipboard.
     */
    // Copy to Clipboard Text
    public void copyText() throws InterruptedException {

        Clipboard clip = Clipboard.getSystemClipboard();

        ClipboardContent cont = new ClipboardContent();

        cont.putString(photos.get(photoIdSelected).getImgSrc()); //Change ImgSrc to the proper variable or method to get the link

        clip.setContent(cont);

        copyButton.setText("Copied!");

    }

    /**
     * historyButtonM() is a method that gets called if the hyperlink is clicked. It changes JavaFX ListView @fx:id
     * photoList to the stack. Allowing the user to switch between photos and their previously viewed photos.
     */
    public void historyButtonM() throws IOException {

        mode*=-1;

        historyButton.setVisited(false);

        if (mode>0) {

            photoList.getItems().clear();

            idToPhoto.clear();

            historyButton.setText("View History");

            setImage();

        }
        else {

            getHistory();

        }

    }

    /**
     * getHistory() Records the PhotoIds into a stack of previously selected images.
     */

    public void getHistory() throws IOException {

        photos.clear();

        photoList.getItems().clear();

        idToPhoto.clear();

        Queue<Photo>auxS = new LinkedList<Photo>();

        int stackS = photoHistory.size();

        // Stack to Queue Twice to keep the same order of photoHistory (Stack)

        for (int i = 0;i<stackS;i++) {

            photos.add(photoHistory.peek());

            idToPhoto.put(photoHistory.peek().getId(), i);

            auxS.add(photoHistory.pop());

        }

        for (int i = 0;i<stackS;i++) {

            photoHistory.push(auxS.remove());

        }

        for (int i = 0;i<stackS;i++) {

            auxS.add(photoHistory.pop());;

        }

        for (int i = 0;i<stackS;i++) {

            photoHistory.push(auxS.remove());

        }


        for (int i = 0;i<photoHistory.size();i++) {

            idToPhoto.put(photoHistory.get(i).getId(), i);

        }

        photoList.getItems().addAll(idToPhoto.keySet());

        historyButton.setText("Photos");


    }


}
// Citations
// http://tutorials.jenkov.com/java-io/inputstream.html
// https://www.programiz.com/java-programming/outputstream
// https://docs.oracle.com/javase/7/docs/api/java/nio/file/Files.html#copy(java.nio.file.Path,%20java.io.OutputStream)

// https://stackoverflow.com/questions/47654918/javafx-directory-chooser-how-to-get-the-path-from-directory
// http://tutorials.jenkov.com/javafx/directorychooser.html
// https://www.javatpoint.com/how-to-create-a-new-folder-in-java