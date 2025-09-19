import java.util.NoSuchElementException;
public class CustomQueue<T> {
    Node<T> front;
    Node<T> rear;
    int size;
    static class Node<T> {
        T data;
        Node<T> next;
        Node(T data) {
            this.data = data;
        }
    }
    public CustomQueue() {
        front = null;
        rear = null;
        size = 0;
    }
    public void enqueue(T item) {
        Node<T> newNode = new Node<>(item);
        if (rear != null) {
            rear.next = newNode;
        }
        rear = newNode;
        if (front == null) {
            front = rear;
        }
        size++;
    }
    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        T data = front.data;
        front = front.next;
        if (front == null) {
            rear = null;
        }
        size--;
        return data;
    }
    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }
}
