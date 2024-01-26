package com.example.yongDiary.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemoDaoImpl implements MemoDao{
	private static SqlSession session;
}
