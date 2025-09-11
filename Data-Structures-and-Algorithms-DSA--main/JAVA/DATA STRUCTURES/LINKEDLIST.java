import java.util.*;

public class LINKEDLIST<T> { 
    Node head;
  
  public static void insert(int data) { 
      Node newNode = new Node(data); 
      
      if (this.head == null) { 
          this.head = newNode; 
      } else { 
          Node last = head; 
          while (last.next != null) { 
              last = last.next; 
          } 
          last.next = newNode; 
      }
  } 
  
  public static void deleteData(T key) {
      Node currNode = head, prev = null;

      if (currNode != null && currNode.data == key) {
          head = currNode.next;
      }

      while (currNode != null && currNode.data != key) {
          prev = currNode;
          currNode = currNode.next;
      }
      if (currNode != null) {
          prev.next = currNode.next;
      }
  }
    
  public static void deleteIndex(int index) {
      Node currNode = head, prev = null;

      if (index == 0 && currNode != null) {
          head = currNode.next;
      }

      int counter = 0;
      while (currNode != null) {
          if (counter == index) {
              prev.next = currNode.next;
              break;
          }
          else {
              prev = currNode;
              currNode = currNode.next;
              counter++;
          }
      }
  }
        
  static class Node<T> { 
      T data; 
      Node next; 
  
      Node(T data) { 
          this.data = data; 
          this.next = null; 
      } 
  } 
}
