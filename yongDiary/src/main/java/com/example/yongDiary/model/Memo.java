package com.example.yongDiary.model;

import lombok.Data;

@Data
public class Memo {
	private int		memoNum;
	private int		diaryNum;
	private String	memoContent;
	private	int		memoImport;
	private int		memoHide;
}
