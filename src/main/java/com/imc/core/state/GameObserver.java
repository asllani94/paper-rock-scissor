package com.imc.core.state;

import com.imc.core.events.GameEvent;

public interface GameObserver {
    void update(GameEvent event);
}
