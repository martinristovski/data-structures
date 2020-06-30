import java.util.NoSuchElementException;

public class LinkedList {
    private class Node {
        private int value;
        private Node next;

        public Node(int nodeValue) {
            this.value = nodeValue;
        }
    }

    private Node first;
    private Node last;

    public void addLast(int newInt) {
        Node newNode = new Node(newInt);

        if (isEmpty()) {
            first = newNode;
            last = newNode;
        } else {
            last.next = newNode;
            last = newNode;
        }
    }

    public void addFirst(int newInt) {
        Node newNode = new Node(newInt);

        if (isEmpty()) {
            first = newNode;
            last = newNode;
        } else {
            newNode.next = first;
            first = newNode;
        }
    }

    public int indexOf(int queryInt) {
        int index = 0;
        Node current = first;
        while (current != null) {
            if (current.value == queryInt) { return index; }
            current = current.next;
            index++;
        }
        return -1;
    }

    public boolean contains(int queryInt) {
        return indexOf(queryInt) != -1;
    }

    public void deleteFirst() {
        if (isEmpty()) throw new NoSuchElementException();

        if (first == last) {
            first = last = null;
            return;
        }

        Node second = first.next;
        first.next = null;
        first = second;
    }

    public void deleteLast() {
        if (isEmpty()) throw new NoSuchElementException();

        if (first == last) {
            first = last = null;
            return;
        }
        
        Node previous = getPrevious(last);
        last = previous;
        last.next = null;
    }

    private boolean isEmpty() {
        return first == null;
    }

    private Node getPrevious(Node node) {
        Node current = first;
        while (current != null) {
            if (current.next == last) {
                return current;
            } else {
                current = current.next;
            }
        }
        return null;
    }
}