package com.thoughtworks.marsrover;

import akka.actor.testkit.typed.javadsl.ActorTestKit;
import akka.actor.testkit.typed.javadsl.TestProbe;
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

    @Test
    public void should_accept_initialization_message_and_send_own_position_and_direct() {
        final ActorRef<MarsRover.Command> marsRover = actorTestKit.spawn(MarsRover.create(), "mars-rover");
        final TestProbe<MarsRover.ReceivePositionAndDirect> testProbe = actorTestKit.createTestProbe();
        marsRover.tell(new MarsRover.Initialization(10.0, 20.0, Direct.W, testProbe.getRef()));
        testProbe.expectMessage(new MarsRover.ReceivePositionAndDirect(10.0, 20.0, Direct.W));
    }

    @Test
    public void should_move_to_west_1_step_when_receive_move_message() {
        final ActorRef<MarsRover.Command> marsRover = actorTestKit.spawn(MarsRover.create(), "mars-rover");
        final TestProbe<MarsRover.ReceivePositionAndDirect> center = actorTestKit.createTestProbe();
        marsRover.tell(new MarsRover.Initialization(10.0, 20.0, Direct.W, center.getRef()));
        center.expectMessage(new MarsRover.ReceivePositionAndDirect(10.0, 20.0, Direct.W));
        marsRover.tell(new MarsRover.Move(center.getRef()));
        center.expectMessage(new MarsRover.ReceivePositionAndDirect(9.0, 20.0, Direct.W));
        marsRover.tell(new MarsRover.Move(center.getRef()));
        center.expectMessage(new MarsRover.ReceivePositionAndDirect(8.0, 20.0, Direct.W));
    }

    @Test
    public void should_move_to_east_1_step_when_receive_move_message() {
        final ActorRef<MarsRover.Command> marsRover = actorTestKit.spawn(MarsRover.create(), "mars-rover");
        final TestProbe<MarsRover.ReceivePositionAndDirect> controlCenter = actorTestKit.createTestProbe();
        marsRover.tell(new MarsRover.Initialization(10.0, 20.0, Direct.E, controlCenter.getRef()));
        controlCenter.expectMessage(new MarsRover.ReceivePositionAndDirect(10.0, 20.0, Direct.E));
        marsRover.tell(new MarsRover.Move(controlCenter.getRef()));
        controlCenter.expectMessage(new MarsRover.ReceivePositionAndDirect(11.0, 20.0, Direct.E));
    }

    @After
    public void tearDown() {
        actorTestKit.shutdownTestKit();
    }
}
