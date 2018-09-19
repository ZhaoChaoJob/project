package com.geotmt.commons.utils.http;

import org.apache.commons.lang.StringUtils;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * http访问工具类
 * 
 * @author wushujia
 * 
 */
public class HttpUtil {
	private final static Logger log = LoggerFactory.getLogger(HttpUtil.class);
	private static ThreadLocal<Integer> httpState = new ThreadLocal<Integer>();  // http 本次请求返回的状态码
	private static ThreadLocal<CookieStore> httpCookieStore = new ThreadLocal<CookieStore>();  // http 本次请求返回的cookie   httpclientRs 用这个方法时有效
	private static ThreadLocal<String> httpCookieString = new ThreadLocal<String>();  // http 本次请求返回的cookie   getRs 用这个方法时有效
	public static void removeThreadLocal(){
		httpState.remove();
		httpCookieStore.remove();
		httpCookieString.remove();
	}
	/**
	 * 使用完立马回收避免内存泄漏或者因为线程池原因造成别的线程读到上个线程的值
	 * @return
	 */
	public static Integer getHttpState(){
		Integer state = httpState.get();
		/// httpState.remove();
		return state ;
	}
	public static CookieStore getHttpCookieStore(){
		CookieStore cookieStore = httpCookieStore.get() ;
		// httpCookieStore.remove();
		return cookieStore ;
	}
	public static String getHttpCookieString(){
		String cookies = httpCookieString.get() ;
		// httpCookieString.remove();
		return cookies ;
	}
	
	
	private HttpUtil() {
	}


	public static String readCookies(URLConnection url_con){
		StringBuilder cookieSb = new StringBuilder();
		List<String> cookieList = url_con.getHeaderFields().get("Set-Cookie"); // 响应cookie头
		if(cookieList != null){
			for(String cookie : cookieList){
				//cookieSb.append(cookie).append(";") ;
				
				if(cookie!=null&&!"".equals(cookie)){
					int p = cookie.indexOf("=");
					if(p>0){
						String key = cookie.substring(0, p);
						int vp = cookie.indexOf(";") ;
						if(vp<=0){
							vp = cookie.length() ;
						}
						String value = cookie.substring(p+1, vp) ;
						cookieSb.append(key).append("=").append(value).append(";") ;
					}
				}
				
			}
		}
		return cookieSb.toString() ;
	}

	public static String httpclientRs(String url,Object params, String requestMethod,Integer httpConnectTimeout,Integer httpReadTimeout) {
		return httpclientRs(url, params, requestMethod,"UTF-8", "UTF-8", httpConnectTimeout, httpReadTimeout,null);
	}
	
	
	// 不验证证书
	private static class AnyTrustStrategy implements TrustStrategy{  
	    @Override  
	    public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {  
	        return true;  
	    }
	}
	
