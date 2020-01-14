package com.thoughtworks.marsrover;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;

import java.util.List;

public class MarsRover {
    public static Behavior<Command> create() {
        return null;
    }

    public interface Command {
    }

    public static class Initialization implements Command {
        public Initialization(double x, double y, Direct direct) {
        }
    }

    public static class BatchMessage implements Command {
        public BatchMessage(ActorRef<Status> replyTo, List<Command> messages) {
        }
    }

    public static class Status {
        public Status(double x, double y, Direct direct) {
        }
    }
}
