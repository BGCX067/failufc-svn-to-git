import java.io.File;
import java.io.FileWriter;
//import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Random;
//import java.util.Scanner;



public class Maw {

        /**
         * @param args
         */
        public static void main(String[] args) {
                /* Os argumentos passados para a main são:
                 * Tamanho do Labirinto
                 * Número de agentes ingênuos
                 * Número de agentes espertos
                 * Número de agentes forever alone*/
                
                int size = Integer.parseInt(args[0]);
                //int ingenuos = Integer.parseInt(args[1]);
                //int espertos = Integer.parseInt(args[2]);
                //int foreverAlone = Integer.parseInt(args[3]);
                
                FileWriter writer = null;
                
                try {
                        writer = new FileWriter(new File("Pontos/Ganhos_do_Smart_"+size), true);
                        PrintWriter saida = new PrintWriter(writer,true);
                        saida.println("Ingenuos Espertos ForeverAlone Ganho");
                        saida.close();
                        writer.close();
                } catch (Exception e) {
                        e.printStackTrace();
                }
                
                try {
                        writer = new FileWriter(new File("Pontos/Ganhos_do_Innocent_"+size), true);
                        PrintWriter saida = new PrintWriter(writer,true);
                        saida.println("Ingenuos Espertos ForeverAlone Ganho");
                        saida.close();
                        writer.close();
                } catch (Exception e) {
                        e.printStackTrace();
                }
                
                try {
                        writer = new FileWriter(new File("Pontos/Ganhos_do_ForeverAlone_"+size), true);
                        PrintWriter saida = new PrintWriter(writer,true);
                        saida.println("Ingenuos Espertos ForeverAlone Ganho");
                        saida.close();
                        writer.close();
                } catch (Exception e) {
                        e.printStackTrace();
                }
                
                double innocentsum;
                double smartsum;
                double fasum;
                
                for(int ingenuos = (int)((size*size*0.1)/3);ingenuos<=(size*size*0.6)/3;ingenuos++){
                        for(int espertos = (int)((size*size*0.1)/3);espertos<=(size*size*0.6)/3;espertos++){
                                for(int foreverAlone = (int)((size*size*0.1)/3);foreverAlone<=(size*size*0.6)/3;foreverAlone++){
                                        
                                        innocentsum=0;
                                        smartsum=0;
                                        fasum=0;
                                        
                                        int agentes = ingenuos+espertos+foreverAlone;
                                        
                                        Random rand = new Random();
                                        //Scanner input = new Scanner(System.in);
                                        
                                        Agent[] agentVector = new Agent[agentes];
                                        
                                        boolean[][] posUsadas = new boolean[size][size];
                                        
                                        for(int i=0; i<ingenuos;i++){
                                                agentVector[i] = new InnocentAgent(i, 0, 0);
                                        }
                                        
                                        for(int i=0; i<espertos;i++){
                                                agentVector[i+ingenuos] = new SmartAgent(i+ingenuos, 0, 0);
                                        }
                                        
                                        for(int i=0; i<foreverAlone;i++){
                                                agentVector[i+ingenuos+espertos] = new ForeverAloneAgent(i+ingenuos+espertos, 0, 0);
                                        }
                                        
                                        for(int k= 1; k<=100;k++){
                                                
                                                //System.out.println("+++++++++++++++++++++++++++++RODADA "+k+"+++++++++++++++++++++++++++++");
                                                //input.next();
                                                
                                                for(int i= 0; i<size; i++){
                                                        for(int j= 0; j<size; j++){
                                                                posUsadas[i][j] =false;
                                                        }
                                                }
                                                
                                                LinkedList<Coalition> coalitions= new LinkedList<Coalition>();
                                                
                                                for(int i = 0; i<ingenuos; i++){
                                                        int posx;
                                                        int posy;
                                                        
                                                        do{
                                                                posx = rand.nextInt(size);
                                                                posy = rand.nextInt(size);
                                                        }while(posUsadas[posy][posx]);
                                                        
                                                        posUsadas[posy][posx] = true;
                                                        agentVector[i].newPosition(posx, posy);
                                                        coalitions.add(new Coalition(agentVector[i]));
                                                        
                                                }
                                                
                                                for(int i = 0; i<espertos; i++){
                                                        int posx;
                                                        int posy;
                                                        
                                                        do{
                                                                posx = rand.nextInt(size);
                                                                posy = rand.nextInt(size);
                                                        }while(posUsadas[posy][posx]);
                                                        
                                                        agentVector[i+ingenuos].newPosition(posx, posy);
                                                        posUsadas[posy][posx] = true;
                                                        coalitions.add(new Coalition(agentVector[i+ingenuos]));
                                                        
                                                }
                                                
                                                for(int i = 0; i<foreverAlone; i++){
                                                        int posx;
                                                        int posy;
                                                        
                                                        do{
                                                                posx = rand.nextInt(size);
                                                                posy = rand.nextInt(size);
                                                        }while(posUsadas[posy][posx]);
                                                        
                                                        agentVector[i+ingenuos+espertos].newPosition(posx, posy);
                                                        posUsadas[posy][posx] = true;
                                                        coalitions.add(new Coalition(agentVector[i+ingenuos+espertos]));
                                                        
                                                }
                                                
                                                int gposy;
                                                int gposx;
                                                do{
                                                        gposx = rand.nextInt(size);
                                                        gposy = rand.nextInt(size);
                                                }while(posUsadas[gposy][gposx]);
                                                
                                                Maze maze = new Maze(size, gposx, gposy, coalitions);
                                                
                                                for(int i=0;i<agentes;i++){
                                                        maze.rooms[agentVector[i].posy][agentVector[i].posx] = agentVector[i];
                                                }
                                                
                                                //maze.printRooms();
                                                //input.next();
                                                //System.out.println();
                                                //System.out.println();
                                                //System.out.println();
                                                int vencedor = -1;
                                                boolean terminou = false;
                                                while(true){
                                                        for(int i=0; i<agentes;i++){
                                                                terminou = agentVector[i].yourTurn(maze);
                                                                if(terminou){
                                                                        vencedor = i;
                                                                        break;
                                                                }
                                                        }
                                                        //maze.printRooms();
                                                        //input.next();
                                                        /*try{
                                                                for(int i=0;i<10000;i++){
                                                                        maze.wait(999999999);
                                                                }
                                                        }catch(Exception e){
                                                                
                                                        }*/
                                                        //System.out.println();
                                                        //System.out.println();
                                                        //System.out.println();
                                                        if(terminou){
                                                                break;
                                                        }
                                                }
                                                //maze.printCoalitions();
                                                //System.out.println("Quem achou: "+vencedor);
                                                Coalition winnerCoalition= maze.getCoalition(vencedor);
                                                //winnerCoalition.print();
                                                winnerCoalition.DistributePrize(1000);
                                                
                                                //for(int i=0; i<agentes;i++){
                                                        //agentVector[i].print();
                                                //}
                                                //System.out.println();
                                                //System.out.println();
                                                //System.out.println();
                                        }
                                        for(int i=0; i< ingenuos; i++){
                                                innocentsum = innocentsum + agentVector[i].cash;
                                        }
                                        for(int i=0; i< espertos; i++){
                                                smartsum = smartsum + agentVector[i+ingenuos].cash;
                                        }
                                        for(int i=0; i< foreverAlone; i++){
                                                fasum = fasum + agentVector[i+ingenuos+espertos].cash;
                                        }
                                        
                                        innocentsum = innocentsum/10/ingenuos;
                                        smartsum = smartsum/10/espertos;
                                        fasum = fasum/10/foreverAlone;
                                        
                                        
                                        try {
                                                writer = new FileWriter(new File("Pontos/Ganhos_do_Smart_"+size), true);
                                                PrintWriter saida = new PrintWriter(writer,true);
                                                saida.println(ingenuos +" "+ espertos+" "+foreverAlone +" "+smartsum);
                                                saida.close();
                                                writer.close();
                                        } catch (Exception e) {
                                                e.printStackTrace();
                                        }
                                        
                                        try {
                                                writer = new FileWriter(new File("Pontos/Ganhos_do_Innocent_"+size), true);
                                                PrintWriter saida = new PrintWriter(writer,true);
                                                saida.println( ingenuos +" "+ espertos+" "+ foreverAlone+" "+innocentsum);
                                                saida.close();
                                                writer.close();
                                        } catch (Exception e) {
                                                e.printStackTrace();
                                        }
                                        
                                        try {
                                                writer = new FileWriter(new File("Pontos/Ganhos_do_ForeverAlone_"+size), true);
                                                PrintWriter saida = new PrintWriter(writer,true);
                                                saida.println( ingenuos +" "+ espertos+" "+ foreverAlone+" "+fasum);
                                                saida.close();
                                                writer.close();
                                        } catch (Exception e) {
                                                e.printStackTrace();
                                        }
                                }
                        }
                }
        }
}

