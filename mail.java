package mailchimp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import org.apache.commons.codec.binary.Base64;

public class mail {

	public static void main(String[] args) throws Exception {

		sendPost();

	}

	private static void sendPost() throws Exception {

		String url = "https://mandrillapp.com/api/1.0/messages/send.json";
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		
		con.setRequestMethod("POST");
		
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		String key = "";
		String html_body = "";
		String subject = "";
		String sender_email = "";
		String sender_name = "";
		String file_type = "";
		String file_name = "";
		
		String receiver_email = "";
		String receiver_name = "";
		String file_content=encodeFileToBase64Binary("test_file.java");
		
		
		String urlParameters = "{\"key\": \""+key+"\",\"message\": {\"html\": \""+html_body+"\",\"subject\": \" "+subject+" \",\"from_email\":\""+sender_email+"\",\"from_name\": \""+sender_name+"\",\"attachments\": [{\"type\": \""+file_type+"\",\"name\": \""+file_name+"\",\"content\": \""+file_content+"\"}],\"to\": [{\"email\": \""+receiver_email+"\",\"name\": \""+receiver_name+"\"}]},\"async\": false}";
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		//print result
		System.out.println(response.toString());

	}
	

	
	private static String encodeFileToBase64Binary(String fileName)
			throws IOException {

		File file = new File(fileName);
		byte[] bytes = loadFile(file);
		
		String encodedString = Base64.encodeBase64String(bytes);
		
		return encodedString;
	}

	@SuppressWarnings("resource")
	private static byte[] loadFile(File file) throws IOException {
	    InputStream is = new FileInputStream(file);

	    long length = file.length();
	    if (length > Integer.MAX_VALUE) {
	        // File is too large
	    }
	    byte[] bytes = new byte[(int)length];
	    
	    int offset = 0;
	    int numRead = 0;
	    while (offset < bytes.length
	           && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
	        offset += numRead;
	    }

	    if (offset < bytes.length) {
	        throw new IOException("Could not completely read file "+file.getName());
	    }

	    is.close();
	    return bytes;
	}

}