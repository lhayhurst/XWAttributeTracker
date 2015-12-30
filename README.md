# XWAttributeTracker

This is a simple java program that allows you to track attributes of ships in the game of XWing Miniature Combat.  It is intended to be used in conjunction with Vassal and something like Twitch.

This program is bundled as an executable jar file.  This program uses obscure keyboard events!

# Download
[Download the executable jar file](https://github.com/lhayhurst/XWAttributeTracker/raw/master/StatTracker.jar), double click on it, and off you go.




# Manual

Press the following keys on the keyboard to do stuff:

- 'N' : add a new pilot
- Number : jump to a pilot in the list.  For example, hitting '1' will jump to the first pilot, '2' the second, etc.
- 'P' : increment the pilot
- 'E' or 'e': edit the pilot name
- 'P' : increment the pilot skill
- 'p' : decrement the pilot skill
- 'A': increment the attack value
- 'a': decrement the attack value
- 'D': increment the defense value
- 'd': decrement the defense value
- 'S': increment the shield value
- 's': decrement the shield value
- 'H': increment the hull value
-  'h': decrement the hull value

# Say it with pictures

When the health of a pilot is zero, it will be grayed out:

![Image of Grayedout pilot list](https://c2.staticflickr.com/6/5790/23688572439_1ffa14b275_c.jpg)

And when you change its hull, it'll come to life!

![color](https://c2.staticflickr.com/6/5708/24030336826_758e5d83df_c.jpg)

# Title

If you are twitch casting, you might want to give the window a title so OBS recognizes it.  To do that, launch the program from the command line and give it a command line argument:

% java -jar StatTracker.jar "Your window title here" 

