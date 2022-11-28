package pl.edu.agh.kis.pz1.util;

import java.util.ArrayList;
import java.util.Collections;
//import java.util.Random;

public class Deck {
public
    ArrayList<Card> cards = new ArrayList<>();
    public Deck(){
        create_whole_deck();
        shufle();
    }

    public ArrayList<Card> get_cards(){
        return cards;
    }

    public void create_whole_deck(){
        for(int i = 0;i<4;i++) {
            for (int j = 0; j < 13; j++) {
                Card.Rank r = Card.Rank.values()[j];
                Card.Suit s = Card.Suit.values()[i];
                Card temp = new Card(r, s);
                cards.add(temp);
            }
        }
    }
    public ArrayList<Card> take_hand(){
        ArrayList<Card> temp = new ArrayList<>();
        for(int i = 0;i<5;i++){
            temp.add(cards.remove(0));
        }
        return temp;
    }
    public Card take_one(){
        return cards.remove(0);
    }

    /*public void sortuj(){
        cards.sort(Card.comp);
    }*/
    public void shufle(){
        Collections.shuffle(cards);
    }
    /*public void show(){
        for(int i=0;i<cards.size();i++){
            System.out.println(cards.get(i));
        }
    }*/
}