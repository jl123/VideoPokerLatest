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

import javax.swing.*;
import java.util.concurrent.atomic.AtomicInteger;

public class VPGui extends Application {
   static int betInt = 5;
   static int credits = 1000;

   @Override
   public void start(Stage primaryStage) {
      GuiGame game = new GuiGame();
      boolean gameStart;

      ImageView cardImages[] = new ImageView[Hand.MAX_CARDS];
      Button cardButton[] = new Button[Hand.MAX_CARDS];
      Button credits[] = new Button[5];
      StackPane root = new StackPane();
      GridPane grid = new GridPane();
      Image image;

      Label[] holdLabel = new Label[Hand.MAX_CARDS];
      System.out.println("game credts" + game.getCredits());
      for (int k = 0; k < Hand.MAX_CARDS; k++)
      {
         game.newHand();

         image = new Image(CardImageUtils.getImage(game.playerHand.inspectCard(k)));
         cardImages[k] = new ImageView();
         cardImages[k].setImage(image);


         holdLabel[k] = new Label();
         cardButton[k] = new Button();
         cardButton[k].setGraphic(new ImageView(image));
         cardButton[k].setOnAction(new JoesEventHandler(game, holdLabel, k));

         Button play = new Button("PLAY");

         //CREDITS BUTTONS
         int credit = k + 1;
         credits[k] = new Button(String.valueOf(credit));
         grid.add(cardButton[k], k, 0);
         grid.add(holdLabel[k], k, 1);
         grid.add(credits[k], k, 2);
         credits[k].setOnAction(new CreditEventsHandler(k+1, game));
      }
      Button play = new Button("PLAY");
      Button draw = new Button("DRAW");
      //grid.add(draw, 0, 6);
      draw.setOnAction(new EventHandler<ActionEvent>()
      {
         @Override
         public void handle(ActionEvent event)
         {
            Image image;
            System.out.println(game.playerHand.toString());
            game.draw();
            System.out.println(game.playerHand.toString());

            grid.add(play, 5, 2);
            for (int k = 0; k < Hand.MAX_CARDS; k++)
            {

               game.playerHand.switchCard[k] = false;
               holdLabel[k].setText("      ");
               grid.add(credits[k], k, 2);
               image = new Image(CardImageUtils.getImage(game.playerHand.inspectCard(k)));
               cardImages[k] = new ImageView();
               cardImages[k].setImage(image);
               //btn[k] = new Button();
               cardButton[k].setGraphic(new ImageView(image));
            }
            grid.getChildren().remove(draw);
            game.evaluateHand();
            System.out.println(game.getCredits());
         }
      });

      Text oddsTable = new Text(HandEvaluator.oddsTable());
      root.getChildren().add(oddsTable);

      //GuiGame.newGame(grid);

      root.getChildren().add(grid);

      play.setOnAction(new EventHandler<ActionEvent>()
      {
         @Override
         public void handle(ActionEvent event)
         {
            game.newHand();
            grid.add(draw, 0, 6);
            grid.getChildren().remove(play);
            for (int k = 0; k < Hand.MAX_CARDS; k++)
            {
               holdLabel[k].setText("        ");
               grid.getChildren().remove(credits[k]);
               Image img = new Image(CardImageUtils.getImage(game.playerHand.inspectCard(k)));
               cardImages[k] = new ImageView();
               cardImages[k].setImage(img);
               //btn[k] = new Button();
               cardButton[k].setGraphic(new ImageView(img));
               //System.out.println(img.toSring());
               //grid.getChildren().remove(draw);
               //System.out.println(HandEvaluator.getHandVal(game.playerHand));
            }

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
      //StringProperty labelProperties[];
      JoesEventHandler(GuiGame game, Label holdDrawLabel[], int cardNum)
      {
         this.cardNum = cardNum;
         this.playerHand = game.playerHand;
         this.holdDrawLabel = holdDrawLabel;


      }

      @Override
      public void handle(ActionEvent event)
      {
         System.out.println("old val: " + playerHand.switchCard[cardNum]);
         playerHand.switchCard[cardNum] = !playerHand.switchCard[cardNum];
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
