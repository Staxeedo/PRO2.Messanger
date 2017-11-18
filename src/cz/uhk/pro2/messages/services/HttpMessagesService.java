package cz.uhk.pro2.messages.services;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

import com.sun.xml.internal.ws.api.pipe.ThrowableContainerPropertySet;

import cz.uhk.pro2.messenger.model.Message;

public class HttpMessagesService {
	// nacte co vsechno je v tom seznamu
	private String room;

	public HttpMessagesService(String room) {
		this.room = room;
	}

	//
	public List<Message> getAllMessages() {
		List<Message> mes = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.");
		try {
			String result = Request.Get("http://uhk.alesberger.cz/chat/?room=" + room).execute().returnContent()
					.asString();

			for (String line : result.split("\n")) {
				// System.out.println("Radek: "+ line);
				String[] attributes = line.split(";");

				if (attributes[2].equals("All") || attributes[2].equals(System.getProperty("user.name"))) {
					Message m = new Message();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.");
					// unix time stamp, nebo parseLong
					String date = sdf.format(new Date(1000 * Long.valueOf(attributes[0])));
					// System.out.println(date);
					LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);

					m.setTime(localDateTime);
					m.setFrom(attributes[1]);
					m.setTo(attributes[2]);
					m.setText(attributes[3]);

					mes.add(m);

				}
			}
			return mes;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			// vyhodime zaobalenou vyjimku --rethrow
			// tato vyjimka neni povinne zachytavana
			// misto RuntimeException muzu vytvorit vlastni vyjimku(vlastne udelanou)
			throw new RuntimeException(e);
		} catch (IOException e) {
			// vyhodime zaobalenou vyjimku --rethrow
			throw new RuntimeException(e);
		}
	}

	public void sendMessage(String from, String to, String txt) {
		// apatche http fluent Form, posilame to jako form z html

		List<NameValuePair> formData = Form.form().add("sender", from).add("recipient", to).add("message", txt).build();

		try {
			Request.Post("http://uhk.alesberger.cz/chat/send.php?room=" + room).bodyForm(formData).execute();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

}
