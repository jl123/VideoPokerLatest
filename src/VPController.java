import javafx.geometry.HPos;
import javafx.scene.image.Image;
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
         VPView.cardImages[k].setFitHeight(VPView.cardImg.getHeight() * 1.25);
         VPView.cardImages[k].setPreserveRatio(true);
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
      int amountWon;
      game.draw();
      for (int k = 0; k < Hand.MAX_CARDS; k++)
      {

         game.playerHand.switchCard[k] = false;
         VPView.grid.add(VPView.betAmount[k], k, 2);
         VPView.cardImg = new Image(CardImageUtils.getImage(game.playerHand.inspectCard(k)));
         VPView.cardImages[k].setImage(VPView.cardImg);
         VPView.cardImages[k].setFitHeight(VPView.cardImg.getHeight() * 1.25);
         VPView.cardImages[k].setPreserveRatio(true);
         VPView.cardButton[k].setGraphic(VPView.cardImages[k]);
         VPView.grid.getChildren().remove(VPView.holdView[k]);
      }
      VPView.grid.getChildren().remove(VPView.drawButton);
      amountWon = game.evaluateHand();
      if (amountWon > 0)
      {
         VPView.amountWonLabel.setText("YOU WON: " + amountWon);

         MediaPlayer mediaPlayer = new MediaPlayer(new Media(getClass()
               .getResource("sound_win.wav").toExternalForm()));
         mediaPlayer.play();
      }
      try
      {
         sleep(500);
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
      try
      {
         sleep(500);
      } catch (InterruptedException e)
      {
         e.printStackTrace();
      }
      VPView.grid.add(VPView.drawButton, 5, 2);
      VPView.grid.getChildren().remove(VPView.play);
      for (int k = 0; k < Hand.MAX_CARDS; k++)
      {
         VPView.grid.getChildren().remove(VPView.betAmount[k]);
         VPView.grid.getChildren().remove(VPView.holdView[k]);
         VPView.cardImg = new Image(CardImageUtils.getImage(game.playerHand.inspectCard(k)));
         VPView.cardImages[k].setImage(VPView.cardImg);
         VPView.cardImages[k].setImage(VPView.cardImg);
         VPView.cardImages[k].setFitHeight(VPView.cardImg.getHeight() * 1.25);
         VPView.cardImages[k].setPreserveRatio(true);
         VPView.cardButton[k].setGraphic(VPView.cardImages[k]);
      }
   }

   void setBet(int bet) { game.setBet(bet); }

   int getBet() { return game.getBet(); }

   int getCredits() { return game.getCredits(); }
}