package cn.stb.stbcrmserver.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class TemplateService {
	static final List<String> FILE_LIST = Arrays.asList("static/中文报价单.xlsx", "static/出口商业发票模板.xlsx", "static/出口箱单模板.xls",
			"static/出口订单英文合同.xlsx", "static/英文报价单.xlsx", "static/订单中文合同.doc");
	static final List<String> FILE_NAMES = Arrays.asList("中文报价单.xlsx", "出口商业发票模板.xlsx", "出口箱单模板.xls",
			"出口订单英文合同.xlsx", "英文报价单.xlsx", "订单中文合同.doc");

	public void downLoad(HttpServletResponse res) throws Exception {
		//文件名 可以通过形参传进来
		String fileName = "模板.docx";
		//要下载的文件地址
		String filepath = "/Users/yanbin/Desktop/个人材料/沿假条.docx";
		ServletOutputStream out = null;
		FileInputStream in = null;
        try {
	        // 取得输如流
	        in = new FileInputStream(filepath);
	        res.setContentType("application/x-download;charset=GBK");
	        res.setHeader("Content-Disposition",
			        "attachment;filename=" + new String(fileName.getBytes("utf-8"), "iso-8859-1"));
			// 输出流
	        out = res.getOutputStream();
	        // 复制
	        int len = 0;
	        byte[] buffer = new byte[1024 * 10];
	        while ((len = in.read(buffer)) != -1) {
		        out.write(buffer, 0, len);
	        }
	        out.flush();
            } catch (IOException e) {
        	    e.printStackTrace();
			} finally {
	             // 文件的关闭放在finally中
                try {
	                if (in != null) {
	                    in.close();
                    }
                } catch (IOException e) {
	                System.out.println(e.toString());
	            }
                try {
	                if (out != null) {
	                    out.close();
	                }
	            } catch (IOException e) {
	                System.out.println(e.toString());
                }
            }
	}


	public void buildExcelDocument(HttpServletResponse response){
		String filePath = "/Users/yanbin/Desktop/个人材料/沿假条.docx";
		if(filePath.contains("%")){
			try {
				filePath = URLDecoder.decode(filePath,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		ServletOutputStream out = null;
		FileInputStream in = null;
		try {
			in = new FileInputStream(new File(filePath));
			String[] dir = filePath.split("/");
			String fileName = dir[dir.length-1];
			String[] array = fileName.split("[.]");
			String fileType = array[array.length-1].toLowerCase();
			//设置文件ContentType类型
			if("jpg,jepg,gif,png".contains(fileType)){//图片类型
				response.setContentType("image/"+fileType);
			} else if("pdf".contains(fileType)){//pdf类型
				response.setContentType("application/pdf");
			} else{//自动判断下载文件类型
				response.setContentType("multipart/form-data");
			}
			//设置文件头：最后一个参数是设置下载文件名
			fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());
			response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
			out = response.getOutputStream();
			// 读取文件流
			int len = 0;
			byte[] buffer = new byte[1024 * 10];
			while ((len = in.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			out.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
				in.close();
			} catch (NullPointerException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	@Autowired
	ApplicationContext applicationContext;

	/**
	 * 可下载 resources 夹子下的文件
	 * @param response
	 */
	public void downloadTemp(String template, HttpServletResponse response) {
		InputStream inputStream = null;
		ServletOutputStream servletOutputStream = null;
		int index = getIndex(template);
		try {
			String filename = FILE_NAMES.get(index);
			String path = FILE_LIST.get(index);
			Resource resource = applicationContext.getResource("classpath:"+path);
			response.setContentType("application/vnd.ms-excel");
			response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			response.addHeader("charset", "utf-8");
			response.addHeader("Pragma", "no-cache");
			String encodeName = URLEncoder.encode(filename, StandardCharsets.UTF_8.toString());
			response.setHeader("Content-Disposition", "attachment; filename=\"" + encodeName + "\"; filename*=utf-8''" + encodeName);

			inputStream = resource.getInputStream();
			servletOutputStream = response.getOutputStream();
			IOUtils.copy(inputStream, servletOutputStream);
			response.flushBuffer();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (servletOutputStream != null) {
					servletOutputStream.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
				// 召唤jvm的垃圾回收器
				System.gc();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void getIp() {
		InetAddress ia = null;
		try {
			ia = ia.getLocalHost();
			String localName = ia.getHostName();
			String localIp = ia.getHostAddress();
			System.out.println(localName);
			System.out.println(localIp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getIndex(String template) {
		int index = 0;
		for (int i = 0; i <= FILE_NAMES.size() - 1; i++) {
			if (FILE_LIST.get(i).contains(template)) {
				index = i;
				break;
			}
		}
		return index;
	}

	/**
	 * 前端代码
	 */
//	down() {
//		location.href = 'http://localhost:9911/crm/TemplateController/downLoadTest';
//	}

}
