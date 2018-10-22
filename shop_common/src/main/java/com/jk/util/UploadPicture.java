package com.jk.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

public class UploadPicture {
	/**
	 * <pre>uploadPhoto(这里用一句话描述这个方法的作用)
	 * @param request : HttpServletRequest的对象
	 * @param Photo ：所要上传的图片文件
	 * @return 将保存好的文件名称以Map形式返回</pre>
	 */
	public static Map<String,Object> uploadPhoto(HttpServletRequest request,MultipartFile uploadFile){
		Map<String,Object> map = new HashMap<String,Object>();
		if(uploadFile.getSize() > 0){
			String realpath = request.getServletContext().getRealPath("/") + "/upload/";
			File file = new File(realpath);
			if(!file.exists()){
				file.mkdirs();
			}
			
			String originalFilename = uploadFile.getOriginalFilename();
			String newFileName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));
			
			try {
				uploadFile.transferTo(new File(realpath + newFileName));
				map.put("filePath", "/upload/" + newFileName);
				map.put("fileName", originalFilename);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return map;
	}
	
	/**
	 * <pre>downLoadFile(这里用一句话描述这个方法的作用)   
	 * 用于图片下载操作
	 * @param response ：HttpServletResponse对象
	 * @param request：HttpServletRequest对象
	 * @param photoTitle：要下载的图片名称</pre>
	 */
	public static void downLoadFile(HttpServletResponse response, HttpServletRequest request, String photoTitle) {
		String realPath = request.getServletContext().getRealPath("/") + photoTitle;
		File file = new File(realPath);
		if (file.exists()) {
			response.setCharacterEncoding("utf-8");
	        response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
	        response.setHeader("Content-Type", "application/octet-stream");
	        FileInputStream inputStream = null;
	        ServletOutputStream outputStream = null;
	        try {
	        	inputStream = new FileInputStream(file);
				outputStream = response.getOutputStream();
				byte[] b = new byte[1024];
				int read = inputStream.read(b);
				while (-1 != read) {
					outputStream.write(b);
					outputStream.flush();
					//读取下一次
					read = inputStream.read(b);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (null != outputStream) {
					try {
						outputStream.close();
						outputStream = null;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (null != inputStream) {
					try {
						inputStream.close();
						inputStream = null;	//方便GC更快回收
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
