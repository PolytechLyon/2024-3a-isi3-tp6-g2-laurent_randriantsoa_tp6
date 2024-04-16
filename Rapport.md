**LAURENT Clément / RANDRIANTSOA Matthieu**

# Compte Rendu du TP 1 : Patrons de Conceptions

## Exercice 1
Cette conception suit le Design Pattern Composite.

Dans ce modèle, `MobileObject` agit en tant que `composant`, fournissant le comportement de base pour tous les objets mobiles. Tandis que `Vehicle` agit comme un `composite`, organisant et gérant les différentes instances de `MobileObject`. Dans notre contexte, les feuilles et les composites sont représentés par la même classe.

Il n'y a pas besoin d'implémenter les méthodes `getMass()` et `getVelocity()` puisqu'elles sont déjà implémentées de façon générique dans la classe `Vehicule` que la classe `Bike` extend.

```java
public class TagAlongBike extends SimpleBike {
    public TagAlongBike() {
        components.add(new SimpleBike());
    }
}
```

## Exercice 2

La méthode utilise le Design Pattern Iterator.

De ce fait, nous allons pouvoir parcourir les composants indépendamment de leur structure interne et donc pouvoir utiliser un set ou une liste. Ainsi, il n'y a pas besoin de modifier la méthode `getVelocity()`.

Avant modification de la classe `Vehicule` :

````java
protected final Set<MobileObject> components = new HashSet<>();
````
Après modification :

````java
protected final List<MobileObject> components = new ArrayList<>();
````

## Exercice 3
Modifications appliquées sur la classe `Clock` afin qu'elle respecte le Design Pattern Singleton et qu'elle ne soit instanciable qu'une seule fois.

````java
public class Clock {
    private static Clock instance;

    private Clock() {
    }

    public static Clock getClock() {
        if (instance == null) {
            instance = new Clock();
        }
        return instance;
    }

    // ...
}
````

1) Le constructeur de la classe est rendu privé pour empêcher l'instanciation
2) Une variable statique de la classe est déclarée pour stocker l'unique instance de la classe.
3) Une méthode statique `getClock()` est fournie pour récupérer l'instance unique de la classe. Cette méthode vérifie d'abord si une instance existe déjà. Si c'est le cas, elle retourne cette instance. Sinon, elle crée une nouvelle instance en appelant le constructeur privé et la stocke dans la variable statique et la retourne ensuite.

Dans `Wheel`, on remplace l'instanciation de `Clock` par l'appel de la méthode `getClock()` pour obtenir l'instance unique de `Clock`.

Avant modification de la classe `Wheel` :
````java
private final Clock clock = new Clock();
````

Après modification :

````java
private final Clock clock = Clock.getClock();
````

## Exercice 4
La classe `Bike` est dans le package `cycling` tandis que la classe `Vehicule` se trouve dans le package `transport`.

De plus la classe `Bike` hérite de la classe `Vehicule` qui est quant à elle une classe `abstraite`. Et la classe `Wheel` dépend de la classe `Bike`. Nous sommes donc en présence d'une dépendance cyclique.

C'est une mauvaise pratique car les dépendances cycliques violent le principe de fermeture commune. Elles rendent le code plus complexe et difficile à maintenir. De plus cela accru les risque de bugs et complexifie les tests.

Pour casser cette dépendance, nous allons introduire une indirectionn en utilisant l'inversion de dépendance. 
Nous déplaçons la dépendance de `Bike` vers `Vehicule`, puisque `Bike` hérite de `Vehicule` et que `Vehicule` se trouve dans le même package que `Wheel`.

Ansi, toute mention de `Bike` dans `Wheel` sera remplacé par `Vehicule` afin d'utiliser la méthode `getPush()` de `Vehicule` plutôt que de `Bike`.

Avant :

````java
private final Bike drive;
````

Après :

````java
private final Vehicule drive;
````

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

La méthode injectAll() propose un patron de conception appelé "Iterator" pour parcourir tous les objets d'un type donné disponibles dans le contexte applicatif.

Fonction injectAll :
````java
    public static <T> Iterator<T> injectAll(Class<T> klass) {
        ServiceLoader<T> serviceLoader = ServiceLoader.load(klass);
        Iterator<T> iterator = serviceLoader.iterator();
        if (!iterator.hasNext()) {
            return Collections.emptyIterator();
        }
        return iterator;
    }
````
Utilisation de la fonction injectAll :
````java
        Iterator<Bike> bikeIterator = Context.injectAll(Bike.class);
````
Modficiation du constructeur de la classe bikeSimulator
````java
    public BikeSimulator(Iterator<Bike> bikeIterator) {
    if (bikeIterator.hasNext()) {
        this.bike = bikeIterator.next();
    } else {
        throw new IllegalArgumentException("Il n'y a pas assez de vélos dans l'itérateur.");
    }

    if (bikeIterator.hasNext()) {
        this.tag = bikeIterator.next();
    } else {
        throw new IllegalArgumentException("Il n'y a pas assez de vélos dans l'itérateur.");
    }
}
````
