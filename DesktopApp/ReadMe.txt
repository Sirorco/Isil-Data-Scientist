Normalement, vous n'avez rien à faire étant donné que j'upload également les fichiers de la carte à puce sur le github, l'applet devrait donc être déjà installée dans l'eeprom qui se situe ans carteAPuces

Si jamais:
Pour l'applet à installer:

Faire le CAP File:

/!\ compiler la classe cartePuceApplet avec un ancien jdk (1.2)
Créer le cap en se plaçant dans DesktopApp\build\classes et exécuter ..\..\java_card_kit-2_2_1\bin\converter.bat -config ..\..\carteAPuces\cartePuceApplet.opt
Il faudra changer le chemin de l'export path dans le fichier cartePuceApplet pour qu'il corresponde bien à votre chemin
Il y a des variables d'environnement à setter : JC_HOME avec le chemin vers le répertoire java card kit 
						JAVA_HOME avec le chemin vers le jdk 1.2
/!\ à qu'il n'y ait pas d'espaces dans les chemins d'accès

Installer l'applet:
java_card_kit-2_2_1\bin\scriptgen.bat -o carteAPuces\applInstall.scr build\classes\Utils\Cards\javacard

modifier applInstal.scr:
ajouter au début:
powerup;

0x00 0xA4 0x04 0x00 0x09 0xa0 0x00 0x00 0x00 0x62 0x03 0x01 0x08 0x01 0x7F;

ajouter à la fin:
0x80 0xB0 0x00 0x00 0x00 0x7F;
0x80 0xB8 0x00 0x00 0x08 0x06 0x00 0x00 0x00 0x00 0x01 0x01 0x00 0x7F;
0x80 0xBA 0x00 0x00 0x00 0x7F;
powerdown;

lancer l'émulateur:
java_card_kit-2_2_1\bin\cref.exe -o carteAPuces\eepromCartePuce

installer:
java_card_kit-2_2_1\bin\apdutool.bat -o carteAPuces\logsInstall carteAPuces\applInstall.scr