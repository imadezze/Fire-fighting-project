# Ensimag 2A POO - TP 2018/19
# ============================
#
# Ce Makefile permet de compiler le test de l'ihm en ligne de commande.
# Alternative (recommandee?): utiliser un IDE (eclipse, netbeans, ...)
# Le but est ici d'illustrer les notions de "classpath", a vous de l'adapter
# a votre projet.
#
# Organisation:
#  1) Les sources (*.java) se trouvent dans le repertoire src
#     Les classes d'un package toto sont dans src/toto
#     Les classes du package par defaut sont dans src
#
#  2) Les bytecodes (*.class) se trouvent dans le repertoire bin
#     La hierarchie des sources (par package) est conservee.
#     L'archive bin/gui.jar contient les classes de l'interface graphique
#
# Compilation:
#  Options de javac:
#   -d : repertoire dans lequel sont places les .class compiles
#   -classpath : repertoire dans lequel sont cherches les .class deja compiles
#   -sourcepath : repertoire dans lequel sont cherches les .java (dependances)

all: testInvader testLecture testSimulateur testScenario  testScenario1  testElementaire  testEvolueeBeta   testEvoluee

testInvader:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/tests/TestInvader.java

testLecture:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/tests/TestLecteurDonnees.java
	
testSimulateur:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/tests/TestSimulateur.java
	
testScenario:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/tests/TestScenario.java
	
testElementaire:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/tests/TestStrategieElement.java
	
testEvolueeBeta:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/tests/TestStrategieEvolueeBeta.java
	
testScenario1:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/tests/TestScenario1.java
	
testEvoluee:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/tests/TestStrategieEvoluee.java

# Execution:
# on peut taper directement la ligne de commande :
#   > java -classpath bin:bin/gui.jar TestInvader
# ou bien lancer l'execution en passant par ce Makefile:
#   > make exeInvader
exeInvader: 
	java -classpath bin:bin/gui.jar tests/TestInvader

exeLecture: 
	java -classpath bin:bin/gui.jar tests/TestLecteurDonnees cartes/carteSujet.map

exeSimulateur:
	java -classpath bin:bin/gui.jar tests/TestSimulateur cartes/carteSujet.map

exeScenario:
	java -classpath bin:bin/gui.jar tests/TestScenario cartes/carteSujet.map
	
exeElementaire:
	java  -classpath bin:bin/gui.jar tests/TestStrategieElement cartes/carteSujet.map

exeEvolueeBeta:
	java  -classpath bin:bin/gui.jar tests/TestStrategieEvolueeBeta cartes/carteSujet.map
	
exeScenario1:
	java  -classpath bin:bin/gui.jar tests/TestScenario1 cartes/carteSujet.map
	
exeEvoluee:
	java  -classpath bin:bin/gui.jar tests/TestStrategieEvoluee cartes/carteSujet.map
exeEvoluee1:
	java  -classpath bin:bin/gui.jar tests/TestStrategieEvoluee cartes/desertOfDeath-20x20.map
exeEvoluee2:
	java  -classpath bin:bin/gui.jar tests/TestStrategieEvoluee cartes/mushroomOfHell-20x20.map
exeEvoluee3:
	java  -classpath bin:bin/gui.jar tests/TestStrategieEvoluee cartes/spiralOfMadness-50x50.map		
clean:
	rm -rf bin/*.class
