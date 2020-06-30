public class Queue<T> implements Iterable<T> {
    private java.util.LinkedList<T> list = new java.util.LinkedList<T>();

    public Queue() {}
    
    public Queue(T firstElement) {
        enqueue(firstElement);
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public T peek() {
        if (isEmpty()) throw new RuntimeException("Queue Empty");
        return list.peekFirst();
    }

    public T dequeue() {
        if (isEmpty()) throw new RuntimeException("Queue Empty");
        return list.removeFirst();
    }

    public void enqueue(T element) {
        list.addLast(element);
    }

    @Override public java.util.Iterator<T> iterator() {
        return list.iterator();
    }
}