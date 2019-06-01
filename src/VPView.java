import Hand.Hand;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.concurrent.Task;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Thread.sleep;

public class VPView extends Application
{
   private static final int BUTTON_W = 75;
   private static final int BUTTON_H = 75;
   private static final int BUTTON_PADDING = 15;
   private static final int LABEL_PADDING = 10;

   private static int winSleep = 300;

   private VPController controller;
   private StackPane root;
   private GridPane cardGrid;
   private GridPane oddsGrid;
   private ImageView[] cardImages;
   private ImageView[] holdImages;
   private Button[] holdButton;
   private Button[] cardButton;
   private Label creditsLabel;
   Label handRankLabel;
   private  Label gameOverLabel;
   private Label betLabel;
   private Label amountWonLabel;
   private Button betMax;
   private Button betPlusOne;
   private Button dealDrawButton;
   private boolean tableReset;

   public static void main(String[] args)
   {
      launch(args);
   }

   @Override
   public void start(Stage primaryStage)
   {
      tableReset = false;

      root = new StackPane();
      cardGrid = new GridPane();
      cardGrid.setPadding(new Insets(230, BUTTON_W + BUTTON_PADDING * 2, 0, 0));
      cardGrid.setAlignment(Pos.CENTER);

      controller = new VPController(new VPModel(), this);

      cardImages = new ImageView[VPController.getMaxCards()];
      cardButton = new Button[VPController.getMaxCards()];
      holdButton = new Button[VPController.getMaxCards()];
      holdImages = new ImageView[VPController.getMaxCards()];
      // BUTTONS
      dealDrawButton = new Button("DEAL\n------\nDRAW");
      dealDrawButton.setOnAction((event) -> controller.dealDraw());
      dealDrawButton.setId("bet-button");

      betMax = new Button("MAX\nBET");
      betMax.setOnAction(event -> controller.maxBet());
      betMax.setId("bet-button");

      betPlusOne = new Button("BET 1\nCOIN");
      betPlusOne.setOnAction((ActionEvent event) -> controller.incrementBet());
      betPlusOne.setId("bet-button");

      createHoldIcons();

      //credits display

      creditsLabel = new Label ("CREDITS: " + controller.getCredits() + "\n");
      creditsLabel.setId("info-label");

      //bet display
      betLabel = new Label ("WAGER: " + controller.getBet() + "\n");
      betLabel.setId("info-label");

      //amount won label
      amountWonLabel = new Label("");
      amountWonLabel.setId("info-label");
      amountWonLabel.setPadding(new Insets(20,6,6,6));

      GridPane infoGrid = new GridPane();
      infoGrid.add(creditsLabel,0,0);
      infoGrid.add(betLabel, 0, 1);
      infoGrid.setAlignment(Pos.BOTTOM_LEFT);
      root.getChildren().add(infoGrid);

      //oddsGrid setup
      oddsGrid = getOddsGrid();
      root.getChildren().add(oddsGrid);

      //game over display
      gameOverLabel = new Label("GAME OVER");
      animateFadeBlink(gameOverLabel);
      gameOverLabel.setId("end-game");
      root.getChildren().add(gameOverLabel);


      //hand rank display
      handRankLabel = new Label("");
      root.getChildren().add(handRankLabel);
      handRankLabel.setId("end-game");
      endGameLabelsOff();


      // add nodes to root
//      root.getChildren().add(creditsLabel);
      StackPane.setAlignment(creditsLabel, Pos.BOTTOM_LEFT);
      StackPane.setMargin(creditsLabel, new Insets(0, LABEL_PADDING * 6, LABEL_PADDING + creditsLabel.getHeight(), LABEL_PADDING));

//      root.getChildren().add(betLabel);
      StackPane.setAlignment(betLabel, Pos.BOTTOM_LEFT);
      StackPane.setMargin(betLabel, new Insets(0, 0, LABEL_PADDING, LABEL_PADDING));

      StackPane.setAlignment(handRankLabel, Pos.BOTTOM_CENTER);
      StackPane.setMargin(handRankLabel, new Insets(0, 50 + BUTTON_PADDING * 2, LABEL_PADDING * 2, 0));


      StackPane.setMargin(betLabel, new Insets(0, 0, LABEL_PADDING, LABEL_PADDING));
      StackPane.setMargin(gameOverLabel,new Insets(205, BUTTON_W + BUTTON_PADDING * 2, 0, 0));


      root.getChildren().add(amountWonLabel);
      StackPane.setAlignment(amountWonLabel, Pos.BOTTOM_RIGHT);
      StackPane.setMargin(amountWonLabel, new Insets(5, 25, BUTTON_PADDING, 0));

      root.getChildren().add(cardGrid);

      root.getChildren().add(dealDrawButton);
      StackPane.setAlignment(dealDrawButton,Pos.BOTTOM_RIGHT);
      StackPane.setMargin(dealDrawButton, new Insets(0, BUTTON_PADDING, BUTTON_PADDING *5, 0));

      root.getChildren().add(betMax);
      StackPane.setAlignment(betMax,Pos.BOTTOM_RIGHT);
      StackPane.setMargin(betMax, new Insets(0, BUTTON_PADDING, BUTTON_H + BUTTON_PADDING * 6, 0));

      root.getChildren().add(betPlusOne);
      StackPane.setAlignment(betPlusOne,Pos.BOTTOM_RIGHT);
      StackPane.setMargin(betPlusOne, new Insets(0,BUTTON_PADDING, BUTTON_H * 2 + BUTTON_PADDING * 7, 0));

      Scene scene = new Scene(root, 900, 700);
      scene.getStylesheets().addAll(getClass().getResource(
            "stylesheet.css"
      ).toExternalForm());

      primaryStage.setTitle("Video Poker!");
      primaryStage.setScene(scene);
      primaryStage.setResizable(false);
      primaryStage.show();

      resetTable();
   }

