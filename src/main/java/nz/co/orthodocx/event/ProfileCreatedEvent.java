package nz.co.orthodocx.event;

import org.springframework.context.ApplicationEvent;

public class ProfileCreatedEvent extends ApplicationEvent {

    public ProfileCreatedEvent(Object source) {
        super(source);
    }
}
