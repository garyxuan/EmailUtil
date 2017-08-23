package com.wondertek.email;

import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.util.MailSSLSocketFactory;

/**
 * 简单的邮件发送工具类 reference mail.jar and  activation.jar
 * @version 1.0.0
 * @author garyxuan
 * @see <a href="http://www.oracle.com/technetwork/java/javamail/index.html">download JavaMail</a>
 * @see <a href="http://www.oracle.com/technetwork/articles/java/index-135046.html">download JAF</a>
 *
 */
public class EmailUtil {

	public EmailUtil() {}
	
	/**
	 * 常用的邮件服务器主机地址
	 * @author garyxuan
	 *
	 */
	public static class Host {
		//网易163邮箱
		public static final  String  _163  = "smtp.163.com";
		//腾讯QQ邮箱
		public static final  String  qq  = "smtp.qq.com";
		//新浪免费邮箱
		public static final  String sina  = "smtp.163.net";
		//腾讯企业邮箱
		public static final  String exqq = "smtp.exmail.qq.com";
		//雅虎免费邮箱
		public static final  String yahoo = "smtp.mail.yahoo.cn";
		//网易126邮箱
		public static final  String _126 = "smtp.126.com";
		//搜狐免费邮箱
		public static final  String sohu = "smtp.sohu.com";
		//谷歌邮箱
		public static final  String gmail = "smtp.gmail.com";
	}
	
	/**
	 * 邮件正文消息类型
	 * @author garyxuan
	 *
	 */
	public enum ContentType {
		GENERAL,//普通文本
		TEXT_HTML,//网页
	}
	
	/**
	 * 发送邮件
	 * @param from 发件人邮箱
	 * @param to 收件人邮箱
	 * @param host 主机地址  {@link EmailUtil.Host}
	 * @param password 邮箱密码
	 * @param subject 主题
	 * @param message 消息体
	 * @param contentType 消息类型 {@link EmailUtil.ContentType}
	 */
	public static void sendEmail(String from, String to, String host, String password, String subject
			, String message, ContentType contentType) {
		// 获取系统属性
		Properties properties = System.getProperties();
		
		// 设置邮件服务器
		properties.setProperty("mail.smtp.host", host);
		
		//设置为需要授权
		properties.put("mail.smtp.auth", "true");
		
		// 获取默认session对象
		Session session = Session.getDefaultInstance(properties, new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password); //发件人邮件用户名、密码
			}
		});
		
		try {
			// 创建默认的 MimeMessage 对象
			MimeMessage mimeMessage = new MimeMessage(session);
			
			// Set From: 发件人
			mimeMessage.setFrom(new InternetAddress(from));
			
			// Set To: 收件人
			mimeMessage.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));
			
			// Set Subject: 标题
			mimeMessage.setSubject(subject);
			
			// 设置消息体
			if (contentType == ContentType.GENERAL) {
				mimeMessage.setText(message);
			} else if (contentType == ContentType.TEXT_HTML) {
				mimeMessage.setContent(message,"text/html;charset=UTF-8");
			}
			
			// 发送消息
			Transport.send(mimeMessage);
			
			System.out.println("Sent message successfully....from " + from + " to " + to);
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 发送邮件 使用SSL加密
	 * @param from 发件人邮箱
	 * @param to 收件人邮箱
	 * @param host 主机地址  {@link EmailUtil.Host}
	 * @param password 邮箱密码
	 * @param subject 主题
	 * @param message 消息体
	 * @param contentType 消息类型 {@link EmailUtil.ContentType}
	 * @throws GeneralSecurityException GeneralSecurityException
	 */
	public static void sendEmailWithSSL(String from, String to, String host, String password, String subject
			, String message, ContentType contentType) throws GeneralSecurityException {
		// 获取系统属性
		Properties properties = System.getProperties();
		
		// 设置邮件服务器
		properties.setProperty("mail.smtp.host", host);
		
		//设置为需要授权
		properties.put("mail.smtp.auth", "true");
		
		//SSL加密工厂
		MailSSLSocketFactory sf = new MailSSLSocketFactory();
		
		//信任所有主机
		sf.setTrustAllHosts(true);
		
		//设置为打开ssl加密
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.ssl.socketFactory", sf);
		
		// 获取默认session对象
		Session session = Session.getDefaultInstance(properties, new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password); //发件人邮件用户名、密码
			}
		});
		
		try {
			// 创建默认的 MimeMessage 对象
			MimeMessage mimeMessage = new MimeMessage(session);
			
			// Set From: 发件人
			mimeMessage.setFrom(new InternetAddress(from));
			
			// Set To: 收件人
			mimeMessage.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));
			
			// Set Subject: 标题
			mimeMessage.setSubject(subject);
			
			// 设置消息体
			if (contentType == ContentType.GENERAL) {
				mimeMessage.setText(message);
			} else if (contentType == ContentType.TEXT_HTML) {
				mimeMessage.setContent(message,"text/html;charset=UTF-8");
			}
			
			// 发送消息
			Transport.send(mimeMessage);
			
			System.out.println("Sent message successfully....from " + from + " to " + to);
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
