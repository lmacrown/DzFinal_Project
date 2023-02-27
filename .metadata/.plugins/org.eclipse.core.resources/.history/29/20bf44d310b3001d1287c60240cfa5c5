package com.myspring.DZ_shop.mappers;

import java.util.List;

import com.myspring.DZ_shop.entity.MemberVO;
import com.myspring.DZ_shop.entity.QnAVO;

public interface MemberDAO {
	MemberVO selectView(String MEMBER_ID, String MEMBER_PW);

	String selectId(String email);

	void deleteId(String MEMBER_ID);

	void update(String MEMBER_ID, String MEMBER_PW, String MEMBER_NAME, String EMAIL);

	void register(String MEMBER_ID, String MEMBER_PW, String MEMBER_NAME, String EMAIL);

	MemberVO dupUidCheck(String MEMBER_ID);

	List<MemberVO> listMembers();

	void deleteMembers(String MEMBER_ID);

	void QnAUpload(QnAVO qnAVO);

	int findPwd(String uid, String email);

	void pwdFix(String Member_Id, String member_PW);

	void addPreSearch(String text, String memberId);

	List<String> searchPreSearch(String memberId);
}
