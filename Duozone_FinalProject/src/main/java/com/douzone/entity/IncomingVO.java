package com.douzone.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IncomingVO {
	private String earner_no;
	private String worker_id;
	private String corp_cd;
	private String earn_div_cd;
	private String earn_cd;
	private String resi_div;
	private String nation_div;
	private String earner_name;
	private String ssn1;
	private String ssn2;
	private String address;
	private String address_det;
	private String cable_phone1;
	private String cable_phone2;
	private String cable_phone3;
	private String cell_phone1;
	private String cell_phone2;
	private String cell_phone3;
	private String email_1;
	private String email_2;
	private String st_loan_dis;
	private String st_loan_price;
	private String artist_yn;
	private String artist_type;
	private String artist_ins_red;
	private String total_payment;
	private String tariff;
	private String artist_expenses;
	private String st_loan_payment;
	private String emp_ins;
	private String total_tax;
	private String dif_payment;
	private String belonging_ym;
	private String payment_date;
	private String etc;
	private String zonecode;
}
