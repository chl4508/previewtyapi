package com.morpheus.previewtyapi.doc;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document(collection="record")
@Data
public class RecordDoc {
	
	@Id
	private String id;
	
	private String chart;
	
	private String record;
	
	private String type;

	private String date;

	private String description;

	private String[] data;
	


	
}