package com.codewithdurgesh.blog.controllers;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codewithdurgesh.blog.payloads.FileResponse;
import com.codewithdurgesh.blog.services.FileService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/file")
public class FileController {
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	@PostMapping("/upload")
	public ResponseEntity<FileResponse> fileUpload(@RequestParam("image")  MultipartFile image)
	{
		String filename=null;
		try {
			filename = this.fileService.uploadImage(path, image);
		} catch (IOException e) {
			e.printStackTrace();
			
		return new ResponseEntity<>(new FileResponse(null, "image is not ploaded due to server error"),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(new FileResponse(filename, "image uploaded succesfully"),HttpStatus.OK);
			
	}
	//method to serve file
	//(URL USED BY USER-localhost://8085/images/abc.png)
	
	@GetMapping("/profiles/{imageName}")
	public void downloadImage(@PathVariable
			("imageName")String imageName, 
				HttpServletResponse response) 
						throws IOException
	{	
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(org.springframework.http.MediaType.IMAGE_JPEG_VALUE);
		org.springframework.util.StreamUtils.copy(resource, response.getOutputStream());
	}

}
