/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uci.acm.challenge.Player.lastChallanges.y2018;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Vector;
import javafx.geometry.Point2D;
import javafx.util.Pair;
import uci.acm.challenge.Player.Actions.AttackTo;
import uci.acm.challenge.Player.Actions.BuyItem;
import uci.acm.challenge.Player.Actions.GeneralAction;
import uci.acm.challenge.Player.Actions.Move;
import uci.acm.challenge.Player.Actions.MoveTo;
import uci.acm.challenge.Player.Actions.Wait;
import uci.acm.challenge.Player.Entities.PlayerEntity;
import uci.acm.challenge.Player.Items.PlayerItems;
import uci.acm.challenge.Player.Items.PlayerStore;
import uci.acm.challenge.Player.Player;
import uci.acm.challenge.Player.PlayerMap;
import uci.acm.challenge.Player.University;

/**
 *
 * @author Eloy
 */
public class Limitless extends Player {

    private int iteracionNumero = -1;
    private Vector<Integer> rocasX;
    private Vector<Integer> rocasY;

    private Vector<Integer> arbolesX;
    private Vector<Integer> arbolesY;

    private Vector<Integer> hierbasX;
    private Vector<Integer> hierbasY;

    private boolean existenRocas = false;

    private Vector<Integer> enemiesX;
    private Vector<Integer> enemiesY;
    private int totalEnemigos = 3;

    private int havokOneX;
    private int havokOneY;

    private int havokTwoX;
    private int havokTwoY;

    private boolean havokOneDead = false;
    private boolean havokTwoDead = false;
    private boolean enemigoCerca = false;

    private int ordenCompraAntman = 0;

    private boolean starfighterComprado = false;
    private int ESTADO_ACTUAL = 0;

    /*0->matando a Havok, 1->buscando piedras, 2->buscando enemigo 3->todos muertos*/
    @Override
    public String MyName() {
        return "SuicideSquad";
    }

    @Override
    public University MyUniversity() {
        return University.UO_SAM;
    }

    @Override
    public PlayerEntity firstHero(ArrayList<PlayerEntity> al) {
        for (PlayerEntity pl : al) {
            if (pl.getType() == "Hulk") {
                return pl;
            }
        }
        return al.get(0);
    }

    @Override
    public PlayerEntity secondHero(ArrayList<PlayerEntity> al) {
        for (PlayerEntity pl : al) {
            if (pl.getType() == "Havok") {
                return pl;
            }
        }
        return al.get(0);
    }

    @Override
    public PlayerEntity thirdHero(ArrayList<PlayerEntity> al) {
        for (PlayerEntity pl : al) {
            if (pl.getType() == "Havok") {
                return pl;
            }
        }
        return al.get(0);
    }

    @Override
    public String firstHeroName() {
        return "JOKER";
    }

    @Override
    public String secondHeroName() {
        return "SuicideOne";
    }

    @Override
    public String thirdHeroName() {
        return "SuicideTwo";
    }

    private int ataquesSeguidos = 0;
    private int movesSeguidos = 0;
    private boolean proximaAccion = false;//false ataca, true se mueve

