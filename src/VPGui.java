import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.StringPropertyBase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.text.Text;

public class VPGui extends Application {


   @Override
   public void start(Stage primaryStage) {
      GuiGame game = new GuiGame();
      boolean gameStart;

      ImageView cardImages[] = new ImageView[Hand.MAX_CARDS];
      Button cardButton[] = new Button[Hand.MAX_CARDS];
      Button credits[] = new Button[5];
      Button play = new Button("PLAY");
      Button draw = new Button("DRAW");
      StackPane root = new StackPane();
      root.setMaxHeight(600);
      root.setStyle("-fx-background: darkblue;");
      GridPane grid = new GridPane();

      grid.setHgap(15);
      grid.setVgap(15);
      Image image;


      Hand startHand = new Hand();
      Deck startDeck = new Deck(false);
      Label creditsLabel = new Label ("CREDITS: " + game.getCredits());
      Label amountWonLabel = new Label("");
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
         cardButton[k].setOnAction(new JoesEventHandler(game, holdLabel, k));//CREDITS BUTTONS
         cardButton[k].setStyle("-fx-focus-color: transparent;");
         cardButton[k].setStyle("-fx-background-color: transparent;");
         int credit = k + 1;
         credits[k] = new Button(String.valueOf(credit));
         credits[k].setStyle("-fx-background-color: yellow;");
         credits[k].setStyle("-fx-focus-color: firebrick");
         grid.add(cardButton[k], k, 0);
         grid.add(holdLabel[k], k, 1);
         grid.add(credits[k], k, 2);
         credits[k].setOnAction(new CreditEventsHandler(k+1, game));
      }

      grid.add(creditsLabel, 0, 9);
      grid.add(amountWonLabel,0,8);
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
            //cardImages[k].setImage(image1);
            //cardImages[k].setFitWidth(40);

            //btn[k] = new Button();
            cardButton[k].setGraphic(new ImageView(image1));
            //cardButton[k].setMaxWidth(40);
         }
         grid.getChildren().remove(draw);
         amountWon = game.evaluateHand();
         if (amountWon > 0)
         {
            amountWonLabel.setText("YOU WON: " + amountWon);
         }
         creditsLabel.setText("CREDITS: " + game.getCredits());
      });

      ImageView oddsTable = new ImageView("poker odds.jpg");
      oddsTable.setPreserveRatio(true);
      oddsTable.setFitWidth( 600 );
      root.getChildren().add(oddsTable);
      root.getChildren().add(grid);


      play.setOnAction(event ->
      {
         game.newHand();
         creditsLabel.setText("CREDITS: " + game.getCredits());
         amountWonLabel.setText("");
         grid.add(draw, 0, 6);
         grid.getChildren().remove(play);
         for (int k = 0; k < Hand.MAX_CARDS; k++)
         {
            holdLabel[k].setText("        ");
            grid.getChildren().remove(credits[k]);
            Image img = new Image(CardImageUtils.getImage(game.playerHand.inspectCard(k)));
            cardImages[k] = new ImageView();
            cardImages[k].setImage(img);
            cardButton[k].setGraphic(new ImageView(img));
         }
      });
      grid.add(play, 5, 2);
      Scene scene = new Scene(root, 900, 1000);
      primaryStage.setTitle("Video Poker!");
      primaryStage.setScene(scene);
      primaryStage.show();

   }

   public static void main(String[] args)
   {
      launch(args);
   }

   private static class CreditEventsHandler implements EventHandler<ActionEvent>
   {
      int credit;
      GuiGame game;
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
      int cardNum;
      Hand playerHand;
      Label holdDrawLabel[];
      GuiGame game;
      JoesEventHandler(GuiGame game, Label holdDrawLabel[], int cardNum)
      {
         this.cardNum = cardNum;
         this.playerHand = game.playerHand;
         this.holdDrawLabel = holdDrawLabel;
         this.game = game;
      }

      @Override
      public void handle(ActionEvent event)
      {
         playerHand.switchCard[cardNum] = !playerHand.switchCard[cardNum];
         if (!playerHand.switchCard[cardNum] && game.getDealt())
         {
            holdDrawLabel[cardNum].setText("    HOLD     ");
         }
         else
         {
            holdDrawLabel[cardNum].setText("      ");
         }
      }
   }
}
