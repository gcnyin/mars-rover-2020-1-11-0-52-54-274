package com.thoughtworks.marsrover;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class MarsRover extends AbstractBehavior<MarsRover.Command> {
    public interface Command {
    }

    public MarsRover(ActorContext<Command> context) {
        super(context);
    }

    public static Behavior<Command> create() {
        return Behaviors.setup(MarsRover::new);
    }

    @Override
    public Receive<Command> createReceive() {
        return newReceiveBuilder().build();
    }

    public static class ReceivePositionAndDirect {
        public ReceivePositionAndDirect(double x, double y, Direct direct) {

        }
    }

    public static class Initialization implements Command {
        public Initialization(double x, double y, Direct direct, ActorRef<ReceivePositionAndDirect> replyTo) {
        }
    }
}
