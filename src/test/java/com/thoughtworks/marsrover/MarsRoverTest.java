package com.thoughtworks.marsrover;

import akka.actor.testkit.typed.javadsl.ActorTestKit;
import akka.actor.testkit.typed.javadsl.TestProbe;
import akka.actor.typed.ActorRef;
import org.junit.Test;

import java.util.Arrays;
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

        ActorRef<MarsRover.Command> marsRover5 = testKit.spawn(MarsRover.create(), "mars-rover");
        final double x5 = 15.0;
        final double y5 = 25.0;
        final Direct direct5 = Direct.E;
        final MarsRover.Initialization initialization5 = new MarsRover.Initialization(x5, y5, direct5);
        marsRover5.tell(new MarsRover.BatchMessage(controlCenter.getRef(), Collections.singletonList(initialization5)));
        controlCenter.expectMessage(new MarsRover.Status(x5, y5, direct5));
        testKit.stop(marsRover5);

        ActorRef<MarsRover.Command> marsRover6 = testKit.spawn(MarsRover.create(), "mars-rover");
        final double x6 = 16.0;
        final double y6 = 26.0;
        final Direct direct6 = Direct.W;
        final MarsRover.Initialization initialization6 = new MarsRover.Initialization(x6, y6, direct6);
        marsRover6.tell(new MarsRover.BatchMessage(controlCenter.getRef(), Collections.singletonList(initialization6)));
        controlCenter.expectMessage(new MarsRover.Status(x6, y6, direct6));
        testKit.stop(marsRover6);
    }

    @Test
    public void should_move_some_steps() {
        final TestProbe<MarsRover.Status> controlCenter = testKit.createTestProbe("control-center");

        final ActorRef<MarsRover.Command> marsRover = testKit.spawn(MarsRover.create(), "mars-rover");
        final MarsRover.Initialization initialization = new MarsRover.Initialization(10.0, 20.0, Direct.N);
        final MarsRover.Move move = new MarsRover.Move();
        marsRover.tell(new MarsRover.BatchMessage(controlCenter.getRef(), Arrays.asList(initialization, move)));
        controlCenter.expectMessage(new MarsRover.Status(11.0, 20.0, Direct.N));
        testKit.stop(marsRover);
    }
}
