import dev.plazmovich.event.Event;

public abstract class EventCancellable implements Event {
    private boolean cancelled;

    public boolean isCanceled() {
        return cancelled;
    }

    public void cancel() {
        cancelled = true;
    }
}
