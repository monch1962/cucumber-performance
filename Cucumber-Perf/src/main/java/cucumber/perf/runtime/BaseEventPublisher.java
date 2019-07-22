package cucumber.perf.runtime;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cucumber.perf.api.event.Event;
import cucumber.perf.api.event.EventHandler;
import cucumber.perf.api.event.EventPublisher;

@SuppressWarnings("rawtypes")
public class BaseEventPublisher implements EventPublisher {
	    
		protected Map<Class<? extends Event>, List<EventHandler>> handlers = new HashMap<Class<? extends Event>, List<EventHandler>>();

	    @Override
	    public final <T extends Event> void registerHandlerFor(Class<T> eventType, EventHandler<T> handler) {
	        if (handlers.containsKey(eventType)) {
	            handlers.get(eventType).add(handler);
	        } else {
	            List<EventHandler> list = new ArrayList<EventHandler>();
	            list.add(handler);
	            handlers.put(eventType, list);
	        }
	    }

	    @Override
	    public final <T extends Event> void removeHandlerFor(Class<T> eventType, EventHandler<T> handler) {
	        if (handlers.containsKey(eventType)) {
	            handlers.get(eventType).remove(handler);
	        }
	    }

	    @SuppressWarnings("unchecked")
		protected void send(Event event) {
	        if (handlers.containsKey(Event.class)) {
	            for (EventHandler handler : handlers.get(Event.class)) {
	                //noinspection unchecked: protected by registerHandlerFor
	                handler.receive(event);
	            }
	        }

	        if (handlers.containsKey(event.getClass())) {
	            for (EventHandler handler : handlers.get(event.getClass())) {
	                //noinspection unchecked: protected by registerHandlerFor
	                handler.receive(event);
	            }
	        }
	    }

	    protected void sendAll(Iterable<Event> events) {
	        for (Event event : events) {
	            send(event);
	        }
	    }
	    
	    public void sendEvent(final Event event) {
	    	send(event);
	    }
	}