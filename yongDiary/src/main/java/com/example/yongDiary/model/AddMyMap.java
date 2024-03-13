package com.example.yongDiary.model;

import java.util.Date;

import lombok.Data;

@Data
public class AddMyMap {
	private int 	placeNum;
	private int 	memNum;
	private String 	placeName;
	private Date	addDate;
	private int 	delState;
}
