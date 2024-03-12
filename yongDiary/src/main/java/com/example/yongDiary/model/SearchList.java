package com.example.yongDiary.model;

import java.util.Date;

import lombok.Data;

@Data
public class SearchList {
	private int 	memNum;
	private String 	keyword;
	private Date    searchDate;
	private int     delState;
}
