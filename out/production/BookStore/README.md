For linux

To compile:

javac --module-path $PATH_TO_FX --add-modules javafx.controls -d classes  BookShop.java

To run:

java -cp mariadb-java-client-3.0.3.jar:classes BookShop
java --module-path $PATH_TO_FX --add-modules javafx.controls -cp mariadb-java-client-3.0.3.jar:classes BookShop
