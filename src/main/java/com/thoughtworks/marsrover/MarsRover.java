package com.thoughtworks.marsrover;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import lombok.EqualsAndHashCode;
import lombok.ToString;

public class MarsRover extends AbstractBehavior<MarsRover.Command> {
    private double x;
    private double y;
    private Direct direct;

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
        return newReceiveBuilder()
                .onMessage(Initialization.class, this::onInitialization)
                .onMessage(Move.class, this::onMove)
                .onMessage(TurnDirect.class, this::onTurnDirect)
                .build();
    }

    private Behavior<Command> onTurnDirect(TurnDirect turnDirect) {
        direct = turnDirect.direct;
        turnDirect.replyTo.tell(new MarsRover.ReceivePositionAndDirect(10.0, 20.0, direct));
        return this;
    }

    private Behavior<Command> onMove(Move a) {
        if (direct == Direct.W) {
            x -= 1;
        } else if (direct == Direct.E) {
            x += 1;
        } else if (direct == Direct.N) {
            y += 1;
        } else if (direct == Direct.S) {
            y -= 1;
        }
        a.replyTo.tell(new ReceivePositionAndDirect(x, y, direct));
        return this;
    }

    public Behavior<Command> onInitialization(Initialization initialization) {
        x = initialization.x;
        y = initialization.y;
        direct = initialization.direct;
        initialization.replyTo.tell(new ReceivePositionAndDirect(x, y, direct));
        return this;
    }

    @ToString
    @EqualsAndHashCode
    public static class ReceivePositionAndDirect {
        public final double x;
        public final double y;
        public final Direct direct;

        public ReceivePositionAndDirect(double x, double y, Direct direct) {
            this.x = x;
            this.y = y;
            this.direct = direct;
        }
    }

    @ToString
    @EqualsAndHashCode
    public static class Initialization implements Command {
        public final double x;
        public final double y;
        public final Direct direct;
        public final ActorRef<ReceivePositionAndDirect> replyTo;

        public Initialization(double x, double y, Direct direct, ActorRef<ReceivePositionAndDirect> replyTo) {
            this.x = x;
            this.y = y;
            this.direct = direct;
            this.replyTo = replyTo;
        }
    }

    @ToString
    @EqualsAndHashCode
    public static class Move implements Command {
        public final ActorRef<ReceivePositionAndDirect> replyTo;

        public Move(ActorRef<ReceivePositionAndDirect> ref) {
            replyTo = ref;
        }
    }

    @ToString
    @EqualsAndHashCode
    public static class TurnDirect implements Command {
        public Direct direct;
        public ActorRef<ReceivePositionAndDirect> replyTo;

        public TurnDirect(Direct direct, ActorRef<ReceivePositionAndDirect> replyTo) {
            this.direct = direct;
            this.replyTo = replyTo;
        }
    }
}
