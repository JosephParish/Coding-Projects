package cw2.cw2;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Class to create the Display
 */
public class Display extends Application {

    /**
     * DO NOT EDIT
     * Variables relating to the display
     */
    private Pane root;
    private VBox vBox;
    private Button deliveryButton;
    private final SimpleStringProperty deliveryButtonText = new SimpleStringProperty("Start Deliveries");
    private final VBox parcelBox = new VBox();

    /**
     * Button for use in Stage 7
     */
    private Button receiptButton;
    private final SimpleStringProperty receiptButtonText = new SimpleStringProperty("Print Receipts");

    /**
     * DO NOT EDIT
     * Constants for the display
     */
    private final int WINDOW_SIZE_WIDTH = 800;
    private final int WINDOW_SIZE_HEIGHT = 500;
    private final int IMAGE_HW = 30;
    private final int PATH_OFFSET = 15;
    private final int PARCEL_OFFSET = 20;

    /**
     * DO NOT EDIT
     * Method to launch display and run program
     * @param args from command line
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * DO NOT EDIT
     * Method to set up stage
     * @param primaryStage stage to set up
     */
    @Override
    public void start(Stage primaryStage) throws IOException
    {
        root = new Pane();
        root.setLayoutX(10);
        root.setLayoutY(10);
        root.setMaxSize(WINDOW_SIZE_WIDTH,WINDOW_SIZE_HEIGHT);

        HBox hBox = new HBox();
        vBox = new VBox();
        vBox.setSpacing(10);

        // Students implement this
        initialize();

        //these are pre-existing methods not to be changed
        addKey(); //key is added to vBox
        addParcelList(); //parcelList is added to vBox
        addProcessingFacilities(); //map of facilities

        //arrange the display
        hBox.getChildren().add(vBox);
        root.getChildren().add(hBox);

        //Set up and show scene
        Scene scene = new Scene(root, WINDOW_SIZE_WIDTH, WINDOW_SIZE_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Delivery Service");
        primaryStage.show();
    }

    /**
     * DO NOT EDIT
     * Method to add the key to the screen which displays all types of parcels
     * and a button to show the next delivery.
     */
    private void addKey() {
        Image standardParcel = new Image("standardParcel.png");
        ImageView standardParcelImage = new ImageView();
        standardParcelImage.setFitHeight(IMAGE_HW);
        standardParcelImage.setFitWidth(IMAGE_HW);
        standardParcelImage.setImage(standardParcel);

        Image trackedParcel = new Image("trackedParcel.png");
        ImageView trackedParcelImage = new ImageView();
        trackedParcelImage.setFitHeight(IMAGE_HW);
        trackedParcelImage.setFitWidth(IMAGE_HW);
        trackedParcelImage.setImage(trackedParcel);

        Image medicalParcel = new Image("medicalParcel.png");
        ImageView medicalParcelImage = new ImageView();
        medicalParcelImage.setFitHeight(IMAGE_HW);
        medicalParcelImage.setFitWidth(IMAGE_HW);
        medicalParcelImage.setImage(medicalParcel);

        Label standardParcelLabel = new Label("Standard Parcel");
        Label trackedParcelLabel = new Label("Tracked Parcel");
        Label medicalParcelLabel = new Label("Medical Parcel");

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(standardParcelImage, 1, 1);
        gridPane.add(trackedParcelImage, 1, 2);
        gridPane.add(medicalParcelImage, 1, 3);
        gridPane.add(standardParcelLabel, 2, 1);
        gridPane.add(trackedParcelLabel, 2, 2);
        gridPane.add(medicalParcelLabel, 2, 3);
        vBox.getChildren().add(gridPane);

        // Set up of delivery button.
        deliveryButton  = new Button();
        deliveryButton.textProperty().bind(deliveryButtonText);
        deliveryButton.setOnAction(e->nextDelivery());

        if(Data.getParcels().isEmpty()){
            updateDeliveryButtonText("No parcels to deliver");
            deliveryButton.setDisable(true);
        }
        vBox.getChildren().add(deliveryButton);

        //students program this method
        addButtonStageSeven(vBox);

        Label deliveryLabel = new Label();
        deliveryLabel.setText("Deliveries");
        vBox.getChildren().add(deliveryLabel);
    }

    /**
     * DO NOT EDIT
     * Method to update the buttonText
     * @param text to update button to
     */
    private void updateDeliveryButtonText(String text) {
        deliveryButtonText.set(text);
    }

    /**
     * DO NOT EDIT
     * This method lists the parcels in the parcel stack on the display according to their type.
     */
    private void addParcelList(){
        parcelBox.setSpacing(5.00);
        LinkedList<Parcel> parcels = new LinkedList<>(Data.getParcels());
        Collections.reverse(parcels);
        for(Parcel parcel: parcels){
            parcelBox.getChildren().add(getImageView(parcel));

        }
        // Create a ScrollPane and set the VBox as its content
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(parcelBox);

        // Set the preferred size of the scroll pane
        scrollPane.setPrefSize(150, 250);
        vBox.getChildren().addAll(scrollPane);
    }

    /**
     * DO NOT EDIT
     * This method adds the processing facilities in the processing facilities list to the display.
     */
    private void addProcessingFacilities(){
        for(ProcessingFacility processingFacility : Data.getProcessingFacilities()){
            Image processingFacilityImage = new Image("processing-facility.png");
            ImageView processingFacilityImageView = new ImageView();
            processingFacilityImageView.setFitHeight(IMAGE_HW);
            processingFacilityImageView.setFitWidth(IMAGE_HW);
            processingFacilityImageView.setImage(processingFacilityImage);
            processingFacilityImageView.setLayoutX(processingFacility.getX());
            processingFacilityImageView.setLayoutY(processingFacility.getY());
            root.getChildren().add(processingFacilityImageView);
            for(ProcessingFacility neighbour:processingFacility.getNeighbours()){
                Line path = new Line(processingFacility.getX()+PATH_OFFSET, processingFacility.getY()+PATH_OFFSET,
                        neighbour.getX()+PATH_OFFSET, neighbour.getY()+PATH_OFFSET);
                path.setFill(Color.BLUE);
                root.getChildren().add(path);
            }
        }
    }

    /**
     * DO NOT EDIT
     * This method is called with the delivery button is pressed. It delivers the parcels from the parcel stack one by
     * one until complete.
     */
    private void nextDelivery() {
        // Pop the first parcel and start its delivery
        Parcel parcel = Data.getParcels().pop();
        parcelBox.getChildren().remove(0);
        if(Data.getParcels().isEmpty()){
            updateDeliveryButtonText("No more parcels");
            deliveryButton.setDisable(true);
        } else {
            updateDeliveryButtonText("Next delivery");
        }
        if (parcel != null) {
            // Deliver the parcel and start animation
            // After animation completes, check for next parcel in the queue
            deliverParcel(parcel, this::nextDelivery);
        }
    }

    /**
     * DO NOT EDIT
     * Method to create an image view for a parcel
     * @param parcel to create an image for
     * @return imageview for the parcel
     */
    private ImageView getImageView(Parcel parcel){
        Image parcelImage = null;
        if(parcel.getClass().getSimpleName().equals("StandardParcel")){
            parcelImage = new Image("standardParcel.png");
        } else if(parcel.getClass().getSimpleName().equals("TrackedParcel")) {
            parcelImage = new Image("trackedParcel.png");
        } else if (parcel.getClass().getSimpleName().equals("MedicalParcel")){
            parcelImage = new Image("medicalParcel.png");
        }
        ImageView parcelImageView = new ImageView();
        parcelImageView.setFitHeight(IMAGE_HW);
        parcelImageView.setFitWidth(IMAGE_HW);
        parcelImageView.setImage(parcelImage);
        return parcelImageView;
    }

    /**
     * DO NOT EDIT
     * This method takes in a Parcel object, ensures the correct image is output to the display, and calls getPath to
     * animate the image along the delivery path.
     * @param parcel to be delivered on display
     * @param onFinish to track animation
     */
    private void deliverParcel(Parcel parcel, Runnable onFinish){
        ImageView parcelImageView = getImageView(parcel);
        List<ProcessingFacility> path = getPath(parcel.getSender(), parcel.getRecipient());
        if (!path.isEmpty()) {
            // Add ImageView to the root pane
            root.getChildren().add(parcelImageView);
            for (int i=0; i<path.size()-1;i++) {
                parcel.process(path.get(i));
                animateImageView(parcelImageView, path, 0);
            }
            parcel.process(parcel.getRecipient());
            Data.getProcessedParcels().push(parcel);
        }
    }

    /**
     * DO NOT EDIT
     * This method constructs a path from one facilty to another, to find a route that a parcel can take. If no path
     * exists then an empty list is returned. This route is not necessarily the shortest.
     * @param start processing facility
     * @param target processing facility
     * @return path as a list
     */
    public static List<ProcessingFacility> getPath(ProcessingFacility start, ProcessingFacility target)
    {
        ArrayList<ProcessingFacility> visited = new ArrayList<>();
        Stack<ProcessingFacility> stack = new Stack<>();
        Map<ProcessingFacility, ProcessingFacility> parentMap = new HashMap<>();

        stack.push(start);
        visited.add(start);

        while (!stack.isEmpty()) {
            ProcessingFacility current = stack.pop();
            if (current == target) {
                List<ProcessingFacility> path = new ArrayList<>();
                while (target != start) {
                    path.add(target);
                    target = parentMap.get(target);
                }
                path.add(start); // Add start facility
                Collections.reverse(path);
                return path;
            }
            for (ProcessingFacility neighbor : current.getNeighbours()) {
                if (!visited.contains(neighbor)) {
                    stack.push(neighbor);
                    visited.add(neighbor);
                    parentMap.put(neighbor, current);
                }
            }
        }
        return Collections.emptyList(); // No path found
    }

    /**
     * DO NOT EDIT
     * This method is called recursively with an imageView and a path. An animation is then played until the imageView
     * has travelled along the entire path (using animateTransition method).
     * The index is used to track how many pairs of transitions there are in the path upon which the image can travel.
     * @param imageView the image to animate
     * @param path the path to travel along
     * @param index the index, to keep track of the pairs to travel from/to
     */
    private void animateImageView(ImageView imageView, List<ProcessingFacility> path, int index) {
        if (index < path.size() - 1) {
            ProcessingFacility currentFacility = path.get(index);
            ProcessingFacility nextFacility = path.get(index + 1);

            // Animate the transition from current facility to next facility
            animateTransition(imageView, currentFacility.getX(), currentFacility.getY(),
                    nextFacility.getX(), nextFacility.getY(), () -> {
                        // Recursively call animateImageView to animate the next segment
                        animateImageView(imageView, path, index + 1);
                    });
        } else {
            // Animation completed for the entire path
            root.getChildren().remove(imageView);
//            System.out.println("Animation completed for the entire path.");
        }
    }

    /**
     * DO NOT EDIT
     * This animates the transition along a path.
     * @param imageView the image to animate
     * @param startX the X coord starting point of the animation
     * @param startY the Y coord starting point of the animation
     * @param endX the X coord ending point of the animation
     * @param endY the Y coord starting point of the animation
     * @param onFinished triggers the next animation if there is a longer path than 1 pair.
     */
    private void animateTransition(ImageView imageView, double startX, double startY,
                                   double endX, double endY, Runnable onFinished) {
        // Create TranslateTransition for the current segment
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(imageView);
        translateTransition.setDuration(Duration.seconds(3)); // Adjust duration as needed
        translateTransition.setFromX(startX);
        translateTransition.setFromY(startY);
        translateTransition.setToX(endX);
        translateTransition.setToY(endY);
        if (onFinished != null) {
            // Set onFinished event to trigger the next animation
            translateTransition.setOnFinished(event -> onFinished.run());
        }
        // Play the animation
        translateTransition.play();
    }

    /**
     * DO NOT EDIT ANY CODE ABOVE THIS COMMENT. There are some methods below which you need to complete. You will also
     * need to write additional methods.
     */

    /**
     * Initializes data by reading processing facilities, connections, and parcels.
     * This method is required to run before displaying data.
     */
    private void initialize()
    {
        Data.readProcessingFacilities();
        Data.readConnections();
        Data.readParcels();
    }

    /**
     * Adds a receipt button to a VBox for Stage 7.
     *
     * @param vBox the VBox to add the button to
     */
    private void addButtonStageSeven(VBox vBox)
    {
        Button receiptButton = new Button();
        receiptButton.textProperty().bind(receiptButtonText);
        receiptButton.setOnAction(e -> printReceipts());

        vBox.getChildren().add(receiptButton);
    }

    /**
     * Prints receipts of processed parcels to a text file ("receipts.txt").
     */
    private void printReceipts()
    {
        File receipts = new File("receipts.txt");
        try (PrintWriter pw = new PrintWriter(new FileWriter(receipts)))
        {
            for (Parcel parcel : Data.getProcessedParcels())
            {
                pw.println(parcel.toString());
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
