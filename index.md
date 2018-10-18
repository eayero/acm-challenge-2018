## VI Torneo Caribeño de Jugadores Virtuales “ICPChallenge” 

The tournament consists on programming virtual players capable of automatic game playing, i.e. without human interaction. Every 2016 ACM-ICPC Caribbean finalist team may submit one virtual player implementation. A virtual player must be implemented in Java using a single file containing the source code describing a single class. To submit a virtual player, the source code must be sent before november 7 at the address acm-icpc@uci.cu. The message should contain the subject: ICPChallenge "team official name" (for example: ICPChallenge "MyTeam").

### The Game

"Battles in the Caribbean II" game was designed to be easy to use and easy to understand overall dynamics. In this sense it can be described quickly as a strategy tournament, where each player has a team of three heroes who have resources and individual properties. Each hero has different weapons, items and your own gold that can decrease or increase in response to game development. Each team may perform one action per turn (attack, move, buy, rest), alternating with the opposing team, the ultimate goal of the game is to accumulate as much gold as possible.

``` The game consists of an improved version with new players and items from the previous edition ```

### THE FIELD

The playing field is a matrix of cells with varying dimensions (X = [20, 60] and X = [20, 80]), each cell in the matrix may possess or elements in it, and likewise can run effects on entities that are on it.

![useful image]({{ site.url }}/assets/1.png)

### EFFECTS AND PROPERTIES

Before getting into other features of the game it is necessary to explain what are the effects because they are present in almost all elements of the game. In the game there are two types of effects:

-	Single: This type of effects affects a property only once in the game to be assigned to an entity (see entities)
-	Permanent: This type of effects affects a property, each turn of the game to be assigned to an entity (see entities)
The properties on the other hand are the characteristics that describe entities ("Life", "Defense", "Attack", "Gold"), each of these has a current value and a maximum that can not exceed For example gold a player is the sum of the "Gold" property of all its heroes.




```markdown
Syntax highlighted code block

# Header 1
## Header 2
### Header 3

- Bulleted
- List

1. Numbered
2. List

**Bold** and _Italic_ and `Code` text

[Link](url) and ![Image](src)
```

For more details see [GitHub Flavored Markdown](https://guides.github.com/features/mastering-markdown/).

### Jekyll Themes

Your Pages site will use the layout and styles from the Jekyll theme you have selected in your [repository settings](https://github.com/eayero/acm-challenge-2018/settings). The name of this theme is saved in the Jekyll `_config.yml` configuration file.

### Support or Contact

Having trouble with Pages? Check out our [documentation](https://help.github.com/categories/github-pages-basics/) or [contact support](https://github.com/contact) and we’ll help you sort it out.
