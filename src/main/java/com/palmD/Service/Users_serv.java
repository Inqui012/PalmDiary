package com.palmD.Service;

import javax.persistence.EntityNotFoundException;

import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.palmD.DTO.UsersProfile_dto;
import com.palmD.DTO.UsersRegist_dto;
import com.palmD.Entity.Users;
import com.palmD.Repository.Users_repo;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class Users_serv implements UserDetailsService {
	
	private final ApplicationContext ac;
//	순환참조........
//	private final PasswordEncoder passwordEncoder;
	private final Users_repo userRepo;
	
	public Users registerUser(UsersRegist_dto UserRegistDto) {
//		checkUser(UserRegistDto.getRegistId());			
		Users registUser = Users.registUser(UserRegistDto, ac.getBean(PasswordEncoder.class));
		return userRepo.save(registUser);			
	}
	
	@Transactional(readOnly = true)
	public void checkUser (String id) {
		userRepo.findById(id).orElseThrow(EntityNotFoundException::new);
	}
	
	public void deleteUser (String userId) {
		Users deleteUser = userRepo.findById(userId).orElseThrow(EntityNotFoundException::new);
		userRepo.delete(deleteUser);
	}
	
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		Users findUser = userRepo.findById(userId).orElseThrow(EntityNotFoundException::new);
		return User.builder().username(findUser.getUsersId()).password(findUser.getUsersPw()).roles(findUser.getUsersRole().toString()).build();
	}
	
	public UsersProfile_dto loadUserProfile (String userId) {
		Users findUser = userRepo.findById(userId).orElseThrow(EntityNotFoundException::new);
		return UsersProfile_dto.mappedOf(findUser);
	}
	
}
