package com.example.configs;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class CloudinaryConfiguration {
	@Autowired
	@Lazy
	private Cloudinary cloudinary;

	@Bean
	public Cloudinary cloudinary() {
		// Set your Cloudinary credentials
		Dotenv dotenv = Dotenv.load();
		Cloudinary cloudinary = new Cloudinary(dotenv.get("CLOUDINARY_URL"));
		if(cloudinary == null) {
            throw new IllegalStateException("CLOUDINARY_URL environment variable not set");
		}
		cloudinary.config.secure = true;
		return cloudinary;
	}

	Map<String, Object> options = ObjectUtils.asMap("use_filename", true, "unique_filename", false, "overwrite", true);

	public Map<String, Object> uploadOnCloudinary(MultipartFile file) {
		Map<String, Object> cloudinaryMap = null;
		try {
            File tempFile = convertMultipartFileToFile(file);
            cloudinaryMap = cloudinary.uploader().upload(tempFile, options);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cloudinaryMap;
	}
	
	

	private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File tempFile = File.createTempFile(file.getOriginalFilename(), "");
        file.transferTo(tempFile);
        return tempFile;
    }
}
