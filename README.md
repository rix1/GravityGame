# GravityGame

This game is really simple:

You and your mini-bot fly around in space picking up parts to your space ship that went missing after crashing with a cloud of small meteors. You do this while listening to classic hip-hop music. When a part is found, the bot automatically finds the way back to the space ship, and guides you back there. Then you go again, looking for new parts. Oh, and you grow when you pick up a wrench/part, just because.

### How to run

Import project or clone from https://github.com/rix1/GravityGame

This game uses gradle. Just set it up in your preferred IDE and run the `build.gradle` file. If you are using IntelliJ, run configurations should already be set. In Eclipse, go to `Run Configurations..` and set `Main Class` to the `org.rix1.gravity.desktop.DesktopLauncher` class. Working directory needs to be set to `Gravity/core/assets/` and the classpath of module is the `desktop` module.

Google GSON is used for saving game state. Should it not be downloaded by Gradle, you need to download it yourself and add it to `External Libraries`.
 
### Controls

Walk your character around the map with the `left, right` keys on your keyboard. To initiate the jet-pack, press `up` - don't worry, gravity will drag you down again.

### Game features

The game offers the following features:

- Scoring system
- Saving and loading games upon start/exit (this happens automatically!)
- Animated character! This includes a animation graph, so that future animations can be added swiftly!
- Different blocks! (some can be picked up, while other are solid). In the future, generating new blocks will even be super easy thanks to object orientation! WO!
	- In the next version some will even chase you!
- Background music: To save the precious internet for traffic, this is not included on Github. If you want it tho, simply add a any music file called "music.mp3" to `Gravity/core/assets/` and you can have plenty of fun digging to your music.

### AI

Your friend, SpaceShip finder uses A* to follow your every move. When a part is picked up, the bot will find the way back to your space ship. 