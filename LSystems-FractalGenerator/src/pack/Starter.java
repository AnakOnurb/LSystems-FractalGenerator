package pack;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Starter extends Application
{
	public static void main(String[] args) 
	{
		launch(args);
	}//main
	
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		Button btnRun = new Button();
		btnRun.setText("Generate Image");
		btnRun.setPrefSize(150, 30);
		btnRun.setLayoutX(75);
		btnRun.setLayoutY(25);
		
		Pane root = new Pane();
		root.getChildren().add(btnRun);
		
		Scene scene = new Scene(root, 310, 80);
		
		primaryStage.setTitle("Fractals");
		primaryStage.setScene(scene);
		primaryStage.show();
			
		btnRun.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent event)
			{
				Processor.start();
				JOptionPane.showMessageDialog(null, "Arquivo criado!");
			}
		});
	}
}