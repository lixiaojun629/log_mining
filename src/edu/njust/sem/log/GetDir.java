package edu.njust.sem.log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.junit.Test;

import edu.njust.sem.log.util.ConnectUtil;

public class GetDir {
	private static Document doc;
	public static String getHtmlByUrl(String url) throws Exception {
		String html = null;
		DefaultHttpClient httpClient = new DefaultHttpClient();// 创建httpClient对象
		HttpGet httpget = new HttpGet(url);// 以get方式请求该URL
		try {
			HttpResponse responce = httpClient.execute(httpget);// 得到responce对象
			int resStatu = responce.getStatusLine().getStatusCode();// 返回码
			if (resStatu == HttpStatus.SC_OK) {// 200正常 其他就不对
				// 获得相应实体
				HttpEntity entity = responce.getEntity();
				if (entity != null) {
					html = EntityUtils.toString(entity);// 获得html源代码
				}
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		//System.out.println(html);
		doc = Jsoup.parse(html);
		Elements es = doc.select("title");
		Element e = es.first();
		if (e.ownText().contains("Access Denied")) {
			System.out.println("已被拉入黑名单");
			ConnectUtil.reConnAdsl();
			getHtmlByUrl(url);
		}
		return html;
	}
	public static  String getDirFromHtml(String html) throws Exception{
		String dir = "";
		Elements elements;
		Element elementDiv;
		Element elementLastA;
		
		Node nodeDir;
		try {
			elements = doc.select("div.crumb");
			if (elements.size() > 1) {
				System.out.println("目录提取异常，网页中存在多个目录");
			}
			elementDiv = elements.first();
			elements = elementDiv.select("a[href]");
			elementLastA = elements.last();
			nodeDir = elementLastA.nextSibling();
			dir = nodeDir.outerHtml();
		} catch (Exception e) {
			nodeDir = null;
			elementLastA = null;
			elementDiv = null;
			elements = null;
			doc = null;
			throw new Exception(e);
		}

		dir = dir.replaceAll("&raquo;", "");
		dir = dir.replaceAll("&amp;", "&");
		dir = dir.replaceAll("Product List", "");
		dir = dir.replaceAll("&quot;", "\"");
		dir = dir.trim();
		return dir;
	}
	
}
