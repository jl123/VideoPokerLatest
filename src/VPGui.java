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

import static java.lang.Thread.sleep;

public class VPGui extends Application {


   @Override
   public void start(Stage primaryStage) {
      final int NUM_COLUMNS = 6 ;

      GuiGame game = new GuiGame();
      ImageView cardImages[] = new ImageView[Hand.MAX_CARDS];
      Button cardButton[] = new Button[Hand.MAX_CARDS];
      Button credits[] = new Button[5];
      Button play = new Button("PLAY");
      Button draw = new Button("DRAW");
      StackPane root = new StackPane();
      //noinspection SpellCheckingInspection
      root.setStyle("-fx-background: darkblue;");
      GridPane grid = new GridPane();
      //set true for testing
      grid.setGridLinesVisible(false);
      grid.setHgap(30);
      grid.setVgap(20);
      grid.setPadding(new Insets(0, 0, 30, 0));

      ImageView[] holdView = new ImageView[Hand.MAX_CARDS];
      for (int i = 0; i < Hand.MAX_CARDS; i++)
      {
         holdView[i] = new ImageView("hold.jpg");
      }

      for (int i = 0; i < NUM_COLUMNS; i++) {
         ColumnConstraints colConst = new ColumnConstraints();
         colConst.setPercentWidth(100 / NUM_COLUMNS);
         grid.getColumnConstraints().add(colConst);
      }
      grid.setAlignment(Pos.BOTTOM_CENTER);
      Image image;


      Hand startHand = new Hand();
      Deck startDeck = new Deck(false);

      Label creditsLabel = new Label ("CREDITS: " + game.getCredits() + "\n\n\n");
      creditsLabel.setFont(new Font(20 ));


      Label handRankLabel = new Label("");
      handRankLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
      handRankLabel.setTextFill(Color.valueOf("red"));

      Label amountWonLabel = new Label("");
      amountWonLabel.setFont(new Font(20 ));

      for (int k = 0; k < Hand.MAX_CARDS; k++)
      {
         try
         {
            startHand.takeCard(startDeck.dealCard());
         }
         catch (OutOfCardsException e)
         {
            System.out.println(e.getMessage());
         }
         image = new Image(CardImageUtils.getImage(startHand.inspectCard(k)));

         cardImages[k] = new ImageView();
         cardImages[k].setImage(image);
         //holdLabel[k] = new Label();
         cardButton[k] = new Button();
         cardButton[k].setGraphic(new ImageView(image));
         cardButton[k].setOnAction(new HoldEventHandler(game, grid, holdView, k));//CREDITS BUTTONS
         cardButton[k].setStyle("-fx-focus-color: transparent;");
         cardButton[k].setStyle("-fx-background-color: transparent;");
         GridPane.setHalignment(cardButton[k], HPos.CENTER);
         int credit = k + 1;
         credits[k] = new Button(String.valueOf(credit));
         credits[k].setStyle("-fx-background-color: pink;");////////////////get this to work
         credits[k].setStyle("-fx-focus-color: firebrick");
         grid.add(cardButton[k], k, 0);
         //grid.add(holdLabel[k], k, 1);
         grid.add(credits[k], k, 2);
         credits[k].setOnAction(new CreditEventsHandler(k+1, game));
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
      draw.setOnAction(event ->
      {
         Image image1;
         int amountWon;
         game.draw();
         for (int k = 0; k < Hand.MAX_CARDS; k++)
         {

            game.playerHand.switchCard[k] = false;
            //holdLabel[k].setText("      ");
            grid.add(credits[k], k, 2);
            image1 = new Image(CardImageUtils.getImage(game.playerHand.inspectCard(k)));
            cardImages[k] = new ImageView();
            cardImages[k].setFitHeight(image1.getHeight() * 2.0);
            cardImages[k].setPreserveRatio(true);
            cardButton[k].setGraphic(new ImageView(image1));
            grid.getChildren().remove(holdView[k]);
         }
         grid.getChildren().remove(draw);
         amountWon = game.evaluateHand();
         if (amountWon > 0)
         {
            amountWonLabel.setText("YOU WON: " + amountWon);
            String audio = "sound_win.wav";     // For example
            Media sound = new Media(getClass().getResource(audio).toExternalForm());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
         }
         try
         {
            sleep(300);
         } catch (InterruptedException e)
         {
            e.printStackTrace();
         }
         grid.add(play, 5, 2);
         creditsLabel.setText("CREDITS: " + game.getCredits() + "\n\n\n");
         if (!game.getHandVal().equals("LOSER"))
         {
            handRankLabel.setText(game.getHandVal());
         }
      });

      play.setOnAction(event ->
      {
         game.newHand();
         creditsLabel.setText("CREDITS: " + game.getCredits() + "\n\n\n");
         amountWonLabel.setText("");
         handRankLabel.setText("");
         grid.add(draw, 5, 2);
         grid.getChildren().remove(play);
         for (int k = 0; k < Hand.MAX_CARDS; k++)
         {
            //holdLabel[k].setText("        ");
            grid.getChildren().remove(credits[k]);
            grid.getChildren().remove(holdView[k]);
            Image img = new Image(CardImageUtils.getImage(game.playerHand.inspectCard(k)));
            cardImages[k] = new ImageView();
            //cardImages[k].setImage(img);
            cardButton[k].setGraphic(new ImageView(img));
         }
      });
      grid.add(play, 5, 2);
      Scene scene = new Scene(root, 900, 700);
      primaryStage.setTitle("Video Poker!");
      primaryStage.setScene(scene);
      primaryStage.setResizable(false);
      primaryStage.show();

   }
   public static void main(String[] args)
   {
      launch(args);
   }

   private static class CreditEventsHandler implements EventHandler<ActionEvent>
   {
      final int credit;
      final GuiGame game;
      CreditEventsHandler(int credit, GuiGame game)
      {
         this.credit = credit;
         this.game = game;
      }

      @Override
      public void handle(ActionEvent event)
      {
         game.setBet(credit);
      }

   }

   private static class HoldEventHandler implements EventHandler<ActionEvent>
   {
      final int cardNum;
      final Hand playerHand;
      final GuiGame game;
      final GridPane grid;
      final ImageView[] holdView;


      HoldEventHandler(GuiGame game, GridPane grid, ImageView[] holdView, int cardNum)
      {
         this.cardNum = cardNum;
         this.playerHand = game.playerHand;
         this.game = game;
         this.grid = grid;
         this.holdView = holdView;
      }

      @Override
      public void handle(ActionEvent event)
      {
         playerHand.switchCard[cardNum] = !playerHand.switchCard[cardNum];
         if (!playerHand.switchCard[cardNum] && game.getDealt())
         {
            //holdDrawLabel[cardNum].setText("      HOLD      ");
            grid.add(holdView[cardNum], cardNum, 0);
            GridPane.setHalignment(holdView[cardNum], HPos.CENTER);

         }
         else
         {
            //holdDrawLabel[cardNum].setText("      ");
            grid.getChildren().remove(holdView[cardNum]);
         }
      }
   }
}
