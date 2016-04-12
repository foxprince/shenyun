package cn.anthony.boot.web;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class PageRequest {
    @NotNull(message = "Please enter ")
    @Min(value = 1, message = "Please addresss.")
    @Max(100000)
    int page = 1;
    @NotNull
    @Min(1)
    @Max(1000)
    int size = 10;

    public PageRequest() {
	super();
    }

    public int getPage() {
	return page;
    }

    public void setPage(int currPage) {
	this.page = currPage;
    }

    public int getSize() {
	return size;
    }

    public void setSize(int size) {
	this.size = size;
    }

}
