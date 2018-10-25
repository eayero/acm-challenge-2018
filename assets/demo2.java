package uci.acm.challenge.Player.lastChallanges.y2018;

import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import uci.acm.challenge.Logic.ItemsType;
import uci.acm.challenge.Player.Player;
import uci.acm.challenge.Player.PlayerMap;
import uci.acm.challenge.Player.PlayerProp;
import uci.acm.challenge.Player.University;
import uci.acm.challenge.Player.Actions.AttackTo;
import uci.acm.challenge.Player.Actions.BuyItem;
import uci.acm.challenge.Player.Actions.GeneralAction;
import uci.acm.challenge.Player.Actions.Move;
import uci.acm.challenge.Player.Actions.MoveTo;
import uci.acm.challenge.Player.Actions.Wait;
import uci.acm.challenge.Player.Entities.PlayerEntity;
import uci.acm.challenge.Player.Items.PlayerItems;
import uci.acm.challenge.Player.Items.PlayerStore;

public class miJugador extends Player {

    @Override
    public String MyName() {
        return "Team Matanzas";
    }

    @Override
    public University MyUniversity() {
        return University.UM;
    }

    @Override
    public PlayerEntity firstHero(ArrayList<PlayerEntity> al) {
        PlayerEntity Jugador1 = al.get(0);
        return Jugador1;
    }

    @Override
    public String firstHeroName() {
        return "The Intelligence";
    }

    public double distancia(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    @Override
    public GeneralAction iterate(List<PlayerEntity> myHeros, List<PlayerEntity> enemyHeros, PlayerMap pm, PlayerStore ps) {
        Random r = new Random();

        int h = r.nextInt(myHeros.size());

        /**
         * *******************************
         */
		//Caerle atras a algun jugador enemigo
        //que si lo tengo en la mira le tiro
        //pero si tengo pokita vida mejor huyo pero no se como hacer eso		
        //que lastima pero se mataban entre elloooos pero hubiese servido                
        for (PlayerEntity i : myHeros) {
            List<PlayerItems> itemsHero = i.getItems();
            for (PlayerEntity j : enemyHeros) {
                for (PlayerItems k : itemsHero) {
                    if (distancia(i.getX(), i.getY(), j.getX(), j.getY()) <= k.getScope()) {
                        if (i.getProperty(PlayerProp.Life).getValue() >= 300) {
                            if (i.getProperty(PlayerProp.Attack).getValue() > j.getProperty(PlayerProp.Attack).getValue()) {
                                try {
                                    if (j.getTeam() != i.getTeam()) //esto es absurdo
                                    {
                                        return new AttackTo(i, k, (int) j.getX(), (int) j.getY());
                                    }

                                } catch (Exception e) {
                                }
                            }
                        }
                    }
                }
            }
        }

        /**
         * *************************************
         */
		// UNFINISHED
        //Usar items especificos		
        for (PlayerEntity i : myHeros) {
            if (i.getProperty(PlayerProp.Life).getValue() <= 150) {
                if (!i.getItems().contains(ItemsType.Posion)) {
                    ArrayList<PlayerItems> piss = ps.canBuy(i);

                    PlayerItems p = null;
                    for (PlayerItems d : piss) {
                        if (d.getType() == "Posion") {
                            p = d;
                            break;
                        }
                    }
                    if (!i.getItems().contains(ItemsType.Posion) && p != null) {
                        return new BuyItem(i, p);
                    }

                }

            }
        }
        /**
         * *****************************
         */

        if (r.nextBoolean()) {
            List<PlayerItems> itemsHero = myHeros.get(h).getItems();
            //Seleccion			
            PlayerItems weapon = null;
            for (Iterator<PlayerItems> iterator = itemsHero.iterator(); iterator.hasNext();) {
                PlayerItems next = iterator.next();
                if (next.isWeapon()) {
                    weapon = next;
                    break;
                }
            }

            double alcance = weapon.getScope();
            Point2D target = null;
            int index = 0;
            PlayerEntity hero = myHeros.get(h);

            for (int x = (int) (hero.getX() - alcance); x <= hero.getX() + alcance; x++) {
                for (int y = (int) (hero.getY() - alcance); y <= hero.getY() + alcance; y++) {

                    try {
                        if (distancia(hero.getX(), hero.getY(), x, y) == 0) {
                            continue;
                        }

                        if (distancia(hero.getX(), hero.getY(), x, y) <= alcance && pm.existItem(x, y)) {
                            if (pm.getGameCell(x, y).getEntity().getType() != "Rock") {

                                if (pm.getGameCell(x, y).getEntity().isHero()) {
                                    if (pm.getGameCell(x, y).getEntity().getTeam() != hero.getTeam()) {
                                        return new AttackTo(hero, weapon, (int) x, (int) y);
                                    }
                                } else {
                                        return new AttackTo(hero, weapon, (int) x, (int) y);
                                }

                            }

                        }
                    } catch (Exception e) {
                        //Error
                    }
                }
            }
        }

        int mivim = 0;

	//Move
        if (r.nextBoolean()) {
            MoveTo[] m = {MoveTo.Down, MoveTo.DownLeft, MoveTo.DownRigth, MoveTo.Left, MoveTo.Rigth, MoveTo.Up, MoveTo.UpLeft, MoveTo.UpRigth};
            int t = r.nextInt(m.length);
            if (pm.canMove(myHeros.get(h), m[t])) {
                return new Move(myHeros.get(h), m[t]);
            }
        }

	//Buy
        if (r.nextBoolean()) {

            ArrayList<PlayerItems> pis = ps.canBuy(myHeros.get(h));
            PlayerItems acomp = null;

            int rt = r.nextInt(pis.size() + 1);
            int yupi = 0;

            for (PlayerItems p : pis) {
                yupi++;
                if (yupi == rt) {
                    acomp = p;
                }
            }           
            
            if (!myHeros.get(h).getItems().contains(acomp)) {
                return new BuyItem(myHeros.get(h), acomp);
            }

        }

        return new Wait(myHeros.get(h));
    }

    @Override
    public PlayerEntity secondHero(ArrayList<PlayerEntity> al) {

        PlayerEntity Jugador2 = al.get(2);

        return Jugador2;

    }

    @Override
    public String secondHeroName() {
        // TODO Auto-generated method stub
        return "The Agility";
    }

    @Override
    public PlayerEntity thirdHero(ArrayList<PlayerEntity> al) {

        PlayerEntity Jugador3 = al.get(3);

        return Jugador3;
    }

    @Override
    public String thirdHeroName() {
        // TODO Auto-generated method stub
        return "The Strength";
    }

}
