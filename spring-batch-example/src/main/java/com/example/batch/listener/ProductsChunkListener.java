package com.example.batch.listener;

import org.springframework.batch.core.ChunkListener;

public class ProductsChunkListener implements ChunkListener {

	public ProductsChunkListener() {
	}

	@Override
	public void beforeChunk() {
		System.out.println("before chunk");
	}

	@Override
	public void afterChunk() {
		System.out.println("after chunk");
	}
}