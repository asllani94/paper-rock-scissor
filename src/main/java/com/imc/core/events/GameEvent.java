package com.imc.core.events;

public abstract class GameEvent {
    private final EventType eventType;

    protected GameEvent(EventType eventType) {
        this.eventType = eventType;
    }

    public EventType getEventType() {
        return eventType;
    }
}


