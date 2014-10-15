package com.codefriday.bangkokunitrade.util;

import android.content.Context;
import android.util.Log;

public class Api {

	private Context context;
	private String TAG = getClass().getSimpleName();

	public Api(Context c) {
		context = c;
	}

	// post
	public String getApiauth() {
		StringBuilder br = new StringBuilder();
		br.append("http://apps.bucmedical.com/api/auth/verify/format/json");
		Log.d("getApiauth", br.toString());
		return br.toString();
	}

	public String getApiSendOrder() {
		StringBuilder br = new StringBuilder();
		br.append("http://apps.bucmedical.com/api/order/send/format/json");
		Log.d("getApiSendOrder", br.toString());
		return br.toString();
	}

	public String getApiOrderStatus() {
		StringBuilder br = new StringBuilder();
		br.append("http://apps.bucmedical.com/api/order/status/format/json");
		Log.d("getApiOrderStatus", br.toString());
		return br.toString();
	}
	
	public String getApiToolSet() {
		StringBuilder br = new StringBuilder();
		br.append("http://apps.bucmedical.com/api/tools/list/format/json");
		Log.d("getApiToolSet", br.toString());
		return br.toString();
	}
	
	public String getApiQuotationRequest() {
		StringBuilder br = new StringBuilder();
		br.append("http://apps.bucmedical.com/api/quotations/request/format/json");
		Log.d("getApiQuotationRequest", br.toString());
		return br.toString();
	}
	
	public String getApiSendMail() {
		StringBuilder br = new StringBuilder();
		br.append("http://apps.bucmedical.com/api/mail/send/format/json");
		Log.d("getApiSendMail", br.toString());
		return br.toString();
	}
	
	public String getApiCaseInform() {
		StringBuilder br = new StringBuilder();
		br.append("http://apps.bucmedical.com/api/cases/inform/format/json");
		Log.d("getApiCaseInform", br.toString());
		return br.toString();
	}
	
	public String getApiReportCreate() {
		StringBuilder br = new StringBuilder();
		br.append("http://apps.bucmedical.com/api/report/create/format/json");
		Log.d("getApiReportCreate", br.toString());
		return br.toString();
	}
	
	public String getApiOrderCancel() {
		StringBuilder br = new StringBuilder();
		br.append("http://apps.bucmedical.com/api/order/cancel/format/json");
		Log.d("getApiOrderCancel", br.toString());
		return br.toString();
	}
	
	public String getApiOrderUpdate() {
		StringBuilder br = new StringBuilder();
		br.append("http://codelo.gs/p/bangkokunitrade/api/order/update/format/json");
		Log.d("getApiOrderUpdate", br.toString());
		return br.toString();
	}
	
	

	// get
	public String getApiMasterImplant() {
		StringBuilder br = new StringBuilder();
		br.append("http://apps.bucmedical.com/api/implants/setlist/format/json");
		Log.d("getApiMasterImplant", br.toString());
		return br.toString();
	}
	
	
	public String getApiorderDetail(String order_id) {
		StringBuilder br = new StringBuilder();
		br.append("http://apps.bucmedical.com/api/order/info/id/");
		br.append(order_id);
		br.append("/format/json");
		Log.d("getApiorderDetail", br.toString());
		return br.toString();
	}
	
	public String getApiImplantQuotation(String hospital_id,String user_id) {
		StringBuilder br = new StringBuilder();
		
		br.append("http://apps.bucmedical.com/api/quotations/implant/hospital_id/");
		br.append(hospital_id);
		br.append("/user_id/");
		br.append(user_id);
		br.append("/format/json");
		Log.d("getApiImplantQuotation", br.toString());
		return br.toString();
	}

	public String getApiHospital(String id) {
		StringBuilder br = new StringBuilder();
		br.append("http://apps.bucmedical.com/api/hospital/data/id/");
		br.append(id);
		br.append("/format/json");
		Log.d("getApiHospital", br.toString());
		return br.toString();
	}
	
	public String getApiDoctorList(String hospital_id) {
		StringBuilder br = new StringBuilder();
		br.append("http://apps.bucmedical.com/api/hospital/doctor/hospital_id/");
		br.append(hospital_id);
		br.append("/format/json");
		Log.d("getApiDoctorList", br.toString());
		return br.toString();
	}
	
	public String getApiDoctorsTools(String doctor_id) {
		StringBuilder br = new StringBuilder();
		br.append("http://apps.bucmedical.com/api/doctors/tools/id/");
		br.append(doctor_id);
		br.append("/format/json");
		Log.d("getApiDoctorsTools", br.toString());
		return br.toString();
	}
	
