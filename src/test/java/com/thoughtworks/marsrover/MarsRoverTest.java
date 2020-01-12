package com.thoughtworks.marsrover;

import akka.actor.testkit.typed.javadsl.ActorTestKit;
import akka.actor.typed.ActorRef;
import org.junit.After;
import org.junit.Test;

public class MarsRoverTest {
    private final ActorTestKit actorTestKit = ActorTestKit.create("test-kit");

    @Test
    public void should_receive_message() {
        final ActorRef<MarsRover.Command> marsRover = actorTestKit.spawn(MarsRover.create(), "mars-rover");
        marsRover.tell(new MarsRover.Command() {
        });
    }

    @After
    public void tearDown() {
        actorTestKit.shutdownTestKit();
    }
}
