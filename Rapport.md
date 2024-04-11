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

Interface LoggerFactory
```java
package fr.polytech.sim.log;

public interface LoggerFactory {

    static Logger createLogger(String name) {
        return new ConsoleLogger(name);
    }

}


```
De plus on modifie tous les new Logger par des LoggerFactory.creatLogger()

Le patron de conception "Méthode de Fabrique" centralise le processus de création d'objets en fournissant une interface commune avec une méthode abstraite pour créer des instances, tandis que les sous-classes concrètes fournissent l'implémentation spécifique. Il permet de déléguer la création d'objets à des sous-classes tout en préservant l'encapsulation. Contrairement au patron Singleton qui garantit une seule instance d'une classe, la méthode de fabrique est utilisée pour créer des instances variées d'une même interface ou classe abstraite. Ce patron offre une flexibilité dans le choix de l'implémentation des objets, favorisant ainsi la maintenabilité et la réutilisabilité du code. Son utilisation centralise le choix de la réalisation d'une interface à un seul endroit dans le code.

## Exercices 7

La classe `TimestampedLoggerDecorator` est un décorateur de Logger qui ajoute un timestamp à chaque message loggé.
````java
public class TimestampedLoggerDecorator implements Logger {

    protected Logger logger;

    public TimestampedLoggerDecorator(Logger logger) {
        this.logger = logger;
    }

    private String addTimestamp(String message) {
        LocalDateTime currentTime = LocalDateTime.now();
        String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return "[" + formattedTime + "] " + message;
    }

    @Override
    public void log(String format, Object... args) {
        String timestampedMessage = addTimestamp(this.logger.message);
        this.logger.log(timestampedMessage,args);
    }
}

````

Afin d'utiliser cette classe, dans la classe BikeSimulator :
 ````java
    private final TimestampedLoggerDecorator logger = new TimestampedLoggerDecorator(LoggerFactory.createLogger("BikeSimulator"));
````

## Exercices 8
La classe Context suit le patron de conception "Factory Method" (Méthode de Fabrique) vis-à-vis de l'outil ServiceLoader.
Grâce au fichier fr.polytech.sim.cycling.Bike, le ServiceLoader charge toutes les implémentations de Bike spécifiées dans ce fichier et les rend disponibles pour l'injection de dépendance via la classe Context.

Ainsi, pour instancier un nouveau Bike, le new Bike() est changé par la méthode context.inject(Bike.Class)
````java
Bike bike = Context.inject(Bike.class);
````
Etant donné que dans le fichier fr.polytech.sim.cycling.Bike, la classe SimpleBike est donnée, cette injection instancie un SimpleBike.

Il est possible d'avoir plusieurs lignes sur ce fichier, chaque ligne correspond à une implémentation spécifique de l'interface Bike. Chaque implémentation est spécifiée par son nom de classe complet. L'outil ServiceLoader charge toutes les implémentations spécifiées dans ce fichier et les rend disponibles pour l'injection de dépendance via la classe Context. Chaque ligne dans ce fichier correspond donc à une implémentation de la classe Bike que le programme peut utiliser.

## Exercices 9


