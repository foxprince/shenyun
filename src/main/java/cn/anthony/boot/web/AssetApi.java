package cn.anthony.boot.web;

import cn.anthony.boot.util.Constant;
import cn.anthony.util.StringTools;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/api/asset")
@Slf4j
public class AssetApi {
	@Resource
	Constant constant;

	@RequestMapping(value = {"/preview"})
	public void preview(String fileName, String size, HttpServletResponse response) throws IOException {
		File f = new File(constant.getUploadAbsoluteDir() + Constant.FILE_SEPA + fileName);
		if (f.exists() && f.isFile()) {
			InputStream is = new FileInputStream(f);
			if (size != null)
				if (size.equals("small"))
					Thumbnails.of(f).size(215, 215).outputFormat("png").toOutputStream(response.getOutputStream());
				else if (size.equals("medium"))
					Thumbnails.of(f).size(430, 430).outputFormat("png").toOutputStream(response.getOutputStream());
				else if (size.equals("orig")) {
					IOUtils.copy(is, response.getOutputStream());
				} else if (f.length() > 1024 * 1024 * 5)
					Thumbnails.of(f).size(1024, 1024).outputFormat("png").toOutputStream(response.getOutputStream());
				else
					IOUtils.copy(is, response.getOutputStream());
			else {
				if (f.length() > 1024 * 1024 * 5)
					Thumbnails.of(f).size(1024, 1024).outputFormat("png").toOutputStream(response.getOutputStream());
				else
					IOUtils.copy(is, response.getOutputStream());
			}
			response.flushBuffer();
			is.close();
		}
	}

	/**
	 * 针对huploadify跨域的设置
	 *
	 * @param req
	 * @param resp
	 */
	@RequestMapping(value = {"/upload","/uploadOnly"}, method = RequestMethod.OPTIONS)
	public void uploadOptions(HttpServletRequest req, HttpServletResponse resp) {
		resp.addHeader("Content-Type", "application/json;charset=UTF-8");
		resp.addHeader("Access-Control-Allow-Origin", "*");
		resp.addHeader("Access-Control-Allow-Headers", "X-Requested-With, Content-Type");
	}

	@PostMapping(value = {"/uploadOnly"})
	public JsonResponse uploadOnly(@RequestParam("file") MultipartFile file) throws IOException {
		String orighFilename = file.getOriginalFilename();
		String fileName = StringTools.createFileNameWithYM(orighFilename);// file.getOriginalFilename();//
		String thumbName = StringTools.getThumbName(fileName);
		File f = new File(constant.getUploadAbsoluteDir() + Constant.FILE_SEPA + fileName);
		FileUtils.copyInputStreamToFile(file.getInputStream(), f);
		// 生成缩略图
		Thumbnails.of(f).size(120, 120)
				.toFile(new File(constant.getUploadAbsoluteDir() + Constant.FILE_SEPA + thumbName));
		if (f.length() > 1024 * 1024 * 1)
			Thumbnails.of(f).size(1024, 1024).outputQuality(0.5f).toFile(f);
		else if (f.length() > 1024 * 1024 * 0.5)
			Thumbnails.of(f).size(1024, 1024).toFile(f);
		return new JsonResponse(Arrays.asList(orighFilename.substring(0, orighFilename.lastIndexOf(".")), fileName, thumbName));
	}

	@Data
	class AssetReq {
		String type, title, labels;
		List<String> location;
	}
}
