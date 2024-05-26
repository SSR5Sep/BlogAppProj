package com.codewithdurgesh.blog.services;

import java.util.List;
import com.codewithdurgesh.blog.payloads.PostDto;
import com.codewithdurgesh.blog.payloads.PostResponse;

public interface PostService {

	//create
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
	
	
	//update
	PostDto updatePost(PostDto postDto, Integer postId);
	
	
	//delete
	void deletePost(Integer postId);
	
	
	//get single post
	PostDto getPostByID(Integer postId);
	
	
	//get all post
	PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
	
	//get all post by category
	List<PostDto> getPostsByUser(Integer userId);
	
	
	//get post by category
	public List<PostDto> getPostsbyCategory(Integer categoryId);
	
	
	//search post
	List<PostDto> searchPosts(String keyword);
	
	
	
}
