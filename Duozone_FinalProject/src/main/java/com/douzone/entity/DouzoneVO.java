package com.douzone.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DouzoneVO {
	private String worker_id;
	private String worker_passwd;
	private String corp_code;
	private String worker_name;
}
