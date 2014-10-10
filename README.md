# GravityGame

This game is really simple:

1. You and your avatar have to find the exit (Spoiler alert: marked with pink) while digging to the music.
2. Dig some more to the music and watch your score increase!
3. Watch out for the enemy! He's more of a stalker than a enemy because he wont hurt you, but hey - who likes stalkers? 

*(Hint: Exists a removable tile that gives you even more points!)*
 

### How to run

Import project or clone from https://github.com/rix1/GravityGame

This game uses gradle. Just set it up in your preferred IDE and run the `build.gradle` file. If you are using IntelliJ, run configurations should already be set. In Eclipse, go to `Run Configurations..` and set `Main Class` to the `org.rix1.gravity.desktop.DesktopLauncher` class. Working directory needs to be set to `Gravity/core/assets/` and the classpath of module is the `desktop` module.
 
### Controls

Walk your character around the map with the `up, down, left, right` keys on your keyboard. No jumping in this version!

### Game features

The game offers the following features:

- Scoring system
- Saving and loading games upon start/exit (this happens automatically!)
- Animated character! This includes a animation graph, so that future animations can be added swiftly!
	- Next version will include more animations (jump maybe???)
- Different blocks! (some can be picked up, while other are solid). In the future, generating new blocks will even be super easy thanks to object orientation! WO!
	- In the next version some will even chase you!
- Background music: To save the precious internet for traffic, this is not included on Github. If you want it tho, simply add a any music file called "music.mp3" to `Gravity/core/assets/` and you can have plenty of fun digging to your music.

### AI

There is little AI, but some stupid green stalker is following you.≤