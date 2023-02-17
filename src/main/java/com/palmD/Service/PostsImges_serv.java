package com.palmD.Service;

import java.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.palmD.DTO.PostsImges_dto;
import com.palmD.Entity.Posts;
import com.palmD.Entity.PostsImges;
import com.palmD.Repository.PostsImges_repo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostsImges_serv {
	
	@Value("${postedImgLocation}")
	private String uploadPath;
	private final PostsImges_repo postsImgesRepo;
	private final File_serv fileServ;
	
	public PostsImges addPostsImges (List<MultipartFile> uploadImgList, Posts Post) throws Exception {
		String imgOriName = "";
		String imgName = "";
		String imgUrl = "";
		for(MultipartFile img : uploadImgList) {
			imgOriName = img.getOriginalFilename();
			if(!StringUtils.isEmpty(imgOriName)) {
				imgName = fileServ.uploadFile(uploadPath, imgOriName, img.getBytes());
				imgUrl = "/palmD/postedImg/" + imgName;
			}
			PostsImges addPostsImges = PostsImges.createPostsImges(imgName, imgOriName, imgUrl);
			addPostsImges.setPostId(Post);
			postsImgesRepo.save(addPostsImges);
		}
		return null;
	}
	
	public void editPostsImges (List<MultipartFile> uploadImgList, Posts Post) throws Exception {
		List<PostsImges> editPostsImgesList = postsImgesRepo.findByPostIdOrderByImgId(Post);
		String imgOriName = "";
		String imgName = "";
		String imgUrl = "";
		for(int i = 0; i < uploadImgList.size(); i++) {
			if(!uploadImgList.get(i).isEmpty()) {
				PostsImges editPostsImge = editPostsImgesList.get(i);	
				if(!StringUtils.isEmpty(editPostsImge.getImgName())) {
					fileServ.deleteFile(uploadPath + "/" + editPostsImge.getImgName());
				}
				imgOriName = uploadImgList.get(i).getOriginalFilename();
				imgName = fileServ.uploadFile(uploadPath, imgOriName, uploadImgList.get(i).getBytes());
				imgUrl = "/palmD/postedImg/" + imgName;
				
				editPostsImge.updatePostsImges(imgName, imgOriName, imgUrl);
			}
		}
	}
	
	public List<PostsImges_dto> findPostImgConvert (Posts post) {
		List<PostsImges> postImgList = postsImgesRepo.findByPostIdOrderByImgId(post);
		List<PostsImges_dto> postImgDtoList = new ArrayList<>();
		if(!postImgList.isEmpty()) {
			for(PostsImges img : postImgList) {
				postImgDtoList.add(PostsImges_dto.convertFrom(img));
			}
		}
		return postImgDtoList;
	}
}
