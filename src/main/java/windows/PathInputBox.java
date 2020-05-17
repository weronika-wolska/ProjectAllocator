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
import org.apache.poi.ss.formula.functions.T;


public class PathInputBox {
    private static String pathString = "";

    public String getPath(){
        return this.pathString;
    }

    public static String displayBox(){
        final Stage popupWindow = new Stage();
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle("Choose File Localtion");
        popupWindow.setMinHeight(100);
        popupWindow.setMaxHeight(250);

        Label message = new Label("Please enter the path where you want the file to be saved.");
        Label information = new Label("IMPORTANT: Please make sure the file paths you enter ar in the format C/Users/mary/...");
        TextField path = new TextField();
        path.setText("src/main/templates");
        HBox fileName = new HBox(10);
        Label name = new Label("File Name: ");
        TextField getName = new TextField("candidateSolution");
        fileName.getChildren().addAll(name, getName);
        Button button = new Button("Okay");
        button.setOnAction(new EventHandler<ActionEvent>(){
        
            @Override
            public void handle(ActionEvent event) {
                pathString = path.getText()+"/"+getName.getText()+".xlsx";
                popupWindow.close();
            }
        });

        popupWindow.setOnCloseRequest(event -> {
            //return pathString;
        });
        
        VBox layout = new VBox(25);
        layout.getChildren().addAll(message, information,path, fileName, button);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        popupWindow.setScene(scene);
        popupWindow.show();
        return pathString;

    }

}