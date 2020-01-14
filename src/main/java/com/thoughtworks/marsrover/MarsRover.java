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
                .onMessage(QueryStatus.class, this::onQueryStatus)
                .onMessage(Move.class, this::onMove)
                .build();
    }

    private Behavior<Command> onMove(Move message) {
        if (direct == Direct.N) {
            y += 1;
        } else if (direct == Direct.S) {
            y -= 1;
        } else if (direct == Direct.W) {
            x -= 1;
        } else if (direct == Direct.E) {
            x += 1;
        }
        return this;
    }

    private Behavior<Command> onInitialization(Initialization message) {
        x = message.x;
        y = message.y;
        direct = message.direct;
        return this;
    }

    private Behavior<Command> onQueryStatus(QueryStatus message) {
        message.replyTo.tell(new Status(x, y, direct));
        return this;
    }

    private Behavior<Command> onBatchMessage(BatchMessage message) {
        final ActorRef<Command> self = getContext().getSelf();
        message.messages.forEach(self::tell);
        self.tell(new QueryStatus(message.replyTo));
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