	// http 连接池
	static PoolingHttpClientConnectionManager connManager = null ;
	static{
		/*
		//创建TrustManager
        X509TrustManager xtm = new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        //这个好像是HOST验证
        X509HostnameVerifier hostnameVerifier = new X509HostnameVerifier() {
            public boolean verify(String arg0, SSLSession arg1) {
                return true;
            }
            public void verify(String arg0, SSLSocket arg1) throws IOException {}
            public void verify(String arg0, String[] arg1, String[] arg2) throws SSLException {}
            public void verify(String arg0, X509Certificate arg1) throws SSLException {}
        };
		
        //TLS1.0与SSL3.0基本上没有太大的差别，可粗略理解为TLS是SSL的继承者，但它们使用的是相同的SSLContext
        SSLContext ctx;
        SSLSocketFactory socketFactory = null ;
		try {
			//TLS1.0与SSL3.0基本上没有太大的差别，可粗略理解为TLS是SSL的继承者，但它们使用的是相同的SSLContext
            ctx = SSLContext.getInstance("TLS");
            //使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
            ctx.init(null, new TrustManager[] { xtm }, null);
            //创建SSLSocketFactory
            socketFactory = new SSLSocketFactory(ctx);
            socketFactory.setHostnameVerifier(hostnameVerifier);
		} catch (NoSuchAlgorithmException e2) {
			e2.printStackTrace();
		}catch (KeyManagementException e) {
			e.printStackTrace();
		}
		*/
		//SSLContextBuilder builder = new SSLContextBuilder();
        //builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
        //SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build());
		
		RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.<ConnectionSocketFactory>create();  
		ConnectionSocketFactory plainSF = new PlainConnectionSocketFactory();  
		registryBuilder.register("http", plainSF); 
        
		KeyStore trustStore = null;
		SSLContext sslContext = null;
		try {
			trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			sslContext = SSLContexts.custom().loadTrustMaterial(trustStore, new AnyTrustStrategy()).build();
		} catch (KeyStoreException e2) {
			log.error("", e2);
		} catch (KeyManagementException e2) {
			log.error("", e2);
		} catch (NoSuchAlgorithmException e2) {
			log.error("", e2);
		}
		
		//LayeredConnectionSocketFactory sslSF = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.getDefaultHostnameVerifier()); // 这个好像还是会验证证书
		//LayeredConnectionSocketFactory sslSF = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);  // 这个不会验证证书
		LayeredConnectionSocketFactory sslSF = new SSLConnectionSocketFactory(sslContext, new HostnameVerifier(){
			@Override
			public boolean verify(String arg0, SSLSession arg1) {
				return true ;   // 不验证证书
			}
		});
		
		registryBuilder.register("https", sslSF);
		
		Registry<ConnectionSocketFactory> registry = registryBuilder.build(); 
		  
		//设置连接管理器  
		connManager = new PoolingHttpClientConnectionManager(registry);  
		// Increase max total connection to 200
		connManager.setMaxTotal(900);
	    // Increase default max connection per route to 20
		/**
		 * 这里route的概念可以理解为 运行环境机器 到 目标机器的一条线路。举例来说，我们使用HttpClient的实现来分别请求 www.baidu.com 的资源和 www.bing.com 的资源那么他就会产生两个route。
                    这里为什么要特别提到route最大连接数这个参数呢，因为这个参数的默认值为2，如果不设置这个参数值默认情况下对于同一个目标机器的最大并发连接只有2个！这意味着如果你正在执行一个针对某
                    一台目标机器的抓取任务的时候，哪怕你设置连接池的最大连接数为200，但是实际上还是只有2个连接在工作，其他剩余的198个连接都在等待，都是为别的目标机器服务的。
		 */
		connManager.setDefaultMaxPerRoute(500);
		
	}
	/**
	 * java.lang.IllegalStateException: Connection pool shut down
	 * @param url 
	 * @param params  1、List<org.apache.http.NameValuePair> list = new ArrayList<org.apache.http.NameValuePair>(); list.add(new BasicNameValuePair("pwd","2544"))  // 普通参数
	 *                2、String   json 服务端用这种方式获取
							 * ServletInputStream inputStream = request.getInputStream();
							   String requestJson = IOUtils.toString(inputStream);  
	                  3、MultipartEntityBuilder builder = MultipartEntityBuilder.create();   //文件
	                  //		builder.setCharset(Charset.forName("uft-8"));//设置请求的编码格式
					  builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);//设置浏览器兼容模式
					  int count=0;
					  for (File file:files) {
							//builder.addBinaryBody("file"+count, file);
							builder.addBinaryBody("upfile"+count, file, ContentType.DEFAULT_BINARY, "imageFileName");
							count++;
					  }		
					  builder.addTextBody("text", "msg", ContentType.TEXT_PLAIN);//设置请求参数
					  builder.addTextBody("fileTypes", params.get("fileTypes"));//设置请求参数
					  
	 * @param requestMethod POST|GET
	 * @param reqencoding 请求参数编码
	 * @param respencoding 响应结果编码
	 * @param httpConnectTimeout
	 * @param httpReadTimeout
	 * @param headerMap http头
	 * @param cookieStore 不为空时为自定义cookie请求
	 * @param proxy 代理
	 * @return
	 */
	public static String httpclientRs(String url, Object params, String requestMethod,String reqencoding,String respencoding,
			                          Integer httpConnectTimeout,Integer httpReadTimeout,Map<String, String> headerMap,CookieStore cookieStore,HttpHost proxy,String contentType) {
		long a = System.currentTimeMillis();
		httpState.set(null); // 初始化
		httpCookieStore.set(null); // 初始化
		if(cookieStore==null){
			cookieStore = new BasicCookieStore();
		} 
		String reqCookies = cookieStore.toString() ;
		//CloseableHttpClient httpclient = org.apache.http.impl.client.HttpClients.createDefault();
		
		//httpclient.getParams().setIntParameter(CoreConnectionPNames.SO_LINGER, httpReadTimeout);
		//httpclient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, httpReadTimeout);
		//httpclient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, httpConnectTimeout); 
//		if(httpConnectTimeout == null){
//			httpConnectTimeout = Util.toInt(SystemRequestContext.getObj("httpConnectTimeout")); // 优先取个性化设置
//			if(httpConnectTimeout!=null) {
//				// 使用完清理掉
//				SystemRequestContext.addObj("httpConnectTimeout", null);
//			}else {
//				httpConnectTimeout = Util.toint(ObjectStore.getProperty("httpConnectTimeout")) ;
//			}
//		}
//		if(httpReadTimeout == null){
//			httpReadTimeout = Util.toInt(SystemRequestContext.getObj("httpReadTimeout")); // 优先取个性化设置
//			if(httpReadTimeout!=null) {
//				// 使用完清理掉
//				SystemRequestContext.addObj("httpReadTimeout", null);
//			}else {
//				httpReadTimeout = Util.toint(ObjectStore.getProperty("httpReadTimeout")) ;
//			}
//		}
		// 代理设置 .setProxy(proxy)
		// http://zhangzhaoaaa.iteye.com/blog/2094680
		// http://bbs.csdn.net/topics/391844932?list=lz
		// http://www.cnblogs.com/sprinng/p/5776587.html
		// 4.3以上版本不设置超时的话，一旦服务器没有响应，等待时间N久(>24小时)。
		
		RequestConfig.Builder builder = RequestConfig.custom()
																				   .setSocketTimeout(httpReadTimeout)
																				   .setConnectTimeout(httpConnectTimeout)
																				   .setConnectionRequestTimeout(httpConnectTimeout) ;
		if(proxy!=null){
			builder.setProxy(proxy) ;
		}
		RequestConfig requestConfig = builder.build();
		
		// AsyncHttpClient AsyncHttpClient ;
		CloseableHttpClient httpclient = HttpClients.custom()
			    .setDefaultRequestConfig(requestConfig)
			    .disableContentCompression()  // 禁用内容自动解压缩
			    .setConnectionManager(connManager)
			    .setDefaultCookieStore(cookieStore)
			    .build();
		
		
		//通过SchemeRegistry将SSLSocketFactory注册到我们的HttpClient上
		//httpclient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", socketFactory, 443)) ;
		/*
		DecompressingHttpClient client = new DecompressingHttpClient();
		HttpClientBuilder HttpClientBuilder = org.apache.http.impl.client.HttpClientBuilder.create();
		*/
		
		if ("POST".equalsIgnoreCase(requestMethod)) {
			HttpPost httpPost = new HttpPost(url);
			
			httpPost.setHeader(HttpHeaders.CONNECTION, "close");
			httpPost.addHeader(HttpHeaders.ACCEPT_ENCODING, "gzip,deflate"); // 设置压缩
			httpPost.addHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");
			StringBuilder hsb = new StringBuilder();
			if (headerMap != null) {
				Set<String> set = headerMap.keySet() ;
				if(set != null){
					for (String k : set) {
						httpPost.addHeader(k, headerMap.get(k));
						hsb.append(k).append(":").append(headerMap.get(k)).append(";") ;
					}
				}
			}
			httpPost.setConfig(requestConfig);
			HttpResponse re = null ;
			try {
				if(params!=null){
					if(params instanceof String){
						// 将JSON进行UTF-8编码,以便传输中文
				        // String encoderJson = URLEncoder.encode((String)params, HTTP.UTF_8);
				        StringEntity se = new StringEntity((String)params,reqencoding);
				        if(StringUtils.isNotEmpty(contentType)){
				        	se.setContentType(contentType);
				        }
				        /*
				        httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json");
				        se.setContentType("text/json");
				        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
				        */
				        httpPost.setEntity(se);
					}else if(params instanceof MultipartEntityBuilder){
						MultipartEntityBuilder mb = ((MultipartEntityBuilder)params) ;
						HttpEntity entity = mb.build();// 生成 HTTP POST 实体
						
						httpPost.setEntity(entity); //设置请求参数
					}else{
						httpPost.setEntity(new UrlEncodedFormEntity((List<org.apache.http.NameValuePair>)params,reqencoding));
					}
				}
				// 提交数据
				re = httpclient.execute(httpPost);
				//获取响应状态码  
				int status = re.getStatusLine().getStatusCode();
				httpState.set(status);
				httpCookieStore.set(cookieStore);
				boolean isGzip = checkGzip(re) ; // 验证是否有Gzip压缩
				String rs = "" ;
				if(isGzip){
				    //需要进行gzip解压处理
				    rs = EntityUtils.toString(new GzipDecompressingEntity(re.getEntity()),respencoding) ;
				}else{
					rs = EntityUtils.toString(re.getEntity(),respencoding);
				}
				log.info(url + "?" + params + "@head="+hsb.toString() + "@" + reqCookies + ";状态码:" + status + ";gzip:"+isGzip+";返回结果:" + rs + ";cookies:" + cookieStore +"##耗时:"+(System.currentTimeMillis()-a));
				return rs;
				
			} catch (ClientProtocolException e) {
				log.error(url + "?" + params + "@head="+hsb.toString() + "@" + reqCookies + ";cookies:" + cookieStore +"##耗时:"+(System.currentTimeMillis()-a), e);
			} catch (IOException e) {
				log.error(url + "?" + params + "@head="+hsb.toString() + "@" + reqCookies + ";cookies:" + cookieStore +"##耗时:"+(System.currentTimeMillis()-a), e);
			} finally {
				
				/**
				 * org.apache.http.ConnectionClosedException: Premature end of Content-Length delimited message body (expected: 24225; received: 11536

				     某个使用netty作为server通讯，应用在传输大一点的数据时就会出现
				   org.apache.http.ConnectionClosedException: Premature end of Content-Length delimited message body (expected: 29869; received: 15846。
				     经过仔细分析，原来在接收数据时，程序员显式关闭了连接（同步调用），而netty是异步处理，当连接被显式关闭后，后来的数据再次触发读写操作时连结已经不可用。所以出现读不一完整数据的问题。
				 */
				/*
				try {
					if(re!=null){
						re.getEntity().getContent().close();
					}
				} catch (UnsupportedOperationException e1) {
					log.error("", e1);
				} catch (IOException e1) {
					log.error("", e1);
				}
				
				
				try {
				    httpPost.releaseConnection();
				}catch (Exception e) {
					log.error("", e);
				}
				*/
				
				
				
				
				try {
				    httpPost.abort();
				}catch (Exception e) {
					log.error("", e);
				}
				// 启用了http连接池所以不能在这里关闭  不然下次调用会报错：java.lang.IllegalStateException: Connection pool shut down
				/*
				try {
					httpclient.close();
				} catch (IOException e) {
					log.error("", e);
				}
				*/
			}
		} else {
			StringBuilder sb = new StringBuilder();
			if(params!=null){
				List<org.apache.http.NameValuePair> list = (List<org.apache.http.NameValuePair>)params ;
				for(int i=0,l=list.size();i<l;i++){
					org.apache.http.NameValuePair nameValuePair = list.get(i) ;
					sb.append("&").append(nameValuePair.getName()).append("=").append(nameValuePair.getValue()) ;
				}
				if(sb.length()>0){
					if(url.contains("?")){
						url = url + sb.toString() ;
					}else{
						url = url + "?" + sb.substring(1) ;
					}
				}
			}
			
			HttpGet httpGet = new HttpGet(url);
			
			//Header header = new BasicHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
			//httpGet.addHeader(header);
			//httpGet.addHeader("Cookie", "xxxxxxx");
			httpGet.setHeader(HttpHeaders.CONNECTION, "close");
			httpGet.addHeader(HttpHeaders.ACCEPT_ENCODING, "gzip,deflate");
			httpGet.addHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");
			StringBuilder hsb = new StringBuilder();
			if (headerMap != null) {
				Set<String> set = headerMap.keySet() ;
				if(set != null){
					for (String k : set) {
						httpGet.addHeader(k, headerMap.get(k));
						hsb.append(k).append(":").append(headerMap.get(k)).append(";") ;
					}
				}
			}
	        //httpGet.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
	        //httpGet.setHeader("Accept-Encoding", "gzip, deflate");
	        //httpGet.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
	        
	        //httpGet.setHeader("Connection", "keep-alive");
	        //httpGet.setHeader("Cookie", "__utma=226521935.73826752.1323672782.1325068020.1328770420.6;");
	        //httpGet.setHeader("Host", "www.cnblogs.com");
	        //httpGet.setHeader("refer", "http://www.baidu.com/s?tn=monline_5_dg&bs=httpclient4+MultiThreadedHttpConnectionManager");
			
			httpGet.setConfig(requestConfig);
			
			HttpResponse re = null;
			try {
				re = httpclient.execute(httpGet);
				//获取响应状态码  
				int status = re.getStatusLine().getStatusCode();
				httpState.set(status);
				httpCookieStore.set(cookieStore);
				boolean isGzip = checkGzip(re) ; // 验证是否有Gzip压缩
				String rs = "" ;
				if(isGzip){
				    //需要进行gzip解压处理
				    rs = EntityUtils.toString(new GzipDecompressingEntity(re.getEntity()),respencoding) ;
				}else{
					rs = EntityUtils.toString(re.getEntity(),respencoding);
				}
				
				log.info(url + "@head="+hsb.toString() + "@" + reqCookies + ";状态码:" + status + ";gzip:"+isGzip+";返回结果:" + rs + ";cookies:" + cookieStore +"##耗时:"+(System.currentTimeMillis()-a));
				return rs;
			} catch (ClientProtocolException e) {
				log.error(url + "@head="+hsb.toString() + "@" + reqCookies + ";状态码:" + ";cookies:" + cookieStore +"##耗时:"+(System.currentTimeMillis()-a), e);
			} catch (IOException e) {
				log.error(url + "@head="+hsb.toString() + "@" + reqCookies + ";状态码:" + ";cookies:" + cookieStore +"##耗时:"+(System.currentTimeMillis()-a), e);
			} finally {
				
				/**
				 * org.apache.http.ConnectionClosedException: Premature end of Content-Length delimited message body (expected: 24225; received: 11536

				     某个使用netty作为server通讯，应用在传输大一点的数据时就会出现
				   org.apache.http.ConnectionClosedException: Premature end of Content-Length delimited message body (expected: 29869; received: 15846。
				     经过仔细分析，原来在接收数据时，程序员显式关闭了连接（同步调用），而netty是异步处理，当连接被显式关闭后，后来的数据再次触发读写操作时连结已经不可用。所以出现读不一完整数据的问题。
				 */
				/*
				try {
					if(re!=null){
						re.getEntity().getContent().close();
					}
				} catch (UnsupportedOperationException e1) {
					log.error("", e1);
				} catch (IOException e1) {
					log.error("", e1);
				}
				
				
				try {
				   httpGet.releaseConnection();
				}catch (Exception e) {
					log.error("", e);
				}
				*/
				
				
				
				try {
				   httpGet.abort();
				}catch (Exception e) {
					log.error("", e);
				}
				// 启用了http连接池所以不能在这里关闭  不然下次调用会报错：java.lang.IllegalStateException: Connection pool shut down
				/*
				try {
					httpclient.close();
				} catch (IOException e) {
					log.error("", e);
				}
				*/
			}
		}
        return "" ;
	}
	public static String httpclientRs(String url, Object params, String requestMethod,String reqencoding,String respencoding,
            Integer httpConnectTimeout,Integer httpReadTimeout,Map<String, String> headerMap,CookieStore cookieStore,HttpHost proxy) {
		return httpclientRs(url, params, requestMethod, reqencoding, respencoding,
	             httpConnectTimeout, httpReadTimeout, headerMap, cookieStore, proxy,null) ;
	}
	public static String httpclientRs(String url, Object params, String requestMethod,String reqencoding,String respencoding,
            Integer httpConnectTimeout,Integer httpReadTimeout,Map<String, String> headerMap,CookieStore cookieStore){
		return httpclientRs(url, params, requestMethod,reqencoding,respencoding,httpConnectTimeout,httpReadTimeout,headerMap,cookieStore,null) ;
	}
	public static String httpclientRs(String url, Object params, String requestMethod,String reqencoding,String respencoding,
            Integer httpConnectTimeout,Integer httpReadTimeout,Map<String, String> headerMap) {
		return httpclientRs(url, params, requestMethod,reqencoding,respencoding,httpConnectTimeout,httpReadTimeout,headerMap,null) ;
	}
	// 验证服务端是否压缩
	public static boolean checkGzip(HttpResponse re){
		//Header[] headers = re.getAllHeaders();
		/*
		HttpEntity entity = re.getEntity();
		Header header = entity.getContentEncoding();
		*/
		Header[] headers = re.getHeaders("Content-Encoding");  // Content-Encoding:gzip
		boolean isGzip = false;
		for(Header h:headers){
			//System.out.println(h.getName()+"###"+h.getValue());
		    if(h.getValue().equals("gzip")){
	            //返回头中含有gzip
	            isGzip = true;
	            break ;
		    }
		}
		return isGzip ;
	}
	public static boolean isHttps(String url){
		return url.startsWith("https:") ;
	}

