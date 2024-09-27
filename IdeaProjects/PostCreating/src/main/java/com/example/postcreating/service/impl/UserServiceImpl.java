package com.example.postcreating.service.impl;

import com.example.postcreating.dto.UserDTO;
import com.example.postcreating.entity.User;
import com.example.postcreating.handlers.NotFoundException;
import com.example.postcreating.mapper.UserMapper;
import com.example.postcreating.repository.UserRepository;
import com.example.postcreating.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private UserMapper mapper;

    @Override
    public UserDTO addUser(UserDTO userDTO) {
        log.debug("Adding a user with email: {}", userDTO.getEmail());

        User savedUser = (User) userRepository.saveAndFlush(mapper.toModule(userDTO));
        UserDTO savedUserDTO = mapper.toDTO(savedUser);

        log.info("User added with ID: {}", savedUserDTO.getId());
        return savedUserDTO;

    }

    @Override
    public UserDTO updateUser(final Long id, UserDTO userDTO) {
        log.debug("Updating user with ID: {}", id);

        User realUser = findUserById(id);
        updateUserInfoDTO(realUser, userDTO);
        userRepository.save(realUser);

        log.info("User updated with ID: {}", id);
        return mapper.toDTO(realUser);
    }

    @Override
    public UserDTO getFromId(Long id) {
        log.debug("Retrieving user with ID: {}", id);

        User user = findUserById(id);

        log.info("User retrieved with ID: {}", id);
        return mapper.toDTO(user);
    }

    @Override
    public void eraseUser(final Long id) {
        log.debug("Deleting user with ID: {}", id);

        userRepository.deleteById(id);

        log.info("User deleted with ID: {}", id);
    }

    @Override
    public List<UserDTO> getEveryUser() {
        return UserMapper.toDTOUserList(userRepository.findAll());
    }

    @Override
    public boolean userExists(Long id) {
        final boolean exists = userRepository.existsById(id);

        log.debug("User existence check for ID: {} - Exists: {}", id, exists);
        return exists;    }

    private User findUserById(final Long id){

        try {
            return userRepository.findById(id).orElse(null);
        }catch (Exception e){
            System.out.println("User with ID: {}" + id + " not found");
        }
        return null;

    }

    private void updateUserInfoDTO(User user, UserDTO userDTO) {
        if (userDTO.getName() != null) {
            user.setName(userDTO.getName());
        }
        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail());
        }
    }
}
