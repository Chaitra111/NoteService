package com.bridgelabz.eurekaServerNoteService.note.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Chaitra Ankolekar
 * Purpose :Feign service to perform feignclient
 */
@FeignClient(name="ToDoEurekaUserService",url="http://localhost:8082")
@Service
public interface FeignService {

	@RequestMapping("/users/getuser")
	public List<?> getUsers();
}
