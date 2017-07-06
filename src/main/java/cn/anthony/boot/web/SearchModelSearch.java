package cn.anthony.boot.web;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import cn.anthony.boot.domain.SearchModel;
import lombok.Data;

@Data
public class SearchModelSearch extends SearchModel {
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date searchBegin;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date searchEnd;
	Integer size, page;
}
