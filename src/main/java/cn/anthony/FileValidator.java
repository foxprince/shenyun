package cn.anthony;

import java.util.Arrays;
import java.util.List;

import org.codehaus.plexus.util.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * 文件上传验证类
 * 
 * @author Administrator
 *
 */
@Component
public class FileValidator implements Validator {
    public boolean supports(Class<?> clazz) {
	return FileBucket.class.isAssignableFrom(clazz);
    }

    static List<String> PIC_EXT_LIST = Arrays.asList(new String[] { "BMP", "JPG", "JPEG", "PNG", "GIF" });
    public void validate(Object obj, Errors errors) {
	FileBucket file = (FileBucket) obj;
	if (file.getFile() != null) {
	    if (file.getFile().getSize() == 0) {
		errors.rejectValue("file", "请选择上传文件");
	    }
	    if (!PIC_EXT_LIST.contains(FileUtils.getExtension(file.getFile().getOriginalFilename()).toUpperCase())) {
		errors.rejectValue("file", "只支持jpg、jpeg、bmp、png和gif格式的文件");
	    }
	}
    }
}