    @Override
    public GeneralAction iterate(List<PlayerEntity> list, List<PlayerEntity> list1, PlayerMap pm, PlayerStore ps) {
        iteracionNumero++;
        if (iteracionNumero == 0) {
            init(list, list1, pm, ps);
        } else {
            updateValues(list, list1, pm, ps);
        }

        PlayerEntity heroe = list.get(0);
        
        GeneralAction accion = null;
        
        if (ordenCompraAntman>0 && enemigoCerca){
            accion  = antmanAttack(heroe, pm, ps);
            if (accion != null)
                return accion;
        }
        
        if (ESTADO_ACTUAL != 3){
            accion = antmanBuy(heroe, pm, ps);
            if (accion != null) {
                return accion;
            }
        }

        switch (ESTADO_ACTUAL) {
            case 0: case 3:
                accion = antmanAttack(heroe, pm, ps);
                if (accion == null) {
                    accion = antmanMove(heroe, pm);
                }
                break;

            case 1:
                if (ordenCompraAntman > 0) {//se compro el starfighter
                    if (!proximaAccion) {
                        accion = antmanAttack(heroe, pm, ps);
                        if (accion == null) {
                            accion = antmanMove(heroe, pm);
                        } else {
                            ataquesSeguidos++;
                        }

                        if (ataquesSeguidos == 10) {
                            movesSeguidos = 0;
                            proximaAccion = true;
                        }

                    } else {
                        accion = antmanMove(heroe, pm);
                        if (accion == null) {
                            accion = antmanAttack(heroe, pm, ps);
                        } else {
                            movesSeguidos++;
                        }

                        if (movesSeguidos == 2) {
                            ataquesSeguidos = 0;
                            proximaAccion = false;
                        }
                    }

                } else {
                    accion = antmanAttack(heroe, pm, ps);
                    if (accion == null) {
                        accion = antmanMove(heroe, pm);
                    }
                }
                break;
            case 2:
                if (!proximaAccion) {
                    accion = antmanAttack(heroe, pm, ps);
                    if (accion == null) {
                        accion = antmanMove(heroe, pm);
                    } else {
                        ataquesSeguidos++;
                    }

                    if (ataquesSeguidos == 8) {
                        movesSeguidos = 0;
                        proximaAccion = true;
                    }

                } else {
                    accion = antmanMove(heroe, pm);
                    if (accion == null) {
                        accion = antmanAttack(heroe, pm, ps);
                    } else {
                        movesSeguidos++;
                    }

                    if (movesSeguidos == 4) {
                        ataquesSeguidos = 0;
                        proximaAccion = false;
                    }
                }
                break;
        }

        if (accion != null) {
            return accion;
        }

        Random ran = new Random();

        int h = 0;

        MoveTo[] mov = MoveTo.values();

        for (MoveTo m : mov) {
            if (pm.canMove(list.get(h), m)) {
                return new Move(list.get(h), m);
            }
        }

        return new Wait(list.get(h));
    }

    //movimiento orden = piedras, ultima victima, random
//    
    private GeneralAction antmanAttack(PlayerEntity antman, PlayerMap mapa, PlayerStore store) {

        List<PlayerItems> items = antman.getItems();

        //seleccion de arma de mayor alcance
        PlayerItems weapon = null;
        double alcance = 0;

        for (PlayerItems item : items) {
            if (item.isWeapon() && item.getScope() > alcance) {
                weapon = item;
                alcance = item.getScope();
            }
        }

        Point2D posAntman = new Point2D((double) antman.getX(), (double) antman.getY());
        PriorityQueue<Pair<Pair<Integer, Double>, Pair<Integer, Integer>>> pq
                = new PriorityQueue<Pair<Pair<Integer, Double>, Pair<Integer, Integer>>>(
                        new Comparator<Pair<Pair<Integer, Double>, Pair<Integer, Integer>>>() {
                    @Override
                    public int compare(Pair<Pair<Integer, Double>, Pair<Integer, Integer>> o1, Pair<Pair<Integer, Double>, Pair<Integer, Integer>> o2) {
                        if (o1.getKey().getKey() < o2.getKey().getKey()) {
                            return 1;
                        } else if (o1.getKey().getKey() > o2.getKey().getKey()) {
                            return -1;
                        }

                        if (o1.getKey().getValue() > o2.getKey().getValue()) {
                            return 1;
                        }
                        return -1;

                    }
                });

        for (int x = (int) (antman.getX() - alcance); x <= (int) (antman.getX() + alcance); x++) {
            for (int y = (int) (antman.getY() - alcance); y <= (int) (antman.getY() + alcance); y++) {
                Point2D posTemp = new Point2D(x, y);
                Pair<Integer, Integer> par = new Pair(x, y);
                try {
                    double distance = posAntman.distance(posTemp);
                    if (distance == 0) {
                        continue;
                    } else if (distance <= alcance && mapa.existItem(x, y)) {
                        if (mapa.getGameCell(x, y).getEntity().isHero()) {
                            pq.add(new Pair(new Pair(4, distance), par));
                        } else if (mapa.getGameCell(x, y).getEntity().getType() == "Rock") {
                            pq.add(new Pair(new Pair(3, distance), par));
                        } else if (mapa.getGameCell(x, y).getEntity().getType() == "Tree") {
                            pq.add(new Pair(new Pair(2, distance), par));
                        } else {
                            pq.add(new Pair(new Pair(1, distance), par));
                        }
                    }

                } catch (Exception e) {

                }
            }
        }
GeneralAction accion = null;
        try {
            if (pq.size() > 0) {
                Pair<Pair<Integer, Double>, Pair<Integer, Integer>> temp = pq.element();
                
                if (ordenCompraAntman>0 && enemigoCerca && temp.getKey().getKey() != 4)
                    accion = antmanBuy(antman, mapa, store);
                if (accion == null)
                    return new AttackTo(antman, weapon,
                            temp.getValue().getKey(), temp.getValue().getValue());
                
            }
        } catch (Exception e) {

        }
        return accion;
    }

