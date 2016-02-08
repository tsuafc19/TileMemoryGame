import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemoryGame extends ArrayList {

 private int REMAINING_MATCHES;
 private int numCol=10;
 private int numRow=10;
 private int CORRECT_GUESSES;
 private int GUESSES_MADE;
 private List<String> cardList ;//initialize card list with size 100
 String[]theCards;//1d array of type cards with size 100
 String[][] cards;//2d array of type crds with size 100
 String[]fileNames;

    public MemoryGame() {
      REMAINING_MATCHES=50;
      CORRECT_GUESSES=0;
      GUESSES_MADE=0;
      fileNames =  new java.io.File("icons").list();
      cards=new String[numRow][numCol];
      theCards=new String[2*fileNames.length];//the new array double the  size of filename
      cardList = new ArrayList<String>();
    }


    //to double up the file names
    public void doubleUp(){
      int count=0;

      for (int i = 0; i < 100; i++){
        theCards[i] = fileNames[count];
        count++;
        if(count==49)
          count=0;
      }
    }



  //create the list of 100 cars so we can randomize it later
    public List<String> createCardList(){
      doubleUp();
      // add ARRAY_SIZE squared elements into the list:
      for (int i = 0; i < 100; i++){
        String output =theCards[i];
        cardList.add(output);
      }
      return cardList;
     }

 // this is to Randomize the order of elements in an string array
    public  String [][] createGame(){

      //to randomize the list then to add it in the 2d array
      Collections.shuffle(createCardList());


      int k=0;//index to go through list
      for (int i = 0; i < 10; i++){
        for (int j = 0; j < 10; j++){
          cards[i][j] = cardList.get(k);
          k=k+1;
        }
      }
      return cards;
    }



    public int getRemainingMatches(){
      return REMAINING_MATCHES;
    }

    public int getCorrectGuesses(){
      return CORRECT_GUESSES;
    }

    public int getGuessesMade(){
      return GUESSES_MADE;
    }






 //to determine when the game is over/no more matches to be done
    public boolean isOver(){

      if(getRemainingMatches()==0){
        return true;
      }
      else{
        return false;
      }
 }



 // To check if 2 tiles matches
    public boolean Match(String a,String b){
      boolean match=true;
      if((a.equals(b)&& !(a.equals(a)))){
        System.out.println("we have a match");
        CORRECT_GUESSES++;
        REMAINING_MATCHES--;
        GUESSES_MADE=GUESSES_MADE+1;
        match=true;
      }else{
        match=false;
        System.out.println("Sorry, try again");
        GUESSES_MADE=GUESSES_MADE+1;

      }
      return match;

    }


 //return the score of gamer
    public String gameScore(){
      String output;
      output=getCorrectGuesses()+"/"+getGuessesMade();
      return output;
    }

 }

