
public class Card {
 public String name;

    public Card(String n) {
     this.name=n;
    }

 public String getName(){
  return name;
 }
 public String setName(String a){
  return name=a;
 }

 public String toString()
  {
    return getName();
  }


}