    private GeneralAction antmanMove(PlayerEntity antman, PlayerMap mapa) {
        //   System.err.println("Vader Move");
        if (!havokOneDead) {
            GeneralAction move = moveHeroeTo(antman, mapa, havokOneX, havokOneY);
            if (move != null) {
                return move;
            }
        }
        if (!havokTwoDead) {
            GeneralAction move = moveHeroeTo(antman, mapa, havokTwoX, havokTwoY);
            if (move != null) {
                return move;
            }
        }

        if (ESTADO_ACTUAL == 1) {
            int rocaMasCercana = indexRocaMasCerca(mapa, antman.getX(), antman.getY());
            if (rocaMasCercana == -1) {
                existenRocas = false;
                ESTADO_ACTUAL = 2;
            } else {
                GeneralAction move = moveHeroeTo(antman, mapa,
                        rocasX.get(rocaMasCercana), rocasY.get(rocaMasCercana));
                if (move != null) {
                    return move;
                }
            }
        }
        if (ESTADO_ACTUAL == 2) {
            int enemigoMasCerca = indexEnemigoMasCerca(mapa, antman.getX(), antman.getY());
            if (enemigoMasCerca == -1) {
                ESTADO_ACTUAL = 3;
            } else {
                GeneralAction move = moveHeroeTo(antman, mapa,
                        enemiesX.get(enemigoMasCerca), enemiesY.get(enemigoMasCerca));
                if (move != null) {
                    return move;
                }
            }
        }
        if (ESTADO_ACTUAL == 3){
            int arbolMasCerca = indexArbolMasCerca(mapa, antman.getX(), antman.getY());            
            if (arbolMasCerca != -1) {
                GeneralAction move = moveHeroeTo(antman, mapa,
                        arbolesX.get(arbolMasCerca), arbolesY.get(arbolMasCerca));
                if (move != null) 
                    return move;
            }
        }
        MoveTo[] mov = MoveTo.values();
        Random ran = new Random();
        for (int i = 0; i < mov.length; i++) {
            int pos = ran.nextInt(mov.length);
            if (mapa.canMove(antman, mov[pos])) {
                return new Move(antman, mov[pos]);
            }
        }
        return null;
    }

