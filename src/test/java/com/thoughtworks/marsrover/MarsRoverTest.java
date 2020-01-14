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
        final double x1 = 10.0;
        final double y1 = 20.0;
        final Direct direct1 = Direct.N;
        final MarsRover.Initialization initialization1 = new MarsRover.Initialization(x1, y1, direct1);
        marsRover1.tell(new MarsRover.BatchMessage(controlCenter.getRef(), Collections.singletonList(initialization1)));
        controlCenter.expectMessage(new MarsRover.Status(x1, y1, Direct.N));
        testKit.stop(marsRover1);

        ActorRef<MarsRover.Command> marsRover2 = testKit.spawn(MarsRover.create(), "mars-rover");
        final double x2 = 15.0;
        final double y2 = 20.0;
        final Direct direct2 = Direct.N;
        final MarsRover.Initialization initialization2 = new MarsRover.Initialization(x2, y2, direct2);
        marsRover2.tell(new MarsRover.BatchMessage(controlCenter.getRef(), Collections.singletonList(initialization2)));
        controlCenter.expectMessage(new MarsRover.Status(x2, y2, direct2));
        testKit.stop(marsRover2);

        ActorRef<MarsRover.Command> marsRover3 = testKit.spawn(MarsRover.create(), "mars-rover");
        final double x3 = 15.0;
        final double y3 = 25.0;
        final Direct direct3 = Direct.N;
        final MarsRover.Initialization initialization3 = new MarsRover.Initialization(x3, y3, direct3);
        marsRover3.tell(new MarsRover.BatchMessage(controlCenter.getRef(), Collections.singletonList(initialization3)));
        controlCenter.expectMessage(new MarsRover.Status(x3, y3, direct3));
        testKit.stop(marsRover3);

        ActorRef<MarsRover.Command> marsRover4 = testKit.spawn(MarsRover.create(), "mars-rover");
        final double x4 = 15.0;
        final double y4 = 25.0;
        final Direct direct4 = Direct.S;
        final MarsRover.Initialization initialization4 = new MarsRover.Initialization(x4, y4, direct4);
        marsRover4.tell(new MarsRover.BatchMessage(controlCenter.getRef(), Collections.singletonList(initialization4)));
        controlCenter.expectMessage(new MarsRover.Status(x4, y4, direct4));
        testKit.stop(marsRover4);
    }
}
