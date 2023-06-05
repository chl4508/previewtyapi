package com.morpheus.previewtyapi.doc;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document(collection="fs.chunks")
public class ChunksDoc {
	
	@Id
	private String id;
	
	private byte[] data;
	
	private String files_id;
	
	

	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getFiles_id() {
		return files_id;
	}

	public void setFiles_id(String files_id) {
		this.files_id = files_id;
	}

	
	
}