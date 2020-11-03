package cn.anthony.boot.web;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class PageRequest {
	@NotNull(message = "Please enter ")
	@Min(value = 1, message = "Please addresss.")
	@Max(100000)
	public int page = 1;
	@NotNull
	@Min(1)
	@Max(1000)
	public int size = 10;
}
