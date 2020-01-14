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
        final TestProbe<MarsRover.Status> controlCenter = testKit.createTestProbe("control-center");

        ActorRef<MarsRover.Command> marsRover1 = testKit.spawn(MarsRover.create(), "mars-rover");
        final MarsRover.Initialization initialization1 = new MarsRover.Initialization(10.0, 20.0, Direct.N);
        marsRover1.tell(new MarsRover.BatchMessage(controlCenter.getRef(), Collections.singletonList(initialization1)));
        controlCenter.expectMessage(new MarsRover.Status(10.0, 20.0, Direct.N));
        testKit.stop(marsRover1);

        ActorRef<MarsRover.Command> marsRover2 = testKit.spawn(MarsRover.create(), "mars-rover");
        final double x2 = 15.0;
        final double y2 = 20.0;
        final Direct direct2 = Direct.N;
        final MarsRover.Initialization initialization2 = new MarsRover.Initialization(x2, y2, Direct.N);
        marsRover2.tell(new MarsRover.BatchMessage(controlCenter.getRef(), Collections.singletonList(initialization2)));
        controlCenter.expectMessage(new MarsRover.Status(x2, y2, direct2));
    }
}