	public String getApiToolSetImplant() {
		StringBuilder br = new StringBuilder();
		br.append("http://apps.bucmedical.com/api/implants/tools/format/json");
		Log.d("getApiToolSetImplant", br.toString());
		return br.toString();
	}

	public String getApiDetailMasterImplant(String id) {
		StringBuilder br = new StringBuilder();
		br.append("http://apps.bucmedical.com/api/implants/set/id/");
		br.append(id);
		br.append("/format/json");
		Log.d("getApiDetailMasterImplant", br.toString());
		return br.toString();
	}


	public String getApiCaseDetail(String id) {
		StringBuilder br = new StringBuilder();
		br.append("http://apps.bucmedical.com/api/cases/detail/case_id/");
		br.append(id);
		br.append("/format/json");
		Log.d("getApiCaseDetail", br.toString());
		return br.toString();
	}
	
	public String getApiTramuaList(String position) {
		StringBuilder br = new StringBuilder();
		br.append("http://apps.bucmedical.com/api/traumas/list/position/");
		br.append(position);
		br.append("/format/json");
		Log.d("getApiTramuaList", br.toString());
		return br.toString();
	}
	
	public String getApiTramuaSetList(String id) {
		StringBuilder br = new StringBuilder();
		br.append("http://apps.bucmedical.com/api/traumas/implant/id/");
		br.append(id);
		br.append("/format/json");
		Log.d("getApiTramuaSetList", br.toString());
		return br.toString();
	}
	
	public String getApiBiomaterialsSetList() {
		StringBuilder br = new StringBuilder();
		br.append("http://apps.bucmedical.com/api/biomaterials/list/format/json");
		Log.d("getApiBiomaterialsSetList", br.toString());
		return br.toString();
	}
	
	public String getApiTramuaSetListIndicator(String id) {
		StringBuilder br = new StringBuilder();
		br.append("http://apps.bucmedical.com/api/traumas/indication/id/");
		br.append(id);
		br.append("/format/json");
		Log.d("getApiTramuaSetList", br.toString());
		return br.toString();
	}
	
	public String getApiTransportsType() {
		StringBuilder br = new StringBuilder();
		br.append("http://apps.bucmedical.com/api/transports/list/format/json");
		Log.d("getApiTransportsType", br.toString());
		return br.toString();
	}
	
	public String getApiImplantsListbyTraumasID(String tramua_id) {
		StringBuilder br = new StringBuilder();
		br.append("http://apps.bucmedical.com/api/traumas/implant/id/");
		br.append(tramua_id);
		br.append("/format/json");
		Log.d("getApiImplantsListbyTraumasID", br.toString());
		return br.toString();
	}
	
	public String getApiCatalogs() {
		StringBuilder br = new StringBuilder();
		br.append("http://apps.bucmedical.com/api/catalogs/list/format/json");
		Log.d("getApiCatalogs", br.toString());
		return br.toString();
	}
	
	public String getApiCasesList(String hospital_id) {
		StringBuilder br = new StringBuilder();
		br.append("http://apps.bucmedical.com/api/cases/list/hospital/");
		br.append(hospital_id);
		br.append("/format/json");
		Log.d("getApiCasesList", br.toString());
		return br.toString();
	}
	
	public String getApiCasedetail(String case_id) {
		StringBuilder br = new StringBuilder();
		br.append("http://apps.bucmedical.com/api/cases/detail/id/");
		br.append(case_id);
		br.append("/format/json");
		Log.d("getApiCasedetail", br.toString());
		return br.toString();
	}
	

	
	public String getApiReportList(String user_id) {
		StringBuilder br = new StringBuilder();
		br.append("http://apps.bucmedical.com/api/report/list/user_id/");
		br.append(user_id);
		br.append("/format/json");
		Log.d("getApiReportList", br.toString());
		return br.toString();
	}
	
	public String getApiReportDetail(String id) {
		StringBuilder br = new StringBuilder();
		br.append("http://apps.bucmedical.com/api/report/detail/id/");
		br.append(id);
		br.append("/format/json");
		Log.d("getApiReportDetail", br.toString());
		return br.toString();
	}

	
	public String getApiReportTopicList() {
		StringBuilder br = new StringBuilder();
		br.append("http://apps.bucmedical.com/api/report/topic/format/json");
		Log.d("getApiReportTopicList", br.toString());
		return br.toString();
	}
	
	
	
	

	
}
