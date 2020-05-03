package windows;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;



public class InvalidArgumentErrorBox {

    public static void displayErrorBox(){
        final Stage popupWindow = new Stage();
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle("Error");
        popupWindow.setMinHeight(100);
        popupWindow.setMaxHeight(250);

        Label message = new Label("The files you entered have incorrect format. Please try again.");
        Button button = new Button("Okay");
        button.setOnAction(new EventHandler<ActionEvent>(){
        
            @Override
            public void handle(ActionEvent event) {
                popupWindow.close();
            }
        });
        
        VBox layout = new VBox(25);
        layout.getChildren().addAll(message, button);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        popupWindow.setScene(scene);
        popupWindow.show();

    }

	

}