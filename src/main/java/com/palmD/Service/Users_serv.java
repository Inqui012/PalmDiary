package com.palmD.Service;

import javax.persistence.EntityExistsException;
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
	private final Users_repo usersRepo;
	
	public Users registerUser(UsersRegist_dto userRegistDto) {
		if(usersRepo.findById(userRegistDto.getRegistId()).isPresent()) throw new EntityExistsException("이미 가입된 회원입니다.");
		Users registUser = Users.registUser(userRegistDto, ac.getBean(PasswordEncoder.class));
		return usersRepo.save(registUser);			
	}
	
	@Transactional(readOnly = true)
	public Users findUser (String userid) {
		return usersRepo.findById(userid).orElseThrow(() -> new EntityNotFoundException("유저를 찾을 수 없습니다."));
	}
	
	public void deleteUser (String userId) {
		Users deleteUser = findUser(userId);
		usersRepo.delete(deleteUser);
	}
	
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		Users findUser = findUser(userId);
		return User.builder().username(findUser.getUsersId()).password(findUser.getUsersPw()).roles(findUser.getUsersRole().toString()).build();
	}
	
	public UsersProfile_dto loadUserProfile (String userId) {
		Users findUser = findUser(userId);
		return UsersProfile_dto.mappedOf(findUser);
	}
	
}
