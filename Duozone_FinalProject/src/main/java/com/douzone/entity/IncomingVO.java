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
	  	private String earner_code;
	  	private String count;
	    private String worker_id;
	    private String div_code;
	    private String earner_name;
	    private String residence_status;
	    private String is_native;
	    private String personal_no;
	    private String zipcode;
	    private String address;
	    private String address_detail;
	    private String email1;
	    private String email2;
	    private String tel1;
	    private String tel2;
	    private String tel3;
	    private String phone1;
	    private String phone2;
	    private String phone3;
	    private String etc;
	    private String is_tuition;
	    private String deduction_amount;
	    private String is_artist;
	    private String artist_type;
	    private String ins_reduce;
	    private String create_date;
	    private String tax_id;
	    private String tax_rate;
	    private String total_payment;
	    private String tax_income;
	    private String tax_local;
	    private String tax_total;
	    private String real_payment;
	    private String payment_ym;
	    private String payment_date;
	    private String accrual_ym;
	    private String repay_amount;
	    private String artist_cost;
	    private String ins_cost;
}
