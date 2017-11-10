package com.wjl.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.wjl.common.util.IDUtils;
import com.wjl.common.util.JsonUtils;

/**
 * 上传图片的Controller
 * @author wujiale
 * 2017-10-17 下午5:46:30
 */
@Controller
public class FileUploadController {
	@Value("${IMAGE_URL}")
	private String IMAGE_URL;
	
	@RequestMapping("/pic/upload")
	@ResponseBody
	public String upload(@RequestParam("uploadFile")CommonsMultipartFile file,HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object>();
		String path = request.getRealPath("WEB-INF/image");
		File file2 = new File(path);
		if (!file2.exists()) {
			file2.mkdirs();
		}
		String oldName = file.getOriginalFilename();
		String newName = IDUtils.getImageName()+oldName.substring(oldName.lastIndexOf("."));
		try {
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path,newName));
			map.put("error", 0);
			map.put("url", IMAGE_URL+"/image/"+newName);
		} catch (IOException e) {
			map.put("error", 1);
			map.put("message", "上传文件出错");
			e.printStackTrace();
		}
		String json = JsonUtils.objectToJson(map);
		return json;
	}
}
