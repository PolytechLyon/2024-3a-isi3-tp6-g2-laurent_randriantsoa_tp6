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

## Exercice 5

Afin de suivre le patron de conception patron de méthode (Template Method) :
- Nous avons repris la méthode `log()` de `NamedLogger` pour séparer la logique de formatage du message de la logique d'écriture du message.

```java
@Override
public void log(String format, Object... args) {
    String entry = String.format(format, args);
    String message = formatMessage(entry);
    writeMessage(message);
}
```

- La méthode abstraite `writeMessage()` déclarée dans la classe `NamedLogger` devront être implémentées par les sous-classes.

`FileLogger` :

```java
@Override
protected void writeMessage(String message) {
    synchronized (FileLogger.class) {
        try (FileWriter fileWriter = new FileWriter(FILE_NAME, true)) {
            fileWriter.write(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
```

`ConsoleLogger` :

```java
@Override
protected void writeMessage(String message) {
    System.out.print(message);
}
```

## Exercice 6

On utilise la méthode de fabrique statique (Static Factory Method) pour centraliser la création de l'objet `Logger` au sein d'une interface `LoggerFactory`.

```java
public interface LoggerFactory {
    static Logger createLogger(String name) {
        return new ConsoleLogger(name);
    }
}
```

De plus, on modifie tous les appels de constructeur `new Logger()` par la méthode `LoggerFactory.createLogger()`

Le patron de conception "Méthode de Fabrique" centralise le processus de création d'objets en fournissant une interface commune avec une méthode pour créer des instances. Son utilisation centralise le choix de la réalisation d'une interface à un seul endroit dans le code.

Le patron Singleton, quant à lui, garantit une seule instance d'une classe et fournit un point d'accès global à cette instance.

## Exercice 7

La classe `TimestampedLoggerDecorator` est un décorateur de `Logger` qui ajoute un timestamp à chaque message loggé.
```java
public class TimestampedLoggerDecorator implements Logger {

    private final Logger logger;

    public TimestampedLoggerDecorator(Logger logger) {
        this.logger = logger;
    }

    private String addTimestamp(String message) {
        LocalDateTime currentTime = LocalDateTime.now();
        String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return String.format("[%s] %s", formattedTime, message);
    }

    @Override
    public void log(String format, Object... args) {
        String timestampedMessage = addTimestamp(format);
        this.logger.log(timestampedMessage, args);
    }
}
```

Afin d'utiliser ce décorateur dans les classes :

 ```java
private final TimestampedLoggerDecorator logger = new TimestampedLoggerDecorator(LoggerFactory.createLogger("BikeSimulator"));
```

## Exercice 8
La classe `Context` suit le patron de conception Façade (Facade) vis-à-vis de l'outil ServiceLoader. La classe `Context` sert alors d'interface unifiée pour utiliser des fonctionnalités porposées par le ServiceLoader.

Ainsi, pour instancier Bike, l'appel de constructeur `new Bike()` est remplacé par la méthode `context.inject(Bike.Class)`.

```java
Bike bike = Context.inject(Bike.class);
```

Grâce au fichier fr.polytech.sim.cycling.Bike, nous pouvons spécifier l'implémentation de l'interface Bike à utiliser. Ainsi, remplacer SimpleBike par TagAlongBike dans le fichier fr.polytech.sim.cycling.Bike permet de changer l'implémentation de Bike sans modifier le code source.

Il est possible d'avoir plusieurs lignes sur ce fichier, chaque ligne correspond à une implémentation spécifique de l'interface Bike. Cependant, seule la première ligne est prise en compte.

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
