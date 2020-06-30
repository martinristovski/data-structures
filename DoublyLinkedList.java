public class DoublyLinkedList<T> implements Iterable<T> {
    private int size = 0;
    private Node<T> head;
    private Node<T> tail;

    private static class Node<T> {
        private T data;
        private Node<T> prev, next;
        
        public Node(T data, Node<T> prev, Node<T> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }

        @Override public String toString() {
            return data.toString();
        }
    }

    // Methods: clear, size, isEmpty, add, addFirst, addLast, peekFirst, 
    // peekLast, removeFirst, removeLast, removeNode, removeAt, remove, 
    // indexOf, contains, iterator, toString

    public void clear() {
        Node<T> traverser = head;

        while (traverser != null) {
            Node<T> next = traverser.next;
            traverser.prev = traverser.next = null;
            traverser.data = null;
            traverser = next;
        }

        head = tail = traverser = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void addLast(T element) {
        if (isEmpty()) {
            head = tail = new Node<T>(element, null, null);
        } else {
            tail.next = new Node<T>(element, null, null);
            tail = tail.next;
        }
        size++;
    }

    public void addFirst(T element) {
        if (isEmpty()) {
            head = tail = new Node<T>(element, null, null);
        } else {
            head.prev = new Node<T>(element, null, null);
            head = head.prev;
        }
        size++;
    }

    public T getFirst() {
        if (isEmpty()) throw new RuntimeException("List is empty.");
        return head.data;
    }

    public T getLast() {
        if (isEmpty()) throw new RuntimeException("List is empty.");
        return tail.data;
    }

    public void removeFirst() {
        if (isEmpty()) throw new RuntimeException("List is empty.");

        head = head.next;
        size--;

        if (isEmpty()) {
            tail = null;
        } else {
            head.prev = null;
        }
    }

    public void removeLast() {
        if (isEmpty()) throw new RuntimeException("List is empty.");

        tail = tail.prev;
        size--;

        if (isEmpty()) {
            head = null;
        } else {
            tail.next = null;
        }
    }

    private void remove(Node<T> node) {
        if (node.prev == null) removeFirst();
        if (node.next == null) removeLast();

        node.next.prev = node.prev;
        node.prev.next = node.next;

        node.data = null;
        node = node.prev = node.next = null;

        size--;
    }

    public void remove(Object obj) {
        Node<T> traverser = head;

        if (obj == null) {
            for (traverser = head; traverser != null; traverser = traverser.next) {
                if (traverser.data == null) remove(traverser);
            }
        } else {
            for (traverser = head; traverser != null; traverser = traverser.next) {
                if (obj.equals(traverser.data)) remove(traverser);
            }
        }
    }

    public void removeAt(int index) {
        if (index < 0 || index >= size) throw new IllegalArgumentException();

        int i;
        Node<T> traverser;

        if (index < size/2) {
            for (i = 0, traverser = head; i != index; i++) {
                traverser = traverser.next;
            }
        } else {
            for (i = size-1, traverser = tail; i != index; i--) {
                traverser = traverser.prev;
            }
        }

        remove(traverser);
    }

    public int indexOf(Object obj) {
        int index = 0;
        Node<T> traverser = head;

        if (obj == null) {
            for (; traverser != null; traverser = traverser.next, index++) {
                if (traverser.data == null) return index;
            }
        } else {
            for (; traverser != null; traverser = traverser.next, index++) {
                if (obj.equals(traverser.data)) return index;
            }
        }

        return -1;
    }

    public boolean contains(Object obj) {
        return indexOf(obj) != -1;
    }

    @Override public java.util.Iterator<T> iterator() {
        return new java.util.Iterator<T>() {
            private Node<T> traverser = head;

            @Override public boolean hasNext() {
                return traverser != null;
            }

            @Override public T next() {
                T data = traverser.data;
                traverser = traverser.next;
                return data;
            }

            @Override public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("[ ");

        Node<T> traverser = head;

        while (traverser != null) {
            sb.append(traverser.data + ", ");
            traverser = traverser.next;
        }

        sb.append(" ]");

        return sb.toString();
    }
}