    private GeneralAction antmanBuy(PlayerEntity antman, PlayerMap mapa, PlayerStore store) {
        
        ArrayList<PlayerItems> items = store.canBuy(antman);

        for (PlayerItems item : items) {
            switch (item.getType()) {
                case "Helicopter_Medical":
                    if (ordenCompraAntman == 1 || ordenCompraAntman == 6
                            || ordenCompraAntman == 12 || ordenCompraAntman == 18) {
                        ordenCompraAntman++;
                        return new BuyItem(antman, item);
                    }
                    break;
                case "Shield":
                    if (ordenCompraAntman >= 2 && ordenCompraAntman != 6 && ordenCompraAntman != 12
                            && ordenCompraAntman != 18) {
                        ordenCompraAntman++;
                        return new BuyItem(antman, item);
                    }
                    break;
////                case "Rifle":
////                    if (ordenCompraYokai == 4) {
////                        ordenCompraYokai++;
////                        return new BuyItem(yokai, item);
////                    }     
////                    break;
                case "Starfighter":
                    if (ordenCompraAntman == 0) {
                        ordenCompraAntman++;
                        return new BuyItem(antman, item);
                    }
                    break;
            }
        }

        return null;

    }

    private void busqueda(PlayerEntity heroe, PlayerMap pm) {
        rocasX = new Vector<Integer>();
        rocasY = new Vector<Integer>();
        arbolesX = new Vector<Integer>();
        arbolesY = new Vector<Integer>();
        hierbasX = new Vector<Integer>();
        hierbasY = new Vector<Integer>();
        enemiesX = new Vector<>();
        enemiesY = new Vector<>();

        int totalEnemiesFound = 0;
        for (int i = 0; i < 60; i++) {
            for (int j = 0; j < 80; j++) {
                try {
                    if (pm.getGameCell(i, j).getEntity().getType() == "Rock") {
                        rocasX.add(i);
                        rocasY.add(j);
                    } else if (pm.getGameCell(i, j).getEntity().getType() == "Tree") {
                        arbolesX.add(i);
                        arbolesY.add(j);
                    } else if (pm.getGameCell(i, j).getEntity().getType() == "Hierba") {
                        hierbasX.add(i);
                        hierbasY.add(j);
                    } else if (pm.getGameCell(i, j).getEntity().isHero()) {
                        if (pm.getGameCell(i, j).getEntity().getTeam()
                                != heroe.getTeam()) {
                            enemiesX.add(i);
                            enemiesY.add(j);
                            totalEnemiesFound++;
                        }
                    }

                } catch (Exception exc) {

                }
            }
        }
       // System.out.println(totalEnemiesFound);
        existenRocas = rocasX.size() > 0;
//        System.out.println(rocasX.size());
//        System.out.println(hierbasY.size());
//        System.out.println(arbolesY.size());
    }

    private void init(List<PlayerEntity> list, List<PlayerEntity> list1, PlayerMap pm, PlayerStore ps) {
        busqueda(list.get(0), pm);

        havokOneX = list.get(2).getX();
        havokOneY = list.get(2).getY();

        havokTwoX = list.get(1).getX();
        havokTwoY = list.get(1).getY();
    }

    /*mover un heroe hacia esa posicion*/
    private GeneralAction moveHeroeTo(PlayerEntity heroe, PlayerMap mapa, int posX, int posY) {

        if (posX < heroe.getX() && posY < heroe.getY()
                && mapa.canMove(heroe, MoveTo.UpLeft)) {
            return new Move(heroe, MoveTo.UpLeft);
        }
        if (posX < heroe.getX() && posY > heroe.getY()
                && mapa.canMove(heroe, MoveTo.DownLeft)) {
            return new Move(heroe, MoveTo.DownLeft);
        }

        if (posX > heroe.getX() && posY < heroe.getY()
                && mapa.canMove(heroe, MoveTo.UpRigth)) {
            return new Move(heroe, MoveTo.UpRigth);
        }
        if (posX > heroe.getX() && posY > heroe.getY()
                && mapa.canMove(heroe, MoveTo.DownRigth)) {
            return new Move(heroe, MoveTo.DownRigth);
        }

        if (posX < heroe.getX()
                && mapa.canMove(heroe, MoveTo.Left)) {
            return new Move(heroe, MoveTo.Left);
        }
        if (posX > heroe.getX()
                && mapa.canMove(heroe, MoveTo.Rigth)) {
            return new Move(heroe, MoveTo.Rigth);
        }
        if (posY < heroe.getY()
                && mapa.canMove(heroe, MoveTo.Up)) {
            return new Move(heroe, MoveTo.Up);
        }

        if (mapa.canMove(heroe, MoveTo.Down)) {
            return new Move(heroe, MoveTo.Down);
        }

        return null;
    }

