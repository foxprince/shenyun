package cn.anthony.boot.web;

import cn.anthony.boot.domain.SearchModel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class SearchModelSearch extends SearchModel {
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date searchBegin;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date searchEnd;
	Integer size, page;
}
