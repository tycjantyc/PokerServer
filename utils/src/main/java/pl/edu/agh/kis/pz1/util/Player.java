package pl.edu.agh.kis.pz1.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;


public class Player implements  Runnable{

    static Deck deck = new Deck();

    static ArrayList<Player> players = new ArrayList<>();
    ArrayList<Card> hand = new ArrayList<>();
    int mark;
    Socket socket;
    public boolean replaced ;
    Scanner input;
    public PrintWriter output;
    int ile_osob;

    public Player(Socket socket, int mark, int ile) {
        this.socket = socket;
        this.mark = mark;
        this.ile_osob = ile;
        this.replaced = false;
        players.add(this);

    }
    public void take_5(){ hand = deck.take_hand();
    }

    public boolean replace_n(Integer []args){
        if(args.length > 5){
            System.out.println("Zły format! Wpisz dobry format.");
            return false;
        }
        for(int arg:args){
            if(arg>4 | arg < 0){
                System.out.println("Zły format! Wpisz dobry format.");
                return false;
            }
        }
        for(int num:args){
            hand.remove(num);
            hand.add(num,deck.take_one());
        }
        return true;
    }
    public String show_hand(){
        String str ="";
        for(int i = 0;i<5;i++){
            str += hand.get(i)+" ";
        }
        return str;
    }
    public void sort_hand(){hand.sort(Card.comp);}
    public int hand_score(){
        int score = 0;
        sort_hand();
        ArrayList<Card.Rank> lista_rank = new ArrayList<>();
        ArrayList<Card.Suit> lista_suit = new ArrayList<>();
        for(Card c: hand){
            lista_rank.add(c.getRank());
            lista_suit.add(c.getSuit());
        }
        ArrayList<Integer>lista = new ArrayList<>();
        int cursor = 0;
        for(Card.Rank r:lista_rank){
            if(cursor == 0){
                lista.add(1);
                cursor +=1;
            }
            else{
                if(lista_rank.get(cursor)==lista_rank.get(cursor-1)){
                    int a = lista.get(cursor-1);
                    a +=1;
                    lista.set(cursor-1, a);

                }
                else{
                    lista.add(1);
                    cursor +=1;
                }
            }

        }
        if(lista.contains(2)){
            score = 1;
        }
        if(lista.contains(3)){
            score = 3;
        }
        if(lista.contains(4)){
            score = 8;
        }
        if(lista.size() == 3 && !lista.contains(3)){
            score = 2;
        }
        if(lista.contains(2) && lista.contains(3)){
            score = 7;
        }
        ArrayList<Card.Rank> all = new ArrayList<>();
        for(Card.Rank r:Card.Rank.values())
        {
            all.add(r);
        }
        int pos = all.indexOf(lista_rank.get(0));
        boolean is_strit = true;
        for(int i =0;i<5;i++){
            if(all.get(pos+i) != lista_rank.get(i)){
                is_strit = false;
            }
        }
        if(is_strit){
            score = 4;
            if(lista_rank.get(4)==Card.Rank.Ace){
                score+=2;
            }
        }




        Set set = new HashSet(lista_suit);
        if(set.size() == 1){
            score += 6;
        }




        return score;
    }
    @Override
    public void run() {
        try {
            setup();
            processCommands();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void setup() throws IOException {
        input = new Scanner(socket.getInputStream());
        output = new PrintWriter(socket.getOutputStream(), true);
        output.println("WELCOME " + mark);
        if (mark < ile_osob) {
            output.println("MESSAGE Waiting for opponent to connect");
        } else {
            for(Player p:players){
                p.output.println("MESSAGE Wszyscy gotowi więc gramy!");
            }
        }
    }

    private void processCommands() {
        while (input.hasNextLine()) {
            String command = input.nextLine();
            if (command.startsWith("QUIT")) {
                return;
            } else if (command.startsWith("GRAJ")) {
                take_5();
                sort_hand();
                output.println("MESSAGE Twoje karty: "+show_hand()+",Które chcesz wymienić?");
            } else if (command.startsWith("WYMIEN:")){
                processWymienCommand(command.substring(7));

            } else{
                output.println("MESSAGE Zły input! Spróbuj jeszcze raz!");
            }
        }
    }
    private void processWymienCommand(String wymien) {
        //try {
        String[] split = wymien.split(",");
        Integer[] array_kart = new Integer[split.length];
        for (int i = 0; i < split.length; i++) {
            array_kart[i] = Integer.parseInt(split[i]);
        }
        if(!replace_n(array_kart)){
            return;
        }
        sort_hand();
        this.replaced = true;
        for (Player p : players) {
            if(!p.replaced) {
                output.println("MESSAGE Czekaj! Inni jeszcze wymieniają");
                System.out.println("Czekaj! Inni jeszcze wymieniają");
                return;
            }
        }
        System.out.println("Tu działa");
        ArrayList<Integer> lista_wynikow = new ArrayList<>();
        for(Player player : players){
            lista_wynikow.add(player.hand_score());
        }
        System.out.println("Tu działa");
        int winner = lista_wynikow.indexOf(Collections.max(lista_wynikow))+1;
        for (Player p : players) {
            p.output.println("MESSAGE Zwyciezca to player nr:"+winner);
        }
        System.out.println("Tu działa koniec!");

        //} catch (ParseException p){}
    }
}


