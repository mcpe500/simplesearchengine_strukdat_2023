
public class LinkedList {
	private Node head;
	public LinkedList() {
		head = null;
	}
	public boolean isAda(String kata) {
		Node temp = head;
		while(temp!=null) {
			if(temp.getKata().equals(kata)) {
				return true;
			}
			temp = temp.getNext();
		}
		return false;
	}
	public void append(Node data) {
		if(head == null) {
			head = data;
		}else {
			if(!isAda(data.getKata())) {
				data.setNext(head);
				head = data;
			}
		}
	}
	public void print() {
		Node temp = head;
		while(temp!=null) {
			System.out.print( "\nKata : " + temp.getKata() + " Link : " + temp.getLInk() );
			temp = temp.getNext();
		}
		System.out.println();
	}
}
