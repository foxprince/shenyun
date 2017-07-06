package cn.anthony.boot.web;

public class DatatablePageRequest {
	int draw = 1;// integerJS
	// 请求次数计数器，每次发送给服务器后又原封返回，因为请求是异步的为了确保每次请求能对应到服务器返回的数据，假设你请求的是第一页的数据收到的却是第二页的，这样就乱套了
	// （这是我对draw的理解，如果有不同理解的可以告知）
	int start = 0;// integerJS 第一条数据的起始位置，比如0代表第一条数据
	int length = 1;// integerJS
	// 告诉服务器每页显示的条数，这个数字会等于返回的记录数，可能会大于因为服务器可能没有那么多数据。这个也可能是-1，代表需要返回全部数据(尽管这个和服务器处理的理念有点违背)
	String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDraw() {
		return draw;
	}

	public int getStart() {
		return start;
	}

	public int getLength() {
		return length;
	}
}
