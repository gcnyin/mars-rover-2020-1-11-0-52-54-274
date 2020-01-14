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

        final ActorRef<MarsRover.Command> marsRover1 = testKit.spawn(MarsRover.create(), "mars-rover");
        final double x1 = 10.0;
        final double y1 = 20.0;
        final Direct direct1 = Direct.N;
        final MarsRover.Initialization initialization1 = new MarsRover.Initialization(x1, y1, direct1);
        final MarsRover.Move move1 = new MarsRover.Move();
        marsRover1.tell(new MarsRover.BatchMessage(controlCenter.getRef(), Arrays.asList(initialization1, move1)));
        controlCenter.expectMessage(new MarsRover.Status(x1, y1 + 1, direct1));
        testKit.stop(marsRover1);

        final ActorRef<MarsRover.Command> marsRover2 = testKit.spawn(MarsRover.create(), "mars-rover");
        final double x2 = 13.0;
        final double y2 = 21.0;
        final Direct direct2 = Direct.S;
        final MarsRover.Initialization initialization2 = new MarsRover.Initialization(x2, y2, direct2);
        final MarsRover.Move move2 = new MarsRover.Move();
        marsRover2.tell(new MarsRover.BatchMessage(controlCenter.getRef(), Arrays.asList(initialization2, move2)));
        controlCenter.expectMessage(new MarsRover.Status(x2, y2 - 1, direct2));
        testKit.stop(marsRover2);

        final ActorRef<MarsRover.Command> marsRover3 = testKit.spawn(MarsRover.create(), "mars-rover");
        final double x3 = 13.0;
        final double y3 = 31.0;
        final Direct direct3 = Direct.W;
        final MarsRover.Initialization initialization3 = new MarsRover.Initialization(x3, y3, direct3);
        final MarsRover.Move move3 = new MarsRover.Move();
        marsRover3.tell(new MarsRover.BatchMessage(controlCenter.getRef(), Arrays.asList(initialization3, move3)));
        controlCenter.expectMessage(new MarsRover.Status(x3 - 1, y3, direct3));
        testKit.stop(marsRover3);

        final ActorRef<MarsRover.Command> marsRover4 = testKit.spawn(MarsRover.create(), "mars-rover");
        final double x4 = 14.0;
        final double y4 = 41.0;
        final Direct direct4 = Direct.E;
        final MarsRover.Initialization initialization4 = new MarsRover.Initialization(x4, y4, direct4);
        final MarsRover.Move move4 = new MarsRover.Move();
        marsRover4.tell(new MarsRover.BatchMessage(controlCenter.getRef(), Arrays.asList(initialization4, move4)));
        controlCenter.expectMessage(new MarsRover.Status(x4 + 1, y4, direct4));
        testKit.stop(marsRover4);
    }

    @Test
    public void should_turn_direct() {
        final TestProbe<MarsRover.Status> controlCenter = testKit.createTestProbe("control-center");

        ActorRef<MarsRover.Command> marsRover1 = testKit.spawn(MarsRover.create(), "mars-rover");
        final double x1 = 10.0;
        final double y1 = 20.0;
        final Direct direct1 = Direct.N;
        final MarsRover.Initialization initialization1 = new MarsRover.Initialization(x1, y1, direct1);
        final MarsRover.TurnDirect turnDirect1 = new MarsRover.TurnDirect(controlCenter.getRef(), Direct.S);
        marsRover1.tell(new MarsRover.BatchMessage(controlCenter.getRef(), Arrays.asList(initialization1, turnDirect1)));
        controlCenter.expectMessage(new MarsRover.Status(x1, y1, Direct.S));
    }
}
