public class Node {
	private String kata;
	private String link;
	private Node nextNode;
	public Node(String data,String link) {
		this.kata = data;
		this.link = link;
	}
	public String getKata() {
		return this.kata;
	}

	public Node getNext() {
		return this.nextNode;
	}
	public void setNext(Node next) {
		this.nextNode = next;
	}

	public String getLInk() {
		return this.link;
	}
	public void setLink(String link) {
		this.link = link;
	}
}
