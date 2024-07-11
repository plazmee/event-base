# Event System / Система событий

Author: plazmee

## English

### Description

The event system is a tool for organizing interactions between objects in an application. It allows objects to subscribe to events and receive notifications when these events occur.

### Usage

#### Registering Objects

To receive event notifications, an object must be registered with the event system. To do this, create an instance of the `EventManger` class and call the `register` method, passing the object to be registered.

#### Creating Events

An event is an object that inherits from the `Event` interface. An event can be stopped or cancelled if it inherits from the `EventStoppable` or `EventCancellable` interfaces, respectively.

#### Handling Events

To handle an event, create a method in the registered object with the `@EventTarget` annotation, which takes the event object as a parameter.

#### Calling Events

To call an event, create an instance of the `EventManger` class and call the `call` method, passing the event object.

### Example Usage

```java
public class MyObject {
    @EventTarget
    public void onMyEvent(MyEvent event) {
        // Event handling
    }
}

public class MyEvent extends Event {
    // Event fields and methods
}

public class Main {
    public static void main(String[] args) {
        EventManger eventManger = new EventManger();
        MyObject myObject = new MyObject();
        eventManger.register(myObject);

        MyEvent myEvent = new MyEvent();
        eventManger.call(myEvent);
    }
}
```

## Русский

### Описание

Система событий - это инструмент для организации взаимодействия между объектами в приложении. Она позволяет объектам подписываться на события и получать уведомления, когда эти события происходит.

### Использование

#### Регистрация объектов

Чтобы объект мог получать уведомления о событиях, его нужно зарегистрировать в системе событий. Для этого нужно создать экземпляр класса `EventManger` и вызвать метод `register`, передав в него объект, который нужно зарегистрировать.

#### Создание событий

Событие - это объект, который наследуется от интерфейса `Event`. Событие может быть остановлено или отменено, если оно наследуется от интерфейсов `EventStoppable` или `EventCancellable` соответственно.

#### Обработка событий

Чтобы обработать событие, нужно создать метод в объекте, который зарегистрирован в системе событий. Метод должен иметь аннотацию `@EventTarget` и принимать в качестве параметра объект события.

#### Вызов событий

Чтобы вызвать событие, нужно создать экземпляр класса `EventManger` и вызвать метод `call`, передав в него объект события.

### Пример использования

```java
public class MyObject {
    @EventTarget
    public void onMyEvent(MyEvent event) {
        // Обработка события
    }
}

public class MyEvent extends Event {
    // Поля и методы события
}

public class Main {
    public static void main(String[] args) {
        EventManger eventManger = new EventManger();
        MyObject myObject = new MyObject();
        eventManger.register(myObject);

        MyEvent myEvent = new MyEvent();
        eventManger.call(myEvent);
    }
}
```