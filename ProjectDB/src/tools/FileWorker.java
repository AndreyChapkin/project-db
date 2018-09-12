package tools;

import javax.servlet.http.Part;

import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class FileWorker {

	public FileWorker() {}
	
	public static String extractFileName (Part part) {
		 String contentDisp = part.getHeader("content-disposition");
	     String[] items = contentDisp.split(";");
	     for (String s : items) {
	         if (s.trim().startsWith("filename")) {
	             String fileName = s.substring(s.indexOf("=") + 2, s.length() - 1);
	             fileName = fileName.replace("\\", "/");
	             int i = fileName.lastIndexOf('/');
	             return fileName.substring(i + 1);
	         }
	     }
	     return null;
	}
	
	public static String extractFileType (String fileName) {

		String fileType = fileName.substring(fileName.lastIndexOf(".")+1);
		return fileType;
	}
	
	public static byte[] extractFileBytes(Part part) throws IOException {
		InputStream is = part.getInputStream();
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		byte[] buffer = new byte[0xFFFF];
		for (int len = is.read(buffer); len!=-1; len=is.read(buffer)) {
			os.write(buffer);
		}
		return os.toByteArray();
	}
}
