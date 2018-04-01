import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.StringPropertyBase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

import java.util.concurrent.atomic.AtomicInteger;

public class VPGui extends Application {
   static int betInt = 5;
   static int credits = 1000;

   @Override
   public void start(Stage primaryStage) {
      Deck deck = new Deck(true);
      Hand playerHand = new Hand();
      ImageView cardImages[] = new ImageView[Hand.MAX_CARDS];
      Button cardButton[] = new Button[Hand.MAX_CARDS];
      Button credits[] = new Button[5];

      Image image;
      StackPane root = new StackPane();
      GridPane grid = new GridPane();
      Text oddsTable = new Text(HandEvaluator.oddsTable());
      Label[] holdDrawLabel = new Label[Hand.MAX_CARDS];

      root.getChildren().add(oddsTable);
      for (int k = 0; k < Hand.MAX_CARDS; k++)
      {
         playerHand.takeCard(deck.dealCard());

         image = new Image(CardImageUtils.getImage(playerHand.inspectCard(k)));
         cardImages[k] = new ImageView();
         cardImages[k].setImage(image);

         holdDrawLabel[k] = new Label();
         //System.out.println(holdDrawLabel[k].toString());
         cardButton[k] = new Button();
         cardButton[k].setGraphic(new ImageView(image));
         cardButton[k].setOnAction(new JoesEventHandler(playerHand, holdDrawLabel, k));

         //CREDITS BUTTONS
         int credit = k + 1;
         credits[k] = new Button(String.valueOf(credit));
         grid.add(cardButton[k], k, 0);
         grid.add(holdDrawLabel[k], k, 1);
         grid.add(credits[k], k, 2);
         credits[k].setOnAction(new CreditEventsHandler(k+1));
      }
      root.getChildren().add(grid);
      Button play = new Button("PLAY");
      grid.add(play, 5, 2);
      Button draw = new Button("DRAW");
      draw.setOnAction(new EventHandler<ActionEvent>()
      {
         @Override
         public void handle(ActionEvent event)
         {
            Image image;
            playerHand.draw(deck);
            System.out.println(playerHand.toString());
            for (int k = 0; k < Hand.MAX_CARDS; k++)
            {
               image = new Image(CardImageUtils.getImage(playerHand.inspectCard(k)));
               //cardImages[k] = new ImageView();
               cardImages[k].setImage(image);
               //btn[k] = new Button();
               cardButton[k].setGraphic(new ImageView(image));
               System.out.println(image.toString());
               grid.getChildren().remove(draw);
            }

         }
      });

      grid.add(draw,0,3);
      Scene scene = new Scene(root, 900, 1000);

      primaryStage.setTitle("Video Poker!");
      primaryStage.setScene(scene);
      primaryStage.show();

   }
   public static void main(String[] args) {
      launch(args);
   }

   private static class CreditEventsHandler implements EventHandler<ActionEvent>
   {
      int credit;
      CreditEventsHandler(int credit)
      {
         this.credit = credit;
      }

      @Override
      public void handle(ActionEvent event)
      {
         betInt = credit;
         System.out.println(betInt);
      }

   }
   private static class JoesEventHandler implements EventHandler<ActionEvent>
   {
      int cardNum;
      Hand playerHand;
      Label holdDrawLabel[];
      StringProperty labelProperties[];
      JoesEventHandler(Hand playerHand, Label holdDrawLabel[], int cardNum)
      {
         this.cardNum = cardNum;
         this.playerHand = playerHand;
         this.holdDrawLabel = holdDrawLabel;


      }

      @Override
      public void handle(ActionEvent event)
      {
         System.out.println("old val: " + playerHand.switchCard[cardNum]);
         boolean currentValue = playerHand.switchCard[cardNum];
         playerHand.switchCard[cardNum] = !currentValue;
         System.out.println("new val: " + playerHand.switchCard[cardNum]);
         if (!playerHand.switchCard[cardNum])
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
