package entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import static javax.persistence.TemporalType.TIMESTAMP;
import entities.User;

@Entity
@Table(name = "Message")
@NamedQuery(name = "AllMessages", query = "select mes from Message mes")
public class Message implements Serializable {

	private static final long serialVersionUID = 1L;

	public Message() {
	}

	public Message(String title, String text, Date sendTime, User sender, User recipient) {
		this.title = title;
		this.text = text;
		this.sendTime = sendTime;
		this.sender = sender;
		this.recipient = recipient;
		this.readedMes = false;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MES_ID")
	private int mesId;

	@Column(name = "TEXT")
	private String text;

	@Temporal(TIMESTAMP)
	@Column(name = "SEND_TIME")
	private Date sendTime;

	@Column(name = "TITLE")
	private String title;

	@Column(name= "READED_MES")
	private Boolean readedMes;

	@ManyToOne
	@JoinColumn(name = "SENDER_ID")
	private User sender;

	@ManyToOne
	@JoinColumn(name = "RECIPIENT_ID")
	private User recipient;

	public int getMesId() {
		return mesId;
	}

	public void setMesId(int param) {
		this.mesId = param;
	}

	public String getText() {
		return text;
	}

	public void setText(String param) {
		this.text = param;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date param) {
		this.sendTime = param;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String param) {
		this.title = param;
	}

	public Boolean getReadedMes() {
		return readedMes;
	}

	public void setReadedMes(Boolean param) {
		this.readedMes = param;
	}

	public User getSender() {
	    return sender;
	}

	public void setSender(User param) {
	    this.sender = param;
	}

	public User getRecipient() {
	    return recipient;
	}

	public void setRecipient(User param) {
	    this.recipient = param;
	}

}
