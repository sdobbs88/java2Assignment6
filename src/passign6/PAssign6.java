/* Shaun Dobbs
 CSCI 1302
 Assignment 6
 April 13, 2016
 Purpose : Create an mp3 player which shows the album cover, a brief description of the group, 
 plays the song selected and also gives the user the option to play, pause, rewind,
 stop or adjust volume. 
 */
package passign6;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

public class PAssign6 extends Application {

    String MEDIA_URL = "http://web-students.armstrong.edu/~sd7379/java/lynyrdSkynyrd.mp3";

    //Declaring string of song names 
    private final String songNames[] = {"Lynyrd Skynyrd - Simple Man", "Metallica - Enter Sandman",
        "ZZ Top - Gimme All Your Lovin", "Van Halen - Panama", "Stevie Ray Vaughan - Little Wing"};

    //Declaring a picture to display when each of the above titles is selected
    private final ImageView[] coverArt = {
        new ImageView("http://web-students.armstrong.edu/~sd7379/java/lynyrdSkynyrd.jpg"),
        new ImageView("http://web-students.armstrong.edu/~sd7379/java/metallica.jpg"),
        new ImageView("http://web-students.armstrong.edu/~sd7379/java/zzTop.jpg"),
        new ImageView("http://web-students.armstrong.edu/~sd7379/java/vanHalen.jpg"),
        new ImageView("http://web-students.armstrong.edu/~sd7379/java/srv.jpg")
    };

    Media media = new Media(MEDIA_URL);
    MediaPlayer mediaPlayer = new MediaPlayer(media);
    MediaView mediaView = new MediaView(mediaPlayer);

    @Override //Creating the song list 
    public void start(Stage primaryStage) {

        //Declaring an array which will play the media when a choice is selected
        //Create a list to hold all of the songs so the user can make a selection
        ListView<String> songList = new ListView<>(FXCollections.observableArrayList(songNames));
        songList.setPrefSize(200, 162);
        songList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        songList.getSelectionModel().selectFirst();

        Button playButton = new Button(">");
        playButton.setStyle("-fx-color:green;");

        playButton.setOnAction(e -> {
            if (playButton.getText().equals(">")) {
                mediaPlayer.play();
                playButton.setText("||");
                playButton.setStyle("-fx-color:yellow;");
            } else {
                mediaPlayer.pause();
                playButton.setText(">");
                playButton.setStyle("-fx-color:green;");
            }
        });

        Button rwButton = new Button("<<");
        Button ffButton = new Button(">>");
        rwButton.setStyle("-fx-color:lightblue;");
        ffButton.setStyle("-fx-color:lightblue;");
        Button stop = new Button("STOP");
        stop.setStyle("-fx-color:red;");
        rwButton.setOnAction(e -> mediaPlayer.seek(Duration.ZERO));
        ffButton.setOnAction(e -> mediaPlayer.seek(Duration.INDEFINITE));

        stop.setOnAction(e -> {
            mediaPlayer.stop();
            playButton.setText(">");
            playButton.setStyle("-fx-color:green;");
        });
        Slider slVol = new Slider();
        slVol.setPrefWidth(150);
        slVol.setMaxWidth(Region.USE_PREF_SIZE);
        slVol.setMinWidth(30);
        slVol.setValue(50);
        mediaPlayer.volumeProperty().bind(slVol.valueProperty().divide(100));

        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.autosize();
        hBox.getChildren().addAll(rwButton, playButton, stop, ffButton,
                new Label("Volume"), slVol);

        //Creating a pane to hold all of the items
        FlowPane imagePane = new FlowPane(10, 10);
        BorderPane pane = new BorderPane();
        pane.setLeft(new ScrollPane(songList));
        pane.setCenter(imagePane);
        pane.setBottom(hBox);
        imagePane.getChildren().add(coverArt[0]);


        /*Determining which item is selected. And based off of this determination,
         a album cover is printed within the window.*/
        songList.getSelectionModel().selectedItemProperty().addListener(
                ov -> {
                    mediaPlayer.stop();
                    imagePane.getChildren().clear();
                    for (Integer i : songList.getSelectionModel().getSelectedIndices()) {
                        imagePane.getChildren().add(coverArt[i]);
                    }
                    
                    playButton.setText(">");
                    playButton.setStyle("-fx-color:green;");
                    MEDIA_URL = setURL(songList.getSelectionModel().getSelectedIndex());
                    media = new Media(MEDIA_URL);
                    mediaPlayer = new MediaPlayer(media);
                    //mediaPlayer.setAutoPlay(true);
                    mediaPlayer.volumeProperty().bind(
                            slVol.valueProperty().divide(100));
                });
        //Making a scene to display all of the item

        Scene scene = new Scene(pane, 465, 190);
        primaryStage.setTitle("MP3 Player");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

    public String setURL(int selection) {
        String s0, s1, s2, s3, s4;
        if (selection == 0) {
            s0 = "http://web-students.armstrong.edu/~sd7379/java/lynyrdSkynyrd.mp3";
            MEDIA_URL = s0;
        } else if (selection == 1) {
            s1 = "http://web-students.armstrong.edu/~sd7379/java/metallica.mp3";
            MEDIA_URL = s1;
        } else if (selection == 2) {
            s2 = "http://web-students.armstrong.edu/~sd7379/java/zzTop.mp3";
            MEDIA_URL = s2;
        } else if (selection == 3) {
            s3 = "http://web-students.armstrong.edu/~sd7379/java/vanHalen.mp3";
            MEDIA_URL = s3;
        } else if (selection == 4) {
            s4 = "http://web-students.armstrong.edu/~sd7379/java/srv.mp3";
            MEDIA_URL = s4;

        }
        return MEDIA_URL;
    }
}