   protected void resetTable()
   {
      if (tableReset || controller.getInGameStatus()) {
         return; }

      endGameLabelsOff();
      disableHoldButtons(true);
      updateCards(VPController.getCardBacks());

      tableReset = true;

   }

   void endGameLabelsOn(String message)
   {
      gameOverLabel.setVisible(true);
      gameOverLabel.toFront();

      if (!message.equals(""))
      {
         handRankLabel.setText(message);
         handRankLabel.setVisible(true);
      }
   }

   void endGameLabelsOff()
   {
      gameOverLabel.setVisible(false);
      handRankLabel.setVisible(false);
   }

   private void createHoldIcons()
   {
      Image holdImg = new Image("hold.jpg");

      for (int i = 0; i < Hand.MAX_CARDS; i++)
      {
         HoldEventHandler holdHandle = new HoldEventHandler(i);
         cardButton[i] = new Button();
         cardButton[i].setOnAction(holdHandle);
         cardButton[i].setId("invis-button");

         GridPane.setMargin(cardButton[i], new Insets(0,3, 0, 5));
         GridPane.setHalignment(cardButton[i], HPos.CENTER);
         cardGrid.add(cardButton[i], i, 0);
//         cardGrid.getColumnConstraints().add(new ColumnConstraints(Pos.CENTER));

         cardImages[i] = new ImageView();
         cardGrid.add(cardImages[i], i, 0);
         StackPane.setMargin(cardImages[i], new Insets(0,5, 0, 5));

         holdImages[i] = new ImageView(holdImg);
         holdImages[i].setFitHeight(holdImg.getHeight() * 2);
         holdImages[i].setPreserveRatio(true);

         holdButton[i] = new Button();
         holdButton[i].setOnAction(holdHandle);
         holdButton[i].setId("invis-button");
         holdButton[i].setGraphic(holdImages[i]);
         GridPane.setHalignment(holdButton[i], HPos.CENTER);
      }
   }

   void updateCards(ArrayList<String> hand)
   {
      tableReset = false;
      double sizeMult = 2.0;

      for (int k = 0; k < hand.size(); k++)
      {
         Image cardImg = new Image(hand.get(k));
         cardImages[k].setFitHeight(cardImg.getHeight() * sizeMult);
         cardImages[k].setImage(cardImg);
         cardImages[k].setPreserveRatio(true);
         cardButton[k].setMinSize(cardImg.getWidth() * sizeMult, cardImg.getHeight() * sizeMult);
         cardButton[k].toFront();
      }
   }

   void disableHoldButtons(boolean disable)
   {
      Arrays.stream(cardButton).forEach(b -> b.setDisable(disable));

      if (disable)
      {
         Arrays.stream(holdButton).forEach(b -> cardGrid.getChildren().remove(b));
      }
   }

   void disableBetButtons(boolean disable)
   {
      betMax.setDisable(disable);
      betPlusOne.setDisable(disable);
   }

   class HoldEventHandler implements EventHandler<ActionEvent>
   {
      final int cardNum;


      HoldEventHandler(int cardNum)
      {
         this.cardNum = cardNum;
      }

