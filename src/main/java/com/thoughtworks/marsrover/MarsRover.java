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
    private Double x;
    private Double y;
    private Direct direct;

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
                .onMessage(Initialization.class, this::onInitialization)
                .onMessage(Move.class, this::onMove)
                .onMessage(QueryStatus.class, this::onQueryStatus)
                .build();
    }

    private Behavior<Command> onQueryStatus(QueryStatus a) {
        a.replyTo.tell(new Status(11.0, 20.0, Direct.N));
        return this;
    }

    private Behavior<Command> onInitialization(Initialization initialization) {
        this.x = initialization.x;
        this.y = initialization.y;
        this.direct = initialization.direct;
        return this;
    }

    private Behavior<Command> onMove(Move move) {
        return this;
    }

    private Behavior<Command> onBatchMessage(BatchMessage a) {
        final ActorRef<Command> self = getContext().getSelf();
        a.messages.forEach(self::tell);
        self.tell(new MarsRover.QueryStatus(a.replyTo));
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

    private static class QueryStatus implements Command {
        public final ActorRef<Status> replyTo;

        public QueryStatus(ActorRef<Status> replyTo) {
            this.replyTo = replyTo;
        }
    }
}
