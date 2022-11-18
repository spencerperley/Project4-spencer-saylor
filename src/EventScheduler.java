import java.util.*;

/**
 * Keeps track of events that have been scheduled.
 */
public final class EventScheduler
{
    private PriorityQueue<Event> eventQueue;
    private Map<Entity, List<Event>> pendingEvents;
    private double timeScale;

    public EventScheduler(double timeScale) {
        this.setEventQueue(new PriorityQueue<>(new EventComparator()));
        this.setPendingEvents(new HashMap<>());
        this.setTimeScale(timeScale);
    }



    public void scheduleEvent(
            Entity entity,
            Action action,
            long afterPeriod)
    {
        long time = System.currentTimeMillis() + (long)(afterPeriod
                * this.getTimeScale());
        Event event = new Event(action, time, entity);

        this.getEventQueue().add(event);

        // update list of pending events for the given entity
        List<Event> pending = this.getPendingEvents().getOrDefault(entity,
                new LinkedList<>());
        pending.add(event);
        this.getPendingEvents().put(entity, pending);
    }









    public void unscheduleAllEvents(
             Entity entity) {
        List<Event> pending = this.getPendingEvents().remove(entity);

        if (pending != null) {
            for (Event event : pending) {
                this.getEventQueue().remove(event);
            }
        }
    }

    public void removePendingEvent(
            Event event) {
        List<Event> pending = this.getPendingEvents().get(event.getEntity());

        if (pending != null) {
            pending.remove(event);
        }
    }

    public void updateOnTime(long time) {
        while (!this.getEventQueue().isEmpty()
                && this.getEventQueue().peek().getTime() < time) {
            Event next = this.getEventQueue().poll();

            this.removePendingEvent(next);

            next.getAction().executeAction(this);
        }
    }

    public PriorityQueue<Event> getEventQueue() {
        return eventQueue;
    }

    public void setEventQueue(PriorityQueue<Event> eventQueue) {
        this.eventQueue = eventQueue;
    }

    public Map<Entity, List<Event>> getPendingEvents() {
        return pendingEvents;
    }

    public void setPendingEvents(Map<Entity, List<Event>> pendingEvents) {
        this.pendingEvents = pendingEvents;
    }

    public double getTimeScale() {
        return timeScale;
    }

    public void setTimeScale(double timeScale) {
        this.timeScale = timeScale;
    }
}
