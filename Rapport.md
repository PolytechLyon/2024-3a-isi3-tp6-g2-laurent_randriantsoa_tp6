LAURENT Clément / RANDRIANTSOA Matthieu

# Compte Rendu du TP 1 : Patrons de Conceptions

## Exercices 1
Cette conception suit le design pattern composite.
Il n'y a pas besoin d'implémenter les méthodes getMass et getVelocity puisqu'elles sont déjà implémentées de façon générique dans la classe Vehicule que la classe Bike extend.

```java
public class TagAlongBike extends SimpleBike{

    public TagAlongBike() {
        components.add(new SimpleBike());
    }
}
```

## Exercices 2

Nous allons pouvoir parcourir les composants peu importe si nous utilisons un set ou une liste. Ce pettern correspond au pattern Iterator. Il n'y a pas besoin de modifier la méthode getVelocity.

Avant modification de la classe `Vehicule` :

````java
import java.util.HashSet;

protected final Set<MobileObject> components = new HashSet<>();
````
Après modification :
````java
import java.util.List;
import java.util.ArrayList;

protected final List<MobileObject> components = new ArrayList<>();
````

## Exercices 3
Modifications appliquées sur la classe `Clock` afin qu'elle respecte le design pattern Singleton et qu'elle ne soit instanciable qu'une seule fois.

````java
private static Clock instance;

private Clock () {
    Clock.instance = new Clock();
}

public static Clock getClock() {
    if (Clock.instance == null) {
        new Clock();
    }
    return Clock.instance;
}

````


Avant modification de la classe `Wheel` :
````java
private final Clock clock = new Clock();
````
Après modification :
````java
private final Clock clock = Clock.getClock();
````

## Exercices 4
La classe Bike est dans le package cycling tandis que la classe vehicule se trouve dans le package transport.
De plus la classe Bike extend la classe Vehicule qui est quand à elle une classe abstraite. C'est une dépendance cyclique.
C'est une mauvaise pratique car les dépendances cycliques rendent le code plus complexe et difficile à réutiliser, de plus cela accru les risque de bugs et complexifie les tests.

La class Bike possède la fonction getPush qui est utilisée dans la class Wheel

Pour casser cette dépendance nous allons utiliser le principe d'inverser de dépendance.

Avant :
````java
private final Bike drive;
````
Après :
````java
private final Vehicule drive;
````
De même, toute mention de Bike sera remplacé par Vehicule afin d'utiliser le getPush de vehicule plutôt que de Bike afin de ne plus avoir besoin de l'import de Bike.

## Exercices 5

Modifications apportées :

Classe `NamedLogger`

Le message est enregistré en tant qu'attribut pour pouvoir être récupéré facilement par ses sous-classes. 
```java
public String message;

@Override
public void log(String format, Object... args) {
    String entry = String.format(format, args);
    this.message = String.format("%s\t%s\n", this.name, entry);
}
```
Classe `ConsoleLogger`
```java
@Override
public void log(String format, Object... args) {
    super.log(format, args);
    System.out.print(message);
}
```
Classe `FileLogger`
```java
@Override
synchronized public void log(String format, Object... args) {
    super.log(format, args);
    try (FileWriter fileWriter = new FileWriter(FILE_NAME, true)) {
        fileWriter.write(message);
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}
```

## Exercices 6

Classe Abstraite LoggerFactory
```java
public abstract class LoggerFactory {

    public static Logger createLogger(String name) {
        return null;
    }

}

```
Classe ConsoleLoggerFactory
````java
public class ConsoleLoggerFactory {

    public static Logger createLogger(String name) {
        return new ConsoleLogger(name);
    }
}

````
Classe FileLoggerFactory
````java
public class FileLoggerFactory extends LoggerFactory {

    public static Logger createLogger(String name) {
        return new FileLogger(name);
    }
}
````

Le patron de conception "Méthode de Fabrique" centralise le processus de création d'objets en fournissant une interface commune avec une méthode abstraite pour créer des instances, tandis que les sous-classes concrètes fournissent l'implémentation spécifique. Il permet de déléguer la création d'objets à des sous-classes tout en préservant l'encapsulation. Contrairement au patron Singleton qui garantit une seule instance d'une classe, la méthode de fabrique est utilisée pour créer des instances variées d'une même interface ou classe abstraite. Ce patron offre une flexibilité dans le choix de l'implémentation des objets, favorisant ainsi la maintenabilité et la réutilisabilité du code. Son utilisation centralise le choix de la réalisation d'une interface à un seul endroit dans le code.

## Exercices 7

## Exercices 8

## Exercices 9


