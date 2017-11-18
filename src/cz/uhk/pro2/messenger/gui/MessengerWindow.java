package cz.uhk.pro2.messenger.gui;

import java.awt.BorderLayout;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Timer;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;

import cz.uhk.pro2.messages.services.HttpMessagesService;
import cz.uhk.pro2.messenger.model.Message;

public class MessengerWindow extends JFrame {

	private List<Message> messages = new ArrayList<>();
	private MessagesTableModel model = new MessagesTableModel(messages);
	private JTable tab = new JTable(model);
	private HttpMessagesService service = new HttpMessagesService("kriz");
	private JTextField txtNewMessage = new JTextField(20);
	private JTextField txtRecipient = new JTextField(10);
	public MessengerWindow() {

		// dodelat tabulku, kde budou jednotlive zpravy
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("MESSENGER");
		// String x;
		add(new JScrollPane(tab), BorderLayout.CENTER);

		tab.setAutoCreateRowSorter(true);
		JPanel pnl = new JPanel();
		JLabel lblTO = new JLabel("To:");
		JButton btnSend = new JButton("Odeslat");
		btnSend.addActionListener(e -> sendMessage());
		pnl.add(txtNewMessage);
		pnl.add(lblTO);
		pnl.add(txtRecipient);
		pnl.add(btnSend);
		add(pnl, BorderLayout.SOUTH);

		pack();

		/*
		 * try { // http pozadavek na server x =
		 * Request.Get("http://www.uhk.cz").execute().returnContent().asString();
		 * System.out.println(x);
		 * 
		 * } catch (ClientProtocolException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (IOException e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); } testovaci message
		 * 
		 * messages.add(new Message(LocalTime.now(), "Ahoj", "Karel", "All"));
		 * 
		 * messages.add(new Message(LocalTime.now(), "Hi", "Joe", "All"));
		 */
		refresh();
		// dalsi vlakno 1000 kazdou sekundu
		
		Timer t = new Timer(5000, e -> refresh());
		// zapnu to vlakno (musim pokazde)
		t.start();

	}

	private void refresh() {
		// naplnime tabulku ze serveru
		// aktualizujeme tabulku 
		messages = service.getAllMessages();
		model.setMessages(messages);

	}

	private void sendMessage() {
		// datum si server pridava sam
								// ziska username ze systemu
		service.sendMessage(System.getProperty("user.name"),
				txtRecipient.getText(),
				txtNewMessage.getText());
		// vymazeme obsah textoveho pole
		txtNewMessage.setText("");
		txtRecipient.setText("");
		// aby se nam to rovnou nacetlo
		refresh();

	}

	public static void main(String[] args) {
		MessengerWindow mw = new MessengerWindow();
		mw.setVisible(true);

	}

}