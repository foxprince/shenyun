package cn.anthony.boot.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mysema.query.annotations.QueryEntity;

import lombok.Data;

@QueryEntity
@Entity
@Data
public class SearchField extends GenericEntity {
    @ManyToOne(cascade = { CascadeType.DETACH }, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "search_id")
    @JsonIgnore
    private SearchModel searchModel;
    public String skey, svalue;

    public SearchField(String key, String value) {
	super();
	this.skey = key;
	this.svalue = value;
    }
}
