import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.awt.*;
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
      grid.setVgap(10);

      ImageView[] holdView = new ImageView[Hand.MAX_CARDS];
      for (int i = 0; i < Hand.MAX_CARDS; i++)
      {
         holdView[i] = new ImageView("hold.jpg");
      }

      for (int i = 0; i < NUM_COLUMNS; i++) {
         ColumnConstraints colConst = new ColumnConstraints();
         colConst.setPercentWidth(100/ NUM_COLUMNS);
         grid.getColumnConstraints().add(colConst);
      }
      grid.setAlignment(Pos.TOP_CENTER);
      Image image;


      Hand startHand = new Hand();
      Deck startDeck = new Deck(false);
      Label creditsLabel = new Label ("CREDITS: " + game.getCredits() + "\n\n\n");
      creditsLabel.setFont(new Font(20 ));
      Label amountWonLabel = new Label("");
      amountWonLabel.setFont(new Font(20 ));
      Label[] holdLabel = new Label[Hand.MAX_CARDS];

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

         holdLabel[k] = new Label();
         cardButton[k] = new Button();
         cardButton[k].setGraphic(new ImageView(image));
         cardButton[k].setOnAction(new JoesEventHandler(game, grid, holdLabel, holdView, k));//CREDITS BUTTONS
         cardButton[k].setStyle("-fx-focus-color: transparent;");
         cardButton[k].setStyle("-fx-background-color: transparent;");
         int credit = k + 1;
         credits[k] = new Button(String.valueOf(credit));
         credits[k].setStyle("-fx-background-color: pink;");////////////////get this to work
         credits[k].setStyle("-fx-focus-color: firebrick");
         grid.add(cardButton[k], k, 0);
         grid.add(holdLabel[k], k, 1);
         grid.add(credits[k], k, 2);
         credits[k].setOnAction(new CreditEventsHandler(k+1, game));
      }



      ImageView oddsTable = new ImageView("poker odds.jpg");
      StackPane.setAlignment(oddsTable, Pos.BOTTOM_CENTER);
      oddsTable.setPreserveRatio(true);
      oddsTable.setFitWidth( 600 );
      oddsTable.setSmooth(true);
      root.getChildren().add(oddsTable);

      root.getChildren().add(creditsLabel);
      StackPane.setAlignment(creditsLabel, Pos.CENTER_LEFT);
      root.getChildren().add(amountWonLabel);
      StackPane.setAlignment(amountWonLabel, Pos.CENTER_RIGHT);

      root.getChildren().add(grid);
      StackPane.setAlignment(grid, Pos.TOP_CENTER);

      draw.setOnAction(event ->
      {
         Image image1;
         int amountWon;
         game.draw();
         grid.add(play, 5, 2);
         for (int k = 0; k < Hand.MAX_CARDS; k++)
         {

            game.playerHand.switchCard[k] = false;
            holdLabel[k].setText("      ");
            grid.add(credits[k], k, 2);
            image1 = new Image(CardImageUtils.getImage(game.playerHand.inspectCard(k)));
            cardImages[k] = new ImageView();
            cardButton[k].setGraphic(new ImageView(image1));
            grid.getChildren().remove(holdView[k]);
         }
         grid.getChildren().remove(draw);
         amountWon = game.evaluateHand();
         if (amountWon > 0)
         {
            amountWonLabel.setText("YOU WON: " + amountWon);
            Runnable runnable = (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.default");
            runnable.run();
         }
         creditsLabel.setText("CREDITS: " + game.getCredits() + "\n\n\n");
      });

      play.setOnAction(event ->
      {
         game.newHand();
         creditsLabel.setText("CREDITS: " + game.getCredits() + "\n\n\n");
         amountWonLabel.setText("");
         grid.add(draw, 5, 2);
         grid.getChildren().remove(play);
         for (int k = 0; k < Hand.MAX_CARDS; k++)
         {
            holdLabel[k].setText("        ");
            grid.getChildren().remove(credits[k]);
            grid.getChildren().remove(holdView[k]);
            Image img = new Image(CardImageUtils.getImage(game.playerHand.inspectCard(k)));
            cardImages[k] = new ImageView();
            //cardImages[k].setImage(img);
            cardButton[k].setGraphic(new ImageView(img));
         }
      });
      grid.add(play, 5, 2);
      Scene scene = new Scene(root, 750, 700);
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

   private static class JoesEventHandler implements EventHandler<ActionEvent>
   {
      final int cardNum;
      final Hand playerHand;
      final Label[] holdDrawLabel;
      final GuiGame game;
      final GridPane grid;
      final ImageView[] holdView;

      JoesEventHandler(GuiGame game, GridPane grid, Label[] holdDrawLabel, ImageView[] holdView, int cardNum)
      {
         this.cardNum = cardNum;
         this.playerHand = game.playerHand;
         this.holdDrawLabel = holdDrawLabel;
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
            holdDrawLabel[cardNum].setText("      HOLD      ");
            grid.add(holdView[cardNum], cardNum, 0);

         }
         else
         {
            holdDrawLabel[cardNum].setText("      ");
            grid.getChildren().remove(holdView[cardNum]);
         }
      }
   }
}
