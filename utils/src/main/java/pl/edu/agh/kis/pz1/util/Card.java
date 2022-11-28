package pl.edu.agh.kis.pz1.util;

import java.util.Comparator;

public class Card {
    enum Rank{Two,Three,Four,Five,Six,Seven,Eight,Nine,Ten,Jack,Queen,King,Ace}
    enum Suit{Spades,Clubs,Diamonds,Hearts}
    Rank r;
    Suit s;
    /*Card()
    {
        this.s = Suit.spades;
        this.r = Rank.two;
    }*/
    Card(Rank r, Suit s){
        this.r = r;
        this.s = s;
    }
    /*public boolean equals(Card c){
        if(this.r == c.r && this.s == c.s) return true;
        return false;
    }*/

    /*void setRank(Rank r){
        this.r = r;
    }
    void setSuit(Suit s){
        this.s = s;
    }*/



    Rank getRank(){
        return this.r;
    }

    Suit getSuit(){
        return this.s;
    }

    public static Comparator<Card> comp = new Comparator<Card>(){
        public int compare(Card d1, Card d2) {
            int x = d1.getRank().compareTo(d2.getRank());
            if(x==0){
                x = d1.s.compareTo(d2.s);
            }
            return x;
        }
    };
    public String toString(){
        return this.r.toString() + " of "+this.s.toString();
    }

}
