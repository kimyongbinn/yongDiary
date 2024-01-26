package com.example.yongDiary.model;

import java.util.Date;

import lombok.Data;

@Data
public class Diary {
	private int		diaryNum;
	private int		memNum;
	private String	diaryTitle;
	private Date	diaryRegDate;
	private Date	diaryUpDate;
	private String	diaryContent;
	private String	diaryImage;
	private int		diarySecret;
}
