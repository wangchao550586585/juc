package concurrent.C;


import java.util.HashSet;
import java.util.Set;

/**
 * 在相互协作对象之间的锁顺序死锁
 * Taxi调用setLocation获得E_Taxi锁,内部调用notifyAvailable获得Dispatcher的锁
 * Dispatcher调用getImage获得Dispatcher的锁,绘画时获得E_Taxi的锁，二者按照不同顺序获得2个锁,可能产生死锁
 */
public class E_CooperatingDeadlock {
    class Taxi {
        private final Dispatcher dispatcher;
        private Point location, destination;

        public Taxi(Dispatcher dispatcher) {
            this.dispatcher = dispatcher;
        }

        public synchronized Point getLocation() {
            return location;
        }

        public synchronized void setLocation(Point location) {
            this.location = location;
            if (location.equals(destination)) {
                dispatcher.notifyAvailable(this);
            }
        }

        public synchronized Point getDestination() {
            return destination;
        }

        public synchronized void setDestination(Point destination) {
            this.destination = destination;
        }
    }

    class Dispatcher {
        private final Set<Taxi> taxis;
        private final Set<Taxi> availableTaxis;

        public Dispatcher() {
            taxis = new HashSet<Taxi>();
            availableTaxis = new HashSet<Taxi>();
        }

        public synchronized void notifyAvailable(Taxi taxi) {
            availableTaxis.add(taxi);
        }

        public synchronized Image getImage() {
            Image image = new Image();
            for (Taxi t : taxis)
                image.drawMarker(t.getLocation());
            return image;
        }
    }

    class Image {
        public void drawMarker(Point p) {
        }
    }

    class Point {
    }


}
