package cz.uhk.pro2.messenger.model;

import java.time.LocalDateTime;

public class Message {

 private LocalDateTime time;
 private String text;
 private String from;
 private String to;
public Message()
{}
 public Message(LocalDateTime time, String text, String from, String to) {
	super();
	this.time = time;
	this.text = text;
	this.from = from;
	this.to = to;
}
@Override
public String toString() {
	return "Message [time=" + time + ", text=" + text + ", from=" + from + ", to=" + to + "]";
}

public LocalDateTime getTime() {
	return time;
}
public void setTime(LocalDateTime time) {
	this.time = time;
}
public String getText() {
	return text;
}
public void setText(String text) {
	this.text = text;
}
public String getFrom() {
	return from;
}
public void setFrom(String from) {
	this.from = from;
}
public String getTo() {
	return to;
}
public void setTo(String to) {
	this.to = to;
}
}
