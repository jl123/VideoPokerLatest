import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class VPView extends Application {

   private static VPController controller;
   static GridPane grid;
   static ImageView[] cardImages;
   static ImageView[] holdView;
   static Button[] cardButton;
   static Button[] betAmount;
   static Button play;
   static Button drawButton;
   static Image cardImg;
   static Label creditsLabel;
   static Label handRankLabel;
   static Label amountWonLabel;

   public static void main(String[] args)
   {
      VPModel game = new VPModel();
      controller = new VPController(game);
      launch(args);
   }

   @Override
   public void start(Stage primaryStage) {
      final int NUM_COLUMNS = Hand.MAX_CARDS + 1 ;
      StackPane root = new StackPane();
      grid = new GridPane(); //grid for cards and buttons
      cardImages = new ImageView[Hand.MAX_CARDS];
      holdView = new ImageView[Hand.MAX_CARDS];
      cardButton = new Button[Hand.MAX_CARDS];
      betAmount = new Button[5];

      play = new Button("PLAY");
      drawButton = new Button("DRAW");

      //noinspection SpellCheckingInspection
      root.setStyle("-fx-background: darkblue;");
      //grid setup
      //set true for testing
      grid.setGridLinesVisible(false);
      grid.setHgap(30);
      grid.setVgap(20);
      grid.setPadding(new Insets(0, 0, 30, 0));
      //Cards
      for (int i = 0; i < Hand.MAX_CARDS; i++)
      {
         cardButton[i] = new Button();
         cardButton[i].setOnAction(new HoldEventHandler(i));
         cardButton[i].setStyle("-fx-focus-color: transparent;");
         cardButton[i].setStyle("-fx-background-color: transparent;");
         GridPane.setHalignment(cardButton[i], HPos.CENTER);
         grid.add(cardButton[i], i, 0);

         cardImages[i] = new ImageView();
         holdView[i] = new ImageView("hold.jpg");
      }

      ColumnConstraints colConst = new ColumnConstraints();
      colConst.setPercentWidth(100 / NUM_COLUMNS);
      grid.getColumnConstraints().add(colConst);
      grid.setAlignment(Pos.BOTTOM_CENTER);
      //credits display
      creditsLabel = new Label ("CREDITS: " + controller.getCredits() + "\n\n\n");
      creditsLabel.setFont(new Font(20 ));

      handRankLabel = new Label("");
      handRankLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
      handRankLabel.setTextFill(Color.valueOf("red"));

      amountWonLabel = new Label("");
      amountWonLabel.setFont(new Font(20 ));

      for (int k = 0; k < HandEvaluator.MAX_BET; k++)
      {
         int credit = k + 1;
         betAmount[k] = new Button(String.valueOf(credit));
         betAmount[k].setStyle("-fx-background-color: pink;");////////////////get this to work
         betAmount[k].setStyle("-fx-focus-color: firebrick");
         betAmount[k].setOnAction(new CreditEventsHandler(k + 1));
      }

      ImageView oddsTable = new ImageView("poker odds.jpg");
      StackPane.setAlignment(oddsTable, Pos.TOP_CENTER);
      oddsTable.setPreserveRatio(true);
      oddsTable.setFitWidth( 600 );
      oddsTable.setSmooth(true);
      root.getChildren().add(oddsTable);

      root.getChildren().add(creditsLabel);
      StackPane.setAlignment(creditsLabel, Pos.CENTER_LEFT);

      root.getChildren().add(handRankLabel);
      StackPane.setAlignment(handRankLabel, Pos.CENTER);

      root.getChildren().add(amountWonLabel);
      StackPane.setAlignment(amountWonLabel, Pos.CENTER_RIGHT);

      root.getChildren().add(grid);
      StackPane.setAlignment(grid, Pos.TOP_CENTER);

      grid.add(play, 5, 2);
      Scene scene = new Scene(root, 900, 700);
      primaryStage.setTitle("Video Poker!");
      primaryStage.setScene(scene);
      primaryStage.setResizable(false);
      primaryStage.show();

      drawButton.setOnAction(event -> controller.processDraw());

      play.setOnAction((ActionEvent event) -> controller.processPlay());
      controller.startGame();
   }


   static class CreditEventsHandler implements EventHandler<ActionEvent>
   {
      final int betAmount;

      CreditEventsHandler(int betAmount)
      {
         this.betAmount = betAmount;
      }

      @Override
      public void handle(ActionEvent event)
      {
         controller.setBet(betAmount);
      }

   }

   static class HoldEventHandler implements EventHandler<ActionEvent>
   {
      final int cardNum;


      HoldEventHandler(int cardNum)
      {
         this.cardNum = cardNum;
      }

      @Override
      public void handle(ActionEvent event)
      {
         controller.processHold(cardNum);

         MediaPlayer mediaPlayer = new MediaPlayer(new Media(getClass()
               .getResource("sound_hold.wav").toExternalForm()));
         mediaPlayer.play();
      }
   }
}
