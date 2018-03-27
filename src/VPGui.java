import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class VPGui extends Application {

   @Override
   public void start(Stage primaryStage) {

      Deck deck = new Deck(true);
      Hand playerHand = new Hand();
      ImageView cardImages[] = new ImageView[Hand.MAX_CARDS];
      Button btn[] = new Button[Hand.MAX_CARDS];
      Image image;
      GridPane root = new GridPane();

      for (int k = 0; k < Hand.MAX_CARDS; k++)
      {
         playerHand.takeCard(deck.dealCard());

         image = new Image(CardImageUtils.getImage(playerHand.inspectCard(k)));
         cardImages[k] = new ImageView();
         cardImages[k].setImage(image);

         btn[k] = new Button();
         btn[k].setGraphic(new ImageView(image));
         btn[k].setOnAction(new JoesEventHandler(playerHand, k));

         root.add(btn[k], k, 0);
         //root.add(cardImages[k], k, 1);
      }

      Button butt = new Button("DRAW");
      butt.setOnAction(new EventHandler<ActionEvent>()
      {
         @Override
         public void handle(ActionEvent event)
         {
            System.out.println("AAAAA");
         }
      });

      root.add(butt,0,2);
      Scene scene = new Scene(root, 900, 1000);

      primaryStage.setTitle("Video Poker!");
      primaryStage.setScene(scene);
      primaryStage.show();

   }
   public static void main(String[] args) {
      launch(args);
   }

   private static class JoesEventHandler implements EventHandler<ActionEvent>
   {
      int cardNum;
      Hand playerHand;
      JoesEventHandler(Hand playerHand, int i)
      {
         cardNum = i;
         this.playerHand = playerHand;
      }

      @Override
      public void handle(ActionEvent event)
      {
         boolean currentValue = playerHand.switchCard[cardNum];
         playerHand.switchCard[cardNum] = !currentValue;
      }
   }

}
