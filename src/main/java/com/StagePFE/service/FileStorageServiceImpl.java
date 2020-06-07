package com.StagePFE.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.StagePFE.config.FileStorageProperties;
import com.StagePFE.exception.FileStorageException;
import com.StagePFE.exception.MyFileNotFoundException;

//@Service
//public class FileStorageServiceImpl implements FileStorageService{
	
//	private final Path fileStorageLocation;
//	
//	
//	@Autowired
//	public FileStorageServiceImpl(FileStorageProperties fileStorageProperties) {
//		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
//
//		try {
//			Files.createDirectories(this.fileStorageLocation);
//		} catch (Exception ex) {
//			throw new FileStorageException("Impossible de créer le répertoire dans lequel les fichiers téléchargés seront stockés", ex);
//		}
//	}
//	
//	@Override
//	public String storeFile(MultipartFile file) throws IOException {
//		
//		if (!(file.getOriginalFilename().endsWith(".png") || file.getOriginalFilename().endsWith(".jpeg") || file.getOriginalFilename().endsWith("jpg")))
//			throw new FileStorageException("Seules les images PNG, JPEG et JPG sont autorisées");
//		
//		File f = new File("C://TMP//"+file.getOriginalFilename());
//		
//		 f.createNewFile();
//			FileOutputStream fout = new FileOutputStream(f);
//			fout.write(file.getBytes());
//			fout.close();
//			 BufferedImage image = ImageIO.read(f);
//		   int height = image.getHeight();
//		   int width = image.getWidth();
//		   if(width>300 || height>300) {
//			   if(f.exists())
//				   f.delete();
//			   throw new FileStorageException("Dimensions de fichier non valides. La dimension du fichier doit être supérieure à 300 X 300");
//		   }
//		
//		   if(f.exists())
//			   f.delete();
//		
//		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//		try {
//			if (fileName.contains("..")) {
//				throw new FileStorageException("Désolé! Le nom de fichier contient une séquence de chemin non valide" + fileName);
//			}
//			String newFileName = System.currentTimeMillis() + "_" + fileName;
//			Path targetLocation = this.fileStorageLocation.resolve(newFileName);
//			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
//			return newFileName;
//		} catch (IOException ex) {
//			throw new FileStorageException(String.format("Could not store file %s !! Please try again!", fileName), ex);
//		}
//
//	}
//
//	@Override
//	public Resource loadFileAsResource(String fileName) {
//		try {
//			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
//			Resource resource = new UrlResource(filePath.toUri());
//			if (resource.exists()) {
//				return resource;
//			} else {
//				throw new MyFileNotFoundException("File not found " + fileName);
//			}
//		} catch (MalformedURLException ex) {
//			throw new MyFileNotFoundException("File not found " + fileName, ex);
//		}
//	}

//}
