import dev.plazmovich.event.target.EventTarget;
import dev.plazmovich.event.callables.EventStoppable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventManger {
    private final Map<Class<? extends Event>, List<MethodData>> REGISTRY_MAP = new HashMap<>();

    public void register(Object object) {
        for (Method method : object.getClass().getDeclaredMethods()) {
            if (isMethodBad(method)) {
                continue;
            }

            register(method, object);
        }
    }

    public void unregister(Object object) {
        for (final List<MethodData> dataList : REGISTRY_MAP.values()) {
            dataList.removeIf(data -> data.source().equals(object));
        }
        cleanMap();
    }

    private void register(Method method, Object object) {
        Class<? extends Event> indexClass = (Class<? extends Event>) method.getParameterTypes()[0];
        MethodData data = new MethodData(object, method);

        if (!data.target().isAccessible()) {
            data.target().setAccessible(true);
        }

        REGISTRY_MAP.computeIfAbsent(indexClass, k -> new CopyOnWriteArrayList<>()).add(data);
    }

    private void cleanMap() {
        REGISTRY_MAP.entrySet().removeIf(entry -> entry.getValue().isEmpty());
    }

    private boolean isMethodBad(Method method) {
        return method.getParameterTypes().length!= 1 ||!method.isAnnotationPresent(EventTarget.class);
    }

    public Event call(Event event) {
        List<MethodData> dataList = REGISTRY_MAP.get(event.getClass());

        if (dataList!= null) {
            if (event instanceof EventStoppable stoppable) {
                for (MethodData data : dataList) {
                    invoke(data, event);
                    if (stoppable.isStopped()) {
                        break;
                    }
                }
            } else {
                dataList.forEach(data -> invoke(data, event));
            }
        }

        return event;
    }

    private void invoke(MethodData data, Event argument) {
        try {
            data.target().invoke(data.source(), argument);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ignored) {
        }
    }

    private record MethodData(Object source, Method target) {
    }
}