package com.codefriday.bangkokunitrade.activity;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.codefriday.bangkokunitrade.R;
import com.codefriday.bangkokunitrade.adapters.ReportListAdapter;
import com.codefriday.bangkokunitrade.adapters.ReportTopicAdapter;
import com.codefriday.bangkokunitrade.dataset.ReportDetailEntry;
import com.codefriday.bangkokunitrade.dataset.ReportToppicEntry;
import com.codefriday.bangkokunitrade.dataset.ReportlistEntry;
import com.codefriday.bangkokunitrade.dataset.UserDataset;
import com.codefriday.bangkokunitrade.json.ReportDetailPaser;
import com.codefriday.bangkokunitrade.json.ReportPaser;
import com.codefriday.bangkokunitrade.json.ReporttopicPaser;
import com.codefriday.bangkokunitrade.util.Api;
import com.codefriday.bangkokunitrade.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReportActivity extends Activity implements OnTouchListener,OnItemClickListener {
	private TextView textheader,texttopic,textsubject,textDetail,textDate;
	public ImageView imageView,img;
	public File outputFileName;
	private ListView list;
	private EditText editTitle,editDetail;
	public String filePath,topic_id;
	private Spinner spinner;
	private ReportPaser reportPaser;
	private ReportDetailPaser reportDetailPaser;
	private ReportListAdapter reportListAdapter;
	private ReporttopicPaser reporttopicPaser;
	private ReportTopicAdapter adapter;
	private ArrayList<ReportlistEntry> reportlistEntries = new ArrayList<ReportlistEntry>();
	private ArrayList<ReportToppicEntry> reportToppicEntries  = new ArrayList<ReportToppicEntry>();
	private ArrayList<File> listFilePicPath = new ArrayList<File>();
	private Button btnCreate,btnAddPhoto;
	private LinearLayout layout;
	private static final int takePhotoCode = 1;
	AlertDialog helpDialog;
	int targetW = 300;
	int targetH = 300;
	private String strApiCall;
	private AQuery aq;
	private Api api;
	Bitmap originalBitmap;
	File mLastTakenImageAsJPEGFile;
	int FixOneTouch = 0;
	ProgressDialog dialog;
	UserDataset dataset = UserDataset.getInstance();
	int position = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report);

		dialog = ProgressDialog.show(this, "", "Loading. Please wait...", true);
		dialog.setCancelable(true);
		dialog.show();
		
		init();
		
		ApiReportList();

	}

	private void init() {
		api = new Api(this);
		aq = new AQuery(this);
		reporttopicPaser = new ReporttopicPaser();
		reportDetailPaser = new ReportDetailPaser();
		reportPaser = new ReportPaser();
		textheader = (TextView) findViewById(R.id.textheader);
		textheader.setTypeface(Util.getBoldFont(this));
		textheader.setTextSize(30f);
		
		btnCreate = (Button) findViewById(R.id.buttonReport);
		btnCreate.setTypeface(Util.getBoldFont(this));
		btnCreate.setTextSize(30f);
		
		list = (ListView) findViewById(R.id.list);
		
		list.setOnItemClickListener(this);
		
		btnCreate.setOnTouchListener(this);
	}


	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (v.getId()) {
		case R.id.buttonReport:
			
			if (event.getAction() == MotionEvent.ACTION_UP) {
				showPopUpCreate();
				return true;
			}
			break;
			
		case R.id.btnaddpic:
			
			if (event.getAction() == MotionEvent.ACTION_UP) {
				
				if (listFilePicPath.size() < 1) {
					try {
						outputFileName = createImageFile(".jpg");
					} catch (IOException e) {
						e.printStackTrace();
					}
					filePath = outputFileName.getPath();
					
					Intent CameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					CameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputFileName));
					startActivityForResult(CameraIntent, takePhotoCode);
				}else{
					Toast.makeText(this, "Limit 1 Pictures.", Toast.LENGTH_SHORT).show();
				}
				
				return true;
			}
			break;
			
		case R.id.btnsend:
			
			if (event.getAction() == MotionEvent.ACTION_UP) {
				
				if (editTitle.getText().toString().matches("") ) {
					Toast.makeText(ReportActivity.this, "กรุณาใส่หัวข้อเรื่องค่ะ", Toast.LENGTH_SHORT).show();
					return true;
				}
				
				if (editDetail.getText().toString().matches("") ) {
					Toast.makeText(ReportActivity.this, "กรุณาใส่รายละเอียดค่ะ", Toast.LENGTH_SHORT).show();
					return true;
				}
				
				if (FixOneTouch == 0) {
					FixOneTouch++;
					dialog.show();
					UploadFile();
				}else{
					Toast.makeText(this, "At least 1 Pictures", Toast.LENGTH_SHORT).show();
				}
				
				return true;
			}
			break;

		default:
			break;
		}
		return false;
	}
	
	private void showPopUpCreate() {

		final Builder helpBuilder = new Builder(this);
		final LayoutInflater inflater = getLayoutInflater();
		final View view = inflater.inflate(R.layout.layout_create_report, null);
		view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		TextView header = (TextView) view.findViewById(R.id.textheader);
		header.setTypeface(Util.getBoldFont(this));
		header.setTextSize(30f);
		
		editTitle = (EditText) view.findViewById(R.id.editTitle);
		editTitle.setTypeface(Util.getFont(this));
		editTitle.setTextSize(25f);
		
		editDetail = (EditText) view.findViewById(R.id.editDetail);
		editDetail.setTypeface(Util.getFont(this));
		editDetail.setTextSize(25f);
		
		spinner = (Spinner) view.findViewById(R.id.spinner);
		
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				topic_id = String.valueOf(reportToppicEntries.get(position).getId());
		    }
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
		
		ApiReportTopicList();
		
		btnAddPhoto = (Button) view.findViewById(R.id.btnaddpic);
		btnAddPhoto.setOnTouchListener(this);
		btnCreate = (Button) view.findViewById(R.id.btnsend);
		btnCreate.setOnTouchListener(this);
		
		layout = (LinearLayout) view.findViewById(R.id.addpic);
		helpBuilder.setView(view);

		helpDialog = helpBuilder.create();
		helpDialog.show();
	}
	 
	private void showPopUpDetail(ArrayList<ReportDetailEntry> arrayList) {

		final Builder helpBuilder = new Builder(this);
		final LayoutInflater inflater = getLayoutInflater();
		final View view = inflater.inflate(R.layout.layout_report_detail, null);
		view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		textDate  = (TextView) view.findViewById(R.id.textDate);
		textDate.setTypeface(Util.getBoldFont(this));
		textDate.setTextSize(30f);
		
		texttopic = (TextView) view.findViewById(R.id.texttopic);
		texttopic.setTypeface(Util.getBoldFont(this));
		texttopic.setTextSize(30f);
		
		textsubject = (TextView) view.findViewById(R.id.textsubject);
		textsubject.setTypeface(Util.getBoldFont(this));
		textsubject.setTextSize(30f);
		
		textDetail = (TextView) view.findViewById(R.id.textDetail);
		textDetail.setTypeface(Util.getBoldFont(this));
		textDetail.setTextSize(30f);
		
		img = (ImageView) view.findViewById(R.id.img);
		
		textDate.setText(arrayList.get(position).getDate());
		texttopic.setText(arrayList.get(position).getTopic());
		textsubject.setText(arrayList.get(position).getSubject());
		textDetail.setText(arrayList.get(position).getDetail());
		
		aq.id(img).image(arrayList.get(position).getImage(), true, true, 0, 0, null, AQuery.FADE_IN_FILE, AQuery.RATIO_PRESERVE);
		
		Button buttonClose = (Button) view.findViewById(R.id.buttonClose);
		buttonClose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				helpDialog.dismiss();
			}
		});
		helpBuilder.setView(view);

		helpDialog = helpBuilder.create();
		helpDialog.show();
	}
	
	private void ApiReporDetailtList(int id) {
		strApiCall = api.getApiReportDetail(String.valueOf(id));
		aq.ajax(strApiCall, JSONObject.class, new AjaxCallback<JSONObject>() {
			ArrayList<ReportDetailEntry> arrayList = new ArrayList<ReportDetailEntry>();
			@Override
			public void callback(String url, JSONObject json, AjaxStatus status) {
				 try {
					 if (json != null) {
						 arrayList = reportDetailPaser.getData(json);
						 showPopUpDetail(arrayList);
					 }
				 }catch(Exception e){
					 e.printStackTrace();
				 }finally{
					 dialog.dismiss();
				 }
			}
        });
		
	}
	
	private void ApiReportList() {
		strApiCall = api.getApiReportList(dataset.getUser_id());
		aq.ajax(strApiCall, JSONObject.class, new AjaxCallback<JSONObject>() {
			ArrayList<ReportlistEntry> arrayList = new ArrayList<ReportlistEntry>();
			@Override
			public void callback(String url, JSONObject json, AjaxStatus status) {
				 try {
					 if (json != null) {
						 arrayList = reportPaser.getData(json);
						 reportlistEntries = arrayList;
						 reportListAdapter = new ReportListAdapter(ReportActivity.this, arrayList);
						 list.setAdapter(reportListAdapter);
					 }
					 
				 }catch(Exception e){
					 e.printStackTrace();
				 }finally{
					 dialog.dismiss();
				 }
			}
        });
	}
	
	
	private void ApiReportTopicList() {
		strApiCall = api.getApiReportTopicList();
		aq.ajax(strApiCall, JSONObject.class, new AjaxCallback<JSONObject>() {
			ArrayList<ReportToppicEntry> arrayList = new ArrayList<ReportToppicEntry>();
			@Override
			public void callback(String url, JSONObject json, AjaxStatus status) {
				 try {
					 if (json != null) {
						 arrayList = reporttopicPaser.getData(json);
						 reportToppicEntries = arrayList;
						 adapter = new ReportTopicAdapter(ReportActivity.this, arrayList);
						 spinner.setAdapter(adapter);
					 }
					 
				 }catch(Exception e){
					 e.printStackTrace();
				 }
			}
        });
		
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data); 
		
		if (resultCode != RESULT_OK) {
			return;
		}
		switch (requestCode) {

		case takePhotoCode:
			processPhoto();
			break;
		
		default:
			break;
		}
	}
	
	protected void processPhoto() {

		int imageExifOrientation = 0;
		ExifInterface exif;
		try {
			exif = new ExifInterface(outputFileName.getAbsolutePath());
			imageExifOrientation = exif.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
		} catch (IOException e) {
			e.printStackTrace();
		}

		int rotationAmount = 0;

		if (imageExifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
			rotationAmount = 270;
		}
		if (imageExifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
			rotationAmount = 90;
		}
		if (imageExifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
			rotationAmount = 180;
		}

		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(outputFileName.getAbsolutePath(), bmOptions);
		int photoWidth = bmOptions.outWidth;
		int photoHeight = bmOptions.outHeight;

		int scaleFactor = Math.min(photoWidth / targetW, photoHeight / targetH);

		bmOptions.inJustDecodeBounds = false;
		bmOptions.inSampleSize = scaleFactor;
		bmOptions.inPurgeable = true;

		Bitmap scaledDownBitmap = BitmapFactory.decodeFile(
                outputFileName.getAbsolutePath(), bmOptions);

		if (rotationAmount != 0) {
			Matrix mat = new Matrix();
			mat.postRotate(rotationAmount);
			scaledDownBitmap = Bitmap.createBitmap(scaledDownBitmap, 0, 0,
                    scaledDownBitmap.getWidth(), scaledDownBitmap.getHeight(),
                    mat, true);
		}

		FileOutputStream outFileStream = null;
		try {
			mLastTakenImageAsJPEGFile = createImageFile(".jpg");
			outFileStream = new FileOutputStream(mLastTakenImageAsJPEGFile);
			scaledDownBitmap.compress(Bitmap.CompressFormat.JPEG, 100,
					outFileStream);
			
			originalBitmap = scaledDownBitmap;

			// cropImage(outputFileName);
			listFilePicPath.add(mLastTakenImageAsJPEGFile);
			imageView = new ImageView(this);
			imageView.setPadding(5, 5, 5, 5);
			imageView.setLayoutParams(new LayoutParams(400, 400));
			imageView.setImageBitmap(originalBitmap);
			layout.addView(imageView);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private File createImageFile(String fileExtensionToUse) throws IOException {

		File storageDir = new File(
				Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"MyImages");

		if (!storageDir.exists()) {
			if (!storageDir.mkdir()) {
			}
		}
		if (!storageDir.isDirectory()) {
		}

		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = "FOO_" + timeStamp + "_image";

		File image = File.createTempFile(imageFileName, fileExtensionToUse, storageDir);

		return image;
	}
	
	public void UploadFile() {
		// Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        
        String resServer = null;
        String strUrlServer = api.getApiReportCreate();
        
        if (listFilePicPath.size() > 0) {
        	String strSDPath = mLastTakenImageAsJPEGFile.getAbsolutePath();
    		resServer = uploadFiletoServer(strSDPath, strUrlServer);
		}else{
			resServer = uploadFiletoServer(null, strUrlServer);
		}
		

		
		final AlertDialog.Builder ad = new AlertDialog.Builder(this);
		/**
		 * Get result from Server (Return the JSON Code) StatusID = ?
		 * [0=Failed,1=Complete] Error = ? [On case error return custom error
		 * message]
		 * 
		 * Eg Upload Failed = {"StatusID":"0","Error":"Cannot Upload file!"} Eg
		 * Upload Complete = {"StatusID":"1","Error":""}
		 */

		/*** Default Value ***/
		String strStatusID = "0";
		String strError = "Unknow Status!";

		try {
			JSONObject c = new JSONObject(resServer);
			strStatusID = c.getString("code");
			strError = c.getString("message");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		if (strStatusID.equals("0")) {
			ad.setTitle("Error!");
			ad.setIcon(android.R.drawable.btn_star_big_on);
			ad.setMessage(strError);
			ad.setPositiveButton("Close", null);
			ad.show();
		} else {
			Toast.makeText(this, "Upload file Successfully", Toast.LENGTH_SHORT).show();
			listFilePicPath.clear();
			ApiReportList();
		}

	}

	public String uploadFiletoServer(String strSDPath, String strUrlServer) {

		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 1 * 1024 * 1024;
		int resCode = 0;
		String resMessage = "";

		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";

		try {
			
			FileInputStream fileInputStream = null;
			
			if (strSDPath != null) {
				
				/** Check file on SD Card ***/
				File file = new File(outputFileName.getAbsolutePath());
				if (!file.exists()) {
					return "{\"StatusID\":\"0\",\"Error\":\"Please check path on SD Card\"}";
				}
				
				fileInputStream = new FileInputStream(new File(strSDPath));
			}

			URL url = new URL(strUrlServer);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");

			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Content-Type","multipart/form-data;boundary=" + boundary);

			DataOutputStream outputStream = new DataOutputStream(conn.getOutputStream());
			outputStream.writeBytes(twoHyphens + boundary + lineEnd);
			
			outputStream.writeBytes(twoHyphens + boundary + lineEnd);
			outputStream.writeBytes("Content-Disposition: form-data; name=\"user_id\""+ lineEnd);
			outputStream.writeBytes(lineEnd);
			outputStream.writeBytes(String.valueOf(dataset.getUser_id()) + lineEnd);
			
			outputStream.writeBytes(twoHyphens + boundary + lineEnd);
			outputStream.writeBytes("Content-Disposition: form-data; name=\"topic_id\""+ lineEnd);
			outputStream.writeBytes(lineEnd);
			outputStream.writeBytes(topic_id + lineEnd);
			
			outputStream.writeBytes(twoHyphens + boundary + lineEnd);
			outputStream.writeBytes("Content-Disposition: form-data; name=\"subject\""+ lineEnd);
			outputStream.writeBytes(lineEnd);
			outputStream.writeBytes(editTitle.getText().toString()  + lineEnd);
			
			outputStream.writeBytes(twoHyphens + boundary + lineEnd);
			outputStream.writeBytes("Content-Disposition: form-data; name=\"detail\""+ lineEnd);
			outputStream.writeBytes(lineEnd);
			outputStream.writeBytes(editDetail.getText().toString()  + lineEnd);
			
			if (listFilePicPath.size() > 0) {
				
				outputStream.writeBytes(twoHyphens + boundary + lineEnd);
				outputStream.writeBytes("Content-Disposition: form-data; name=\"fileUpload\";filename=\"" + strSDPath +"\"" + lineEnd);
	             
				outputStream.writeBytes(lineEnd);

				bytesAvailable = fileInputStream.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				buffer = new byte[bufferSize];

				// Read file
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);

				while (bytesRead > 0) {
					outputStream.write(buffer, 0, bufferSize);
					bytesAvailable = fileInputStream.available();
					bufferSize = Math.min(bytesAvailable, maxBufferSize);
					bytesRead = fileInputStream.read(buffer, 0, bufferSize);
				}
			}

			outputStream.writeBytes(lineEnd);
			outputStream.writeBytes(twoHyphens + boundary + twoHyphens+ lineEnd);

			// Response Code and Message
			resCode = conn.getResponseCode();
			if (resCode == HttpURLConnection.HTTP_OK) {
				InputStream is = conn.getInputStream();
				ByteArrayOutputStream bos = new ByteArrayOutputStream();

				int read = 0;
				while ((read = is.read()) != -1) {
					bos.write(read);
				}
				byte[] result = bos.toByteArray();
				bos.close();

				resMessage = new String(result);
			}

			Log.d("resCode=", Integer.toString(resCode));
			Log.d("resMessage=", resMessage.toString());

			if (fileInputStream != null) {
				fileInputStream.close();
			}
			
			outputStream.flush();
			outputStream.close();

			return resMessage.toString();

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}finally{
			FixOneTouch--;
			helpDialog.dismiss();
			
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
	
		ApiReporDetailtList(reportlistEntries.get(position).getId());
	}


}
