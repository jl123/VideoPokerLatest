import javafx.geometry.HPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import static java.lang.Thread.sleep;

class VPController
{
   private final VPModel game;


   VPController(VPModel game)
   {
      this.game = game;
   }

   void startGame()
   {
      Hand startHand = VPModel.getStartHand();
      for (int k = 0; k < Hand.MAX_CARDS; k++)
      {
         VPView.cardImg = new Image(CardImageUtils.getImage(startHand.inspectCard(k)));
         VPView.cardImages[k].setImage(VPView.cardImg);
         VPView.cardButton[k].setGraphic(VPView.cardImages[k]);
         VPView.grid.add(VPView.betAmount[k], k, 2);
      }
   }

   void processHold(int cardNum)
   {
      game.playerHand.switchCard[cardNum] = !game.playerHand.switchCard[cardNum];
      if (!game.playerHand.switchCard[cardNum] && game.getDealt())
      {
         VPView.grid.add(VPView.holdView[cardNum], cardNum, 0);
         GridPane.setHalignment(VPView.holdView[cardNum], HPos.CENTER);
      }
      else
      {
         VPView.grid.getChildren().remove(VPView.holdView[cardNum]);
      }
   }
   void processDraw()
   {
      Image image1;
      int amountWon;
      game.draw();
      for (int k = 0; k < Hand.MAX_CARDS; k++)
      {

         game.playerHand.switchCard[k] = false;
         VPView.grid.add(VPView.betAmount[k], k, 2);
         image1 = new Image(CardImageUtils.getImage(game.playerHand.inspectCard(k)));
         VPView.cardImages[k].setFitHeight(image1.getHeight() * 2.0);
         VPView.cardImages[k].setPreserveRatio(true);
         VPView.cardButton[k].setGraphic(new ImageView(image1));
         VPView.grid.getChildren().remove(VPView.holdView[k]);
      }
      VPView.grid.getChildren().remove(VPView.drawButton);
      amountWon = game.evaluateHand();
      if (amountWon > 0)
      {
         VPView.amountWonLabel.setText("YOU WON: " + amountWon);

         String audio = "sound_win.wav";
         Media sound = new Media(getClass().getResource(audio).toExternalForm());
         MediaPlayer mediaPlayer = new MediaPlayer(sound);
         mediaPlayer.play();
      }
      try
      {
         sleep(300);
      }
      catch (InterruptedException e)
      {
         e.printStackTrace();
      }
      VPView.grid.add(VPView.play, 5, 2);
      VPView.creditsLabel.setText("CREDITS: " + game.getCredits() + "\n\n\n");
      if (!game.getHandVal().equals("LOSER"))
      {
         VPView.handRankLabel.setText(game.getHandVal());
      }

   }

   public void processHand()
   {

   }

   void processPlay()
   {

      game.newHand();
      VPView.creditsLabel.setText("CREDITS: " + game.getCredits() + "\n\n\n");
      VPView.amountWonLabel.setText("");
      VPView.handRankLabel.setText("");
      VPView.grid.add(VPView.drawButton, 5, 2);
      VPView.grid.getChildren().remove(VPView.play);
      for (int k = 0; k < Hand.MAX_CARDS; k++)
      {
         VPView.grid.getChildren().remove(VPView.betAmount[k]);
         VPView.grid.getChildren().remove(VPView.holdView[k]);
         Image img = new Image(CardImageUtils.getImage(game.playerHand.inspectCard(k)));
         VPView.cardButton[k].setGraphic(new javafx.scene.image.ImageView(img));

      }
   }

   void setBet(int bet)
   {
      game.setBet(bet);
   }

   int getBet()
   {
      return game.getBet();
   }

   int getCredits() { return game.getCredits(); }
}