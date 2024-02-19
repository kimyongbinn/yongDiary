package com.example.yongDiary.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;


@Configuration
public class TilesConfig {

	// tile 설정할 xml 파일 경로 설정
	@Bean
	public TilesConfigurer tilesConfigurer() {
		final TilesConfigurer tilesConfigurer = new TilesConfigurer();
		tilesConfigurer.setDefinitions(new String[] {
				"/WEB-INF/tiles.xml"
		});
		tilesConfigurer.setCheckRefresh(true);
		return tilesConfigurer;
	}
	@Bean
	public TilesViewResolver tilesViewResolver() {
		final TilesViewResolver tilesViewResolver = new TilesViewResolver();
		tilesViewResolver.setViewClass(TilesView.class);
		tilesViewResolver.setOrder(1);
		return tilesViewResolver;
	}
}
