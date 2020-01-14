package com.thoughtworks.marsrover;

import akka.actor.testkit.typed.javadsl.ActorTestKit;
import akka.actor.testkit.typed.javadsl.TestProbe;
import akka.actor.typed.ActorRef;
import org.junit.Test;

import java.util.Collections;

public class MarsRoverTest {
    private final ActorTestKit testKit = ActorTestKit.create("test-kit");

    @Test
    public void should_return_rover_status() {
        ActorRef<MarsRover.Command> marsRover = testKit.spawn(MarsRover.create(), "mars-rover");
        final TestProbe<MarsRover.Status> testProbe = testKit.createTestProbe("control-center");
        final MarsRover.Initialization initialization = new MarsRover.Initialization(10.0, 20.0, Direct.N);
        marsRover.tell(new MarsRover.BatchMessage(testProbe.getRef(), Collections.singletonList(initialization)));
        testProbe.expectMessage(new MarsRover.Status(10.0, 20.0, Direct.N));
    }
}
