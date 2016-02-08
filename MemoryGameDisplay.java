import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import java.io.*;
import java.io.File;
import javax.swing.Timer;
//sets the model for the game

public class MemoryGameDisplay extends JFrame implements ActionListener {
  JPanel listPanel;//panel containing
  JPanel CardPanel;//
  JButton startButton;// the start button
  JButton [][]card;
  JList gameStyle;//list containing different style of game
 // public static int matches;//number of pairs matched
 // public static int tries;//increments every second click
  
  JLabel style,score,time;// for style score and time remaining respectively
  JTextField scoreField,timeField;//txtfield for score and to show the time
  Timer myTimer;
  int counter = 0;
  private boolean running = false;
  private MemoryGame model;
  public static MemoryGameDisplay currentTest=null;
  private static JButton firstCard=null;//
  private static JButton secondCard=null;//
  int mode = 2;
  boolean gameIsOn = false;
  String img1="";
  String img2="";

  public MemoryGameDisplay(String title) {
    super(title);
    currentTest=this;
     model =  new MemoryGame();


    //panel containing card panel and the list panel
    JPanel panel=(JPanel)getContentPane();
    panel.setLayout(new GridBagLayout());
    GridBagConstraints c= new GridBagConstraints();
    //the panel that contains the card
    CardPanel=new JPanel();
    CardPanel.setLayout(new GridLayout(10, 10, 5, 5));
    c.fill=GridBagConstraints.BOTH;
    c.gridx=0;
    c.gridy=0;
    panel.add(CardPanel,c);
    //the panel that contains the labels and list and the start button
    listPanel=new JPanel();

    //initialize the style label
    style= new JLabel("Style");
    style.setMinimumSize(new Dimension(100,20));

    //initialize the score label
    score=new JLabel("Score");
    //initialize the gamestyle list
    String[] data={"Timed-(2mn)","Timed-(10mn)","No Limit" };
    gameStyle=new JList(data);
    gameStyle.setSelectedIndex(mode);
    gameStyle.setSize(50,200);
    MouseListener mouseListener = new MouseAdapter() {
     public void mouseClicked(MouseEvent e) {
        currentTest.mode = currentTest.gameStyle.getSelectedIndex();
     }
 };
    gameStyle.addMouseListener(mouseListener);

    //initialize time label
    time=new JLabel("Time Remaining :");
    time.setSize(50,10);
    //initialize timeField
    timeField=new JTextField();
    timeField.setSize(50,10);
    timeField.setEditable(false);
    //initialize the score textfield
    startButton=new JButton("Start");//initialize the start button




    startButton.setSize(50,5);
    startButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if(!currentTest.gameIsOn){
          currentTest.newGame();
          currentTest.gameIsOn = true;
          startButton.setText("Stop");
          if(scoreField != null){scoreField.setText("");}
          if(timeField != null){timeField.setText("");}
          for (int i=0;i<10;i++){
            for(int j=0;j<10;j++){
              card[i][j].setEnabled(true);
            }
          }
          if(gameStyle.getSelectedIndex()== 0){
            counter = 60 * 2;
          }else if(gameStyle.getSelectedIndex()== 1){
            counter = 60 * 10;
          }else{
            counter = 0;
          }
          myTimer.start();
        }
        else{
          currentTest.stopGame();
          currentTest.myTimer.stop();
          currentTest.timeField.setText("0m0s");
          currentTest.gameIsOn = false;
        }
      }});

    newGame();//Initiliaze cards

    scoreField=new JTextField(model.gameScore());
    scoreField.setText(model.getCorrectGuesses()+"/"+model.getGuessesMade());
    scoreField.setSize(50,10);
    scoreField.setEditable(false);

    listPanel.setLayout(new GridLayout(7,1));
    listPanel.add(style);
    listPanel.add(gameStyle);
    listPanel.add(startButton);
    listPanel.add(score);
    listPanel.add(scoreField);
    listPanel.add(time);
    listPanel.add(timeField);
    c.weightx=30.0;
    c.gridx=1;
    c.gridy=0;
    c.fill = GridBagConstraints.BOTH;
    panel.add(listPanel,c);




    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(500, 440);

    myTimer = new Timer(1000, new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if(currentTest.counter > 0){
          currentTest.counter--;
          currentTest.timeField.setText((counter/60)+"m"+(counter%60)+"s");
        }else{
          currentTest.timeField.setText("0m0s");
          myTimer.stop();
          if(mode < 2){
            currentTest.stopGame();
          }


        }

      }
    } );
    myTimer.start();
  }
  public  static void paintImmediately(){
    JPanel panel2= (JPanel) currentTest.getContentPane() ;
    panel2.paintImmediately(panel2.getX(),panel2.getY(),panel2.getWidth(),panel2.getHeight());
  }
  public void actionPerformed(ActionEvent e){

  }
  public void update(){
    scoreField.setText(model.getCorrectGuesses()+"/"+model.getGuessesMade());
  }
  private void handleTimerTick() {
    //update();
  }

  public void newGame()
  {
 CardPanel.removeAll();
    model =  new MemoryGame();
    model.createGame();
    card = new JButton[10][10];
    for (int i=0;i<10;i++){
      for(int j=0;j<10;j++){
        String image =model.cards[i][j];
        card[i][j]=new JButton(new ImageIcon("blank.jpg"));
        card[i][j].setSelectedIcon(new ImageIcon("icons" +java.io.File.separator + image));
        card[i][j].setSize(32,32);
        card[i][j].setMinimumSize(new Dimension(32,32));
        card[i][j].setMaximumSize(new Dimension(32,32));
        card[i][j].setSelected(false);
        card[i][j].setEnabled(false);
        card[i][j].addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            int hash=e.getSource().hashCode();//hascode of the source
            for(int i=0; i<10; i++) {
              for (int j=0; j<10; j++) {
                if (hash==card[i][j].hashCode()){
                  card[i][j].setSelected(true);
                  String img1 =model.cards[i][j];
                  repaint();
                  if(firstCard==null){
                    firstCard=card[i][j];
                    return;
                  }
                  if(firstCard.hashCode() == hash){// The same button was clicked twice in a row, return
                    return;
                  }
                  secondCard=card[i][j];
                  secondCard.setSelected(true);
                  MemoryGameDisplay.paintImmediately();
                  if((model.Match(firstCard.getSelectedIcon().toString(),secondCard.getSelectedIcon().toString()))){
                   // matches=matches+1;
                    if(currentTest.model.isOver()){
                      currentTest.startButton.setText("Play Again");
                    }
                    firstCard.setEnabled(false);
                    secondCard.setEnabled(false);
                  }else{
                    try {
                      Thread.sleep(500);}
                    catch(InterruptedException ex) {
                    }
                    firstCard.setSelected(false);
                    secondCard.setSelected(false);
                  }
                  firstCard=null;
                  secondCard=null;
                }
              }
            }
            update();
          }});
        CardPanel.add(card[i][j]);
      }
    }
  }

  //to stop the game
  public void stopGame(){
    startButton.setText("Start");
    gameIsOn = false;
    counter = 0;
    for (int i=0;i<10;i++){
      for(int j=0;j<10;j++){
        card[i][j].setEnabled(false);
        card[i][j].setSelected(false);
      }
    }
  }

  public static void main(String[] args) {
    new MemoryGameDisplay("The Memory game ").setVisible(true);
  }
}