	/**
	 * 字符转码
	 * 
	 * @param theString
	 * @return
	 */
	public static String decodeUnicode(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer buffer = new StringBuffer(len);
		for (int i = 0; i < len;) {
			aChar = theString.charAt(i++);
			if (aChar == '\\') {
				aChar = theString.charAt(i++);
				if (aChar == 'u') {
					int val = 0;
					for (int j = 0; j < 4; j++) {
						aChar = theString.charAt(i++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							val = (val << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							val = (val << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							val = (val << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException(
									"Malformed      encoding.");
						}
					}
					buffer.append((char) val);
				} else {
					if (aChar == 't') {
						aChar = '\t';
					}
					if (aChar == 'r') {
						aChar = '\r';
					}
					if (aChar == 'n') {
						aChar = '\n';
					}
					if (aChar == 'f') {
						aChar = '\f';
					}
					buffer.append(aChar);
				}
			} else {
				buffer.append(aChar);
			}
		}
		return buffer.toString();
	}

	/**
	 * 处理参数
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public static String trsPara(String params, String reqencoding)
			throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		if (params == null || "".equals(params)) {
			return "";
		} else {
			String[] ps = params.split("&");
			for (int i = 0, l = ps.length; i < l; i++) {
				if (ps[i] == null) {
					continue;
				} else {
					String[] ps1 = ps[i].split("=");
					if (ps1.length != 2) {
						continue;
					} else {
						sb.append("&").append(ps1[0]).append("=").append(
								URLEncoder.encode(ps1[1], reqencoding));
					}
				}
			}
			return sb.length() <= 1 ? "" : sb.toString().substring(1);
		}
	}

	/**
	 * 判断是否ajax请求 通过报文头来判断
	 * 
	 * @param req
	 * @return
	 */
	public static boolean checkAjaxReq(HttpServletRequest req) {
		String callback = req.getParameter("callback") ;
		String requestType = req.getHeader("X-Requested-With");
		if ((requestType != null && requestType.equals("XMLHttpRequest")) || StringUtils.isNotEmpty(callback)) {
			return true;
		} else {
			return false;
		}
	}

	public static Cookie getCookie(HttpServletRequest request, String key) {
		Cookie[] cookies = null ;
		try{
			cookies = request.getCookies();
		}catch(RuntimeException e){
			e.printStackTrace();
		}
		Cookie ck = null;
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(key)) {
					ck = cookie;
					break;
				}
			}
		}
		return ck;
	}

