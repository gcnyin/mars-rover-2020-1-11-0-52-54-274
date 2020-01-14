package com.thoughtworks.marsrover;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

public class MarsRover extends AbstractBehavior<MarsRover.Command> {
    public static Behavior<Command> create() {
        return Behaviors.setup(MarsRover::new);
    }

    public MarsRover(ActorContext<Command> context) {
        super(context);
    }

    @Override
    public Receive<Command> createReceive() {
        return newReceiveBuilder()
                .onMessage(BatchMessage.class, this::onBatchMessage)
                .build();
    }

    private Behavior<Command> onBatchMessage(BatchMessage a) {
        final Initialization initialization = (Initialization) a.messages.get(0);
        a.replyTo.tell(new Status(initialization.x, initialization.y, initialization.direct));
        return this;
    }

    public interface Command {
    }

    public static class Initialization implements Command {
        public final Double x;
        public final Double y;
        public final Direct direct;

        public Initialization(double x, double y, Direct direct) {
            this.x = x;
            this.y = y;
            this.direct = direct;
        }
    }

    public static class BatchMessage implements Command {
        public final ActorRef<Status> replyTo;
        public final List<Command> messages;

        public BatchMessage(ActorRef<Status> replyTo, List<Command> messages) {
            this.replyTo = replyTo;
            this.messages = messages;
        }
    }

    @ToString
    @EqualsAndHashCode
    public static class Status {
        public final Double x;
        public final Double y;
        public final Direct direct;

        public Status(Double x, Double y, Direct direct) {
            this.x = x;
            this.y = y;
            this.direct = direct;
        }
    }

    public static class Move implements Command {
    }
}
