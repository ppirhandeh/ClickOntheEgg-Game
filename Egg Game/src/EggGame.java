/**
 * A clicking game which counts hits and missed shots and shows game over message if the missed
 * shots get to 5 nad for every hit, it adds a chick below the background.
 */
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import java.util.concurrent.ThreadLocalRandom;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import java.util.concurrent.ThreadLocalRandom;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Arc;
import javafx.geometry.Pos;
import javafx.scene.control.*; 
import javafx.scene.layout.*;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Font;
import javafx.stage.WindowEvent;



public class EggGame extends Application {
	private Stage stage;
	private BorderPane root;
	private Scene scene;
	private Pane mainPane;
	private Rectangle backGround;
	private Ellipse egg;
	private Button pause;
	private Button reset;
	private Text missed;
	private Text hits;
	private int missedN;
	private int hitsN;
	private Text gameOver;
	private EggRun animation;
	private int randomY;
	private int randomEntry;
	private int entryControl;
	private double xVelocity;
	private int pauseControl;
	private int gameOverC;
	private int chickN;
	
	@Override
	public void start(Stage primaryStage) {
		stage = new Stage();
		root =new BorderPane();
		root.setPrefSize(450,550);
		
		Scene scene = new Scene(root);
		
		mainPane = new Pane();
		mainPane.setPrefWidth(450);
		mainPane.setPrefHeight(550);
		root.setCenter(mainPane);
		
		missedN = 0;
		hitsN = 0;
		
		backGround = new Rectangle(0,0,450,510);
		backGround.setFill(Color.LIGHTBLUE);
		
		hits = new Text("");
		hits.setText("Hits: " +hitsN);
		hits.setX(20);
		hits.setY(20);
		missed = new Text("");
		missed.setText("Missed: "+ missedN);
		missed.setX(70);
		missed.setY(20);
		
		pause = new Button("Pause");
		pause.setLayoutX(325);
		pause.setLayoutY(514);
		
		reset = new Button("Reset");
		reset.setLayoutX(390);
		reset.setLayoutY(514);
		
		gameOver = new Text("");
		gameOver.setX(30);
		gameOver.setY(255);
		gameOver.setFont(Font.font("Arial",70));
		gameOver.setFill(Color.ORANGE);
		gameOver.setStrokeWidth(4);
		
		egg = new Ellipse(-50,200,20,30);
		egg.setFill(Color.WHITE);
		egg.setOnMouseClicked(new EggClick());
		
		Pause pauseB = new Pause();
		pause.setOnAction(pauseB);
		
		Reset resetB = new Reset();
		reset.setOnAction(resetB);
		
		randomY = 0;
		randomEntry = 0;
		entryControl = 0;
		xVelocity = 1;
		pauseControl = 0;
		gameOverC = 0;
		chickN = 0;
		
		
		
		
		animation = new EggRun();
		animation.start();
		mainPane.getChildren().addAll(backGround,missed,hits,pause,reset,gameOver,egg);
		
		
		
		
		stage.setResizable(false);
		stage.setTitle("Egg Game");
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	/*
	 * A class that creates an animation of a egg passing the screen and count the missed
	 * shots.
	 */
	private class EggRun extends AnimationTimer{
		@Override
		public void handle(long arg0) {
			if(entryControl == 0) {
				randomY = ThreadLocalRandom.current().nextInt(50, 370 + 1); 
				randomEntry = ThreadLocalRandom.current().nextInt(0, 4 + 1); 
				randomEntry *= 60;
				egg.setCenterY(randomY);
			}
			entryControl++;
			double x = egg.getCenterX();
			double y = egg.getCenterY();
			if(entryControl >= randomEntry) {
				x += xVelocity;
				egg.setCenterX(x);
			}
			if(x > 520) {
				missedN++;
				missed.setText("Missed: "+missedN);
				egg.setCenterX(-50);
				entryControl = 0;
			}
			if(missedN >= 5) {
				Rectangle cover = new Rectangle(0,400,450,110);
				cover.setFill(Color.LIGHTBLUE);
				chickN = 0;
				mainPane.getChildren().add(cover);
				animation.stop();
				gameOver.setText("Game Over!");
				gameOverC = 1;
			}
			
			
		}
	}
	
	/*
	 * A class which pause the game by hitting the pause button.
	 */
	private class Pause implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent e) {
			if(pauseControl == 0) {
				animation.stop();
				pauseControl = 1;
			}
			else if(pauseControl == 1 && gameOverC == 0) {
				animation.start();
				pauseControl = 0;
			}
		}
	}
	
	/*
	 * A class that reset the game.
	 */
	private class Reset implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent e) {
			Rectangle cover = new Rectangle(0,400,450,110);
			cover.setFill(Color.LIGHTBLUE);
			chickN = 0;
			mainPane.getChildren().add(cover);
			gameOver.setText("");
			animation.stop();
			hitsN = 0;
			missedN = 0;
			hits.setText("Hits: "+hitsN);
			missed.setText("Missed: "+missedN);
			egg.setCenterX(-50);
			entryControl = 0;
			pauseControl = 0;
			gameOverC = 0;
			xVelocity = 1;
			animation.start();
		}
		
	}
	
	/*
	 * A class that makes chick pictures each time the player hits the egg correctly.
	 */
	private class Chick{
		private Ellipse chickH;
		private Ellipse chickE1;
		private Ellipse chickE2;
		private Ellipse chickB;
		private Ellipse chickBud;
		
		/**
		 * The chick class constructor which makes chicks using an integer for chick's head 
		 * center.
		 * @param x is the center of chick's head.
		 */
		public Chick(int x) {
			chickH = new Ellipse(x,445,20,20);
			chickH.setFill(Color.YELLOW);
			chickH.setStrokeWidth(1.5);
			chickH.setStroke(Color.ORANGE);
			chickBud = new Ellipse(x,472,20,23);
			chickBud.setFill(Color.YELLOW);
			chickBud.setStrokeWidth(1.5);
			chickBud.setStroke(Color.ORANGE);
			chickE1 = new Ellipse(x-6,440,2,2);
			chickE1.setFill(Color.BLACK);
			chickE2 = new Ellipse(x+6,440,2,2);
			chickE2.setFill(Color.BLACK);
			chickB = new Ellipse(x,453,2.5,2.5);
			chickB.setFill(Color.ORANGE);
			mainPane.getChildren().addAll(chickBud,chickH,chickE1,chickE2,chickB);
		}
	}
	
	/*
	 * A click event handler for egg object.
	 */
	private class EggClick implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent e) {
			if(pauseControl == 0 && gameOverC == 0) {
					
				hitsN++;
				chickN++;
				hits.setText("Hits: "+hitsN);
				egg.setCenterX(-50);
				xVelocity += 0.3;
				if(chickN > 8) {
					chickN = 1;
					Rectangle cover = new Rectangle(0,400,450,110);
					cover.setFill(Color.LIGHTBLUE);
					mainPane.getChildren().add(cover);
				}
				Chick chick = new Chick((chickN *44)+5);
				
				entryControl = 0;
				
			}
			
		}
	}
	
	
}