    private int indexArbolMasCerca(PlayerMap mapa, int x, int y) {
        int arbolMasCerca = -1;
        Point2D posYokai = new Point2D(x, y);
        double menor = Double.POSITIVE_INFINITY;

        for (int i = 0; i < arbolesX.size(); i++) {
            if (!mapa.existItem(arbolesX.get(i), arbolesY.get(i))
                    || mapa.getGameCell(arbolesX.get(i), arbolesY.get(i)).getEntity().getType() != "Tree") {
                continue;
            }
            double dist = posYokai.distance(arbolesX.get(i), arbolesY.get(i));
            if (dist < menor) {
                menor = dist;
                arbolMasCerca = i;
            }
        }
        return arbolMasCerca;
    }

    private int indexRocaMasCerca(PlayerMap mapa, int x, int y) {
        int rocaMasCercana = -1;
        Point2D posAntman = new Point2D(x, y);
        double menor = Double.POSITIVE_INFINITY;

        for (int i = 0; i < rocasX.size(); i++) {
            if (!mapa.existItem(rocasX.get(i), rocasY.get(i))
                    || mapa.getGameCell(rocasX.get(i), rocasY.get(i)).getEntity().getType() != "Rock") {
                continue;
            }
            double dist = posAntman.distance(rocasX.get(i), rocasY.get(i));
            if (dist < menor) {
                menor = dist;
                rocaMasCercana = i;
            }
        }
        return rocaMasCercana;
    }

    private int indexEnemigoMasCerca(PlayerMap mapa, int x, int y) {
        int enemigoMasCerca = -1;
        Point2D posHeroe = new Point2D(x, y);
        double menor = Double.POSITIVE_INFINITY;

        for (int i = 0; i < enemiesX.size(); i++) {
            if (!mapa.existItem(enemiesX.get(i), enemiesY.get(i))) {
                continue;
            }
            double dist = posHeroe.distance(enemiesX.get(i), enemiesY.get(i));
            if (dist < menor) {
                menor = dist;
                enemigoMasCerca = i;
            }
        }
        return enemigoMasCerca;
    }

//    int movX[] = {1,1,1,-1,-1,-1,0,0};
//    int movY[] = {-1,0,1,-1,0,1,-1,1};
    private void updateValues(List<PlayerEntity> list, List<PlayerEntity> list1, PlayerMap pm, PlayerStore ps) {
        totalEnemigos = list1.size();
        actualizarPosicionEnemigos(list1, list.get(0));
        if (list.size() == 2) {
            havokOneDead = true;
        }
        if (list.size() == 1) {
            ESTADO_ACTUAL = 1;
            havokTwoDead = true;
        }
        if (totalEnemigos==0) ESTADO_ACTUAL=3;
    }

    private void actualizarPosicionEnemigos(List<PlayerEntity> enemies, PlayerEntity heroe) {
        Point2D punto = new Point2D(heroe.getX(), heroe.getY());
        enemigoCerca=false;
        for (int i = 0; i < enemies.size(); i++) {
            enemiesX.set(i, enemies.get(i).getX());
            enemiesY.set(i, enemies.get(i).getY());
            if (punto.distance(new Point2D(enemiesX.get(i), enemiesY.get(i))) <= 12){
                enemigoCerca = true;
              //  System.out.println("cerca");
            }
        }
    }
    
}
