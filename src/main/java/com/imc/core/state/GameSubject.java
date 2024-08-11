package com.imc.core.state;

import com.imc.core.events.GameEvent;

public interface GameSubject {
    void registerObserver(GameObserver observer);
    void removeObserver(GameObserver observer);
    void notifyObservers(GameEvent event);
}