	public static String getCookieValue(HttpServletRequest request, String key) {
		Cookie cookie = getCookie(request, key) ;
		if(cookie==null){
			return "" ;
		}else{
			return cookie.getValue();
		}
	}
	public static void setCookieValue(HttpServletRequest request, HttpServletResponse response, String key,
                                      String value) {
		setCookieValue(request,response,key,value,"UTF-8") ;
	}
	public static void setCookieValue(HttpServletRequest request, HttpServletResponse response, String key,
                                      String value, String encoding, int expiry) {
		if(StringUtils.isNotEmpty(encoding) && StringUtils.isNotEmpty(value)){
			try {
				//value = new String(value.getBytes(),encoding);
				value = URLEncoder.encode(value,encoding);
			} catch (Exception e) {
				log.error("", e) ;
			}
		}
		setCookieValue(request,response, key, value, expiry,null); // -1为关闭浏览器后cookie失效
	}
	public static void setCookieValue(HttpServletRequest request, HttpServletResponse response, String key,
                                      String value, String encoding){
		setCookieValue(request,response, key,value,encoding,-1) ;
	}
	
	public static void setCookieValue(HttpServletRequest request, HttpServletResponse response, String key,
                                      String value, int expiry, String contextPath) {
		setCookieValue(request,response, key, value, expiry,false,contextPath) ;
	}
	public static void setCookieValue(HttpServletRequest request, HttpServletResponse response, String key,
                                      String value, int expiry, boolean pDomain, String contextPath) {
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(expiry); // 过期时间 单位秒
		if(pDomain&&request!=null){
			String domain = request.getServerName() ;
			if(StringUtils.isNotEmpty(domain)){
				int sidx = domain.indexOf(".")+1 ;
				int length = domain.length() ;
				if(length>sidx){
					String fd = domain.substring(sidx) ;
					cookie.setDomain(fd);
				}
			}
			cookie.setPath("/"); // 如果是父级域名那么需要设置到根目录
		}else{
			if(request!=null){
				cookie.setPath(request.getContextPath()+"/");
			}else{
				if(StringUtils.isNotEmpty(contextPath)){
					cookie.setPath(contextPath+"/");
				}else{
					cookie.setPath("/");
				}
			}
		}
		//System.out.println("Domain:"+cookie.getDomain());
		response.addCookie(cookie);
	}