      @Override
      public void handle(ActionEvent event)
      {
         if (controller.processHold(cardNum))
         {
            cardGrid.add(holdButton[cardNum], cardNum, 0);
         }
         else
         {
            cardGrid.getChildren().remove(holdButton[cardNum]);
         }
         new MediaPlayer(new Media(getClass()
               .getResource("sound_hold.wav").toExternalForm())).play();
      }
   }

   void updateWon(int won, int increment)
   {
      Task <Void> updateWonLabel = new Task<Void>()
      {
         @Override
         public Void call() throws Exception
         {
            updateMessage("");
            if (won == 0) { return null; }

            for (int i = 0; i <= won; i += increment)
            {
               updateMessage("YOU WON: " + i);

               if (i > 0)
               {
                  new MediaPlayer(new Media(getClass()
                        .getResource("sound_win.wav").toExternalForm())).play();
               }

               try
               {
                  sleep(winSleep);

               } catch (InterruptedException e)
               {
                  e.printStackTrace();
               }
            }
            return null;
         }
      };

      amountWonLabel.textProperty().bind(updateWonLabel.messageProperty());
      Thread threadWon = new Thread(updateWonLabel);
      threadWon.setDaemon(true);
      threadWon.start();
   }

   void updateCredits(int credits, int oldCredits, int increment, String sound)
   {
      Task <Void> updateCreditsLabel = new Task<Void>() {
         @Override
         public Void call() throws Exception
         {
            int oCredits = oldCredits;
            int num  = (credits > oCredits) ? increment : -increment;

            updateMessage("CREDITS: " + oCredits + "\n");

            while (oCredits != credits)
            {
               oCredits += num;
               updateMessage("CREDITS: " + oCredits + "\n");

               if (credits >= oldCredits)
               {
                  new MediaPlayer(new Media(getClass()
                        .getResource("sound_win.wav").toExternalForm())).play();
               }

               try
               {
                  sleep(winSleep);

               } catch (InterruptedException e)
               {
                  e.printStackTrace();
               }
            }
            return null;
         }};

      creditsLabel.textProperty().bind(updateCreditsLabel.messageProperty());
      Thread threadCredits = new Thread(updateCreditsLabel);
      threadCredits.setDaemon(true);
      threadCredits.start();
   }

   void updateBet(int bet)
   {
      Task<Void> updateBetLabel = new Task<Void>()
      {
         @Override
         public Void call() throws Exception
         {
            updateMessage("WAGER: " + bet + "\n");

            new MediaPlayer(new Media(getClass()
                  .getResource("bet_sound.wav").toExternalForm())).play();

            try
            {
               sleep(winSleep);

            } catch (InterruptedException e)
            {
               e.printStackTrace();
            }
            return null;
         }};

      betLabel.textProperty().bind(updateBetLabel.messageProperty());
      Thread threadCredits = new Thread(updateBetLabel);
      threadCredits.setDaemon(true);
      threadCredits.start();
   }

   private GridPane getOddsGrid()
   {
      oddsGrid = new GridPane();

//      oddsGrid.setId("odds-grid");
//      oddsGrid.getStyleClass().add("odds-grid");
      oddsGrid.setAlignment(Pos.TOP_CENTER);
//      StackPane.setAlignment(oddsGrid, Pos.TOP_CENTER);
      //i is row
      ArrayList<ArrayList<String>> gridVals = controller.getWinTable();
      for (int i = 0; i < gridVals.size(); i++)
      {
         ArrayList<String> addList = gridVals.get(i);
         int row = i;

         for (int j = 0; j < addList.size(); j++)
         {
            int column = j;
            Pane pane = new Pane();

            oddsGrid.add(pane,column,row);
            Label gridLab = new Label(addList.get(column));
            pane.getChildren().add(gridLab);
            gridLab.setId("win-table-text");
            String addProp = column == 0 ? "wintable-handvals" : "wintable-win";
            gridLab.getStyleClass().add(addProp);
            pane.getStyleClass().add("odds-grid-cell");
            if (row == 0)
            {
               pane.getStyleClass().add("first-row");
            }
            if (row == gridVals.size() - 1)
            {
               pane.getStyleClass().add("last-row");
            }
         }
      }
      return oddsGrid;
   }

   private void animateFadeBlink(Label label)
   {
      FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), label);
      fadeTransition.setFromValue(100);
      fadeTransition.setToValue(0);
      fadeTransition.setByValue(0.0);
      fadeTransition.setAutoReverse(true);
      fadeTransition.setCycleCount(Animation.INDEFINITE);
      fadeTransition.play();
   }
}
