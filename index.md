## VI Torneo Caribeño de Jugadores Virtuales “ICPChallenge” 

The tournament consists on programming virtual players capable of automatic game playing, i.e. without human interaction. Every 2016 ACM-ICPC Caribbean finalist team may submit one virtual player implementation. A virtual player must be implemented in Java using a single file containing the source code describing a single class. To submit a virtual player, the source code must be sent before november 7 at the address acm-icpc@uci.cu. The message should contain the subject: ICPChallenge "team official name" (for example: ICPChallenge "MyTeam").

### The Game

"Battles in the Caribbean II" game was designed to be easy to use and easy to understand overall dynamics. In this sense it can be described quickly as a strategy tournament, where each player has a team of three heroes who have resources and individual properties. Each hero has different weapons, items and your own gold that can decrease or increase in response to game development. Each team may perform one action per turn (attack, move, buy, rest), alternating with the opposing team, the ultimate goal of the game is to accumulate as much gold as possible.

``` The game consists of an improved version with new players and items from the previous edition ```

### THE FIELD

The playing field is a matrix of cells with varying dimensions (X = [20, 60] and X = [20, 80]), each cell in the matrix may possess or elements in it, and likewise can run effects on entities that are on it.

![useful image]({{ site.url }}/acm-challenge-2018/assets/1.png)

### EFFECTS AND PROPERTIES

Before getting into other features of the game it is necessary to explain what are the effects because they are present in almost all elements of the game. In the game there are two types of effects:

-	Single: This type of effects affects a property only once in the game to be assigned to an entity (see entities)
-	Permanent: This type of effects affects a property, each turn of the game to be assigned to an entity (see entities)
The properties on the other hand are the characteristics that describe entities ("Life", "Defense", "Attack", "Gold"), each of these has a current value and a maximum that can not exceed For example gold a player is the sum of the "Gold" property of all its heroes.

### CELLS

Each cells of the game, or boxes, has associated to a position (X, Y), in them can exist or not entities. Each cell in the game has the ability to add an effect to the entities to move toward it. To calculate the distance between two cells, we use the Euclidean distance. Thus three types of cells exist in the game:
-	Normal
-	Water
-	Fire

### ITEMS

Items, or articles, of the game are elements having the entities, or they can buy with your gold. There are two types of articles: weapons and effects, each item has a set of effects that moves to the entity that acquires. In the specific case of weapons are the only items that can be used in an attack, and are distinguished from the rest by having Scope (Scope) and Damage (Damage). **An entity can have as many items as you can buy.**

### ENTITIES
The entities are the elements with "Life" on the game, there are two types of entities which can be called obstacles, or objects that are distributed all over the field, and heroes. The heroes are the protagonists of the game and differ from the rest because they can perform actions (Buy Items, Move, Attack or Wait), also have between its properties the value of attack.

To create a hero we have 1600 points, which we must distribute in its four characteristics (Life, Attack, Defense, Gold) so that none of them have their initial value 0. A hero also has two permanent effects one for life and one for gold, that change the current value of these properties at each iteration, for it have 5 points to distribute as with the properties.

At the beginning of the game is placed in each cell an entity type object, and the heroes of each player are placed clustered in the corners.

### THE ACTIONS
Each player by iteration can make a single action on a specific hero. The time it takes to give the answer must be less than 1000ms.

**Action Move**

The move action can be executed whenever the cell to where you want to move the hero exists and not occupied by another entity, the cell must be one of the cells adjacent to the current position of the hero.

**Action Buy**

The action buy an item carries a gold expense at the entity performing the action. To buy an item the hero must be possess sufficient amount of gold otherwise the action will not run. An entity can be have associated many items as you can buy.

**Action Attack**

To a hero can to attack must select an item to perform the attack (weapon type) may attack any entity which is at a distance equal or less than the range of the selected item. If as a result of an attack an entity loses all his life points, the gold that this has passed to the entity that has defeated him.

*Important: A hero can die as a result of an attack on an obstacle and the same rules apply to gold.*

**Action Wait**

A player can decide not to take any action, in this case to define, the hero lose 50 points if possesses gold and 10 points of life.

*Important: A hero can die result of this action.*

### THE LIBRARY

The **uci.acm.challenge** library implements a computational variant of the game Battles in the Caribbean and is distributed through the **uci.acm.challenge.jar** file. The library documentation is also available.

Running the command line: **java -jar uci.acm.challenge.jar**
Appears a window where you can choose 2 strategies implemented and play.

### IMPLEMENTING A PLAYER

The uci.acm.challenge library allows compete to virtual players that can be implemented using the library itself.

To create a tactic is only necessary to write a class that implements the interface uci.acm.challenge.Player.Player. This interface contains the methods needed to their ejecution, each of them is identified in the documentation.

A video tutorial is also available, which is shown as start a project.
 The implementation of a demonstration class is also attached. Commented

### ABOUT THE TOURNAMENT
The tournament will make a round of all against all, to stay with the 8 implementations that more games will be able to overcome. The players crosses elected qualifying will be held until only players subtract the final.
The dimensions of the field, in each round decreases up to 20x20

### TAKE PART OF THE ACTION
Every team participating in the regional contest, has the option of sending personalized hero and an item. In both cases should follow a similar format description, and must send each image suggesting, preferably with a transparent background.
The deadline is for sending the heroes and items is October 20, after this date on day 21 an update of the library will be published with the heroes of each team


### Documents

[Entities Descriptions]({{ site.url }}(/acm-challenge-2018/assets/1.png)