    /**
     * 获取鉴权系统的tokenId
     * @param request
     * @return
     */
	public static String getSSOTokenIdByCookie(HttpServletRequest request) {
		Cookie cookie = getCookie(request, "ssoTokenId");
		if (cookie == null) {
			return "" ;
		} else {
			return cookie.getValue();
		}
	}
	// URI 处理
	public static String uriFormat(String uri,String path){
		//去除双斜杠
		uri = analyzeAuthURL(uri) ;
		//去除工程名
		uri = rmContextPath(uri, path);
		// 去除 .web  .app
		uri = rmSuffix(uri) ;
		return uri ;
	}
	// 去除工程名
	public static String rmContextPath(String uri,String path){
		if(!"/".equals(path) && StringUtils.isNotEmpty(path)){
			int nextIdx = uri.indexOf("/", uri.indexOf("/")+1) ;
			if(nextIdx>0){
				if(uri.startsWith(path)){
					uri = uri.substring(nextIdx);
				}
			}
		}
		return uri ;
	}
	
	
	//处理URL双斜杠问题
	public static String analyzeAuthURL(String URL){
		URL = URL.replace("//", "/") ;
		if(URL.contains("//")){
			URL = analyzeAuthURL(URL) ;
		}
		return URL ;
	}
	// 去除 .web  .app .* ,不能简单的只去除.web .app 如果是.其他的那么就权限拦截失败了
	public static String rmSuffix(String URL){
		String[] uu = URL.split("\\.") ;
		/*
		if(URL.endsWith(".web")){
			URL = URL.substring(0, URL.length()-4) ;
		}else if(URL.endsWith(".app")){
			URL = URL.substring(0, URL.length()-4) ;
		}
		*/
		return uu[0] ;
	}
	//获取action名字
	public static String getActionName(String uri){
		if(!"/".equals(uri) && StringUtils.isNotEmpty(uri)){
			if(uri.contains("/")){
				int lidx = uri.lastIndexOf("/") ;
				uri = uri.substring(lidx) ;
			}
		}
		return uri ;
	}
	
	//从request里面取出bean里面一样的字段并赋值给bean
	public static void initBean(HttpServletRequest request, Object bean) throws IntrospectionException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		Class<?> type = bean.getClass(); 
        BeanInfo beanInfo = Introspector.getBeanInfo(type);
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors(); 
        for (int i=0,l=propertyDescriptors.length; i<l; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i]; 
            String propertyName = descriptor.getName(); 
            if (!propertyName.equals("class")) {
            	String value = request.getParameter(propertyName);
            	if(StringUtils.isNotEmpty(value)){
            		Object[] args = new Object[1]; 
                    args[0] = value; 
                	descriptor.getWriteMethod().invoke(bean, args);
            	}
            } 
        } 
	}
	
}
