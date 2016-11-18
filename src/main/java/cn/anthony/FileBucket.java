package cn.anthony;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传通用类
 * 
 * @author Administrator
 *
 */
public class FileBucket {
    MultipartFile file;

    public MultipartFile getFile() {
	return file;
    }

    public void setFile(MultipartFile file) {
	this.file = file;
    }
}
